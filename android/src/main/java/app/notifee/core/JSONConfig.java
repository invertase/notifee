package app.notifee.core;

import org.json.JSONException;
import org.json.JSONObject;

class JSONConfig {
  private static JSONConfig mInstance = null;
  private JSONObject mJSONObject = null;
  private String mJSONString;

  private JSONConfig(String jsonString) {
    mJSONString = jsonString;
    try {
      mJSONObject = new JSONObject(jsonString);
    } catch (JSONException e) {
      Logger.e("JSONConfig", "error parsing json config", e);
    }
  }

  static void initialize(String jsonString) {
    if (mInstance == null) {
      mInstance = new JSONConfig(jsonString);
    }
  }

  static JSONConfig getInstance() {
    return mInstance;
  }

  public boolean contains(String key) {
    if (mJSONObject == null) return false;
    return mJSONObject.has(key);
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    if (mJSONObject == null) return defaultValue;
    return mJSONObject.optBoolean(key, defaultValue);
  }

  public long getLong(String key, long defaultValue) {
    if (mJSONObject == null) return defaultValue;
    return mJSONObject.optLong(key, defaultValue);
  }

  public String getString(String key, String defaultValue) {
    if (mJSONObject == null) return defaultValue;
    return mJSONObject.optString(key, defaultValue);
  }

  public String getJSONString() {
    return mJSONString;
  }
}
