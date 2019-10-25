package io.invertase.notifee.core;

import com.facebook.react.bridge.WritableMap;

public class NotifeeEvent implements NotifeeNativeEvent {
  private String eventName;
  private WritableMap eventBody;

  public NotifeeEvent(String eventName, WritableMap eventBody) {
    this.eventName = eventName;
    this.eventBody = eventBody;
  }

   @Override
  public String getEventName() {
    return eventName;
  }

  @Override
  public WritableMap getEventBody() {
    return eventBody;
  }
}
