package app.notifee.core.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WorkDataDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(WorkDataEntity word);

  @Query("DELETE FROM work_data")
  void deleteAll();

  @Query("DELETE FROM work_data WHERE id = :id")
  void deleteById(String id);

  @Query("SELECT * from work_data WHERE id = :id")
  WorkDataEntity getWorkDataById(String id);
}
