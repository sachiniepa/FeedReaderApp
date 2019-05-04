package com.example.feedreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton twitterFeed ;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        twitterFeed = (ImageButton)findViewById(R.id.twitterFeed);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setLogo(R.drawable.ic_twitter);
        toolbar.setTitle("Feed Reader");
        setSupportActionBar(toolbar);

        twitterFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeedView.class));
            }
        });
    }
}
