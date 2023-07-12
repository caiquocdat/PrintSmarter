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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.printmaster.fragment.FragmentActivity;
import com.example.printmaster.fragment.homefragment.HomeFragment;
import com.example.printmaster.fragment.homefragment.QuickActivity;

import java.util.ArrayList;

public class LablesActivity extends AppCompatActivity {
    LinearLayout linear_1, linear_2, linear_3, linear_4,bottomLinear;
    ImageView img_1, img_2, img_3, img_4, lable_1_img, lable_2_img, lable_3_img, lable_4_img;
    ArrayList<ImageView> all;
    ArrayList<ImageView> allLable;
    ArrayList<LinearLayout> allCheck;
    int row,collum,count_item;
    Drawable selectedItem, unSelectItem, selectedLable_6, unSelectLable_6, selectedLable_8, unSelectLable_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lables);
        mapping();

        row=3;
        collum=2;
        count_item=4;
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
        unSelectItem = getResources().getDrawable(R.drawable.un_select_printer);
        selectedItem = getResources().getDrawable(R.drawable.lable_item);

        Bitmap bitmap_click = BitmapFactory.decodeResource(getResources(), R.drawable.img_click);
        Bitmap bitmap_un_click = BitmapFactory.decodeResource(getResources(), R.drawable.img_un_click);
        Bitmap bitmap_click_8 = BitmapFactory.decodeResource(getResources(), R.drawable.img_8_click_lable);
        Bitmap bitmap_un_click_8 = BitmapFactory.decodeResource(getResources(), R.drawable.img_8_lable);
        Bitmap bitmap_click_6 = BitmapFactory.decodeResource(getResources(), R.drawable.img_6_click_lable);
        Bitmap bitmap_un_click_6 = BitmapFactory.decodeResource(getResources(), R.drawable.img_6_lable);

        lable_1_img.setImageBitmap(bitmap_click_6);
        lable_2_img.setImageBitmap(bitmap_un_click_8);
        lable_3_img.setImageBitmap(bitmap_un_click_6);
        lable_4_img.setImageBitmap(bitmap_un_click_6);

        all = new ArrayList<>();
        all.add(img_1);
        all.add(img_2);
        all.add(img_3);
        all.add(img_4);
        allCheck = new ArrayList<>();
        allCheck.add(linear_1);
        allCheck.add(linear_2);
        allCheck.add(linear_3);
        allCheck.add(linear_4);
        allLable = new ArrayList<>();
        allLable.add(lable_1_img);
        allLable.add(lable_2_img);
        allLable.add(lable_3_img);
        allLable.add(lable_4_img);

        linear_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ImageView img : all) {
                    img.setImageBitmap(bitmap_un_click);
                }
                img_1.setImageBitmap(bitmap_click);
                for (LinearLayout linear : allCheck) {
                    linear.setBackground(unSelectItem);
                }
                linear_1.setBackground(selectedItem);
                for (ImageView img : allLable) {
//                    Bitmap bitmapFromImageView = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    Bitmap bitmapFromImageView_2 = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    if (compareBitmaps(bitmapFromImageView_2, bitmap_un_click_8)||compareBitmaps(bitmapFromImageView_2, bitmap_click_8)) {
                        img.setImageBitmap(bitmap_un_click_8);
                    }else{
                        img.setImageBitmap(bitmap_un_click_6);
                    }
                }
                lable_1_img.setImageBitmap(bitmap_click_6);
                row=2;
                collum=2;
                count_item=4;
            }
        });
        linear_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ImageView img : all) {
                    img.setImageBitmap(bitmap_un_click);
                }
                img_2.setImageBitmap(bitmap_click);
                for (LinearLayout linear : allCheck) {
                    linear.setBackground(unSelectItem);
                }
                linear_2.setBackground(selectedItem);
                for (ImageView img : allLable) {
                    Bitmap bitmapFromImageView_2 = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    if (compareBitmaps(bitmapFromImageView_2, bitmap_un_click_8)) {
                        img.setImageBitmap(bitmap_un_click_8);
                    }else{
                        img.setImageBitmap(bitmap_un_click_6);
                    }
                }
                lable_2_img.setImageBitmap(bitmap_click_8);
                row=4;
                collum=2;
                count_item=8;
            }
        });
        linear_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ImageView img : all) {
                    img.setImageBitmap(bitmap_un_click);
                }
                img_3.setImageBitmap(bitmap_click);
                for (LinearLayout linear : allCheck) {
                    linear.setBackground(unSelectItem);
                }
                linear_3.setBackground(selectedItem);
                for (ImageView img : allLable) {
                    Bitmap bitmapFromImageView_2 = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    if (compareBitmaps(bitmapFromImageView_2, bitmap_un_click_8)||compareBitmaps(bitmapFromImageView_2, bitmap_click_8)) {
                        img.setImageBitmap(bitmap_un_click_8);
                    }else{
                        img.setImageBitmap(bitmap_un_click_6);
                    }
                }
                lable_3_img.setImageBitmap(bitmap_click_6);
                row=3;
                collum=2;
                count_item=6;
            }
        });
        linear_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ImageView img : all) {
                    img.setImageBitmap(bitmap_un_click);
                }
                img_4.setImageBitmap(bitmap_click);
                for (LinearLayout linear : allCheck) {
                    linear.setBackground(unSelectItem);
                }
                linear_4.setBackground(selectedItem);
                for (ImageView img : allLable) {
                    Bitmap bitmapFromImageView_2 = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    if (compareBitmaps(bitmapFromImageView_2, bitmap_un_click_8)||compareBitmaps(bitmapFromImageView_2, bitmap_click_8)) {
                        img.setImageBitmap(bitmap_un_click_8);
                    }else{
                        img.setImageBitmap(bitmap_un_click_6);
                    }
                }
                lable_4_img.setImageBitmap(bitmap_click_6);
                row=3;
                collum=3;
                count_item=9;
            }
        });
        bottomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LablesActivity.this, QuickActivity.class);
                startActivity(intent);
                SharedPreferences sharedPreferences_lable = getSharedPreferences("activity", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_lable = sharedPreferences_lable.edit();
                editor_lable.putString("activity", "lable");
                editor_lable.apply();
                SharedPreferences sharedPreferences = getSharedPreferences("info_lable", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("row", row);
                editor.putInt("collum", collum);
                editor.putInt("count_item", count_item);
                editor.apply();
            }
        });
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


    private void mapping() {
        linear_1 = findViewById(R.id.linear_1);
        linear_2 = findViewById(R.id.linear_2);
        linear_3 = findViewById(R.id.linear_3);
        linear_4 = findViewById(R.id.linear_4);
        img_1 = findViewById(R.id.img_1);
        img_2 = findViewById(R.id.img_2);
        img_3 = findViewById(R.id.img_3);
        img_4 = findViewById(R.id.img_4);
        lable_1_img = findViewById(R.id.lable_1_img);
        lable_2_img = findViewById(R.id.lable_2_img);
        lable_3_img = findViewById(R.id.lable_3_img);
        lable_4_img = findViewById(R.id.lable_4_img);
        bottomLinear=findViewById(R.id.bottomLinear);
    }
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