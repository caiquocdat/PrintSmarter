package com.example.printmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.printmaster.adapter.GridSelectViewAdapter;
import com.example.printmaster.data.OnItemClickSelectListener;
import com.example.printmaster.fragment.homefragment.QuickActivity;

import java.util.ArrayList;

public class SelectOptionActivity extends AppCompatActivity {
    GridView girdView;
    LinearLayout bottomLinear;
    int row = 2, collum = 0;
    int countItem = 2;
    private GridSelectViewAdapter gridSelectViewAdapter;
    private ArrayList<Bitmap> bitmaps;
    SharedPreferences sharedPreferences_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option);
        mapping();
        sharedPreferences_click = getSharedPreferences("Click", MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
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
        bitmaps = new ArrayList<>();
        Bitmap bitmap_click_2_row = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_2_row_click);
        Bitmap bitmap_un_click_2_row = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_2_row);
        Bitmap bitmap_click_2_collum = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_phototo_layout_2_collum_click);
        Bitmap bitmap_un_click_2_collum = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_2_collum);
        Bitmap bitmap_click_3_row = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_3_row_click);
        Bitmap bitmap_un_click_3_row = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_3_row);
        Bitmap bitmap_click_2_2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_2_2_click);
        Bitmap bitmap_un_click_2_2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_2_2);
        Bitmap bitmap_click_2_3 = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_2_3_click);
        Bitmap bitmap_un_click_2_3 = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_2_3);
        Bitmap bitmap_click_3_3 = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_3_3_click);
        Bitmap bitmap_un_click_3_3 = BitmapFactory.decodeResource(getResources(), R.drawable.img_select_photo_layout_3_3);
        bitmaps.add(bitmap_click_2_row);
        bitmaps.add(bitmap_un_click_2_collum);
        bitmaps.add(bitmap_un_click_3_row);
        bitmaps.add(bitmap_un_click_2_2);
        bitmaps.add(bitmap_un_click_2_3);
        bitmaps.add(bitmap_un_click_3_3);
        gridSelectViewAdapter = new GridSelectViewAdapter(this, bitmaps);
        gridSelectViewAdapter.setOnItemClickSelectListener(new OnItemClickSelectListener() {
            @Override
            public void onItemClick(int row, int collum, int countItem) {

                bottomLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SelectOptionActivity.this, QuickActivity.class);
                        startActivity(intent);
                        SharedPreferences sharedPreferences = getSharedPreferences("info_sheet", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("row", row);
                        editor.putInt("collum", collum);
                        editor.putInt("itemCount", countItem);
                        editor.apply();
                    }
                });
            }
        });
        girdView.setAdapter(gridSelectViewAdapter);
        bottomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectOptionActivity.this, QuickActivity.class);
                startActivity(intent);
                SharedPreferences sharedPreferences = getSharedPreferences("info_sheet", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("row", row);
                editor.putInt("collum", collum);
                editor.putInt("itemCount", countItem);
                editor.apply();
            }
        });

    }

    private void mapping() {
        girdView = findViewById(R.id.girdView);
        bottomLinear = findViewById(R.id.bottomLinear);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                SharedPreferences.Editor myEdit = sharedPreferences_click.edit();
                myEdit.putInt("check",1);
                myEdit.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        // Xử lý sự kiện khi người dùng nhấn nút back
        // Thêm mã xử lý của bạn ở đây
        SharedPreferences.Editor myEdit = sharedPreferences_click.edit();
        myEdit.putInt("check",1);
        myEdit.apply();

        // Gọi phương thức gốc để thực hiện hành động mặc định của nút back
        super.onBackPressed();
    }
}