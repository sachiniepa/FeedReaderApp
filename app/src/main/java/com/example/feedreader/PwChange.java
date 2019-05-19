package com.example.feedreader;

import android.content.Intent;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PwChange extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button reset, update;
    EditText password, confirmPassword;
    DatabaseHelper db;
    String uname;

    Toolbar toolbar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_change);

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
        reset = (Button) findViewById(R.id.resetBtn);
        update = (Button) findViewById(R.id.chngPwBtn);
        password = (EditText) findViewById(R.id.pw);
        confirmPassword = (EditText) findViewById(R.id.cnfPw);

        Intent intent = getIntent();
        uname = intent.getStringExtra("username");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw1, pw2;
                pw1 = password.getText().toString();
                pw2 = confirmPassword.getText().toString();
                if(pw1.equals(pw2)){
                    boolean m = db.changePassword(pw1,pw2,uname);
                    if(m == true){
                        Toast.makeText(PwChange.this,"Password Updated.",Toast.LENGTH_SHORT).show();
                        password.setText("");
                        confirmPassword.setText("");
                    }
                    else{
                        Toast.makeText(PwChange.this,"Updation failed.",Toast.LENGTH_SHORT).show();
                        password.setText("");
                        confirmPassword.setText("");
                        Animation shake = AnimationUtils.loadAnimation(PwChange.this, R.anim.shake);
                        password.startAnimation(shake);
                        confirmPassword.startAnimation(shake);
                    }
                }else {
                    Toast.makeText(PwChange.this,"Passwords don't match.",Toast.LENGTH_SHORT).show();
                    password.setText("");
                    confirmPassword.setText("");
                    Animation shake = AnimationUtils.loadAnimation(PwChange.this, R.anim.shake);
                    password.startAnimation(shake);
                    confirmPassword.startAnimation(shake);
                }


            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText("");
                confirmPassword.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch(item.getItemId()){
            case R.id.logout :
                Intent logout = new Intent(PwChange.this,MainActivity.class);
                startActivity(logout);
                break;
            case R.id.changepwd:
                Intent pw = new Intent(PwChange.this,PwChange.class);
//                Intent intent1 = getIntent();
//                String username1 = intent1.getStringExtra("username");
                //Passing the username
                Log.e("Username", uname);
                pw.putExtra("username", uname);
                startActivity(pw);
                break;
            case R.id.profile :
                Intent profile = new Intent(PwChange.this,ProfileActivity.class);
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
            startActivity(new Intent(PwChange.this, FeedView.class).putExtra("username", uname));
        }
        else if(id == R.id.instaMenu){
            startActivity(new Intent(PwChange.this, InstagramFeedView.class).putExtra("username", uname));
        }
        else if (id == R.id.pinterestMenu){
            startActivity(new Intent(PwChange.this, PinterestFeedView.class).putExtra("username", uname));
        }
        else if(id == R.id.redditMenu){
            startActivity(new Intent(PwChange.this, RedditFeedView.class).putExtra("username", uname));
        }
        else if(id == R.id.myProfile){
            Intent profile = new Intent(PwChange.this,ProfileActivity.class);
            //Getting the username
//            Intent intent = getIntent();
//            String username = intent.getStringExtra("username");
            Log.e("Username", uname);
            //Passing the username
            profile.putExtra("username", uname);
            startActivity(profile);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
