package com.jack.appnews.bean;

import java.util.List;

public class NewsBean {
    private String id;
    private int type;
    private String publishTime;
    private String title;
    private String author;
    private String cover;
    private List<String> covers;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getTime() {
        return publishTime;
    }
    public void setTime(String time) {
        this.publishTime = time;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCover() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<String> getCovers() {
        return covers;
    }

    public void setCovers(List<String> covers) {
        this.covers = covers;
    }


}