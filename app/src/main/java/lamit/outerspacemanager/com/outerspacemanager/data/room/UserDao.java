package lamit.outerspacemanager.com.outerspacemanager.data.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;

import lamit.outerspacemanager.com.outerspacemanager.model.User;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE username = :username")
    LiveData<User> loadByUsername(String username);

    @Query("SELECT * FROM user WHERE lastConnection IS NOT NULL AND expires > :now ORDER BY lastConnection DESC LIMIT 1")
    LiveData<User> loadLastConnectedFreshUser(Date now);

    @Insert(onConflict = REPLACE)
    void save(User user);
}

