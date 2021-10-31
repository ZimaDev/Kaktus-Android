package kg.zima.gesvitaly.zanozakg.utils;

import android.graphics.drawable.ColorDrawable;
import androidx.annotation.ColorInt;

public class SizedColorDrawable extends ColorDrawable {
    private int width;
    private int height;

    public SizedColorDrawable(@ColorInt int color, int width, int height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }
}
