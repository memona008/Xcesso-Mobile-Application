
package com.xcesso;

import android.app.Fragment;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Database.AppDatabase;
import Database.ContactDao;
import Database.Contact_Entity;
import utils.Constants;

/**
 * Created by EG on 5/19/2018.
 */

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

       getActivity().setTitle("Profile");
        View view =inflater.inflate(R.layout.activity_profile,container,false);
        if(Globals.isActive)
            ((TextView)view.findViewById(R.id.tv_profile_appStatus)).setText("Active");
        else
            ((TextView)view.findViewById(R.id.tv_profile_appStatus)).setText("DeActivated");
SharedPreferences sp = getActivity().getSharedPreferences(Globals.UserSharedPrefName,Context.MODE_PRIVATE);
        ((TextView)view.findViewById(R.id.tv_profile_passcode)).setText(sp.getString("passCode","0000"));

      //  SharedPreferences mySp = this.getActivity().getSharedPreferences(Globals.UserSharedPrefName, Context.MODE_PRIVATE);
        ((TextView)view.findViewById(R.id.tv_profile_email)).setText(sp.getString("email",""));
        ((TextView)view.findViewById(R.id.tv_profile_name)).setText(sp.getString("name",""));
        ((TextView)view.findViewById(R.id.tv_profile_phone)).setText(sp.getString("phone",""));
        ContactDao Dao = (ContactDao) AppDatabase.getInstance(getActivity()).cd();
        List<Contact_Entity> trustedContacts= Dao.getAll();
        ((TextView)view.findViewById(R.id.tv_profile_contactsCount)).setText(String.valueOf(trustedContacts.size()));

        return view;
    }
}
