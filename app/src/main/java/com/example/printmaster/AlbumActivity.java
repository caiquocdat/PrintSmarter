package com.example.printmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.printmaster.adapter.AlbumAdapter;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {
    RecyclerView albumsRcv;
    ArrayList<String> albums;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mapping();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#AAB2FF"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) this;
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setTitle("Compatible List");
            toolbar.setTitleTextColor(Color.parseColor("#F7F7F7"));
        }
        albums=getAlbums(AlbumActivity.this);
        albumsRcv.setLayoutManager(new LinearLayoutManager(this));
        AlbumAdapter adapter = new AlbumAdapter(this, albums);
        albumsRcv.setAdapter(adapter);
    }
    public static ArrayList<String> getAlbums(Context context) {
        ArrayList<String> albums = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                if (!albums.contains(album)) {
                    albums.add(album);
                }
            }
            cursor.close();
        }

        return albums;
    }

    private void mapping() {
        albumsRcv=findViewById(R.id.albumRcv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}