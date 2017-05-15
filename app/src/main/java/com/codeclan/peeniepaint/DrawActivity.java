package com.codeclan.peeniepaint;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class DrawActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }
}
