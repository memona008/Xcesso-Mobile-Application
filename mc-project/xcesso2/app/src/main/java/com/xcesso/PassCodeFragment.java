package com.xcesso;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by EG on 6/24/2018.
 */

public class PassCodeFragment extends Fragment {
    Button btnChangePassCode;
    EditText newPassCode;
    TextView passCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.passcode_fragment_title);
        View view =inflater.inflate(R.layout.activity_change_passcode,container,false);

        passCode= (TextView)(view.findViewById(R.id.tv_passcode));
        //String c1 ="00  00";
        SharedPreferences sp = getActivity().getSharedPreferences(Globals.UserSharedPrefName, Context.MODE_PRIVATE);
        final SharedPreferences.Editor et = sp.edit();
        passCode.setText(sp.getString("passCode","0000"));

        btnChangePassCode= (Button)view.findViewById(R.id.btn_change_passcode);
        newPassCode=(EditText)(view.findViewById(R.id.et_new_passcode));
        btnChangePassCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(newPassCode.getText().toString().length()>=4)
                {
                    et.putString("passCode",newPassCode.getText().toString());
                    et.commit();
                    passCode.setText(newPassCode.getText().toString());
                    Toast.makeText(getActivity(),"Your Text PassCode has been Changed",Toast.LENGTH_LONG).show();

                }else
                {
                    Toast.makeText(getActivity(),"Passcode must be atleast 4 Characters long !",Toast.LENGTH_LONG).show();
                }
            }
        });




        return view;
    }
    //public View
}
