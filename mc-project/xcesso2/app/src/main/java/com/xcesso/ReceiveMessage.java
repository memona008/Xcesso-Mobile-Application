package com.xcesso;

import android.annotation.SuppressLint;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.microedition.khronos.opengles.GL;

import Database.AppDatabase;
import Database.ContactDao;
import Database.Contact_Entity;
import Database.HistoryDao;
import Database.History_Entity;
import RecyclerView_contacts.Contacts;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by EG on 5/1/2018.
 */

public class ReceiveMessage extends BroadcastReceiver {
    LocationManager locationManager;
    LocationListener locationListener;
    HistoryDao Dao;
    String  passCode;
    String loc="https://www.google.com/maps/place/";
    @Override

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Dao = (HistoryDao) AppDatabase.getInstance(context).hd();
        SharedPreferences mySp=context.getSharedPreferences(Globals.UserSharedPrefName,Context.MODE_PRIVATE);
        passCode=mySp.getString("passCode","0000");
        SharedPreferences settings_sp = context.getSharedPreferences("settings_flags",Context.MODE_PRIVATE);

        locationManager=(LocationManager)context.getSystemService(LOCATION_SERVICE);
        if(bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            String format = bundle.getString("format");
            String msg ="";
            final SmsMessage[] messages = new SmsMessage[pdus.length];
            for(int i = 0; i < pdus.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                msg = messages[0].getMessageBody();
                String senderPhoneNo = messages[i].getDisplayOriginatingAddress();
                msg = msg.toLowerCase();

                //matching sender number with trusted contacts
                if (matchTrustedCOntacts(senderPhoneNo,context))
                {

                    //Seperating commands and passcode
                    StringTokenizer stringTokenizer = new StringTokenizer(msg, " ");
                    String passCode = "";
                    String command = "";


                    passCode = stringTokenizer.nextToken();
                    if (stringTokenizer.hasMoreTokens())
                        command = stringTokenizer.nextToken();
                    command = command.toLowerCase();
                    if (command.contains(Globals.locationCommand) && passCode.equals(passCode) && settings_sp.getString("location_flag","").equals("true")) {
                        loc = "https://www.google.com/maps/place/";
                        sendLocation(senderPhoneNo);
                    } else if (command.contains(Globals.contactCommand) && passCode.equals(passCode) && settings_sp.getString("contacts_flag","").equals("true")) {

                        String contactName = "";
                        while (stringTokenizer.hasMoreTokens()) {
                            contactName = contactName+stringTokenizer.nextToken() + " ";
                        }

                        String msgToSend = GetContact(contactName, context);
                        if (msgToSend == null && settings_sp.getString("contacts_flag","").equals("true"))
                            msgToSend = "Cant find such contact ! "+ contactName;
                        else if(settings_sp.getString("contacts_flag","").equals("false"))
                            msgToSend = "Access Denied!";

                        sendMessage(senderPhoneNo, msgToSend);
                        Toast.makeText(context, msgToSend, Toast.LENGTH_LONG).show();

                    } else if (command.equals(Globals.profileCommand) && settings_sp.getString("profile_flag","").equals("true")) {
                        String mode = "";
                        if (stringTokenizer.hasMoreTokens()) {
                            mode = stringTokenizer.nextToken();
                            mode.toLowerCase();
                        }
                        try {
                            if (mode.equals("") || mode.equals("general")) {
                                AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                sendMessage(senderPhoneNo, "Mode Changed Succesfully to General");
                                am = null;
                            } else if (mode.equals("vibrate")) {
                                AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                                am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                sendMessage(senderPhoneNo, "Mode Changed Succesfully to Vibrate");
                                am = null;
                            }
                        } catch (Exception ex) {
                            sendMessage(senderPhoneNo, "Can't Change Mode");
                        }
                    }else if (command.equals(Globals.fileCommand) && passCode.equals(passCode) && settings_sp.getString("sd_card_flag","").equals("true"))
                    {
                        SharedPreferences sp = context.getSharedPreferences(Globals.UserSharedPrefName,Context.MODE_PRIVATE);
                        String fileName="";
                        String email=sp.getString("email","");
                        fileName=stringTokenizer.nextToken();
                        if(stringTokenizer.hasMoreTokens())
                            email=stringTokenizer.nextToken();

                        sendFile(context,fileName,email);
                    }
                    //   Toast.makeText(context, "Message " + messages[0].getMessageBody() + ", from " + senderPhoneNo, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @SuppressLint("MissingPermission")
    public void sendLocation(final String Num)
    {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                loc=loc+location.getLatitude() +"," +location.getLongitude();
                sendMessage(Num,loc);
                locationManager.removeUpdates(locationListener);
                locationListener = null;

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                // Location location=locationManager.getLastKnownLocation(provider);
                //loc=loc+location.getLatitude() +"," +location.getLongitude();
                //sendMessage(Num,loc);
                //locationManager.removeUpdates(locationListener); //Pending for checking
                //locationListener = null;  //Pending for checking
            }
        };

        try
        {
            locationManager.requestLocationUpdates("gps",2000,0,locationListener);
            // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
//
//  Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            loc=loc+location.getLatitude() +"," +location.getLongitude();
//            sendMessage(Num,loc);

        }catch (Exception ex)
        {
            sendMessage(Num,ex.getMessage());
        }

    }
    private Boolean matchTrustedCOntacts(String senderPhoneNo,Context context)
    {
        ContactDao Dao = (ContactDao) AppDatabase.getInstance(context).cd();
        List<Contact_Entity> trustedContacts= Dao.getAll();
        for(int j =0 ;j<trustedContacts.size();j++)
        {
            String c1 = trustedContacts.get(j).getNumber();
            String c2 = senderPhoneNo;
            if(c1.charAt(0)=='0')
            {
                c1= c1.substring(1);
            }else
            {
                c1=c1.substring(3);
            }
            //for removing spaces
            c1=c1.replaceAll("\\s+","");



            if(c2.charAt(0)=='0')
            {
                c2= c2.substring(1);
            }else
            {
                c2=c2.substring(3);
            }

            if(c2.equals(c1))
                return true;
        }

        return  false;
    }
    private String GetContact(String nameSearch,Context mContext)
    {
        Cursor phones = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name=name.toLowerCase();
            name=name.trim();
            nameSearch=nameSearch.toLowerCase();
            nameSearch=nameSearch.trim();
            if(name.equals(nameSearch))
            {
                phones.close();
                return phoneNumber ;
            }

        }
        phones.close();
        return null;
    }

    //Yet to test
    private void sendFile(Context context,String fileName,String email)
    {
        WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        // SharedPreferences sp = context.getSharedPreferences(Globals.UserSharedPrefName,Context.MODE_PRIVATE);
        try {

            SendMail sm = new SendMail(context, email, Globals.DEFAULT_EMAIL_SUBJECT, Globals.DEFAULT_EMAIL_MESSAGE, fileName);
            sm.execute();
        }catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void sendMessage(String phoneNo,String msg)
    {
        saveHistory(phoneNo,msg);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, msg, null, null);

    }
    public void saveHistory(String con , String msg)
    {
        History_Entity h = new History_Entity();
        h.setHistoryContact(con);
        h.setHistoryMessage(msg);
        String now = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        h.setHistoryTime(now);
        Dao.insertHistory(h);
    }

}
