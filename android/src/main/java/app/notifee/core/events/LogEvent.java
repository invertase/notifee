package app.notifee.core.events;

import androidx.annotation.NonNull;

import app.notifee.core.KeepForSdk;

@KeepForSdk
public class LogEvent {
  @KeepForSdk
  public static final String LEVEL_ERROR = "error";

  private final String tag;
  private final String level;
  private final String message;
  private final Throwable throwable;

  public LogEvent(@NonNull String level, @NonNull String tag, String message) {
    this.tag = tag;
    this.level = level;
    this.message = message;
    this.throwable = null;
  }

  public LogEvent(@NonNull String level, @NonNull String tag, String message, Throwable throwable) {
    this.tag = tag;
    this.level = level;
    this.message = message;
    this.throwable = throwable;
  }

  @KeepForSdk
  public String getTag() {
    return tag;
  }

  @KeepForSdk
  public String getLevel() {
    return level;
  }

  @KeepForSdk
  public String getMessage() {
    return message;
  }

  @KeepForSdk
  public Throwable getThrowable() {
    return throwable;
  }
}
