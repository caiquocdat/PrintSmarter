package com.example.printmaster.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.printmaster.R;
import com.example.printmaster.data.OnItemClickListener;

import java.io.File;
import java.util.ArrayList;

public class ImageLibraryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> images;
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ImageLibraryAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;

    }
    @Override
    public int getCount() {
        if (images==null) {
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

//        Toast.makeText(context, ""+activity_source, Toast.LENGTH_SHORT).show();
        LayoutInflater inflater;
//        if (convertView == null&& !activity_source.equals("sheet")) {
//            Toast.makeText(context, "a", Toast.LENGTH_SHORT).show();
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_image_library, parent, false);
//        }else{
//            Toast.makeText(context, "b", Toast.LENGTH_SHORT).show();
//             inflater = LayoutInflater.from(context);
//             convertView= inflater.inflate(R.layout.item_image_library_sheet, parent, false);
//        }

        String imagePath = images.get(position);

        // Lấy thông tin kích thước của ảnh
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(imagePath, options);
//        int imageWidth = options.outWidth;
//        int imageHeight = options.outHeight;

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
//        Log.d("QuocDat", ""+position+": "+images.get(position));
        Glide.with(context).load(images.get(position)) //.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.drawable.fragme_library_img).override(800,800).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(images.get(position),1);
                }
            }
        });

        return convertView;
    }
}
