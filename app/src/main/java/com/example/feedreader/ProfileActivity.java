package com.example.feedreader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText username, fullName, phone, dob;
    DatabaseHelper db;
    Button updateBtn, deleteBtn,changePwBtn;
    Toolbar toolbar;
    String un;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //setting the custom toolbar as the action bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.ic_twitter);
        //toolbar.setTitle("Feed Reader");
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);

        db = new DatabaseHelper(this);
        updateBtn = (Button)findViewById(R.id.updateBtn);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);
//        changePwBtn = (Button) findViewById(R.id.changePwBtn);
        fullName = (EditText)findViewById(R.id.fname);
        username = (EditText)findViewById(R.id.username);
        dob = (EditText)findViewById(R.id.birthday);
        phone = (EditText)findViewById(R.id.mobile);

        //getting the value from the intent and setting it as the username
        Intent intent = getIntent();
        un = intent.getStringExtra("username");
        username.setText(un);
        //making the username uneditable
        username.setEnabled(false);

        //onclick event for the update profile button
        //gets the values entered by the user and updates the profile.
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname, un, mob, bday;

                //getting the user entered values
                fname = fullName.getText().toString();
                un = username.getText().toString();
                mob = phone.getText().toString();
                bday = dob.getText().toString();

                //calling the updateUser method in the DatabaseHalper class to update the database.
                boolean m = db.updateUser(fname,un,mob,bday);

                //giving the user a message after updation.
                if(m == true)
                    Toast.makeText(ProfileActivity.this,"Profile Updated.",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ProfileActivity.this,"Update failed",Toast.LENGTH_SHORT).show();
            }
        });

        //Setting the day,month and year of the calendar
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

        /*This on click event of the dob edittext pops up a calendar when
         * the user clicks on the dob edittext.*/
        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ProfileActivity.this, date , myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //onclick event for the delete button.
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the username
                String un = username.getText().toString();
                //calling the deleteUser method on the DatabaseHelper class to delete the record form the database.
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

    //setting the date on the dob edittext according to the date selected.
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    //setting the actions for navigation pane
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch(item.getItemId()){
            case R.id.logout :
                //redirects the user to the login page when the user clicks on logout
                Intent logout = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(logout);
                break;
            case R.id.changepwd:
                //redirects the user to the password change page when the user clicks on change password
                Intent pw = new Intent(ProfileActivity.this,PwChange.class);
//                Intent intent1 = getIntent();
//                String username1 = intent1.getStringExtra("username");
                //Passing the username
                Log.e("Username", un);
                pw.putExtra("username", un);
                startActivity(pw);
                break;
            case R.id.profile :
                //redirects the user to the profile page when the user clicks on profile icon
                Intent profile = new Intent(ProfileActivity.this,ProfileActivity.class);
                //Getting the username
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                //Passing the username
                profile.putExtra("username", username);
                startActivity(profile);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.twitterMenu){
            startActivity(new Intent(ProfileActivity.this, FeedView.class).putExtra("username", un));
        }
        else if(id == R.id.instaMenu){
            startActivity(new Intent(ProfileActivity.this, InstagramFeedView.class).putExtra("username", un));
        }
        else if (id == R.id.pinterestMenu){
            startActivity(new Intent(ProfileActivity.this, PinterestFeedView.class).putExtra("username", un));
        }
        else if(id == R.id.redditMenu){
            startActivity(new Intent(ProfileActivity.this, RedditFeedView.class).putExtra("username", un));
        }
        else if(id == R.id.myProfile){
            Intent profile = new Intent(ProfileActivity.this,ProfileActivity.class);
            //Getting the username
            Intent intent = getIntent();
            String username = intent.getStringExtra("username");
            Log.e("Username", username);
            //Passing the username
            profile.putExtra("username", username);
            startActivity(profile);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
