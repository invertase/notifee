package io.invertase.notifee.core;

import com.facebook.react.bridge.WritableMap;

public interface NotifeeNativeError {
  String getErrorCode();

  String getErrorMessage();

  WritableMap getUserInfo();
}
