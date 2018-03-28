package lamit.outerspacemanager.com.outerspacemanager.data.room;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import lamit.outerspacemanager.com.outerspacemanager.model.Search;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface SearchDao {

    @Query("SELECT * FROM search WHERE searchId = :id")
    LiveData<Search> loadById(int id);

    @Insert(onConflict = REPLACE)
    void save(Search search);
}

