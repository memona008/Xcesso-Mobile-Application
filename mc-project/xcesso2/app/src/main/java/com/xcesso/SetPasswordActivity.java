package com.xcesso;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SetPasswordActivity extends AppCompatActivity {
    String userEnteredStr="";

    final int PIN_LENGTH = 4;
    Context appContext;

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
    Button confirmButton;
    ImageButton deleteback;
    EditText etEnterPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appContext = this;
        userEnteredStr = "";
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Animation animScale = AnimationUtils.loadAnimation(this,R.anim.scale);

        setContentView(R.layout.activity_set_password);

        etEnterPass = (EditText) findViewById(R.id.etEnterPassText);

        etEnterPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(appContext,"on click",Toast.LENGTH_SHORT).show();

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        });



        deleteback = (ImageButton) findViewById(R.id.ImageDeleteBack);
        deleteback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(animScale);
                int lastindex=0;
                //subsrting(0,2)=> 2 is exclusive index =>string from 0 to 1 index is returned
                if (userEnteredStr.length() > 0) {
                    lastindex = userEnteredStr.length()-1;
                    if(lastindex == 0){
                        userEnteredStr = "";
                        etEnterPass.setText("");
                        Log.i("main", "pincode"+userEnteredStr.substring(0, lastindex));
                    }
                    else {
                        Log.i("main", "pincode"+userEnteredStr.substring(0, lastindex));
                        userEnteredStr = userEnteredStr.substring(0, lastindex);
                        etEnterPass.setText(etEnterPass.getText().toString().substring(0, lastindex));
                        etEnterPass.setSelection(etEnterPass.getText().length());
                    }
                }
            }
        });

        View.OnClickListener pinButtonHandler = new View.OnClickListener() {
            public void onClick(View v) {

                Button pressedButton = (Button) v;
                v.startAnimation(animScale);
                if (userEnteredStr.length() < PIN_LENGTH) {
                    userEnteredStr = userEnteredStr + pressedButton.getText();
                    Log.v("PinView", "User entered=" + userEnteredStr);
                    etEnterPass.setText(etEnterPass.getText().toString() + "*");
                    etEnterPass.setSelection(etEnterPass.getText().toString().length());
                }
                else {
                    //Roll over
                    Toast toast = Toast.makeText(getApplicationContext(), "Only 4 digit password is allowed", Toast.LENGTH_LONG);
                    toast.show();
                    userEnteredStr = "";
                    etEnterPass.setText("");
                }
            }
        };

        confirmButton = (Button) findViewById(R.id.buttonConirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEnteredStr.length() == PIN_LENGTH) {
                    //Check if entered PIN's length is correct
                    SharedPreferences sp = getSharedPreferences(Globals.UserSharedPrefName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("Password", userEnteredStr);
                    ed.commit();

                    //Toast.makeText(getApplicationContext(), "PinCode " + userEnteredStr, Toast.LENGTH_LONG).show();

                    userEnteredStr = "";
                    etEnterPass.setText("");

                    Intent i = new Intent(appContext,PasswordActivity.class);
                    startActivity(i);

                }
                else{

                    Toast.makeText(getApplicationContext(), "Enter a 4 digit pincode", Toast.LENGTH_LONG).show();

                    userEnteredStr = "";
                    etEnterPass.setText("");
                }
            }
        });

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



}
