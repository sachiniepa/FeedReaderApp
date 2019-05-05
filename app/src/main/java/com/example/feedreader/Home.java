package com.example.feedreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {

    ImageButton twitterFeed , instaFeed, pinterestFeed, redditFeed;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        twitterFeed = (ImageButton)findViewById(R.id.twitterFeed);
        instaFeed = (ImageButton)findViewById(R.id.instaFeed);
        pinterestFeed = (ImageButton)findViewById(R.id.pinterestFeed);
        redditFeed = (ImageButton)findViewById(R.id.redditFeed);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.ic_twitter);
        //toolbar.setTitle("Feed Reader");
        setSupportActionBar(toolbar);

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
}
