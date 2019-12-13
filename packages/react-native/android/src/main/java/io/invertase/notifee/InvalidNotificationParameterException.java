package io.invertase.notifee;

import java.security.InvalidParameterException;

class InvalidNotificationParameterException extends InvalidParameterException {
  static String CHANNEL_NOT_FOUND = "channel-not-found";
  static String ACTIVITY_NOT_FOUND = "activity-not-found";
  private String code;

  public InvalidNotificationParameterException(String code, String msg) {
    super(msg);
    this.code = code;
  }

  String getCode() {
    return code;
  }
}
