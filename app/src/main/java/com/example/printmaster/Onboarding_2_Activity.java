package com.example.printmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Onboarding_2_Activity extends AppCompatActivity {
    LinearLayout bottomLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding2);
        mapping();
        bottomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Onboarding_2_Activity.this, Onboarding_3_Activity.class);
                startActivity(intent);
            }
        });
    }


    private void mapping() {
        bottomLinear=findViewById(R.id.bottomLinear);
    }
    @Override
    public void onBackPressed() {

    }
}