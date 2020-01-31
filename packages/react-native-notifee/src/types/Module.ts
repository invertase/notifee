/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { InitialNotification, Notification, NotificationEvent } from './Notification';
import {
  AndroidChannel,
  AndroidChannelGroup,
  NativeAndroidChannel,
  NativeAndroidChannelGroup,
} from './NotificationAndroid';

export interface Module {
  /**
   * API used to cancel all notifications.
   *
   * The `cancelAllNotifications` API removes any displayed notifications from the users device and
   * any notifications which have triggers set.
   *
   * This method does not cancel [Foreground Service](/react-native/docs/android/foreground-service)
   * notifications.
   */
  cancelAllNotifications(): Promise<void>;

  /**
   * API used to cancel a single notification.
   *
   * The `cancelNotification` API removes removes any displayed notifications or ones with triggers
   * set for the specified ID.
   *
   * This method does not cancel [Foreground Service](/react-native/docs/android/foreground-service)
   * notifications.
   *
   * @param notificationId The unique notification ID. This is automatically generated and returned
   * when creating a notification, or has been set manually via the `id` property.
   */
  cancelNotification(notificationId: string): Promise<void>;

  /**
   * API to create and update channels on supported Android devices.
   *
   * Creates a new Android channel. Channels are used to collectively assign notifications to
   * a single responsible channel. Users can manage settings for channels, e.g. disabling sound or vibration.
   * Channels can be further organized into groups (see `createChannelGroup`).
   *
   * By providing a `groupId` property, channels can be assigned to groups created with
   * [`createChannelGroup`](/react-native/reference/createchannelgroup).
   *
   * The channel ID is returned once the operation has completed.
   *
   * View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.
   *
   * @platform android
   * @param channel The [`AndroidChannel`](/react-native/reference/androidchannel) interface used to create/update a group.
   */
  createChannel(channel: AndroidChannel): Promise<string>;

  /**
   * API to create and update multiple channels on supported Android devices.
   *
   * This API is used to perform a single operation to create or update channels. See the
   * [`createChannel`](/react-native/reference/createchannel) documentation for more information.
   *
   * @platform android
   * @param channels An array of [`AndroidChannel`](/react-native/reference/androidchannel) interfaces.
   */
  createChannels(channels: AndroidChannel[]): Promise<void>;

  /**
   * API to create or update a channel group on supported Android devices.
   *
   * Creates a new Android channel group. Groups are used to further organize the appearance of your
   * channels in the settings UI. Groups allow users to easily identify and control multiple
   * notification channels.
   *
   * Channels can be assigned to groups during creation using the
   * [`createChannel`](/react-native/reference/createchannel) method.
   *
   * The channel group ID is returned once the operation has completed.
   *
   * View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.
   *
   * @platform android
   * @param channelGroup The [`AndroidChannelGroup`](/react-native/reference/androidchannelgroup)
   * interface used to create/update a group.
   */
  createChannelGroup(channelGroup: AndroidChannelGroup): Promise<string>;

  /**
   * API to create and update multiple channel groups on supported Android devices.
   *
   * This API is used to perform a single operation to create or update channel groups. See the
   * [`createChannelGroup`](/react-native/reference/createchannelgroup) documentation for more information.
   *
   * @platform android
   * @param channelGroups An array of [`AndroidChannelGroup`](/react-native/reference/androidchannelgroup) interfaces.
   */
  createChannelGroups(channelGroups: AndroidChannelGroup[]): Promise<void>;

  /**
   * API used to delete a channel by ID on supported Android devices.
   *
   * Channels can be deleted using this API by providing the channel ID. Channel information (including
   * the ID) can be retrieved from the [`getChannels`](/react-native/reference/getchannels) API.
   *
   * > When a channel is deleted, notifications assigned to that channel will fail to display.
   *
   * View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.
   *
   * @platform android
   * @param channelId The unique channel ID to delete.
   */
  deleteChannel(channelId: string): Promise<void>;

  /**
   * API used to delete a channel group by ID on supported Android devices.
   *
   * Channel groups can be deleted using this API by providing the channel ID. Channel information (including
   * the ID) can be retrieved from the [`getChannels`](/react-native/reference/getchannels) API.
   *
   * Deleting a group does not delete channels which are assigned to the group, they will instead be
   * unassigned the group and continue to function as expected.
   *
   * View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.
   *
   * @platform android
   * @param channelGroupId The unique channel group ID to delete.
   */
  deleteChannelGroup(channelGroupId: string): Promise<void>;

  /**
   * API used to immideatly display or update a notification on the users device.
   *
   * This API is used to display a notification on the users device. All
   * channels/categories should be created before triggering this method during the apps lifecycle.
   *
   * View the [Displaying a Notification](/react-native/docs/displaying-a-notification)
   * documentation for more information.
   *
   * @param notification The [`Notification`](/react-native/reference/notification) interfaced used
   * to create a notification for both Android & iOS.
   */
  displayNotification(notification: Notification): Promise<string>;

  /**
   * API used to return a channel on supported Android devices.
   *
   * This API is used to return a `NativeAndroidChannel`. Returns `null` if no channel could be matched to
   * the given ID.
   *
   * A "native channel" also includes additional properties about the channel at the time it's
   * retrieved from the device. View the [`NativeAndroidChannel`](/react-native/reference/nativeandroidchannel)
   * documentation for more information.
   *
   * View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.
   *
   * @platform android
   * @param channelId The channel ID created with [`createChannel`](/react-native/reference/createchannel). If
   * a unknown channel ID is provided, `null` is returned.
   */
  getChannel(channelId: string): Promise<NativeAndroidChannel | null>;

  /**
   * API used to return all channels on supported Android devices.
   *
   * This API is used to return a `NativeAndroidChannel`. Returns an empty array if no channels
   * exist.
   *
   * A "native channel" also includes additional properties about the channel at the time it's
   * retrieved from the device. View the [`NativeAndroidChannel`](/react-native/reference/nativeandroidchannel)
   * documentation for more information.
   *
   * View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.
   *
   * @platform android
   */
  getChannels(): Promise<NativeAndroidChannel[]>;

  /**
   * API used to return a channel group on supported Android devices.
   *
   * This API is used to return an `NativeAndroidChannelGroup`. Returns `null` if no channel could be matched to
   * the given ID.
   *
   * A "native channel group" also includes additional properties about the channel group at the time it's
   * retrieved from the device. View the [`NativeAndroidChannelGroup`](/react-native/reference/nativeandroidchannelgroup)
   * documentation for more information.
   *
   * View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.
   *
   * @platform android
   * @param channelGroupId The channel ID created with [`createChannelGroup`](/react-native/reference/createchannelgroup). If
   * a unknown channel group ID is provided, `null` is returned.
   */
  getChannelGroup(channelGroupId: string): Promise<NativeAndroidChannelGroup | null>;

  /**
   * API used to return all channel groups on supported Android devices.
   *
   * This API is used to return a `NativeAndroidChannelGroup`. Returns an empty array if no channel
   * groups exist.
   *
   * A "native channel group" also includes additional properties about the channel group at the time it's
   * retrieved from the device. View the [`NativeAndroidChannelGroup`](/react-native/reference/nativeandroidchannelgroup)
   * documentation for more information.
   *
   * View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.
   *
   * @platform android
   */
  getChannelGroups(): Promise<NativeAndroidChannelGroup[]>;

  /**
   * API used to fetch the notification which causes the application to open.
   *
   * This API can be used to fetch which notification & press action has caused the application to
   * open. The call returns a `null` value when the application wasn't launched by a notification.
   *
   * Once the initial notification has been consumed by this API, it is removed and will no longer
   * be available. It will also be removed if the user relaunches the application.
   *
   * View the [App open events](/react-native/docs/events#app-open-events) documentation for more
   * information and example usage.
   */
  getInitialNotification(): Promise<InitialNotification | null>;

  onBackgroundEvent(observer: (event: NotificationEvent) => Promise<void>): void;

  onForegroundEvent(observer: (event: NotificationEvent) => void): () => void;

  /**
   * API used to open the Android System settings for the application.
   *
   * If the API version is >= 26:
   * - With no `channelId`, the notification settings screen is displayed.
   * - With a `channelId`, the notification settings screen for the specific channel is displayed.
   *
   * If the API version is < 26, the application settings screen is displayed. The `channelId`
   * is ignored.
   *
   * If an invalid `channelId` is provided (e.g. does not exist), the settings screen will redirect
   * back to your application.
   *
   * On iOS, this is a no-op & instantly resolves.
   *
   * @platform android
   * @param channelId The ID of the channel which will be opened. Can be ignored/omitted to display the
   * overall notification settings.
   */
  openNotificationSettings(channelId?: string): Promise<void>;

  /**
   * API used to register a foreground service on Android devices.
   *
   * This method is used to register a long running task which can be used with Foreground Service
   * notifications.
   *
   * Only a single foreground service can exist for the application, and calling this method more
   * than once will update the existing task runner.
   *
   * View the [Foreground Service](/react-native/docs/android/foreground-service) documentation for
   * more information.
   *
   * @platform android
   * @param runner The runner function which runs for the duration of the service's lifetime.
   */
  registerForegroundService(runner: (notification: Notification) => Promise<void>): void;

  // TODO introduce as part of iOS
  // scheduleNotification(notification: Notification, schedule: Schedule): Promise<void>;
}

/**
 * An interface describing the Android specific configuration properties for the `notifee.config.json` file.
 */
export interface JsonConfigAndroid {
  license: string;
}

/**
 * An interface describing the iOS specific configuration properties for the `notifee.config.json` file.
 */
export interface JsonConfigIOS {
  license: string;
}

/**
 * An interface describing the contents of a `notifee.config.json` file.
 */
export interface JsonConfig {
  android?: JsonConfigAndroid;
  ios?: JsonConfigIOS;
}

export interface ModuleStatics {
  SDK_VERSION: string;
}

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export interface ModuleWithStatics extends Module, ModuleStatics {}
