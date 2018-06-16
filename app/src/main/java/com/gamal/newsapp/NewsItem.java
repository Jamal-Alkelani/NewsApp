package com.gamal.newsapp;

public class NewsItem {
    private String title;
    private String section;
    private String DOP;
    private String url;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public NewsItem(String title, String section, String url, String DOP) {
        this.title = title;
        this.section = section;
        this.url = url;
        this.DOP = DOP;
    }

    public String getDOP() {
        return DOP;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getUrl() {
        return url;
    }
}
