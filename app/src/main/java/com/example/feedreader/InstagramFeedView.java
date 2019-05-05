package com.example.feedreader;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.feedreader.Adapter.InstagramFeedAdapter;
import com.example.feedreader.Common.HTTPDataHandler;
import com.example.feedreader.Model.RSSObject;
import com.google.gson.Gson;

public class InstagramFeedView extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    RSSObject rssObject;

    private final String RSSlink = "https://rss.app/feeds/3naluiOa5aTX9g5E.xml";
    private final String RSS_to_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url=";
    private final String API_Key = "zzbi1izgxvbkjtdediygmv5kpllecn7cbts2xkkp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_instagram);
        toolbar.setTitle("   Instagram");
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        loadRSS();

    }

    private void loadRSS() {
        AsyncTask<String, String, String> loadRSSAsync = new AsyncTask<String, String, String>(){
            ProgressDialog progressDialog = new ProgressDialog(InstagramFeedView.this);

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
                InstagramFeedAdapter InstagramFeedAdapter = new InstagramFeedAdapter(rssObject, getBaseContext());
                recyclerView.setAdapter(InstagramFeedAdapter);
                InstagramFeedAdapter.notifyDataSetChanged();
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
        if(item.getItemId()== R.id.menu_refresh){
            loadRSS();
        }
        return true;
    }
}
