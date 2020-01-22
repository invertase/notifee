package app.notifee.core;

import android.content.Context;
import android.content.SharedPreferences;

class Preferences {
  private static final String PREFERENCES_FILE = "app.notifee.core";
  private static Preferences sharedInstance = new Preferences();
  private SharedPreferences preferences;

  static Preferences getSharedInstance() {
    return sharedInstance;
  }

  boolean contains(String key) {
    return getPreferences().contains(key);
  }

  void setBooleanValue(String key, boolean value) {
    getPreferences().edit().putBoolean(key, value).apply();
  }

  boolean getBooleanValue(String key, boolean defaultValue) {
    return getPreferences().getBoolean(key, defaultValue);
  }

  void setLongValue(String key, long value) {
    getPreferences().edit().putLong(key, value).apply();
  }

  long getLongValue(String key, long defaultValue) {
    return getPreferences().getLong(key, defaultValue);
  }

  void setIntValue(String key, int value) {
    getPreferences().edit().putInt(key, value).apply();
  }

  int getIntValue(String key, int defaultValue) {
    return getPreferences().getInt(key, defaultValue);
  }

  void setStringValue(String key, String value) {
    getPreferences().edit().putString(key, value).apply();
  }

  String getStringValue(String key, String defaultValue) {
    return getPreferences().getString(key, defaultValue);
  }

  void clearAll() {
    getPreferences().edit().clear().apply();
  }

  private SharedPreferences getPreferences() {
    if (preferences != null) return preferences;
    preferences = ContextHolder.getApplicationContext()
      .getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    return preferences;
  }
}
