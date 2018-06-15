package com.gamal.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.CubeGrid;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>> {
    public static int ImgRes[]={R.drawable.a,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.b};
    public static final String API_URL="https://content.guardianapis.com/search?q=(football%20OR%20sausages%20OR%20USA)&format=json&api-key=test&page-size=50";
    private GridView gridView;
    private ProgressBar loadingAnimation;
    private TextView dataEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingAnimation=(ProgressBar) findViewById(R.id.spin_kit);
        CubeGrid cubeGrid=new CubeGrid();
        loadingAnimation.setIndeterminateDrawable(cubeGrid);
        dataEmpty=(TextView) findViewById(R.id.emptyData);
        LoaderManager loaderManager=getLoaderManager();
        loaderManager.initLoader(0, null, this);
        gridView=(GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem list= (NewsItem) parent.getAdapter().getItem(position);

                pictureClick(view.findViewById(R.id.image),list.getSection(),list.getUrl());
            }
        });
    }

    public void pictureClick(View v,String section,String url){
        // Create an object containing information about our scene transition animation
        CustomImageView pictureView = (CustomImageView) v;
        int picture = pictureView.getImageResource();
        CharSequence title = v.getContentDescription();

        // Pass information to Detail Activity in order to show the chosen image and its details
        Intent intent = new Intent(this, Detail.class);
        intent.putExtra(Detail.EXTRA_PICTURE, picture);
        intent.putExtra(Detail.EXTRA_TITLE, title);
        intent.putExtra(Detail.EXTRA_SECTION, section);
        intent.putExtra(Detail.URL,url);
        startActivity(intent);
    }

    public void updateUI(List<NewsItem> data){
        CustomGridViewAdapter adapter=new CustomGridViewAdapter(getApplicationContext(),0,data);
        gridView.setAdapter(adapter);
    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        loadingAnimation.setVisibility(View.VISIBLE);
        Thread thread=new Thread();
        try {
            thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected) {
            loadingAnimation.setVisibility(View.GONE);
            dataEmpty.setText(R.string.noInternet);
            dataEmpty.setVisibility(View.VISIBLE);
            return null;
        }
        return new NewsLoader(this,API_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        loadingAnimation.setVisibility(View.GONE);
        if(data.size()==0){
            dataEmpty.setText(R.string.emptyData);
            dataEmpty.setVisibility(View.VISIBLE);
        }else {
            updateUI(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {

    }



}
