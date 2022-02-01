package com.example.seprojectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;

    Animation topAnim, botAnim;
    ImageView logoSplash;
    TextView title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logoSplash = findViewById(R.id.logoSplash);
        title = findViewById(R.id.titleSplash);
        desc = findViewById(R.id.descSplash);

        logoSplash.setAnimation(topAnim);
        title.setAnimation(botAnim);
        desc.setAnimation(botAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,Info.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

    }
}