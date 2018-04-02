package lamit.outerspacemanager.com.outerspacemanager.data.room;


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
    void insert(Ship ship);

    @Query("UPDATE ship SET name = :name, life = :life, shield = :shield, minAttack = :minAttack, maxAttack = :maxAttack, speed = :speed, mineralCost = :mineralCost, gasCost = :gasCost, spatioportLevelNeeded = :spatioportLevelNeeded, timeToBuild = :timeToBuild WHERE shipId = :id")
    int update(
            int id,
            String name,
            int life,
            int shield,
            int minAttack,
            int maxAttack,
            int speed,
            int mineralCost,
            int gasCost,
            int spatioportLevelNeeded,
            int timeToBuild
    );

    @Query("UPDATE ship SET builtAmount = :builtAmount WHERE shipId = :id")
    int updateBuiltAmount(int id, int builtAmount);

    @Query("UPDATE ship SET totalAmount = :totalAmount WHERE shipId = :id")
    int updateTotalAmount(int id, int totalAmount);
}

