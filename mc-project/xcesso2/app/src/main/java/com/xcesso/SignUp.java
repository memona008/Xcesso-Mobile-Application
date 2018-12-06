package com.xcesso;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity implements View.OnClickListener {

    Button btn;
    EditText name,email,phone;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btn = findViewById(R.id.btn_sign_up);

        name = findViewById(R.id.user_name);
        phone = findViewById(R.id.user_phone);
        email = findViewById(R.id.user_email);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_sign_up)
        {

            String n =name.getText().toString();
            String e = email.getText().toString();
            String p = phone.getText().toString();



            if(n.length()==0 || n.trim().equals("") || p.length()==0 || p.trim().equals("")  ||e.length()==0 || e.trim().equals("")  )
            {
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_LONG).show();
            }
            else
            {
                sp = getSharedPreferences(Globals.UserSharedPrefName, Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("name", n);
                ed.putString("phone", p);
                ed.putString("email", e);
                ed.putString("passCode","0000");
                ed.commit();

                if(!sp.contains("location_flag"))
                {
                    ed.putString("location_flag","false");
                }
                if(!sp.contains("profile_flag"))
                {
                    ed.putString("profile_flag","true");
                }
                if(!sp.contains("sd_card_flag"))
                {
                    ed.putString("sd_card_flag","true");
                }
                if(!sp.contains("contacts_flag"))
                {
                    ed.putString("contacts_flag","true");
                }

                Intent i = new Intent(this, SetPasswordActivity.class);
                startActivity(i);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public boolean isPermissionGranted() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermissions() {

        ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.SEND_SMS}, 100);
    }
}
