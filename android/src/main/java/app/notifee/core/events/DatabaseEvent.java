package app.notifee.core.events;

import app.notifee.core.KeepForSdk;

@KeepForSdk
public class DatabaseEvent {
  @KeepForSdk
  public final static String TYPE_CREATED = "created";
  @KeepForSdk
  public final static String TYPE_UPDATED = "updated";
  @KeepForSdk
  public final static String TYPE_DELETED = "deleted";

  private final String type;

  private final String entityId;

  private final String entityType;

  public DatabaseEvent(String type, String entityType, String entityId) {
    this.type = type;
    this.entityType = entityType;
    this.entityId = entityId;
  }

  /**
   * Type of the event, one of:
   * {@code DatabaseEvent.TYPE_CREATED},
   * {@code DatabaseEvent.TYPE_UPDATED},
   * {@code DatabaseEvent.TYPE_DELETED}
   *
   * @return String
   */
  @KeepForSdk
  public String getType() {
    return type;
  }


  /**
   * The id of the record this event applies to.
   *
   * @return int
   */
  @KeepForSdk
  public String getEntityId() {
    return entityId;
  }

  /**
   * The type of the entity this event applies to, e.g. 'notification'
   *
   * @return String
   */
  @KeepForSdk
  public String getEntityType() {
    return entityType;
  }
}
