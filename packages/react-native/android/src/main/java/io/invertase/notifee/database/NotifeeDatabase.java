package io.invertase.notifee.database;

import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.greenrobot.eventbus.Subscribe;

import io.invertase.notifee.NotifeeEventBus;
import io.invertase.notifee.core.NotifeeContextHolder;
import io.invertase.notifee.events.NotifeeNotificationEvent;

@Database(
  entities = {
    NotifeeNotificationEntity.class
  },
  version = 1,
  exportSchema = false
)
public abstract class NotifeeDatabase extends RoomDatabase {

  static NotifeeDatabase database = null;

  public static void initialize() {
    if (database == null) {
      NotifeeDatabase newDatabase = Room.databaseBuilder(
        NotifeeContextHolder.getApplicationContext(),
        NotifeeDatabase.class,
        "notifee"
      ).build();
      NotifeeEventBus.register(newDatabase);
    }
  }

  @Subscribe(priority = 1)
  public void onNotificationEvent(NotifeeNotificationEvent event) {
    Log.d("MIKE", event.toString());
  }

  public abstract NotifeeNotificationDao getDao();
}

