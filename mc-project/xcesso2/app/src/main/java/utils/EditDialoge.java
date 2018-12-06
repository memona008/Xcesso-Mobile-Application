package utils;

/**
 * Created by Memona Sultan on 5/19/2018.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xcesso.R;

import java.util.List;

import Database.AppDatabase;
import Database.ContactDao;
import Database.Contact_Entity;
import RecyclerView_contacts.ContactsAdapter;

import static utils.Constants.CURRENT_CONTACTS;
import static utils.Constants.TOTAL_CONTACTS;


public class EditDialoge {

    private Context context;
    private String displayTitle;
    private String btnText;

    public EditDialoge(Context context,String dt , String btn) {
        this.context = context;
        this.displayTitle = dt;
        this.btnText = btn;
    }

    public void editContact(int dialog_layout, Contact_Entity ct, final List<Contact_Entity> list, final ContactsAdapter cAdapter , final int position)
    {

        final Contact_Entity ect=ct;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater inflater = LayoutInflater.from(context);
            View subView = inflater.inflate(dialog_layout, null);
            final EditText nameField= (EditText)subView.findViewById(R.id.enter_message);
            builder.setTitle(displayTitle);
            builder.setView(subView);
            builder.setMessage(ect.getTag());
            builder.create();



        builder.setPositiveButton(btnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ContactDao messageDao = (ContactDao) AppDatabase.getInstance(context).cd();
                final String message = nameField.getText().toString();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(context, "Fill the field first", Toast.LENGTH_LONG).show();
                }
                else{
// edit dialoge
                    if(displayTitle.equals("Edit Contact") && btnText.equals("EDIT")) {
                        Contact_Entity content = new Contact_Entity();
                        content.setTag(ect.getTag());
                        content.setNumber(message);
                        list.set(position,content);
                        messageDao.insertContact(content);
                        cAdapter.notifyItemRangeChanged(position,cAdapter.getItemCount());

                    }
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something on cancel
            }
        });
        builder.show();
    }

    public void addContact(int dialog_layout,List<Contact_Entity> list,final ContactsAdapter cAdapter)
    {

            final EditText nameField;
            final List<Contact_Entity> fin_list = list;

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View subView = inflater.inflate(dialog_layout, null);
            nameField = (EditText)subView.findViewById(R.id.enter_message);
            builder = new AlertDialog.Builder(context);
            builder.setTitle(displayTitle);
            builder.setView(subView);
            builder.create();

        builder.setPositiveButton(btnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ContactDao messageDao = (ContactDao) AppDatabase.getInstance(context).cd();
                final String message = nameField.getText().toString();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(context, "Fill the field first", Toast.LENGTH_LONG).show();
                }
                else{
// edit dialoge

                        // add dialoge
                        messageDao = (ContactDao) AppDatabase.getInstance(context).cd();
                        //logo.startAnimation(animRotate);
                        List<Contact_Entity> db_contacts = messageDao.getAll();
                        CURRENT_CONTACTS = db_contacts.size();
                        int count = CURRENT_CONTACTS;
                        if (count < TOTAL_CONTACTS) {

                            String tag = "contact" + Integer.toString(count + 1);
                            Contact_Entity ce = new Contact_Entity();
                            ce.setTag(tag);
                            ce.setNumber(message);
                            messageDao.insertContact(ce);
                            fin_list.add(ce);
                            int pos = fin_list.size();
                            cAdapter.notifyItemInserted(pos);
                            cAdapter.notifyItemRangeInserted(pos,cAdapter.getItemCount());
                            CURRENT_CONTACTS++;

                        }
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do something on cancel
            }
        });
        builder.show();

    }
}

