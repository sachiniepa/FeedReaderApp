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
    public DatabaseHelper(Context context) {
        super(context, "FeedReader.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table users(email text primary key, name text, contactNo text, bday text, password text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
    }

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

    public int validateUser(String un, String pw){
        Log.e("Log2","Login2");
//        String q1 = "SELECT * FROM users WHERE email = '"+ un +"' AND password = '"+ pw +"';";
        String q1 = "SELECT * FROM users WHERE email = '"+ un +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(q1,null);
//        Log.d("count", cursor.getCount() + " post rows");
//        Log.v("cursor", DatabaseUtils.dumpCursorToString(cursor));
        Log.e("Log1","Login1");
        if(cursor.getCount()==1){
            cursor.moveToFirst();
            String pwd = cursor.getString(cursor.getColumnIndex("password"));
            if(pw.equals(pwd))
                return 1;
            else
                return 0;
        }else {
            return -1;
        }
        //cursor.close();
    }

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
