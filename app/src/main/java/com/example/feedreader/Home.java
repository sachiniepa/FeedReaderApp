package com.example.feedreader;

import android.annotation.SuppressLint;
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
import android.widget.ImageButton;

/**
 * The first launched after the user logs into the app.
 * Consist of a home page view with links to launch feed view
 * of the four social media sites
 * */
public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ImageButton twitterFeed , instaFeed, pinterestFeed, redditFeed;
    Toolbar toolbar;
    String username;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);

        twitterFeed = (ImageButton)findViewById(R.id.twitterFeed);
        instaFeed = (ImageButton)findViewById(R.id.instaFeed);
        pinterestFeed = (ImageButton)findViewById(R.id.pinterestFeed);
        redditFeed = (ImageButton)findViewById(R.id.redditFeed);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");


        // Twitter button onClickListener
        twitterFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, FeedView.class).putExtra("username", username));
            }
        });

        // Instagram button onClickListener
        instaFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, InstagramFeedView.class).putExtra("username", username));
            }
        });

        // Pinterest button onClickListener
        pinterestFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, PinterestFeedView.class).putExtra("username", username));
            }
        });

        // Reddit button onClickListener
        redditFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, RedditFeedView.class).putExtra("username", username));
            }
        });




    }

    /**
     * Method executed when items on toolbar is clicked
     * @param item: MenuItem
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch(item.getItemId()){
            case R.id.logout :
                Intent logout = new Intent(Home.this,MainActivity.class);
                startActivity(logout);
                break;
            case R.id.changepwd:
                Intent pw = new Intent(Home.this,PwChange.class);
//                Intent intent1 = getIntent();
//                String username1 = intent1.getStringExtra("username");
                //Passing the username
                Log.e("Username", username);
                pw.putExtra("username", username);
                startActivity(pw);
                break;
            case R.id.profile :
                Intent profile = new Intent(Home.this,ProfileActivity.class);
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

    /**
     * Method executed on creation of the custom toolbar
     * Embeds the menu layout to the toolbar
     * @param menu: Menu
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        return true;
    }

    /**
     * On click method for navigation menu items
     * @param menuItem: MenuItem*/
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.twitterMenu){
            startActivity(new Intent(Home.this, FeedView.class).putExtra("username", username));
        }
        else if(id == R.id.instaMenu){
            startActivity(new Intent(Home.this, InstagramFeedView.class).putExtra("username", username));
        }
        else if (id == R.id.pinterestMenu){
            startActivity(new Intent(Home.this, PinterestFeedView.class).putExtra("username", username));
        }
        else if(id == R.id.redditMenu){
            startActivity(new Intent(Home.this, RedditFeedView.class).putExtra("username", username));
        }
        else if(id == R.id.myProfile){
            Intent profile = new Intent(Home.this,ProfileActivity.class);
            //Passing the username
            profile.putExtra("username", username);
            startActivity(profile);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
