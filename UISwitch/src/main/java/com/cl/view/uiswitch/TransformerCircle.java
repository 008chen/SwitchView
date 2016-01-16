package com.cl.view.uiswitch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by phenixchen on 2016/1/12.
 */
public class TransformerCircle implements IDraw {

    private static final float sMagicNumber = 0.55228475f;

    float mCX;
    float mCY;

    float mRadius;

    Path mPath;
    Paint mPaint;

    float mDistance;

    public int  mEndColor= 0xFF2f528F;
    public int mStartColor = 0xFFD74D79;

    public void setOffsetX(float offset) {
        this.mOffsetX = offset;


    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    float mOffsetX;


    public void setRadius(float radius) {
        this.mRadius = mRadius;
    }

    public void setCX(float cx) {
        this.mCX = cx;
    }

    public void updateCX() {
        this.mCX += mOffsetX;
    }

    public void reset() {
        mDistance = 0;
        mOffsetX = 0;
    }


    public void setCY(float cy) {
        this.mCY = cy;
    }


    public TransformerCircle(float cx, float cy, float radius) {
        this.mCX = cx;
        this.mCY = cy;
        this.mRadius = radius;
        mPath = new Path();

        mPaint = new Paint();
        mPaint.setColor(mStartColor);
        mPaint.setStyle(Paint.Style.FILL);

    }

    public void setDistance(float distance) {
        this.mDistance = distance;
    }

    private void updatePath(float distance, float movex) {

        float rightRadius = 0;
        float leftRadius = 0;
        if (distance > 0) {
            float longRadius = mRadius + distance - movex;
            float shortRadius = mRadius - distance * 0.1f + movex * 0.1f;
            rightRadius = longRadius;
            leftRadius = shortRadius;
        } else {
            float longRadius = mRadius - distance + movex;
            float shortRadius = mRadius + distance * 0.1f - movex * 0.1f;
            rightRadius = shortRadius;
            leftRadius = longRadius;
        }


        mPath.reset();

        //右上
        mPath.lineTo(0, -mRadius);
        mPath.cubicTo(mRadius * sMagicNumber, -mRadius
                , rightRadius, -mRadius * sMagicNumber
                , rightRadius, 0);
        mPath.lineTo(0, 0);

        //右下
        mPath.lineTo(0, mRadius);
        mPath.cubicTo(mRadius * sMagicNumber, mRadius
                , rightRadius, mRadius * sMagicNumber
                , rightRadius, 0);
        mPath.lineTo(0, 0);

        mPath.lineTo(0, -mRadius);
        mPath.cubicTo(-mRadius * sMagicNumber, -mRadius
                , -leftRadius, -mRadius * sMagicNumber
                , -leftRadius, 0);
        mPath.lineTo(0, 0);

        mPath.lineTo(0, mRadius);
        mPath.cubicTo(-mRadius * sMagicNumber, mRadius
                , -leftRadius, mRadius * sMagicNumber
                , -leftRadius, 0);
        mPath.lineTo(0, 0);

    }


    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(mCX + mOffsetX, mCY);
        updatePath(mDistance, mOffsetX);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();

    }

}
