package com.codeclan.peeniepaint;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class DrawActivity extends AppCompatActivity{

    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

//    public void clearCanvas(View v){
//        drawView.clearCanvas();
//    }
}
