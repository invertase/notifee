package io.invertase.notifee.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import io.invertase.notifee.core.NotifeeContextHolder;

@Database(entities = {NotifeeNotificationEntity.class}, version = 1, exportSchema = false)
public abstract class NotifeeDatabase extends RoomDatabase {

  public static NotifeeDatabase database;

  public static NotifeeDatabase getDatabase() {
    if (database != null) {
      return database;
    }

    NotifeeDatabase instance = Room.databaseBuilder(NotifeeContextHolder.getApplicationContext(), NotifeeDatabase.class, "notifee-database")
      .build();

    database = instance;
    return database;
  }

  public abstract NotifeeNotificationDao notifeeNotificationDao();
}


