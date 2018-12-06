package Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Memona Sultan on 5/19/2018.
 */

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contact_entity")
    List<Contact_Entity> getAll();

    @Query("SELECT * FROM contact_entity WHERE tag = (:t)")
    Contact_Entity getContact(String t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertContact(Contact_Entity user);

    @Delete
    void delete(Contact_Entity user);
}
