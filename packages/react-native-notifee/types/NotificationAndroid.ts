/*
 * Copyright (c) 2016-present Invertase Limited
 */

/**
 * The interface for Android specific options which are applied to a notification.
 *
 * #### Example
 *
 * ```js
 * const notification = {
 *   body: 'Hello World!',
 *   android: {
 *     color: '#3F51B5',
 *     autoCancel: false,
 *     ongoing: true,
 *   },
 * };
 *
 * await notifee.displayNotification(notification);
 * ```
 *
 * @platform android
 */
export interface NotificationAndroid {
  /**
   *
   */
  actions?: AndroidAction[];

  /**
   * When set to `true` this notification will be shown as a foreground service.
   *
   * The application can only display one foreground service notification at once. If a
   * foreground service notification is already running and a new notification with this flag set to
   * `true` is provided, the service will stop the existing service and start a new one.
   *
   * Ensure a foreground service runner function has been provided to `registerForegroundService`.
   * Without one, the notification will not be displayed.
   *
   * Defaults to `false`.
   */
  asForegroundService?: boolean;

  /**
   * Setting this flag will make it so the notification is automatically canceled when the user
   * clicks it in the panel.
   *
   * By default when the user taps a notification it is automatically removed from the notification
   * panel. Setting this to `false` will keep the notification in the panel.
   *
   * If `false`, the notification will persist in the notification panel after being pressed. It will
   * remain there until the user removes it (e.g. swipes away) or is cancelled via `removeDeliveredNotification`.
   *
   * Defaults to `true`.
   */
  autoCancel?: boolean;

  /**
   * Starting with 8.0 (API level 26), notification badges (also known as notification dots) appear
   * on a launcher icon when the associated app has an active notification. Users can long-press
   * on the app icon to reveal the notifications (alongside any app shortcuts).
   *
   * This value might be ignored, for launchers that don't support badge icons.
   *
   * If the notification is shown as a badge, this option can be set to control how the badge icon
   * is shown:
   *
   * - `NONE`: Uses the default preference of the device launcher. Some launchers will display no icon, others will use the `largeIcon` (if provided).
   * - `SMALL`: Uses the icon provided to `smallIcon`, if available.
   * - `LARGE`: Uses the icon provided to `largeIcon`, if available.
   *
   * Defaults to `AndroidBadgeIconType.LARGE`.
   *
   * @platform android API Level >= 26
   */
  badgeIconType?: AndroidBadgeIconType;

  /**
   * Assigns the notification to a category. Use the one which best describes the notification.
   *
   * The category may be used by the device for ranking and filtering.
   *
   * ```js
   * import notifee, { AndroidCategory } from `@notifee/react-native`;
   *
   * const notification = {
   *   body: 'Congratulations...',
   *   android: {
   *     category: AndroidCategory.MESSAGE,
   *   },
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  category?: AndroidCategory;

  /**
   * Specify the `AndroidChannel` which the notification will be delivered on.
   *
   * Channels override any notification options.
   *
   * > On Android 8.0 (API 26) the channel ID is required. Providing a invalid channel ID will throw an error.
   *
   * #### Example
   *
   * ```js
   * import notifee from `@notifee/react-native`;
   *
   * const channelId = notifee.createChannel({
   *   channelId: 'my-custom-channel',
   *   name: 'Custom Notification Channel',
   * });
   *
   * await notifee.displayNotification({
   *   body: 'Notification with channel',
   *   android: {
   *     channelId,
   *   },
   * });
   * ```
   */
  channelId?: string;

  /**
   * Set an custom accent color for the notification. If not provided, the default notification
   * system color will be used.
   *
   * The color can be a predefined system `AndroidColor` or [hexadecimal](https://gist.github.com/lopspower/03fb1cc0ac9f32ef38f4).
   *
   * Setting a color will change key parts of a notification, such as the small icon, action text and
   * the input area background color.
   *
   * See our [Android Appearance guide](/react-native/docs/android/appearance#color) to learn
   * more about this property.
   *
   * #### Example
   *
   * Using a predefined color.
   *
   * ```js
   * import notifee, { AndroidColor } from '@notifee/react-native';
   *
   * await notifee.displayNotification({
   *   android: {
   *     color: AndroidColor.AQUA,
   *     // or
   *     color: '#2196f3', // material blue
   *   },
   * });
   * ```
   */
  color?: AndroidColor | string;

  /**
   * When `asForegroundService` is `true`, the notification will use the provided `color` property
   * to set a background color on the notification. This property has no effect when `asForegroundService`
   * is `false`.
   *
   * This should only be used for high priority ongoing tasks like navigation, an ongoing call,
   * or other similarly high-priority events for the user.
   *
   * Defaults to `false`.
   */
  colorized?: boolean;

  /**
   * If `showChronometer` is `true`, the direction of the chronometer can be changed to count down instead of up.
   *
   * Has no effect if `showChronometer` is `false`.
   *
   * Defaults to `up`.
   */
  chronometerDirection?: 'up' | 'down';

  /**
   * For devices without notification channel support, this property sets the default behaviour
   * for a notification.
   *
   * On API Level >= 26, this has no effect.
   *
   * See [AndroidDefaults](/react-native/reference/androiddefaults) for more information.
   *
   * @platform android API Level < 26
   */
  defaults?: AndroidDefaults[];

  /**
   * Set this notification to be part of a group of notifications sharing the same key. Grouped notifications may
   * display in a cluster or stack on devices which support such rendering.
   *
   * See our [Android Grouping & Sorting guide](/react-native/docs/android/grouping-and-sorting) to
   * learn more about this property.
   */
  groupId?: string;

  /**
   * Sets the group alert behavior for this notification. Use this method to mute this notification
   * if alerts for this notification's group should be handled by a different notification. This is
   * only applicable for notifications that belong to a `group`. This must be called on all notifications
   * you want to mute. For example, if you want only the summary of your group to make noise, all
   * children in the group should have the group alert behavior `AndroidGroupAlertBehavior.SUMMARY`.
   *
   * See our [Android Grouping & Sorting guide](/react-native/docs/android/grouping-and-sorting#group-behaviour) to
   * learn more about this property.
   */
  groupAlertBehavior?: AndroidGroupAlertBehavior;

  /**
   * Whether this notification should be a group summary.
   *
   * If `true`, Set this notification to be the group summary for a group of notifications. Grouped notifications may display in
   * a cluster or stack on devices which support such rendering. Requires a `group` key to be set.
   *
   * Defaults to `false`.
   */
  groupSummary?: boolean;

  /**
   * The local user input history for this notification.
   *
   * Input history is shown on supported devices below the main notification body. History of the
   * users input with the notification should be shown when receiving action input by updating
   * the existing notification. It is recommended to clear the history when it is no longer
   * relevant (e.g. someone has responded to the users input).
   */
  inputHistory?: string[];

  /**
   * Sets a large icon on the notification.
   *
   * See our [Android Appearance guide](/react-native/docs/android/appearance#large-icons) to learn
   * more about this property.
   */
  largeIcon?: string;

  /**
   * Sets the color and frequency of the light pattern. This only has effect on supported devices.
   *
   * The option takes an array containing a hexadecimal color value or predefined `AndroidColor`,
   * along with the number of milliseconds to show the light, and the number of milliseconds to
   * turn off the light. The light frequency pattern is repeated.
   *
   * #### Example
   *
   * Show a red light, for 300ms and turn it off for 600ms.
   *
   * ```js
   * await notifee.displayNotification({
   *   android: {
   *     lights: ['#f44336', 300, 600],
   *   },
   * });
   * ```
   */
  lights?: [AndroidColor | string, number, number];

  /**
   * Description - if true, wont show on connected devices
   *
   * Defaults to `false`.
   */
  localOnly?: boolean;

  /**
   * Overrides the current number of active notifications shown on the device.
   *
   * The number of active notifications is shown in various locations (such as the notification badge tray)
   * depending on your device and launcher type.
   *
   * If no number is provided, the system displays the current number of active notifications.
   */
  number?: number;

  /**
   * Set whether this is an on-going notification.
   *
   * - Ongoing notifications are sorted above the regular notifications in the notification panel.
   * - Ongoing notifications do not have an 'X' close button, and are not affected by the "Clear all" button.
   */
  ongoing?: boolean;

  /**
   * Notifications with the same `id` will only show a single instance at any one time on your device,
   * however will still alert the user (for example, by making a sound).
   *
   * If this flag is set to `true`, notifications with the same `id` will only alert the user once whilst
   * the notification is active.
   */
  onlyAlertOnce?: boolean;

  /**
   * By default notifications have no behaviour when a user presses them. The
   * `pressAction` property allows you to set what happens when a user presses
   * the notification.
   *
   * The notification will always open the application when an `pressAction` is provided. It is
   * however possible to provide advanced configuration to the press action to open custom
   * activities or React components.
   *
   * See our [Android Interaction guide](/react-native/docs/android/interaction#press-action) to learn
   * more about this property.
   *
   */
  pressAction?: AndroidPressAction;

  /**
   * Set a notification importance for devices without channel support.
   *
   * Devices using Android API Level < 26 have no channel support, meaning incoming notifications
   * won't be assigned an importance level from the channel. If your application supports devices
   * without channel support, set this property to directly assign an importance level to the incoming
   * notification.
   *
   * Defaults to `AndroidImportance.DEFAULT`.
   *
   * See our [Android Appearance guide](/react-native/docs/android/appearance#importance) to learn
   * more about this property.
   *
   * @platform android API Level < 26
   */
  importance?: AndroidImportance;

  /**
   * A notification can show current progress of a task. The progress state can either be fixed or
   * indeterminate (unknown).
   *
   * See our [Android Progress Indicators guide](/react-native/docs/android/progress-indicators) to
   * learn more about this property.
   */
  progress?: AndroidProgress;

  /**
   * If this notification is duplicative of a Launcher shortcut, sets the id of the shortcut,
   * in case the Launcher wants to hide the shortcut.
   *
   * Note: This field will be ignored by Launchers that don't support badging or shortcuts.
   */
  shortcutId?: string;

  /**
   * Sets whether the `timestamp` provided is shown in the notification.
   *
   * Setting this field is useful for notifications which are more informative with a timestamp,
   * such as an E-Mail.
   *
   * If no `timestamp` is set, this field has no effect.
   *
   * See our [Android Timestamps guide](/react-native/docs/android/timers#timestamps) to
   * learn more about this property.
   */
  showTimestamp?: boolean;

  /**
   * The small icon to show in the heads-up notification.
   *
   * See our [Android Appearance guide](/react-native/docs/android/appearance#small-icons) to learn
   * more about this property.
   *
   * Defaults to `ic_launcher`.
   *
   * #### Example
   *
   * ```js
   * await notifee.displayNotification({
   *   body: 'Custom small icon',
   *   android: {
   *     smallIcon: 'app-icon',
   *   },
   * });
   * ```
   */
  smallIcon?: string;

  /**
   * An additional level parameter for when the icon is an instance of a Android `LevelListDrawable`.
   */
  smallIconLevel?: number;

  /**
   * Set a sort key that orders this notification among other notifications from the same package.
   * This can be useful if an external sort was already applied and an app would like to preserve
   * this. Notifications will be sorted lexicographically using this value, although providing
   * different priorities in addition to providing sort key may cause this value to be ignored.
   *
   * If a `group` has been set, the sort key can also be used to order members of a notification group.
   *
   * See our [Android Grouping & Sorting guide](/react-native/docs/android/grouping-and-sorting#sorting) to
   * learn more about this property.
   */
  sortKey?: string;

  /**
   * Styled notifications provide users with more informative content and additional functionality.
   *
   * See our [Android Styles guide](/react-native/docs/android/grouping-and-sorting) to
   * learn more about the various Android Notification styles that are supported.
   **/
  style?: AndroidBigPictureStyle | AndroidBigTextStyle | AndroidInboxStyle | AndroidMessagingStyle;

  /**
   * Text that summarizes this notification for accessibility services. As of the Android L release, this
   * text is no longer shown on screen, but it is still useful to accessibility services
   * (where it serves as an audible announcement of the notification's appearance).
   *
   * Ticker text does not show in the notification.
   *
   * #### Example
   *
   * ```js
   * await notifee.displayNotification({
   *   android: {
   *     body: 'You have 1 new message',
   *     ticker: 'A new message has been received',
   *   },
   * });
   * ```
   */
  ticker?: string;

  /**
   * Sets the time in milliseconds at which the notification should be
   * cancelled once displayed, if it is not already cancelled.
   *
   * #### Example
   *
   * Time out after 10 seconds.
   *
   * ```js
   * await notifee.displayNotification({
   *   body: 'Show for 10 seconds',
   *   android: {
   *     timeoutAfter: 10000,
   *   },
   * });
   * ```
   */
  timeoutAfter?: number;

  /**
   * Shows a counting timer on the notification, useful for on-going notifications such as a phone call.
   *
   * If no `timestamp` is provided, a counter will display on the notification starting from 00:00. If a `timestamp` is
   * provided, the number of hours/minutes/seconds since that have elapsed since that value will be shown instead.
   *
   * Defaults to `false`.
   *
   * See our [Android Timers guide](/react-native/docs/android/timers#timers) to
   * learn more about this property.
   */
  showChronometer?: boolean;

  /**
   * Enables and sets the vibrate pattern.
   *
   * The pattern in milliseconds. Must be an even amount of numbers.
   *
   * #### Example
   *
   * Vibrate for 300ms with a 300ms delay.
   *
   * ```js
   * await notifee.displayNotification({
   *   android: {
   *     body: 'Vibrating notification',
   *     vibrationPattern: [300, 300],
   *   },
   * });
   * ```
   */
  vibrationPattern?: number[];

  /**
   * Sets the visibility for this notification. This may be used for apps which show user
   * sensitive information (e.g. a banking app).
   *
   * Defaults to `AndroidVisibility.PRIVATE`.
   *
   * See our [Android Appearance guide](/react-native/docs/android/appearance#visibility) to learn
   * more about this property.
   */
  visibility?: AndroidVisibility;

  /**
   * - tags
   * - for querying notifications
   * - has no visual impact on notifications
   */
  tag?: string;

  /**
   * The timestamp in milliseconds for this notification. Notifications in the panel are sorted by this time.
   *
   * - Use with `showTimestamp` to show the timestamp to the users.
   * - Use with `showChronometer` to create a on-going timer.
   *
   * #### Example
   *
   * Show the length of time the notification has been showing for.
   *
   * ```js
   * await notifee.displayNotification({
   *   body: 'Phone call in progress',
   *   android: {
   *     ongoing: true,
   *     timestamp: Date.now(),
   *     showChronometer: true,
   *   },
   * });
   * ```
   */
  timestamp?: number;
}

/**
 * The interface used to describe a notification action.
 *
 * Notification actions allow users to interact with notifications, allowing you to handle events
 * within your application. When an action completes (e.g. pressing an action, or filling out an input
 * box) and event is sent and can be handled by a `onEvent` listener.
 *
 * When an action completes, it is up to you handle the event by either cancelling or updating the
 * notification.
 *
 * @platform android
 */
export interface AndroidAction {
  /**
   * The press action interface describing what happens when an action completes.
   *
   * Note; unlike the `pressAction` in the notification body, an action is not required to open the application
   * and can perform background tasks. See the [AndroidPressAction](/react-native/reference/androidpressaction) reference
   * or [Android Actions](/react-native/docs/android/actions) for more information.
   */
  pressAction: AndroidPressAction;

  /**
   * The title of the notification, e.g. "Reply", "Mark as read" etc.
   */
  title: string;

  /**
   * An remote http icon representing the action. Newer devices may not show the icon.
   *
   * Recommended icon size is 24x24 px.
   */
  icon?: string;

  /**
   * If provided, the action accepts user input.
   *
   * If `true`, the user will be able to provide free text input when the action is pressed. This
   * property can be further configured for advanced inputs.
   *
   * See the [AndroidInput](/react-native/reference/androidinput) reference
   * or [Android Actions](/react-native/docs/android/actions) for more information.
   */
  input?: true | AndroidInput;
}

/**
 * The interface used to describe a press action for Android notifications.
 *
 * There are various ways a user can interact with a notification, the most common being pressing
 * the notification, pressing an action or providing text input. This interface defines what happens
 * when a user performs such interaction.
 *
 * When provided to a notification `pressAction`, the application will always open (if not already)
 * using the default `launchActivity` for the application.
 *
 * When provided to a notification action, the action will only open the application if a `launchActivity`
 * and/or `mainComponent` is provided.
 *
 * @platform android
 */
export interface AndroidPressAction {
  /**
   * The unique ID for the action.
   *
   * The `id` property is used to differentiate between user press actions. When listening to notification
   * events via `onEvent`, the ID can be read from the `event.action` object:
   *
   * ```js
   * notifee.onEvent((eventType, event) => {
   *   if (eventType === notifee.EventType.PRESS || eventType === notifee.EventType.ACTION_PRESS) {
   *     console.log(`User press action id: ${event.action.id}`);
   *   }
   * });
   * ```
   */
  id: string;

  /**
   * The custom Android Activity to launch on a press action.
   *
   * This property can be used in advanced scenarios to launch a custom Android Activity when the user
   * performs a press action.
   *
   * If the action originated from the notification body, this value defaults to `default`, opening the
   * default Android Activity your application runs on. When providing a custom Activity class you must provide the
   * full scope & the class must extend `ReactActivity`.
   *
   * TODO Guide
   */
  launchActivity?: string;

  /**
   * A custom registered React component to launch on press action.
   *
   * This property can be used to open a custom React component when the user performs a press action.
   * For this to correctly function, a basic native code change is required. See [Android Actions](#)
   * to learn more.
   *
   * TODO Guide
   */
  mainComponent?: string;
}

/**
 * TODO
 *
 * @platform android
 */
export interface AndroidInput {
  /**
   * If `true`, when an action is pressed this allows the user to type free form text into the input area.
   * If `false`, you must provide an array of `choices` the user is allowed to use as the input.
   *
   * Defaults to `true`.
   */
  allowFreeFormInput?: boolean;

  /**
   * Sets whether generated replies can be added to the action.
   *
   * Generated replies will only be shown if the input has `choices` and whether the device
   * is able to generate replies.
   *
   * Defaults to `true`.
   */
  allowGeneratedReplies?: boolean;

  /**
   * An array of pre-defined input choices the user can select.
   *
   * If `allowFreeFormInput` is `false`, this property must contain at least one choice.
   */
  choices?: string[];

  /**
   * If `true`, the user will be able to edit the selected choice before sending the action event, however
   * `allowFreeFormInput` must also be `true`.
   *
   * By default, the platform will decide whether choices can be editable. To explicitly enable or disable
   * this, provide `true` or `false`.
   */
  editableChoices?: boolean;

  /**
   * The placeholder text to display inside of the user input area.
   */
  placeholder?: string;
}

/**
 * Notifications can show a large image when expanded, which is useful for apps with a heavy media
 * focus, such as Instagram.
 *
 * ![Big Picture Style](https://developer.android.com/images/ui/notifications/template-image_2x.png)
 *
 * #### Example
 *
 * ```js
 * TODO example
 * ```
 *
 * @platform android
 */
export interface AndroidBigPictureStyle {
  type: AndroidStyle.BIGPICTURE;
  picture: string;
  title?: string;
  largeIcon?: string;
  summary?: string;
}

/**
 * Notifications can show a large amount of text when expanded, for example when displaying new
 * messages.
 *
 * By default, messages are not expanded, causing any overflowing notification `body` next to be
 * truncated. Setting a `bigTextStyle` allows the notification to be expandable showing the full
 * text body.
 *
 * ![Big Text Style](https://developer.android.com/images/ui/notifications/template-large-text_2x.png)
 *
 * #### Example
 *
 * ```js
 * await notifee.displayNotification({
 *   body: 'Hello World',
 *   android: {
 *     style: {
 *       type: notifee.AndroidStyle.BIGTEXT,
 *       text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis consectetur magna ut nulla blandit tristique.',
 *     },
 *   }
 * });
 * ```
 *
 * @platform android
 */
export interface AndroidBigTextStyle {
  type: AndroidStyle.BIGTEXT;
  /**
   * The text to display when the notification is expanded.
   */
  text: string;

  /**
   * Overrides the notification title when expanded.
   */
  title?: string;

  /**
   * Sets summary text when the notification is expanded.
   */
  summary?: string;
}

/**
 * TODO
 */
export interface AndroidInboxStyle {
  type: AndroidStyle.INBOX;
  lines: string[];
  title?: string;
  summary?: string;
}

/**
 *
 */
export interface AndroidMessagingStyle {
  type: AndroidStyle.MESSAGING;

  /**
   * The person who is receiving a message on the current device.
   */
  person: AndroidPerson;

  /**
   * An array of messages to display inside the notification.
   */
  messages: AndroidMessagingStyleMessage[];

  /**
   * An optional conversation title, displayed at the top of the
   * notification.
   */
  title?: string;

  /**
   * Sets whether this conversation notification represents a group
   * (3 or more persons).
   */
  group?: boolean;
}

/**
 * The interface for messages when constructing a Messaging Style notification.
 *
 * #### Example
 *
 * ```js
 *
 * ```
 */
export interface AndroidMessagingStyleMessage {
  /**
   * The content of the message.
   */
  text: string;

  /**
   * The timestamp of when the message arrived in milliseconds.
   */
  timestamp: number;

  /**
   * The sender of this message. See `AndroidPerson` reference for more information
   * on the properties available.
   *
   * > This property should only be provided if the message is from an external person, and not the person receiving the message.
   */
  person?: AndroidPerson;
}

/**
 * The interface used to describe a person shown in notifications. Currently used with `AndroidMessagingStyle` notifications.
 */
export interface AndroidPerson {
  /**
   * The name of the person.
   *
   * If no `id` is provided, the name will be used as the unique identifier.
   */
  name: string;

  /**
   * An optional unique ID of the person. Setting this property is preferred for unique identification,
   * however not required. If no value is provided, the `name` will be used instead..
   */
  id?: string;

  /**
   * If `true` this person represents a machine rather than a human. This is used primarily for testing and automated tooling.
   *
   * Defaults to `false`.
   */
  bot?: boolean;

  /**
   * If `true` this person will be marked as important.
   *
   * Important users are those who frequently contact the receiving person. If the app is in
   * "Do not disturb" mode, a notification containing an important person may override this mode
   * if the person has been whitelisted on the device.
   *
   * Defaults to `false`.
   */
  important?: boolean;

  /**
   * The icon to display next to the person in the notification. The icon can be URL or local
   * Android resource.
   *
   * If not provided, an icon will be automatically creating using the `name` property.
   */
  icon?: string;

  /**
   * TODO
   */
  uri?: string; // todo - how?
}

/**
 * Interface for defining the progress of an Android Notification.
 *
 * A notification can show current progress of a task. The progress state can either be fixed or
 * indeterminate (unknown).
 *
 * #### Example - Fixed Progress
 *
 * ![Fixed Progress](https://miro.medium.com/max/480/1*OHOY45cU27NaYkF0MU3hrw.gif)
 *
 * ```js
 * await notifee.displayNotification({
 *   android: {
 *     progress: {
 *       max: 10,
 *       current: 5,
 *     }
 *   },
 * });
 * ```
 *
 * #### Example - Indeterminate Progress
 *
 * Setting `indeterminate` to `true` overrides the `max`/`current` settings.
 *
 * ![Progress](https://miro.medium.com/max/480/1*mW-_3PUxAG1unAZOf0IuoQ.gif)
 *
 * ```js
 * await notifee.displayNotification({
 *   android: {
 *     progress: {
 *       max: 10,
 *       current: 5,
 *       indeterminate: true,
 *     }
 *   },
 * });
 * ```
 *
 * @platform android
 */
export interface AndroidProgress {
  /**
   * The maximum progress number. E.g `10`.
   *
   * Must be greater than the `current` value.
   */
  max?: number;

  /**
   * The current progress.
   *
   * E.g. setting to `4` with a `max` value of `10` would set a fixed progress bar on the notification at 40% complete.
   */
  current?: number;

  /**
   * If `true`, overrides the `max` and `current` values and displays an unknown progress style. Useful when you have no
   * knowledge of a tasks completion state.
   *
   * Defaults to `false`.
   */
  indeterminate?: boolean;
}

/**
 * An interface for describing an Android Channel.
 *
 * Channels override any individual notification preferences (e.g. lights/vibration) and the user
 * has control over the setting.
 *
 * See our [Android Channels & Groups guide](/react-native/docs/android/channels) to learn more
 * about Channels.
 *
 * > On Android 8.0 (API 26) each notification must be assigned to a channel.
 *
 * ![Android Channel](https://prismic-io.s3.amazonaws.com/invertase%2Fbb773539-581a-457d-ae43-687a7a7646a9_new+project+%2822%29.jpg)
 *
 * #### Example
 *
 * ```js
 * await notifee.createChannel({
 *   id: 'alarms',
 *   name: 'Alarms & Timers',
 *   lightColor: '#3f51b5',
 * });
 * ```
 *
 * @platform android
 */
export interface AndroidChannel {
  /**
   * The unique channel ID.
   */
  id: string;

  /**
   * The channel name. This is shown to the user so must be descriptive and relate to the notifications
   * which will be delivered under this channel.
   *
   * This setting can be updated after creation.
   */
  name: string;

  /**
   * Sets whether notifications posted to this channel can appear as application icon badges in a Launcher.
   *
   * Defaults to `true`.
   *
   * This setting cannot be overridden once the channel is created.
   */
  badge?: boolean;

  /**
   * Sets whether or not notifications posted to this channel can interrupt the user in
   * 'Do Not Disturb' mode.
   *
   * Defaults to `false`.
   *
   * This setting cannot be overridden once the channel is created.
   *
   * @platform android API Level >= 29
   */
  bypassDnd?: boolean;

  /**
   * Sets the user visible description of this channel.
   *
   * The recommended maximum length is 300 characters; the value may be truncated if it is too long.
   *
   * This setting can be updated after creation.
   *
   * @platform android API Level >= 28
   */
  description?: string;

  /**
   * Sets whether notifications posted to this channel should display notification lights, on devices that support that feature.
   *
   * Defaults to `true`.
   *
   * This setting cannot be overridden once the channel is created.
   */
  lights?: boolean;

  /**
   * Sets whether notification posted to this channel should vibrate.
   *
   * Defaults to `true`.
   *
   * This setting cannot be overridden once the channel is created.
   */
  vibration?: boolean;

  /**
   * Sets what group this channel belongs to. Group information is only used for presentation, not for behavior.
   *
   * Create a group via `createChannelGroup()`.
   *
   * This setting cannot be overridden once the channel is created.
   */
  groupId?: string;

  /**
   * Sets the level of interruption of this notification channel.
   *
   * This setting can only be set to a lower importance level once set.
   */
  importance?: AndroidImportance;

  /**
   * If lights are enabled (via `enableLights`), sets/overrides the light color for notifications
   * posted to this channel.
   *
   * This setting cannot be overridden once the channel is created.
   */
  lightColor?: AndroidColor | string;

  /**
   * Sets whether notifications posted to this channel appear on the lockscreen or not,
   * and if so, whether they appear in a redacted form.
   *
   * This setting cannot be overridden once the channel is created.
   */
  visibility?: AndroidVisibility;

  /**
   * Sets/overrides the vibration pattern for notifications posted to this channel.
   *
   * The pattern in milliseconds. Must be an even amount of numbers.
   *
   * This setting cannot be overridden once the channel is created.
   */
  vibrationPattern?: number[];

  /**
   * Overrides the sound the notification is displayed with.
   *
   * The default value is `default`, which is the system default sound.
   *
   * This setting cannot be overridden once the channel is created.
   */
  sound?: string;
}

export interface NativeAndroidChannel extends AndroidChannel {
  /*
   * Returns whether or not notifications posted to this Channel group are
   * blocked.
   *
   * On API levels < 28, returns `false`.
   *
   * @platform android API Level >= 28
   */
  blocked: boolean;
}

/**
 * Interface for an Android Channel Group.
 *
 * See our [Android Channels & Groups guide](/react-native/docs/android/introduction) to learn more
 * about Channel Groups.
 *
 * ![Channel Group Example](https://prismic-io.s3.amazonaws.com/invertase%2F21fb6bbf-6932-47c3-8695-877e1d4f296b_new+project+%2821%29.jpg)
 *
 * @platform android API Level >= 26
 */
export interface AndroidChannelGroup {
  /**
   * Unique id for this channel group.
   */
  id: string;

  /**
   * The name of the group. This is visible to the user so should be a descriptive name which
   * categorizes other channels (e.g. reminders).
   *
   * The recommended maximum length is 40 characters; the value may be truncated if it is too long.
   */
  name: string;

  /**
   * An optional description of the group. This is visible to the user.
   *
   * On Android APIs less than 28 this will always be undefined.
   *
   * @platform android API Level >= 28
   */
  description?: string;
}

/**
 * Interface for a native Android Channel Group.
 *
 * @platform android API Level >= 26
 */
export interface NativeAndroidChannelGroup extends AndroidChannelGroup {
  /**
   * Returns whether or not notifications posted to a Channel belonging to this group are
   * blocked by the user.
   *
   * On API levels < 28, returns `false`.
   *
   * @platform android API Level >= 28
   */
  blocked: boolean;

  /**
   * Returns a list of channels assigned to this channel group.
   */
  channels: NativeAndroidChannel[];
}

/**
 * When a notification is being displayed as a badge, the `AndroidBadgeIconType` interface
 * describes how the badge icon is shown to the user.
 *
 * @platform android
 */
export enum AndroidBadgeIconType {
  /**
   * No badge is displayed, will always show as a number.
   */
  NONE = 0,

  /**
   * Shows the notification `smallIcon`.
   */
  SMALL = 1,

  /**
   * Shows the notification `largeIcon`.
   */
  LARGE = 2,
}

/**
 * The category of a notification.
 *
 * Setting a category on a notification helps the device to understand what the notification is for,
 * or what impact it will have on the user. The category can be used for ranking and filtering
 * the notification.
 *
 * @platform android
 */
export enum AndroidCategory {
  ALARM = 'alarm',
  CALL = 'call',
  EMAIL = 'email',
  ERROR = 'error',
  EVENT = 'event',
  MESSAGE = 'msg',
  NAVIGATION = 'navigation',
  PROGRESS = 'progress',
  PROMO = 'promo',
  RECOMMENDATION = 'recommendation',
  REMINDER = 'reminder',
  SERVICE = 'service',
  SOCIAL = 'social',
  STATUS = 'status',
  SYSTEM = 'sys',
  TRANSPORT = 'transport',
}

/**
 * A set or predefined colors which can be used with Android Notifications.
 *
 * @platform android
 */
export enum AndroidColor {
  RED = 'red',
  BLUE = 'blue',
  GREEN = 'green',
  BLACK = 'black',
  WHITE = 'white',
  CYAN = 'cyan',
  MAGENTA = 'magenta',
  YELLOW = 'yellow',
  LIGHTGRAY = 'lightgray',
  DARKGRAY = 'darkgray',
  GRAY = 'gray',
  LIGHTGREY = 'lightgrey',
  DARKGREY = 'darkgrey',
  AQUA = 'aqua',
  FUCHSIA = 'fuchsia',
  LIME = 'lime',
  MAROON = 'maroon',
  NAVY = 'navy',
  OLIVE = 'olive',
  PURPLE = 'purple',
  SILVER = 'silver',
  TEAL = 'teal',
}

/**
 * On devices which do not support notification channels (API Level < 26), the notification
 * by default will use all methods to alert the user (depending on the importance).
 *
 * To override the default behaviour, provide an array of defaults to the notification.
 *
 * On API Levels >= 26, this has no effect and notifications will use the channel behaviour.
 *
 * @platform android API Level < 26
 */
export enum AndroidDefaults {
  /**
   * All options will be used where possible.
   */
  ALL = -1,

  /**
   * The notification will use lights to alert the user.
   */
  LIGHTS = 4,

  /**
   * The notification will use sound to alert the user.
   */
  SOUND = 1,

  /**
   * The notification will vibrate to alert the user.
   */
  VIBRATE = 2,
}

/**
 * TODO docs
 *
 * @platform android
 */
export enum AndroidGroupAlertBehavior {
  ALL = 0,
  SUMMARY = 1,
  CHILDREN = 2,
}

/**
 * The interface describing the importance levels of an incoming notification.
 *
 * A notification importance level can be set directly onto a notification channel for supported devices (API Level >= 26)
 * or directly onto the notification for devices which do not support channels.
 *
 * The importance is used by the device to both change the visual prompt of a received notification
 * and also how it visually appears in the device notification shade.
 *
 * See our [Android Appearance guide](/react-native/docs/android/appearance#importance) to learn
 * more about importance.
 *
 * @platform android
 */
export enum AndroidImportance {
  /**
   * The default importance applied to a channel/notification.
   *
   * The application small icon will show in the device statusbar. When the user pulls down the
   * notification shade, the notification will show in it's expanded state (if applicable).
   */
  DEFAULT = 3,

  /**
   * The highest importance level applied to a channel/notification.
   *
   * Notifications will appear on-top of applications, allowing direct interaction without pulling
   * down the notification shade. This level should only be used for urgent notifications, such as
   * incoming phone calls, messages etc, which require immediate attention.
   */
  HIGH = 4,

  /**
   * A low importance level applied to a channel/notification.
   *
   * The application small icon will show in the device statusbar, however the notification will not alert
   * the user (no sound or vibration). The notification will show in it's expanded state when the
   * notification shade is pulled down.
   */
  LOW = 2,

  /**
   * The minimum importance level applied to a channel/notification.
   *
   * The application small icon will not show up in the statusbar, or alert the user. The notification
   * will be in a collapsed state in the notification shade and placed at the bottom of the list.
   *
   * This level should be used when the notification requires no immediate attention. An example of this
   * importance level is the Google app providing weather updates and only being visible when the
   * user pulls the notification shade down,
   *
   */
  MIN = 1,

  /**
   * The notification will not be shown. This has the same effect as the user disabling notifications
   * in the application settings.
   */
  NONE = 0,
}

/**
 * Available Android Notification Styles.
 *
 * Used when providing a `style` to a notification builder with `displayNotification`.
 *
 * @platform android
 */
export enum AndroidStyle {
  BIGPICTURE = 0,
  BIGTEXT = 1,
  INBOX = 2,
  MESSAGING = 3,
}

/**
 * Interface used to define the visibility of an Android notification.
 *
 * Use with the `visibility` property on the notification.
 *
 * Default value is `AndroidVisibility.PRIVATE`.
 *
 * @platform android
 */
export enum AndroidVisibility {
  /**
   * Show the notification on all lockscreens, but conceal sensitive or private information on secure lockscreens.
   */
  PRIVATE = 0,

  /**
   * Show this notification in its entirety on all lockscreens.
   */
  PUBLIC = 1,

  /**
   * Do not reveal any part of this notification on a secure lockscreen.
   *
   * Useful for notifications showing sensitive information such as banking apps.
   */
  SECRET = -1,
}
