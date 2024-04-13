package com.buiseness.saafcitybuiseness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.buiseness.saafcitybuiseness.R;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN=5000;
    Animation top,bottom;
    ImageView logoimge;
    TextView slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animation
                top= AnimationUtils.loadAnimation(this,R.anim.top_animtion);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        logoimge=findViewById(R.id.logo);
        slogan=findViewById(R.id.slogan);

        logoimge.setAnimation(top);
        slogan.setAnimation(bottom);

        //Next Activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}