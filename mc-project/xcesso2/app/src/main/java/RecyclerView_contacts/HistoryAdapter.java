package RecyclerView_contacts;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xcesso.R;

import org.w3c.dom.Text;

import java.util.List;

import Database.Contact_Entity;
import Database.History_Entity;

/**
 * Created by Memona Sultan  on 6/23/2018.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    public List<History_Entity> hisList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView historyContact;
        public TextView historyMessage;
        public TextView historyTime;
       // public TextView historyDate;

        public MyViewHolder(View view) {
            super(view);
            historyContact = (TextView) view.findViewById(R.id.historyContact);
            historyMessage = (TextView) view.findViewById(R.id.historyMessage);
            historyTime = (TextView) view.findViewById(R.id.historyTime);
           // historyDate = (TextView) view.findViewById(R.id.historyDate);
        }
    }

    public HistoryAdapter(List<History_Entity> l) {
        this.hisList = l;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_cell, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        History_Entity his = hisList.get(position);

        holder.historyContact.setText(his.getHistoryContact());
        holder.historyMessage.setText(his.getHistoryMessage());
        holder.historyTime.setText(his.getHistoryTime());
       // holder.historyDate.setText(his.getHistoryDate());

    }

    @Override
    public int getItemCount() {
        return hisList.size();
    }
}
