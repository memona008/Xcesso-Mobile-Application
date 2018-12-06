package Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import javax.annotation.Nonnull;

/**
 * Created by Memona Sultan on 5/19/2018.
 */

@Entity
public class History_Entity {

    @ColumnInfo(name = "historyContact")
    private String historyContact;
    @ColumnInfo(name = "historyMessage")
    private String historyMessage;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "historyTime")
    private String historyTime;

    public String getHistoryContact() {
        return historyContact;
    }

    public void setHistoryContact(String historyContact) {
        this.historyContact = historyContact;
    }

    public String getHistoryMessage() {
        return historyMessage;
    }

    public void setHistoryMessage(String historyMessage) {
        this.historyMessage = historyMessage;
    }

    public String getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(String historyTime) {
        this.historyTime = historyTime;
    }


    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.
}
