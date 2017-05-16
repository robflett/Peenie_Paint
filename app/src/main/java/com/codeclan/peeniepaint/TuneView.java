package com.codeclan.peeniepaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;


public class TuneView extends View {


    private SparseArray<PointF> mActivePointers;

    public int width;
    public int height;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;

    private int[] colours = {Color.BLUE, Color.GREEN, Color.MAGENTA,
            Color.CYAN, Color.RED, Color.WHITE,
            Color.LTGRAY, Color.YELLOW};

    ArrayList randColour = new ArrayList<Integer>();

    private Paint textPaint;

    private float mX, mY;

    Context context;

    private static final int SIZE = 60;


    public TuneView(Context context) {
        super(context);

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


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



//        SET BACKGROUND COLOR TO BLACK
        canvas.drawColor(Color.BLACK);

//        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.soundFileName);
//        mp.start();

        canvas.drawPath(mPath, mPaint);

        for (int size = mActivePointers.size(), i = 0; i < size; i++) {
            PointF point = mActivePointers.valueAt(i);

            if (point != null)
                mPaint.setColor((int) randColour.get(i));
//                mPaint.setColor(colours[i % 9]);

//            if (randColour.get(i) != null) {
//                mPaint.setColor((int) randColour.get(i));
//            }


            canvas.drawCircle(point.x, point.y, SIZE, mPaint);
        }
        canvas.drawText("TEXT HERE " + mActivePointers.size(), 10, 40, textPaint);
    }

//
//    public void clearCanvas() {
//        mPath.reset();
//        invalidate();
//    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        int maskedAction = event.getActionMasked();

        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {

                Random ran = new Random();
                int x = ran.nextInt(colours.length);
                randColour.add(colours[x]);

                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                mActivePointers.put(pointerId, f);
                break;
            }

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



