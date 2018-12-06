package com.xcesso;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static utils.Constants.CURRENT_CONTACTS;
import static utils.Constants.PICK_CONTACT_REQUEST_CODE;
import static utils.Constants.TOTAL_CONTACTS;

/**
 * Created by dell pc on 5/18/2018.
 */

public class AccessMobileFragment extends Fragment implements View.OnClickListener {
    View accessMobile;
    String option="";
    EditText contactsEditText;
    EditText filenameEditText;
    EditText emailEditText;
    TextView phone;
    TextView btnAddPhoneNoFromPhone;
    EditText password;
    String msgBody="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {



        accessMobile=inflater.inflate(R.layout.activity_access_mobile,container,false);
        contactsEditText=(EditText) accessMobile.findViewById(R.id.ed_contact_access_mobile);
        filenameEditText=(EditText) accessMobile.findViewById(R.id.ed_file_name_access_mobile);
        emailEditText=(EditText) accessMobile.findViewById(R.id.ed_email_access_mobile);
        password=(EditText)accessMobile.findViewById(R.id.ed_password_access_mobile);
        btnAddPhoneNoFromPhone=(TextView)accessMobile.findViewById(R.id.btn_add_contact_from_phone_access_mobile);
        btnAddPhoneNoFromPhone.setOnClickListener(this);
        phone=(TextView)accessMobile.findViewById(R.id.tx_phone_number_access_mobile);
        //phone.setText(phoneNo);
        Spinner acessType=(Spinner)accessMobile.findViewById(R.id.sp_type_send_sms);
        acessType.setPrompt("Acess Type");
        ArrayAdapter<CharSequence> adapAcess=ArrayAdapter.createFromResource(accessMobile.getContext(),R.array.access_types,
                android.R.layout.simple_spinner_item);
        adapAcess.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        acessType.setAdapter(adapAcess);
        acessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0) {
                    option = parent.getItemAtPosition(position).toString();
                    if (option.equals("contacts")) {
                        contactsEditText.setVisibility(View.VISIBLE);
                        filenameEditText.setVisibility(View.GONE);
                        emailEditText.setVisibility(View.GONE);
                    } else if (option.equals("file")) {
                        filenameEditText.setVisibility(View.VISIBLE);
                        emailEditText.setVisibility(View.VISIBLE);
                        contactsEditText.setVisibility(View.GONE);

                    } else {
                        contactsEditText.setVisibility(View.GONE);
                        filenameEditText.setVisibility(View.GONE);
                        emailEditText.setVisibility(View.GONE);
                        msgBody = "";
                    }
                }
                else {
                    option="";
                    contactsEditText.setVisibility(View.GONE);
                    filenameEditText.setVisibility(View.GONE);
                    emailEditText.setVisibility(View.GONE);
                    msgBody = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button fab=(Button) accessMobile.findViewById(R.id.btn_access_sms);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phoneNumber=phone.getText().toString();
                        if(phoneNumber.equals(""))
                        {
                            Toast t = Toast.makeText(accessMobile.getContext(), "Phone Number is required", Toast.LENGTH_LONG);
                            t.show();
                        }
                        else if(option.equals("") || (option.equals("contacts")&& contactsEditText.getText().toString().equals("")) )
                        {
                            Toast t = Toast.makeText(accessMobile.getContext(), "Select Every Field", Toast.LENGTH_LONG);
                            t.show();
                        }
                        else if(option.equals("") || (option.equals("file")&& (filenameEditText.getText().toString().equals("")
                                || emailEditText.getText().toString().equals("")) ) )
                        {
                            Toast t = Toast.makeText(accessMobile.getContext(), "Select Every Field", Toast.LENGTH_LONG);
                            t.show();
                        }
                        else {
                            if(option.equals("contacts"))
                            {
                                msgBody="";
                                msgBody=msgBody+" "+contactsEditText.getText().toString();

                            }
                            else if(option.equals("file"))
                            {
                                msgBody="";
                                msgBody=msgBody+" "+filenameEditText.getText().toString()+ " " + emailEditText.getText().toString();
                            }
                            else
                            {
                                msgBody="";
                            }
                            String message="";
                            message+=password.getText().toString();
                            message+=" ";
                            message+=option;
                            message+=" ";
                            message+=msgBody;
                            try {
                                sendMessage(phoneNumber, message);
                                Toast t = Toast.makeText(accessMobile.getContext(), "Message Sent", Toast.LENGTH_LONG);
                                t.show();
                            }
                            catch (Exception e)
                            {
                                Toast t = Toast.makeText(accessMobile.getContext(), "Message Sent Failed", Toast.LENGTH_LONG);
                                t.show();
                            }


                        }

                    }
                });
        return accessMobile;
    }



    public void sendMessage(String phoneNo, String msg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, msg, null, null);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_add_contact_from_phone_access_mobile)
        {
            v.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.anim_rotate));
            if(CURRENT_CONTACTS==TOTAL_CONTACTS){
                Toast.makeText(getActivity(), R.string.max_contacts_allowed_str,Toast.LENGTH_LONG).show();
            }else {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(i, PICK_CONTACT_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contactsData = data.getData();
                CursorLoader loader = new CursorLoader(getActivity(), contactsData, null, null, null, null);
                Cursor c = loader.loadInBackground();
                if (c.moveToFirst()) {
                    String phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phone.setText(phoneNumber);
                }
            }
        }
    }
}
