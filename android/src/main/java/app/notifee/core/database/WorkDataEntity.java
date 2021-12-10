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

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "work_data")
public class WorkDataEntity {

  @PrimaryKey(autoGenerate = false)
  @NonNull
  private String id;

  @ColumnInfo(name = "notification", typeAffinity = ColumnInfo.BLOB)
  private byte[] notification;

  @ColumnInfo(name = "trigger", typeAffinity = ColumnInfo.BLOB)
  private byte[] trigger;

  @ColumnInfo(name = "with_alarm_manager", defaultValue = "0")
  @NonNull
  private Boolean withAlarmManager;

  public WorkDataEntity(String id, byte[] notification, byte[] trigger, Boolean withAlarmManager) {
    this.id = id;
    this.notification = notification;
    this.trigger = trigger;
    this.withAlarmManager = withAlarmManager;
  }

  public String getId() {
    return this.id;
  }

  public byte[] getNotification() {
    return this.notification;
  }

  public byte[] getTrigger() {
    return this.trigger;
  }

  public Boolean getWithAlarmManager() {
    return this.withAlarmManager;
  }

  public void setTrigger(byte[] trigger) {
    this.trigger = trigger;
  }
}
