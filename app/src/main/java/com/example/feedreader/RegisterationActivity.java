package com.example.feedreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterationActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText name;
    EditText phone;
    EditText dob;
    EditText confirmpassword;
    Button register;
    TextView login;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        db = new DatabaseHelper(this);
        name = (EditText) findViewById(R.id.fullName);
        dob = (EditText) findViewById(R.id.dob);
        phone = (EditText) findViewById(R.id.mobile);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        register = (Button) findViewById(R.id.registerNow);
        login = (TextView) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterationActivity.this, MainActivity.class);
                startActivity(loginIntent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test1","msg");

                String un = username.getText().toString();
                String pw1 = password.getText().toString();
                String pw2 = confirmpassword.getText().toString();
                String fullName = name.getText().toString();
                String mobile = phone.getText().toString();
                String birthday = dob.getText().toString();


                if((un.equals("")) || (pw1.equals("")) || (pw2.equals("")) || (fullName.equals("")) || (mobile.equals("")) || (birthday.equals(""))){
                    Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pw1.equals(pw2)){
                    boolean r = db.checkUser(un);
                    if(r==true){
                        boolean n = db.insertUser(un,fullName,mobile,birthday,pw1);
                        if(n == true){
                            Toast.makeText(getApplicationContext(),"Registration Successful.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterationActivity.this,First.class);
                            startActivity(intent);
//                            SmsManager smsManager = SmsManager.getDefault();
//                            smsManager.sendTextMessage("+94766172382", null, "sms message", null, null);
                        }else {
                            Toast.makeText(getApplicationContext(),"Registration Failed.",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(),"You are already registered.",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Passwords don't match.",Toast.LENGTH_SHORT).show();
                    return;
                }



            }
        });
    }
}
