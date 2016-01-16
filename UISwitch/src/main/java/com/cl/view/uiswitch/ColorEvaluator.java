package com.cl.view.uiswitch;

import android.animation.TypeEvaluator;
import android.graphics.Color;

/**
 * Created by phenixchen on 2016/1/12.
 */

public class ColorEvaluator implements TypeEvaluator<Integer> {

    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {

        int red =Color.red(startValue)+ (int) ((Color.red(endValue) - Color.red(startValue))*fraction);
        int green =Color.green(startValue)+ (int) ((Color.green(endValue) - Color.green(startValue))*fraction);
        int blue =Color.blue(startValue)+ (int) ((Color.blue(endValue) - Color.blue(startValue))*fraction);
        return Color.rgb(red,green,blue);
    }
}

