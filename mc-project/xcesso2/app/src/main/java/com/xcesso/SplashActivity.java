package com.xcesso;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;


/**
 * Created by EG on 5/18/2018.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                SharedPreferences sp = getSharedPreferences(Globals.UserSharedPrefName,MODE_PRIVATE);
                Intent mainIntent;
                if(sp.contains("Password"))
                {
                    mainIntent=new Intent(SplashActivity.this,PasswordActivity.class);
                }
                else
                    mainIntent = new Intent(SplashActivity.this,SignUp.class);

                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, 2500);
    }
}

