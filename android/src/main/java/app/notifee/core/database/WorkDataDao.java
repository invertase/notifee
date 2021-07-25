package app.notifee.core.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface WorkDataDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(WorkDataEntity word);

  @Query("DELETE FROM work_data")
  void deleteAll();

  @Query("DELETE FROM work_data WHERE id = :id")
  void deleteById(String id);

  @Query("DELETE FROM work_data WHERE id in (:ids)")
  void deleteByIds(List<String> ids);

  @Query("SELECT * from work_data WHERE id = :id")
  WorkDataEntity getWorkDataById(String id);

  @Query("SELECT * FROM work_data WHERE with_alarm_manager = :withAlarmManager")
  List<WorkDataEntity> getAllWithAlarmManager(Boolean withAlarmManager);

  @Query("SELECT * FROM work_data")
  List<WorkDataEntity> getAll();
}
