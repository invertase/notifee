/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NotificationIOS } from './NotificationIOS';
import {
  AndroidPressAction,
  NativeAndroidChannel,
  NativeAndroidChannelGroup,
  NotificationAndroid,
} from './NotificationAndroid';

/**
 * Interface for building a local notification for both Android & iOS devices.
 *
 * #### Example
 *
 * ```js
 * const notification = {
 *   body: 'Hello World!',
 *   title: 'Welcome',
 *   data: {
 *     user: '123',
 *   },
 *   android: {
 *     color: '#3f51b5',
 *   },
 *   ios: {
 *     alertAction: 'Open App',
 *   },
 * };
 *
 * await notifee.displayNotification(notification);
 * ```
 */
export interface Notification {
  /**
   * The main body content of a notification.
   *
   * #### Example
   *
   * ![Body Text](https://prismic-io.s3.amazonaws.com/invertase%2F7f9cc068-c618-44f0-88da-6041c6d55f48_new+project+%2817%29.jpg)
   *
   * ```js
   * const notification = {
   *   body: 'Hello World!',
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  body?: string;

  /**
   * A unique identifier for your notification.
   *
   * Defaults to a random string.
   */
  id?: string;

  /**
   * The notification title which appears above the body text.
   *
   * On Android, if you wish to read the title when the notification is opened, add it to the `data` object.
   *
   * #### Example
   *
   * ![Title Text](https://prismic-io.s3.amazonaws.com/invertase%2F6fa1dea9-8cf6-4e9a-b318-8d8f73d568cf_new+project+%2818%29.jpg)
   *
   * ```js
   * const notification = {
   *   title: 'Welcome!',
   *   body: 'Hello World!',
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  title?: string;

  /**
   * The notification subtitle, which appears on a new line below the title.
   *
   * #### Example
   *
   * ![Title Text](https://prismic-io.s3.amazonaws.com/invertase%2F6fa1dea9-8cf6-4e9a-b318-8d8f73d568cf_new+project+%2818%29.jpg)
   *
   * ```js
   * const notification = {
   *   title: 'Welcome!',
   *   subtitle: 'Learn more...',
   *   body: 'Hello World!',
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  subtitle?: string;

  /**
   * Additional data to store on the notification. Only `string` values can be stored.
   *
   * #### Example
   *
   * ```js
   * const notification = {
   *   body: 'Hello World!',
   *   data: {
   *     user: '123',
   *   }
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  data?: { [key: string]: string };

  /**
   * Android specific notification options. See the `NotificationAndroid` interface for more
   * information and default options which are applied to a notification.
   *
   * #### Example
   *
   * ```js
   * const notification = {
   *   body: 'Hello World!',
   *   android: {
   *     color: '#3f51b5',
   *   },
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   *
   * @platform android
   */
  android?: NotificationAndroid;

  /**
   * iOS specific notification options. See the `NotificationIOS` interface for more information
   * and default options which are applied to a notification.
   *
   * #### Example
   *
   * ```js
   * const notification = {
   *   body: 'Hello World!',
   *   ios: {
   *     alertAction: 'Open App',
   *   },
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   *
   * @platform ios
   */
  ios?: NotificationIOS;
}

export interface InitialNotification {
  notification: Notification;
  // todo ios press action?
  pressAction: AndroidPressAction;
}

export interface NotificationEvent {
  type: EventType;
  headless: boolean;
  detail:
    | AndroidNotificationEvent
    | AndroidChannelBlockedEvent
    | AndroidChannelGroupBlockedEvent
    | AndroidAppBlockedEvent;
}

export type NotificationEventObserver = (event: NotificationEvent) => Promise<void>;

export type ForegroundServiceTask = (notification: Notification) => Promise<void>;

/**
 * An enum representing an event type for `onNotificationEvent` subscriptions.
 */
export enum EventType {
  /**
   * An unknown event was received.
   *
   * This event type is a failsafe to catch any unknown events from the device. Please
   * report an issue with a reproduction so it can be correctly handled.
   */
  UNKNOWN = -1,

  /**
   * Event type is sent when the user dismisses a notification. This is triggered via the user swiping
   * the notification from the notification shade or performing "Clear all" notifications.
   *
   * This event is **not** sent when a notification is cancelled or times out.
   *
   * The payload sent with this event is [AndroidNotificationEvent](/react-native/reference/androidnotificationevent).
   */
  DISMISSED = 0,

  /**
   * Event type is sent when a notification has been pressed by the user.
   *
   * On Android, notifications must include an `android.pressAction` property for this event to trigger.
   *
   * The payload sent with this event is [AndroidNotificationEvent](/react-native/reference/androidnotificationevent).
   */
  PRESS = 1,

  /**
   * Event type is sent when a user presses a notification action.
   *
   * The event sent with this type is [AndroidNotificationEvent](/react-native/reference/androidnotificationevent).
   */
  ACTION_PRESS = 2,

  /**
   * Event type sent when a notification has been delivered to the device. For scheduled notifications,
   * this event is sent when at the point when the schedule runs, not when a notification is first
   * created.
   *
   * It's important to note even though a notification has been delivered, it may not be shown to the
   * user. For example, they may have notifications disabled on the device/channel/app.
   *
   * The event payload sent with this event is [AndroidNotificationEvent](/react-native/reference/androidnotificationevent).
   */
  DELIVERED = 3,

  /**
   * Event is sent when the user changes the notification blocked state for the entire application or
   * when the user opens the application settings.
   *
   * The payload sent with this event is [AndroidAppBlockedEvent](/react-native/reference/androidappblockedevent).
   *
   * @platform android API Level >= 28
   */
  APP_BLOCKED = 4,

  /**
   * Event type is sent when the user changes the notification blocked state for a channel in the application.
   *
   * The payload sent with this event is [AndroidChannelBlockedEvent](/react-native/reference/androidappblockedevent).
   *
   * @platform android API Level >= 28
   */
  CHANNEL_BLOCKED = 5,

  /**
   * Event type is sent when the user changes the notification blocked state for a channel group in the application.
   *
   * The payload sent with this event is [AndroidChannelGroupBlockedEvent](/react-native/reference/androidchannelgroupblockedevent).
   *
   * @platform android API Level >= 28
   */
  CHANNEL_GROUP_BLOCKED = 6,

  /**
   *
   */
  SCHEDULED = 7,
}

// TODO
// export enum ScheduleUnit {
//   DAY,
//   HOUR,
//   MINUTE,
// }
//
// export interface Schedule {
//   timestamp?: number;
//   interval?: number;
//   unit?: ScheduleUnit;
// }

export interface AndroidNotificationEvent {
  notification: Notification;

  /**
   * The press action which triggered the event.
   *
   * If a press action caused the event, this property will be available allowing you to retrieve the
   * action ID and perform logic.
   */
  pressAction?: AndroidPressAction;

  /**
   * The input from a notification action.
   *
   * Once an input has been received, the notification should be updated to remove the pending state
   * of the notification action, by adding the input value to the `inputHistory` property.
   *
   * @platform android API Level >= 20
   */
  input?: string;
}

export interface AndroidChannelBlockedEvent {
  /**
   * The channel that had its block state changed.
   *
   * Note that if the channel no longer exists during the time the event
   * was sent the channel property will be undefined.
   */
  channel?: NativeAndroidChannel;
}

export interface AndroidChannelGroupBlockedEvent {
  /**
   * The channel group that had its block state changed.
   *
   * Note that if the channel group no longer exists during the time the event
   * was sent the channelGroup property will be undefined.
   */
  channelGroup?: NativeAndroidChannelGroup;
}

export interface AndroidAppBlockedEvent {
  /**
   * The blocked status of your entire application.
   */
  blocked: boolean;
}
