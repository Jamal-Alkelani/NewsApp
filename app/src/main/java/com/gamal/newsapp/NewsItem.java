package com.gamal.newsapp;

public class NewsItem {
    private String title;
    private String section;
    private String DOP;
    private String url;

    public NewsItem(String title, String section, String url,String DOP) {
        this.title = title;
        this.section = section;
        this.url = url;
        this.DOP=DOP;
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
