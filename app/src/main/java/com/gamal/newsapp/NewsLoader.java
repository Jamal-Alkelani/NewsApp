package com.gamal.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {
    String API_URL;

    public NewsLoader(Context context, String url) {
        super(context);
        this.API_URL = url;
    }

    @Override
    public List<NewsItem> loadInBackground() {
        News news = new News();
        List data = news.fetchData(API_URL);
        return data;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
