package com.example.printmaster.filter;

import android.graphics.Color;

public class ToneFilter implements FilterStrategy {
    private int targetRed, targetGreen, targetBlue;

    public ToneFilter(int targetRed, int targetGreen, int targetBlue) {
        this.targetRed = targetRed;
        this.targetGreen = targetGreen;
        this.targetBlue = targetBlue;
    }

    @Override
    public int processPixel(int pixelColor) {
        int alpha = Color.alpha(pixelColor);
        int red = Color.red(pixelColor);
        int green = Color.green(pixelColor);
        int blue = Color.blue(pixelColor);

        // Điều chỉnh màu sắc của pixel dựa trên tông màu đích
        red = (red + targetRed) / 2;
        green = (green + targetGreen) / 2;
        blue = (blue + targetBlue) / 2;

        // Kiểm tra và giới hạn giá trị màu sắc trong khoảng từ 0 đến 255
        red = Math.max(0, Math.min(255, red));
        green = Math.max(0, Math.min(255, green));
        blue = Math.max(0, Math.min(255, blue));

        return Color.argb(alpha, red, green, blue);
    }
}

