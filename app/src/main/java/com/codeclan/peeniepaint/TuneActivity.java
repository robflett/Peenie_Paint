package com.codeclan.peeniepaint;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class TuneActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TuneView(this));

        mp = MediaPlayer.create(getApplicationContext(), R.raw.heartandsoul);

        mp.start();
    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap BACK button to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }


    protected void onPause() {
        super.onPause();

        if (mp != null && mp.isPlaying()) {
            mp.stop();
            mp.release();
            mp = null;
        }
    }

}

