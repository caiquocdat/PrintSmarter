package com.example.printmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Lable_Intro_Activity extends AppCompatActivity {
    ImageView clickImg;
    Boolean click = false;
    LinearLayout bottomLinear;
    SharedPreferences sharedPreferences_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lable_intro);
        mapping();
        SharedPreferences sharedPreferences_click = getSharedPreferences("CheckClick", MODE_PRIVATE);
        sharedPreferences_back = getSharedPreferences("Click", MODE_PRIVATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#E5E6EE"));
        }
        clickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click == false) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_click);
                    clickImg.setImageBitmap(bitmap);
                    click = true;
                } else {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_un_click);
                    clickImg.setImageBitmap(bitmap);
                    click = false;
                }
            }
        });
        bottomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lable_Intro_Activity.this, SelectOptionActivity.class);
                startActivity(intent);
                SharedPreferences.Editor myEdit = sharedPreferences_click.edit();
                myEdit.putString("check", ""+click);
                myEdit.apply();
            }
        });
    }

    private void mapping() {
        clickImg = findViewById(R.id.clickImg);
        bottomLinear = findViewById(R.id.bottomLinear);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int check = sharedPreferences_back.getInt("check", 0);
//        Toast.makeText(this, ""+check, Toast.LENGTH_SHORT).show();
        if(check==1){
            new CountDownTimer(1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    // Các công việc cần thực hiện sau mỗi giây (ví dụ: cập nhật giao diện)
                    // millisUntilFinished / 1000 = số giây còn lại
                }

                public void onFinish() {
                    // Công việc cần thực hiện sau khi đếm xong
                  finish();
                }

            }.start();

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor myEdit = sharedPreferences_back.edit();
        myEdit.remove("check");
        myEdit.apply();
    }
}