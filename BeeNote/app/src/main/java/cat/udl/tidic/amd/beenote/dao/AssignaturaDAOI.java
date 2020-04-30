package cat.udl.tidic.amd.beenote.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cat.udl.tidic.amd.beenote.models.Assignatures_Model;

@Dao
public interface AssignaturaDAOI {

    @Insert
    void insert(Assignatures_Model event);

    @Update
    void update(Assignatures_Model event);

    @Delete
    void delete(Assignatures_Model event);

    @Query("DELETE FROM event_table")
    void deleteAllEvents();

    @Query("SELECT * FROM event_table")
    LiveData<List<Assignatures_Model>> getAllEvents();

    @Query("SELECT * FROM event_table WHERE userId == :userId")
    LiveData<List<Assignatures_Model>> getAllEvents(int userId);

}
