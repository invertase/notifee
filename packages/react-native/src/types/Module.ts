/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { Event, ForegroundServiceTask, InitialNotification, Notification } from './Notification';
import { Trigger } from './Trigger';
import {
  AndroidChannel,
  AndroidChannelGroup,
  NativeAndroidChannel,
  NativeAndroidChannelGroup,
} from './NotificationAndroid';
import { IOSNotificationCategory, IOSNotificationPermissions } from './NotificationIOS';
import { PowerManagerInfo } from './PowerManagerInfo';
import { DisplayedNotification, NotificationSettings, TriggerNotification } from '..';

export interface Module {
  /**
   * API used to cancel all notifications.
   *
   * The `cancelAllNotifications` API removes any displayed notifications from the users device and
   * any pending trigger notifications.
   *
   * This method does not cancel Android [Foreground Service](/react-native/android/foreground-service)
   * notifications.
   * @param notificationIds An array of notifications IDs. This is automatically generated and returned
   * when creating a notification, or has been set manually via the `id` property.
   *
   * @param tag The tag set when creating the notification. This is only relative to Android.
   */
  cancelAllNotifications(notificationIds?: string[], tag?: string): Promise<void>;

  /**
   * API used to cancel any displayed notifications.
   *
   * This method does not cancel Android [Foreground Service](/react-native/android/foreground-service)
   * notifications.
   */
  cancelDisplayedNotifications(notificationIds?: string[]): Promise<void>;

  /**
   * API used to cancel any trigger notifications.
   */
  cancelTriggerNotifications(notificationIds?: string[]): Promise<void>;

  /**
   * API used to cancel a single notification.
   *
   * The `cancelNotification` API removes any displayed notifications or ones with triggers
   * set for the specified ID.
   *
   * This method does not cancel [Foreground Service](/react-native/android/foreground-service)
   * notifications.
   *
   * @param notificationId The unique notification ID. This is automatically generated and returned
   * when creating a notification, or has been set manually via the `id` property.
   *
   * @param tag The tag set when creating the notification. This is only relative to Android.
   */
  cancelNotification(notificationId: string, tag?: string): Promise<void>;

  /**
   * API used to cancel a single displayed notification.
   *
   *
   * This method does not cancel [Foreground Service](/react-native/android/foreground-service)
   * notifications.
   *
   * @param notificationId The unique notification ID. This is automatically generated and returned
   * when creating a notification, or has been set manually via the `id` property.
   *
   * @param tag The tag set when creating the notification. This is only relative to Android.
   */
  cancelDisplayedNotification(notificationId: string, tag?: string): Promise<void>;

  /**
   * API used to cancel a single trigger notification.
   *
   *
   * @param notificationId The unique notification ID. This is automatically generated and returned
   * when creating a notification, or has been set manually via the `id` property.
   */
  cancelTriggerNotification(notificationId: string): Promise<void>;

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
   * View the [Channels & Groups](/react-native/android/channels) documentation for more information.
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
   * View the [Channels & Groups](/react-native/android/channels) documentation for more information.
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
   * View the [Channels & Groups](/react-native/android/channels) documentation for more information.
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
   * View the [Channels & Groups](/react-native/android/channels) documentation for more information.
   *
   * @platform android
   * @param channelGroupId The unique channel group ID to delete.
   */
  deleteChannelGroup(channelGroupId: string): Promise<void>;

  /**
   * API used to immediately display or update a notification on the users device.
   *
   * This API is used to display a notification on the users device. All
   * channels/categories should be created before triggering this method during the apps lifecycle.
   *
   * View the [Displaying a Notification](/react-native/displaying-a-notification)
   * documentation for more information.
   *
   * @param notification The [`Notification`](/react-native/reference/notification) interface used
   * to create a notification for both Android & iOS.
   */
  displayNotification(notification: Notification): Promise<string>;

  /**
   * API used to open the Android Alarm special access settings for the application.
   *
   * On Android >= 12 / API >= 31, the alarm special access settings screen is displayed, otherwise,
   * this is a no-op & instantly resolves.
   *
   * View the [Trigger](/react-native/android/triggers#android-12-limitations) documentation for more information.
   *
   * @platform android
   */
  openAlarmPermissionSettings(): Promise<void>;

  /**
   * API used to create a trigger notification.
   *
   * All channels/categories should be created before calling this method during the apps lifecycle.
   *
   * View the [Triggers](/react-native/triggers) documentation for more information.
   *
   * @param notification The [`Notification`](/react-native/reference/notification) interface used
   * to create a notification.
   *
   * @param trigger The [`Trigger`](/react-native/reference/trigger) interface used
   * to create a trigger.
   */
  createTriggerNotification(notification: Notification, trigger: Trigger): Promise<string>;

  /**
   * API used to return the ids of trigger notifications that are pending.
   *
   * View the [Triggers](/react-native/triggers) documentation for more information.
   */
  getTriggerNotificationIds(): Promise<string[]>;

  /**
   * API used to return the notifications that are displayed.
   */
  getDisplayedNotifications(): Promise<DisplayedNotification[]>;

  /**
   * API used to return the trigger notifications that are pending.
   */
  getTriggerNotifications(): Promise<TriggerNotification[]>;

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
   * View the [Channels & Groups](/react-native/android/channels) documentation for more information.
   *
   * @platform android
   * @param channelId The channel ID created with [`createChannel`](/react-native/reference/createchannel). If
   * a unknown channel ID is provided, `null` is returned.
   */
  getChannel(channelId: string): Promise<NativeAndroidChannel | null>;

  /**
   * API used to check if a channel is created.
   *
   * On iOS, this will default to true
   *
   * @platform android
   */
  isChannelCreated(channelId: string): Promise<boolean>;

  /**
   * API used to check if a channel is blocked.
   *
   * On iOS, this will default to false
   *
   * @platform android
   */
  isChannelBlocked(channelId: string): Promise<boolean>;

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
   * View the [Channels & Groups](/react-native/android/channels) documentation for more information.
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
   * View the [Channels & Groups](/react-native/android/channels) documentation for more information.
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
   * View the [Channels & Groups](/react-native/android/channels) documentation for more information.
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
   * View the [App open events](/react-native/events#app-open-events) documentation for more
   * information and example usage.
   *
   * Deprecated for iOS in favour of `onForegroundEvent` - you can still use this method on iOS
   * but you will also receive a `onForegroundEvent`
   */
  getInitialNotification(): Promise<InitialNotification | null>;

  /**
   * API used to handle events when the application is in a background state.
   *
   * Applications in a background state will use an event handler registered by this API method
   * to send events. The handler must return a Promise once complete and only a single event handler
   * can be registered for the application.
   *
   * View the [Background events](/react-native/events#background-events) documentation for more
   * information and example usage.
   *
   * To listen to foreground events, see the [`onForegroundEvent`](/react-native/reference/onforegroundevent) documentation.
   *
   * @param observer A Function which returns a Promise, called on a new event when the application
   * is in a background state.
   */
  onBackgroundEvent(observer: (event: Event) => Promise<void>): void;

  /**
   * API used to handle events when the application is in a foreground state.
   *
   * Applications in a foreground state will use an event handler registered by this API method
   * to send events. Multiple foreground observers can be registered throughout the applications
   * lifecycle. The method returns a function, used to unsubscribe from further events,
   *
   * View the [Foreground events](/react-native/events#foreground-events) documentation for more
   * information and example usage.
   *
   * To listen to background events, see the [`onBackgroundEvent`](/react-native/reference/onbackgroundevent) documentation.
   *
   * @param observer A Function which returns a Promise, called on a new event when the application
   * is in a foreground state.
   */
  onForegroundEvent(observer: (event: Event) => void): () => void;

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
   * View the [Foreground Service](/react-native/android/foreground-service) documentation for
   * more information.
   *
   * @platform android
   * @param task The runner function which runs for the duration of the service's lifetime.
   */
  registerForegroundService(task: ForegroundServiceTask): void;

  /**
   * Call this to stop the foreground service that is running
   *
   * @platform android
   *
   */
  stopForegroundService(): Promise<void>;

  /**
   * Request specific notification permissions for your application on the current device.
   *
   * Both iOS & Android return an `NotificationSettings` interface. To check whether overall
   * permission was granted, check the `authorizationStatus` property in the response:
   *
   * ```js
   * import notifee, { AuthorizationStatus } from '@notifee/react-native';
   *
   * const settings = await notifee.requestPermission(...);
   *
   * if (settings.authorizationStatus === AuthorizationStatus.DENIED) {
   *   console.log('User denied permissions request');
   * } else if (settings.authorizationStatus === AuthorizationStatus.AUTHORIZED) {
   *    console.log('User granted permissions request');
   * } else if (settings.authorizationStatus === AuthorizationStatus.PROVISIONAL) {
   *    console.log('User provisionally granted permissions request');
   * }
   * ```
   *
   * For iOS specific settings, use the `ios` properties to view which specific permissions were
   * authorized.
   *
   * On Android, `authorizationStatus` will return only either `AuthorizationStatus.DENIED` or `AuthorizationStatus.AUTHORIZED`
   * and all of the properties on the `ios` interface response return as `AUTHORIZED`.
   *
   * @param permissions
   */
  requestPermission(permissions?: IOSNotificationPermissions): Promise<NotificationSettings>;

  /**
   * Set the notification categories to be used on this Apple device.
   *
   * @platform ios
   *
   * @param categories
   */
  setNotificationCategories(categories: IOSNotificationCategory[]): Promise<void>;

  /**
   * Gets the currently set notification categories on this Apple device.
   *
   * Returns an empty array on Android.
   *
   *@platform ios
   */
  getNotificationCategories(): Promise<IOSNotificationCategory[]>;

  /**
   * Get the current notification settings for this application on the current device.
   * On Android, `authorizationStatus` will return only either `AuthorizationStatus.DENIED` or `AuthorizationStatus.AUTHORIZED`
   * and all of the properties on the `IOSNotificationSettings` interface response return as `AUTHORIZED`.
   */
  getNotificationSettings(): Promise<NotificationSettings>;

  /**
   * Get the current badge count value for this application on the current device.
   *
   * Returns `0` on Android.
   *
   * @platform ios
   */
  getBadgeCount(): Promise<number>;

  /**
   * Set the badge count value for this application on the current device.
   *
   * If set to zero, the badge count is removed from the device. The count must also
   * be a positive number.
   *
   * @platform ios
   *
   * @param count The number value to set as the badge count.
   */
  setBadgeCount(count: number): Promise<void>;

  /**
   * Increments the badge count for this application on the current device by a specified
   * value.
   *
   * Defaults to an increment of `1`.
   *
   * @platform ios
   *
   * @param incrementBy The value to increment the badge count by.
   */
  incrementBadgeCount(incrementBy?: number): Promise<void>;

  /**
   * Decrements the badge count for this application on the current device by a specified
   * value.
   *
   * Defaults to an decrement of `1`.
   *
   * @platform ios
   */
  decrementBadgeCount(decrementBy?: number): Promise<void>;

  /**
   * API used to open the Android System settings for the application.
   *
   * If the API version is >= 23, the battery optimization settings screen is displayed, otherwise,
   * this is a no-op & instantly resolves.
   *
   * View the [Background Restrictions](/react-native/android/behaviour#background-restrictions) documentation for more information.
   *
   * @platform android
   */
  openBatteryOptimizationSettings(): Promise<void>;

  /**
   * API used to check if battery optimization is enabled for your application.
   *
   * Supports API versions >= 23.
   *
   * View the [Background Restrictions](/react-native/android/behaviour#background-restrictions) documentation for more information.
   *
   * @platform android
   */
  isBatteryOptimizationEnabled(): Promise<boolean>;

  /**
   * API used to get information about the device and its power manager settings, including manufacturer, model, version and activity.
   *
   * If `activity` is `null`, `openPowerManagerSettings()` will be noop.
   *
   * On iOS, an instance of `PowerManagerInfo` will be returned with `activity` set to `null`.
   *
   * View the [Background Restrictions](/react-native/android/background-restrictions) documentation for more information.
   *
   * ```js
   * import notifee from `@notifee/react-native`;
   *
   * const powerManagerInfo = await notifee.getPowerManagerInfo();
   *
   * if (powerManagerInfo.activity) {
   *  // 1. ask the user to adjust their Power Manager settings
   *  // ...
   *
   *  // 2. open settings
   *  await notifee.openPowerManagerSettings();
   * }
   * ```
   *
   * @platform android
   */
  getPowerManagerInfo(): Promise<PowerManagerInfo>;

  /**
   * API used to navigate to the appropriate Android System settings for the device.
   *
   * Call `getPowerManagerInfo()` first to find out if the user's device is supported.
   *
   * View the [Background Restrictions](/react-native/android/background-restrictions) documentation for more information.
   *
   * ```js
   * import notifee from `@notifee/react-native`;
   *
   * const powerManagerInfo = await notifee.getPowerManagerInfo
   *
   * if (powerManagerInfo.activity) {
   * // 1. ask the user to adjust their Power Manager settings
   * // ...
   *
   * // 2. if yes, navigate them to settings
   * await notifee.openPowerManagerSettings();
   * }
   * ```
   *
   * @platform android
   */
  openPowerManagerSettings(): Promise<void>;

  /**
   * API used to hide the notification drawer, for example,
   * when the user presses one of the quick actions on the notification, you may wish to hide the drawer.
   *
   * Please use this functionality carefully as it could potentially be quite intrusive to the user.
   *
   * Requires the following permission to be added to your `AndroidManifest.xml`:
   * ```xml
   * <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />
   * ```
   *
   * ```js
   * import notifee from `@notifee/react-native`;
   *
   * notifee.hideNotificationDrawer();
   * ```
   *
   * @platform android
   */
  hideNotificationDrawer(): void;
}

/**
 * Interface describing the static properties available on the default `@notifee/react-native` export.
 *
 * ```js
 * import notifee from `@notifee/react-native`;
 *
 * console.log(notifee.SDK_VERSION);
 * ```
 */
export interface ModuleStatics {
  /**
   * Returns the current Notifee SDK version in use.
   */
  SDK_VERSION: string;
}

export interface ModuleWithStatics extends Module, ModuleStatics {}
