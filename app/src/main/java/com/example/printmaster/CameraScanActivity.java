package com.example.printmaster;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class CameraScanActivity extends AppCompatActivity {
    private static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final String TAG = "CameraScanActivity";
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private int flashMode = ImageCapture.FLASH_MODE_OFF;
    private ImageCapture imageCapture = null;
    TextView toolbar_auto,toolbar_normal;
    private PreviewView viewFinder;
    private ExecutorService cameraExecutor;
    ImageView takePhotoImg,flashImg,turnCameraImg;
    private int lensFacing = CameraSelector.LENS_FACING_BACK;
    private ProcessCameraProvider cameraProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_scan);
        mapping();
        checkCameraPermission();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#AAB2FF"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) CameraScanActivity.this;
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setTitle("Compatible List");
            toolbar.setTitleTextColor(Color.parseColor("#F7F7F7"));
        }

        toolbar_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar_auto.setVisibility(View.GONE);
                toolbar_normal.setVisibility(View.VISIBLE);
                takePhotoImg.setVisibility(View.GONE);
                new CountDownTimer(2000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        // Mỗi lần đếm, bạn có thể thực hiện các hành động cần thiết
                        // Ví dụ: cập nhật giao diện, hiển thị thời gian còn lại, v.v.
                        long secondsRemaining = millisUntilFinished / 1000;
                        Log.d("Countdown", "Seconds remaining: " + secondsRemaining);
                    }

                    public void onFinish() {
                        // Khi đếm ngược hoàn thành, bạn có thể thực hiện các hành động sau cùng
                        Log.d("Countdown", "Countdown finished!");
                        takePicture();
                    }
                }.start();

            }
        });
        toolbar_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar_auto.setVisibility(View.VISIBLE);
                toolbar_normal.setVisibility(View.GONE);
                takePhotoImg.setVisibility(View.VISIBLE);
            }
        });
        takePhotoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
//                Intent intent = new Intent(getContext(), FilterActivity.class);
//                startActivity(intent);
            }
        });
        flashImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flashMode == ImageCapture.FLASH_MODE_OFF) {
                    flashMode = ImageCapture.FLASH_MODE_ON;
//                    flashButton.setText("Flash On");
                } else if (flashMode == ImageCapture.FLASH_MODE_ON) {
                    flashMode = ImageCapture.FLASH_MODE_AUTO;
//                    flashButton.setText("Flash Auto");
                } else {
                    flashMode = ImageCapture.FLASH_MODE_OFF;
//                    flashButton.setText("Flash Off");
                }
                // If the image capture use case has been initialized, update the flash mode
                if (imageCapture != null) {
                    imageCapture.setFlashMode(flashMode);
                }
            }
        });
//        turnCameraImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchCamera();
//            }
//        });

    }
    private void switchCamera() {
        if (lensFacing == CameraSelector.LENS_FACING_BACK) {
            lensFacing = CameraSelector.LENS_FACING_FRONT;
        } else {
            lensFacing = CameraSelector.LENS_FACING_BACK;
        }
        startCamera(); // restart the camera with the new lensFacing value
    }
    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(CameraScanActivity.this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled as this future should not be cancelled
            }
        }, ContextCompat.getMainExecutor(CameraScanActivity.this));

    }
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        if (imageCapture != null && viewFinder != null) {
            cameraProvider.unbindAll();
        }
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();

        imageCapture = new ImageCapture.Builder()
                .setFlashMode(flashMode).build();

        preview.setSurfaceProvider(viewFinder.getSurfaceProvider());

        cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview, imageCapture);
    }

    private void takePicture() {
        File photoFile = new File(
                getOutputDirectory(),
                new SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg");

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(CameraScanActivity.this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Uri savedUri = Uri.fromFile(photoFile);
                        String msg = "Photo capture succeeded: " + savedUri;

// Không giảm kích thước ảnh

// Lấy kích thước của ảnh gốc
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);
                        int imageWidth = options.outWidth;
                        int imageHeight = options.outHeight;
                        Log.d(TAG, "width: " + imageWidth + ", height:" + imageHeight);

                        Intent intent = new Intent(CameraScanActivity.this, DetailScanActivity.class);
                        intent.putExtra("image_uri", savedUri.toString());
                        intent.putExtra("activity", "photo");
                        intent.putExtra("width", imageWidth);
                        intent.putExtra("height", imageHeight);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.e("QuocDat", "Photo capture failed: " + exception.getMessage(), exception);
                    }
                });
    }
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startCamera();
        }
    }

    private Bitmap decodeSampledBitmapFromFile(String filePath, int maxImageSize) {
        // Đọc thông tin kích thước ảnh
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Tính toán giá trị inSampleSize để thu nhỏ ảnh
        options.inSampleSize = calculateInSampleSize(options, maxImageSize);

        // Đọc lại ảnh với inSampleSize đã tính toán
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int maxImageSize) {
        final int imageWidth = options.outWidth;
        final int imageHeight = options.outHeight;
        int inSampleSize = 1;

        if (imageWidth > maxImageSize || imageHeight > maxImageSize) {
            final int halfWidth = imageWidth / 4;
            final int halfHeight = imageHeight / 4;

            while ((halfWidth / inSampleSize) >= maxImageSize && (halfHeight / inSampleSize) >= maxImageSize) {
                inSampleSize *= 4;
            }
        }

        return inSampleSize;
    }

    private void saveBitmapToFile(Bitmap bitmap, File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getOutputDirectory() {
        File mediaDir = CameraScanActivity.this.getExternalMediaDirs()[0];
        if (mediaDir != null) {
            mediaDir = new File(mediaDir, getResources().getString(R.string.app_name));
            mediaDir.mkdirs();
        }
        return mediaDir != null ? mediaDir : CameraScanActivity.this.getFilesDir();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CameraScanActivity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_CAMERA_PERMISSION) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startCamera();
//                Toast.makeText(getContext(), "a", Toast.LENGTH_SHORT).show();
//            } else {
//                Log.e(TAG, "Camera permission denied");
//                getActivity().finish();
//            }
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(CameraScanActivity.this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void mapping() {
        takePhotoImg=findViewById(R.id.capture_button);
        flashImg=findViewById(R.id.flash_button);
        toolbar_auto=findViewById(R.id.toolbar_auto);
        toolbar_normal=findViewById(R.id.toolbar_normal);
//        turnCameraImg=findViewById(R.id.turnCameraImg);

        viewFinder =findViewById(R.id.viewFinder);
    }


}
