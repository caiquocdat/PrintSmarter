package com.example.printmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.printmaster.adapter.ImageLableAdapter;
import com.example.printmaster.adapter.ImageSheetAdapter;
import com.example.printmaster.adapter.PrintPDFAdapter;

import java.util.ArrayList;

public class DetailLableActivity extends AppCompatActivity {
    GridView lableGrv;
    private ImageLableAdapter adapter;
    LinearLayout bottomLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lable);
        mapping();
        bottomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = Bitmap.createBitmap(lableGrv.getWidth(), lableGrv.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                lableGrv.draw(canvas);
                PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
                String jobName = getString(R.string.app_name) + " Document";
                printManager.print(jobName, new PrintPDFAdapter(DetailLableActivity.this, bitmap), null);
            }
        });
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("image_uri");
        SharedPreferences sharedPreferences_info = getSharedPreferences("info_sheet", Context.MODE_PRIVATE);
        int row = sharedPreferences_info.getInt("row", 0);
        int collum = sharedPreferences_info.getInt("collum", 0);
        int count_item = sharedPreferences_info.getInt("itemCount", 0);
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
        Log.d("test11", "row: "+row+",collum: "+collum+",path: "+imageUriString+",count: "+count_item);
        addImagesToGridView(row,collum,imageUriString,count_item,lableGrv);
    }
    private void addImagesToGridView(int numRows, int numColumns, String imagePaths,int countItem, GridView gridView) {
        gridView.setNumColumns(numColumns);

        ImageLableAdapter adapter = new ImageLableAdapter(DetailLableActivity.this, imagePaths,countItem,numRows,numColumns);
        gridView.setAdapter(adapter);
    }



    private void mapping() {
        lableGrv=findViewById(R.id.lableGrv);
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