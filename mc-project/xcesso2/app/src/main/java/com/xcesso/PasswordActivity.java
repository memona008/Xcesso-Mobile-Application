package com.xcesso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PasswordActivity extends Activity {

    String userEntered="";
    String userPin = "";

    final int PIN_LENGTH = 4;
    boolean keyPadLockedFlag = false;
    Context appContext;

    TextView statusView;
    TextView titleView;

    ImageView dot1;
    ImageView dot2;
    ImageView dot3;
    ImageView dot4;
    ImageView lockedimage;
    ImageButton deleteback;

    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;

    int dot1Id;
    int dot2Id;
    int dot3Id;
    int dot4Id;

    boolean flag1 = false;
    boolean flag2 = false;
    boolean flag3 = false;
    boolean flag4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appContext = this;
        userEntered = "";
        //Log.i("MainActivity", "hello");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_password);

        final Animation animScale = AnimationUtils.loadAnimation(this,R.anim.scale);

        dot1Id = R.id.dot1;
        dot2Id = R.id.dot2;
        dot3Id = R.id.dot3;
        dot4Id = R.id.dot4;

        dot1 = (ImageView)findViewById(R.id.dot1);
        dot2 = (ImageView)findViewById(R.id.dot2);
        dot3 = (ImageView)findViewById(R.id.dot3);
        dot4 = (ImageView)findViewById(R.id.dot4);
        lockedimage = (ImageView)findViewById(R.id.lock_image);
        SharedPreferences sp = getSharedPreferences(Globals.UserSharedPrefName, Context.MODE_PRIVATE);
     //   if(sp.contains("Password")){
            userPin = sp.getString("Password","0000");
            Log.v("Password",userPin);
//            if(userPin == ""){
//                Toast.makeText(appContext,"Password is not set",Toast.LENGTH_LONG).show();
//                return;
//            }
       // }

        statusView = (TextView) findViewById(R.id.statusview);
        deleteback = (ImageButton) findViewById(R.id.ImageDeleteBack);
        deleteback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(animScale);
                if (userEntered.length() > 0) {
                    userEntered = userEntered.substring(0, userEntered.length() - 1);
                }
                if(flag4 == true){
                    dot4.setImageResource(R.drawable.dot_empty);
                    flag4 = false;
                }
                else if(flag3 == true && flag4 == false){
                    dot3.setImageResource(R.drawable.dot_empty);
                    flag3 = false;
                }
                else if(flag2 == true && flag3 == false){
                    dot2.setImageResource(R.drawable.dot_empty);
                    flag2 = false;
                }
                else if(flag1 == true && flag2 == false){
                    dot1.setImageResource(R.drawable.dot_empty);
                    flag1 = false;
                }

            }
        });




        View.OnClickListener pinButtonHandler = new View.OnClickListener() {
            public void onClick(View v) {

                Button pressedButton = (Button) v;
                v.startAnimation(animScale);
                if (userEntered.length() < PIN_LENGTH) {
                    userEntered = userEntered + pressedButton.getText();
                    Log.v("PinView", "User entered=" + userEntered);

                    if (flag1 == false) {
                        dot1.setImageResource(R.drawable.dot_filled);
                        flag1 = true;
                    } else if (flag1 == true && flag2 == false) {
                        dot2.setImageResource(R.drawable.dot_filled);
                        flag2 = true;
                    } else if (flag2 == true && flag3 == false) {
                        dot3.setImageResource(R.drawable.dot_filled);
                        flag3 = true;
                    } else if (flag3 == true && flag4 == false) {
                        dot4.setImageResource(R.drawable.dot_filled);
                        flag4 = true;
                    }

                    if (userEntered.length() == PIN_LENGTH) {
                        //Check if entered PIN is correct
                        if (userEntered.equals(userPin)==true) {
                            //finish();
                            lockedimage.setImageResource(R.drawable.unlockedgrey);
                            /*try {
                                Thread.sleep(1000);
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }*/
                            //Toast.makeText(getApplicationContext(), "PinCode" + userEntered, Toast.LENGTH_SHORT).show();
                            userEntered = "";
                            resetFlags();
                            resetDots();
                            Intent i = new Intent(appContext, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("status", "Success");
                            startActivity(i);

                            //lockedimage.setImageResource(R.drawable.untitledgrey);

                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(), "Wrong PinCode", Toast.LENGTH_LONG);
                            toast.show();
                            //keyPadLockedFlag = true;
                            resetFlags();
                            resetDots();
                            userEntered = "";
                            Log.v("PinView", "Wrong PIN");
                            //new LockKeyPadOperation().execute("");
                        }
                    }
                }
                else {
                    //Roll over
                    userEntered = "";
                    resetFlags();
                    resetDots();
                    Toast toast = Toast.makeText(getApplicationContext(), "Only 4 digit password is allowed", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        };
        button0 = (Button) findViewById(R.id.button0);
        button0.setOnClickListener(pinButtonHandler);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(pinButtonHandler);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(pinButtonHandler);
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(pinButtonHandler);
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(pinButtonHandler);
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(pinButtonHandler);
        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(pinButtonHandler);
        button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(pinButtonHandler);
        button8 = (Button) findViewById(R.id.button8);
        button8.setOnClickListener(pinButtonHandler);
        button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(pinButtonHandler);
        deleteback = (ImageButton) findViewById(R.id.ImageDeleteBack);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void resetDots() {
        dot1 = (ImageView) findViewById(R.id.dot1);
        dot2 = (ImageView) findViewById(R.id.dot2);
        dot3 = (ImageView) findViewById(R.id.dot3);
        dot4 = (ImageView) findViewById(R.id.dot4);
        dot1.setImageResource(R.drawable.dot_empty);
        dot2.setImageResource(R.drawable.dot_empty);
        dot3.setImageResource(R.drawable.dot_empty);
        dot4.setImageResource(R.drawable.dot_empty);
    }

    public void resetFlags() {
        flag1=false;
        flag2=false;
        flag3=false;
        flag4=false;
    }
}
