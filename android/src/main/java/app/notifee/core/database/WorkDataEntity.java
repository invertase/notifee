package app.notifee.core.database;

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

  @ColumnInfo(name = "with_alarm_manager", defaultValue = "false")
  private boolean withAlarmManager;

  public WorkDataEntity(String id, byte[] notification, byte[] trigger, boolean withAlarmManager) {
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

  public boolean getWithAlarmManager() {
    return this.withAlarmManager;
  }
}
