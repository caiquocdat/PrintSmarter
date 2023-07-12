package com.example.printmaster.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageSheetAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imagePaths;
    private int numRows;
    private int numColumns;

    public ImageSheetAdapter(Context context, ArrayList<String> imagePaths, int numRows, int numColumns) {
        this.context = context;
        this.imagePaths = imagePaths;
        this.numRows = numRows;
        this.numColumns = numColumns;
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return imagePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            imageView = (ImageView) convertView;
        }
        if (numColumns==0){
            numColumns=1;
        }
        if (numRows==0){
            numRows=1;
        }

        // Tính toán kích thước của ảnh dựa trên số hàng và số cột
        int screenWidth = getScreenWidth();
        int itemWidth = screenWidth / numColumns;
        int itemHeight = screenWidth / numRows;
        Log.d("QuocDat_size", "total: "+screenWidth+", width: "+itemWidth+", itemHeight: "+itemHeight);

        imageView.getLayoutParams().width = itemWidth;
        imageView.getLayoutParams().height = itemHeight;

        Glide.with(context)
                .load(imagePaths.get(position))
                .into(imageView);

        return imageView;
    }
    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
