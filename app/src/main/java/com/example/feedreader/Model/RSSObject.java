package com.example.feedreader.Model;

import java.util.List;

/**
* This is the JSON to java mapper class used to map
* JSON object returned from the RSS feed to a Java
* object .
* This class is referred in the FeedView, PinterestFeedView,
* InstagramFeedView and RedditFeedView classes.
* Contains only relevant attributes and getter and setter methods
* */
public class RSSObject {

    private String status;
    public Feed FeedObject;
    public List<Item> items ;

    public RSSObject(String status, Feed feedObject, List<Item> items) {
        this.status = status;
        FeedObject = feedObject;
        this.items = items;
    }

    // Getter Methods

    public String getStatus() {
        return status;
    }

    public Feed getFeed() {
        return FeedObject;
    }

    public List<Item> getItems() {
        return items;
    }


    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFeed(Feed feedObject) {
        this.FeedObject = feedObject;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
