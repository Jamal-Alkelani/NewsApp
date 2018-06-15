package com.gamal.newsapp;

import android.content.Context;
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
    public CustomGridViewAdapter(@NonNull Context context, int resource, @NonNull List<NewsItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        if(view==null){
            view= LayoutInflater.from(getContext()).inflate(R.layout.gridview_item,parent,false);
        }

        view.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 600));
        ImageView iv=(ImageView) view.findViewById(R.id.image);
        TextView tv=(TextView) view.findViewById(R.id.caption);

        iv.setImageResource(MainActivity.ImgRes[new Random().nextInt(MainActivity.ImgRes.length)]);
        iv.setContentDescription(getItem(position).getTitle());
        tv.setText(getItem(position).getTitle().toString().substring(0,5));
        return view;
    }
}
