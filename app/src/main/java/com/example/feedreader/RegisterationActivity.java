package com.example.feedreader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/*This activity contains a form which will take the required information
* from the user for regestration.*/

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
    final Calendar myCalendar = Calendar.getInstance();

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

        //on click event for the login text view
        //The user is redirected to the login page when the user clicks on the textview.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterationActivity.this, MainActivity.class);
                startActivity(loginIntent);
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            //Setting the day,month and year of the calendar
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        /*This on click event of the dob edittext pops up a calendar when
        * the user clicks on the dob edittext.*/
        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterationActivity.this, date , myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /*This on click event of the register button, method gets the details entered by the user and checks for password and equality
        * and checks whether all the input details are provided.
        * If the username is not taken the user is registered to the app.*/
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("test1","msg");

                //getting the values entered by the user
                String un = username.getText().toString();
                String pw1 = password.getText().toString();
                String pw2 = confirmpassword.getText().toString();
                String fullName = name.getText().toString();
                String mobile = phone.getText().toString();
                String birthday = dob.getText().toString();


                //checks whether all the inputs are provided
                if((un.equals("")) || (pw1.equals("")) || (pw2.equals("")) || (fullName.equals("")) || (mobile.equals("")) || (birthday.equals(""))){
                    Toast.makeText(getApplicationContext(),"Please fill all the details",Toast.LENGTH_SHORT).show();
                    return;
                }

                //checking for password equality
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
                            //warns the user when the registration is failed.
                            Toast.makeText(getApplicationContext(),"Registration Failed.",Toast.LENGTH_SHORT).show();
                            name.setText("");
                            dob.setText("");
                            phone.setText("");
                            username.setText("");
                            password.setText("");
                            confirmpassword.setText("");
                        }
                    }else {
                        //gives the user an error message when an already existing user is trying to register
                        Toast.makeText(getApplicationContext(),"You are already registered.",Toast.LENGTH_SHORT).show();
                        name.setText("");
                        dob.setText("");
                        phone.setText("");
                        username.setText("");
                        password.setText("");
                        confirmpassword.setText("");
                    }
                }else {
                    //gives the user an error message when the passwords don't match.
                    Toast.makeText(getApplicationContext(),"Passwords don't match.",Toast.LENGTH_SHORT).show();
                    password.setText("");
                    confirmpassword.setText("");
                    Animation shake = AnimationUtils.loadAnimation(RegisterationActivity.this, R.anim.shake);
                    password.startAnimation(shake);
                    confirmpassword.startAnimation(shake);
                    return;
                }



            }
        });
    }

    //setting the date on the dob edittext according to the date selected.
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }
}
