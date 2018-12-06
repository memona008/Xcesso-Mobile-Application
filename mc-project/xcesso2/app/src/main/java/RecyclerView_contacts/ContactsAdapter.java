package RecyclerView_contacts;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcesso.R;

import java.util.List;

import Database.Contact_Entity;

/**
 * Created by Memona Sultan  on 5/19/2018.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    public List<Contact_Entity> conList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView number;
        public MyViewHolder(View view) {
            super(view);
            number = (TextView) view.findViewById(R.id.number);
        }
    }

    public ContactsAdapter(List<Contact_Entity> l) {
        this.conList = l;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_cell, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Contact_Entity con = conList.get(position);

        holder.number.setText(con.getNumber());

    }

    @Override
    public int getItemCount() {
        return conList.size();
    }
}
