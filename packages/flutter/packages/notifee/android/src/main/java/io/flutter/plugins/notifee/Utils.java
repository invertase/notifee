package io.flutter.plugins.notifee;

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

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.notifee.core.ContextHolder;
import app.notifee.core.event.NotificationEvent;
import app.notifee.core.utility.ObjectUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Utils {
  public static Bundle mapToBundle(Map<String, Object> map) {
    Bundle bundle = new Bundle();

    for (Map.Entry<String, Object> entry : map.entrySet()) {
      Object value = entry.getValue();
      if (value instanceof Map<?, ?>) {
        bundle.putBundle(entry.getKey(), mapToBundle((Map<String, Object>) value));
      } else if (value instanceof List<?>) {
        //          value = listToJson((List<Object>) value);
      } else if (value instanceof Boolean) {
        bundle.putBoolean(entry.getKey(), (Boolean) value);
      } else if (value instanceof String) {
        bundle.putString(entry.getKey(), (String) value);
      } else if (value instanceof Double) {
        bundle.putDouble(entry.getKey(), (Double) value);
      } else if (value instanceof Number) {
        bundle.putInt(entry.getKey(), (int) value);
      }
    }

    return bundle;
  }

  public static List<Map<String, Object>> convertBundleListToMap(List<Bundle> bundleList) throws JSONException {
    List<Map<String, Object>> mapList = new ArrayList<>();
    for (Bundle bundle : bundleList) {
      mapList.add(convertBundleToMap(bundle));
    }

    return mapList;
  }

  public static Map<String, Object> convertBundleToMap(Bundle bundle) throws JSONException {
    JSONObject json = new JSONObject();
    Set<String> keys = bundle.keySet();
    for (String key : keys) {
      try {
        // json.put(key, bundle.get(key)); see edit below
        json.put(key, JSONObject.wrap(bundle.get(key)));
      } catch(JSONException e) {
        //Handle exception here
      }
    }

    return convertJsonToMap(json);
  }

  public static Map<String, Object> convertJsonToMap(JSONObject jsonObject) throws JSONException {
    Map map = new HashMap();

    String key;
    Object object;
    for(Iterator iterator = jsonObject.keys(); iterator.hasNext(); map.put(key, object)) {
      if ((object = jsonObject.get(key = (String) iterator.next())) instanceof JSONArray) {
        object = convertJsonArrayTolist((JSONArray)object);
      } else if (object instanceof JSONObject) {
        object = convertJsonToMap((JSONObject)object);
      }
    }

    return map;
  }

  public static List<Object> convertJsonArrayTolist(JSONArray jsonArray) throws JSONException {
    ArrayList arrayList = new ArrayList();

    for(int i = 0; i < jsonArray.length(); ++i) {
      Object object;
      if ((object = jsonArray.get(i)) instanceof JSONArray) {
        object = convertJsonArrayTolist((JSONArray)object);
      } else if (object instanceof JSONObject) {
        object = convertJsonToMap((JSONObject)object);
      }

      arrayList.add(object);
    }

    return arrayList;
  }

  public static Map<String, Object> parseNotificationEvent(NotificationEvent event) {
    Map<String, Object> map = new HashMap<>();
    map.put("detail", ObjectUtils.bundleToMap(event.getNotification().toBundle()));
    map.put("type", event.getType());

    return map;
  }

  /**
   * Identify if the application is currently in a state where user interaction is possible. This
   * method is called when a remote message is received to determine how the incoming message should
   * be handled.
   *
   * @return True if the application is currently in a state where user interaction is possible,
   *     false otherwise.
   */
  static boolean isApplicationForeground() {
    KeyguardManager keyguardManager =
        (KeyguardManager)
            ContextHolder.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);

    if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
      return false;
    }

    ActivityManager activityManager =
        (ActivityManager)
            ContextHolder.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
    if (activityManager == null) return false;

    List<ActivityManager.RunningAppProcessInfo> appProcesses =
        activityManager.getRunningAppProcesses();
    if (appProcesses == null) return false;

    final String packageName = ContextHolder.getApplicationContext().getPackageName();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
          && appProcess.processName.equals(packageName)) {
        return true;
      }
    }

    return false;
  }
}
