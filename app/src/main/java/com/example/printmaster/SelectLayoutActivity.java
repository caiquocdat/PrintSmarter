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

import com.example.printmaster.fragment.homefragment.QuickActivity;

import java.util.ArrayList;

public class SelectLayoutActivity extends AppCompatActivity {
    LinearLayout linear_1, linear_2, linear_3, linear_4,linear_5,linear_6,bottomLinear;
    ImageView img_1, img_2, img_3, img_4,img_5,img_6, lable_1_img, lable_2_img, lable_3_img, lable_4_img,lable_5_img,lable_6_img;
    ArrayList<ImageView> all;
    ArrayList<ImageView> allLable;
    ArrayList<LinearLayout> allCheck;
    int row=2,collum=0;
    int itemCount=2;
    Drawable selectedItem, unSelectItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_layout);
        mapping();
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
        lable_1_img.setImageBitmap(bitmap_click_2_row);
        lable_2_img.setImageBitmap(bitmap_un_click_2_collum);
        lable_3_img.setImageBitmap(bitmap_un_click_3_row);
        lable_4_img.setImageBitmap(bitmap_un_click_2_2);
        lable_5_img.setImageBitmap(bitmap_un_click_2_3);
        lable_6_img.setImageBitmap(bitmap_un_click_3_3);
        all = new ArrayList<>();
        all.add(img_1);
        all.add(img_2);
        all.add(img_3);
        all.add(img_4);
        all.add(img_5);
        all.add(img_6);
        allCheck = new ArrayList<>();
        allCheck.add(linear_1);
        allCheck.add(linear_2);
        allCheck.add(linear_3);
        allCheck.add(linear_4);
        allCheck.add(linear_5);
        allCheck.add(linear_6);
        allLable = new ArrayList<>();
        allLable.add(lable_1_img);
        allLable.add(lable_2_img);
        allLable.add(lable_3_img);
        allLable.add(lable_4_img);
        allLable.add(lable_5_img);
        allLable.add(lable_6_img);
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
                    if (compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_row)) {
                        img.setImageBitmap(bitmap_un_click_2_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_collum)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_collum)){
                        img.setImageBitmap(bitmap_un_click_2_collum);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_row)){
                        img.setImageBitmap(bitmap_un_click_3_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_2)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_2)){
                        img.setImageBitmap(bitmap_un_click_2_2);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_3)){
                        img.setImageBitmap(bitmap_un_click_2_3);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_3)){
                        img.setImageBitmap(bitmap_un_click_3_3);
                    }
                }
                lable_1_img.setImageBitmap(bitmap_click_2_row);
                row=2;
                collum=0;
                itemCount=2;
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
                    if (compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_row)) {
                        img.setImageBitmap(bitmap_un_click_2_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_collum)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_collum)){
                        img.setImageBitmap(bitmap_un_click_2_collum);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_row)){
                        img.setImageBitmap(bitmap_un_click_3_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_2)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_2)){
                        img.setImageBitmap(bitmap_un_click_2_2);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_3)){
                        img.setImageBitmap(bitmap_un_click_2_3);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_3)){
                        img.setImageBitmap(bitmap_un_click_3_3);
                    }
                }
                lable_2_img.setImageBitmap(bitmap_click_2_collum);
                row=0;
                collum=2;
                itemCount=2;
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
                    if (compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_row)) {
                        img.setImageBitmap(bitmap_un_click_2_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_collum)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_collum)){
                        img.setImageBitmap(bitmap_un_click_2_collum);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_row)){
                        img.setImageBitmap(bitmap_un_click_3_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_2)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_2)){
                        img.setImageBitmap(bitmap_un_click_2_2);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_3)){
                        img.setImageBitmap(bitmap_un_click_2_3);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_3)){
                        img.setImageBitmap(bitmap_un_click_3_3);
                    }
                }
                lable_3_img.setImageBitmap(bitmap_click_3_row);
                row=3;
                collum=0;
                itemCount=3;
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
                    if (compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_row)) {
                        img.setImageBitmap(bitmap_un_click_2_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_collum)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_collum)){
                        img.setImageBitmap(bitmap_un_click_2_collum);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_row)){
                        img.setImageBitmap(bitmap_un_click_3_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_2)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_2)){
                        img.setImageBitmap(bitmap_un_click_2_2);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_3)){
                        img.setImageBitmap(bitmap_un_click_2_3);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_3)){
                        img.setImageBitmap(bitmap_un_click_3_3);
                    }
                }
                lable_4_img.setImageBitmap(bitmap_click_2_2);
                row=2;
                collum=2;
                itemCount=4;
            }
        });
        linear_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ImageView img : all) {
                    img.setImageBitmap(bitmap_un_click);
                }
                img_5.setImageBitmap(bitmap_click);
                for (LinearLayout linear : allCheck) {
                    linear.setBackground(unSelectItem);
                }
                linear_5.setBackground(selectedItem);
                for (ImageView img : allLable) {
                    Bitmap bitmapFromImageView_2 = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    if (compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_row)) {
                        img.setImageBitmap(bitmap_un_click_2_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_collum)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_collum)){
                        img.setImageBitmap(bitmap_un_click_2_collum);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_row)){
                        img.setImageBitmap(bitmap_un_click_3_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_2)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_2)){
                        img.setImageBitmap(bitmap_un_click_2_2);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_3)){
                        img.setImageBitmap(bitmap_un_click_2_3);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_3)){
                        img.setImageBitmap(bitmap_un_click_3_3);
                    }
                }
                lable_5_img.setImageBitmap(bitmap_click_2_3);
                row=3;
                collum=2;
                itemCount=6;
            }
        });
        linear_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ImageView img : all) {
                    img.setImageBitmap(bitmap_un_click);
                }
                img_6.setImageBitmap(bitmap_click);
                for (LinearLayout linear : allCheck) {
                    linear.setBackground(unSelectItem);
                }
                linear_6.setBackground(selectedItem);
                for (ImageView img : allLable) {
                    Bitmap bitmapFromImageView_2 = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    if (compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_row)) {
                        img.setImageBitmap(bitmap_un_click_2_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_collum)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_collum)){
                        img.setImageBitmap(bitmap_un_click_2_collum);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_row)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_row)){
                        img.setImageBitmap(bitmap_un_click_3_row);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_2)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_2)){
                        img.setImageBitmap(bitmap_un_click_2_2);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_2_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_2_3)){
                        img.setImageBitmap(bitmap_un_click_2_3);
                    }else if(compareBitmaps(bitmapFromImageView_2, bitmap_un_click_3_3)||compareBitmaps(bitmapFromImageView_2, bitmap_click_3_3)){
                        img.setImageBitmap(bitmap_un_click_3_3);
                    }
                }
                lable_6_img.setImageBitmap(bitmap_click_3_3);
                row=3;
                collum=3;
                itemCount=9;
            }
        });
        bottomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLayoutActivity.this, QuickActivity.class);
                startActivity(intent);
                SharedPreferences sharedPreferences = getSharedPreferences("info_sheet", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("row", row);
                editor.putInt("collum", collum);
                editor.putInt("itemCount", itemCount);
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
        linear_5 = findViewById(R.id.linear_5);
        linear_6 = findViewById(R.id.linear_6);
        img_1 = findViewById(R.id.img_1);
        img_2 = findViewById(R.id.img_2);
        img_3 = findViewById(R.id.img_3);
        img_4 = findViewById(R.id.img_4);
        img_5 = findViewById(R.id.img_5);
        img_6 = findViewById(R.id.img_6);
        lable_1_img = findViewById(R.id.lable_1_img);
        lable_2_img = findViewById(R.id.lable_2_img);
        lable_3_img = findViewById(R.id.lable_3_img);
        lable_4_img = findViewById(R.id.lable_4_img);
        lable_5_img = findViewById(R.id.lable_5_img);
        lable_6_img = findViewById(R.id.lable_6_img);
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