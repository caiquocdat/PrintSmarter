package com.example.printmaster.filter;

import android.graphics.Color;

public class ToasterFilter implements FilterStrategy {
    @Override
    public int processPixel(int pixelColor) {
        int alpha = Color.alpha(pixelColor);
        int red = Color.red(pixelColor) + 60; // Tăng màu đỏ
        int green = Color.green(pixelColor);
        int blue = Color.blue(pixelColor) - 50; // Giảm màu xanh dương

        // Kiểm tra và giới hạn giá trị màu sắc trong khoảng từ 0 đến 255
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        return Color.argb(alpha, red, green, blue);
    }
}

