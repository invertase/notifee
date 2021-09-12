package app.notifee.core;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
    preferences =
        ContextHolder.getApplicationContext()
            .getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
    return preferences;
  }
}
