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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ImageButton twitterFeed , instaFeed, pinterestFeed, redditFeed;
    Toolbar toolbar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        twitterFeed = (ImageButton)findViewById(R.id.twitterFeed);
        instaFeed = (ImageButton)findViewById(R.id.instaFeed);
        pinterestFeed = (ImageButton)findViewById(R.id.pinterestFeed);
        redditFeed = (ImageButton)findViewById(R.id.redditFeed);



        twitterFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, FeedView.class));
            }
        });

        instaFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, InstagramFeedView.class));
            }
        });

        pinterestFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, PinterestFeedView.class));
            }
        });

        redditFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, RedditFeedView.class));
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.twitterMenu){
            startActivity(new Intent(Home.this, FeedView.class));
        }
        else if(id == R.id.instaMenu){
            startActivity(new Intent(Home.this, InstagramFeedView.class));
        }
        else if (id == R.id.pinterestMenu){
            startActivity(new Intent(Home.this, PinterestFeedView.class));
        }
        else if(id == R.id.redditMenu){
            startActivity(new Intent(Home.this, RedditFeedView.class));
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
