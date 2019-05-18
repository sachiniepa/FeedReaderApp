package com.example.feedreader.Model;

import java.util.List;

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
