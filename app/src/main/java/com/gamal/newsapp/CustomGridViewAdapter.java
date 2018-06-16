package com.gamal.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class CustomGridViewAdapter extends ArrayAdapter<NewsItem> {
    private Context context;
    public CustomGridViewAdapter(@NonNull Context context, int resource, @NonNull List<NewsItem> objects) {
        super(context, resource, objects);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        if(view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.gridview_item,parent,false);
        }


        view.findViewById(R.id.openOutside).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirect(getItem(position).getUrl());
            }
        });
        ImageView iv=(ImageView) view.findViewById(R.id.image);
        TextView tv=(TextView) view.findViewById(R.id.caption);
        TextView section=(TextView) view.findViewById(R.id.sectionName);
        TextView DOP=(TextView) view.findViewById(R.id.DateOfPublish);

        iv.setImageResource(MainActivity.ImgRes[new Random().nextInt(MainActivity.ImgRes.length)]);
        iv.setContentDescription(getItem(position).getTitle());
        tv.setText(getItem(position).getTitle());
        section.setText(getItem(position).getSection());
        DOP.setText(getItem(position).getDOP());
        return view;
    }

    public void redirect(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
