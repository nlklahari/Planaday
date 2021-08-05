package com.example.planaday.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.daimajia.androidanimations.library.attention.BounceAnimator;
import com.example.planaday.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        BounceAnimator animator = new BounceAnimator();
        animator.prepare(findViewById(R.id.tvAppName));
        animator.animate();

        new CountDownTimer(1500,1500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent();
                intent.setClass(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        }.start();
    }
}