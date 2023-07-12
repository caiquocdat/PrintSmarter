package com.example.printmaster.fragment.homefragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.printmaster.R;
import com.example.printmaster.filter.LinearFilter;
import com.example.printmaster.fragment.homefragment.HomeFragment;
import com.example.printmaster.fragment.homefragment.LibraryFragment;
import com.example.printmaster.fragment.homefragment.PhotoFragment;

public class QuickActivity extends AppCompatActivity {
    TextView toolbar_libraryTv,toolbar_photoTv;
    LinearLayout bottomLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);
        mapping();
        Intent intent = getIntent();
        String albumName = intent.getStringExtra("nameAlbum");
        SharedPreferences sharedPreferences = getSharedPreferences("activity", Context.MODE_PRIVATE);
        String activityValue = sharedPreferences.getString("activity", "");
        if (activityValue.equals("photo")||activityValue.equals("lable")){

        }else{
            bottomLinear.setVisibility(View.GONE);
        }
        LibraryFragment fragment1= new LibraryFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();
        toolbar_libraryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LibraryFragment fragment1= new LibraryFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });
        toolbar_photoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoFragment fragment1= new PhotoFragment();
                FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.content,fragment1,"");
                ft1.commit();
            }
        });
    }

    private void mapping() {
        toolbar_libraryTv=findViewById(R.id.toolbar_libraryTv);
        toolbar_photoTv=findViewById(R.id.toolbar_photoTv);
        bottomLinear=findViewById(R.id.bottomLinear);
    }



    @Override
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