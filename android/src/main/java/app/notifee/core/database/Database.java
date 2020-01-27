package app.notifee.core.database;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import app.notifee.core.ContextHolder;
import app.notifee.core.EventBus;
import app.notifee.core.events.DatabaseEvent;
import app.notifee.core.events.NotificationEvent;

@androidx.room.Database(entities = {
  DatabaseNotificationEntity.class
}, version = 2, exportSchema = false)
public abstract class Database extends RoomDatabase {
  public static String DB_NAME = "notifee";
  private static Database database = null;

  public static void initialize() {
    if (database == null) {
      database = Room
        .databaseBuilder(ContextHolder.getApplicationContext(), Database.class, DB_NAME).build();
      EventBus.register(database);
    }
  }

  @Subscribe(priority = 1, threadMode = ThreadMode.ASYNC)
  public void onNotificationEvent(NotificationEvent event) {
    DatabaseNotificationDao dao = database.getDao();
    String entityId = event.getNotification().getId();
    DatabaseNotificationEntity[] existingEntity = dao.getById(entityId);

    String eventType = DatabaseEvent.TYPE_CREATED;
    if (existingEntity.length > 0) {
      eventType = DatabaseEvent.TYPE_UPDATED;
    }


    // TODO setup db records

    EventBus.post(new DatabaseEvent(eventType, "notification", entityId));

  }

  abstract DatabaseNotificationDao getDao();
}

