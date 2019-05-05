package com.example.feedreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.feedreader.Interface.ItemClickListner;
import com.example.feedreader.Model.RSSObject;
import com.example.feedreader.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

class InstagramFeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    public TextView txtTitle, txtContent;
    public ImageView imageView;
    private ItemClickListner itemClickListner;

    public InstagramFeedViewHolder(@NonNull View itemView) {
        super(itemView);

        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        imageView = (ImageView)itemView.findViewById(R.id.img);
        //txtPubDate = (TextView) itemView.findViewById(R.id.pubDate);
        txtContent = (TextView) itemView.findViewById(R.id.txtContent);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v, getAdapterPosition(), false);

    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListner.onClick(v, getAdapterPosition(), true);
        return true;
    }
}

public class InstagramFeedAdapter  extends RecyclerView.Adapter<InstagramFeedViewHolder>{
    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;
    private Bitmap mIcon_val;

    public InstagramFeedAdapter(RSSObject rssObject, Context context) {
        this.rssObject = rssObject;
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public InstagramFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.insta_pinterest_card_view, viewGroup,false);
        return new InstagramFeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstagramFeedViewHolder feedViewHolder, int i) {
        feedViewHolder.txtTitle.setText(rssObject.getItems().get(i).getTitle());
//        feedViewHolder.txtPubDate.setText(rssObject.getItems().get(i).getPubDate());
        feedViewHolder.txtContent.setText(rssObject.getItems().get(i).getContent());


        new DownloadImageTask(feedViewHolder.imageView)
                .execute(rssObject.getItems().get(i).getThumbnail());


        feedViewHolder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position, boolean isLongCick) {
                if(!isLongCick){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(rssObject.getItems().get(position).getLink()));
                    mContext.startActivity(browserIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}

