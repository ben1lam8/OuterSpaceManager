package lamit.outerspacemanager.com.outerspacemanager.data.room;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import lamit.outerspacemanager.com.outerspacemanager.model.Search;
import lamit.outerspacemanager.com.outerspacemanager.model.Ship;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface SearchDao {

    @Query("SELECT * FROM search WHERE searchId = :id")
    LiveData<Search> loadById(int id);

    @Query("SELECT * FROM search")
    LiveData<List<Search>> loadAll();

    @Insert(onConflict = REPLACE)
    void insert(Search search);

    @Query("UPDATE search SET name = :name, level = :level, building = :building, effect = :effect, amountOfEffectByLevel = :amountOfEffectByLevel, amountOfEffectLevel0 = :amountOfEffectLevel0, gasCostByLevel = :gasCostByLevel, gasCostLevel0 = :gasCostLevel0, mineralCostByLevel = :mineralCostByLevel, mineralCostLevel0 = :mineralCostLevel0, timeToBuildByLevel = :timeToBuildByLevel, timeToBuildLevel0 = :timeToBuildLevel0 WHERE searchId = :id")
    int update(
            int id,
            String name,
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

    @Query("UPDATE search SET upgradeStart = :constructionStart, upgradeFinish = :constructionFinish WHERE searchId = :id")
    int updateUpgrade(
            int id,
            Date constructionStart,
            Date constructionFinish
    );
}

