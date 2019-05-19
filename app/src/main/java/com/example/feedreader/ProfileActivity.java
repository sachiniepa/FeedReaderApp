package com.example.feedreader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    EditText username, fullName, phone, dob;
    DatabaseHelper db;
    Button updateBtn, deleteBtn,changePwBtn;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(this);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
//        changePwBtn = (Button) findViewById(R.id.changePwBtn);
        fullName = (EditText)findViewById(R.id.fname);
        username = (EditText)findViewById(R.id.username);
        dob = (EditText)findViewById(R.id.birthday);
        phone = (EditText)findViewById(R.id.mobile);

        Intent intent = getIntent();
        String un = intent.getStringExtra("username");

        ArrayList<String> userDetails = new ArrayList<String>();
        userDetails = db.getUserDetails(un);
        username.setText(un);
        username.setEnabled(false);
        fullName.setText(userDetails.get(1));
        dob.setText(userDetails.get(3));
        phone.setText(userDetails.get(2));

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProfileActivity.this, date , myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname, un, mob, bday;
                fname = fullName.getText().toString();
                un = username.getText().toString();
                mob = phone.getText().toString();
                bday = dob.getText().toString();

                boolean m = db.updateUser(fname,un,mob,bday);
                if(m == true){
                    Toast.makeText(ProfileActivity.this,"Profile Updated.",Toast.LENGTH_SHORT).show();
                    fullName.setText("");
                    dob.setText("");
                    phone.setText("");
                }
                else{
                    Toast.makeText(ProfileActivity.this,"Update failed",Toast.LENGTH_SHORT).show();
                    fullName.setText("");
                    dob.setText("");
                    phone.setText("");
                }

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

//        changePwBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent changePw = new Intent(ProfileActivity.this,PwChange.class);
//                Intent intent = getIntent();
//                String un = intent.getStringExtra("username");
//                changePw.putExtra("username",un);
//                startActivity(changePw);
//            }
//        });



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

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

}
