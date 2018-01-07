package com.rokomari_poc.noteme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rokomari_poc.noteme.Home.HomeActivity;
import com.rokomari_poc.noteme.LogIn.LogInActivity;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView ivSplash;
    private TextView tvNoteMe;
    private Animation fromBottom, fromTop;

    private Typeface typeface;

    PulsatorLayout pulsatorLayout;
    private String account_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //getting account id starts
        SharedPreferences prefs = getSharedPreferences("Profile_PREF", MODE_PRIVATE);
        String restoredAccount = prefs.getString("account_id", null);

        if (restoredAccount != null) {
            account_id = prefs.getString("account_id", "No account defined");
        }

        ivSplash = findViewById(R.id.imageview_splash_image);
        tvNoteMe = findViewById(R.id.textview_note_me);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);

        ivSplash.setAnimation(fromTop);
        tvNoteMe.setAnimation(fromBottom);

        //adding pulsator starts
        pulsatorLayout = findViewById(R.id.pulsator);
        pulsatorLayout.start();
        //adding pulsator ends

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

//                        Intent intent = new Intent(SplashScreenActivity.this,HomeActivity.class);
//                        startActivity(intent);
//                        finish();


                    if (account_id == null) {
                        Intent intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        // overridePendingTransition(R.anim.move_right_to_left_enter, R.anim.move_right_to_left_exit);
                    }

                }
            }
        };
        timer.start();


    }
}
