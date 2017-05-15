package com.codeclan.peeniepaint;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



public class TuneActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TuneView(this));
    }

}

