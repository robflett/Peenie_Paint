package com.codeclan.peeniepaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawActivity extends View{

    public int width;
    public int height;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;

    private float mX, mY;

    Context context;

    //    the below compares floats - if the TOLERANCE is below the given amount (in pixels), they are considered equal
    private static final float TOLERANCE = 5;

    public DrawActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
//        below we set 'this' to equal the above context
        this.context = context;

        mPath = new Path();

        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.MAGENTA);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(5f);

    }

//    below - setting up Bitmap (the drawing surface)

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
    }

    private void firstTouch(float x, float y){
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    //    moving method - that includes a catch that resets the value of x and y if they exceed TOLERANCE (set above)
    private void moveTouch(float x, float y){
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if(dx >= TOLERANCE || dy >= TOLERANCE){
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) /2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas(){
        mPath.reset();
        invalidate();
    }

    private void upTouch(){
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;

        }

        return true;

    }

}
