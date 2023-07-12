package com.example.printmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.printmaster.adapter.ImagePrintDocumentAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {
    private WebView myWebView;
    LinearLayout layoutWeb;
    TextView toolbar_printTv;
    int part = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
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
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Có thể hiển thị hình ảnh đang tải ở đây
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Trang web đã tải xong, bạn có thể chụp ảnh ở đây
//                captureWebParts(myWebView);
//                Bitmap bitmap = Bitmap.createBitmap(myWebView.getWidth(), myWebView.getHeight(), Bitmap.Config.ARGB_8888);
//                Canvas canvas = new Canvas(bitmap);
//                myWebView.draw(canvas);
//                List<Bitmap> listBitmap= new ArrayList<>();
//                listBitmap.add(bitmap);
//                PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
//                String jobName = getString(R.string.app_name) + " Document";
//                printManager.print(jobName, new ImagePrintDocumentAdapter(WebViewActivity.this,listBitmap), null);
            }
        });
        myWebView.loadUrl("http://www.google.com");
        toolbar_printTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                captureWebParts(myWebView);
                takeScreenshot();
            }
        });
    }
    public void captureWebPartsNew(WebView webView) {
        int totalHeight = webView.getContentHeight();

        List<Bitmap> listBimap = new ArrayList<>();

        // Copy vào một danh sách để cuộn qua mỗi phần
        List<Integer> heights = new ArrayList<>();
        for (int i = 0; i < totalHeight; i += webView.getHeight()) {
            heights.add(i);
        }

        // Tạo một Runnable để chụp từng phần
        Runnable capturePartRunnable = new Runnable() {
            @Override
            public void run() {
                if (!heights.isEmpty()) {
                    int height = heights.remove(0);
                    webView.scrollTo(0, height);
                    webView.postDelayed(() -> {
                        Bitmap bitmap = captureBitmapFromWebView(webView);
                        listBimap.add(bitmap);
                        Log.d("Test_2", "captureWebParts: "+bitmap);
                        String filename = "screenshot" + part + ".png";
                        saveBitmap(bitmap, filename);
                        part++;

                        // Chụp phần tiếp theo sau một độ trễ nhỏ
                        webView.postDelayed(this, 500);
                    }, 500);
                } else {
                    PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
                    String jobName = getString(R.string.app_name) + " Document";
                    printManager.print(jobName, new ImagePrintDocumentAdapter(WebViewActivity.this,listBimap), null);
                }
            }
        };

        // Bắt đầu chụp
        webView.post(capturePartRunnable);
        //print
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        String jobName = getString(R.string.app_name) + " Document";
        printManager.print(jobName, new ImagePrintDocumentAdapter(WebViewActivity.this,listBimap), null);
    }

    public void captureWebParts(WebView webView) {
        int totalHeight = webView.getContentHeight();
        int scrolledHeight = 0;
        int part = 0;
        List<Bitmap> listBimap = new ArrayList<>();
        List<Bitmap> listBimapCopy = new ArrayList<>();

        while (scrolledHeight < totalHeight) {
            webView.scrollTo(0, scrolledHeight);
            Bitmap bitmap = captureBitmapFromWebView(webView);
            listBimap.add(bitmap);
            Log.d("Test_2", "captureWebParts: "+bitmap);
            // Save the bitmap
            String filename = "screenshot" + part + ".png";
            saveBitmap(bitmap, filename);

            scrolledHeight += webView.getHeight();
            part++;
        }
        webView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(webView.getDrawingCache(false));
        webView.setDrawingCacheEnabled(false);
        listBimapCopy.add(bitmap);
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        String jobName = getString(R.string.app_name) + " Document";
        printManager.print(jobName, new ImagePrintDocumentAdapter(WebViewActivity.this,listBimapCopy), null);
    }

    public Bitmap captureBitmapFromWebView(WebView webView) {
        Bitmap bitmap = Bitmap.createBitmap(webView.getWidth(),
                webView.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        webView.draw(canvas);

        return bitmap;
    }
    public void saveBitmap(Bitmap bitmap, String filename) {
        try {
            FileOutputStream fos = myWebView.getContext().openFileOutput(filename, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void mapping() {
        myWebView = (WebView) findViewById(R.id.webview);
        toolbar_printTv = findViewById(R.id.toolbar_printTv);
        layoutWeb = findViewById(R.id.layoutWeb);
    }

    public void takeScreenshot() {
        try{
            List<Bitmap> listBimapCopy = new ArrayList<>();
            View v1 = myWebView;
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
            String jobName = getString(R.string.app_name) + " Document";
            listBimapCopy.add(bitmap);
            printManager.print(jobName, new ImagePrintDocumentAdapter(WebViewActivity.this,listBimapCopy), null);
            v1.setDrawingCacheEnabled(false);
            String mPath = Environment.getExternalStorageDirectory().toString() + "/screenshot.jpg";
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public void onBackPressed() {
//        if (myWebView.canGoBack()) {
//            myWebView.goBack();
//        } else {
//            super.onBackPressed();
//        }
//    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (myWebView.canGoBack()) {
                    myWebView.goBack();
                } else {
                    super.onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}