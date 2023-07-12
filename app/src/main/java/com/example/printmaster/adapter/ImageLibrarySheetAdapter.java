package com.example.printmaster.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.printmaster.R;
import com.example.printmaster.data.OnItemClickListener;

import org.json.JSONArray;

import java.util.ArrayList;



import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.printmaster.R;
import com.example.printmaster.data.OnItemClickListener;

import org.json.JSONArray;

import java.util.ArrayList;

public class ImageLibrarySheetAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> images;
    private OnItemClickListener listener;
    int count = 0;
    private ArrayList<String> listItem = new ArrayList<>();
    private ArrayList<String> listPath= new ArrayList<>();

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ImageLibrarySheetAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        if (images == null) {
            return 0;
        } else {
            return images.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_image_library_sheet, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.clickImg = (ImageView) convertView.findViewById(R.id.clickImg);
            holder.clickRel = (RelativeLayout) convertView.findViewById(R.id.itemRel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SharedPreferences sharedPreferences_info = context.getSharedPreferences("info_sheet", Context.MODE_PRIVATE);
        int itemCount = sharedPreferences_info.getInt("itemCount", 0);

        Log.d("QuocDat", "getView " + position + ": " + images.get(position));
        Glide.with(context).load(images.get(position))
                .placeholder(R.drawable.fragme_library_img).override(800, 800).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int width = holder.imageView.getMeasuredWidth();
                int height = holder.imageView.getMeasuredHeight();
                String item = "item" + position;
//                Log.d("Test_1", "Width: "+width+", height: "+height);
                if (listItem.contains(item) && listPath.contains(images.get(position))) {
                    // Item đã được click trước đó, cho phép click vào item khác
                    listItem.remove(item);
                    listPath.remove(images.get(position));
                    Drawable drawable = context.getResources().getDrawable(R.drawable.img_un_click);
                    holder.clickImg.setImageDrawable(drawable);
                    count--;
                    Log.d("QuocDat_2", "onClick: " + item + ", itemcount " + itemCount + ", list size" + listItem.size() + "-" + count);
                    if(listener != null) {
                        listener.onItemClick(images.get(position), count);
                    }
                } else {
                    if (count >= itemCount) {
                        Toast.makeText(context, "The number of photos exceeds the requirement.", Toast.LENGTH_SHORT).show();
                    } else {
                        Drawable drawable = context.getResources().getDrawable(R.drawable.img_click);
                        holder.clickImg.setImageDrawable(drawable);
                        listItem.add(item);
                        listPath.add(images.get(position));
                        count++;
                        if (listener != null) {
                            listener.onItemClick(images.get(position), count);
                            Log.d("QuocDat_3", "size: " + listItem.size() + " about:");
                            for (int i = 0; i < listItem.size(); i++) {
                                Log.d("QuocDat_3", listItem.get(i));
                            }
                            Log.d("QuocDat_3", "onClick " + position + ": " + images.get(position));
                            Log.d("QuocDat_3", "size: " + listPath.size() + " about:");
                            for (int i = 0; i < listPath.size(); i++) {
                                Log.d("QuocDat_3", listPath.get(i));
                            }

                            SharedPreferences sharedPreferences_lable = context.getSharedPreferences("path_sheet", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_lable = sharedPreferences_lable.edit();
                            JSONArray jsonArray = new JSONArray(listPath);
                            String pathJson = jsonArray.toString();
                            editor_lable.putString("path", pathJson);
                            editor_lable.apply();
                        }
                    }
                }
                Log.d("QuocDat_2", "onClick: " + item + ", itemcount " + itemCount + ", list size" + listItem.size() + "-" + count);
            }
        });

        // Set ảnh đã chọn hoặc chưa chọn cho clickImg
        if (listItem.contains("item" + position) && listPath.contains(images.get(position))) {
            Drawable drawable = context.getResources().getDrawable(R.drawable.img_click);
            holder.clickImg.setImageDrawable(drawable);
        } else {
            Drawable drawable = context.getResources().getDrawable(R.drawable.img_un_click);
            holder.clickImg.setImageDrawable(drawable);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        ImageView clickImg;
        RelativeLayout clickRel;
    }
}
