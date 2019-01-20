package com.example.gilgoldzweig.moviedemo.ui.views.simplesearchview.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;

public final class DimensUtils {

    public static int convertDpToPx(int dp, @NonNull Context context) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }

    public static float convertDpToPx(float dp, @NonNull Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
