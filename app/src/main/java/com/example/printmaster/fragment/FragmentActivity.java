package com.example.printmaster.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.printmaster.R;
import com.example.printmaster.fragment.homefragment.HomeFragment;

public class FragmentActivity extends AppCompatActivity {
    ImageView chatImg,exploreImg,searchImg,profileImg;
    LinearLayout chatBrg,exploreBrg,recentBrg;
    Drawable selectedItem,unSelectItem ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        HomeFragment fragment1= new HomeFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();
        unSelectItem=getResources().getDrawable(R.drawable.shape_item_bottom_bar);
        selectedItem = getResources().getDrawable(R.drawable.shape_item_bottom_bar_selected);
        Drawable drawableHomeWhite = getResources().getDrawable(R.drawable.item_home_fragment_white);
        Bitmap bitmapHomeWhite = ((BitmapDrawable) drawableHomeWhite).getBitmap();
        Drawable drawableHomeBlack = getResources().getDrawable(R.drawable.item_home_fragment);
        Bitmap bitmapHomeBlack = ((BitmapDrawable) drawableHomeBlack).getBitmap();
        Drawable drawableScanWhite = getResources().getDrawable(R.drawable.item_scan_fragment);
        Bitmap bitmapScanWhite = ((BitmapDrawable) drawableScanWhite).getBitmap();
        Drawable drawableScanBlack = getResources().getDrawable(R.drawable.item_scan_fragment_black);
        Bitmap bitmapScanBlack = ((BitmapDrawable) drawableScanBlack).getBitmap();
        Drawable drawableSettingWhite = getResources().getDrawable(R.drawable.item_setting_fragment);
        Bitmap bitmapSettingWhite = ((BitmapDrawable) drawableSettingWhite).getBitmap();
        Drawable drawableSettingBlack = getResources().getDrawable(R.drawable.item_setting_fragment_black);
        Bitmap bitmapSettingBlack = ((BitmapDrawable) drawableSettingBlack).getBitmap();

        mapping();

        chatBrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBrg.setBackground(selectedItem);
                exploreBrg.setBackground(unSelectItem);
                recentBrg.setBackground(unSelectItem);
                chatImg.setImageBitmap(bitmapHomeBlack);
                exploreImg.setImageBitmap(bitmapScanWhite);
                searchImg.setImageBitmap(bitmapSettingWhite);
                HomeFragment fragment1= new HomeFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });
        exploreBrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBrg.setBackground(unSelectItem);
                exploreBrg.setBackground(selectedItem);
                recentBrg.setBackground(unSelectItem);
                chatImg.setImageBitmap(bitmapHomeWhite);
                exploreImg.setImageBitmap(bitmapScanBlack);
                searchImg.setImageBitmap(bitmapSettingWhite);
                ScanFragment fragment1= new ScanFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });

        recentBrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatBrg.setBackground(unSelectItem);
                exploreBrg.setBackground(unSelectItem);
                recentBrg.setBackground(selectedItem);
                chatImg.setImageBitmap(bitmapHomeWhite);
                exploreImg.setImageBitmap(bitmapScanWhite);
                searchImg.setImageBitmap(bitmapSettingBlack);
                SettingFragment fragment1= new SettingFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });
    }
    private void mapping() {
        chatImg=findViewById(R.id.chatImg);
        exploreImg=findViewById(R.id.exploreImg);
        searchImg=findViewById(R.id.searchImg);
        chatBrg=findViewById(R.id.chatBrg);
        exploreBrg=findViewById(R.id.exploreBrg);
        recentBrg=findViewById(R.id.recentBrg);
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        // hoặc
        // finish();
        // System.exit(0);
    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("check_first", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", 1);
        editor.apply();
    }
}