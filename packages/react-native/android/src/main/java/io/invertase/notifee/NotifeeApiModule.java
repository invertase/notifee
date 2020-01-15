package io.invertase.notifee;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.notifee.core.Notifee;

public class NotifeeApiModule extends ReactContextBaseJavaModule {
  public NotifeeApiModule(@NonNull ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @ReactMethod
  public void cancelNotification(String notificationId, Promise promise) {
    Notifee.getInstance().cancelNotification(notificationId, (e, aVoid) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(aVoid);
      }
    });
  }

  @ReactMethod
  public void cancelAllNotifications(Promise promise) {
    Notifee.getInstance().cancelAllNotifications((e, aVoid) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(aVoid);
      }
    });
  }

  @ReactMethod
  public void createChannel(ReadableMap channelMap, Promise promise) {
    Notifee.getInstance().createChannel(Arguments.toBundle(channelMap), (e, aVoid) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(aVoid);
      }
    });
  }

  @ReactMethod
  public void createChannels(ReadableArray channelsArray, Promise promise) {
    ArrayList<Object> rawChannelsArray = channelsArray.toArrayList();
    ArrayList<Bundle> channels = new ArrayList<>(rawChannelsArray.size());

    for (Object o : rawChannelsArray) {
      channels.add((Bundle) o);
    }

    Notifee.getInstance().createChannels(channels, (e, aVoid) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(aVoid);
      }
    });
  }

  @ReactMethod
  public void createChannelGroup(ReadableMap channelGroupMap, Promise promise) {
    Notifee.getInstance().createChannelGroup(Arguments.toBundle(channelGroupMap), (e, aVoid) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(aVoid);
      }
    });
  }

  @ReactMethod
  public void createChannelGroups(ReadableArray channelGroupsArray, Promise promise) {
    ArrayList<Object> rawChannelGroupsArray = channelGroupsArray.toArrayList();
    ArrayList<Bundle> channelGroups = new ArrayList<>(channelGroupsArray.size());

    for (Object o : rawChannelGroupsArray) {
      channelGroups.add((Bundle) o);
    }

    Notifee.getInstance().createChannelGroups(channelGroups, (e, aVoid) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(aVoid);
      }
    });
  }

  @ReactMethod
  public void deleteChannel(String channelId, Promise promise) {
    Notifee.getInstance().deleteChannel(channelId, (e, aVoid) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(aVoid);
      }
    });
  }

  @ReactMethod
  public void deleteChannelGroup(String channelId, Promise promise) {
    Notifee.getInstance().deleteChannelGroup(channelId, (e, aVoid) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(aVoid);
      }
    });
  }

  @ReactMethod
  public void displayNotification(ReadableMap notificationMap, Promise promise) {
    Notifee.getInstance().displayNotification(Arguments.toBundle(notificationMap), (e, aVoid) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(aVoid);
      }
    });
  }

  @ReactMethod
  public void getAllChannels(Promise promise) {
    Notifee.getInstance().getAllChannels((e, aBundleList) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        WritableArray writableArray = Arguments.createArray();
        for (Bundle bundle : aBundleList) {
          writableArray.pushMap(Arguments.fromBundle(bundle));
        }
        promise.resolve(writableArray);
      }
    });
  }

  @ReactMethod
  public void getChannel(String channelId, Promise promise) {
    Notifee.getInstance().getChannel(channelId, (e, aBundle) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(Arguments.fromBundle(aBundle));
      }
    });
  }

  @ReactMethod
  public void getAllChannelGroups(Promise promise) {
    Notifee.getInstance().getAllChannelGroups((e, aBundleList) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        WritableArray writableArray = Arguments.createArray();
        for (Bundle bundle : aBundleList) {
          writableArray.pushMap(Arguments.fromBundle(bundle));
        }
        promise.resolve(writableArray);
      }
    });
  }

  @ReactMethod
  public void getChannelGroup(String channelGroupId, Promise promise) {
    Notifee.getInstance().getChannel(channelGroupId, (e, aBundle) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(Arguments.fromBundle(aBundle));
      }
    });
  }

  @ReactMethod
  public void getInitialNotification(Promise promise) {
    Notifee.getInstance().getInitialNotification((e, aBundle) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(Arguments.fromBundle(aBundle));
      }
    });
  }

  @ReactMethod
  public void openNotificationSettings(String channelId, Promise promise) {
    Notifee.getInstance().openNotificationSettings(channelId, getCurrentActivity(), (e, aVoid) -> {
      if (e != null) {
        // TODO custom error class with message/code
        promise.reject(e);
      } else {
        promise.resolve(aVoid);
      }
    });
  }

  @NonNull
  @Override
  public String getName() {
    return "NotifeeApiModule";
  }

  @Override
  public Map<String, Object> getConstants() {
    Map<String, Object> constants = new HashMap<>();
    constants.put("ANDROID_API_LEVEL", android.os.Build.VERSION.SDK_INT);
    constants.put("NOTIFICATION_EVENT_KEY", NotifeeEventSubscriber.NOTIFICATION_EVENT_KEY);
    constants.put("FOREGROUND_NOTIFICATION_TASK_KEY",
      NotifeeEventSubscriber.FOREGROUND_NOTIFICATION_TASK_KEY
    );
    return constants;
  }
}
