package com.example.printmaster.filter;

import android.graphics.Color;

public class ChromeFilter implements FilterStrategy {
    @Override
    public int processPixel(int pixelColor) {
        int alpha = Color.alpha(pixelColor);
        int red = (int) (Color.red(pixelColor) * 0.9);
        int green = (int) (Color.green(pixelColor) * 1.1);
        int blue = (int) (Color.blue(pixelColor) * 1.1);

        // Kiểm tra và giới hạn giá trị màu sắc trong khoảng từ 0 đến 255
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        return Color.argb(alpha, red, green, blue);
    }
}

