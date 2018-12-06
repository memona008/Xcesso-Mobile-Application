package Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Memona Sultan on 5/19/2018.
 */

@Entity
public class Contact_Entity {
    @PrimaryKey
    @NonNull
    private String tag;

    @ColumnInfo(name = "number")
    private String number;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.
}
