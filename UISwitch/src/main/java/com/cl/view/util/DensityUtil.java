package com.cl.view.util;

import android.content.res.Resources;

/**
 * Created by phenixchen on 2016/1/4.
 */
public class DensityUtil {



    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static final int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale);
    }
}
