package io.invertase.notifee.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import java.util.Map;

public class NotifeePreferences {
  private static final String PREFERENCES_FILE = "io.invertase.notifee";
  private static NotifeePreferences sharedInstance = new NotifeePreferences();
  private SharedPreferences preferences;

  public static NotifeePreferences getSharedInstance() {
    return sharedInstance;
  }

  public boolean contains(String key) {
    return getPreferences().contains(key);
  }

  public void setBooleanValue(String key, boolean value) {
    getPreferences().edit().putBoolean(key, value).apply();
  }

  public boolean getBooleanValue(String key, boolean defaultValue) {
    return getPreferences().getBoolean(key, defaultValue);
  }

  public void setLongValue(String key, long value) {
    getPreferences().edit().putLong(key, value).apply();
  }

  public long getLongValue(String key, long defaultValue) {
    return getPreferences().getLong(key, defaultValue);
  }

  public void setStringValue(String key, String value) {
    getPreferences().edit().putString(key, value).apply();
  }

  public String getStringValue(String key, String defaultValue) {
    return getPreferences().getString(key, defaultValue);
  }

  public WritableMap getAll() {
    WritableMap writableMap = Arguments.createMap();
    Map<String, ?> prefMap = getPreferences().getAll();

    for (Map.Entry<String, ?> entry : prefMap.entrySet()) {
      NotifeeCoreUtils.mapPutValue(entry.getKey(), entry.getValue(), writableMap);
    }

    return writableMap;
  }

  public void clearAll() {
    getPreferences().edit().clear().apply();
  }

  private SharedPreferences getPreferences() {
    if (preferences != null) return preferences;
    preferences = NotifeeContextHolder.getApplicationContext().getSharedPreferences(
      PREFERENCES_FILE,
      Context.MODE_PRIVATE
    );
    return preferences;
  }
}
