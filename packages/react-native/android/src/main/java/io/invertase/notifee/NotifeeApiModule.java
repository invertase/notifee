/*
 * Copyright (c) 2016-present Invertase Limited
 */

package io.invertase.notifee;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import app.notifee.core.Logger;
import app.notifee.core.Notifee;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotifeeApiModule extends ReactContextBaseJavaModule implements PermissionListener {
  private static final int NOTIFICATION_TYPE_DISPLAYED = 1;
  private static final int NOTIFICATION_TYPE_TRIGGER = 2;
  private static final int NOTIFICATION_TYPE_ALL = 0;

  public NotifeeApiModule(@NonNull ReactApplicationContext reactContext) {
    super(reactContext);
  }

  public static String getMainComponent(@NonNull String defaultComponent) {
    return Notifee.getInstance().getMainComponent(defaultComponent);
  }

  // This method was removed upstream in react-native 0.74+, replaced with invalidate
  // We will leave this stub here for older react-native versions compatibility
  // ...but it will just delegate to the new invalidate method
  public void onCatalystInstanceDestroy() {
    invalidate();
  }

  // This method was added in react-native 0.74 as a replacement for onCatalystInstanceDestroy
  // It should be marked @Override but that would cause problems in apps using older react-native
  // When minimum supported version is 0.74+ add @Override & remove onCatalystInstanceDestroy
  public void invalidate() {
    NotifeeReactUtils.headlessTaskManager.stopAllTasks();
  }

  @ReactMethod
  public void cancelAllNotifications(Promise promise) {
    Notifee.getInstance()
        .cancelAllNotifications(
            NOTIFICATION_TYPE_ALL, (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void cancelDisplayedNotifications(Promise promise) {
    Notifee.getInstance()
        .cancelAllNotifications(
            NOTIFICATION_TYPE_DISPLAYED,
            (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void cancelTriggerNotifications(Promise promise) {
    Notifee.getInstance()
        .cancelAllNotifications(
            NOTIFICATION_TYPE_TRIGGER, (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void cancelAllNotificationsWithIds(
      ReadableArray idsArray, int notificationType, String tag, Promise promise) {
    ArrayList<String> ids = new ArrayList<>(idsArray.size());
    for (int i = 0; i < idsArray.size(); i++) {
      ids.add(idsArray.getString(i));
    }

    Notifee.getInstance()
        .cancelAllNotificationsWithIds(
            notificationType,
            ids,
            tag,
            (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void getDisplayedNotifications(Promise promise) {
    Notifee.getInstance()
        .getDisplayedNotifications(
            (e, aBundleList) -> NotifeeReactUtils.promiseResolver(promise, e, aBundleList));
  }

  @ReactMethod
  public void getTriggerNotifications(Promise promise) {
    Notifee.getInstance()
        .getTriggerNotifications(
            (e, aBundleList) -> NotifeeReactUtils.promiseResolver(promise, e, aBundleList));
  }

  @ReactMethod
  public void getTriggerNotificationIds(Promise promise) {
    Notifee.getInstance()
        .getTriggerNotificationIds(
            (e, aStringList) ->
                NotifeeReactUtils.promiseStringListResolver(promise, e, aStringList));
  }

  @ReactMethod
  public void createChannel(ReadableMap channelMap, Promise promise) {
    Notifee.getInstance()
        .createChannel(
            Arguments.toBundle(channelMap),
            (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void createChannels(ReadableArray channelsArray, Promise promise) {
    ArrayList<Bundle> channels = new ArrayList<>(channelsArray.size());
    for (int i = 0; i < channelsArray.size(); i++) {
      channels.add(Arguments.toBundle(channelsArray.getMap(i)));
    }

    Notifee.getInstance()
        .createChannels(channels, (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void createChannelGroup(ReadableMap channelGroupMap, Promise promise) {
    Notifee.getInstance()
        .createChannelGroup(
            Arguments.toBundle(channelGroupMap),
            (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void createChannelGroups(ReadableArray channelGroupsArray, Promise promise) {
    ArrayList<Bundle> channelGroups = new ArrayList<>(channelGroupsArray.size());

    for (int i = 0; i < channelGroupsArray.size(); i++) {
      channelGroups.add(Arguments.toBundle(channelGroupsArray.getMap(i)));
    }

    Notifee.getInstance()
        .createChannelGroups(
            channelGroups, (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void deleteChannel(String channelId, Promise promise) {
    Notifee.getInstance()
        .deleteChannel(channelId, (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void deleteChannelGroup(String channelId, Promise promise) {
    Notifee.getInstance()
        .deleteChannelGroup(channelId, (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void displayNotification(ReadableMap notificationMap, Promise promise) {
    Notifee.getInstance()
        .displayNotification(
            Arguments.toBundle(notificationMap),
            (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void openAlarmPermissionSettings(Promise promise) {
    Notifee.getInstance()
        .openAlarmPermissionSettings(
            getCurrentActivity(), (e, avoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void createTriggerNotification(
      ReadableMap notificationMap, ReadableMap triggerMap, Promise promise) {
    Notifee.getInstance()
        .createTriggerNotification(
            Arguments.toBundle(notificationMap),
            Arguments.toBundle(triggerMap),
            (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void getChannels(Promise promise) {
    Notifee.getInstance()
        .getChannels(
            (e, aBundleList) -> NotifeeReactUtils.promiseResolver(promise, e, aBundleList));
  }

  @ReactMethod
  public void getChannel(String channelId, Promise promise) {
    Notifee.getInstance()
        .getChannel(
            channelId, (e, aBundle) -> NotifeeReactUtils.promiseResolver(promise, e, aBundle));
  }

  @ReactMethod
  public void getChannelGroups(Promise promise) {
    Notifee.getInstance()
        .getChannelGroups(
            (e, aBundleList) -> NotifeeReactUtils.promiseResolver(promise, e, aBundleList));
  }

  @ReactMethod
  public void getChannelGroup(String channelGroupId, Promise promise) {
    Notifee.getInstance()
        .getChannel(
            channelGroupId, (e, aBundle) -> NotifeeReactUtils.promiseResolver(promise, e, aBundle));
  }

  @ReactMethod
  public void isChannelCreated(String channelId, Promise promise) {
    Notifee.getInstance()
        .isChannelCreated(
            channelId, (e, aBool) -> NotifeeReactUtils.promiseBooleanResolver(promise, e, aBool));
  }

  @ReactMethod
  public void isChannelBlocked(String channelId, Promise promise) {
    Notifee.getInstance()
        .isChannelBlocked(
            channelId, (e, aBool) -> NotifeeReactUtils.promiseBooleanResolver(promise, e, aBool));
  }

  @ReactMethod
  public void getInitialNotification(Promise promise) {
    Notifee.getInstance()
        .getInitialNotification(
            getCurrentActivity(),
            (e, aBundle) -> NotifeeReactUtils.promiseResolver(promise, e, aBundle));
  }

  @ReactMethod
  public void getNotificationSettings(Promise promise) {
    Notifee.getInstance()
        .getNotificationSettings(
            (e, aBundle) -> NotifeeReactUtils.promiseResolver(promise, e, aBundle));
  }

  @ReactMethod
  public void requestPermission(Promise promise) {
    // For Android 12 and below, we return the notification settings
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
      Notifee.getInstance()
          .getNotificationSettings(
              (e, aBundle) -> NotifeeReactUtils.promiseResolver(promise, e, aBundle));
      return;
    }

    // We have to handle this logic outside of our core module due to a react-native limitation
    // with obtaining the correct activity
    PermissionAwareActivity activity = (PermissionAwareActivity) getCurrentActivity();
    if (activity == null) {
      Logger.d(
          "requestPermission",
          "Unable to get permissionAwareActivity for " + Build.VERSION.SDK_INT);

      Notifee.getInstance()
          .getNotificationSettings(
              (e, aBundle) -> NotifeeReactUtils.promiseResolver(promise, e, aBundle));
      return;
    }

    // Setting the request permission callback before attempting to call requestPermissions
    Notifee.getInstance()
        .setRequestPermissionCallback(
            (e, aBundle) -> NotifeeReactUtils.promiseResolver(promise, e, aBundle));

    try {
      activity.requestPermissions(
          new String[] {Manifest.permission.POST_NOTIFICATIONS},
          Notifee.REQUEST_CODE_NOTIFICATION_PERMISSION,
          this);
    } catch (Exception e) {
      Logger.d(
          "requestPermission",
          "Failed to request POST_NOTIFICATIONS permission: " + e.getMessage());
      NotifeeReactUtils.promiseResolver(promise, e);
    }
  }

  @ReactMethod
  public void openNotificationSettings(String channelId, Promise promise) {
    Notifee.getInstance()
        .openNotificationSettings(
            channelId,
            getCurrentActivity(),
            (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void openBatteryOptimizationSettings(Promise promise) {
    Notifee.getInstance()
        .openBatteryOptimizationSettings(
            getCurrentActivity(), (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void isBatteryOptimizationEnabled(Promise promise) {
    Notifee.getInstance()
        .isBatteryOptimizationEnabled(
            (e, aBool) -> NotifeeReactUtils.promiseBooleanResolver(promise, e, aBool));
  }

  @ReactMethod
  public void getPowerManagerInfo(Promise promise) {
    Notifee.getInstance()
        .getPowerManagerInfo(
            (e, aBundle) -> NotifeeReactUtils.promiseResolver(promise, e, aBundle));
  }

  @ReactMethod
  public void openPowerManagerSettings(Promise promise) {
    Notifee.getInstance()
        .openPowerManagerSettings(
            getCurrentActivity(), (e, avoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void stopForegroundService(Promise promise) {
    Notifee.getInstance()
        .stopForegroundService((e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void hideNotificationDrawer() {
    NotifeeReactUtils.hideNotificationDrawer();
  }

  @ReactMethod
  public void addListener(String eventName) {
    // Keep: Required for RN built in Event Emitter Calls.
  }

  @ReactMethod
  public void removeListeners(Integer count) {
    // Keep: Required for RN built in Event Emitter Calls.
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
    return constants;
  }

  @Override
  public boolean onRequestPermissionsResult(
      int requestCode, String[] permissions, int[] grantResults) {
    return Notifee.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
  }
}
