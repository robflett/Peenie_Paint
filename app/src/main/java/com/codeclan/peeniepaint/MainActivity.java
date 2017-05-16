package com.codeclan.peeniepaint;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button drawButton;
    Button tuneButton;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawButton = (Button)findViewById(R.id.draw);
        tuneButton = (Button)findViewById(R.id.tune);

        mp = MediaPlayer.create(getApplicationContext(), R.raw.heartandsoul);

//        mp.setLooping(true);
        mp.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.setLooping(false);
        mp.stop();
        mp.release();
    }



    public void onDrawButtonClicked(View view) {
        Intent intent = new Intent(this, DrawActivity.class);
        startActivity(intent);
    }

    public void onTuneButtonClicked(View view) {
        Intent intent = new Intent(this, TuneActivity.class);
        startActivity(intent);
    }



}