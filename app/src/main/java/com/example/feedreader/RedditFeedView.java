package com.example.feedreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.feedreader.Adapter.FeedAdapter;
import com.example.feedreader.Common.HTTPDataHandler;
import com.example.feedreader.Model.RSSObject;
import com.google.gson.Gson;

public class RedditFeedView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    RecyclerView recyclerView;
    RSSObject rssObject;
    String username;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;

    private final String RSSlink = "https://rss.app/feeds/BPfv7kVnPUMSFCsp.xml";
    private final String RSS_to_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url=";
    private final String API_Key = "zzbi1izgxvbkjtdediygmv5kpllecn7cbts2xkkp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_view);


        // Getting the username
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_reddit);
        toolbar.setTitle("   Reddit");
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        loadRSS();

    }

    private void loadRSS() {
        AsyncTask<String, String, String> loadRSSAsync = new AsyncTask<String, String, String>(){
            ProgressDialog progressDialog = new ProgressDialog(RedditFeedView.this);

            @Override
            protected void onPreExecute() {
                progressDialog.setMessage("Loading Feeds......");
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HTTPDataHandler httpDataHandler = new HTTPDataHandler();
                result = httpDataHandler.GetHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
                rssObject = new Gson().fromJson(s, RSSObject.class);
                FeedAdapter feedAdapter = new FeedAdapter(rssObject, getBaseContext());
                recyclerView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
            }
        };

        StringBuilder url_get_data = new StringBuilder(RSS_to_JSON_API);
        url_get_data.append(RSSlink+"&api_key="+API_Key);
        loadRSSAsync.execute(url_get_data.toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        if(item.getItemId()== R.id.menu_refresh){
            loadRSS();
        }
        switch(item.getItemId()){
            case R.id.logout :
                Intent logout = new Intent(RedditFeedView.this,MainActivity.class);
                startActivity(logout);
                break;
            case R.id.changepwd:
                Intent pw = new Intent(RedditFeedView.this,PwChange.class);
//                Intent intent1 = getIntent();
//                String username1 = intent1.getStringExtra("username");
                //Passing the username
                pw.putExtra("username", username);
                startActivity(pw);
                break;
            case R.id.profile :
                Intent profile = new Intent(RedditFeedView.this,ProfileActivity.class);
                //Getting the username
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                //Passing the username
                profile.putExtra("username", username);
                startActivity(profile);
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.twitterMenu){
            startActivity(new Intent(RedditFeedView.this, FeedView.class).putExtra("username", username));
        }
        else if(id == R.id.instaMenu){
            startActivity(new Intent(RedditFeedView.this, InstagramFeedView.class).putExtra("username", username));
        }
        else if (id == R.id.pinterestMenu){
            startActivity(new Intent(RedditFeedView.this, PinterestFeedView.class).putExtra("username", username));
        }
        else if(id == R.id.redditMenu){
            startActivity(new Intent(RedditFeedView.this, RedditFeedView.class).putExtra("username", username));
        }
        else if(id == R.id.myProfile){
            Intent profile = new Intent(RedditFeedView.this,ProfileActivity.class);
            //Passing the username
            profile.putExtra("username", username);
            startActivity(profile);
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
