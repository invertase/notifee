package app.notifee.core.events;

import androidx.annotation.NonNull;

public class LogErrorEvent {
  private final String clazz;
  private final String message;
  private final Throwable throwable;

  public LogErrorEvent(@NonNull String clazz, String message, Throwable throwable) {
    this.clazz = clazz;
    this.message = message;
    this.throwable = throwable;
  }

  public String getClazz() {
    return clazz;
  }

  public String getMessage() {
    return message;
  }

  public Throwable getThrowable() {
    return throwable;
  }


}
