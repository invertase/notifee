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

import static io.flutter.plugins.notifee.Utils.mapToBundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import app.notifee.core.ContextHolder;
import app.notifee.core.Notifee;
import app.notifee.core.utility.ObjectUtils;
import io.flutter.embedding.engine.FlutterShellArgs;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.notifee.background.FlutterBackgroundService;
import java.util.List;
import java.util.Map;

/** NotifeePlugin */
public class NotifeePlugin
    implements FlutterPlugin, MethodCallHandler, ActivityAware, PluginRegistry.NewIntentListener {

  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Activity mainActivity;

  @Override
  public void onAttachedToActivity(ActivityPluginBinding binding) {
    this.mainActivity = binding.getActivity();
    if (mainActivity.getIntent() != null && mainActivity.getIntent().getExtras() != null) {
      if ((mainActivity.getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)
          != Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) {
        onNewIntent(mainActivity.getIntent());
      }
    }
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    this.mainActivity = null;
  }

  @Override
  public void onReattachedToActivityForConfigChanges(ActivityPluginBinding binding) {
    binding.addOnNewIntentListener(this);
    this.mainActivity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivity() {
    this.mainActivity = null;
  }

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel =
        new MethodChannel(
            flutterPluginBinding.getBinaryMessenger(), "plugins.invertase.io/notifee");
    channel.setMethodCallHandler(this);

    EventChannel eventChannel =
        new EventChannel(
            flutterPluginBinding.getBinaryMessenger(),
            "plugins.invertase.io/notifee/on_foreground");

    eventChannel.setStreamHandler(new ForegroundEventStreamHandler());
    ContextHolder.setApplicationContext(flutterPluginBinding.getApplicationContext());
  }

  private void displayNotification(Map<String, Object> arguments, final Result result) {
    Notifee.getInstance()
        .displayNotification(
            mapToBundle(arguments),
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  private void createTriggerNotification(Map<String, Object> arguments, final Result result) {
    Bundle bundle = mapToBundle(arguments);

    Notifee.getInstance()
        .createTriggerNotification(
            bundle.getBundle("notification"),
            bundle.getBundle("trigger"),
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  private void cancelAllNotifications(Map<String, Object> arguments, final Result result) {
    Notifee.getInstance()
        .cancelAllNotifications(
            (int) arguments.get("type"),
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  private void cancelAllNotificationsWithIds(Map<String, Object> arguments, final Result result) {
    Notifee.getInstance()
        .cancelAllNotificationsWithIds(
            (int) arguments.get("type"),
            (List<String>) arguments.get("ids"),
            (String) arguments.get("tag"),
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  private void getTriggerNotificationIds(final Result result) {
    Notifee.getInstance()
        .getTriggerNotificationIds(
            (e, aList) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(aList);
            });
  }

  private void getTriggerNotifications(final Result result) {
    Notifee.getInstance()
        .getTriggerNotifications(
            (e, aList) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }

              try {
                result.success(ObjectUtils.listToMap(aList));
              } catch (Exception exception) {
                result.error(exception.toString(), null, null);
              }
            });
  }

  private void getDisplayedNotifications(final Result result) {
    Notifee.getInstance()
        .getDisplayedNotifications(
            (e, aList) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }

              try {
                result.success(ObjectUtils.listToMap(aList));
              } catch (Exception exception) {
                result.error(exception.toString(), null, null);
              }
            });
  }

  private void getInitialNotification(final Result result) {
    Notifee.getInstance()
        .getInitialNotification(
            this.mainActivity,
            (e, aBundle) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }

              try {
                if (aBundle == null) {
                  result.success(null);
                  return;
                }
                result.success(ObjectUtils.bundleToMap(aBundle));
              } catch (Exception exception) {
                result.error(exception.toString(), null, null);
              }
            });
  }

  private void createChannel(Map<String, Object> arguments, final Result result) {
    if (android.os.Build.VERSION.SDK_INT < 26) {
      result.success(null);
      return;
    }

    Notifee.getInstance()
        .createChannel(
            mapToBundle(arguments),
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  private void createChannelGroup(Map<String, Object> arguments, final Result result) {
    if (android.os.Build.VERSION.SDK_INT < 26) {
      result.success(null);
      return;
    }

    Notifee.getInstance()
        .createChannelGroup(
            mapToBundle(arguments),
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  private void deleteChannel(String channelId, final Result result) {
    if (android.os.Build.VERSION.SDK_INT < 26) {
      result.success(null);
      return;
    }

    Notifee.getInstance()
        .deleteChannel(
            channelId,
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  private void deleteChannelGroup(String channelGroupId, final Result result) {
    if (android.os.Build.VERSION.SDK_INT < 26) {
      result.success(null);
      return;
    }

    Notifee.getInstance()
        .deleteChannelGroup(
            channelGroupId,
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  private void getChannel(String channelId, final Result result) {
    if (android.os.Build.VERSION.SDK_INT < 26) {
      result.success(null);
      return;
    }

    Notifee.getInstance()
        .getChannel(
            channelId,
            (e, aBundle) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }

              try {
                result.success(ObjectUtils.bundleToMap(aBundle));
              } catch (Exception exception) {
                result.error(exception.toString(), null, null);
              }
            });
  }

  private void getChannelGroup(String channelGroupId, final Result result) {
    if (android.os.Build.VERSION.SDK_INT < 26) {
      result.success(null);
      return;
    }

    Notifee.getInstance()
        .getChannel(
            channelGroupId,
            (e, aBundle) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }

              try {
                result.success(ObjectUtils.bundleToMap(aBundle));
              } catch (Exception exception) {
                result.error(exception.toString(), null, null);
              }
            });
  }

  private void getChannels(final Result result) {
    if (android.os.Build.VERSION.SDK_INT < 26) {
      result.success(null);
      return;
    }

    Notifee.getInstance()
        .getChannels(
            (e, aList) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }

              try {
                result.success(ObjectUtils.listToMap(aList));
              } catch (Exception exception) {
                result.error(exception.toString(), null, null);
              }
            });
  }

  private void getChannelGroups(final Result result) {
    if (android.os.Build.VERSION.SDK_INT < 26) {
      result.success(null);
      return;
    }

    Notifee.getInstance()
        .getChannelGroups(
            (e, aList) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }

              try {
                result.success(ObjectUtils.listToMap(aList));
              } catch (Exception exception) {
                result.error(exception.toString(), null, null);
              }
            });
  }

  private void isChannelCreated(String channelId, final Result result) {
    if (android.os.Build.VERSION.SDK_INT < 26) {
      result.success(true);
      return;
    }

    Notifee.getInstance()
        .isChannelCreated(
            channelId,
            (e, aBool) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(aBool);
            });
  }

  private void isChannelBlocked(String channelId, final Result result) {

    if (android.os.Build.VERSION.SDK_INT < 26) {
      result.success(false);
    }

    Notifee.getInstance()
        .isChannelBlocked(
            channelId,
            (e, aBool) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(aBool);
            });
  }

  private void openNotificationSettings(@Nullable String channelId, final Result result) {
    Notifee.getInstance()
        .openNotificationSettings(
            channelId,
            mainActivity,
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  private void openPowerManagerSettings(final Result result) {
    Notifee.getInstance()
        .openPowerManagerSettings(
            mainActivity,
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  private void getNotificationSettings(final Result result) {
    Notifee.getInstance()
        .getNotificationSettings(
            (e, aBundle) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              try {
                result.success(ObjectUtils.bundleToMap(aBundle));
              } catch (Exception exception) {
                result.error(exception.toString(), null, null);
              }
            });
  }

  private void getPowerManagerInfo(final Result result) {
    Notifee.getInstance()
        .getPowerManagerInfo(
            (e, aBundle) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              try {
                result.success(ObjectUtils.bundleToMap(aBundle));
              } catch (Exception exception) {
                result.error(exception.toString(), null, null);
              }
            });
  }

  private void isBatteryOptimizationEnabled(final Result result) {
    Notifee.getInstance()
        .isBatteryOptimizationEnabled(
            (e, aBool) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(aBool);
            });
  }

  private void openBatteryOptimizationSettings(final Result result) {
    Notifee.getInstance()
        .openBatteryOptimizationSettings(
            mainActivity,
            (e, aVoid) -> {
              if (e != null) {
                result.error(e.toString(), null, null);
              }
              result.success(null);
            });
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    } else if (call.method.equals("displayNotification")) {
      displayNotification((Map<String, Object>) call.arguments, result);
    } else if (call.method.equals("createTriggerNotification")) {
      createTriggerNotification((Map<String, Object>) call.arguments, result);
    } else if (call.method.equals("cancelAllNotifications")) {
      cancelAllNotifications((Map<String, Object>) call.arguments, result);
    } else if (call.method.equals("cancelAllNotificationsWithIds")) {
      cancelAllNotificationsWithIds((Map<String, Object>) call.arguments, result);
    } else if (call.method.equals("getTriggerNotificationIds")) {
      getTriggerNotificationIds(result);
    } else if (call.method.equals("getTriggerNotifications")) {
      getTriggerNotifications(result);
    } else if (call.method.equals("getInitialNotification")) {
      getInitialNotification(result);
    } else if (call.method.equals("getDisplayedNotifications")) {
      getDisplayedNotifications(result);
    } else if (call.method.equals("createChannel")) {
      createChannel((Map<String, Object>) call.arguments, result);
    } else if (call.method.equals("createChannelGroup")) {
      createChannelGroup((Map<String, Object>) call.arguments, result);
    } else if (call.method.equals("deleteChannel")) {
      deleteChannel((String) call.arguments, result);
    } else if (call.method.equals("deleteChannelGroup")) {
      deleteChannelGroup((String) call.arguments, result);
    } else if (call.method.equals("getChannel")) {
      getChannel((String) call.arguments, result);
    } else if (call.method.equals("getChannels")) {
      getChannels(result);
    } else if (call.method.equals("getChannelGroup")) {
      getChannelGroup((String) call.arguments, result);
    } else if (call.method.equals("getChannelGroups")) {
      getChannelGroups(result);
    } else if (call.method.equals("isChannelCreated")) {
      isChannelCreated((String) call.arguments, result);
    } else if (call.method.equals("isChannelBlocked")) {
      isChannelBlocked((String) call.arguments, result);
    } else if (call.method.equals("hideNotificationDrawer")) {
      //      hideNotificationDrawer(result);
    } else if (call.method.equals("getNotificationSettings")) {
      getNotificationSettings(result);
    } else if (call.method.equals("openNotificationSettings")) {
      openNotificationSettings((String) call.arguments, result);
    } else if (call.method.equals("openPowerManagerSettings")) {
      openPowerManagerSettings(result);
    } else if (call.method.equals("getPowerManagerInfo")) {
      getPowerManagerInfo(result);
    } else if (call.method.equals("isBatteryOptimizationEnabled")) {
      isBatteryOptimizationEnabled(result);
    } else if (call.method.equals("openBatteryOptimizationSettings")) {
      openBatteryOptimizationSettings(result);
    } else if (call.method.equals("startBackgroundIsolate")) {
      Map<String, Object> arguments = ((Map<String, Object>) call.arguments);

      long pluginCallbackHandle = 0;
      long userCallbackHandle = 0;

      Object arg1 = arguments.get("pluginCallbackHandle");
      Object arg2 = arguments.get("userCallbackHandle");

      if (arg1 instanceof Long) {
        pluginCallbackHandle = (Long) arg1;
      } else {
        pluginCallbackHandle = Long.valueOf((Integer) arg1);
      }

      if (arg2 instanceof Long) {
        userCallbackHandle = (Long) arg2;
      } else {
        userCallbackHandle = Long.valueOf((Integer) arg2);
      }

      FlutterShellArgs shellArgs = null;
      if (mainActivity != null) {
        shellArgs = FlutterShellArgs.fromIntent(mainActivity.getIntent());
      }

      FlutterBackgroundService.initialize(pluginCallbackHandle, userCallbackHandle, shellArgs);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public boolean onNewIntent(Intent intent) {
    if (intent == null || intent.getExtras() == null) {
      return false;
    }

    // Remote Message ID can be either one of the following...
    String messageId = intent.getExtras().getString("google.message_id");
    if (messageId == null) messageId = intent.getExtras().getString("message_id");
    if (messageId == null) {
      return false;
    }

    mainActivity.setIntent(intent);
    return true;
  }
}
