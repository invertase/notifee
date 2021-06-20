package app.notifee.core.database;

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
    entities = {WorkDataEntity.class},
    version = 2,
    exportSchema = true)
public abstract class NotifeeCoreDatabase extends RoomDatabase {

  public abstract WorkDataDao workDao();

  private static volatile NotifeeCoreDatabase INSTANCE;

  static final ExecutorService databaseWriteExecutor = Executors.newCachedThreadPool();

  /**
   * Migrate from: version 1 to version 2 - where the {@link WorkDataEntity} has an extra field:
   * alert
   */
  @VisibleForTesting
  static final Migration MIGRATION_1_2 =
      new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
          database.execSQL(
              "ALTER TABLE work_data"
                  + " ADD COLUMN with_alarm_manager INTEGER NOT NULL DEFAULT 0");
        }
      };

  static NotifeeCoreDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (NotifeeCoreDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE =
              Room.databaseBuilder(
                      context.getApplicationContext(),
                      NotifeeCoreDatabase.class,
                      "notifee_core_database")
                  .addMigrations(MIGRATION_1_2)
                  .build();
        }
      }
    }
    return INSTANCE;
  }
}
