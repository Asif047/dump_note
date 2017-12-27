package com.rokomari_poc.noteme;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView ivSplash;
    private TextView tvNoteMe;
    private Animation fromBottom,fromTop;

    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ivSplash=findViewById(R.id.imageview_splash_image);
        tvNoteMe=findViewById(R.id.textview_note_me);

        fromBottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromTop=AnimationUtils.loadAnimation(this,R.anim.fromtop);

        ivSplash.setAnimation(fromTop);
        tvNoteMe.setAnimation(fromBottom);

        //to remove the action bar starts
//        getSupportActionBar().hide();
        //to removoe the action bar ends

        typeface = Typeface.createFromAsset(getAssets(), "font_blacklist/The Blacklist.ttf");

        tvNoteMe.setTypeface(typeface);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);   // set the duration of splash screen
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                        Intent intent = new Intent(SplashScreenActivity.this, MakeNoteActivity.class);
                        startActivity(intent);
                        finish();
                }
            }
        };
        timer.start();


    }
}
