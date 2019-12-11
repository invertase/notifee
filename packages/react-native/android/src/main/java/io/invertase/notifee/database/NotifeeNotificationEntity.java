package io.invertase.notifee.database;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// todo add index columns

@Entity(tableName = "notifications")
public class NotifeeNotificationEntity {
  @PrimaryKey
  @NonNull
  public String notificationId;

  @ColumnInfo(name = "tag")
  public String tag;

  public NotifeeNotificationEntity(String notificationId, String tag) {
    this.notificationId = notificationId;
    this.tag = tag;
  }
}
