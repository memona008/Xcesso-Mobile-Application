package Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import RecyclerView_contacts.History;

/**
 * Created by Memona Sultan on 6/23/2018.
 */
@Dao
public interface HistoryDao {
    @Query("SELECT * FROM history_entity")
    List<History_Entity> getAllHistory();

    @Query("SELECT * FROM history_entity WHERE historyContact = (:t)")
    List<History_Entity> getHistoryOfContact(String t);

    @Insert()
    public void insertHistory(History_Entity history);

    @Delete
    void delete(History_Entity history);

    @Query("DELETE FROM history_entity")
    public void deleteAll();
}