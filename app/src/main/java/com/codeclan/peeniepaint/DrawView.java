package com.codeclan.peeniepaint;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;

public class DrawView extends View {

//    public int width;
//    public int height;

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint, cPaint;

    private SparseArray<Path> paths;

//    private float mX, mY;

    private int[] colours = {Color.BLUE, Color.GREEN, Color.MAGENTA,
            Color.BLACK, Color.CYAN, Color.GRAY, Color.RED, Color.DKGRAY,
            Color.LTGRAY, Color.YELLOW};

    Context context;


    public DrawView(Context context) {
        super(context);
//        below we set 'this' to equal the above context

        setupDrawing();

        this.context = context;

        mPath = new Path();

        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.MAGENTA);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(20f);

    }

    private void setupDrawing() {
        paths = new SparseArray<>();

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

//

        cPaint = new Paint(Paint.DITHER_FLAG);
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
        canvas.drawColor(Color.BLACK);
//        iterate through paths to draw each line for each finger
        canvas.drawBitmap(mBitmap, 0, 0, cPaint);
        for (int i=0; i<paths.size(); i++) {
//            iterate through colours and choose a random colour for the brush -- Shouldn't this work?
            Collections.shuffle(Arrays.asList(colours));
//
//            Add Blur effect to the line
            mPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));

            mPaint.setColor(colours[i % 9]);
            canvas.drawPath(paths.valueAt(i), mPaint);
        }

//        canvas.drawPath(mPath, mPaint);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int id = event.getPointerId(index);

        Path path;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                path = new Path();
                path.moveTo(event.getX(index), event.getY(index));
                paths.put(id, path);
                break;

            case MotionEvent.ACTION_MOVE:
                for (int i=0; i<event.getPointerCount(); i++) {
                    id = event.getPointerId(i);
                    path = paths.get(id);
                    if (path != null) path.lineTo(event.getX(i), event.getY(i));
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                path = paths.get(id);
                if (path != null) {
                    mCanvas.drawPath(path, mPaint);
                    paths.remove(id);
                }
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }



}
