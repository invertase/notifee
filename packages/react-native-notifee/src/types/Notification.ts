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
 * To learn more about displaying a notification, view the [Displaying a Notification](/react-native/docs/displaying-a-notification)
 * documentation.
 *
 */
export interface Notification {
  /**
   * The main body content of a notification.
   */
  body?: string;

  /**
   * A unique identifier for your notification.
   *
   * Notifications with the same ID will be created as the same instance, allowing you to update
   * a notification which already exists on the device.
   *
   * Defaults to a random string if not provided.
   */
  id?: string;

  /**
   * The notification title which appears above the body text.
   */
  title?: string;

  /**
   * The notification subtitle, which appears on a new line below/next the title.
   */
  subtitle?: string;

  /**
   * Additional data to store on the notification. Only `string` values can be stored.
   *
   * Data can be used to provide additional context to your notification which can be retrieved
   * at a later point in time (e.g. via an event).
   */
  data?: { [key: string]: string };

  /**
   * Android specific notification options. See the [`NotificationAndroid`](/react-native/reference/notificationandroid)
   * interface for more information and default options which are applied to a notification.
   *
   * @platform android
   */
  android?: NotificationAndroid;

  /**
   * iOS specific notification options. See the [`NotificationIOS`](/react-native/reference/notificationios)
   * interface for more information and default options which are applied to a notification.
   *
   * @platform ios
   */
  ios?: NotificationIOS;
}

/**
 * An interface representing a notification & action that launched the current app / activity.
 *
 * View the [App open events](/react-native/docs/events#app-open-events) documentation to learn more.
 *
 * This interface is returned from [`getInitialNotification`](/react-native/reference/getinitialnotification) when
 * an initial notification is available.
 */
export interface InitialNotification {
  /**
   * The notification which the user interacted with, which caused the application to open.
   */
  notification: Notification;

  /**
   * The press action which the user interacted with, on the notification, which caused the application to open.
   */
  pressAction: AndroidPressAction;
}

/**
 * An interface representing a Notifee event.
 *
 * View the [Events](/react-native/docs/events) documentation to learn more about foreground and
 * background events.
 */
export interface Event {
  /**
   * The type of notification event.
   */
  type: EventType;

  /**
   * An object containing event detail.
   */
  detail: EventDetail;
}

/**
 * A representation of a Foreground Service task registered via [`registerForegroundService`](/react-native/reference/registerforegroundservice).
 *
 * The task must resolve a promise once complete, and in turn removes the notification.
 *
 * View the [Foreground Service](/react-native/docs/android/foreground-service) documentation to
 * learn more.
 */
export type ForegroundServiceTask = (notification: Notification) => Promise<void>;

/**
 * An enum representing an event type, defined on [`Event`](/react-native/reference/event).
 *
 * View the [Events](/react-native/docs/events) documentation to learn more about foreground and
 * background events.
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
   */
  DISMISSED = 0,

  /**
   * Event type is sent when a notification has been pressed by the user.
   *
   * On Android, notifications must include an `android.pressAction` property for this event to trigger.
   */
  PRESS = 1,

  /**
   * Event type is sent when a user presses a notification action.
   */
  ACTION_PRESS = 2,

  /**
   * Event type sent when a notification has been delivered to the device. For scheduled notifications,
   * this event is sent at the point when the schedule executes, not when a the schedule is created.
   *
   * It's important to note even though a notification has been delivered, it may not be shown to the
   * user. For example, they may have notifications disabled on the device/channel/app.
   */
  DELIVERED = 3,

  /**
   * Event is sent when the user changes the notification blocked state for the entire application or
   * when the user opens the application settings.
   *
   * @platform android API Level >= 28
   */
  APP_BLOCKED = 4,

  /**
   * Event type is sent when the user changes the notification blocked state for a channel in the application.
   *
   * @platform android API Level >= 28
   */
  CHANNEL_BLOCKED = 5,

  /**
   * Event type is sent when the user changes the notification blocked state for a channel group in the application.
   *
   * @platform android API Level >= 28
   */
  CHANNEL_GROUP_BLOCKED = 6,

  /**
   * Event type is sent when a notification has been scheduled for displaying at a future date/time.
   */
  // SCHEDULED = 7,
}

/**
 * An interface representing the different detail values which can be provided with a notification event.
 *
 * View the [Events](/react-native/docs/events) documentation to learn more.
 */
export interface EventDetail {
  /**
   * The notification this event relates to.
   *
   * The notification details is available when the [`EventType`](/react-native/reference/eventtype) is one of:
   *
   *  - [`EventType.DISMISSED`](/react-native/reference/eventtype#dismissed)
   *  - [`EventType.PRESS`](/react-native/reference/eventtype#press)
   *  - [`EventType.ACTION_PRESS`](/react-native/reference/eventtype#action_press)
   *  - [`EventType.DELIVERED`](/react-native/reference/eventtype#delivered)
   */
  notification?: Notification;

  /**
   * The press action which triggered the event.
   *
   * If a press action caused the event, this property will be available allowing you to retrieve the
   * action ID and perform logic.
   *
   * The press action details is available when the [`EventType`](/react-native/reference/eventtype) is one of:
   *
   * - [`EventType.PRESS`](/react-native/reference/eventtype#press)
   * - [`EventType.ACTION_PRESS`](/react-native/reference/eventtype#action_press)
   */
  pressAction?: AndroidPressAction;

  /**
   * The input from a notification action.
   *
   * The input detail is available when the [`EventType`](/react-native/reference/eventtype) is:
   *
   * - [`EventType.ACTION_PRESS`](/react-native/reference/eventtype#action_press)
   * - The notification quick action has input enabled. View [`AndroidInput`](/react-native/reference/androidinput) for more details.
   *
   * @platform android API Level >= 20
   */
  input?: string;

  /**
   * The channel that had its block state changed.
   *
   * Note that if the channel no longer exists during the time the event was sent the channel property will be undefined.
   *
   * The channel detail is available when the event type is [`EventType.CHANNEL_BLOCKED`](/react-native/reference/eventtype#channel_blocked).
   *
   * @platform android API Level >= 28
   */
  channel?: NativeAndroidChannel;

  /**
   * The channel group that had its block state changed.
   *
   * Note that if the channel no longer exists during the time the event was sent the channel group property will be undefined.
   *
   * The channel group detail is available when the event type is [`EventType.CHANNEL_GROUP_BLOCKED`](/react-native/reference/eventtype#channel_group_blocked).
   *
   * @platform android API Level >= 28
   */
  channelGroup?: NativeAndroidChannelGroup;

  /**
   * The notification blocked status of your entire application.
   *
   * The blocked detail is available when the event type is [`EventType.APP_BLOCKED`](/react-native/reference/eventtype#app_blocked).
   *
   * @platform android API Level >= 28
   */
  blocked?: boolean;
}
