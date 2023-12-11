package app.notifee.core.database;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.content.Context;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
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
  static final ListeningExecutorService databaseWriteListeningExecutor = MoreExecutors.listeningDecorator(databaseWriteExecutor);

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
