package com.example.feedreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class First extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feed Reader");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.logout :
                Intent logout = new Intent(First.this,MainActivity.class);
                startActivity(logout);
                break;
            case R.id.profile :
                Intent profile = new Intent(First.this,ProfileActivity.class);
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
}
