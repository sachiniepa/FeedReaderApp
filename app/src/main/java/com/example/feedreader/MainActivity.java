package com.example.feedreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
    TextView register;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this,RegisterationActivity.class);
                startActivity(registerIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String un = username.getText().toString().trim();
                String pw = password.getText().toString().trim();
                boolean m = db.validateUser(un,pw);
                if(m == true){
                    Intent login = new Intent(MainActivity.this,Home.class);
                    login.putExtra("username",un);
                    startActivity(login);
                    Toast.makeText(MainActivity.this,"Successfully Logged in",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"You don't have an account. Please sign up.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
