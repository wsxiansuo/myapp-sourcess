package com.sxs.app.lifehumor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
public class Splash extends Activity {   


    private final int SPLASH_DISPLAY_LENGHT = 1500; //�ӳ�����

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Runnable runnableJump = new Runnable() {

            @Override
            public void run() {
            	Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        };
        new Handler().postDelayed(runnableJump, SPLASH_DISPLAY_LENGHT);
    }
}
