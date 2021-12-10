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

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface WorkDataDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(WorkDataEntity workDataEntity);

  @Update(onConflict = OnConflictStrategy.REPLACE)
  void update(WorkDataEntity workDataEntity);

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
