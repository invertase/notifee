package io.invertase.notifee.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotifeeNotificationDao {
  @Query("SELECT * from notifications")
  List<NotifeeNotificationEntity> getAll();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(NotifeeNotificationEntity notificationEntity);

  @Update
  void update(NotifeeNotificationEntity notificationEntity);

  @Delete
  void delete(NotifeeNotificationEntity notificationEntity);
}
