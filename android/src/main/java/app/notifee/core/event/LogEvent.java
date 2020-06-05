package app.notifee.core.event;

import androidx.annotation.NonNull;

import app.notifee.core.KeepForSdk;

@KeepForSdk
public class LogEvent {
  @KeepForSdk
  public static final String LEVEL_ERROR = "error";
  @KeepForSdk
  public static final String LEVEL_WARN = "warn";
  @KeepForSdk
  public static final String LEVEL_INFO = "info";
  @KeepForSdk
  public static final String LEVEL_VERBOSE = "verbose";
  @KeepForSdk
  public static final String LEVEL_DEBUG = "debug";

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

  /**
   * Logging TAG, used for filtering purposes.
   *
   * @return String
   */
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
