package com.example.printmaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.impl.ImageInfoProcessor;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.printmaster.adapter.FilterAdapter;
import com.example.printmaster.adapter.ImageLibraryAdapter;
import com.example.printmaster.adapter.ImagePrintDocumentAdapter;
import com.example.printmaster.data.FilteredImagesCallback;
import com.example.printmaster.data.OnItemClickListener;
import com.example.printmaster.data.OnItemFilterClickListener;
import com.example.printmaster.filter.Filter1977;
import com.example.printmaster.filter.FilterStrategy;
import com.example.printmaster.filter.Filters;
import com.example.printmaster.filter.LinearFilter;
import com.example.printmaster.filter.MonoFilter;
import com.example.printmaster.filter.NashvilleFilter;
import com.example.printmaster.filter.NoirFilter;
import com.example.printmaster.filter.ProcessFilter;
import com.example.printmaster.filter.SepiaFilter;
import com.example.printmaster.filter.ToasterFilter;
import com.example.printmaster.filter.TonalFilter;
import com.example.printmaster.filter.ToneFilter;
import com.example.printmaster.filter.TransferFilter;
import com.example.printmaster.filter.XRayFilterStrategy;
import com.example.printmaster.fragment.homefragment.LibraryFragment;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.geometry.Point;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.ImageProcessor;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.io.IOException;

public class FilterActivity extends AppCompatActivity {
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    ImageView detailImg, test;
    int addedItemCount = 0;
    RecyclerView allImageRcv;
    private ArrayList<String> filteredImagePathList;
    private ArrayList<Bitmap> allBitmap;
    TextView toolbar_nextTv;
    private Bitmap originalBitmap;
    private int width;
    private int height;
    FilterAdapter imageAdapter;
    Bitmap bitmapCopy;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mapping();
        allImageRcv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("image_uri");
//        String imageUriString = "path: /storage/emulated/0/DCIM/Screenshots/Screenshot_2023-06-09-15-24-29-00.jpg";
        String activity_check = intent.getStringExtra("activity");
        width = intent.getIntExtra("width", 0);
        height = intent.getIntExtra("height", 0);
        position = intent.getIntExtra("position", 0);
        Log.d("Test_10", "width: " + width + ", height: " + height + ", path: " + imageUriString);
        SharedPreferences sharedPreferences = getSharedPreferences("activity", Context.MODE_PRIVATE);
        String activity_source = sharedPreferences.getString("activity", "");
//        try {
//            Bitmap bitmap = getBitmap(activity_check,imageUriString);
//            Filter clarendonFilter = SampleFilters.getLimeStutterFilter();
//            Filter choromeFilter = SampleFilters.getNightWhisperFilter();
//            Filter fadeFilter = SampleFilters.getStarLitFilter();
//            Filter instantFilter = SampleFilters.getAweStruckVibeFilter();
//
//            Filters filters= new Filters();
//            FilterStrategy xrayStrategy = new XRayFilterStrategy();
//            NashvilleFilter nashvilleFilter= new NashvilleFilter();
//            ToasterFilter toasterFilter= new ToasterFilter();
//            Filter1977 Filter1977= new Filter1977();
//            MonoFilter monoFilter= new MonoFilter();
//            NoirFilter noirFilter= new NoirFilter();
//            ProcessFilter processFilter= new ProcessFilter();
//            TonalFilter tonalFilter= new TonalFilter();
//            TransferFilter transferFilter= new TransferFilter();
//            ToneFilter toneFilter= new ToneFilter(255, 0, 0);
//            LinearFilter lienarFilter= new LinearFilter(1.1f);
//            SepiaFilter sepiaFilter= new SepiaFilter();
//            Bitmap outputImage_2 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),nashvilleFilter);
//            Bitmap outputImage_3 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),toasterFilter);
//            Bitmap outputImage_4 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),Filter1977);
//            Bitmap outputImage_5 = clarendonFilter.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false));
//            Bitmap outputImage_6 = choromeFilter.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false));
//            Bitmap outputImage_7 = fadeFilter.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false));
//            Bitmap outputImage_8 = instantFilter.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false));
//            Bitmap outputImage_9 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),monoFilter);
//            Bitmap outputImage_10 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),noirFilter);
//            Bitmap outputImage_11 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),processFilter);
//            Bitmap outputImage_12 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),tonalFilter);
//            Bitmap outputImage_13 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),transferFilter);
//            Bitmap outputImage_14 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),toneFilter);
//            Bitmap outputImage_15 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),lienarFilter);
//            Bitmap outputImage_16 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),sepiaFilter);
//            Bitmap outputImage_17 = filters.processFilter(Bitmap.createScaledBitmap(bitmap, width, height, false),xrayStrategy);
//            allBitmap = new ArrayList<>();
//            allBitmap.add(bitmap);
//            allBitmap.add(outputImage_2);
//            allBitmap.add(outputImage_3);
//            allBitmap.add(outputImage_4);
//            allBitmap.add(outputImage_5);
//            allBitmap.add(outputImage_6);
//            allBitmap.add(outputImage_7);
//            allBitmap.add(outputImage_8);
//            allBitmap.add(outputImage_9);
//            allBitmap.add(outputImage_10);
//            allBitmap.add(outputImage_11);
//            allBitmap.add(outputImage_12);
//            allBitmap.add(outputImage_13);
//            allBitmap.add(outputImage_14);
//            allBitmap.add(outputImage_15);
//            allBitmap.add(outputImage_16);
//            allBitmap.add(outputImage_17);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            originalBitmap = getBitmap(activity_check, imageUriString);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        applyFiltersInBackground();


//        FilterAdapter imageAdapter = new FilterAdapter(FilterActivity.this, allBitmap);
//        allImageRcv.setAdapter(imageAdapter);
//
//                imageAdapter.setOnItemClickListener(new OnItemFilterClickListener() {
//                    @Override
//                    public void onItemClick(Bitmap item) {
//                        //Chuyển bitmap thành String
//                        Bitmap bitmap = item; // Đối tượng Bitmap cần chuyển đổi
//                        detailImg.setImageBitmap(item);
//                    }
//                });
        toolbar_nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_source.equals("lable")) {
                    Intent intent = new Intent(FilterActivity.this, DetailLableActivity.class);
                    String uri_bitmap;
                    if (bitmapCopy == null) {
                        uri_bitmap = imageUriString;
                    } else {
                        uri_bitmap = saveBitmap(FilterActivity.this, bitmapCopy);
                    }
                    intent.putExtra("image_uri", uri_bitmap);
                    startActivity(intent);
                } else if (activity_source.equals("post")) {
                    Intent intent = new Intent(FilterActivity.this, InfoSheetActivity.class);
                    String uri_bitmap;
                    if (bitmapCopy == null) {
                        uri_bitmap = imageUriString;
                    } else {
                        uri_bitmap = saveBitmap(FilterActivity.this, bitmapCopy);
                    }
                    intent.putExtra("image_uri", uri_bitmap);
                    intent.putExtra("activity", "post");
                    startActivity(intent);
                } else if (activity_source.equals("sheet")) {
                    Intent intent = new Intent(FilterActivity.this, Detail_Sheet_Activity.class);
                    String uri_bitmap;
                    if (bitmapCopy == null) {
                        uri_bitmap = imageUriString;
                    } else {
                        uri_bitmap = saveBitmap(FilterActivity.this, bitmapCopy);
                    }
                    intent.putExtra("image_uri", uri_bitmap);
                    intent.putExtra("position", position);
                    Log.d("Test_11", "position: " + uri_bitmap + ", listPath: " + 1);
                    startActivity(intent);
                } else {
                    detailImg.setDrawingCacheEnabled(true);
                    detailImg.buildDrawingCache();
                    List<Bitmap> listBitmap = new ArrayList<>();
                    Bitmap bitmap = Bitmap.createBitmap(detailImg.getDrawingCache());
                    Log.d("Test_5", "onClick: " + bitmap.getWidth());
                    listBitmap.add(bitmap);
                    detailImg.setDrawingCacheEnabled(false);
                    PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
                    String jobName = getString(R.string.app_name) + " Document";
                    printManager.print(jobName, new ImagePrintDocumentAdapter(FilterActivity.this, listBitmap), null);
                }
            }
        });
    }

    public String saveBitmap(Context context, Bitmap bitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir == null) {
            return null;
        }

        File imageFile = new File(storageDir, imageFileName);
        try {
            // Lấy kích thước gốc của ảnh
            int originalWidth = bitmap.getWidth();
            int originalHeight = bitmap.getHeight();
            Log.d("QuocDat_test", "widh và height: " + originalWidth + ", " + originalHeight);

            // Tạo một bitmap mới với kích thước gốc
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, originalWidth, originalHeight, false);

            // Chuyển đổi bitmap thành mutable bitmap
            Bitmap mutableBitmap = scaledBitmap.copy(Bitmap.Config.ARGB_8888, true);

            // Tạo một Canvas để vẽ bitmap mới
            Canvas canvas = new Canvas(mutableBitmap);

            // Thiết lập màu nền trắng cho Canvas
            canvas.drawColor(Color.WHITE);

            // Vẽ ảnh gốc lên Canvas
            canvas.drawBitmap(bitmap, 0, 0, null);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            mutableBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    Bitmap getBitmap(String activity, String path) throws IOException {
        Bitmap bitmap;
        if (activity.equals("library")) {
            bitmap = BitmapFactory.decodeFile(path);
            int originalWidth = bitmap.getWidth();
            int originalHeight = bitmap.getHeight();
            Log.d("QuocDat", "onCreate: " + originalHeight + ",width: " + originalWidth);
            Glide.with(FilterActivity.this).load(path).into(detailImg);
        } else {
            Uri imageUri = Uri.parse(path);
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            Glide.with(FilterActivity.this).load(imageUri).into(detailImg);
        }
        return bitmap;
    }

    private void mapping() {
        detailImg = findViewById(R.id.detailImg);
        allImageRcv = findViewById(R.id.allImageRcv);
        toolbar_nextTv = findViewById(R.id.toolbar_nextTv);
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

    private void applyFiltersInBackground() {
        AsyncTask<Void, Bitmap, Void> task = new AsyncTask<Void, Bitmap, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                Intent intent = getIntent();
                String imageUriString = intent.getStringExtra("image_uri");
                String activity_check = intent.getStringExtra("activity");
                width = intent.getIntExtra("width", 0);
                height = intent.getIntExtra("height", 0);
                SharedPreferences sharedPreferences = getSharedPreferences("activity", Context.MODE_PRIVATE);
                String activity_source = sharedPreferences.getString("activity", "");
                ArrayList<Bitmap> processedBitmaps = new ArrayList<>();

                imageAdapter = new FilterAdapter(FilterActivity.this, processedBitmaps);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        allImageRcv.setAdapter(imageAdapter);
                    }
                });

                Filter clarendonFilter = SampleFilters.getLimeStutterFilter();
                Filter choromeFilter = SampleFilters.getNightWhisperFilter();
                Filter fadeFilter = SampleFilters.getStarLitFilter();
                Filter instantFilter = SampleFilters.getAweStruckVibeFilter();

                // Áp dụng các bộ lọc hoặc xử lý ảnh khác tại đây
                // Ví dụ:
                Filters filters = new Filters();
                FilterStrategy xrayStrategy = new XRayFilterStrategy();
                NashvilleFilter nashvilleFilter = new NashvilleFilter();
                ToasterFilter toasterFilter = new ToasterFilter();
                Filter1977 filter1977 = new Filter1977();
                MonoFilter monoFilter = new MonoFilter();
                NoirFilter noirFilter = new NoirFilter();
                ProcessFilter processFilter = new ProcessFilter();
                TonalFilter tonalFilter = new TonalFilter();
                TransferFilter transferFilter = new TransferFilter();
                ToneFilter toneFilter = new ToneFilter(255, 0, 0);
                LinearFilter linearFilter = new LinearFilter(1.1f);
                SepiaFilter sepiaFilter = new SepiaFilter();

                processedBitmaps.add(originalBitmap);
                publishProgress(originalBitmap);

                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), nashvilleFilter));
                publishProgress(processedBitmaps.get(1));

                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), toasterFilter));
                publishProgress(processedBitmaps.get(2));


                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), filter1977));
                publishProgress(processedBitmaps.get(3));

                processedBitmaps.add(clarendonFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                publishProgress(processedBitmaps.get(4));

                processedBitmaps.add(choromeFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                publishProgress(processedBitmaps.get(5));

                processedBitmaps.add(fadeFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                publishProgress(processedBitmaps.get(6));

                processedBitmaps.add(instantFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                publishProgress(processedBitmaps.get(7));

                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), monoFilter));
                publishProgress(processedBitmaps.get(8));

                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), noirFilter));
                publishProgress(processedBitmaps.get(9));

                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), processFilter));
                publishProgress(processedBitmaps.get(10));

                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), tonalFilter));
                publishProgress(processedBitmaps.get(11));

                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), transferFilter));
                publishProgress(processedBitmaps.get(12));

                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), toneFilter));
                publishProgress(processedBitmaps.get(13));

                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), linearFilter));
                publishProgress(processedBitmaps.get(14));

                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), sepiaFilter));
                publishProgress(processedBitmaps.get(15));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), xrayStrategy));
                publishProgress(processedBitmaps.get(16));

                return null;
            }

            @Override
            protected void onProgressUpdate(Bitmap... bitmaps) {
                Log.d("QuocDat_test", "doInBackground: " + addedItemCount);
                if (imageAdapter != null && addedItemCount < 17) {
                    Bitmap processedBitmap = bitmaps[0];
                    imageAdapter.addBitmap(processedBitmap);
                    addedItemCount++;
                }
                if (addedItemCount == 17) {
                    // Đã đạt tới giới hạn 17 item, hủy AsyncTask
                    cancel(true);
                }
                imageAdapter.setOnItemClickListener(new OnItemFilterClickListener() {
                    @Override
                    public void onItemClick(Bitmap item) {
                        //Chuyển bitmap thành String
                        Bitmap bitmap = item; // Đối tượng Bitmap cần chuyển đổi
                        bitmapCopy = item;
                        Log.d("Test_6", "onCreate: " + saveBitmap(FilterActivity.this, bitmapCopy));

                        detailImg.setImageBitmap(item);
                    }
                });

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // Hiển thị danh sách các ảnh đã được xử lý
//                imageAdapter = new FilterAdapter(FilterActivity.this, allBitmap);
//                allImageRcv.setAdapter(imageAdapter);
//
//                imageAdapter.setOnItemClickListener(new OnItemFilterClickListener() {
//                    @Override
//                    public void onItemClick(Bitmap item) {
//                        //Chuyển bitmap thành String
//                        Bitmap bitmap = item; // Đối tượng Bitmap cần chuyển đổi
//                        detailImg.setImageBitmap(item);
//                    }
//                });
                if (addedItemCount < 17) {
                    imageAdapter.notifyDataSetChanged();
                }

            }
        };

        task.execute();
    }

    private void applyFiltersInBackgroundCopyNew() {
        ExecutorService executorService = Executors.newFixedThreadPool(8);

        Callable<ArrayList<Bitmap>> task = new Callable<ArrayList<Bitmap>>() {
            @Override
            public ArrayList<Bitmap> call() throws Exception {
                // Các bước tải và xử lý ảnh ở đây
                // ...
                Intent intent = getIntent();
                String imageUriString = intent.getStringExtra("image_uri");
                String activity_check = intent.getStringExtra("activity");
                width = intent.getIntExtra("width", 0);
                height = intent.getIntExtra("height", 0);
                SharedPreferences sharedPreferences = getSharedPreferences("activity", Context.MODE_PRIVATE);
                String activity_source = sharedPreferences.getString("activity", "");
                ArrayList<Bitmap> processedBitmaps = new ArrayList<>();

                Filter clarendonFilter = SampleFilters.getLimeStutterFilter();
                Filter choromeFilter = SampleFilters.getNightWhisperFilter();
                Filter fadeFilter = SampleFilters.getStarLitFilter();
                Filter instantFilter = SampleFilters.getAweStruckVibeFilter();

                // Áp dụng các bộ lọc hoặc xử lý ảnh khác tại đây
                // Ví dụ:
                Filters filters = new Filters();
                FilterStrategy xrayStrategy = new XRayFilterStrategy();
                NashvilleFilter nashvilleFilter = new NashvilleFilter();
                ToasterFilter toasterFilter = new ToasterFilter();
                Filter1977 filter1977 = new Filter1977();
                MonoFilter monoFilter = new MonoFilter();
                NoirFilter noirFilter = new NoirFilter();
                ProcessFilter processFilter = new ProcessFilter();
                TonalFilter tonalFilter = new TonalFilter();
                TransferFilter transferFilter = new TransferFilter();
                ToneFilter toneFilter = new ToneFilter(255, 0, 0);
                LinearFilter linearFilter = new LinearFilter(1.1f);
                SepiaFilter sepiaFilter = new SepiaFilter();

                processedBitmaps.add(originalBitmap);
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), nashvilleFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), toasterFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), filter1977));
                processedBitmaps.add(clarendonFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                processedBitmaps.add(choromeFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                processedBitmaps.add(fadeFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                processedBitmaps.add(instantFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), monoFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), noirFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), processFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), tonalFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), transferFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), toneFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), linearFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), sepiaFilter));

                return processedBitmaps;
            }
        };

        Future<ArrayList<Bitmap>> future = executorService.submit(task);

        try {
            ArrayList<Bitmap> processedBitmaps = future.get();

            // Hiển thị danh sách các ảnh đã được xử lý
            FilterAdapter imageAdapter = new FilterAdapter(FilterActivity.this, processedBitmaps);
            allImageRcv.setAdapter(imageAdapter);

            imageAdapter.setOnItemClickListener(new OnItemFilterClickListener() {
                @Override
                public void onItemClick(Bitmap item) {
                    //Chuyển bitmap thành String
                    Bitmap bitmap = item; // Đối tượng Bitmap cần chuyển đổi
                    detailImg.setImageBitmap(item);
                }
            });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Tắt ExecutorService sau khi hoàn thành
        executorService.shutdown();
    }

    private void applyFiltersInBackgroundCopy() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        AsyncTask<Void, Void, ArrayList<Bitmap>> task = new AsyncTask<Void, Void, ArrayList<Bitmap>>() {
            @Override
            protected ArrayList<Bitmap> doInBackground(Void... voids) {
                Intent intent = getIntent();
                String imageUriString = intent.getStringExtra("image_uri");
                String activity_check = intent.getStringExtra("activity");
                width = intent.getIntExtra("width", 0);
                height = intent.getIntExtra("height", 0);
                SharedPreferences sharedPreferences = getSharedPreferences("activity", Context.MODE_PRIVATE);
                String activity_source = sharedPreferences.getString("activity", "");
                ArrayList<Bitmap> processedBitmaps = new ArrayList<>();

                Filter clarendonFilter = SampleFilters.getLimeStutterFilter();
                Filter choromeFilter = SampleFilters.getNightWhisperFilter();
                Filter fadeFilter = SampleFilters.getStarLitFilter();
                Filter instantFilter = SampleFilters.getAweStruckVibeFilter();

                // Áp dụng các bộ lọc hoặc xử lý ảnh khác tại đây
                // Ví dụ:
                Filters filters = new Filters();
                FilterStrategy xrayStrategy = new XRayFilterStrategy();
                NashvilleFilter nashvilleFilter = new NashvilleFilter();
                ToasterFilter toasterFilter = new ToasterFilter();
                Filter1977 filter1977 = new Filter1977();
                MonoFilter monoFilter = new MonoFilter();
                NoirFilter noirFilter = new NoirFilter();
                ProcessFilter processFilter = new ProcessFilter();
                TonalFilter tonalFilter = new TonalFilter();
                TransferFilter transferFilter = new TransferFilter();
                ToneFilter toneFilter = new ToneFilter(255, 0, 0);
                LinearFilter linearFilter = new LinearFilter(1.1f);
                SepiaFilter sepiaFilter = new SepiaFilter();

                processedBitmaps.add(originalBitmap);
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), nashvilleFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), toasterFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), filter1977));
                processedBitmaps.add(clarendonFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                processedBitmaps.add(choromeFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                processedBitmaps.add(fadeFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                processedBitmaps.add(instantFilter.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false)));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), monoFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), noirFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), processFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), tonalFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), transferFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), toneFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), linearFilter));
                processedBitmaps.add(filters.processFilter(Bitmap.createScaledBitmap(originalBitmap, width, height, false), sepiaFilter));

                return processedBitmaps;
            }

            @Override
            protected void onPostExecute(ArrayList<Bitmap> processedBitmaps) {
                // Hiển thị danh sách các ảnh đã được xử lý
                FilterAdapter imageAdapter = new FilterAdapter(FilterActivity.this, processedBitmaps);
                allImageRcv.setAdapter(imageAdapter);

                imageAdapter.setOnItemClickListener(new OnItemFilterClickListener() {
                    @Override
                    public void onItemClick(Bitmap item) {
                        // Chuyển bitmap thành String
                        Bitmap bitmap = item; // Đối tượng Bitmap cần chuyển đổi
                        bitmapCopy = item;
                        detailImg.setImageBitmap(item);
                    }
                });

                // Tắt ExecutorService sau khi hoàn thành
                executorService.shutdown();
            }
        };

        task.executeOnExecutor(executorService);
    }


}