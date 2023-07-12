package com.example.printmaster.fragment.homefragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.printmaster.AlbumActivity;
import com.example.printmaster.DetailLableActivity;
import com.example.printmaster.Detail_Sheet_Activity;
import com.example.printmaster.FilterActivity;
import com.example.printmaster.R;
import com.example.printmaster.adapter.AlbumAdapter;
import com.example.printmaster.adapter.ImageLibraryAdapter;
import com.example.printmaster.adapter.ImageLibrarySheetAdapter;
import com.example.printmaster.data.OnImageSavedListener;
import com.example.printmaster.data.OnItemClickListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class LibrarySheetFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    View view;
    PhotoView detailImg;
    ImageView defaultImg;
    String item_curren;
    ArrayList<String> images;
    ArrayList<String> albums;
    TextView toolbar_title, toolbar_nextTv;
    ImageLibraryAdapter imageAdapter;
    ImageLibrarySheetAdapter imageLibrarySheetAdapter;
    String path;
    String activity_source;
    int count_click = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_library_sheet, container, false);
        SharedPreferences sharedPreferences_info = getActivity().getSharedPreferences("path_click", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences_info.edit();
        editor.remove("path");
        editor.apply();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("activity", Context.MODE_PRIVATE);
        activity_source = sharedPreferences.getString("activity", "");
        // số lượng item được click
        SharedPreferences sharedPreferences_count = getActivity().getSharedPreferences("info_sheet", Context.MODE_PRIVATE);
        int itemCount = sharedPreferences_count.getInt("itemCount", 0);

        toolbar_title = view.findViewById(R.id.toolbar_title);
        toolbar_nextTv = view.findViewById(R.id.toolbar_nextTv);
        detailImg = view.findViewById(R.id.detailImg);
        defaultImg = view.findViewById(R.id.defaultImg);
        images = getAllImages(getContext());

//        //zoom in, zoom out image
//        ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
//        detailImg.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                scaleGestureDetector.onTouchEvent(motionEvent);
//                return true;
//            }
//        });
        // Zoom in
        detailImg.setScale(1.5f); // Tỷ lệ thu phóng 2 lần kích thước gốc

        // Zoom out
        detailImg.setScale(1.5f); // Tỷ lệ thu phóng 1/2 kích thước gốc

        // Đặt giới hạn tỷ lệ thu phóng
        detailImg.setMaximumScale(5.0f); // Giới hạn tỷ lệ thu phóng tối đa là 5 lần kích thước gốc
        detailImg.setMinimumScale(0.5f); // Giới hạn tỷ lệ thu phóng tối thiểu là 1/2 kích thước gốc


//        Toast.makeText(getContext(), ""+images.get(0), Toast.LENGTH_SHORT).show();
//        Glide.with(getContext()).load(images.get(0)).into(detailImg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#AAB2FF"));
        }
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);


        defaultImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float initialScale = 1.0f;
                detailImg.setScale(initialScale);
            }
        });
        toolbar_nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_source.equals("sheet")) {

                    if (count_click == itemCount) {
                        Intent intent_sheet = new Intent(getActivity(), Detail_Sheet_Activity.class);
                        startActivity(intent_sheet);
                    }

                } else {
                    //Lấy đường dẫn ảnh từ
                    detailImg.setDrawingCacheEnabled(true);
                    // Tạo bản sao của Bitmap từ hình ảnh đã vẽ trên ImageView
                    Bitmap bitmap = Bitmap.createBitmap(detailImg.getDrawingCache());
                    // Tắt chế độ cache
                    detailImg.setDrawingCacheEnabled(false);
                    String path_img = saveBitmap(getContext(), bitmap);
                    //
                    SharedPreferences sharedPreferences_info = getActivity().getSharedPreferences("path_click", Context.MODE_PRIVATE);
                    path = sharedPreferences_info.getString("path", "");
                    Log.d("QuocDat_1", "onItemClick: " + path);
                    Intent intent = new Intent(getActivity(), FilterActivity.class);
                    if (path.isEmpty()) {
                        int originalWidth = bitmap.getWidth();
                        int originalHeight = bitmap.getHeight();
                        intent.putExtra("image_uri", path_img); // chưa load được dữ liê
                        intent.putExtra("activity", "library");
                        intent.putExtra("width", originalWidth-1);
                        intent.putExtra("height", originalHeight);

                        Log.d("QuocDat", "onCreate: "+originalHeight+",width: "+originalWidth);
                    } else {
                        int originalWidth = bitmap.getWidth();
                        int originalHeight = bitmap.getHeight();
                        intent.putExtra("image_uri", path_img);
                        intent.putExtra("activity", "library");
                        intent.putExtra("width", originalWidth-1);
                        intent.putExtra("height", originalHeight);

                        Log.d("QuocDat", "onCreate: "+originalHeight+",width: "+originalWidth);
                    }
                    startActivity(intent);
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), com.example.printmaster.fragment.FragmentActivity.class);
                startActivity(intent);
            }
        });


        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setTitle("Compatible List");
            toolbar.setTitleTextColor(Color.parseColor("#F7F7F7"));
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // READ_EXTERNAL_STORAGE permission has not been granted.
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            // READ_EXTERNAL_STORAGE permission is already been granted.
//            GridView gridView = (GridView) view.findViewById(R.id.allImageGr);
//            ImageLibraryAdapter imageAdapter = new ImageLibraryAdapter(getContext(), images);
//            gridView.setAdapter(imageAdapter);
//            imageAdapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(String item) {
//                    // Xử lý sự kiện click vào item ở đây
//                    Glide.with(getContext()).load(item).into(detailImg);
//
//                }
//            });
            new LoadImagesTask().execute();
        }
        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlbumActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted
////                    GridView gridView = (GridView) view.findViewById(R.id.allImageGr);
////                    ImageLibraryAdapter imageAdapter = new ImageLibraryAdapter(getContext(), images);
////                    gridView.setAdapter(imageAdapter);
////                    imageAdapter.setOnItemClickListener(new OnItemClickListener() {
////                        @Override
////                        public void onItemClick(String item) {
////                            // Xử lý sự kiện click vào item ở đây
////                            Glide.with(getContext()).load(item).into(detailImg);
////
////                        }
////                    });
//                    new LoadImagesTask().execute();
//                } else {
//                    // permission denied
//                    // Disable the functionality that depends on this permission.
//                }
//                return;
//            }
//        }
//    }

    public ArrayList<String> getImagesFromAlbum(Context context, String albumName) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ArrayList<String> imagePaths = new ArrayList<String>();
        String[] projection = {MediaStore.MediaColumns.DATA};

        // Here we set the selection condition
        String selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[]{albumName};

        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);

        int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while (cursor.moveToNext()) {
            String absolutePathOfImage = cursor.getString(columnIndexData);
            imagePaths.add(absolutePathOfImage);
        }

        cursor.close();

        return imagePaths;
    }


    public ArrayList<String> getAllImages(Context context) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ArrayList<String> imagePaths = new ArrayList<>();

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC"; // Sắp xếp theo ngày chụp giảm dần

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, sortOrder);

        if (cursor != null) {
            int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            while (cursor.moveToNext()) {
                String absolutePathOfImage = cursor.getString(column_index_data);
                imagePaths.add(absolutePathOfImage);
            }
            cursor.close();
        }

        return imagePaths;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getContext(), FragmentActivity.class);
                getContext().startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

            // Tạo một bitmap mới với kích thước gốc
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, originalWidth, originalHeight, false);
            // Tạo một Canvas để vẽ bitmap mới
            Canvas canvas = new Canvas(scaledBitmap);

            // Thiết lập màu nền trắng cho Canvas
            canvas.drawColor(Color.WHITE);

            // Vẽ ảnh gốc lên Canvas
            canvas.drawBitmap(bitmap, 0, 0, null);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static ArrayList<String> getAlbums(Context context) {
        ArrayList<String> albums = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                if (!albums.contains(album)) {
                    albums.add(album);
                }
            }
            cursor.close();
        }

        return albums;
    }

    private class LoadImagesTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            return getAllImages(getContext());
        }

        @Override
        protected void onPostExecute(ArrayList<String> images) {
            SharedPreferences sharedPref = getContext().getSharedPreferences("NameAlbum", Context.MODE_PRIVATE);

            String name = sharedPref.getString("name", "");
            if (activity_source.equals("sheet")) {
                if (name.isEmpty()||name==null) {
                    // getImagesFromAlbum(getContext(),"Camera")


//            AlbumAdapter albumAdapter=new AlbumAdapter(getContext(),getAlbums(getContext()));
//            albumAdapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(String item) {
//                    Toast.makeText(getContext(), ""+item, Toast.LENGTH_SHORT).show();
//                    Glide.with(getContext()).load(getImagesFromAlbum(getContext(),item).get(0)).into(detailImg);
//                }
//            });

                    imageLibrarySheetAdapter = new ImageLibrarySheetAdapter(getContext(), images);
                    GridView gridView = (GridView) view.findViewById(R.id.allImageGr);
                    gridView.setAdapter(imageLibrarySheetAdapter);
                    imageLibrarySheetAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(String item, int count) {
//                            Toast.makeText(getContext(), ""+count, Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences_lable = getActivity().getSharedPreferences("path_click", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_lable = sharedPreferences_lable.edit();
                            editor_lable.putString("path", item);
                            editor_lable.apply();
                            Glide.with(getContext()).load(item).into(detailImg);
                            count_click = count;
                            ScaleListener scaleListener = new ScaleListener();
                            scaleListener.resetScale();
                        }
                    });
                    if (!images.isEmpty()) {
                        Glide.with(getContext()).load(images.get(0)).into(detailImg);
                    }
                } else {
                    ImageLibrarySheetAdapter imageLibrarySheetAdapter1 = new ImageLibrarySheetAdapter(getContext(), getImagesFromAlbum(getContext(), name));
                    GridView gridView = (GridView) view.findViewById(R.id.allImageGr);
                    gridView.setAdapter(imageLibrarySheetAdapter1);
                    imageLibrarySheetAdapter1.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(String item, int count) {
                            Glide.with(getContext()).load(item).into(detailImg);
                            count_click = count;
//                            Toast.makeText(getContext(), "" + count, Toast.LENGTH_SHORT).show();
                            ScaleListener scaleListener = new ScaleListener();
                            scaleListener.resetScale();
                        }
                    });
                    if (!images.isEmpty()) {
                        Glide.with(getContext()).load(getImagesFromAlbum(getContext(), name).get(0)).into(detailImg);
                    }

                }
            } else {
                if (name.isEmpty()) {
                    // getImagesFromAlbum(getContext(),"Camera")


//            AlbumAdapter albumAdapter=new AlbumAdapter(getContext(),getAlbums(getContext()));
//            albumAdapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(String item) {
//                    Toast.makeText(getContext(), ""+item, Toast.LENGTH_SHORT).show();
//                    Glide.with(getContext()).load(getImagesFromAlbum(getContext(),item).get(0)).into(detailImg);
//                }
//            });

                    imageAdapter = new ImageLibraryAdapter(getContext(), images);
                    GridView gridView = (GridView) view.findViewById(R.id.allImageGr);
                    gridView.setAdapter(imageAdapter);
                    imageAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(String item, int count) {
                            SharedPreferences sharedPreferences_lable = getActivity().getSharedPreferences("path_click", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_lable = sharedPreferences_lable.edit();
                            editor_lable.putString("path", item);
                            editor_lable.apply();
                            Glide.with(getContext()).load(item).into(detailImg);
                            ScaleListener scaleListener = new ScaleListener();
                            scaleListener.resetScale();
                        }
                    });
                    if (!images.isEmpty()) {
                        Glide.with(getContext()).load(images.get(0)).into(detailImg);
                    }
                } else {
                    ImageLibraryAdapter imageAdapter = new ImageLibraryAdapter(getContext(), getImagesFromAlbum(getContext(), name));
                    GridView gridView = (GridView) view.findViewById(R.id.allImageGr);
                    gridView.setAdapter(imageAdapter);
                    imageAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(String item, int count) {
                            Glide.with(getContext()).load(item).into(detailImg);
                            ScaleListener scaleListener = new ScaleListener();
                            scaleListener.resetScale();
                        }
                    });
                    if (!images.isEmpty()) {
                        Glide.with(getContext()).load(getImagesFromAlbum(getContext(), name).get(0)).into(detailImg);
                    }
                }
            }

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sharedPref = getContext().getSharedPreferences("NameAlbum", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                images = getAllImages(getContext());
                new LoadImagesTask().execute();
            } else {
                getActivity().finish();
            }
        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float initialScaleFactor = 1.0f;
        private float scaleFactor = 1.0f;
        private float maxScaleFactor = 5.0f;
        private float minScaleFactor = 0.1f;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float previousScaleFactor = scaleFactor;
            scaleFactor *= detector.getScaleFactor();

            // Giới hạn tỷ lệ thu phóng trong khoảng minScaleFactor và maxScaleFactor
            scaleFactor = Math.max(minScaleFactor, Math.min(scaleFactor, maxScaleFactor));

            if (previousScaleFactor != scaleFactor) {
                detailImg.setScaleX(scaleFactor);
                detailImg.setScaleY(scaleFactor);
            }

            return true;
        }

        public void resetScale() {
            scaleFactor = initialScaleFactor;
            detailImg.setScaleX(scaleFactor);
            detailImg.setScaleY(scaleFactor);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initialScaleFactor = scaleFactor;
            return true;
        }
    }
}