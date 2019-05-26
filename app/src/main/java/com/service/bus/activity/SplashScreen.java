package com.service.bus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.service.dream.bus.R;


public class SplashScreen extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private Runnable runnable;
    private Handler splashHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this,FirstScreen.class);
                startActivity(i);

                // close this activity
                finish();
            }
        };
        splashHandler.postDelayed(runnable,SPLASH_TIME_OUT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        splashHandler.removeCallbacks(runnable);
    }
}
