package com.example.printmaster;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.printmaster.adapter.ImagePrintDocumentAdapter;
import com.yalantis.ucrop.UCrop;

//import org.opencv.android.OpenCVLoader;
//import org.opencv.android.Utils;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfPoint;
//import org.opencv.core.MatOfPoint2f;
//import org.opencv.core.Point;
//import org.opencv.core.Rect;
//import org.opencv.imgproc.Imgproc;

//import org.opencv.android.OpenCVLoader;
//import org.opencv.android.Utils;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfPoint;
//import org.opencv.core.MatOfPoint2f;
//import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DetailScanActivity extends AppCompatActivity {
    static ImageView resultImg;
    private static String TAG = "Test_1";
    private View corner1;
    private View corner2;
    private View corner3;
    private View corner4;
    TextView nextTv,printTv;

    private int corner1X, corner1Y;
    private int corner2X, corner2Y;
    private int corner3X, corner3Y;
    private int corner4X, corner4Y;
    private static final int REQUEST_CROP_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_scan);

        mapping();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#AAB2FF"));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) DetailScanActivity.this;
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.img_back);
            actionBar.setTitle("Compatible List");
            toolbar.setTitleTextColor(Color.parseColor("#F7F7F7"));
        }
        String imageUriString = getIntent().getStringExtra("image_uri");
        Glide.with(this)
                .load(imageUriString)
                .into(resultImg);
        Uri imageUri = Uri.parse(imageUriString);
        startCropActivity(imageUri);
//        InputStream inputStream = null;
//        try {
//            inputStream = getContentResolver().openInputStream(imageUri);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//        RectangleCorners rectangleCorners = detectRectangleCorners(bitmap);
//        setInitialCornerPositions(rectangleCorners);
        // Chuẩn bị ảnh và chuyển đổi thành dạng Mat
//        Mat src = new Mat();
//        Utils.bitmapToMat(bitmap, src);
//
//// Chuyển đổi ảnh sang ảnh đen trắng (grayscale)
//        Mat gray = new Mat();
//        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
//
//// Phát hiện contour
//        List<MatOfPoint> contours = new ArrayList<>();
//        Mat hierarchy = new Mat();
//        Imgproc.findContours(gray, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//
//// Tìm hình chữ nhật có diện tích lớn nhất
//        double maxArea = 0;
//        Rect boundingRect = null;
//        for (MatOfPoint contour : contours) {
//            double area = Imgproc.contourArea(contour);
//            if (area > maxArea) {
//                maxArea = area;
//                Log.d("test12", "tr: "+maxArea);
//                boundingRect = Imgproc.boundingRect(contour);
//            }
//        }
//
//// Xử lý tọa độ góc để tính toán vị trí và kích thước của các view
//        Point[] corners = getCornersFromBoundingRect(boundingRect);
////        moveViewsToCorners(corners);

//        corner1.setOnTouchListener(touchListener);
//        corner2.setOnTouchListener(touchListener);
//        corner3.setOnTouchListener(touchListener);
//        corner4.setOnTouchListener(touchListener);
        printTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultImg.setDrawingCacheEnabled(true);
                resultImg.buildDrawingCache();
                List<Bitmap> listBitmap = new ArrayList<>();
                Bitmap bitmap = Bitmap.createBitmap(resultImg.getDrawingCache());
                Log.d("Test_5", "onClick: " + bitmap.getWidth());
                listBitmap.add(bitmap);
                resultImg.setDrawingCacheEnabled(false);
                PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
                String jobName = getString(R.string.app_name) + " Document";
                printManager.print(jobName, new ImagePrintDocumentAdapter(DetailScanActivity.this, listBitmap), null);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CROP_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri croppedUri = UCrop.getOutput(data);
            if (croppedUri != null) {
                resultImg.setImageURI(croppedUri);
            } else {
                Toast.makeText(DetailScanActivity.this, "Failed to crop image.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCropActivity(Uri sourceUri) {
        String destinationFileName = "cropped_image.jpg";

        UCrop uCrop = UCrop.of(sourceUri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));

        uCrop.withAspectRatio(1, 1);
        uCrop.withMaxResultSize(500, 500);

        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(80);
        uCrop.withOptions(options);

        uCrop.start(this, REQUEST_CROP_IMAGE);
    }
//    private void setInitialCornerPositions(RectangleCorners rectangleCorners) {
//        // Lấy tọa độ của 4 góc
//        Point cornerx1 = rectangleCorners.corner1;
//        Point cornerx2 = rectangleCorners.corner2;
//        Point cornerx3 = rectangleCorners.corner3;
//        Point cornerx4 = rectangleCorners.corner4;
//
//        Log.d("test13", "1: "+cornerx1+",2:"+cornerx2+",3:"+cornerx3+",4:"+cornerx4);
//
//        // Set giá trị ban đầu cho 4 góc
//        if (cornerx1 != null) {
//            corner1X = (int) cornerx1.x-1000;
//            corner1Y = (int) cornerx1.y-1000;
//        }else{
//            corner1X = 120;
//            corner1Y = 400;
//        }
//        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) corner1.getLayoutParams();
//
//// Thiết lập giá trị margin cho cạnh bottom là 20dp
//        layoutParams.leftMargin = (corner1X);
//        layoutParams.topMargin = (corner1Y);
//
//// Áp dụng LayoutParams mới vào view
//        corner1.setLayoutParams(layoutParams);
////        moveCornerToInitialPosition(corner1,corner1X,corner1Y);
//        if (cornerx2 != null) {
//            corner2X = (int) cornerx2.x+corner1X-1000;
//            corner2Y = (int) cornerx2.y-1000;
//        }else{
//            corner2X = 500;
//            corner2Y = 410;
//        }
//        ViewGroup.MarginLayoutParams layoutParams2 = (ViewGroup.MarginLayoutParams) corner2.getLayoutParams();
//
//// Thiết lập giá trị margin cho cạnh bottom là 20dp
//        layoutParams2.leftMargin = (corner2X);
//        layoutParams2.topMargin = (corner2Y);
//
//// Áp dụng LayoutParams mới vào view
//        corner2.setLayoutParams(layoutParams2);
//        if (cornerx3 != null) {
//            corner3X = (int) cornerx3.x-1000;
//            corner3Y = (int) cornerx3.y+corner1Y-1000;
//        }else{
//            corner3X = 130;
//            corner3Y = 800;
//        }
//        ViewGroup.MarginLayoutParams layoutParams3 = (ViewGroup.MarginLayoutParams) corner3.getLayoutParams();
//
//// Thiết lập giá trị margin cho cạnh bottom là 20dp
//        layoutParams3.leftMargin =(corner3X);
//        layoutParams3.topMargin = (corner3Y);
//
//// Áp dụng LayoutParams mới vào view
//        corner3.setLayoutParams(layoutParams3);
//        if (cornerx4 != null) {
//            corner4X = (int) cornerx4.x+corner1X-1000;
//            corner4Y = (int) cornerx4.y+corner1Y-1000;
//        }else{
//            corner4X = 450;
//            corner4Y = 700;
//        }
//        ViewGroup.MarginLayoutParams layoutParams4 = (ViewGroup.MarginLayoutParams) corner4.getLayoutParams();
//
//// Thiết lập giá trị margin cho cạnh bottom là 20dp
//        layoutParams4.leftMargin = (corner4X);
//        layoutParams4.topMargin =(corner4Y);
//
//// Áp dụng LayoutParams mới vào view
//        corner4.setLayoutParams(layoutParams4);
//
//    }
//    private RectangleCorners detectRectangleCorners(Bitmap bitmap) {
//        // Chuyển đổi bitmap thành Mat
//        Mat imageMat = new Mat();
//        Utils.bitmapToMat(bitmap, imageMat);
//
//        // Xử lý hình ảnh để tìm hình chữ nhật
//        Mat grayMat = new Mat();
//        Imgproc.cvtColor(imageMat, grayMat, Imgproc.COLOR_BGR2GRAY);
//        Imgproc.Canny(grayMat, grayMat, 80, 100);
//
//        List<MatOfPoint> contours = new ArrayList<>();
//        Mat hierarchy = new Mat();
//        Imgproc.findContours(grayMat, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
//        hierarchy.release();
//
//        Rect maxRect = null;
//        double maxArea = 0;
//
//        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
//            MatOfPoint2f approxCurve = new MatOfPoint2f();
//            MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(contourIdx).toArray());
//            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.01;
//            Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);
//            MatOfPoint points = new MatOfPoint(approxCurve.toArray());
//            Rect rect = Imgproc.boundingRect(points);
//            double area = rect.width * rect.height;
//
//            if (area > maxArea && rect.height > 300 && rect.width > 300) {
//                maxArea = area;
//                maxRect = rect;
//            }
//        }
//
//        // Lưu trữ tọa độ của 4 góc hình chữ nhật
//        Point corner1 = null;
//        Point corner2 = null;
//        Point corner3 = null;
//        Point corner4 = null;
//
//        if (maxRect != null) {
//            corner1 = maxRect.tl();
//            corner2 = new Point(maxRect.br().x, maxRect.tl().y);
//            corner3 = maxRect.br();
//            corner4 = new Point(maxRect.tl().x, maxRect.br().y);
//        }
//
//        // Tạo đối tượng chứa tọa độ của 4 góc
//        RectangleCorners rectangleCorners = new RectangleCorners();
//        rectangleCorners.corner1 = corner1;
//        rectangleCorners.corner2 = corner2;
//        rectangleCorners.corner3 = corner3;
//        rectangleCorners.corner4 = corner4;
//
//        return rectangleCorners;
//    }

//    private class RectangleCorners {
//        Point corner1;
//        Point corner2;
//        Point corner3;
//        Point corner4;
//    }
//    private Point[] getCornersFromBoundingRect(Rect boundingRect) {
//        Point[] corners = new Point[4];
//        corners[0] = new Point(boundingRect.x, boundingRect.y); // Góc trên bên trái
//        corners[1] = new Point(boundingRect.x + boundingRect.width, boundingRect.y); // Góc trên bên phải
//        corners[2] = new Point(boundingRect.x, boundingRect.y + boundingRect.height); // Góc dưới bên trái
//        corners[3] = new Point(boundingRect.x + boundingRect.width, boundingRect.y + boundingRect.height); // Góc dưới bên phải
//        Log.d("test12", "tr: "+corners[0]);
//        Log.d("test12", "tl: "+corners[1]);
//        Log.d("test12", "bl: "+corners[3]);
//        Log.d("test12", "br: "+corners[2]);
//        return corners;
//    }
//
//    private void moveViewsToCorners(Point[] corners) {
//        if (corners.length < 4) {
//            return;
//        }
//
//        // Lấy tọa độ của 4 góc
//        Point corners1 = corners[0];
//        Point corners2 = corners[1];
//        Point corners3 = corners[2];
//        Point corners4 = corners[3];
//
//        // Tính toán vị trí và kích thước cho từng view
//        int viewWidth = (int) (corners2.x - corners1.x);
//        int viewHeight =(int) (corners4.y - corners1.y);
//
//        // Đặt view1 vào góc trái trên
//        corner1.setX((float)corners1.x);
//        corner1.setY((float)corners1.y);
//        ViewGroup.LayoutParams view1LayoutParams = corner1.getLayoutParams();
////        view1LayoutParams.width = viewWidth;
////        view1LayoutParams.height = viewHeight;
//        corner1.setLayoutParams(view1LayoutParams);
//
//        // Đặt view2 vào góc phải trên
//        corner2.setX((float)corners2.x);
//        corner2.setY((float)corners2.y);
//        ViewGroup.LayoutParams view2LayoutParams = corner2.getLayoutParams();
////        view2LayoutParams.width = viewWidth;
////        view2LayoutParams.height = viewHeight;
//        corner2.setLayoutParams(view2LayoutParams);
//
//        // Đặt view3 vào góc trái dưới
//        corner3.setX((float)corners3.x);
//        corner3.setY((float)corners3.y);
//        ViewGroup.LayoutParams view3LayoutParams = corner3.getLayoutParams();
////        view3LayoutParams.width = viewWidth;
////        view3LayoutParams.height = viewHeight;
//        corner3.setLayoutParams(view3LayoutParams);
//
//        // Đặt view4 vào góc phải dưới
//        corner4.setX((float)corners4.x);
//        corner4.setY((float)corners4.y);
//        ViewGroup.LayoutParams view4LayoutParams = corner4.getLayoutParams();
////        view4LayoutParams.width = viewWidth;
////        view4LayoutParams.height = viewHeight;
//        corner4.setLayoutParams(view4LayoutParams);
//    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                DetailScanActivity.this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//    private View.OnTouchListener touchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent event) {
//            int viewId = view.getId();
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    // Lưu giữ tọa độ ban đầu của góc
//                    if (viewId == R.id.corner1) {
//                        corner1X = (int) event.getRawX();
//                        corner1Y = (int) event.getRawY();
//                        Log.d("test1", "x: "+corner1X+", y:"+corner1Y);
//                    } else if (viewId == R.id.corner2) {
//                        corner2X = (int) event.getRawX();
//                        corner2Y = (int) event.getRawY();
//                        Log.d("test1", "x: "+corner2X+", y:"+corner2Y);
//                    } else if (viewId == R.id.corner3) {
//                        corner3X = (int) event.getRawX();
//                        corner3Y = (int) event.getRawY();
//                        Log.d("test1", "x: "+corner3X+", y:"+corner3Y);
//                    } else if (viewId == R.id.corner4) {
//                        corner4X = (int) event.getRawX();
//                        corner4Y = (int) event.getRawY();
//                        Log.d("test1", "x: "+corner4X+", y:"+corner4Y);
//                    }
//                    break;
//
//                case MotionEvent.ACTION_MOVE:
//                    // Tính toán khoảng cách di chuyển của góc
//                    int dx = (int) event.getRawX();
//                    int dy = (int) event.getRawY();
//
//                    // Dịch chuyển góc và cập nhật tọa độ mới
//                    if (viewId == R.id.corner1) {
//                        int deltaX = dx - corner1X;
//                        int deltaY = dy - corner1Y;
//                        corner1X = dx;
//                        corner1Y = dy;
//                        Log.d("test1", "x: "+corner1X+", y:"+corner1Y);
//                        moveCorner(corner1, deltaX, deltaY);
//                    } else if (viewId == R.id.corner2) {
//                        int deltaX = dx - corner2X;
//                        int deltaY = dy - corner2Y;
//                        corner2X = dx;
//                        corner2Y = dy;
//                        moveCorner(corner2, deltaX, deltaY);
//                    } else if (viewId == R.id.corner3) {
//                        int deltaX = dx - corner3X;
//                        int deltaY = dy - corner3Y;
//                        corner3X = dx;
//                        corner3Y = dy;
//                        moveCorner(corner3, deltaX, deltaY);
//                    } else if (viewId == R.id.corner4) {
//                        int deltaX = dx - corner4X;
//                        int deltaY = dy - corner4Y;
//                        corner4X = dx;
//                        corner4Y = dy;
//                        moveCorner(corner4, deltaX, deltaY);
//                    }
//                    break;
//            }
//            return true;
//        }
//    };
//    private void moveCorner(View corner, int deltaX, int deltaY) {
//        Log.d("Test2", "x: "+corner1.getX()+",y: "+corner1.getY());
//        int newX = corner.getLeft() + deltaX;
//        int newY = corner.getTop() + deltaY;
//        int newRight = corner.getRight() + deltaX;
//        int newBottom = corner.getBottom() + deltaY;
//        Log.d("Test_5", "x: "+deltaX+", y:"+deltaY);
//        Log.d("Test_5", "l: "+corner.getLeft()+", t:"+corner.getTop()+", r:"+corner.getRight()+", b:"+corner.getBottom());
//
//        Log.d(TAG, "newX: "+newX+", newY: "+newY+", newRight: "+newRight+", newBottom: "+newBottom);
////        corner1.setX(newX);
////        corner1.setY(newY);
//
//        corner.layout(newX, newY, newRight, newBottom);
//    }


    private void mapping() {
        resultImg = findViewById(R.id.resultImg);

//        nextTv = findViewById(R.id.toolbar_next);
        printTv = findViewById(R.id.toolbar_print);
    }
}