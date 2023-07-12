package com.example.printmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.printmaster.fragment.FragmentActivity;
import com.example.printmaster.fragment.homefragment.QuickActivity;

public class PurpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purple);
        View purpleView = findViewById(R.id.purpleBackgroundView);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
//        view.startAnimation(animation);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(PurpleActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 1000);
        purpleView.post(new Runnable() {
            @Override
            public void run() {
                // Tính toán vị trí tâm
                int centerX = purpleView.getWidth() / 2;
                int centerY = purpleView.getHeight() / 2;

                // Tính toán bán kính lớn nhất để che phủ toàn bộ View.
                float maxRadius = (float) Math.hypot(centerX, centerY);

                // Tạo một animation CircularReveal cho view.
                Animator reveal = ViewAnimationUtils.createCircularReveal(
                        purpleView, centerX, centerY, 0, maxRadius);

                // Đặt thời lượng animation và bắt đầu nó
                reveal.setDuration(1000).start();

                // Sau khi hoàn tất hiệu ứng, chuyển sang MainActivity
                reveal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.setStatusBarColor(Color.parseColor("#AAB2FF"));
                        }
                        SharedPreferences sharedPreferences = getSharedPreferences("check_first", Context.MODE_PRIVATE);
                        int value = sharedPreferences.getInt("key", 0);
                        if (value == 0) {
                            Intent intent = new Intent(PurpleActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(PurpleActivity.this, FragmentActivity.class);
                            startActivity(intent);
                        }
                        finish(); // kết thúc PurpleActivity
                    }
                });
            }
        });
    }
}