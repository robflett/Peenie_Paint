package com.codeclan.peeniepaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

public class TuneActivity extends View {


    private SparseArray<PointF> mActivePointers;

    public int width;
    public int height;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;

    private int[] colours = {Color.BLUE, Color.GREEN, Color.MAGENTA,
            Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
            Color.LTGRAY, Color.YELLOW};

    private Paint textPaint;

    private float mX, mY;

    Context context;

//    the below compares floats - if the TOLERANCE is below the given amount (in pixels), they are considered equal
//    private static final float TOLERANCE = 5;


    private static final int SIZE = 60;

    public TuneActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
//        below we set 'this' to equal the above context
        this.context = context;

        mActivePointers = new SparseArray<PointF>();

        mPath = new Path();

        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.MAGENTA);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(20f);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);

    }

//    below - setting up Bitmap (the drawing surface)

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//        mCanvas = new Canvas(mBitmap);
//
////        below is an attempt to set the canvas background to black.
////        Bitmap newBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
////        Canvas canvas = new Canvas(newBitmap);
////        canvas.drawColor(Color.BLACK);
////        canvas.drawBitmap(mBitmap, 0, 0, null);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        SET BACKGROUND COLOR TO BLACK
        canvas.drawColor(Color.BLACK);
        canvas.drawPath(mPath, mPaint);

        for (int size = mActivePointers.size(), i = 0; i < size; i++) {
            PointF point = mActivePointers.valueAt(i);
            if (point != null)
                mPaint.setColor(colours[i % 9]);
            canvas.drawCircle(point.x, point.y, SIZE, mPaint);
        }
        canvas.drawText("TEXT HERE " + mActivePointers.size(), 10, 40, textPaint);
    }


//    private void firstTouch(float x, float y){
//        mPath.moveTo(x, y);
//        mX = x;
//        mY = y;
//
//    }

//    moving method - that includes a catch that resets the value of x and y if they exceed TOLERANCE (set above)
//    private void moveTouch(float x, float y){
//        float dx = Math.abs(x - mX);
//        float dy = Math.abs(y - mY);
//        if(dx >= TOLERANCE || dy >= TOLERANCE){
//            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) /2);
//            mX = x;
//            mY = y;
//        }
//    }

    public void clearCanvas() {
        mPath.reset();
        invalidate();
    }

//    private void upTouch(){
//        mPath.lineTo(mX, mY);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();

        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        int maskedAction = event.getActionMasked();

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {

                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                mActivePointers.put(pointerId, f);
                break;
            }
//                firstTouch(x, y);
//                invalidate();
//                break;
            case MotionEvent.ACTION_MOVE: {
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF point = mActivePointers.get(event.getPointerId(i));
                    if (point != null) {
                        point.x = event.getX(i);
                        point.y = event.getY(i);
                    }
                }
                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                mActivePointers.remove(pointerId);
                break;
            }
        }
        invalidate();

        return true;
    }
}
//                moveTouch(x, y);
//                invalidate();
//                break;

//            case MotionEvent.ACTION_UP:
//                upTouch();
//                invalidate();
//                break;
//
//        }
//
//        return true;
//
//    }

