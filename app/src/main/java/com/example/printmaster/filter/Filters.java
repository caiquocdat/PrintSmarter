package com.example.printmaster.filter;

import android.graphics.Bitmap;
import android.graphics.Color;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Filters {
    public Filters() {
    }

    public Bitmap processFilter(Bitmap original, FilterStrategy filterStrategy) {
        // Tạo một bitmap mới để không thay đổi bitmap gốc
        Bitmap newBitmap = original.copy(Bitmap.Config.ARGB_8888, true);

        // Duyệt qua từng pixel và áp dụng bộ lọc
        for (int x = 0; x < newBitmap.getWidth(); x++) {
            for (int y = 0; y < newBitmap.getHeight(); y++) {
                // Lấy màu sắc hiện tại của pixel
                int pixel = newBitmap.getPixel(x, y);

                // Tính toán màu sắc mới dựa trên màu sắc hiện tại của pixel
                int newColor = filterStrategy.processPixel(pixel);

                // Đặt màu sắc mới cho pixel
                newBitmap.setPixel(x, y, newColor);
            }
        }

        return newBitmap;
    }
}

// Thêm các FilterStrategy khác tại đây...
