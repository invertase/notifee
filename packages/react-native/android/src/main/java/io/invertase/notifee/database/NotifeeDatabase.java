package io.invertase.notifee.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import io.invertase.notifee.core.NotifeeContextHolder;

@Database(
  entities = {
    NotifeeNotificationEntity.class
  },
  version = 1,
  exportSchema = false
)
public abstract class NotifeeDatabase extends RoomDatabase {

  static NotifeeDatabase database;

  public static NotifeeDatabase getDatabase() {
    if (database != null) return database;
    database = Room.databaseBuilder(
      NotifeeContextHolder.getApplicationContext(),
      NotifeeDatabase.class,
      "notifee"
    ).build();
    return database;
  }

  public abstract NotifeeNotificationDao getDao();
}

