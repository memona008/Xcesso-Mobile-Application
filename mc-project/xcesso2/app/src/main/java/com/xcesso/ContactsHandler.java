package com.xcesso;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class ContactsHandler extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_handler);
       loadFragment(new EditContacts());
    }
    private void loadFragment(Fragment fragment) {
       FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
      fragmentTransaction.replace(R.id.frameLayout,fragment);
       fragmentTransaction.commit();
   }
}
