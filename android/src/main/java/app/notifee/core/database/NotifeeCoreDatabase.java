package app.notifee.core.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
    entities = {WorkDataEntity.class},
    version = 1,
    exportSchema = false)
public abstract class NotifeeCoreDatabase extends RoomDatabase {

  public abstract WorkDataDao wordDao();

  private static volatile NotifeeCoreDatabase INSTANCE;

  static final ExecutorService databaseWriteExecutor = Executors.newCachedThreadPool();

  static NotifeeCoreDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (NotifeeCoreDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE =
              Room.databaseBuilder(
                      context.getApplicationContext(),
                      NotifeeCoreDatabase.class,
                      "notifee_core_database")
                  .build();
        }
      }
    }
    return INSTANCE;
  }
}
