package com.example.feedreader;

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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText username, fullName, phone, dob;
    DatabaseHelper db;
    Button updateBtn, deleteBtn,changePwBtn;
    Toolbar toolbar;
    String un;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        Intent intent = getIntent();
        un = intent.getStringExtra("username");
        username.setText(un);
        username.setEnabled(false);

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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch(item.getItemId()){
            case R.id.logout :
                Intent logout = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(logout);
                break;
            case R.id.changepwd:
                Intent pw = new Intent(ProfileActivity.this,PwChange.class);
//                Intent intent1 = getIntent();
//                String username1 = intent1.getStringExtra("username");
                //Passing the username
                Log.e("Username", un);
                pw.putExtra("username", un);
                startActivity(pw);
                break;
            case R.id.profile :
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
