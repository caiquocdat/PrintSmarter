package com.example.printmaster.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.printmaster.R;

import org.json.JSONArray;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.printmaster.R;
import com.example.printmaster.data.OnItemClickSelectListener;

import org.json.JSONArray;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridSelectViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Bitmap> bitmapList;
    private OnItemClickSelectListener listener;
    private int selectedItem = 0;

    public void setOnItemClickSelectListener(OnItemClickSelectListener listener) {
        this.listener = listener;
    }

    public GridSelectViewAdapter(Context context, ArrayList<Bitmap> bitmapList) {
        this.context = context;
        this.bitmapList = bitmapList;
    }

    public void setSelectedItem(int position) {
        this.selectedItem = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bitmapList.size();
    }

    @Override
    public Object getItem(int position) {
        return bitmapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Drawable unSelectItem = context.getResources().getDrawable(R.drawable.lable_item_un_select);
        Drawable selectItem = context.getResources().getDrawable(R.drawable.lable_item);
        Bitmap bitmap_click = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_click);
        Bitmap bitmap_un_click = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_un_click);
        Bitmap bitmap_click_2_row = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_2_row_click);
        Bitmap bitmap_un_click_2_row = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_2_row);
        Bitmap bitmap_click_2_collum = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_phototo_layout_2_collum_click);
        Bitmap bitmap_un_click_2_collum = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_2_collum);
        Bitmap bitmap_click_3_row = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_3_row_click);
        Bitmap bitmap_un_click_3_row = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_3_row);
        Bitmap bitmap_click_2_2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_2_2_click);
        Bitmap bitmap_un_click_2_2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_2_2);
        Bitmap bitmap_click_2_3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_2_3_click);
        Bitmap bitmap_un_click_2_3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_2_3);
        Bitmap bitmap_click_3_3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_3_3_click);
        Bitmap bitmap_un_click_3_3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_select_photo_layout_3_3);
        if (convertView == null) {
            // Inflate the item layout
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_sheet_option_grid, parent, false);

            // Create a ViewHolder to hold references to the views
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.lableImageView = convertView.findViewById(R.id.img_1);
            holder.linear_1 = convertView.findViewById(R.id.linear_1);

            convertView.setTag(holder);
        } else {
            // View is being recycled, retrieve the ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        // Check if this is the selected item
        if (position == selectedItem) {
            holder.linear_1.setBackground(selectItem);
            holder.lableImageView.setImageBitmap(bitmap_click);
            Bitmap bitmapFromImageView_2 = ((BitmapDrawable) holder.imageView.getDrawable()).getBitmap();
//            Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
//            if (position==0) {
//                holder.imageView.setImageBitmap(bitmap_click_2_row);
//            }else if(position==1){
//                holder.imageView.setImageBitmap(bitmap_click_2_collum);
//            }else if(position==2){
//                holder.imageView.setImageBitmap(bitmap_click_3_row);
//            }else if(position==3){
//                holder.imageView.setImageBitmap(bitmap_click_2_2);
//            }else if(position==4){
//                holder.imageView.setImageBitmap(bitmap_click_2_3);
//            }else if(position==5){
//                holder.imageView.setImageBitmap(bitmap_click_3_3);
//            }
        } else {
            holder.linear_1.setBackground(unSelectItem);
            holder.lableImageView.setImageBitmap(bitmap_un_click);
//            if (position==0) {
//                holder.imageView.setImageBitmap(bitmap_un_click_2_row);
//            }else if(position==1){
//                holder.imageView.setImageBitmap(bitmap_un_click_2_collum);
//            }else if(position==2){
//                holder.imageView.setImageBitmap(bitmap_un_click_3_row);
//            }else if(position==3){
//                holder.imageView.setImageBitmap(bitmap_un_click_2_2);
//            }else if(position==4){
//                holder.imageView.setImageBitmap(bitmap_un_click_2_3);
//            }else if(position==5){
//                holder.imageView.setImageBitmap(bitmap_un_click_3_3);
//            }
        }

        holder.linear_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedItem(position); // set the selected item when clicked
                Bitmap currentBitmap = bitmapList.get(position);
                for (int i = 0; i < bitmapList.size(); i++) {
                    Bitmap newBitmap;
                    if (i == 0) {
                        newBitmap = bitmap_un_click_2_row;
                    } else if (i == 1) {
                        newBitmap = bitmap_un_click_2_collum;
                    } else if (i == 2) {
                        newBitmap = bitmap_un_click_3_row;
                    } else if (i == 3) {
                        newBitmap = bitmap_un_click_2_2;
                    } else if (i == 4) {
                        newBitmap = bitmap_un_click_2_3;
                    } else if (i == 5) {
                        newBitmap = bitmap_un_click_3_3;
                    } else {
                        newBitmap = null; // Thay đổi bitmap theo logic của bạn
                    }
                    bitmapList.set(i, newBitmap);
                    if (position == 0) {
                        Bitmap newBitmap_ct = bitmap_click_2_row;
                        bitmapList.set(position, newBitmap_ct);
                        if (listener != null) {
                            listener.onItemClick(2, 0, 2);
                        }
                    } else if (position == 1) {
                        Bitmap newBitmap_ct = bitmap_click_2_collum;
                        bitmapList.set(position, newBitmap_ct);
                        if (listener != null) {
                            listener.onItemClick(0, 2, 2);
                        }

                    } else if (position == 2) {
                        Bitmap newBitmap_ct = bitmap_click_3_row;
                        bitmapList.set(position, newBitmap_ct);
                        if (listener != null) {
                            listener.onItemClick(3, 0, 3);
                        }

                    } else if (position == 3) {
                        Bitmap newBitmap_ct = bitmap_click_2_2;
                        bitmapList.set(position, newBitmap_ct);
                        if (listener != null) {
                            listener.onItemClick(2, 2, 4);
                        }

                    } else if (position == 4) {
                        Bitmap newBitmap_ct = bitmap_click_2_3;
                        bitmapList.set(position, newBitmap_ct);
                        if (listener != null) {
                            listener.onItemClick(3, 2, 6);
                        }

                    } else if (position == 5) {
                        Bitmap newBitmap_ct = bitmap_click_3_3;
                        bitmapList.set(position, newBitmap_ct);
                        if (listener != null) {
                            listener.onItemClick(3, 3, 9);
                        }
                    }

                }

                notifyDataSetChanged(); // Cập nhật lại GridView

            }
        });

        // Set the bitmap to the ImageView
        Bitmap bitmap = bitmapList.get(position);
        holder.imageView.setImageBitmap(bitmap);

        return convertView;
    }

    private Bitmap getModifiedBitmap(Bitmap bitmap) {
        // Modify the bitmap as needed

        // Example: Rotate the bitmap by 90 degrees
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return rotatedBitmap;
    }

    public boolean compareBitmaps(Bitmap bitmap1, Bitmap bitmap2) {
        if (bitmap1.getWidth() != bitmap2.getWidth() || bitmap1.getHeight() != bitmap2.getHeight()) {
            return false;
        }

        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitmap1.getPixel(x, y) != bitmap2.getPixel(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static class ViewHolder {
        ImageView imageView;
        ImageView lableImageView;
        LinearLayout linear_1;
    }
}

