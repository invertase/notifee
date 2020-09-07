package app.notifee.core.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface WorkDataDao {
  // TODO: check if this is the correct strategy to update an existing entity
  @Insert(onConflict = OnConflictStrategy.IGNORE)
  void insert(WorkDataEntity word);

  @Query("DELETE FROM work_data")
  void deleteAll();

  // TODO: implement delete to clear requests once complete
//  @Query("DELETE FROM work_data WHERE id = :id")
//  void delete(String id);

  @Query("SELECT * from work_data WHERE id = :id")
  WorkDataEntity getWorkDataById(String id);
}
