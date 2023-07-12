package com.example.printmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
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
import com.example.printmaster.adapter.ImagePrintDocumentAdapter;
import com.example.printmaster.adapter.ImageSheetAdapter;
import com.example.printmaster.adapter.PrintPDFAdapter;
import com.example.printmaster.data.OnImageSavedListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class InfoSheetActivity extends AppCompatActivity implements OnImageSavedListener {
    GridView sheetGrv;
    ImageView testImg;
    TextView toolbar_printTv;
    private ArrayList<String> cutImagePaths;
    String partImagePath;
    ImageView test;
    Rect rect;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    int intValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sheet);
        mapping();
        //covert kiểu date sang kieu int
        Calendar calendar = Calendar.getInstance();

        // Chuyển đổi thành kiểu int và kết hợp với số ngẫu nhiên
        long currentTimeMillis = calendar.getTimeInMillis();
        Random random = new Random();
        intValue = (int) (currentTimeMillis / 1000) + random.nextInt(100);

        SharedPreferences sharedPreferences_info = getSharedPreferences("info_sheet", Context.MODE_PRIVATE);
        int row = sharedPreferences_info.getInt("row", 0);
        int collum = sharedPreferences_info.getInt("collum", 0);
        int count_item = sharedPreferences_info.getInt("itemCount", 0);
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
//        // lấy gia trị đường dẫn cua activity post
//        Intent intent_post=getIntent();
//        String path_post=intent_post.getStringExtra("image_uri");
//        String activity_detail=intent_post.getStringExtra("activity");


        // lấy gia trị đường dẫn của activity sheet
        Intent intent = getIntent();
        String pathJson = intent.getStringExtra("image_uri");
        ArrayList<String> listPathJson = intent.getStringArrayListExtra("image_uri");
        String activity_detail = intent.getStringExtra("activity");
        if (activity_detail.equals("sheet")) {
//            JSONArray jsonArray = null;
//            try {
//                jsonArray = new JSONArray(pathJson);
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//
//            ArrayList<String> listPath = new ArrayList<>();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                String path = null;
//                try {
//                    path = jsonArray.getString(i);
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//                listPath.add(path);
                addImagesToGridView(row, collum, listPathJson, sheetGrv);
//            }
        } else {
            cutImagePaths = new ArrayList<>();
            // Tạo bitmap từ tấm ảnh gốc
            Bitmap originalBitmap = BitmapFactory.decodeFile(pathJson);
            if (collum == 0) {
                collum = 1;
            }
            if (row == 0) {
                row = 1;
            }
            // Kích thước của mỗi phần cắt
            int partWidth = originalBitmap.getWidth() / collum;
            int partHeight = originalBitmap.getHeight() / row;
            Log.d("QuocDat_size", "total_1: " + originalBitmap.getWidth() + ", width: " + partWidth + ", itemHeight: " + partHeight);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < collum; j++) {
                    // Tạo một Rect để xác định vùng cắt
                    rect = new Rect(j * partWidth, i * partHeight, (j + 1) * partWidth, (i + 1) * partHeight);

                    // Tạo bitmap con từ vùng cắt
                    Bitmap partBitmap = Bitmap.createBitmap(originalBitmap, rect.left, rect.top, rect.width(), rect.height());
                    Log.d("QuocDat_3", i + "onCreate: " + partBitmap);

//                    String directoryPath = getApplicationContext().getFilesDir().getAbsolutePath() + "/your_folder_name/";
//                    String partImagePath = directoryPath + "part_" + i + "_" + j + ".jpg";
                    // Lưu bitmap con vào file hoặc làm bất kỳ xử lý nào khác tùy theo yêu cầu của bạn
//                    String partImagePath = "path/to/your/part_" + i + "_" + j + ".jpg";
                    partImagePath = saveBitmap(InfoSheetActivity.this, partBitmap, this, i + "" + j);
                    Log.d("QuocDat_3", i + "onCreate: " + partImagePath);

                    // Thêm đường dẫn của bitmap con vào danh sách
                    cutImagePaths.add(partImagePath);

                }
            }
            for (int i = 0; i < cutImagePaths.size(); i++) {
                Log.d("QuocDat_3", i + "onCreate: " + cutImagePaths.get(i));
            }

            addImagesToGridView(row, collum, cutImagePaths, sheetGrv);
            Log.d("QuocDat", "onCreate: " + cutImagePaths.size());
        }
        toolbar_printTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasPermissions()) {
                    requestPermissions();
                } else {
                    // Tiến hành tạo tệp PDF
//                    createPDF();
                    Bitmap bitmap = Bitmap.createBitmap(sheetGrv.getWidth(), sheetGrv.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    sheetGrv.draw(canvas);
                    PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
                    String jobName = getString(R.string.app_name) + " Document";
                    printManager.print(jobName, new PrintPDFAdapter(InfoSheetActivity.this, bitmap), null);
                }
            }
        });

    }

    private boolean hasPermissions() {
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted) {
                // Tiến hành tạo tệp PDF
                createPDF();
            } else {
//                Toast.makeText(this, "Ứng dụng cần quyền truy cập bộ nhớ ngoài để tạo tệp PDF", Toast.LENGTH_SHORT).show();
                // Xử lý khi người dùng không cấp quyền
            }
        }
    }

    private void createPDF() {
        // Tạo tài liệu PDF
        PdfDocument document = new PdfDocument();

        // Thiết lập kích thước trang PDF
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1400, 1622, 1).create();

        // Tạo trang PDF
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Lấy kích thước của GridView
        int gridViewWidth = sheetGrv.getWidth();
        int gridViewHeight = sheetGrv.getHeight();

        // Lấy kích thước của trang PDF
        int pdfWidth = pageInfo.getPageWidth();
        int pdfHeight = pageInfo.getPageHeight();

        // Tính toán vị trí vẽ hình ảnh để căn giữa trang PDF
        int offsetX = (pdfWidth - gridViewWidth) / 2;
        int offsetY = (pdfHeight - gridViewHeight) / 2;

        // Di chuyển canvas đến vị trí vẽ hình ảnh
        canvas.translate(offsetX, offsetY);

        // Vẽ lưới GridView lên trang PDF
        sheetGrv.draw(canvas);

        // Kết thúc tài liệu PDF
        document.finishPage(page);

        // Lưu tệp PDF
        try {
            File pdfFile = new File(getFilesDir(), "GridView_+"+intValue+".pdf");
            document.writeTo(new FileOutputStream(pdfFile));

            // Mở tệp PDF
            openPDF(pdfFile);
        } catch (IOException e) {
            Log.e("MainActivity", "Lỗi: " + e.getMessage());
            Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            // Đóng tài liệu PDF
            document.close();
        }
    }


    private void openPDF(File pdfFile) {
        Uri pdfUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", pdfFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }


    public String saveBitmap(Context context, Bitmap bitmap, OnImageSavedListener listener, String text) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + text + ".jpg";

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (storageDir == null) {
            return null;
        }

        File imageFile = new File(storageDir, imageFileName);
        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener.onImageSaved(cutImagePaths);

        return null;
    }

    private void saveBitmapToFile(Bitmap bitmap, String filePath) {
        File directory = getApplicationContext().getCacheDir();
        File file = new File(directory, filePath);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String filePath_detail = file.getAbsolutePath();
        Log.d("QuocDat_3", "saveBitmapToFile: " + filePath_detail);
    }

    private void mapping() {
        sheetGrv = findViewById(R.id.sheetGrv);
        toolbar_printTv = findViewById(R.id.toolbar_printTv);
    }

    private void addImagesToGridView(int numRows, int numColumns, ArrayList<String> imagePaths, GridView gridView) {
        gridView.setNumColumns(numColumns);

        ImageSheetAdapter adapter = new ImageSheetAdapter(InfoSheetActivity.this, imagePaths, numRows, numColumns);
        gridView.setAdapter(adapter);

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

    @Override
    public void onImageSaved(ArrayList<String> imagePaths) {
        cutImagePaths.addAll(imagePaths);

        addImagesToGridView(2, 2, cutImagePaths, sheetGrv);
    }

}