package com.example.printmaster.filter;

import android.graphics.Color;

public class LinearFilter implements FilterStrategy {
    private float scaleFactor;

    public LinearFilter(float scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    @Override
    public int processPixel(int pixelColor) {
        int alpha = Color.alpha(pixelColor);
        int red = Math.round(Color.red(pixelColor) * scaleFactor);
        int green = Math.round(Color.green(pixelColor) * scaleFactor);
        int blue = Math.round(Color.blue(pixelColor) * scaleFactor);

        // Kiểm tra và giới hạn giá trị màu sắc trong khoảng từ 0 đến 255
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        return Color.argb(alpha, red, green, blue);
    }
}

