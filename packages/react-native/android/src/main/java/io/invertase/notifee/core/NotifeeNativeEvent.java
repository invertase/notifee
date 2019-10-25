package io.invertase.notifee.core;

import com.facebook.react.bridge.WritableMap;

public interface NotifeeNativeEvent {
  String getEventName();
  WritableMap getEventBody();
}
