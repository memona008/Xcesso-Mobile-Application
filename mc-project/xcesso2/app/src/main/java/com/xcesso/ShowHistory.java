package com.xcesso;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import java.util.Calendar;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import Database.*;
import RecyclerView_contacts.ContactsAdapter;
import RecyclerView_contacts.History;
import RecyclerView_contacts.HistoryAdapter;
import RecyclerView_contacts.RecyclerTouchListener;
import RecyclerView_contacts.SwipeController;
import RecyclerView_contacts.SwipeControllerActions;
import utils.EditDialoge;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static utils.Constants.CURRENT_CONTACTS;
import static utils.Constants.PICK_CONTACT_REQUEST_CODE;
import static utils.Constants.TOTAL_CONTACTS;


public class ShowHistory extends Fragment implements  View.OnClickListener {

    private List<History_Entity> hisList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HistoryAdapter hAdapter;
    View fragmentView;
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // load the fragment
        fragmentView =  inflater.inflate(R.layout.history_layout, container, false);
        recyclerView = (RecyclerView)fragmentView.findViewById(R.id.recycler_view_history);
        btn = fragmentView.findViewById(R.id.clear_all_history);
        btn.setOnClickListener(this);
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

    }
    /*_____________________________________________________________________*/
    void prepareContactsData()
    {
        HistoryDao Dao = (HistoryDao) AppDatabase.getInstance(getActivity()).hd();
        hisList = Dao.getAllHistory();
        hAdapter = new HistoryAdapter(hisList);
        hAdapter.notifyDataSetChanged();
    }
    /*_____________________________________________________________________*/
    @Override
    public void onResume() {
        super.onResume();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        prepareContactsData();
        recyclerView.setAdapter(hAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
              Toast.makeText(getActivity(), R.string.history_del_info,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, final int position) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                final AlertDialog.Builder no = builder.setTitle(R.string.del_confirm_title)
                        .setMessage(R.string.str_history_del_confirm)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                HistoryDao hd = AppDatabase.getInstance(getActivity()).hd();
                                hd.delete(hisList.get(position));
                                hAdapter.hisList.remove(position);
                                hAdapter.notifyItemRemoved(position);
                                hAdapter.notifyItemRangeChanged(position, hAdapter.getItemCount());
                            }
                        })
                        .setNegativeButton("No", null);

                builder.show();
            }
        }));


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.clear_all_history)
        {
            HistoryDao hd = AppDatabase.getInstance(getActivity()).hd();
             hd.deleteAll();
            hisList.clear();
           hAdapter.notifyDataSetChanged();
        }
    }

    /*_____________________________________________________________________*/

}
