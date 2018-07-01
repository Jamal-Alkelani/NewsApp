package com.gamal.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.CubeGrid;

import java.util.List;

/**
 * @author gamal
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>>,
        SharedPreferences.OnSharedPreferenceChangeListener {
    public static int[] ImgRes = {R.drawable.a, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.b};
    public static final String API_URL = "https://content.guardianapis.com/search";
    private ListView gridView;
    private ProgressBar loadingAnimation;
    private TextView dataEmpty;
    private CustomGridViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref=getApplicationContext().getSharedPreferences("MYPREF",0);
        int pageSize=pref.getInt("pageSize",-1);
        if(pageSize==-1){
            pref.edit().putInt("pageSize",50).apply();
        }

        loadingAnimation =  findViewById(R.id.spin_kit);
        CubeGrid cubeGrid = new CubeGrid();
        loadingAnimation.setIndeterminateDrawable(cubeGrid);
        dataEmpty =  findViewById(R.id.emptyData);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);

        gridView = findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem list = (NewsItem) parent.getAdapter().getItem(position);

                redirect(list.getUrl());

            }
        });

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);


    }

    public void redirect(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }


    public void updateUI(List<NewsItem> data) {
         adapter = new CustomGridViewAdapter(getApplicationContext(), 0, data);
        gridView.setAdapter(adapter);
    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        Uri base=Uri.parse(API_URL);
        Uri.Builder builder=base.buildUpon();
        builder.appendQueryParameter("q","(football%20OR%20sausages%20OR%20USA)");
        builder.appendQueryParameter("format","json");
        builder.appendQueryParameter("api-key","7e099af7-4aa1-4686-b4db-e00e6c832c01");
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(this);
        String x=pref.getString("pageSize","50");
        builder.appendQueryParameter("page-size",x);
        Log.e("xx",builder.toString());
        loadingAnimation.setVisibility(View.VISIBLE);
        Thread thread = new Thread();
        try {
            thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            loadingAnimation.setVisibility(View.GONE);
            dataEmpty.setText(R.string.noInternet);
            dataEmpty.setVisibility(View.VISIBLE);
            return null;
        }
        return new NewsLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        loadingAnimation.setVisibility(View.GONE);
        if (data.size() == 0) {
            dataEmpty.setText(R.string.emptyData);
            dataEmpty.setVisibility(View.VISIBLE);
        } else {
            updateUI(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            adapter.clear();

            loadingAnimation.setVisibility(View.VISIBLE);

            // Restart the loader to requery the USGS as the query settings have been updated
            getLoaderManager().restartLoader(1, null, this);

    }
}
