/*
 * Copyright (c) 2016-present Invertase Limited
 */

package io.invertase.notifee;

import android.os.Bundle;
import androidx.annotation.NonNull;
import app.notifee.core.Notifee;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotifeeApiModule extends ReactContextBaseJavaModule {
  private static final int NOTIFICATION_TYPE_DISPLAYED = 1;
  private static final int NOTIFICATION_TYPE_TRIGGER = 2;
  private static final int NOTIFICATION_TYPE_ALL = 0;

  public NotifeeApiModule(@NonNull ReactApplicationContext reactContext) {
    super(reactContext);
  }

  public static String getMainComponent(@NonNull String defaultComponent) {
    return Notifee.getInstance().getMainComponent(defaultComponent);
  }

  @Override
  public void onCatalystInstanceDestroy() {
    NotifeeReactUtils.clearRunningHeadlessTasks();
  }

  @ReactMethod
  public void cancelNotification(String notificationId, Promise promise) {
    Notifee.getInstance()
        .cancelNotification(
            notificationId,
            NOTIFICATION_TYPE_ALL,
            (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void cancelDisplayedNotification(String notificationId, Promise promise) {
    Notifee.getInstance()
        .cancelNotification(
            notificationId,
            NOTIFICATION_TYPE_DISPLAYED,
            (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
  }

  @ReactMethod
  public void cancelTriggerNotification(String notificationId, Promise promise) {
    Notifee.getInstance()
        .cancelNotification(
            notificationId,
            NOTIFICATION_TYPE_TRIGGER,
            (e, aVoid) -> NotifeeReactUtils.promiseResolver(promise, e));
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
  public void getInitialNotification(Promise promise) {
    Notifee.getInstance()
        .getInitialNotification(
            (e, aBundle) -> NotifeeReactUtils.promiseResolver(promise, e, aBundle));
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
}
