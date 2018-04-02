package lamit.outerspacemanager.com.outerspacemanager.data.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.TypeConverters;
import lamit.outerspacemanager.com.outerspacemanager.model.Building;
import lamit.outerspacemanager.com.outerspacemanager.model.Search;
import lamit.outerspacemanager.com.outerspacemanager.model.Ship;
import lamit.outerspacemanager.com.outerspacemanager.model.User;

@Database(entities = {User.class, Building.class, Ship.class, Search.class}, version = 8)
@TypeConverters(DateConverter.class)
public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase{

    public abstract UserDao         userDao();
    public abstract BuildingDao     buildingDao();
    public abstract ShipDao         shipDao();
    public abstract SearchDao       searchDao();

}
