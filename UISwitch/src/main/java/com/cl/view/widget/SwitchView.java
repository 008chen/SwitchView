package com.cl.view.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.cl.view.uiswitch.AnimatorBuilder;
import com.cl.view.uiswitch.Background;
import com.cl.view.uiswitch.Edge;
import com.cl.view.uiswitch.TransformerCircle;
import com.cl.view.util.DensityUtil;

import static android.animation.ValueAnimator.AnimatorListener;
import static android.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * Created by phenixchen on 2016/1/4.
 */
public class SwitchView extends View implements View.OnClickListener {


    Edge mFrameEdge;
    TransformerCircle mTransformerCircle;
    Background mBackground;

//    private Paint mFrameEdgePaint;


    float mFrameStrokeWidth;

    boolean isOn = false;


    AnimatorSet mL2RAnimatorSet;
    AnimatorSet mR2LAnimatorSet;

    OnClickListener mOnClickListener;

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwitchView(Context context) {

        super(context);
        init();
    }

    private void init() {
        mFrameStrokeWidth = DensityUtil.dpToPx(5);
        super.setOnClickListener(this);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }


    public boolean isOn() {
        return isOn;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureDimension(280, widthMeasureSpec);
        int height = measureDimension(140, heightMeasureSpec);

        setMeasuredDimension(width, height);

        mFrameEdge = new Edge(new RectF(0, 0, width, height), mFrameStrokeWidth);
        mBackground = new Background(mFrameEdge.getLeft() + mFrameEdge.getStrokeWidth(), mFrameEdge.getTop() + mFrameEdge.getStrokeWidth(), mFrameEdge.getRight() - mFrameEdge.getStrokeWidth(), mFrameEdge.getBottom() - mFrameEdge.getStrokeWidth(), mFrameEdge.getArcRadius() - mFrameEdge.getStrokeWidth() / 2);

        float cxOfLeft = mFrameEdge.getLeft() + mFrameEdge.getStrokeWidth() / 2 + mFrameEdge.getArcRadius();
        float cyOfLightAndRight = mFrameEdge.getTop() + mFrameEdge.getStrokeWidth() / 2 + mFrameEdge.getArcRadius();
        float cxOfRight = mFrameEdge.getRight() - mFrameEdge.getStrokeWidth() / 2 - mFrameEdge.getArcRadius();

        mTransformerCircle = new TransformerCircle(cxOfLeft, cyOfLightAndRight, mFrameEdge.getArcRadius() - mFrameEdge.getStrokeWidth() / 2);

        AnimatorUpdateListener updateDistance = new AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                Float value = (Float) animator.getAnimatedValue();
                mTransformerCircle.setDistance(value);
                invalidate();
            }
        };

        AnimatorUpdateListener updateInner = new AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                Float value = (Float) animator.getAnimatedValue();
                mTransformerCircle.setOffsetX(value);
                mBackground.setMiddle(value);
                invalidate();
            }
        };

        AnimatorUpdateListener updateEdgeColor = new AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                Integer value = (Integer) animator.getAnimatedValue();
                mFrameEdge.setColor(value);
                invalidate();
            }
        };
        AnimatorUpdateListener updateBallColor = new AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                Integer value = (Integer) animator.getAnimatedValue();
                mTransformerCircle.setColor(value);
                invalidate();
            }
        };

        Animator l2RAnimator = AnimatorBuilder.buildLeft2RightAnimator(cxOfLeft, cxOfRight, 400, updateDistance);
        Animator l2RBounceAnimator = AnimatorBuilder.buildBounceAnimator(cxOfLeft, cxOfRight, 400, updateInner);
        Animator l2RColorAnimator = AnimatorBuilder.buildColorAnimator(mFrameEdge.mStartColor, mFrameEdge.mEndColor, 800, updateEdgeColor);
        Animator l2RColorAnimator2 = AnimatorBuilder.buildColorAnimator(mTransformerCircle.mStartColor, mTransformerCircle.mEndColor, 800, updateBallColor);

        Animator r2LAnimator = AnimatorBuilder.buildLeft2RightAnimator(cxOfRight, cxOfLeft, 400, updateDistance);
        Animator r2LBounceAnimator = AnimatorBuilder.buildBounceAnimator(cxOfRight, cxOfLeft, 400, updateInner);
        Animator r2LColorAnimator = AnimatorBuilder.buildColorAnimator(mFrameEdge.mEndColor, mFrameEdge.mStartColor, 800, updateEdgeColor);
        Animator r2LColorAnimator2 = AnimatorBuilder.buildColorAnimator(mTransformerCircle.mEndColor, mTransformerCircle.mStartColor, 800, updateBallColor);

        AnimatorListener animatorListener = new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mTransformerCircle.updateCX();
                mTransformerCircle.reset();
                mBackground.reset();
                isOn = !isOn;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

        l2RBounceAnimator.addListener(animatorListener);
        r2LBounceAnimator.addListener(animatorListener);

        mL2RAnimatorSet = new AnimatorSet();
        AnimatorSet l2RColorAnimatorSet = new AnimatorSet();
        l2RColorAnimatorSet.playSequentially(l2RAnimator, l2RBounceAnimator);
        mL2RAnimatorSet.playTogether(l2RColorAnimatorSet, l2RColorAnimator,l2RColorAnimator2);

        mR2LAnimatorSet = new AnimatorSet();
//        mR2LAnimatorSet.playSequentially(right2Left2Animator, right2LeftBounceAnimator);
        AnimatorSet r2lColorAnimatorSet = new AnimatorSet();
        r2lColorAnimatorSet.playSequentially(r2LAnimator, r2LBounceAnimator);
        mR2LAnimatorSet.playTogether(r2lColorAnimatorSet, r2LColorAnimator,r2LColorAnimator2);


    }

    public int measureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mFrameEdge.draw(canvas);
        mBackground.draw(canvas);
        mTransformerCircle.draw(canvas);


    }


    @Override
    public void onClick(View v) {
        if (!mL2RAnimatorSet.isRunning() && !mR2LAnimatorSet.isRunning()) {
            if (!isOn) {
                mL2RAnimatorSet.start();
            } else {
                mR2LAnimatorSet.start();
            }
        }
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }

    }
}
