package com.example.feedreader;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    EditText username, fullName, phone, dob;
    DatabaseHelper db;
    Button updateBtn, deleteBtn,changePwBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(this);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
        changePwBtn = (Button) findViewById(R.id.changePwBtn);
        fullName = (EditText)findViewById(R.id.fname);
        username = (EditText)findViewById(R.id.username);
        dob = (EditText)findViewById(R.id.birthday);
        phone = (EditText)findViewById(R.id.mobile);

        Intent intent = getIntent();
        String un = intent.getStringExtra("username");
        username.setText(un);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname, un, mob, bday;
                fname = fullName.getText().toString();
                un = username.getText().toString();
                mob = phone.getText().toString();
                bday = dob.getText().toString();

                boolean m = db.updateUser(fname,un,mob,bday);
                if(m == true)
                    Toast.makeText(ProfileActivity.this,"Profile Updated.",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ProfileActivity.this,"Update failed",Toast.LENGTH_SHORT).show();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = username.getText().toString();
                boolean n = db.deleteUser(un);
                if (n == true){
                    Toast.makeText(ProfileActivity.this,"Account Deleted.",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ProfileActivity.this,"Deletion failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        changePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePw = new Intent(ProfileActivity.this,PwChange.class);
                Intent intent = getIntent();
                String un = intent.getStringExtra("username");
                changePw.putExtra("username",un);
                startActivity(changePw);
            }
        });



//        username = (EditText)findViewById(R.id.username);
//        fullName = (EditText)findViewById(R.id.fname);
//        phone = (EditText)findViewById(R.id.mobile);
//        dob = (EditText)findViewById(R.id.birthday);

        //Getting the username

//        Log.e("test8","test8");
//        ArrayList<String> user = db.getUserDetails(un);
//        Log.e("test9","test9");
//        fullName.setText(user.get(0));
//        phone.setText(user.get(2));
//        dob.setText(user.get(3));
//        username.setText(un);
//        username.setEnabled(false);

//        Cursor cursor = db.getUserDetails(un);
//        Log.v("test1","test1");
//        fullName.setText(cursor.getString(cursor.getColumnIndex("name")));
//        dob.setText(cursor.getString(cursor.getColumnIndex("bday")));
//        phone.setText(cursor.getString(cursor.getColumnIndex("contactNo")));

    }



}
