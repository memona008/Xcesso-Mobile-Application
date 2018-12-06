package com.xcesso;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import Database.*;
import RecyclerView_contacts.ContactsAdapter;
import RecyclerView_contacts.SwipeController;
import RecyclerView_contacts.SwipeControllerActions;
import utils.EditDialoge;

import java.util.ArrayList;
import java.util.List;

import static utils.Constants.CURRENT_CONTACTS;
import static utils.Constants.PICK_CONTACT_REQUEST_CODE;
import static utils.Constants.TOTAL_CONTACTS;


public class EditContacts extends Fragment implements View.OnClickListener {

    private List<Contact_Entity> conList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ContactsAdapter cAdapter;
    View fragmentView;
    SwipeController swipeController=null;
    ImageView logo;
    TextView btn_newContact;
    TextView btn_existingContact;
    Animation animRotate;
    Animation.AnimationListener animationListener = new Animation.AnimationListener(){

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub

        }};   // Animation Listener class
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
       getActivity().setTitle(R.string.contact_fragment_title);
        // load the fragment
        fragmentView =  inflater.inflate(R.layout.activity_edit_contacts, container, false);
        // initializing the objects
        recyclerView = (RecyclerView)fragmentView.findViewById(R.id.recycler_view);
        logo = fragmentView.findViewById(R.id.logo2);
        animRotate = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_rotate);
        btn_newContact = fragmentView.findViewById(R.id.btn_add_contact_on_edit_screen);
        btn_existingContact = fragmentView.findViewById(R.id.btn_add_contact_from_phone);
        recyclerView.setHasFixedSize(true);
        return fragmentView;
    }
    /*_____________________________________________________________________*/
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /*_____________________________________________________________________*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_newContact.setOnClickListener(this);
        btn_existingContact.setOnClickListener(this);
        animRotate.setAnimationListener(animationListener);
    }
    /*_____________________________________________________________________*/
    void prepareContactsData()
    {
        ContactDao Dao = (ContactDao) AppDatabase.getInstance(getActivity()).cd();
        conList = Dao.getAll();
        cAdapter = new ContactsAdapter(conList);
        cAdapter.notifyDataSetChanged();
    }
    /*_____________________________________________________________________*/
    @Override
    public void onResume() {
        super.onResume();
        logo.startAnimation(animRotate);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        prepareContactsData();
        recyclerView.setAdapter(cAdapter);
        // swipe controll work
        if(swipeController==null) {
            swipeController = new SwipeController(new SwipeControllerActions() {
                // Delete button
                @Override
                public void onRightClicked(final int position) {

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.del_confirm_title)
                            .setMessage(R.string.confirm_dele_str)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ContactDao cd = AppDatabase.getInstance(getActivity()).cd();
                                    cd.delete(conList.get(position));
                                    cAdapter.conList.remove(position);
                                    CURRENT_CONTACTS--;
                                    cAdapter.notifyItemRemoved(position);
                                    cAdapter.notifyItemRangeChanged(position, cAdapter.getItemCount());
                                    // reset all tags of contacts
                                    for (int i = 1; i <= CURRENT_CONTACTS; i++) {
                                        Contact_Entity ce = cAdapter.conList.get(i - 1);
                                        cd.delete(ce);
                                        ce.setTag("contact" + Integer.toString(i));
                                        cd.insertContact(ce);
                                    }
                                }
                            })
                            .setNegativeButton("No", null);

                    builder.show();

                }

                // EDIT button
                @Override
                public void onLeftClicked(int position) {

                    ContactDao cd = AppDatabase.getInstance(getActivity()).cd();
                    Contact_Entity ct = conList.get(position);
                    String displaytext = getString(R.string.edit_contact);
                    String btnText = getString(R.string.edit_str);
                    EditDialoge dialog = new EditDialoge(getActivity(), displaytext, btnText);
                    dialog.editContact(R.layout.dialog_layout, ct, conList, cAdapter, position);
                    cAdapter.notifyItemChanged(position);

                }
            });
        }
        // attaching swipe controller to recycler view
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }


    /*_____________________________________________________________________*/
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.btn_add_contact_on_edit_screen)
        {

            v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_rotate));
            if(CURRENT_CONTACTS==TOTAL_CONTACTS){
                Toast.makeText(getActivity(), R.string.max_contacts_allowed_str,Toast.LENGTH_LONG).show();
            }else {
                String displaytext = getString(R.string.add_new_contact);
                String btnText = getString(R.string.add);
                EditDialoge dialog = new EditDialoge(getActivity(), displaytext, btnText);
                dialog.addContact(R.layout.dialog_layout, conList, cAdapter);
            }
        }
        else if(v.getId()==R.id.btn_add_contact_from_phone)
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
                    addContact(phoneNumber);
                }
            }
        }
    }

    void addContact(String p_num)
    {
        ContactDao Dao = (ContactDao) AppDatabase.getInstance(getActivity()).cd();
        List<Contact_Entity> db_contacts =Dao.getAll();
        CURRENT_CONTACTS = db_contacts.size();
        int count = CURRENT_CONTACTS;
        if (count < TOTAL_CONTACTS) {

            String tag = getString(R.string.tag_contact_str) + Integer.toString(count + 1);
            Contact_Entity ce = new Contact_Entity();
            ce.setTag(tag);
            ce.setNumber(p_num);
            Dao.insertContact(ce);
            conList.add(ce);
            int pos = conList.size();
            cAdapter.notifyItemInserted(pos);
            cAdapter.notifyItemRangeInserted(pos,cAdapter.getItemCount());
            CURRENT_CONTACTS++;

        }

    }
}
