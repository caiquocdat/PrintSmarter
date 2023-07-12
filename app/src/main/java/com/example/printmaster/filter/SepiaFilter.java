package com.example.printmaster.filter;

import android.graphics.Color;

public class SepiaFilter implements FilterStrategy {
    @Override
    public int processPixel(int pixelColor) {
        int alpha = Color.alpha(pixelColor);
        int red = Color.red(pixelColor);
        int green = Color.green(pixelColor);
        int blue = Color.blue(pixelColor);

        // Apply the sepia tone
        int newRed = (int) ((red * 0.393) + (green * 0.769) + (blue * 0.189));
        int newGreen = (int) ((red * 0.349) + (green * 0.686) + (blue * 0.168));
        int newBlue = (int) ((red * 0.272) + (green * 0.534) + (blue * 0.131));

        // Kiểm tra và giới hạn giá trị màu sắc trong khoảng từ 0 đến 255
        newRed = Math.max(0, Math.min(255, newRed));
        newGreen = Math.max(0, Math.min(255, newGreen));
        newBlue = Math.max(0, Math.min(255, newBlue));

        return Color.argb(alpha, newRed, newGreen, newBlue);
    }
}

