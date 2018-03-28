package lamit.outerspacemanager.com.outerspacemanager.datasource.room;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import lamit.outerspacemanager.com.outerspacemanager.model.Ship;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface ShipDao {

    @Query("SELECT * FROM ship WHERE shipId = :id")
    LiveData<Ship> loadById(int id);

    @Query("SELECT * FROM ship")
    LiveData<List<Ship>> loadAll();

    @Insert(onConflict = REPLACE)
    void save(Ship ship);
}

