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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

public class Detail_Sheet_Activity extends AppCompatActivity {
    LinearLayout horizontalScrollViewLayout;
    ImageView img;
    TextView toolbar_nextTv;
    ArrayList<String> listPath,listPathUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sheet);
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
        SharedPreferences sharedPreferences_lable = getSharedPreferences("path_sheet", Context.MODE_PRIVATE);
        String pathJson = sharedPreferences_lable.getString("path", "");
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(pathJson);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        listPath = new ArrayList<>();
        listPathUpdate = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String path = null;
            try {
                path = jsonArray.getString(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            listPath.add(path);
        }
        saveListToSharedPreferences(listPath,"update");
        SharedPreferences sharedPreferences_sheet = getSharedPreferences("update_path", Context.MODE_PRIVATE);
        String pathJson_sheet = sharedPreferences_sheet.getString("update", "");
        JSONArray jsonArray_sheet = null;
        try {
            jsonArray_sheet = new JSONArray(pathJson_sheet);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < jsonArray_sheet.length(); i++) {
            String path = null;
            try {
                path = jsonArray_sheet.getString(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            listPathUpdate.add(path);
        }
        Intent intent= getIntent();
        String path_url=intent.getStringExtra("image_uri");
        int position=intent.getIntExtra("position",0);
        if (path_url!=null){
            listPath.set(position,path_url);
            listPathUpdate.set(position,path_url);
            saveListToSharedPreferences(listPathUpdate,"update");
        }
        Log.d("Test_11", "position: "+position+", listPath: "+listPathUpdate);
//        addImagesToHorizontalScrollView(pathList,horizontalScrollViewLayout);
//        Glide.with(getApplicationContext()).load(listPath.get(0))
//                .placeholder(R.drawable.fragme_library_img).override(80, 80).into(img);
        addImagesToHorizontalScrollView(listPath,horizontalScrollViewLayout);
        toolbar_nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Detail_Sheet_Activity.this,InfoSheetActivity.class);
                intent.putStringArrayListExtra("image_uri",listPath);
                intent.putExtra("activity","sheet");
                startActivity(intent);
            }
        });


    }
    private void saveListToSharedPreferences(ArrayList<String> list, String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("update_path", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray jsonArray = new JSONArray(list);
        editor.putString(key, jsonArray.toString());
        editor.apply();
    }

    private void mapping() {
        horizontalScrollViewLayout=findViewById(R.id.horizontalScrollViewLayout);
        toolbar_nextTv=findViewById(R.id.toolbar_nextTv);
    }

    private void addImagesToHorizontalScrollView(ArrayList<String> imagePaths, LinearLayout linearContainer) {
        for (int i = 0; i < imagePaths.size(); i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    900, // Chiều rộng của ImageView
                    900,  // Chiều cao của ImageView
                    1f // Trọng số (weight) của ImageView
            );

            // Đặt margins cho ImageView
            int margin = 20; // Số pixel margin
            layoutParams.setMargins(
                    i == 0 ? margin : margin / 2, // Đặt margin đầu tiên lớn hơn so với các margin còn lại
                    margin,
                    i == imagePaths.size() - 1 ? margin : margin / 2, // Đặt margin cuối cùng lớn hơn so với các margin còn lại
                    margin
            );

            ImageView imageView = new ImageView(this);
            imageView.setImageURI(Uri.parse(imagePaths.get(i)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(layoutParams);
            final int position = i; // Biến final để sử dụng trong OnClickListener

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Xử lý sự kiện click tại đây. Ví dụ, hiển thị ảnh đã chọn:
//                    Toast.makeText(Detail_Sheet_Activity.this, "Clicked image at position: " + position, Toast.LENGTH_SHORT).show();
                    // Sử dụng 'position' để lấy thông tin liên quan từ 'imagePaths' hoặc thực hiện các hành động khác
                    String img_url=listPath.get(position);
                    BitmapFactory.Options options = getImageSize(img_url);
                    int imageWidth = options.outWidth;
                    int imageHeight = options.outHeight;
                    Intent intent = new Intent(Detail_Sheet_Activity.this, FilterActivity.class);
                    intent.putExtra("image_uri", img_url);
                    intent.putExtra("activity", "library");
                    intent.putExtra("width", imageWidth);
                    intent.putExtra("height", imageHeight);
                    intent.putExtra("position", position);
                    Log.d("Test_1", "width: "+imageWidth+",height: "+imageHeight+", path: "+img_url);
                    startActivity(intent);

                }
            });

            linearContainer.addView(imageView);
        }
    }
    public static BitmapFactory.Options getImageSize(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        return options;
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