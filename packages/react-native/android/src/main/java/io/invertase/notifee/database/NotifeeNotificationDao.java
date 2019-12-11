package io.invertase.notifee.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotifeeNotificationDao {
  @Query("SELECT * from notifications")
  List<NotifeeNotificationEntity> getAll();

  @Insert
  void insertNotification(NotifeeNotificationEntity notificationEntity);

  @Update
  void updateNotification(NotifeeNotificationEntity notificationEntity);

  @Update
  void deleteNotification(NotifeeNotificationEntity notificationEntity);
}
