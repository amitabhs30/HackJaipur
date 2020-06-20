package com.example.mobify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT =3000;
    TextView text_main, text_tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_main = findViewById(R.id.text_main);
        text_tag = findViewById(R.id.text_sub);

        final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                text_main.startAnimation(slide_up);
                text_tag.startAnimation(slide_up);

                Intent i = new Intent(MainActivity.this, Onboard.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
