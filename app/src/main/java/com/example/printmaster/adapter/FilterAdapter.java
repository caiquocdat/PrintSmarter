package com.example.printmaster.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.printmaster.R;
import com.example.printmaster.data.OnItemClickListener;
import com.example.printmaster.data.OnItemFilterClickListener;

import java.util.ArrayList;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Bitmap> images;
    private OnItemFilterClickListener listener;

    public void setOnItemClickListener(OnItemFilterClickListener listener) {
        this.listener = listener;
    }

    public FilterAdapter(Context context, ArrayList<Bitmap> images) {
        this.context = context;
        this.images = images;
    }
    public void addBitmap(Bitmap bitmap) {
        images.add(bitmap);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



      View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_filter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Glide.with(context).load(images.get(position))
//                .placeholder(R.drawable.fragme_library_img).override(60,60).into(holder.imageView);

        holder.imageView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(images.get(position));
            }
        });
        holder.imageView.setImageBitmap(images.get(position));
        if (position==0) {
            holder.nameFilterTv.setText("Normal");
        }else if(position==1){
            holder.nameFilterTv.setText("Nashville");
        }if (position==2) {
            holder.nameFilterTv.setText("Toaster");
        }else if(position==3){
            holder.nameFilterTv.setText("1977");
        }if (position==4) {
            holder.nameFilterTv.setText("Clarendon");
        }else if(position==5){
            holder.nameFilterTv.setText("Chrome");
        }if (position==6) {
            holder.nameFilterTv.setText("Fade");
        }else if(position==7){
            holder.nameFilterTv.setText("Instant");
        }
        if (position==8) {
            holder.nameFilterTv.setText("Mono");
        }else if(position==9){
            holder.nameFilterTv.setText("Noir");
        }if (position==10) {
            holder.nameFilterTv.setText("Process");
        }else if(position==11){
            holder.nameFilterTv.setText("Tonal");
        }if (position==12) {
            holder.nameFilterTv.setText("Transfer");
        }else if(position==13){
            holder.nameFilterTv.setText("Tone");
        }if (position==14) {
            holder.nameFilterTv.setText("Linear");
        }else if(position==15){
            holder.nameFilterTv.setText("Sepia");
        }else if(position==16){
            holder.nameFilterTv.setText("XRay");
        }
        Log.d("QuocDat_test", "onBindViewHolder: "+position);
    }

    @Override
    public int getItemCount() {
        if (images==null) {
            return 0;
        } else {
            return images.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameFilterTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameFilterTv = itemView.findViewById(R.id.nameFilter);
        }
    }
}
