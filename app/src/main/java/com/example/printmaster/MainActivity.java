package com.example.printmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView hpTv,canonTv,brotherTv;
    LinearLayout bottomLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        hpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpTv.setBackground(getDrawable(R.drawable.select_printer));
                hpTv.setTextColor(Color.parseColor("#111111"));
                canonTv.setBackground(getDrawable(R.drawable.un_select_printer));
                canonTv.setTextColor(Color.parseColor("#A0A7AB"));
                brotherTv.setBackground(getDrawable(R.drawable.un_select_printer));
                brotherTv.setTextColor(Color.parseColor("#A0A7AB"));
            }
        });
        canonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpTv.setBackground(getDrawable(R.drawable.un_select_printer));
                canonTv.setBackground(getDrawable(R.drawable.select_printer));
                brotherTv.setBackground(getDrawable(R.drawable.un_select_printer));
                canonTv.setTextColor(Color.parseColor("#111111"));
                hpTv.setTextColor(Color.parseColor("#A0A7AB"));
                brotherTv.setTextColor(Color.parseColor("#A0A7AB"));
            }
        });
        brotherTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hpTv.setBackground(getDrawable(R.drawable.un_select_printer));
                canonTv.setBackground(getDrawable(R.drawable.un_select_printer));
                brotherTv.setBackground(getDrawable(R.drawable.select_printer));
                brotherTv.setTextColor(Color.parseColor("#111111"));
                hpTv.setTextColor(Color.parseColor("#A0A7AB"));
                canonTv.setTextColor(Color.parseColor("#A0A7AB"));
            }
        });
        bottomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Onboarding_1_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void mapping() {
        hpTv=findViewById(R.id.hpTv);
        canonTv=findViewById(R.id.canonTv);
        brotherTv=findViewById(R.id.brotherTv);
        bottomLinear=findViewById(R.id.bottomLinear);
    }
    @Override
    public void onBackPressed() {

    }
}