package com.xcesso;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import java.util.StringTokenizer;

import Database.AppDatabase;
import Database.ContactDao;
import Database.Contact_Entity;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Button btnStop;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.btn_go);
        btnStop = (Button)findViewById(R.id.btn_stop);

        context=this;
        Globals.isActive=isPermissionGranted();
        if(Globals.isActive==true)
            btn.setText("Start");
        else {
            btn.setText("GO");
        }

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringTokenizer stringTokenizer = new StringTokenizer("0000 files kfc"," ");
                String a = stringTokenizer.nextToken();
                String b = stringTokenizer.nextToken();
                String c =stringTokenizer.toString();

//                while (stringTokenizer.hasMoreTokens())
  //              {
    //                c=c+stringTokenizer.nextToken();
      //          }
                c=c;

//                SharedPreferences sp = context.getSharedPreferences(Globals.UserSharedPrefName,Context.MODE_PRIVATE);
//                try {
//
//                    SendMail sm = new SendMail(context, sp.getString("email", "shaheryartariq909@gmail.com"), Globals.DEFAULT_EMAIL_SUBJECT, Globals.DEFAULT_EMAIL_MESSAGE, "KFC");
//                    sm.execute();
//                }catch (Exception ex)
//                {
//                    Toast.makeText(context,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
//                }
            }
        });

        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Globals.isActive==true)
                {
                    Intent i=new Intent(MainActivity.this,nav_drawer.class);
                    startActivity(i);
                }
                if(isPermissionGranted()!=true)
                {
                    requestPermissions();
                    Globals.isActive=false;
                }

                if(isPermissionGranted()==true) {
                    Globals.isActive=true;

                    v.startAnimation(anim);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please give all Permissions to proceed !",Toast.LENGTH_LONG).show();
                }

            }
        });

        Animation.AnimationListener animationListener = new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                //btn.setBackgroundColor(getResources().getColor());

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }};
        anim.setAnimationListener(animationListener);


    }



    public boolean isPermissionGranted() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermissions() {

        ActivityCompat.requestPermissions(this, Globals.permissionsList, 100);
    }

}
