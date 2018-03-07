package com.wja.myapplication;

import android.content.res.Resources;

/**
 * Created by hanke on 2018/2/3.
 */

public class SizeUtil {
    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    /**
     *   dp转换px
     *   @param dp   dp值
     *   @return  转换后的px值
     */
    public static int dp2Px(int dp) {
        return Math.round(dp * DENSITY);
    }
}
