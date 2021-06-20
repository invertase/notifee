package app.notifee.core.database;

import static app.notifee.core.database.NotifeeCoreDatabase.MIGRATION_1_2;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NotifeeCoreDatabaseTest {
  private static final String TEST_DB = "migration-test";

  @Rule public MigrationTestHelper helper;

  public NotifeeCoreDatabaseTest() {
    helper =
        new MigrationTestHelper(
            InstrumentationRegistry.getInstrumentation(),
            NotifeeCoreDatabase.class.getCanonicalName(),
            new FrameworkSQLiteOpenHelperFactory());
  }

  @Test
  public void migrateAll() throws IOException {
    // Create earliest version of the database.

    SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);
    db.delete("work_data", null, null);
    db.close();

    // Open latest version of the database. Room will validate the schema
    // once all migrations execute.
    NotifeeCoreDatabase appDb =
        Room.databaseBuilder(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),
                NotifeeCoreDatabase.class,
                TEST_DB)
            .addMigrations(ALL_MIGRATIONS)
            .build();
    appDb.workDao().getAllWithAlarmManager(true);
    appDb.getOpenHelper().getWritableDatabase();
    appDb.close();
  }

  // Array of all migrations
  private static final Migration[] ALL_MIGRATIONS = new Migration[] {MIGRATION_1_2};
}
