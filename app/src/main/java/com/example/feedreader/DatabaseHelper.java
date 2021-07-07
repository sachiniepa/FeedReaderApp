package com.example.feedreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import javax.xml.transform.sax.SAXTransformerFactory;

public class DatabaseHelper extends SQLiteOpenHelper {
    //creating the FeedReader database
    public DatabaseHelper(Context context) {
        super(context, "FeedReader.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creatig the users table
        db.execSQL("Create table users(email text primary key, name text, contactNo text, bday text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //dropping the users if the table already exists
        db.execSQL("drop table if exists users");
    }

    /*inserting a new user to the database
    * @param email: String
    * @param name : String
    * @param phone : String
    * @param bday : String
    * @param pw : String*/
    public boolean insertUser(String email, String name, String phone, String bday, String pw){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("name",name);
        contentValues.put("contactNo",phone);
        contentValues.put("bday",bday);
        contentValues.put("password",pw);
        long m = db.insert("users",null,contentValues);
        if(m == -1){
            return false;
        }else{
            return true;
        }
    }

    /*checking whether the user is an already registered user
    * @param email : String */
    public boolean checkUser(String email){
        String query = "SELECT * FROM users WHERE email = '"+ email+"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() > 0){
            return false;
        }else{
            return true;
        }
    }

    /*checking whether the username and password are matching
    * @param un : String
    * @param pw : String*/
    public int validateUser(String un, String pw){
        Log.e("Log2","Login2");
//        String q1 = "SELECT * FROM users WHERE email = '"+ un +"' AND password = '"+ pw +"';";
        //getting the record with the matching username
        String q1 = "SELECT * FROM users WHERE email = '"+ un +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(q1,null);
//        Log.d("count", cursor.getCount() + " post rows");
//        Log.v("cursor", DatabaseUtils.dumpCursorToString(cursor));
        Log.e("Log1","Login1");
        if(cursor.getCount()==1){
            cursor.moveToFirst();
            //getting the password from the cursor object returned
            String pwd = cursor.getString(cursor.getColumnIndex("password"));
            //checking for password equality
            if(pw.equals(pwd))
                //returns 1 if the password is correct.
                return 1;
            else
                //returns 0 if the password is incorrect
                return 0;
        }else {
            //returns -1 if  matching record with the username is not found.
            return -1;
        }
        //cursor.close();
    }

    /*returns an ArrayList<String> containing the userdetails with the given username
    * @params un : String*/
    public ArrayList<String> getUserDetails(String un){
        Log.e("test7","inside method");
        String q1 = "SELECT * FROM users WHERE email = '"+ un +"';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(q1,null);
        ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            mArrayList.add(cursor.getString(cursor.getColumnIndex("name")));
            mArrayList.add(cursor.getString(cursor.getColumnIndex("email")));
            mArrayList.add(cursor.getString(cursor.getColumnIndex("contactNo")));
            mArrayList.add(cursor.getString(cursor.getColumnIndex("bday")));
            cursor.moveToNext();
        }
        return mArrayList;
    }

    /*updates the user with the given username
    * @param name : String
    * @param un : String
    * @param phone : String
    * @param dob : String*/
    public boolean updateUser(String name, String un, String phone, String dob){
        Log.e("update","update");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("contactNo",phone);
        contentValues.put("bday",dob);
//        String filter = "email = '" + un + "'";
        String[] args = new String[]{un};
        int m = db.update("users",contentValues,"email=?",args);
        if(m != 1){
            return false;
        }else{
            Log.v("success","success");
            return true;
//
        }
    }

    /*deletes the user with the given username
    * @param un : String*/
    public boolean deleteUser(String un){
        SQLiteDatabase db = this.getWritableDatabase();
        int m = db.delete("users","email = '"+ un +"'",null);
        if (m != 1){
            return false;
        }
        else {
            return true;
        }
    }

    /*checks for password equality and if equal updates the database
    * @param pw1 : String
    * @param pw2 : String
    * @param un : String*/
    public boolean changePassword(String pw1, String pw2,String un){
        if(pw1.equals(pw2)){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("password",pw1);
            int m = db.update("users",contentValues,"email = '"+ un +"'", null);
            if(m>0)
                return true;
            else
                return false;
        }else{
            return false;
        }
    }
//    public Cursor getUserDetails(String username){
//        Log.e("method","met");
//        String q1 = "SELECT * FROM users WHERE email = '"+ username +"';";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(q1,null);000
//        return cursor;
//    }
}
