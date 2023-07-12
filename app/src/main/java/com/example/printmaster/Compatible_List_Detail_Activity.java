package com.example.printmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.printmaster.adapter.CompatibleAdapter;
import com.example.printmaster.data.CompatibleData;
import com.example.printmaster.model.CompatibleModel;
import com.example.printmaster.model.NameCompatibleModel;

import java.util.ArrayList;
import java.util.List;

public class Compatible_List_Detail_Activity extends AppCompatActivity {
    ImageView searchImg,exitImg;
    RelativeLayout compatibleListRel;
    TextView cancelTv;
    EditText contentEdt;
    CompatibleAdapter compatibleAdapter;
    ArrayList<CompatibleModel> compatibleModels;
    RecyclerView nameRcv;
    String value;
    CompatibleData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatible_list_detail);
        mapping();
        Intent intent = getIntent();
        value= intent.getStringExtra("type");
        data = new CompatibleData();


        compatibleAdapter= new CompatibleAdapter(Compatible_List_Detail_Activity.this,getAllName(),value);
        nameRcv.setLayoutManager(new LinearLayoutManager(this));
        nameRcv.setItemAnimator(new DefaultItemAnimator());
        nameRcv.setAdapter(compatibleAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#AAB2FF"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Compatible List");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.img_back);
            getSupportActionBar().setTitle("Compatible List");
            toolbar.setTitleTextColor(Color.parseColor("#F7F7F7"));
        }
        exitImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentEdt.setText("");
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentEdt.setText("");
                compatibleAdapter= new CompatibleAdapter(Compatible_List_Detail_Activity.this,getAllName(),value);
                nameRcv.setLayoutManager(new LinearLayoutManager(Compatible_List_Detail_Activity.this));
                nameRcv.setItemAnimator(new DefaultItemAnimator());
                nameRcv.setAdapter(compatibleAdapter);
                contentEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(contentEdt.getWindowToken(), 0);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_search_detail_compatible);
                searchImg.setImageBitmap(bitmap);
                exitImg.setVisibility(View.GONE);
                cancelTv.setVisibility(View.GONE);

            }
        });
        contentEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<NameCompatibleModel> filteredList=null;
                String searchText = contentEdt.getText().toString();
                if(searchText.isEmpty()){
                    filteredList = getAllName();
                    compatibleAdapter= new CompatibleAdapter(Compatible_List_Detail_Activity.this,filteredList,value);
                    nameRcv.setLayoutManager(new LinearLayoutManager(Compatible_List_Detail_Activity.this));
                    nameRcv.setItemAnimator(new DefaultItemAnimator());
                    nameRcv.setAdapter(compatibleAdapter);
                }else {
                    if (getAllSeachName(contentEdt.getText().toString()).size() > 0) {
                        filteredList = getAllSeachName(searchText);
                        compatibleAdapter= new CompatibleAdapter(Compatible_List_Detail_Activity.this,filteredList,value);
                        nameRcv.setLayoutManager(new LinearLayoutManager(Compatible_List_Detail_Activity.this));
                        nameRcv.setItemAnimator(new DefaultItemAnimator());
                        nameRcv.setAdapter(compatibleAdapter);
                    } else {

                    }
                }
                compatibleAdapter= new CompatibleAdapter(Compatible_List_Detail_Activity.this,filteredList,value);
                nameRcv.setLayoutManager(new LinearLayoutManager(Compatible_List_Detail_Activity.this));
                nameRcv.setItemAnimator(new DefaultItemAnimator());
                nameRcv.setAdapter(compatibleAdapter);
            }
        });
        compatibleListRel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                contentEdt.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(contentEdt.getWindowToken(), 0);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_search_detail_compatible);
                searchImg.setImageBitmap(bitmap);
                exitImg.setVisibility(View.GONE);
                cancelTv.setVisibility(View.GONE);
                return false;
            }
        });
        contentEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_compatible_search_gray);
                searchImg.setImageBitmap(bitmap);
                exitImg.setVisibility(View.VISIBLE);
                cancelTv.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    private void mapping() {
        searchImg=findViewById(R.id.searchImg);
        contentEdt=findViewById(R.id.contentEdt);
        exitImg=findViewById(R.id.exitImg);
        cancelTv=findViewById(R.id.cancelTv);
        compatibleListRel=findViewById(R.id.compatibleListRel);
        nameRcv=findViewById(R.id.nameRcv);
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
    ArrayList<NameCompatibleModel> getAllName(){

        ArrayList<CompatibleModel> compatible = data.getCompatible();
        ArrayList<NameCompatibleModel> listNameCompatible= new ArrayList<>();
        for(int i=0;i<compatible.size();i++){
            if(compatible.get(i).getType().equals(value)){
                listNameCompatible=compatible.get(i).getListName();
            }
        }
       return listNameCompatible;
    }
    ArrayList<NameCompatibleModel> getAllSeachName(String name){
        ArrayList<CompatibleModel> compatible = data.getCompatible();
        ArrayList<NameCompatibleModel> resultList = new ArrayList<>();

        for (int i = 0; i < compatible.size(); i++) {
            if (compatible.get(i).getType().equals(value)) {
                ArrayList<NameCompatibleModel> listNameCompatible = compatible.get(i).getListName();
                for (int j = 0; j < listNameCompatible.size(); j++) {
                    if (listNameCompatible.get(j).getName().toUpperCase().contains(name.toUpperCase())) {
                        resultList.add(listNameCompatible.get(j));
                    }
                }
            }
        }
        return resultList;
    }
}