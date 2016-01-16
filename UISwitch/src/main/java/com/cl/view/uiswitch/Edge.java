package com.cl.view.uiswitch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by phenixchen on 2016/1/5.
 */
public class Edge implements IDraw {

    float mHalfStrokeWidth;

    Paint mPaint;
    RectF mRectF;
    float mArcRadius = -1f;

    RectF mMiddleLineRectF;
    RectF mLeftArcRectF;
    RectF mRightArcRectF;

    public int mStartColor = 0xFF2f528F;
    public int mEndColor = 0xFFD74D79;

    public float getArcRadius() {
        return mArcRadius;
    }


    public void setColor(int color)
    {
        mPaint.setColor(color);
    }
    public Edge(RectF rectF, float strokeWidth) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mStartColor);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        mRectF = rectF;
        mHalfStrokeWidth = strokeWidth / 2;
        mMiddleLineRectF = new RectF();
        mMiddleLineRectF.set(mRectF.left + mHalfStrokeWidth, mRectF.top + mHalfStrokeWidth, mRectF.right - mHalfStrokeWidth, mRectF.bottom - mHalfStrokeWidth);

        float shapeHeight = mMiddleLineRectF.height();
        mArcRadius = shapeHeight / 2;

        mLeftArcRectF = new RectF(mMiddleLineRectF.left, mMiddleLineRectF.top, mMiddleLineRectF.left + 2 * mArcRadius, mMiddleLineRectF.top + 2 * mArcRadius);
        mRightArcRectF = new RectF(mMiddleLineRectF.right - 2 * mArcRadius, mMiddleLineRectF.top, mMiddleLineRectF.right, mMiddleLineRectF.top + 2 * mArcRadius);
    }

    public float getLeft() {
        return mRectF.left;
    }

    public float getTop() {
        return mRectF.top;
    }

    public float getRight() {
        return mRectF.right;
    }

    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    public float getBottom() {
        return mRectF.bottom;

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawArc(mLeftArcRectF, 90, 180, false, mPaint);
        canvas.drawLine((mLeftArcRectF.left + mLeftArcRectF.right) / 2, mLeftArcRectF.top, (mRightArcRectF.left + mRightArcRectF.right) / 2, mLeftArcRectF.top, mPaint);
        canvas.drawLine((mLeftArcRectF.left + mLeftArcRectF.right) / 2, mLeftArcRectF.bottom, (mRightArcRectF.left + mRightArcRectF.right) / 2, mLeftArcRectF.bottom, mPaint);
        canvas.drawArc(mRightArcRectF, 270, 180, false, mPaint);
    }
    public float getStrokeWidth() {
        return mHalfStrokeWidth * 2;
    }

}
