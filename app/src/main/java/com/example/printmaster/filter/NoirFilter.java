package com.example.printmaster.filter;

import android.graphics.Color;

public class NoirFilter implements FilterStrategy {
    @Override
    public int processPixel(int pixelColor) {
        int alpha = Color.alpha(pixelColor);
        int red = Color.red(pixelColor);
        int green = Color.green(pixelColor);
        int blue = Color.blue(pixelColor);

        // Chuyển đổi thành đen trắng bằng cách lấy giá trị trung bình của màu sắc
        int gray = (red + green + blue) / 3;

        // Tăng độ tương phản bằng cách làm sáng các màu sáng và làm tối các màu tối
        if (gray > 128) {
            gray += 50;
        } else {
            gray -= 50;
        }

        // Kiểm tra và giới hạn giá trị màu sắc trong khoảng từ 0 đến 255
        gray = Math.max(0, Math.min(255, gray));

        return Color.argb(alpha, gray, gray, gray);
    }
}

