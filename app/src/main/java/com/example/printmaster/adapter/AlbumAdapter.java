package com.example.printmaster.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.printmaster.data.OnItemClickListener;
import com.example.printmaster.fragment.homefragment.QuickActivity;
import com.example.printmaster.R;

import java.util.HashMap;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private List<String> albums;
    private Context context;

    public AlbumAdapter(Context context, List<String> albums) {
        this.albums = albums;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_library_albums, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String album = albums.get(position);
        HashMap<String, Integer> albumCounts = getAlbumCounts(context);
        holder.albumNameTv.setText(album);

        if (albumCounts.containsKey(album)) {
            int count = albumCounts.get(album);
            holder.countPicturetv.setText(String.valueOf(count));
        }

        Uri firstImageUri = getFirstImageUriFromAlbum(context, album);
        if (firstImageUri != null) {
            Glide.with(context).load(firstImageUri).into(holder.thumImg);
        }
        holder.itemLiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuickActivity.class);
                intent.putExtra("nameAlbum", album);
                context.startActivity(intent);
                SharedPreferences sharedPref = context.getSharedPreferences("NameAlbum", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("name", album);
                editor.apply();


            }
        });
    }
    public HashMap<String, Integer> getAlbumCounts(Context context) {
        // Uri to the table in the provider
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Columns to return
        String[] projection = { MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        // Query the content resolver
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        // HashMap to hold the result
        HashMap<String, Integer> albumCounts = new HashMap<>();

        if (cursor != null) {
            int albumNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            while (cursor.moveToNext()) {
                // Get the album name
                String albumName = cursor.getString(albumNameColumn);

                // If this album is not yet in the map, put it in with count 1
                // If it is, increment the count
                if (albumCounts.containsKey(albumName)) {
                    albumCounts.put(albumName, albumCounts.get(albumName) + 1);
                } else {
                    albumCounts.put(albumName, 1);
                }
            }

            cursor.close();
        }

        return albumCounts;
    }

    private Uri getFirstImageUriFromAlbum(Context context, String album) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media._ID};
        String selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[] { album };

        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            if (columnIndex != -1) {
                int id = cursor.getInt(columnIndex);
                cursor.close();
                return ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            }
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView albumNameTv;
        TextView countPicturetv;
        ImageView thumImg;
        LinearLayout itemLiner;

        ViewHolder(View itemView) {
            super(itemView);
            albumNameTv = itemView.findViewById(R.id.nameAlbumTv);
            itemLiner = itemView.findViewById(R.id.itemLiner);
            thumImg = itemView.findViewById(R.id.thumImg);
            countPicturetv = itemView.findViewById(R.id.countPictureTv);
        }
    }


}

