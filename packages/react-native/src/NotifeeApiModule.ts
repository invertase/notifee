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

export default class NotifeeApiModule extends NotifeeNativeModule implements Module {
  cancelAllNotifications(): Promise<void> {
    throw new Error('Method not implemented.');
  }

  cancelNotification(notificationId: string): Promise<void> {
    throw new Error('Method not implemented.');
  }

  createChannel(channel: AndroidChannel): Promise<string> {
    throw new Error('Method not implemented.');
  }

  createChannels(channels: AndroidChannel[]): Promise<void> {
    throw new Error('Method not implemented.');
  }

  createChannelGroup(channelGroup: AndroidChannelGroup): Promise<string> {
    throw new Error('Method not implemented.');
  }

  createChannelGroups(channelGroups: AndroidChannelGroup[]): Promise<void> {
    throw new Error('Method not implemented.');
  }

  deleteChannel(channelId: string): Promise<void> {
    throw new Error('Method not implemented.');
  }

  deleteChannelGroup(channelGroupId: string): Promise<void> {
    throw new Error('Method not implemented.');
  }

  displayNotification(notification: NotificationBuilder): Promise<string> {
    throw new Error('Method not implemented.');
  }

  getChannel(channelId: string): Promise<AndroidChannel | null> {
    throw new Error('Method not implemented.');
  }

  getChannels(): Promise<AndroidChannel[]> {
    throw new Error('Method not implemented.');
  }

  getChannelGroup(channelGroupId: string): Promise<AndroidChannelGroup | null> {
    throw new Error('Method not implemented.');
  }

  getChannelGroups(): Promise<AndroidChannelGroup[]> {
    throw new Error('Method not implemented.');
  }

  getBadge(): Promise<number | null> {
    throw new Error('Method not implemented.');
  }

  getInitialNotification(): Promise<RemoteNotification | null> {
    throw new Error('Method not implemented.');
  }

  getScheduledNotifications(): Promise<RemoteNotification[]> {
    throw new Error('Method not implemented.');
  }

  onNotification(observer: NotificationObserver): NotificationObserverUnsubscribe {
    throw new Error('Method not implemented.');
  }

  onNotificationDisplayed(observer: NotificationObserver): NotificationObserverUnsubscribe {
    throw new Error('Method not implemented.');
  }

  onNotificationOpened(observer: NotificationObserver): NotificationObserverUnsubscribe {
    throw new Error('Method not implemented.');
  }

  removeAllDeliveredNotifications(): Promise<void> {
    throw new Error('Method not implemented.');
  }

  removeDeliveredNotification(notificationId: string): Promise<void> {
    throw new Error('Method not implemented.');
  }

  scheduleNotification(
    notification: NotificationBuilder,
    schedule: NotificationSchedule,
  ): Promise<void> {
    throw new Error('Method not implemented.');
  }

  setBadge(badge: number): Promise<void> {
    throw new Error('Method not implemented.');
  }
}
