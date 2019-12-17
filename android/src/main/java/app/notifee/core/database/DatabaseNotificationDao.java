package app.notifee.core.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface DatabaseNotificationDao {
  @Query("SELECT * from notifications")
  List<DatabaseNotificationEntity> getAll();


  @Query(
    "DELETE FROM notifications where id NOT IN (" +
      "SELECT id from notifications ORDER BY id DESC " +
      "LIMIT 150)"
  )
  void trimRows();

  @Query("SELECT * FROM notifications WHERE id=:id")
  DatabaseNotificationEntity[] getById(String id);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(DatabaseNotificationEntity notificationEntity);

  @Update
  void update(DatabaseNotificationEntity notificationEntity);

  @Delete
  void delete(DatabaseNotificationEntity notificationEntity);
}
