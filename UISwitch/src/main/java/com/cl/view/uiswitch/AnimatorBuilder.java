package com.cl.view.uiswitch;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

/**
 * Created by phenixchen on 2016/1/11.
 */
public class AnimatorBuilder {
    private static final String TRANSLATION_Y = "translationY";
    private static final String ALPHA = "alpha";

    private final int duration;

    AnimatorBuilder(int duration) {
        this.duration = duration;
    }

    public static AnimatorBuilder newInstance(Context context) {
        int duration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);
        return new AnimatorBuilder(duration);
    }

    public Animator buildTranslationYAnimator(View view, int startY, int endY) {
        Animator animator = ObjectAnimator.ofFloat(view, TRANSLATION_Y, startY, endY);
        animator.setDuration(duration);
        return animator;
    }

    public Animator buildShowAnimator(View view) {
        return buildAlphaAnimator(view, 0f, 1f);
    }

    public Animator buildHideAnimator(View view) {
        return buildAlphaAnimator(view, 1f, 0f);
    }

    public Animator buildAlphaAnimator(View view, float startAlpha, float endAlpha) {
        Animator animator = ObjectAnimator.ofFloat(view, ALPHA, startAlpha, endAlpha);
        animator.setDuration(duration);
        return animator;
    }


    public static Animator buildLeft2RightAnimator(float startX, float endX, int duration, ValueAnimator.AnimatorUpdateListener Left2RightUpdateListener) {
        DistanceEvaluator evaluator = new DistanceEvaluator();
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, startX, endX);

        animator.setDuration(duration);
        animator.addUpdateListener(Left2RightUpdateListener);
        return animator;
    }

    public static Animator buildBounceAnimator(float startX, float endX, int duration, ValueAnimator.AnimatorUpdateListener Left2RightUpdateListener) {
        DistanceEvaluator evaluator = new DistanceEvaluator();
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, startX, endX);
        animator.setInterpolator(new BounceInterpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(Left2RightUpdateListener);

        return animator;
    }

    public static Animator buildColorAnimator(int startX, int endX, int duration, ValueAnimator.AnimatorUpdateListener Left2RightUpdateListener) {
        ColorEvaluator evaluator = new ColorEvaluator();
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, startX, endX);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(Left2RightUpdateListener);

        return animator;
    }

}
