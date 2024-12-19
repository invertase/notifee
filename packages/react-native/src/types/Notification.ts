/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { IOSNotificationSettings, NotificationIOS } from './NotificationIOS';
import {
  NativeAndroidChannel,
  NativeAndroidChannelGroup,
  NotificationAndroid,
  AndroidLaunchActivityFlag,
} from './NotificationAndroid';
import { AndroidNotificationSettings, Trigger } from '..';
import { WebNotificationSettings } from './NotificationWeb';

/**
 * Interface for building a local notification for both Android & iOS devices.
 *
 * To learn more about displaying a notification, view the [Displaying a Notification](/react-native/displaying-a-notification)
 * documentation.
 *
 */
export interface Notification {
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
  title?: string | undefined;

  /**
   * The notification subtitle, which appears on a new line below/next the title.
   */
  subtitle?: string | undefined;

  /**
   * The main body content of a notification.
   */
  body?: string | undefined;

  /**
   * Additional data to store on the notification.
   *
   * Data can be used to provide additional context to your notification which can be retrieved
   * at a later point in time (e.g. via an event).
   */
  data?: { [key: string]: string | object | number };

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

  /**
   * Will be populated if it's a remote notification
   *
   * @platform ios
   */
  readonly remote?: {
    messageId: string;
    senderId: string;
    mutableContent?: number;
    contentAvailable?: number;
  };
}

/**
 * An interface representing a notification & action that launched the current app / or Android activity.
 *
 * View the [App open events](/react-native/events#app-open-events) documentation to learn more.
 *
 * This interface is returned from [`getInitialNotification`](/react-native/reference/getinitialnotification) when
 * an initial notification is available.
 *
 * Deprecated for iOS in favour of `onForegroundEvent`
 *
 * @platform android
 */
export interface InitialNotification {
  /**
   * The notification which the user interacted with, which caused the application to open.
   */
  notification: Notification;

  /**
   * The press action which the user interacted with, on the notification, which caused the application to open.
   */
  pressAction: NotificationPressAction;

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
}

/**
 * An interface representing a notification that is currently displayed in the notification tray.
 */
export interface DisplayedNotification {
  /**
   * ID of the notification
   */
  id?: string;

  /**
   * Date the notification was shown to the user
   */
  date?: string;

  /**
   * The payload that was used to create the notification (if available)
   */
  notification: Notification;

  /**
   * The trigger that was used to schedule the notification (if available)
   *
   * @platform iOS
   */
  trigger: Trigger;
}

/**
 * An interface representing a notification that is pending.
 */
export interface TriggerNotification {
  /**
   * The notification
   */
  notification: Notification;

  /**
   * The trigger that is used to schedule the notification
   */
  trigger: Trigger;
}

/**
 * An interface representing a Notifee event.
 *
 * View the [Events](/react-native/events) documentation to learn more about foreground and
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
 * View the [Foreground Service](/react-native/android/foreground-service) documentation to
 * learn more.
 *
 * @platform android
 */
export type ForegroundServiceTask = (notification: Notification) => Promise<void>;

/**
 * The interface used to describe a press action for a notification.
 *
 * There are various ways a user can interact with a notification, the most common being pressing
 * the notification, pressing an action or providing text input. This interface defines what happens
 * when a user performs such interaction.
 *
 * On Android; when provided to a notification action, the action will only open you application if
 * a `launchActivity` and/or a `mainComponent` is provided.
 */
export interface NotificationPressAction {
  /**
   * The unique ID for the action.
   *
   * The `id` property is used to differentiate between user press actions. When listening to notification
   * events, the ID can be read from the `event.detail.pressAction` object.
   */
  id: string;

  /**
   * The custom Android Activity to launch on a press action.
   *
   * This property can be used in advanced scenarios to launch a custom Android Activity when the user
   * performs a press action.
   *
   * View the [Android Interaction](/react-native/android/interaction) docs to learn more.
   *
   * @platform android
   */
  launchActivity?: string;

  /**
   * Custom flags that are added to the Android [Intent](https://developer.android.com/reference/android/content/Intent.html) that launches your Activity.
   *
   * These are only required if you need to customise the behaviour of how your activities are launched; by default these are not required.
   *
   * @platform android
   */
  launchActivityFlags?: AndroidLaunchActivityFlag[];

  /**
   * A custom registered React component to launch on press action.
   *
   * This property can be used to open a custom React component when the user performs a press action.
   * For this to correctly function on Android, a minor native code change is required.
   *
   * View the [Press Action](/react-native/android/interaction#press-action) document to learn more.
   *
   * @platform android
   */
  mainComponent?: string;
}

/**
 * The interface used to describe a full-screen action for a notification.
 *
 * By setting a `fullScreenAction`, when the notification is displayed, it will launch a full-screen intent.
 *
 * On Android; when provided to a notification action, the action will only open you application if
 * a `launchActivity` and/or a `mainComponent` is provided.
 *
 * Requires the following permission to be added to your `AndroidManifest.xml`:
 * ```xml
 * <uses-permission android:name="android.permission.USE_FULL_SCREEN_INTENT" />
 * ```
 *
 * Please see the [FullScreen Action](/react-native/android/behaviour#full-screen) document to learn more.
 */
export interface NotificationFullScreenAction {
  /**
   * The unique ID for the action.
   *
   * The `id` property is used to differentiate between full-screen actions. When listening to notification
   * events, the ID can be read from the `event.detail.notification.android.fullScreenAction` object.
   */
  id: string;

  /**
   * The custom Android Activity to launch on a full-screen action.
   *
   * This property can be used in advanced scenarios to launch a custom Android Activity when the user
   * performs a full-screen action.
   *
   * View the [Android Full Screen](/react-native/android/behaviour#full-screen) docs to learn more.
   *
   * @platform android
   */
  launchActivity?: string;

  /**
   * Custom flags that are added to the Android [Intent](https://developer.android.com/reference/android/content/Intent.html) that launches your Activity.
   *
   * These are only required if you need to customise the behaviour of how your activities are launched; by default these are not required.
   *
   * @platform android
   */
  launchActivityFlags?: AndroidLaunchActivityFlag[];

  /**
   * A custom registered React component to launch on press action.
   *
   * This property can be used to open a custom React component when the notification is displayed.
   * For this to correctly function on Android, a minor native code change is required.
   *
   * View the [Full-screen Action](/react-native/android/behaviour#full-screen) document to learn more.
   *
   * @platform android
   */
  mainComponent?: string;
}

/**
 * An enum representing an event type, defined on [`Event`](/react-native/reference/event).
 *
 * View the [Events](/react-native/events) documentation to learn more about foreground and
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
   * the notification from the notification shade.
   *
   * On Android, the event is also sent when performing "Clear all" notifications unlike on iOS.
   *
   * This event is **not** sent when a notification is cancelled or times out.
   */
  DISMISSED = 0,

  /**
   * Event type is sent when a notification has been pressed by the user.
   *
   * On Android, notifications must include an `android.pressAction` property for this event to trigger.
   *
   * On iOS, this event is always sent when the user presses a notification.
   */
  PRESS = 1,

  /**
   * Event type is sent when a user presses a notification action.
   */
  ACTION_PRESS = 2,

  /**
   * Event type sent when a notification has been delivered to the device. For trigger notifications,
   * this event is sent at the point when the trigger executes, not when a the trigger notification is created.
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
   * Event type is sent when a notification trigger is created.
   */
  TRIGGER_NOTIFICATION_CREATED = 7,

  /**
   * **ANDROID ONLY**
   *
   * Event type is sent when a notification wants to start a foreground service but a foreground service is already started.
   */
  FG_ALREADY_EXIST = 8,
}

/**
 * An interface representing the different detail values which can be provided with a notification event.
 *
 * View the [Events](/react-native/events) documentation to learn more.
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
   *  - [`EventType.TRIGGER_NOTIFICATION_CREATED`](/react-native/reference/eventtype#trigger_notification_created)
   *  - [`EventType.FG_ALREADY_EXIST`](/react-native/reference/eventtype#fg_already_exist)
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
  pressAction?: NotificationPressAction;

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

/**
 * An enum representing the notification authorization status for this app on the device.
 *
 * Value is greater than 0 if authorized, compare against an exact status (e.g. PROVISIONAL) for a more
 * granular status.
 *
 */
export enum AuthorizationStatus {
  /**
   * The app user has not yet chosen whether to allow the application to create notifications. Usually
   * this status is returned prior to the first call of `requestPermission`.
   *
   * @platform ios
   */
  NOT_DETERMINED = -1,

  /**
   * The app is not authorized to create notifications.
   */
  DENIED = 0,

  /**
   * The app is authorized to create notifications.
   */
  AUTHORIZED = 1,

  /**
   * The app is currently authorized to post non-interrupting user notifications
   * @platform ios iOS >= 12
   */
  PROVISIONAL = 2,
}

export interface NotificationSettings {
  /**
   * Overall notification authorization status for the application.
   * On Android, `authorizationStatus` will return only either `AuthorizationStatus.DENIED` or `AuthorizationStatus.AUTHORIZED`.
   */
  authorizationStatus: AuthorizationStatus;
  /**
   * Overall notification settings for the application in iOS.
   * On non-iOS platforms, this will be populated with default values
   */
  ios: IOSNotificationSettings;
  /**
   * Overall notification settings for the application in android.
   * On non-Android platforms, this will be populated with default values
   */
  android: AndroidNotificationSettings;
  /**
   * Overall notification settings for the application in web.
   * On non-Web platforms, this will be populated with default values
   */
  web: WebNotificationSettings;
}
