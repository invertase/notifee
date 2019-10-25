package io.invertase.notifee.core;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.invertase.notifee.BuildConfig;

public class NotifeeJSON {
  private static NotifeeJSON sharedInstance = new NotifeeJSON();

  private JSONObject jsonObject;

  private NotifeeJSON() {
    try {
      jsonObject = new JSONObject(BuildConfig.NOTIFEE_JSON_RAW);
    } catch (JSONException e) {
      // JSON is validated as part of gradle build - should never error
    }
  }

  public static NotifeeJSON getSharedInstance() {
    return sharedInstance;
  }

  public boolean contains(String key) {
    if (jsonObject == null) return false;
    return jsonObject.has(key);
  }

  public boolean getBooleanValue(String key, boolean defaultValue) {
    if (jsonObject == null) return defaultValue;
    return jsonObject.optBoolean(key, defaultValue);
  }

  public long getLongValue(String key, long defaultValue) {
    if (jsonObject == null) return defaultValue;
    return jsonObject.optLong(key, defaultValue);
  }

  public String getStringValue(String key, String defaultValue) {
    if (jsonObject == null) return defaultValue;
    return jsonObject.optString(key, defaultValue);
  }

  public String getRawJSON() {
    return BuildConfig.NOTIFEE_JSON_RAW;
  }

  public WritableMap getAll() {
    WritableMap writableMap = Arguments.createMap();

    JSONArray keys = jsonObject.names();
    for (int i = 0; i < keys.length(); ++i) {
      try {
        String key = keys.getString(i);
        NotifeeUtils.mapPutValue(key, jsonObject.get(key), writableMap);
      } catch (JSONException e) {
        // ignore
      }
    }

    return writableMap;
  }
}
