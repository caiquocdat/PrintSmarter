//package com.example.printmaster.scan;
//
//import android.graphics.Rect;
//
//import org.opencv.android.Utils;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfPoint;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.imgproc.Imgproc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ImageProcessor {
//    public static List<Rect> detectRectangles(byte[] data, int width, int height) {
//        // Chuyển đổi dữ liệu ảnh từ byte array sang đối tượng Mat của OpenCV
//        Mat mat = new Mat(height + height / 2, width, CvType.CV_8UC1);
//        mat.put(0, 0, data);
//
//        // Chuyển đổi ảnh màu sang ảnh xám để dễ dàng xử lý
//        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_YUV2GRAY_NV21);
//
//        // Xử lý và phát hiện contour (đường viền) trong ảnh
//        Mat binary = new Mat(mat.rows(), mat.cols(), CvType.CV_8UC1);
//        Imgproc.threshold(mat, binary, 128, 255, Imgproc.THRESH_BINARY);
//
//        Mat hierarchy = new Mat();
//        List<MatOfPoint> contours = new ArrayList<>();
//        Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//
//        // Phát hiện và lưu trữ hình chữ nhật trong danh sách rectangles
//        List<Rect> rectangles = new ArrayList<>();
//        for (int i = 0; i < contours.size(); i++) {
//            MatOfPoint contour = contours.get(i);
//            double area = Imgproc.contourArea(contour);
//
//            // Kiểm tra điều kiện diện tích và hình dạng để xác định các hình chữ nhật
//            if (area > 1000 && area < 50000) {
//                Rect rect = Imgproc.boundingRect(contour);
//                rectangles.add(rect);
//            }
//        }
//
//        return rectangles;
//    }
//
//    public static void drawRectangles(Mat mat, List<Rect> rectangles) {
//        for (Rect rect : rectangles) {
//            Imgproc.rectangle(mat, rect.tl(), rect.br(), new Scalar(0, 255, 0), 2);
//        }
//    }
//}
//
