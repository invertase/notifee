package io.invertase.notifee.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.MainThread;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotifeeEventEmitter {
  private static NotifeeEventEmitter sharedInstance = new NotifeeEventEmitter();
  private final List<NotifeeNativeEvent> queuedEvents = new ArrayList<>();
  private final Handler handler = new Handler(Looper.getMainLooper());
  private final HashMap<String, Integer> jsListeners = new HashMap<>();
  private ReactContext reactContext;
  private Boolean jsReady = false;
  private int jsListenerCount;

  public static NotifeeEventEmitter getSharedInstance() {
    return sharedInstance;
  }

  public void attachReactContext(final ReactContext reactContext) {
    handler.post(() -> {
      NotifeeEventEmitter.this.reactContext = reactContext;
      sendQueuedEvents();
    });
  }

  public void notifyJsReady(Boolean ready) {
    handler.post(() -> {
      jsReady = ready;
      sendQueuedEvents();
    });
  }

  public void sendEvent(final NotifeeNativeEvent event) {
    handler.post(() -> {
      synchronized (jsListeners) {
        if (!jsListeners.containsKey(event.getEventName()) || !emit(event)) {
          queuedEvents.add(event);
        }
      }
    });
  }

  public void addListener(String eventName) {
    synchronized (jsListeners) {
      jsListenerCount++;
      if (!jsListeners.containsKey(eventName)) {
        jsListeners.put(eventName, 1);
      } else {
        int listenersForEvent = jsListeners.get(eventName);
        jsListeners.put(eventName, listenersForEvent + 1);
      }
    }

    handler.post(this::sendQueuedEvents);
  }

  public void removeListener(String eventName, Boolean all) {
    synchronized (jsListeners) {
      if (jsListeners.containsKey(eventName)) {
        int listenersForEvent = jsListeners.get(eventName);

        if (listenersForEvent <= 1 || all) {
          jsListeners.remove(eventName);
        } else {
          jsListeners.put(eventName, listenersForEvent - 1);
        }

        jsListenerCount -= all ? listenersForEvent : 1;
      }
    }
  }

  public WritableMap getListenersMap() {
    WritableMap writableMap = Arguments.createMap();
    WritableMap events = Arguments.createMap();

    writableMap.putInt("listeners", jsListenerCount);
    writableMap.putInt("queued", queuedEvents.size());

    synchronized (jsListeners) {
      for (HashMap.Entry<String, Integer> entry : jsListeners.entrySet()) {
        events.putInt(entry.getKey(), entry.getValue());
      }
    }

    writableMap.putMap("events", events);

    return writableMap;
  }

  @MainThread
  private void sendQueuedEvents() {
    synchronized (jsListeners) {
      for (NotifeeNativeEvent event : new ArrayList<>(queuedEvents)) {
        if (jsListeners.containsKey(event.getEventName())) {
          queuedEvents.remove(event);
          sendEvent(event);
        }
      }
    }
  }

  @MainThread
  private boolean emit(final NotifeeNativeEvent event) {
    if (!jsReady || reactContext == null || !reactContext.hasActiveCatalystInstance()) {
      return false;
    }

    try {
      reactContext.getJSModule(
        DeviceEventManagerModule.RCTDeviceEventEmitter.class
      ).emit("notifee_" + event.getEventName(), event.getEventBody());
    } catch (Exception e) {
      Log.wtf("NOTIFEE_EMITTER", "Error sending Event " + event.getEventName(), e);
      return false;
    }

    return true;
  }
}
