/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { Module } from '../types/Module';
import { AndroidChannel, AndroidChannelGroup } from '../types/NotificationAndroid';
import {
  NotificationBuilder,
  NotificationObserver,
  NotificationObserverUnsubscribe,
  NotificationSchedule,
  RemoteNotification,
} from '../types/Notification';
import NotifeeNativeModule from './NotifeeNativeModule';

// TODO migrate to TS?
import { isFunction, isNumber, isString, isIOS, isArray, isNull } from '../lib/utils';
import validateNotification from '../lib/validateNotification';
import validateSchedule from '../lib/validateSchedule';
import validateAndroidChannel from '../lib/validateAndroidChannel';
import validateAndroidChannelGroup from '../lib/validateAndroidChannelGroup';

export default class NotifeeApiModule extends NotifeeNativeModule implements Module {
  cancelAllNotifications(): Promise<void> {
    return this.native.cancelAllNotifications();
  }

  cancelNotification(notificationId: string): Promise<void> {
    if (!isString(notificationId)) {
      throw new Error("notifee.cancelNotification(*) 'notificationId' expected a string value.");
    }

    return this.native.cancelNotification(notificationId);
  }

  createChannel(channel: AndroidChannel): Promise<string> {
    let options: any; // TODO options type
    try {
      options = validateAndroidChannel(channel);
    } catch (e) {
      throw new Error(`notifee.createChannel(*) ${e.message}`);
    }

    if (isIOS) {
      return Promise.resolve('');
    }

    return this.native.createChannel(options).then(() => {
      return options.channelId;
    });
  }

  createChannels(channels: AndroidChannel[]): Promise<void> {
    if (!isArray(channels)) {
      throw new Error("notifee.createChannels(*) 'channels' expected an array of AndroidChannel.");
    }

    const options = [];
    try {
      for (let i = 0; i < channels.length; i++) {
        options[i] = validateAndroidChannel(channels[i]);
      }
    } catch (e) {
      throw new Error(`notifee.createChannels(*) 'channels' a channel is invalid: ${e.message}`);
    }

    if (isIOS) {
      return Promise.resolve();
    }

    return this.native.createChannels(options);
  }

  createChannelGroup(channelGroup: AndroidChannelGroup): Promise<string> {
    let options: any; // TODO options type
    try {
      options = validateAndroidChannelGroup(channelGroup);
    } catch (e) {
      throw new Error(`notifee.createChannelGroup(*) ${e.message}`);
    }

    if (isIOS) {
      return Promise.resolve('');
    }

    return this.native.createChannelGroup(options).then(() => {
      return options.channelGroupId;
    });
  }

  createChannelGroups(channelGroups: AndroidChannelGroup[]): Promise<void> {
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

    if (isIOS) {
      return Promise.resolve();
    }

    return this.native.createChannelGroups(options);
  }

  deleteChannel(channelId: string): Promise<void> {
    if (!isString(channelId)) {
      throw new Error("notifee.deleteChannel(*) 'channelId' expected a string value.");
    }

    if (isIOS) {
      return Promise.resolve();
    }

    return this.native.deleteChannel(channelId);
  }

  deleteChannelGroup(channelGroupId: string): Promise<void> {
    if (!isString(channelGroupId)) {
      throw new Error("notifee.deleteChannelGroup(*) 'channelGroupId' expected a string value.");
    }

    if (isIOS) {
      return Promise.resolve();
    }

    return this.native.deleteChannelGroup(channelGroupId);
  }

  displayNotification(notification: NotificationBuilder): Promise<string> {
    let options: any; // TODO options type
    try {
      options = validateNotification(notification);
    } catch (e) {
      throw new Error(`notifee.displayNotification(*) ${e.message}`);
    }

    return this.native.displayNotification(options).then(() => {
      return options.notificationId;
    });
  }

  getChannel(channelId: string): Promise<AndroidChannel | null> {
    if (!isString(channelId)) {
      throw new Error("notifee.getChannel(*) 'channelId' expected a string value.");
    }

    if (isIOS) {
      return Promise.resolve(null);
    }

    return this.native.getChannel(channelId);
  }

  getChannels(): Promise<AndroidChannel[]> {
    if (isIOS) {
      return Promise.resolve([]);
    }

    return this.native.getChannels();
  }

  getChannelGroup(channelGroupId: string): Promise<AndroidChannelGroup | null> {
    if (!isString(channelGroupId)) {
      throw new Error("notifee.getChannelGroup(*) 'channelGroupId' expected a string value.");
    }

    if (isIOS) {
      return Promise.resolve(null);
    }

    return this.native.getChannelGroup(channelGroupId);
  }

  getChannelGroups(): Promise<AndroidChannelGroup[]> {
    if (isIOS) {
      return Promise.resolve([]);
    }

    return this.native.getChannelGroups();
  }

  getBadge(): Promise<number | null> {
    return this.native.getBadge();
  }

  // TODO is the return direct from native a valid RemoteNotification
  getInitialNotification(): Promise<RemoteNotification | null> {
    return this.native.getInitialNotification();
  }

  // TODO is the return direct from native a valid RemoteNotification array
  getScheduledNotifications(): Promise<RemoteNotification[]> {
    return this.native.getScheduledNotifications();
  }

  onNotification(observer: NotificationObserver): NotificationObserverUnsubscribe {
    if (!isFunction(observer)) {
      throw new Error("notifee.onNotification(*) 'observer' expected a function.");
    }

    // todo return subscriber
    return (): void => {};
  }

  onNotificationDisplayed(observer: NotificationObserver): NotificationObserverUnsubscribe {
    if (!isFunction(observer)) {
      throw new Error("notifee.onNotificationDisplayed(*) 'observer' expected a function.");
    }

    // todo return subscriber
    return (): void => {};
  }

  onNotificationOpened(observer: NotificationObserver): NotificationObserverUnsubscribe {
    if (!isFunction(observer)) {
      throw new Error("notifee.onNotificationOpened(*) 'observer' expected a function.");
    }

    // todo return subscriber
    return (): void => {};
  }

  removeAllDeliveredNotifications(): Promise<void> {
    return this.native.removeAllDeliveredNotifications();
  }

  removeDeliveredNotification(notificationId: string): Promise<void> {
    if (!isString(notificationId)) {
      throw new Error(
        "notifee.removeDeliveredNotification(*) 'notificationId' expected a string value.",
      );
    }

    return this.native.removeDeliveredNotification(notificationId);
  }

  scheduleNotification(
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

  setBadge(badge: number): Promise<void> {
    if (!isNull(badge) || !isNumber(badge)) {
      throw new Error(
        "notifee.removeDeliveredNotification(*) 'badge' expected null or a number value.",
      );
    }

    // todo can a badge be negative?
    return this.native.setBadge(badge);
  }
}
