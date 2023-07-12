package com.example.printmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ResutlScanActivity extends AppCompatActivity {
    ImageView detailScanImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resutl_scan);
        mapping();
        SharedPreferences sharedPreferences_scan = getSharedPreferences("img_scan", Context.MODE_PRIVATE);
        String path_scan = sharedPreferences_scan.getString("path", "");
        Glide.with(getApplicationContext()).load(path_scan).into(detailScanImg);
    }

    private void mapping() {
        detailScanImg=findViewById(R.id.detailScanImg);
    }
}