package app.notifee.core.database;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "notifications")
class DatabaseNotificationEntity {
  @NonNull
  @PrimaryKey
  String id;

  @ColumnInfo(name = "tag")
  String tag;

  @ColumnInfo(name = "created_at")
  Long createdAt;

  @ColumnInfo(name = "modified_at")
  Long modifiedAt;

  // TODO just testing
  @ColumnInfo(name = "bundle")
  String bundle;

  static DatabaseNotificationEntity fromBundle(Bundle notification) {
    DatabaseNotificationEntity entity = new DatabaseNotificationEntity();
    entity.id = Objects.requireNonNull(notification.getString("id"));
    entity.tag = notification.getString("tag");
    entity.modifiedAt = System.currentTimeMillis();
    entity.createdAt = System.currentTimeMillis();

    // TODO just testing
    entity.bundle = notification.toString();

    return entity;
  }
}
