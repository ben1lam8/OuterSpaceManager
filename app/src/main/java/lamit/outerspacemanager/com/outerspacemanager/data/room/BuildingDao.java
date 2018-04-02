package lamit.outerspacemanager.com.outerspacemanager.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
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
    void insert(Building building);

    @Query("UPDATE building SET name = :name, imageUrl = :imageUrl, level = :level, building = :building, effect = :effect, amountOfEffectByLevel = :amountOfEffectByLevel, amountOfEffectLevel0 = :amountOfEffectLevel0, gasCostByLevel = :gasCostByLevel, gasCostLevel0 = :gasCostLevel0, mineralCostByLevel = :mineralCostByLevel, mineralCostLevel0 = :mineralCostLevel0, timeToBuildByLevel = :timeToBuildByLevel, timeToBuildLevel0 = :timeToBuildLevel0 WHERE buildingId= :id")
    int update(
            int id,
            String name,
            String imageUrl,
            int level,
            boolean building,
            String effect,
            int amountOfEffectByLevel,
            int amountOfEffectLevel0,
            int gasCostByLevel,
            int gasCostLevel0,
            int mineralCostByLevel,
            int mineralCostLevel0,
            int timeToBuildByLevel,
            int timeToBuildLevel0
    );

    @Query("UPDATE building SET upgradeStart = :constructionStart, upgradeFinish = :constructionFinish WHERE buildingId = :id")
    int updateUpgrade(
            int id,
            Date constructionStart,
            Date constructionFinish
    );
}
