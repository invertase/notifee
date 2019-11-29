package io.invertase.notifee;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import java.util.HashMap;
import java.util.Map;

import io.invertase.notifee.core.NotifeeEvent;
import io.invertase.notifee.core.NotifeeEventEmitter;
import io.invertase.notifee.core.NotifeeJSON;
import io.invertase.notifee.core.NotifeeNativeModule;
import io.invertase.notifee.core.NotifeePreferences;
import io.invertase.notifee.core.NotifeeUtils;

public class NotifeeCoreModule extends NotifeeNativeModule {
  private static final String TAG = "NotifeeCoreModule";

  NotifeeCoreModule(ReactApplicationContext reactContext) {
    super(reactContext, TAG);
  }

  @Override
  public void initialize() {
    super.initialize();
    NotifeeEventEmitter.getSharedInstance().attachReactContext(getContext());
  }

  @ReactMethod
  public void eventsNotifyReady(Boolean ready) {
    NotifeeEventEmitter emitter = NotifeeEventEmitter.getSharedInstance();
    emitter.notifyJsReady(ready);
  }

  @ReactMethod
  public void eventsGetListeners(Promise promise) {
    NotifeeEventEmitter emitter = NotifeeEventEmitter.getSharedInstance();
    promise.resolve(emitter.getListenersMap());
  }

  @ReactMethod
  public void eventsPing(String eventName, ReadableMap eventBody, Promise promise) {
    NotifeeEventEmitter emitter = NotifeeEventEmitter.getSharedInstance();
    emitter.sendEvent(new NotifeeEvent(
      eventName,
      NotifeeUtils.readableMapToWritableMap(eventBody)
    ));
    promise.resolve(NotifeeUtils.readableMapToWritableMap(eventBody));
  }

  @ReactMethod
  public void eventsAddListener(String eventName) {
    NotifeeEventEmitter emitter = NotifeeEventEmitter.getSharedInstance();
    emitter.addListener(eventName);
  }

  @ReactMethod
  public void eventsRemoveListener(String eventName, Boolean all) {
    NotifeeEventEmitter emitter = NotifeeEventEmitter.getSharedInstance();
    emitter.removeListener(eventName, all);
  }

  /**
   * ------------------
   * JSON
   * ------------------
   */

  @ReactMethod
  public void jsonGetAll(Promise promise) {
    promise.resolve(NotifeeJSON.getSharedInstance().getAll());
  }

  /**
   * ------------------
   * PREFERENCES
   * ------------------
   */

  @ReactMethod
  public void preferencesSetBool(String key, boolean value, Promise promise) {
    NotifeePreferences.getSharedInstance().setBooleanValue(key, value);
    promise.resolve(null);
  }

  @ReactMethod
  public void preferencesSetString(String key, String value, Promise promise) {
    NotifeePreferences.getSharedInstance().setStringValue(key, value);
    promise.resolve(null);
  }

  @ReactMethod
  public void preferencesGetAll(Promise promise) {
    promise.resolve(NotifeePreferences.getSharedInstance().getAll());
  }

  @ReactMethod
  public void preferencesClearAll(Promise promise) {
    NotifeePreferences.getSharedInstance().clearAll();
    promise.resolve(null);
  }

  @Override
  public Map<String, Object> getConstants() {
    Map<String, Object> constants = new HashMap<>();
    constants.put("NOTIFEE_RAW_JSON", NotifeeJSON.getSharedInstance().getRawJSON());
    constants.put("ANDROID_API_LEVEL", android.os.Build.VERSION.SDK_INT);
    return constants;
  }
}
