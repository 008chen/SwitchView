package com.cl.view.uiswitch;

import android.animation.TypeEvaluator;

/**
 * Created by phenixchen on 2016/1/5.
 */
public class DistanceEvaluator implements TypeEvaluator<Float> {
    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {
        return fraction * (endValue - startValue);
    }
}
