package com.example.printmaster.fragment.homefragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.printmaster.FilterActivity;
import com.example.printmaster.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class PhotoFragment extends Fragment {
    private static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final String TAG = "CameraScanActivity";
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private int flashMode = ImageCapture.FLASH_MODE_OFF;
    private ImageCapture imageCapture = null;
    private PreviewView viewFinder;
    private ExecutorService cameraExecutor;
    ImageView takePhotoImg,flashImg,turnCameraImg;
    private int lensFacing = CameraSelector.LENS_FACING_BACK;
    private ProcessCameraProvider cameraProvider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_photo, container, false);
        takePhotoImg=view.findViewById(R.id.capture_button);
        flashImg=view.findViewById(R.id.flash_button);
        turnCameraImg=view.findViewById(R.id.turnCameraImg);

        viewFinder = view.findViewById(R.id.viewFinder);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#AAB2FF"));
        }
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setTitle("Compatible List");
            toolbar.setTitleTextColor(Color.parseColor("#F7F7F7"));
        }
        takePhotoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
                Intent intent = new Intent(getContext(), FilterActivity.class);
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
        turnCameraImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            startCamera();
        }
//        startCamera();
        return view;
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
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled as this future should not be cancelled
            }
        }, ContextCompat.getMainExecutor(getContext()));

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
                ContextCompat.getMainExecutor(getContext()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Uri savedUri = Uri.fromFile(photoFile);
                        String msg = "Photo capture succeeded: " + savedUri;

                        // Giảm kích thước ảnh
                        int maxImageSize = 500; // Kích thước ảnh tối đa (theo cạnh dài)
                        Bitmap scaledBitmap = decodeSampledBitmapFromFile(photoFile.getAbsolutePath(), maxImageSize);

                        // Lưu bitmap đã giảm kích thước vào file mới
                        File scaledPhotoFile = new File(getOutputDirectory(), "scaled_" + photoFile.getName());
                        saveBitmapToFile(scaledBitmap, scaledPhotoFile);

                        // Lấy kích thước của ảnh đã giảm kích thước
                        int imageWidth = scaledBitmap.getWidth();
                        int imageHeight = scaledBitmap.getHeight();
                        Log.d(TAG, "width: "+imageWidth+", height:"+imageHeight);

                        Intent intent = new Intent(getActivity(), FilterActivity.class);
                        intent.putExtra("image_uri", Uri.fromFile(scaledPhotoFile).toString());
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
        File mediaDir = getActivity().getExternalMediaDirs()[0];
        if (mediaDir != null) {
            mediaDir = new File(mediaDir, getResources().getString(R.string.app_name));
            mediaDir.mkdirs();
        }
        return mediaDir != null ? mediaDir : getActivity().getFilesDir();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
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
                Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}