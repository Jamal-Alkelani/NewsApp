package com.gamal.newsapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


public class CustomImageView extends android.support.v7.widget.AppCompatImageView {
    private int ImageRes;
    public CustomImageView(Context context) {
        this(context, null, 0);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            final String namespace = "http://schemas.android.com/apk/res/android";
            final String attribute = "src";
            ImageRes = attrs.getAttributeResourceValue(namespace, attribute, 0);
        }
    }
    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        ImageRes=resId;
    }

    public int getImageResource(){
        return ImageRes;
    }
}
