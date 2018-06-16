package com.gamal.newsapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Detail extends AppCompatActivity {
    static final String EXTRA_PICTURE = Detail.class.getPackage() + ".extra.PICTURE";
    static final String EXTRA_TITLE = Detail.class.getPackage() + ".extra.TITLE";
    static final String EXTRA_SECTION = Detail.class.getPackage() + ".extra.SECTION";
    static final String URL = Detail.class.getPackage() + ".extra.URL";
    private String url;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        int picture = getIntent().getExtras().getInt(EXTRA_PICTURE);
        CharSequence title = getIntent().getExtras().getCharSequence(EXTRA_TITLE);
        CharSequence section = getIntent().getExtras().getCharSequence(EXTRA_SECTION);
        url = getIntent().getExtras().getString(URL);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);

        ImageView pictureView = (ImageView) findViewById(R.id.picture);
        pictureView.setImageResource(picture);
        pictureView.setContentDescription(title);
        TextView tv = findViewById(R.id.section);
        tv.setText("SECTION: " + section);
    }

    public void redirect(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }
}
