package com.example.feedreader.Model;

import java.util.List;
/**
 * JSON to Java mapper class of an item in
 * the RSS feed.
 * */
public class Item {
    public String title;
    public String pubDate;
    public String link;
    public String guid;
    public String author;
    public String thumbnail;
    public String description;
    public String content ;
    public Object enclosure;
    public List<String> categories;

    public Item(String title, String pubDate, String link, String guid, String author, String thumbnail, String description, String content, Object enclosure, List<String> categories) {
        this.title = title;
        this.pubDate = pubDate;
        this.link = link;
        this.guid = guid;
        this.author = author;
        this.thumbnail = thumbnail;
        this.description = description;
        this.content = content;
        this.enclosure = enclosure;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getLink() {
        return link;
    }

    public String getGuid() {
        return guid;
    }

    public String getAuthor() {
        return author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public Object getEnclosure() {
        return enclosure;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEnclosure(Object enclosure) {
        this.enclosure = enclosure;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

}
