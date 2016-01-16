package com.cl.view.uiswitch;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by phenixchen on 2016/1/12.
 */
public class Background implements IDraw {

    float mLeft;
    float mRight;
    float mTop;
    float mBottom;
    float mMiddle;
    float mMiddletmp;
    float mRadius;

    Paint mPaintLeft;
    Paint mPaintRight;


    public void setMiddle(float offset) {
        this.mMiddletmp = mMiddle + offset;
    }

    public void reset() {
        mMiddle = mMiddletmp;
    }
    public Background(float l,  float t, float r,float b, float radius) {
        this.mLeft = l;
        this.mTop = t;
        this.mRight = r+1;
        this.mBottom = b;
        this.mRadius = radius;
        mMiddle = mLeft + radius;

        this.mMiddletmp = mMiddle;

        mPaintLeft = new Paint();
        mPaintLeft.setColor(0xFFE6638A);
        mPaintLeft.setAntiAlias(true);
        mPaintLeft.setStyle(Paint.Style.FILL);

        mPaintRight = new Paint();
        mPaintRight.setColor(0xFF2F528F);
        mPaintRight.setAntiAlias(true);
        mPaintRight.setStyle(Paint.Style.FILL);
    }




    @Override
    public void draw(Canvas canvas) {

        canvas.drawArc(mLeft, mTop, mLeft + 2 * mRadius, mBottom, 90, 180, true, mPaintLeft);
        canvas.drawRect(mLeft + mRadius, mTop, mMiddletmp, mBottom, mPaintLeft);

        canvas.drawRect(mMiddletmp, mTop, mRight - mRadius, mBottom, mPaintRight);
        canvas.drawArc(mRight - 2 * mRadius, mTop, mRight, mBottom, 270, 180, true, mPaintRight);
    }
}
