package app.notifee.core.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.greenrobot.eventbus.Subscribe;

import app.notifee.core.EventBus;
import app.notifee.core.Logger;
import app.notifee.core.events.NotificationEvent;

@androidx.room.Database(
  entities = {
    DatabaseNotificationEntity.class
  },
  version = 1,
  exportSchema = false
)
public abstract class Database extends RoomDatabase {
  public static String DB_NAME = "notifee";
  private static Database database = null;

  public static void initialize(Context context) {
    if (database == null) {
      database = Room.databaseBuilder(
        context,
        Database.class,
        DB_NAME
      ).build();

      EventBus.register(database);
    }
  }

  @Subscribe(priority = 1)
  public void onNotificationEvent(NotificationEvent event) {
    Logger.d("MIKE", event.toString());
  }

  abstract DatabaseNotificationDao getDao();
}

