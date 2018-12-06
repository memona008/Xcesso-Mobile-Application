package com.xcesso;

/**
 * Created by Memona Sultan on 6/30/2018.
 */

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;

import static android.content.Context.LOCATION_SERVICE;

public class XcessoSettings extends Fragment{
    Switch location,contacts,sd_card,profile;
    View fragmentView;
    SharedPreferences sp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.activity_settings, container, false);
        location = fragmentView.findViewById(R.id.location_switch);
        contacts = fragmentView.findViewById(R.id.contacts_switch);
        sd_card = fragmentView.findViewById(R.id.images_switch);
        profile = fragmentView.findViewById(R.id.phone_profile_switch);
        return fragmentView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocationManager lm = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);

        SharedPreferences sp = this.getActivity().getSharedPreferences("settings_flags", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
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
        if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER) && sp.getString("location_flag","").equals("true"))
            location.setChecked(true);
        else {
            location.setChecked(false);
            ed.putString("location_flag","false");
            ed.apply();
        }
        if(sp.getString("contacts_flag","").equals("true"))
            contacts.setChecked(true);
        else
            contacts.setChecked(false);
        if(sp.getString("profile_flag","").equals("true"))
            profile.setChecked(true);
        else
            profile.setChecked(false);
        if(sp.getString("sd_card_flag","").equals("true"))
            sd_card.setChecked(true);
        else
            sd_card.setChecked(false);
    }


    @Override
    public void onResume() {
        super.onResume();
//
        sp = getActivity().getSharedPreferences("settings_flags", Context.MODE_PRIVATE);
        final SharedPreferences.Editor ed = sp.edit();
        //
        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LocationManager lm = (LocationManager)
                            getActivity().getSystemService(Context.LOCATION_SERVICE);
                    if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        // Globals.location_flag = true;

                        ed.putString("location_flag", "true");
                        ed.apply();
                    }
                    else{
                        location.setChecked(false);
                        // Globals.location_flag=false;

                        ed.putString("location_flag", "false");
                        ed.apply();
                        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(i);
                    }

                }
                else { // Globals.location_flag=false;
                    ed.putString("location_flag", "false");
                }
            }
        });


        contacts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ed.putString("contacts_flag", "true");
                else
                    ed.putString("contacts_flag", "false");
                ed.apply();
            }
        });

        profile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ed.putString("profile_flag", "true");
                else
                    ed.putString("profile_flag", "false");
                ed.apply();
            }
        });


        sd_card.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ed.putString("sd_card_flag", "true");
                else
                    ed.putString("sd_card_flag", "false");
                ed.apply();
            }
        });


    }


}
