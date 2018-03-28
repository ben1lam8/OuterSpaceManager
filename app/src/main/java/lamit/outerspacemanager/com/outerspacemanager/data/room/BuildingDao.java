package lamit.outerspacemanager.com.outerspacemanager.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import lamit.outerspacemanager.com.outerspacemanager.model.Building;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface BuildingDao {

    @Query("SELECT * FROM building WHERE buildingId = :id")
    LiveData<Building> loadById(int id);

    @Query("SELECT * FROM building")
    LiveData<List<Building>> loadAll();

    @Insert(onConflict = REPLACE)
    void save(Building building);
}

