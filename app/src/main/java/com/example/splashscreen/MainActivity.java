package com.example.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvNE,tvDesNE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        tvNE = findViewById(R.id.textView);
        tvDesNE =  findViewById(R.id.textoInferior);

        Animation blink= AnimationUtils.loadAnimation(this,R.anim.blink);

        tvNE.startAnimation(blink);
        tvDesNE.startAnimation(blink);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login= new Intent(MainActivity.this,LogInViews.class);
                startActivity(login);
                finish();
            }
        },3000);

    }
}
