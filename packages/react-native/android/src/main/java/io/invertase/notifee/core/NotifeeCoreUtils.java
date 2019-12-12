package io.invertase.notifee.core;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.LifecycleState;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.invertase.notifee.core.NotifeeContextHolder.getApplicationContext;

@SuppressWarnings({"unused", "JavaDoc", "WeakerAccess"})
public class NotifeeCoreUtils {
  private static final String TAG = "NotifeeUtils";
  private static final String RN_DEVSUPPORT_CLASS = "DevSupportManagerImpl";
  private static final String RN_DEVSUPPORT_PACKAGE = "com.facebook.react.devsupport";

  private static final String EXPO_REGISTRY_CLASS = "ModuleRegistry";
  private static final String EXPO_CORE_PACKAGE = "expo.core";

  private static final String FLUTTER_REGISTRY_CLASS = "PluginRegistry";
  private static final String FLUTTER_CORE_PACKAGE = "io.flutter.plugin.common";

  private static final String REACT_NATIVE_REGISTRY_CLASS = "NativeModuleRegistry";
  private static final String REACT_NATIVE_CORE_PACKAGE = "com.facebook.react.bridge";

  /**
   * Creates a WritableMap from an exception, used to create a JS error object.
   *
   * @param exception
   * @return
   */
  public static WritableMap getExceptionMap(Exception exception) {
    WritableMap exceptionMap = Arguments.createMap();
    String code = "unknown";
    String message = exception.getMessage();
    exceptionMap.putString("code", code);
    exceptionMap.putString("message", message);
    exceptionMap.putString("nativeErrorCode", code);
    exceptionMap.putString("nativeErrorMessage", message);
    return exceptionMap;
  }

  /**
   * Return whether application is running.
   *
   * @return Boolean
   * @url https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/AppUtils.java#L255
   */
  public static Boolean isAppRunning() {
    int uid;
    Context appContext = getApplicationContext();

    String packageName = appContext.getPackageName();
    PackageManager packageManager = appContext.getPackageManager();

    try {
      ApplicationInfo ai = packageManager.getApplicationInfo(packageName, 0);
      if (ai == null) return false;
      uid = ai.uid;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
      return false;
    }

    ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);

    if (am != null) {
      List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(Integer.MAX_VALUE);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && taskInfo != null && taskInfo.size() > 0) {
        for (ActivityManager.RunningTaskInfo aInfo : taskInfo) {
          if (aInfo.baseActivity != null && packageName.equals(aInfo.baseActivity.getPackageName())) {
            return true;
          }
        }
      }
      List<ActivityManager.RunningServiceInfo> serviceInfo = am.getRunningServices(Integer.MAX_VALUE);
      if (serviceInfo != null && serviceInfo.size() > 0) {
        for (ActivityManager.RunningServiceInfo aInfo : serviceInfo) {
          if (uid == aInfo.uid) {
            return true;
          }
        }
      }
    }

    return false;
  }

  /**
   * We need to check if app is in foreground otherwise the app will crash.
   * http://stackoverflow.com/questions/8489993/check-android-application-is-in-foreground-or-not
   *
   * @param context Context
   * @return boolean
   */
  public static boolean isAppInForeground(Context context) {
    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    if (activityManager == null) return false;

    List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
    if (appProcesses == null) return false;

    final String packageName = context.getPackageName();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      if (
        appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
          && appProcess.processName.equals(packageName)
      ) {
        ReactContext reactContext;

        try {
          reactContext = (ReactContext) context;
        } catch (ClassCastException exception) {
          // Not react context so default to true
          return true;
        }

        return reactContext.getLifecycleState() == LifecycleState.RESUMED;
      }
    }

    return false;
  }

  /**
   * Checks for dev support availability - so we can ignore in release builds for example.
   *
   * @return Boolean
   */
  public static Boolean reactNativeHasDevSupport() {
    return hasPackageClass(RN_DEVSUPPORT_PACKAGE, RN_DEVSUPPORT_CLASS);
  }

  /**
   * Is the build platform Expo?
   *
   * @return Boolean
   */
  public static Boolean isExpo() {
    return hasPackageClass(EXPO_CORE_PACKAGE, EXPO_REGISTRY_CLASS);
  }

  /**
   * Is the build platform Flutter?
   *
   * @return Boolean
   */
  public static Boolean isFlutter() {
    return hasPackageClass(FLUTTER_CORE_PACKAGE, FLUTTER_REGISTRY_CLASS);
  }

  /**
   * Is the build platform React Native?
   *
   * @return Boolean
   */
  public static Boolean isReactNative() {
    return !isExpo() && hasPackageClass(REACT_NATIVE_CORE_PACKAGE, REACT_NATIVE_REGISTRY_CLASS);
  }

  /**
   * Returns true/false if a class for a package exists in the app class bundle
   *
   * @param packageName
   * @param className
   * @return
   */
  @SuppressWarnings("StringBufferReplaceableByString")
  public static Boolean hasPackageClass(String packageName, String className) {
    // ProGuard is surprisingly smart in this case and will keep a class if it detects a call to
    // Class.forName() with a static string. So instead we generate a quasi-dynamic string to
    // confuse it.
    String fullName = new StringBuilder(packageName)
      .append(".")
      .append(className)
      .toString();

    try {
      Class.forName(fullName);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Converts a native JSONObject into a WritableMap to send back to JS as an object
   *
   * @param jsonObject
   * @return
   * @throws JSONException
   */
  public static WritableMap jsonObjectToWritableMap(JSONObject jsonObject) throws JSONException {
    Iterator<String> iterator = jsonObject.keys();
    WritableMap writableMap = Arguments.createMap();

    while (iterator.hasNext()) {
      String key = iterator.next();
      Object value = jsonObject.get(key);
      if (value instanceof Float || value instanceof Double) {
        writableMap.putDouble(key, jsonObject.getDouble(key));
      } else if (value instanceof Number) {
        writableMap.putInt(key, jsonObject.getInt(key));
      } else if (value instanceof String) {
        writableMap.putString(key, jsonObject.getString(key));
      } else if (value instanceof JSONObject) {
        writableMap.putMap(key, jsonObjectToWritableMap(jsonObject.getJSONObject(key)));
      } else if (value instanceof JSONArray) {
        writableMap.putArray(key, jsonArrayToWritableArray(jsonObject.getJSONArray(key)));
      } else if (value == JSONObject.NULL) {
        writableMap.putNull(key);
      }
    }

    return writableMap;
  }

  /**
   * Converts a native JSONObject into a WritableMap to send back to JS as an array of objects
   *
   * @param jsonArray
   * @return
   * @throws JSONException
   */
  public static WritableArray jsonArrayToWritableArray(JSONArray jsonArray) throws JSONException {
    WritableArray writableArray = Arguments.createArray();

    for (int i = 0; i < jsonArray.length(); i++) {
      Object value = jsonArray.get(i);
      if (value instanceof Float || value instanceof Double) {
        writableArray.pushDouble(jsonArray.getDouble(i));
      } else if (value instanceof Number) {
        writableArray.pushInt(jsonArray.getInt(i));
      } else if (value instanceof String) {
        writableArray.pushString(jsonArray.getString(i));
      } else if (value instanceof JSONObject) {
        writableArray.pushMap(jsonObjectToWritableMap(jsonArray.getJSONObject(i)));
      } else if (value instanceof JSONArray) {
        writableArray.pushArray(jsonArrayToWritableArray(jsonArray.getJSONArray(i)));
      } else if (value == JSONObject.NULL) {
        writableArray.pushNull();
      }
    }
    return writableArray;
  }

  /**
   * Converts a native Map into a WritableMap
   *
   * @param value
   * @return
   */
  public static WritableMap mapToWritableMap(Map<String, Object> value) {
    WritableMap writableMap = Arguments.createMap();

    for (Map.Entry<String, Object> entry : value.entrySet()) {
      mapPutValue(entry.getKey(), entry.getValue(), writableMap);
    }

    return writableMap;
  }

  /**
   * Converts a native List into a WritableArray
   *
   * @param objects
   * @return
   */
  private static WritableArray listToWritableArray(List<Object> objects) {
    WritableArray writableArray = Arguments.createArray();
    for (Object object : objects) {
      arrayPushValue(object, writableArray);
    }
    return writableArray;
  }

  /**
   * Pushes a native type into a WritableArray
   *
   * @param value
   * @param array
   */
  @SuppressWarnings("unchecked")
  public static void arrayPushValue(
    @androidx.annotation.Nullable Object value,
    WritableArray array
  ) {
    if (value == null || value == JSONObject.NULL) {
      array.pushNull();
      return;
    }

    String type = value.getClass().getName();
    switch (type) {
      case "java.lang.Boolean":
        array.pushBoolean((Boolean) value);
        break;
      case "java.lang.Long":
        Long longVal = (Long) value;
        array.pushDouble((double) longVal);
        break;
      case "java.lang.Float":
        float floatVal = (float) value;
        array.pushDouble((double) floatVal);
        break;
      case "java.lang.Double":
        array.pushDouble((double) value);
        break;
      case "java.lang.Integer":
        array.pushInt((int) value);
        break;
      case "java.lang.String":
        array.pushString((String) value);
        break;
      case "org.json.JSONObject$1":
        try {
          array.pushMap(jsonObjectToWritableMap((JSONObject) value));
        } catch (JSONException e) {
          array.pushNull();
        }
        break;
      case "org.json.JSONArray$1":
        try {
          array.pushArray(jsonArrayToWritableArray((JSONArray) value));
        } catch (JSONException e) {
          array.pushNull();
        }
        break;
      default:
        if (List.class.isAssignableFrom(value.getClass())) {
          array.pushArray(listToWritableArray((List<Object>) value));
        } else if (Map.class.isAssignableFrom(value.getClass())) {
          array.pushMap(mapToWritableMap((Map<String, Object>) value));
        } else {
          Log.d(TAG, "utils:arrayPushValue:unknownType:" + type);
          array.pushNull();
        }
    }
  }

  /**
   * Adds a native type into a WritableMap
   *
   * @param key
   * @param value
   * @param map
   */
  @SuppressWarnings("unchecked")
  public static void mapPutValue(String key, @Nullable Object value, WritableMap map) {
    if (value == null || value == JSONObject.NULL) {
      map.putNull(key);
      return;
    }

    String type = value.getClass().getName();
    switch (type) {
      case "java.lang.Boolean":
        map.putBoolean(key, (Boolean) value);
        break;
      case "java.lang.Long":
        Long longVal = (Long) value;
        map.putDouble(key, (double) longVal);
        break;
      case "java.lang.Float":
        float floatVal = (float) value;
        map.putDouble(key, (double) floatVal);
        break;
      case "java.lang.Double":
        map.putDouble(key, (double) value);
        break;
      case "java.lang.Integer":
        map.putInt(key, (int) value);
        break;
      case "java.lang.String":
        map.putString(key, (String) value);
        break;
      case "org.json.JSONObject$1":
        try {
          map.putMap(key, jsonObjectToWritableMap((JSONObject) value));
        } catch (JSONException e) {
          map.putNull(key);
        }
        break;
      case "org.json.JSONArray$1":
        try {
          map.putArray(key, jsonArrayToWritableArray((JSONArray) value));
        } catch (JSONException e) {
          map.putNull(key);
        }
        break;
      default:
        if (List.class.isAssignableFrom(value.getClass())) {
          map.putArray(key, listToWritableArray((List<Object>) value));
        } else if (Map.class.isAssignableFrom(value.getClass())) {
          map.putMap(key, mapToWritableMap((Map<String, Object>) value));
        } else {
          Log.d(TAG, "utils:mapPutValue:unknownType:" + type);
          map.putNull(key);
        }
    }
  }


  /**
   * Convert a ReadableMap to a WritableMap for the purposes of re-sending back to JS
   *
   * @param map ReadableMap
   * @return WritableMap
   */
  public static WritableMap readableMapToWritableMap(ReadableMap map) {
    WritableMap writableMap = Arguments.createMap();
    writableMap.merge(map);
    return writableMap;
  }
}
