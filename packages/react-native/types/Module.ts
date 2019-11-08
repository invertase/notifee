/*
 * Copyright (c) 2016-present Invertase Limited
 */

import {
  NotificationBuilder,
  NotificationObserver,
  NotificationObserverUnsubscribe,
  NotificationRepeatInterval,
  NotificationSchedule,
  RemoteNotification,
} from './Notification';
import {
  AndroidBadgeIconType,
  AndroidCategory,
  AndroidChannel,
  AndroidChannelGroup,
  AndroidColor,
  AndroidDefaults,
  AndroidGroupAlertBehavior,
  AndroidImportance,
  AndroidPriority,
  AndroidSemanticAction,
  AndroidStyle,
  AndroidVisibility,
} from './NotificationAndroid';

/**
 * TODO
 */
export interface Module {
  cancelAllNotifications(): Promise<void>;

  cancelNotification(notificationId: string): Promise<void>;

  /**
   * Creates a new Android channel. Channels are used to collectively assign notifications to
   * a single responsible channel. Users can manage settings for channels, e.g. disabling sound or vibration.
   * Channels can be further organized into groups (see `createChannelGroup`).
   *
   * Once a channel has been created, only certain fields such as the name & description can be
   * modified. To change a groups settings, you must delete the group (via `deleteChannel`)
   * and re-create it. Keep in mind the user always have final control over channel settings.
   *
   * Creating an existing notification channel with its original values performs no operation,
   * so it's safe to call this code when starting an app.
   *
   * Created channels can be viewed/managed under App Info -> Notifications.
   *
   * > On Android 8.0 (API 26) all notifications must be assigned to a channel.
   *
   * Returns the channel ID.
   *
   * #### Example
   *
   * ```js
   * const channelId = await notifee.createChannel({
   *   channelId: 'custom-channel',
   *   name: 'Custom Channel',
   *   description: 'A test channel',
   * });
   *
   * await notifee.displayNotification({
   *   body: 'Test notification',
   *   android: {
   *     channelId, // 'custom-channel'
   *   },
   * });
   * ```
   *
   * @param channel An `AndroidChannel` interface.
   */
  createChannel(channel: AndroidChannel): Promise<string>;

  /**
   * Creates multiple channels in a single operation.
   *
   * See `createChannel` for more information.
   *
   * @param channels An array of AndroidChannel interfaces.
   */
  createChannels(channels: AndroidChannel[]): Promise<void>;

  createChannelGroup(channelGroup: AndroidChannelGroup): Promise<string>;

  createChannelGroups(channelGroups: AndroidChannelGroup[]): Promise<void>;

  /**
   * Deletes a channel by ID.
   *
   * #### Example
   *
   * ```js
   * await notifee.deleteChannel('custom-channel');
   * ```
   *
   * @param channelId The channel ID to delete.
   */
  deleteChannel(channelId: string): Promise<void>;

  deleteChannelGroup(channelGroupId: string): Promise<void>;

  /**
   * Displays a notification on the device.
   *
   * See `NotificationAndroid` and `NotificationIOS` for platform specific options.
   *
   * #### Example
   *
   * ```js
   * await notifee.displayNotification({
   *   title: 'Test',
   *   body: 'Test notification body',
   *   android: {
   *     // Android specific options
   *   },
   *   ios: {
   *     // iOS specific options
   *   },
   * });
   * ```
   *
   * @param notification A `Notification` interface.
   * @return Promise<string> A promise that resolves the new notification id
   */
  displayNotification(notification: NotificationBuilder): Promise<string>;

  /**
   * Returns a single `AndroidChannel` by id.
   *
   * Returns `null` if no channel could be matched to the given ID.
   *
   * @param channelId The channel id.
   * @platform android
   */
  getChannel(channelId: string): Promise<AndroidChannel | null>;

  /**
   * Returns an array of `AndroidChannel` which are currently active on the device.
   *
   * @platform android
   */
  getChannels(): Promise<AndroidChannel[]>;

  getChannelGroup(channelGroupId: string): Promise<AndroidChannelGroup | null>;

  getChannelGroups(): Promise<AndroidChannelGroup[]>;

  // todo null if no badge?
  getBadge(): Promise<number | null>;

  getInitialNotification(): Promise<RemoteNotification | null>;

  getScheduledNotifications(): Promise<RemoteNotification[]>;

  onNotification(observer: NotificationObserver): NotificationObserverUnsubscribe;

  onNotificationDisplayed(observer: NotificationObserver): NotificationObserverUnsubscribe;

  onNotificationOpened(observer: NotificationObserver): NotificationObserverUnsubscribe;

  removeAllDeliveredNotifications(): Promise<void>;

  removeDeliveredNotification(notificationId: string): Promise<void>;

  scheduleNotification(
    notification: NotificationBuilder,
    schedule: NotificationSchedule,
  ): Promise<void>;

  setBadge(badge: number): Promise<void>;
}

/**
 * TODO
 */
export interface ModuleStatics {
  AndroidBadgeIconType: typeof AndroidBadgeIconType;
  AndroidCategory: typeof AndroidCategory;
  AndroidGroupAlertBehavior: typeof AndroidGroupAlertBehavior;
  AndroidSemanticAction: typeof AndroidSemanticAction;
  AndroidPriority: typeof AndroidPriority;
  AndroidVisibility: typeof AndroidVisibility;
  AndroidDefaults: typeof AndroidDefaults;
  AndroidImportance: typeof AndroidImportance;
  AndroidColor: typeof AndroidColor;
  AndroidStyle: typeof AndroidStyle;
  NotificationRepeatInterval: typeof NotificationRepeatInterval;
  SDK_VERSION: string;
}

// eslint-disable-next-line @typescript-eslint/no-empty-interface
/**
 * TODO
 */
export interface ModuleWithStatics extends Module, ModuleStatics {}
