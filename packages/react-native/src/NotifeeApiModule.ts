/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { AppRegistry } from 'react-native';
import { Module } from '../types/Module';
import {
  AndroidChannel,
  AndroidChannelGroup,
  NativeAndroidChannel,
  NativeAndroidChannelGroup,
} from '../types/NotificationAndroid';
import {
  Notification,
  NotificationBuilder,
  NotificationObserver,
  NotificationObserverUnsubscribe,
  NotificationSchedule,
  RemoteNotification,
} from '../types/Notification';
import NotifeeNativeModule from './NotifeeNativeModule';

import { isFunction, isNumber, isString, isIOS, isArray, isNull, isUndefined } from './utils';
import validateNotification from './validators/validateNotification';
import validateSchedule from './validators/validateSchedule';
import validateAndroidChannel from './validators/validateAndroidChannel';
import validateAndroidChannelGroup from './validators/validateAndroidChannelGroup';


export default class NotifeeApiModule extends NotifeeNativeModule implements Module {
  public cancelAllNotifications(): Promise<void> {
    return this.native.cancelAllNotifications();
  }

  public cancelNotification(notificationId: string): Promise<void> {
    if (!isString(notificationId)) {
      throw new Error("notifee.cancelNotification(*) 'notificationId' expected a string value.");
    }

    return this.native.cancelNotification(notificationId);
  }

  public createChannel(channel: AndroidChannel): Promise<string> {
    let options: AndroidChannel;
    try {
      options = validateAndroidChannel(channel);
    } catch (e) {
      throw new Error(`notifee.createChannel(*) ${e.message}`);
    }

    if (isIOS) {
      return Promise.resolve('');
    }

    if (this.core.ANDROID_API_LEVEL < 26) {
      return Promise.resolve(options.id);
    }

    return this.native.createChannel(options).then(() => {
      return options.id;
    });
  }

  public createChannels(channels: AndroidChannel[]): Promise<void> {
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

    if (isIOS || this.core.ANDROID_API_LEVEL < 26) {
      return Promise.resolve();
    }

    return this.native.createChannels(options);
  }

  public createChannelGroup(channelGroup: AndroidChannelGroup): Promise<string> {
    let options: AndroidChannelGroup;
    try {
      options = validateAndroidChannelGroup(channelGroup);
    } catch (e) {
      throw new Error(`notifee.createChannelGroup(*) ${e.message}`);
    }

    if (this.core.ANDROID_API_LEVEL < 26) {
      return Promise.resolve(options.id);
    }

    if (isIOS) {
      return Promise.resolve('');
    }

    return this.native.createChannelGroup(options).then(() => {
      return options.id;
    });
  }

  public createChannelGroups(channelGroups: AndroidChannelGroup[]): Promise<void> {
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

    if (isIOS || this.core.ANDROID_API_LEVEL < 26) {
      return Promise.resolve();
    }

    return this.native.createChannelGroups(options);
  }

  public deleteChannel(channelId: string): Promise<void> {
    if (!isString(channelId)) {
      throw new Error("notifee.deleteChannel(*) 'channelId' expected a string value.");
    }

    if (isIOS || this.core.ANDROID_API_LEVEL < 26) {
      return Promise.resolve();
    }

    return this.native.deleteChannel(channelId);
  }

  public deleteChannelGroup(channelGroupId: string): Promise<void> {
    if (!isString(channelGroupId)) {
      throw new Error("notifee.deleteChannelGroup(*) 'channelGroupId' expected a string value.");
    }

    if (isIOS || this.core.ANDROID_API_LEVEL < 26) {
      return Promise.resolve();
    }

    return this.native.deleteChannelGroup(channelGroupId);
  }

  public displayNotification(notification: NotificationBuilder): Promise<string> {
    let options: NotificationBuilder;
    try {
      options = validateNotification(notification);
    } catch (e) {
      throw new Error(`notifee.displayNotification(*) ${e.message}`);
    }

    return this.native.displayNotification(options).then(() => {
      return options.id;
    });
  }

  public getChannel(channelId: string): Promise<NativeAndroidChannel | null> {
    if (!isString(channelId)) {
      throw new Error("notifee.getChannel(*) 'channelId' expected a string value.");
    }

    if (isIOS || this.core.ANDROID_API_LEVEL < 26) {
      return Promise.resolve(null);
    }

    return this.native.getChannel(channelId);
  }

  public getChannels(): Promise<NativeAndroidChannel[]> {
    if (isIOS || this.core.ANDROID_API_LEVEL < 26) {
      return Promise.resolve([]);
    }

    return this.native.getChannels();
  }

  public getChannelGroup(channelGroupId: string): Promise<NativeAndroidChannelGroup | null> {
    if (!isString(channelGroupId)) {
      throw new Error("notifee.getChannelGroup(*) 'channelGroupId' expected a string value.");
    }

    if (isIOS || this.core.ANDROID_API_LEVEL < 26) {
      return Promise.resolve(null);
    }

    return this.native.getChannelGroup(channelGroupId);
  }

  public getChannelGroups(): Promise<NativeAndroidChannelGroup[]> {
    if (isIOS || this.core.ANDROID_API_LEVEL < 26) {
      return Promise.resolve([]);
    }

    return this.native.getChannelGroups();
  }

  // TODO is the return direct from native a valid RemoteNotification
  public getInitialNotification(): Promise<RemoteNotification | null> {
    return this.native.getInitialNotification();
  }

  // TODO is the return direct from native a valid RemoteNotification array
  public getScheduledNotifications(): Promise<RemoteNotification[]> {
    return this.native.getScheduledNotifications();
  }

  public onNotification(observer: NotificationObserver): NotificationObserverUnsubscribe {
    if (!isFunction(observer)) {
      throw new Error("notifee.onNotification(*) 'observer' expected a function.");
    }

    // todo return subscriber
    return (): void => {};
  }

  public onNotificationDisplayed(observer: NotificationObserver): NotificationObserverUnsubscribe {
    if (!isFunction(observer)) {
      throw new Error("notifee.onNotificationDisplayed(*) 'observer' expected a function.");
    }

    // todo return subscriber
    return (): void => {};
  }

  public onNotificationOpened(observer: NotificationObserver): NotificationObserverUnsubscribe {
    if (!isFunction(observer)) {
      throw new Error("notifee.onNotificationOpened(*) 'observer' expected a function.");
    }

    // todo return subscriber
    return (): void => {};
  }

  public openNotificationSettings(channelId?: string): Promise<void> {
    if (!isUndefined(channelId) && !isString(channelId)) {
      throw new Error("notifee.openNotificationSettings(*) 'channelId' expected a string value.");
    }

    if (isIOS) {
      return Promise.resolve();
    }

    return this.native.openNotificationSettings(channelId || null);
  }

  public registerForegroundService(runner: (notification: Notification) => Promise<void>): void {
    if (!isFunction(runner)) {
      throw new Error("notifee.registerForegroundService(_, *) 'runner' expected a function.");
    }

    if (isIOS) {
      return;
    }

    AppRegistry.registerHeadlessTask(this.core.NOTIFEE_FOREGROUND_SERVICE, () => {
      return ({ notification }) => runner(notification);
    });
  }

  public removeAllDeliveredNotifications(): Promise<void> {
    return this.native.removeAllDeliveredNotifications();
  }

  public removeDeliveredNotification(notificationId: string): Promise<void> {
    if (!isString(notificationId)) {
      throw new Error(
        "notifee.removeDeliveredNotification(*) 'notificationId' expected a string value.",
      );
    }

    return this.native.removeDeliveredNotification(notificationId);
  }

  public scheduleNotification(
    notification: NotificationBuilder,
    schedule: NotificationSchedule,
  ): Promise<void> {
    let notificationOptions;
    try {
      notificationOptions = validateNotification(notification);
    } catch (e) {
      throw new Error(`notifee.scheduleNotification(*) ${e.message}`);
    }

    let scheduleOptions;
    try {
      scheduleOptions = validateSchedule(schedule);
    } catch (e) {
      throw new Error(`notifee.scheduleNotification(_, *) ${e.message}`);
    }

    return this.native.scheduleNotification(notificationOptions, scheduleOptions);
  }
}
