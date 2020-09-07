package app.notifee.core.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WorkDataDao {
  // allowing the insert of the same word multiple times by passing a
  // conflict resolution strategy
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void insert(WorkDataEntity word);

  @Query("DELETE FROM work_data")
  void deleteAll();

  @Query("SELECT * from work_data WHERE id = :id")
  WorkDataEntity getWorkDataById(String id);
}
