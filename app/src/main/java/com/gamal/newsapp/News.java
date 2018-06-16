package com.gamal.newsapp;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class News {
    public List fetchData(String requestUrl) {
        URL url = createURL(requestUrl);
        String jsonResponse = "";

        jsonResponse = makeHTTPRequest(url);

        List<NewsItem> items = extractFromJSON(jsonResponse);
        return items;

    }

    private List<NewsItem> extractFromJSON(String jsonResponse) {

        List<NewsItem> items = new ArrayList<NewsItem>();
        try {
            JSONObject obj = new JSONObject(jsonResponse);
            JSONArray arr = obj.getJSONObject("response").getJSONArray("results");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject resObj = (JSONObject) arr.get(i);
                String title = resObj.getString("webTitle");
                String url = resObj.getString("webUrl");
                String section = resObj.getString("sectionName");
                String DOP = resObj.getString("webPublicationDate");
                String author;
                try {
                    author = resObj.getString("author");
                } catch (Exception e) {
                    author = "";
                }
                NewsItem newsItem = new NewsItem(title, section, url, DOP);
                if (TextUtils.isEmpty(author) || author == null) {
                    newsItem.setAuthor("Author Not Found");
                } else {
                    newsItem.setAuthor(author);
                }
                items.add(newsItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    private String makeHTTPRequest(URL url) {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = extractJSON(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    private String extractJSON(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public URL createURL(String url) {
        URL urlToCreated = null;
        if (TextUtils.isEmpty(url)) {
            return urlToCreated;
        } else {
            try {
                urlToCreated = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return urlToCreated;
    }
}
