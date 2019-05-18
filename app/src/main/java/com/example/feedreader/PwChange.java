package com.example.feedreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PwChange extends AppCompatActivity {

    Button reset, update;
    EditText password, confirmPassword;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_change);

        db = new DatabaseHelper(this);
        reset = (Button) findViewById(R.id.resetBtn);
        update = (Button) findViewById(R.id.chngPwBtn);
        password = (EditText) findViewById(R.id.pw);
        confirmPassword = (EditText) findViewById(R.id.confirmpassword);

        Intent intent = getIntent();
        final String uname = intent.getStringExtra("username");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw1, pw2;
                pw1 = password.getText().toString();
                pw2 = confirmPassword.getText().toString();
                boolean m = db.changePassword(pw1,pw2,uname);
                if(m == true)
                    Toast.makeText(PwChange.this,"Password Updated.",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(PwChange.this,"Updation failed .",Toast.LENGTH_SHORT).show();

            }
        });
    }


}
