/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { AppRegistry } from 'react-native';
import { Module } from './types/Module';
import {
  AndroidChannel,
  AndroidChannelGroup,
  NativeAndroidChannel,
  NativeAndroidChannelGroup,
} from './types/NotificationAndroid';
import {
  InitialNotification,
  Notification,
  Event,
  TriggerNotification,
  DisplayedNotification,
} from './types/Notification';
import { PowerManagerInfo } from './types/PowerManagerInfo';
import { Trigger } from './types/Trigger';
import NotifeeNativeModule, { NativeModuleConfig } from './NotifeeNativeModule';
import {
  isAndroid,
  isArray,
  isFunction,
  isIOS,
  isNumber,
  isString,
  isUndefined,
  kReactNativeNotifeeForegroundServiceHeadlessTask,
  kReactNativeNotifeeNotificationBackgroundEvent,
  kReactNativeNotifeeNotificationEvent,
} from './utils';
import validateNotification from './validators/validateNotification';
import validateTrigger from './validators/validateTrigger';
import validateAndroidChannel from './validators/validateAndroidChannel';
import validateAndroidChannelGroup from './validators/validateAndroidChannelGroup';
import {
  IOSNotificationCategory,
  IOSNotificationSettings,
  IOSNotificationPermissions,
} from './types/NotificationIOS';
import validateIOSCategory from './validators/validateIOSCategory';
import validateIOSPermissions from './validators/validateIOSPermissions';

let backgroundEventHandler: (event: Event) => Promise<void>;

let registeredForegroundServiceTask: (notification: Notification) => Promise<void>;

if (isAndroid) {
  // Register foreground service
  AppRegistry.registerHeadlessTask(kReactNativeNotifeeForegroundServiceHeadlessTask, () => {
    if (!registeredForegroundServiceTask) {
      console.warn(
        '[notifee] no registered foreground service has been set for displaying a foreground notification.',
      );
      return (): Promise<void> => Promise.resolve();
    }

    return ({ notification }): Promise<void> => registeredForegroundServiceTask(notification);
  });
}

export default class NotifeeApiModule extends NotifeeNativeModule implements Module {
  constructor(config: NativeModuleConfig) {
    super(config);
    if (isAndroid) {
      // Register background handler
      AppRegistry.registerHeadlessTask(kReactNativeNotifeeNotificationEvent, () => {
        return (event: Event): Promise<void> => {
          if (!backgroundEventHandler) {
            console.warn(
              '[notifee] no background event handler has been set. Set a handler via the "onBackgroundEvent" method.',
            );
            return Promise.resolve();
          }
          return backgroundEventHandler(event);
        };
      });
    } else if (isIOS) {
      this.emitter.addListener(
        kReactNativeNotifeeNotificationBackgroundEvent,
        (event: Event): Promise<void> => {
          if (!backgroundEventHandler) {
            console.warn(
              '[notifee] no background event handler has been set. Set a handler via the "onBackgroundEvent" method.',
            );
            return Promise.resolve();
          }

          return backgroundEventHandler(event);
        },
      );
    }
  }

  public getTriggerNotificationIds = (): Promise<string[]> => {
    return this.native.getTriggerNotificationIds();
  };

  public getTriggerNotifications = (): Promise<TriggerNotification[]> => {
    return this.native.getTriggerNotifications();
  };

  public getDisplayedNotifications = (): Promise<DisplayedNotification[]> => {
    return this.native.getDisplayedNotifications();
  };

  public isChannelBlocked = (channelId: string): Promise<boolean> => {
    if (!isString(channelId)) {
      throw new Error("notifee.isChannelBlocked(*) 'channelId' expected a string value.");
    }

    if (isIOS || this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve(false);
    }

    return this.native.isChannelBlocked(channelId);
  };

  public isChannelCreated = (channelId: string): Promise<boolean> => {
    if (!isString(channelId)) {
      channelId;
      throw new Error("notifee.isChannelCreated(*) 'channelId' expected a string value.");
    }

    if (isIOS || this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve(true);
    }

    return this.native.isChannelCreated(channelId);
  };

  public cancelAllNotifications = (notificationIds?: string[]): Promise<void> => {
    if (notificationIds) return this.native.cancelAllNotificationsWithIds(notificationIds);
    return this.native.cancelAllNotifications();
  };

  public cancelDisplayedNotifications = (notificationIds?: string[]): Promise<void> => {
    if (notificationIds) return this.native.cancelDisplayedNotificationsWithIds(notificationIds);
    return this.native.cancelDisplayedNotifications();
  };

  public cancelTriggerNotifications = (notificationIds?: string[]): Promise<void> => {
    if (notificationIds) return this.native.cancelTriggerNotificationsWithIds(notificationIds);
    return this.native.cancelTriggerNotifications();
  };

  public cancelNotification = (notificationId: string): Promise<void> => {
    if (!isString(notificationId)) {
      throw new Error("notifee.cancelNotification(*) 'notificationId' expected a string value.");
    }

    return this.native.cancelNotification(notificationId);
  };

  public cancelDisplayedNotification = (notificationId: string): Promise<void> => {
    if (!isString(notificationId)) {
      throw new Error(
        "notifee.cancelDisplayedNotification(*) 'notificationId' expected a string value.",
      );
    }

    return this.native.cancelDisplayedNotification(notificationId);
  };

  public cancelTriggerNotification = (notificationId: string): Promise<void> => {
    if (!isString(notificationId)) {
      throw new Error(
        "notifee.cancelTriggerNotification(*) 'notificationId' expected a string value.",
      );
    }

    return this.native.cancelTriggerNotification(notificationId);
  };

  public createChannel = (channel: AndroidChannel): Promise<string> => {
    let options: AndroidChannel;
    try {
      options = validateAndroidChannel(channel);
    } catch (e) {
      throw new Error(`notifee.createChannel(*) ${e.message}`);
    }

    if (isIOS) {
      return Promise.resolve('');
    }

    if (this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve(options.id);
    }

    return this.native.createChannel(options).then(() => {
      return options.id;
    });
  };

  public createChannels = (channels: AndroidChannel[]): Promise<void> => {
    if (!isArray(channels)) {
      throw new Error("notifee.createChannels(*) 'channels' expected an array of AndroidChannel.");
    }

    const options: AndroidChannel[] = [];
    try {
      for (let i = 0; i < channels.length; i++) {
        options[i] = validateAndroidChannel(channels[i]);
      }
    } catch (e) {
      throw new Error(`notifee.createChannels(*) 'channels' a channel is invalid: ${e.message}`);
    }

    if (isIOS || this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve();
    }

    return this.native.createChannels(options);
  };

  public createChannelGroup = (channelGroup: AndroidChannelGroup): Promise<string> => {
    let options: AndroidChannelGroup;
    try {
      options = validateAndroidChannelGroup(channelGroup);
    } catch (e) {
      throw new Error(`notifee.createChannelGroup(*) ${e.message}`);
    }

    if (this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve(options.id);
    }

    if (isIOS) {
      return Promise.resolve('');
    }

    return this.native.createChannelGroup(options).then(() => {
      return options.id;
    });
  };

  public createChannelGroups = (channelGroups: AndroidChannelGroup[]): Promise<void> => {
    if (!isArray(channelGroups)) {
      throw new Error(
        "notifee.createChannelGroups(*) 'channelGroups' expected an array of AndroidChannelGroup.",
      );
    }

    const options = [];
    try {
      for (let i = 0; i < channelGroups.length; i++) {
        options[i] = validateAndroidChannelGroup(channelGroups[i]);
      }
    } catch (e) {
      throw new Error(
        `notifee.createChannelGroups(*) 'channelGroups' a channel group is invalid: ${e.message}`,
      );
    }

    if (isIOS || this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve();
    }

    return this.native.createChannelGroups(options);
  };

  public deleteChannel = (channelId: string): Promise<void> => {
    if (!isString(channelId)) {
      throw new Error("notifee.deleteChannel(*) 'channelId' expected a string value.");
    }

    if (isIOS || this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve();
    }

    return this.native.deleteChannel(channelId);
  };

  public deleteChannelGroup = (channelGroupId: string): Promise<void> => {
    if (!isString(channelGroupId)) {
      throw new Error("notifee.deleteChannelGroup(*) 'channelGroupId' expected a string value.");
    }

    if (isIOS || this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve();
    }

    return this.native.deleteChannelGroup(channelGroupId);
  };

  public displayNotification = (notification: Notification): Promise<string> => {
    let options: Notification;
    try {
      options = validateNotification(notification);
    } catch (e) {
      throw new Error(`notifee.displayNotification(*) ${e.message}`);
    }

    return this.native.displayNotification(options).then((): string => {
      return options.id as string;
    });
  };

  public createTriggerNotification = (
    notification: Notification,
    trigger: Trigger,
  ): Promise<string> => {
    let options: Notification;
    let triggerOptions: Trigger;

    try {
      options = validateNotification(notification);
    } catch (e) {
      throw new Error(`notifee.createTriggerNotification(*) ${e.message}`);
    }

    try {
      triggerOptions = validateTrigger(trigger);
    } catch (e) {
      throw new Error(`notifee.createTriggerNotification(*) ${e.message}`);
    }

    return this.native.createTriggerNotification(options, triggerOptions).then((): string => {
      return options.id as string;
    });
  };

  public getChannel = (channelId: string): Promise<NativeAndroidChannel | null> => {
    if (!isString(channelId)) {
      throw new Error("notifee.getChannel(*) 'channelId' expected a string value.");
    }

    if (isIOS || this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve(null);
    }

    return this.native.getChannel(channelId);
  };

  public getChannels = (): Promise<NativeAndroidChannel[]> => {
    if (isIOS || this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve([]);
    }

    return this.native.getChannels();
  };

  public getChannelGroup = (channelGroupId: string): Promise<NativeAndroidChannelGroup | null> => {
    if (!isString(channelGroupId)) {
      throw new Error("notifee.getChannelGroup(*) 'channelGroupId' expected a string value.");
    }

    if (isIOS || this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve(null);
    }

    return this.native.getChannelGroup(channelGroupId);
  };

  public getChannelGroups = (): Promise<NativeAndroidChannelGroup[]> => {
    if (isIOS || this.native.ANDROID_API_LEVEL < 26) {
      return Promise.resolve([]);
    }

    return this.native.getChannelGroups();
  };

  public getInitialNotification = (): Promise<InitialNotification | null> => {
    return this.native.getInitialNotification();
  };

  public onBackgroundEvent = (observer: (event: Event) => Promise<void>): void => {
    if (!isFunction(observer)) {
      throw new Error("notifee.onBackgroundEvent(*) 'observer' expected a function.");
    }

    backgroundEventHandler = observer;
  };

  public onForegroundEvent = (observer: (event: Event) => void): (() => void) => {
    if (!isFunction(observer)) {
      throw new Error("notifee.onForegroundEvent(*) 'observer' expected a function.");
    }

    const subscriber = this.emitter.addListener(
      kReactNativeNotifeeNotificationEvent,
      ({ type, detail }) => {
        observer({ type, detail });
      },
    );

    return (): void => {
      subscriber.remove();
    };
  };

  public openNotificationSettings = (channelId?: string): Promise<void> => {
    if (!isUndefined(channelId) && !isString(channelId)) {
      throw new Error("notifee.openNotificationSettings(*) 'channelId' expected a string value.");
    }

    if (isIOS) {
      return Promise.resolve();
    }

    return this.native.openNotificationSettings(channelId || null);
  };

  public requestPermission = (
    permissions: IOSNotificationPermissions = {},
  ): Promise<IOSNotificationSettings> => {
    if (isAndroid) {
      // Android doesn't require permission, so instead we
      // return a dummy response to allow the permissions
      // flow work the same on both iOS & Android
      return Promise.resolve({
        alert: 1,
        badge: 1,
        criticalAlert: 1,
        showPreviews: 1,
        sound: 1,
        carPlay: 1,
        lockScreen: 1,
        announcement: 1,
        notificationCenter: 1,
        inAppNotificationSettings: 1,
        authorizationStatus: 1,
      } as IOSNotificationSettings);
    }

    let options: IOSNotificationPermissions;
    try {
      options = validateIOSPermissions(permissions);
    } catch (e) {
      throw new Error(`notifee.requestPermission(*) ${e.message}`);
    }

    return this.native.requestPermission(options);
  };

  public registerForegroundService(runner: (notification: Notification) => Promise<void>): void {
    if (!isFunction(runner)) {
      throw new Error("notifee.registerForegroundService(_) 'runner' expected a function.");
    }

    if (isIOS) {
      return;
    }

    registeredForegroundServiceTask = runner;
  }

  public setNotificationCategories = (categories: IOSNotificationCategory[]): Promise<void> => {
    if (isAndroid) {
      return Promise.resolve();
    }

    if (!isArray(categories)) {
      throw new Error(
        "notifee.setNotificationCategories(*) 'categories' expected an array of IOSCategory.",
      );
    }

    const options = [];
    try {
      for (let i = 0; i < categories.length; i++) {
        options[i] = validateIOSCategory(categories[i]);
      }
    } catch (e) {
      throw new Error(
        `notifee.setNotificationCategories(*) 'categories' a category is invalid: ${e.message}`,
      );
    }

    return this.native.setNotificationCategories(categories);
  };

  public getNotificationCategories = (): Promise<IOSNotificationCategory[]> => {
    if (isAndroid) {
      return Promise.resolve([]);
    }

    return this.native.getNotificationCategories();
  };

  public getNotificationSettings = (): Promise<IOSNotificationSettings> => {
    if (isAndroid) {
      // Android doesn't support this, so instead we
      // return a dummy response to allow the permissions
      // flow work the same on both iOS & Android
      return Promise.resolve({
        alert: 1,
        badge: 1,
        criticalAlert: 1,
        showPreviews: 1,
        sound: 1,
        carPlay: 1,
        lockScreen: 1,
        announcement: 1,
        notificationCenter: 1,
        inAppNotificationSettings: 1,
        authorizationStatus: 1,
      } as IOSNotificationSettings);
    }

    return this.native.getNotificationSettings();
  };

  public getBadgeCount = (): Promise<number> => {
    if (isAndroid) {
      return Promise.resolve(0);
    }

    return this.native.getBadgeCount();
  };

  public setBadgeCount = (count: number): Promise<void> => {
    if (isAndroid) {
      return Promise.resolve();
    }

    if (!isNumber(count) || count < 0) {
      throw new Error("notifee.setBadgeCount(*) 'count' expected a number value greater than 0.");
    }

    return this.native.setBadgeCount(Math.round(count));
  };

  public incrementBadgeCount = (incrementBy?: number): Promise<void> => {
    if (isAndroid) {
      return Promise.resolve();
    }

    let value = 1;
    if (!isUndefined(incrementBy)) {
      if (!isNumber(incrementBy) || incrementBy < 1) {
        throw new Error(
          "notifee.decrementBadgeCount(*) 'incrementBy' expected a number value greater than 1.",
        );
      }

      value = incrementBy;
    }

    return this.native.incrementBadgeCount(Math.round(value));
  };

  public decrementBadgeCount = (decrementBy?: number): Promise<void> => {
    if (isAndroid) {
      return Promise.resolve();
    }

    let value = 1;
    if (!isUndefined(decrementBy)) {
      if (!isNumber(decrementBy) || decrementBy < 1) {
        throw new Error(
          "notifee.decrementBadgeCount(*) 'decrementBy' expected a number value greater than 1.",
        );
      }

      value = decrementBy;
    }

    return this.native.decrementBadgeCount(Math.round(value));
  };

  public isBatteryOptimizationEnabled = (): Promise<boolean> => {
    if (isIOS) {
      return Promise.resolve(false);
    }

    return this.native.isBatteryOptimizationEnabled();
  };

  public openBatteryOptimizationSettings = (): Promise<void> => {
    if (isIOS) {
      return Promise.resolve();
    }
    return this.native.openBatteryOptimizationSettings();
  };

  public getPowerManagerInfo = (): Promise<PowerManagerInfo> => {
    if (isIOS) {
      // iOS doesn't support this, so instead we
      // return a dummy response to allow the power manager
      // flow work the same on both iOS & Android
      return Promise.resolve({
        manufacturer: 'apple',
        activity: null,
      } as PowerManagerInfo);
    }

    return this.native.getPowerManagerInfo();
  };

  public openPowerManagerSettings = (): Promise<void> => {
    if (isIOS) {
      return Promise.resolve();
    }
    return this.native.openPowerManagerSettings();
  };

  public stopForegroundService = (): Promise<void> => {
    if (isIOS) {
      return Promise.resolve();
    }
    return this.native.stopForegroundService();
  };

  public hideNotificationDrawer = (): void => {
    if (isIOS) {
      return;
    }
    return this.native.hideNotificationDrawer();
  };
}
