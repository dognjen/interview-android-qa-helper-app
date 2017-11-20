package com.outfit.interview.detector.qahelper.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.outfit.interview.detector.qahelper.R;

import java.util.List;

/**
 * Created by Dejan on 19/11/2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<Bitmap> bitmapList;

    public ImageAdapter(Context context, List<Bitmap> bitmapList) {
        this.context = context;
        this.bitmapList = bitmapList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Bitmap bitmap = bitmapList.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.activity_gallery, null);
        }

        //ImageView imageView = (ImageView) convertView.findViewById(R.id.imageview_cover_art);

        return convertView;
    }


    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }




}
