package com.example.feedreader.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.feedreader.Interface.ItemClickListner;
import com.example.feedreader.Model.RSSObject;
import com.example.feedreader.R;

/**
 * This class defines the functionality of a single card
 * element in Twitter and Reddit UIs
 * */
class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView txtTitle, txtPubDate, txtContent;
    private ItemClickListner itemClickListner;

    public FeedViewHolder(@NonNull View itemView) {
        super(itemView);

        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtPubDate = (TextView) itemView.findViewById(R.id.pubDate);
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

/**
 * Adapter class for Twitter and Reddit feed views
 * */
public class FeedAdapter extends RecyclerView.Adapter<FeedViewHolder>{

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater inflater;

    public FeedAdapter(RSSObject rssObject, Context context) {
        this.rssObject = rssObject;
        this.mContext = context;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = inflater.inflate(R.layout.row, viewGroup,false);
        return new FeedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder feedViewHolder, int i) {
        feedViewHolder.txtTitle.setText(rssObject.getItems().get(i).getTitle());
        feedViewHolder.txtPubDate.setText(rssObject.getItems().get(i).getPubDate());
        feedViewHolder.txtContent.setText(rssObject.getItems().get(i).getContent());

        /**
         * Onclick method that enables users to
         * navigate to relevant link
         **/
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
}
