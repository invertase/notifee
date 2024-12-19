/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NotificationPressAction, NotificationFullScreenAction } from './Notification';

/**
 * The interface for Android specific options which are applied to a notification.
 *
 * To learn more about Android notifications, view the [Android](/react-native/android/introduction)
 * documentation for full examples and usage.
 *
 * @platform android
 */
export interface NotificationAndroid {
  /**
   * An array of [AndroidAction](/react-native/reference/androidaction) interfaces.
   *
   * Adds quick actions to a notification. Quick Actions enable users to interact with your application
   * directly from the notification body, providing an overall greater user experience.
   *
   * View the [Quick Actions](/react-native/android/interaction#quick-actions) documentation for more information.
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
   * View the [Foreground Service](/react-native/android/foreground-service) documentation for more information.
   *
   * Defaults to `false`.
   */
  asForegroundService?: boolean;

  /**
   * When set to `true` the screen will light up when the notification is displayed.
   *
   * Defaults to `false`.
   */
  lightUpScreen?: boolean;

  /**
   * Setting this flag will make it so the notification is automatically canceled when the user
   * presses it in the panel.
   *
   * By default when the user taps a notification it is automatically removed from the notification
   * panel. Setting this to `false` will keep the notification in the panel.
   *
   * If `false`, the notification will persist in the notification panel after being pressed. It will
   * remain there until the user removes it (e.g. swipes away) or is cancelled via
   * [`cancelNotification`](/react-native/reference/cancelNotification).
   *
   * Defaults to `true`.
   */
  autoCancel?: boolean;

  /**
   * Overrides the current number of active notifications shown on the device.
   *
   * If no number is provided, the system displays the current number of active notifications.
   */
  badgeCount?: number;

  /**
   * Sets the type of badge used when the notification is being displayed in badge mode.
   *
   * View the [Badges](/react-native/android/appearance#badges) documentation for more information
   * and usage examples.
   *
   * Defaults to `AndroidBadgeIconType.LARGE`.
   *
   * @platform android API Level >= 26
   */
  badgeIconType?: AndroidBadgeIconType;

  /**
   * Assigns the notification to a category. Use the one which best describes the notification.
   *
   * The category may be used by the device for ranking and filtering. It has no visual or behavioural
   * impact.
   */
  category?: AndroidCategory;

  /**
   * Specifies the `AndroidChannel` which the notification will be delivered on.
   *
   * On Android 8.0 (API 26) the channel ID is required. Providing a invalid channel ID will throw
   * an error. View the [Channels & Groups](/react-native/android/channels) documentation for
   * more information and usage examples.
   */
  channelId?: string;

  /**
   * Set an custom accent color for the notification. If not provided, the default notification
   * system color will be used.
   *
   * The color can be a predefined system `AndroidColor` or [hexadecimal](https://gist.github.com/lopspower/03fb1cc0ac9f32ef38f4).
   *
   * View the [Color](/react-native/android/appearance#color) documentation for more information.
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
   * View the [Foreground Service](/react-native/android/foreground-service) documentation for more information.
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
   * On some devices, the system may automatically group notifications.
   *
   * View the [Android Grouping & Sorting guide](/react-native/android/grouping-and-sorting) documentation to
   * learn more.
   */
  groupId?: string;

  /**
   * Sets the group alert behavior for this notification. Use this method to mute this notification
   * if alerts for this notification's group should be handled by a different notification. This is
   * only applicable for notifications that belong to a `groupId`. This must be called on all notifications
   * you want to mute. For example, if you want only the summary of your group to make noise, all
   * children in the group should have the group alert behavior `AndroidGroupAlertBehavior.SUMMARY`.
   *
   * View the [Android Grouping & Sorting guide](/react-native/android/grouping-and-sorting#group-behaviour)
   * documentation to learn more.
   */
  groupAlertBehavior?: AndroidGroupAlertBehavior;

  /**
   * Whether this notification should be a group summary.
   *
   * If `true`, Set this notification to be the group summary for a group of notifications. Grouped notifications may display in
   * a cluster or stack on devices which support such rendering. Requires a `groupId` key to be set.
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
   * A local file path using the 'require()' method or a remote http to the picture to display.
   *
   * Sets a large icon on the notification.
   *
   * View the [Android Appearance](/react-native/android/appearance#large-icons) documentation to learn
   * more about this property.
   */
  largeIcon?: string | number | object;

  /**
   * Whether the large icon should be circular.
   *
   * If `true`, the large icon will be rounded in the shape of a circle.
   *
   * Defaults to `false`.
   */
  circularLargeIcon?: boolean;

  /**
   * Sets the color and frequency of the light pattern. This only has effect on supported devices.
   *
   * The option takes an array containing a hexadecimal color value or predefined `AndroidColor`,
   * along with the number of milliseconds to show the light, and the number of milliseconds to
   * turn off the light. The light frequency pattern is repeated.
   *
   * View the [Lights](/react-native/android/behaviour#lights) documentation for more information.
   */
  lights?: [AndroidColor | string, number, number];

  /**
   * Sets whether the notification will only appear on the local device.
   *
   * Users who have connected devices which support notifications (such as a smart watch) will
   * receive an alert for the notification on that device. If set to `true`, the notification will
   * only alert on the main device.
   *
   * Defaults to `false`.
   */
  localOnly?: boolean;

  /**
   * Set whether this is an on-going notification.
   *
   * Setting this value to `true` changes the default behaviour of a notification:
   *
   * - Ongoing notifications are sorted above the regular notifications in the notification panel.
   * - Ongoing notifications do not have an 'X' close button, and are not affected by the "Clear all" button.
   *
   * View the [Ongoing](/react-native/android/behaviour#ongoing) documentation for more information.
   */
  ongoing?: boolean;

  /**
   * Set whether the sound should loop, by default, the sound will only play once.
   *
   * This property is useful if you have an ongoing notification.
   */
  loopSound?: boolean;

  /**
   * Set any additional flags
   */
  flags?: AndroidFlags[];

  /**
   * Notifications with the same `id` will only show a single instance at any one time on your device,
   * however will still alert the user (for example, by making a sound).
   *
   * If this flag is set to `true`, notifications with the same `id` will only alert the user once whilst
   * the notification is visible.
   *
   * This property is commonly used when frequently updating a notification (such as updating the progress bar).
   */
  onlyAlertOnce?: boolean;

  /**
   * By default notifications have no behaviour when a user presses them. The
   * `pressAction` property allows you to set what happens when a user presses
   * the notification.
   *
   * View the [Interaction](/react-native/android/interaction) documentation to learn
   * more.
   */
  pressAction?: NotificationPressAction;

  /**
   * The `fullScreenAction` property allows you to show a custom UI
   * in full screen when the notification is displayed.
   *
   * View the [FullScreenAction](/react-native/android/behaviour#full-screen) documentation to learn
   * more.
   */
  fullScreenAction?: NotificationFullScreenAction;

  /**
   * Set the foreground service types identifying the work done by the service
   *
   * View the [Foreground service types](https://developer.android.com/develop/background-work/services/fg-service-types) documentation to learn
   * more.
   */
  foregroundServiceTypes?: AndroidForegroundServiceType[];

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
   * View the [Appearance](/react-native/android/appearance#importance) documentation to learn
   * more.
   *
   * @platform android API Level < 26
   */
  importance?: AndroidImportance;

  /**
   * A notification can show current progress of a task. The progress state can either be fixed or
   * indeterminate (unknown).
   *
   * View the [Progress Indicators](/react-native/android/progress-indicators) documentation
   * to learn more.
   */
  progress?: AndroidProgress;

  /**
   * Sets whether the `timestamp` provided is shown in the notification.
   *
   * Setting this field is useful for notifications which are more informative with a timestamp,
   * such as an E-Mail.
   *
   * If no `timestamp` is set, this field has no effect.
   *
   * View the [Timestamps](/react-native/android/timers#timestamps) documentation to learn more.
   */
  showTimestamp?: boolean;

  /**
   * The small icon to show in the heads-up notification.
   *
   * View the [Icons](/react-native/android/appearance#small-icons) documentation to learn
   * more.
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
   * If a `groupId` has been set, the sort key can also be used to order members of a notification group.
   *
   * View the [Android Grouping & Sorting](/react-native/android/grouping-and-sorting#sorting)
   * documentation to learn more.
   */
  sortKey?: string;

  /**
   * Styled notifications provide users with more informative content and additional functionality.
   *
   * Android supports different styles, however only one can be used with a notification.
   *
   * View the [Styles](/react-native/android/styles) documentation to learn more
   * view usage examples.
   **/
  style?: AndroidBigPictureStyle | AndroidBigTextStyle | AndroidInboxStyle | AndroidMessagingStyle;

  /**
   * Text that summarizes this notification for accessibility services. As of the Android L release, this
   * text is no longer shown on screen, but it is still useful to accessibility services
   * (where it serves as an audible announcement of the notification's appearance).
   *
   * Ticker text does not show in the notification.
   */
  ticker?: string;

  /**
   * Sets the time in milliseconds at which the notification should be
   * automatically cancelled once displayed, if it is not already cancelled.
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
   * View the [Timers](/react-native/android/timers#timers) documentation to learn more.
   */
  showChronometer?: boolean;

  /**
   * Sets the vibration pattern the notification uses when displayed. Must be an even amount of numbers.
   *
   * View the [Vibration](/react-native/android/behaviour#vibration) documentation to learn more.
   */
  vibrationPattern?: number[];

  /**
   * Sets the visibility for this notification. This may be used for apps which show user
   * sensitive information (e.g. a banking app).
   *
   * Defaults to `AndroidVisibility.PRIVATE`.
   *
   * View the [Visibility](/react-native/android/appearance#visibility) documentation to learn
   * more.
   */
  visibility?: AndroidVisibility;

  /**
   * Sets a tag on the notification.
   *
   * Tags can be used to query groups notifications by the tag value. Setting a tag has no
   * impact on the notification itself.
   */
  tag?: string;

  /**
   * The timestamp in milliseconds for this notification. Notifications in the panel are sorted by this time.
   *
   * The timestamp can be used with other properties to change the behaviour of a notification:
   *
   * - Use with `showTimestamp` to show the timestamp to the users.
   * - Use with `showChronometer` to create a on-going timer.
   *
   * View the [Timers](/react-native/android/timers) documentation to learn more.
   */
  timestamp?: number;

  /**
   * Overrides the sound the notification is displayed with.
   *
   * The default value is to play no sound. To play the default system sound use 'default'.
   *
   * This setting has no behaviour on Android after API level version 26, instead you can set the
   * sound on the notification channels.
   *
   * View the [Sound](/react-native/android/behaviour#sound) documentation for more information.
   *
   * @platform android API Level < 26
   */
  sound?: string;
}

/**
 * An interface representing the current android only notification-related settings for your app.
 *
 * This interface is returned from [`requestPermission`](/react-native/reference/requestpermission)
 * and [`getNotificationSettings`](/react-native/reference/getnotificationsettings).
 *
 * View the [Permissions](/react-native/android/permissions) documentation to learn more.
 *
 * @platform android
 */

export enum AndroidNotificationSetting {
  /**
   * This setting is not supported on this device. Usually this means that the Android version required
   * for this setting has not been met.
   */
  NOT_SUPPORTED = -1,

  /**
   * This setting is currently disabled by the user.
   */
  DISABLED = 0,

  /**
   * This setting is currently enabled.
   */
  ENABLED = 1,
}

export interface AndroidNotificationSettings {
  /**
   * Enum describing if you can create triggers
   *
   * For Android < 12 / API < 31, this will default to true
   *
   * View the [Trigger](/react-native/android/triggers#android-12-limitations) documentation for more information.
   */
  alarm: AndroidNotificationSetting;
}

/**
 * The interface used to describe a notification quick action for Android.
 *
 * Notification actions allow users to interact with notifications, allowing you to handle events
 * within your application. When an action completes (e.g. pressing an action, or filling out an input
 * box) an event is sent.
 *
 * View the [Quick Actions](/react-native/android/interaction#quick-actions) documentation to learn more.
 *
 * @platform android
 */
export interface AndroidAction {
  /**
   * The press action interface describing what happens when an action completes.
   *
   * Note; unlike the `pressAction` in the notification body, an action does not need to open the application
   * and can perform background tasks. See the [AndroidPressAction](/react-native/reference/androidpressaction) reference
   * or [Quick Actions](/react-native/android/interaction#quick-actions) documentation to learn more.
   */
  pressAction: NotificationPressAction;

  /**
   * The title of the action, e.g. "Reply", "Mark as read" etc.
   */
  title: string;

  /**
   * An remote http or local icon path representing the action. Newer devices may not show the icon.
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
   * View the [Action Input](/react-native/android/interaction#action-input) documentation to
   * learn more.
   */
  input?: true | AndroidInput;
}

/**
 * The interface used to enable advanced user input on a notification.
 *
 * View the [Action Input](/react-native/android/interaction#action-input) documentation to learn more.
 *
 * @platform android
 */
export interface AndroidInput {
  /**
   * Sets whether the user can freely enter text into the input.
   *
   * This value changes the behaviour of the notification:
   *
   * - If `true`, when an action is pressed this allows the user to type free form text into the input area.
   * - If `false`, you must provide an array of `choices` the user is allowed to use as the input.
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
 * The interface used when displaying a Big Picture Style notification.
 *
 * <Vimeo id="android-style-bigpicture" caption="Android Big Picture Style" />
 *
 * View the [Big Picture](/react-native/android/styles#big-picture) documentation to learn more.
 *
 * @platform android
 */
export interface AndroidBigPictureStyle {
  /**
   * Constant enum value used to identify the style type.
   */
  type: AndroidStyle.BIGPICTURE;

  /**
   * A local file path using the 'require()' method or a HTTP or file URL to the picture to display.
   *
   * The image will be automatically resized depending on the device and it's size. If the image could
   * not be found a blank space will appear.
   */
  picture: string | number | object;

  /**
   * If set, overrides the main notification `title` when the notification is expanded.
   */
  title?: string;

  /**
   * A local file path using the 'require()' method or a HTTP or file URL to the picture to display.
   *
   * If set, overrides the main notification `largeIcon` when the notification is expanded.
   *
   * To hide the `largeIcon` when the notification is expanded, set to null. Similar to `thumbnailHidden` for attachments on iOS.
   */
  largeIcon?: string | number | object | null;

  /**
   * If set, overrides the main notification `summary` when the notification is expanded.
   */
  summary?: string;
}

/**
 * The interface used when displaying a Big Text Style notification.
 *
 * <Vimeo id="android-style-bigtext" caption="Android Big Text Style" />
 *
 * View the [Big Text](/react-native/android/styles#big-text) documentation to learn more.
 *
 * @platform android
 */
export interface AndroidBigTextStyle {
  /**
   * Constant enum value used to identify the style type.
   */
  type: AndroidStyle.BIGTEXT;

  /**
   * The text to display when the notification is expanded.
   */
  text: string;

  /**
   * If set, overrides the main notification `title` when the notification is expanded.
   */
  title?: string;

  /**
   * If set, overrides the main notification `summary` when the notification is expanded.
   */
  summary?: string;
}

/**
 * The interface used when displaying a Inbox Style notification.
 *
 * <Vimeo id="android-style-inbox" caption="Android Inbox Style" />
 *
 * View the [Inbox](/react-native/android/styles#inbox) documentation to learn more.
 *
 * @platform android
 */
export interface AndroidInboxStyle {
  /**
   * Constant enum value used to identify the style type.
   */
  type: AndroidStyle.INBOX;

  /**
   * An array of messages to display, in order provided.
   *
   * The device will automatically handle displaying the lines visible depending on space in the notification
   * shade.
   */
  lines: string[];

  /**
   * If set, overrides the main notification `title` when the notification is expanded.
   */
  title?: string;

  /**
   * If set, overrides the main notification `summary` when the notification is expanded.
   */
  summary?: string;
}

/**
 * The interface used when displaying a Messaging Style notification.
 *
 * <Vimeo id="android-style-messaging" caption="Android Messaging Style" />
 *
 * View the [Messaging](/react-native/android/styles#messaging) documentation to learn more.
 *
 * @platform android
 */
export interface AndroidMessagingStyle {
  /**
   * Constant enum value used to identify the style type.
   */
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
   * If set, overrides the main notification `title` when the notification is expanded.
   */
  title?: string;

  /**
   * Sets whether this conversation notification represents a group (3 or more persons).
   */
  group?: boolean;
}

/**
 * The interface for messages when constructing a Messaging Style notification.
 *
 * <Vimeo id="android-style-messaging" caption="Android Messaging Style" />
 *
 * View the [`AndroidMessagingStyle`](/react-native/reference/androidmessagingstyle) reference
 * and [Messaging](/react-native/android/styles#messaging) documentation to learn more.
 *
 * @platform android
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
   * The sender of this message. See [`AndroidPerson`](/react-native/reference/androidperson) reference
   * for more information on the properties available.
   *
   * This property should only be provided if the message is from an external person, and not the person receiving the message.
   */
  person?: AndroidPerson;
}

/**
 * The interface used to describe a person shown in notifications.
 *
 * Currently used with [`AndroidMessagingStyle`](/react-native/reference/androidmessagingstyle) notifications.
 *
 * @platform android
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
   * URI contact of the person.
   *
   * The URI can be any of the following:
   *
   *  - The representation of a contact URI, e.g. `android.provider.ContactsContract.Contacts#CONTENT_LOOKUP_URI`
   *  - A `mailto:` string
   *  - A `tel:` string
   */
  uri?: string;
}

/**
 * Interface for defining the progress of an Android Notification.
 *
 * <Vimeo id="android-progress-summary" caption="Android Progress (w/ Big Picture Style)" />
 *
 * View the [Progress Indicators](/react-native/android/progress-indicators) documentation to learn more.
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
   * The current progress value.
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
 * has final control over the setting. Once created, only channel metadata can be updated (e.g. name).
 *
 * View the [Channels & Groups](/react-native/android/channels) documentation to learn more.
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
   * Sets whether badges are enabled for the channel.
   *
   * View the [Badges](/react-native/android/appearance#badges) documentation to learn more.
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
   * Groups can be created via via [`createChannelGroup`](/react-native/reference/createchannelgroup).
   *
   * This setting cannot be overridden once the channel is created.
   */
  groupId?: string;

  /**
   * Sets the level of interruption of this notification channel.
   *
   * Defaults to `AndroidImportance.DEFAULT`.
   *
   * This setting can only be set to a lower importance level once set.
   */
  importance?: AndroidImportance;

  /**
   * If lights are enabled (via `lights`), sets/overrides the light color for notifications
   * posted to this channel.
   *
   * This setting cannot be overridden once the channel is created.
   */
  lightColor?: AndroidColor | string;

  /**
   * Sets whether notifications posted to this channel appear on the lockscreen or not,
   * and if so, whether they appear in a redacted form.
   *
   * Defaults to `AndroidVisibility.PRIVATE`.
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
   * The default value is to play no sound. To play the default system sound use 'default'.
   *
   * This setting cannot be overridden once the channel is created.
   */
  sound?: string;

  /**
   * The URI of the notification sound associated with the channel, if any.
   *
   * This is a read-only value, and is under user control after the channel is created
   */
  soundURI?: string;
}

/**
 * An interface which describes a channel which has been fetched from the device.
 *
 * Contains additional information which is only available when fetching the channel from the device.
 *
 * @platform android
 */
export interface NativeAndroidChannel extends AndroidChannel {
  /*
   * Returns whether or not notifications posted to this Channel group are
   * blocked.
   *
   * On API levels < 28, returns `false`.
   *
   * View the [Listening to channel events](/react-native/android/channels#listening-to-channel-events)
   * documentation to learn more about subscribing to when a channel is blocked by the user.
   *
   * @platform android API Level >= 28
   */
  blocked: boolean;
}

/**
 * An interface for describing an Android Channel Group.
 *
 * Channel groups have no impact on the notification, they are used to help group channels in the applications
 * settings UI.
 *
 * View the [Channels & Groups](/react-native/android/channels) documentation to learn more.
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
 * An interface which describes a channel group which has been fetched from the device.
 *
 * Contains additional information which is only available when fetching the channel group from the device.
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
   * View the [Listening to channel events](/react-native/android/channels#listening-to-channel-events)
   * documentation to learn more about subscribing to when a channel is blocked by the user.
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
 * Enum used to define how a notification badge is displayed in badge mode.
 *
 * View the [Badges](/react-native/android/appearance#badges) documentation for more information.
 *
 * @platform android
 */
export enum AndroidBadgeIconType {
  /**
   * No badge is displayed, will always show as a number.
   */
  NONE = 0,

  /**
   * Shows the badge as the notifications `smallIcon`.
   */
  SMALL = 1,

  /**
   * Shows the badge as the notifications `largeIcon` (if available).
   *
   * This is the default value used by a notification if not provided.
   */
  LARGE = 2,
}

/**
 * Enum used to describe the category of a notification.
 *
 * Setting a category on a notification helps the device to understand what the notification is for,
 * or what impact it will have on the user. The category can be used for ranking and filtering
 * the notification, however has no visual impact on the notification.
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

  /**
   * Avoid using - generally used by the system.
   */
  SYSTEM = 'sys',
  TRANSPORT = 'transport',
}

/**
 * A set or predefined colors which can be used with Android Notifications.
 *
 * View the [Color](/react-native/android/appearance#color) documentation to learn more.
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
   * All options will be used, where possible.
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
 * Enum used to set any additional flags supported on Android.
 * See Android's [setFlag()](https://developer.android.com/reference/android/app/Notification.Builder#setFlag(int,%20boolean)) documentation.
 */
export enum AndroidFlags {
  /**
   * The audio will be repeated until the notification is cancelled or the notification window is opened.
   * This will be set for you by setting `loopSound`.
   */
  FLAG_INSISTENT = 4,

  /**
   * Prevents the notification from being canceled when the user clicks the Clear all button.
   * This will be set for you by setting `ongoing`.
   */
  FLAG_NO_CLEAR = 32,
}

/**
 * Enum used to describe how a notification alerts the user when it apart of a group.
 *
 * View the [Grouping & Sorting](/react-native/android/grouping-and-sorting#group-behaviour) documentation to
 * learn more.
 *
 * @platform android
 */
export enum AndroidGroupAlertBehavior {
  /**
   * All notifications will alert.
   */
  ALL = 0,

  /**
   * Only the summary notification will alert the user when displayed. The children of the group will not alert.
   */
  SUMMARY = 1,

  /**
   * Children of a group will alert the user. The summary notification will not alert when displayed.
   */
  CHILDREN = 2,
}

/**
 * Available Android Notification Styles.
 *
 * View the [Styles](/react-native/android/styles) documentation to learn more with example usage.
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
 * View the [Visibility](/react-native/android/appearance#visibility) documentation to learn more.
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

/**
 * The interface describing the importance levels of an incoming notification.
 *
 * The importance level can be set directly onto a notification channel for supported devices (API Level >= 26)
 * or directly onto the notification for devices which do not support channels.
 *
 * The importance is used to both change the visual prompt of a received notification
 * and also how it visually appears on the device.
 *
 * View the [Android Appearance](/react-native/android/appearance#importance) documentation to learn more.
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
   * The notifications will appear on-top of applications, allowing direct interaction without pulling
   * down the notification shade. This level should only be used for urgent notifications, such as
   * incoming phone calls, messages etc, which require immediate attention.
   */
  HIGH = 4,

  /**
   * A low importance level applied to a channel/notification.
   *
   * On Android, the application small icon will show in the device statusbar, however the notification will not alert
   * the user (no sound or vibration). The notification will show in it's expanded state when the
   * notification shade is pulled down.
   *
   * On iOS, the notification will not display to the user or alert them. It will still be visible on the devices
   * notification center.
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
   */
  MIN = 1,

  /**
   * The notification will not be shown. This has the same effect as the user disabling notifications
   * in the application settings.
   */
  NONE = 0,
}

/**
 * An enum representing the various flags that can be passed along to `launchActivityFlags` on `NotificationPressAction`.
 *
 * These flags are added to the Android [Intent](https://developer.android.com/reference/android/content/Intent.html) that launches your activity.
 *
 * These are only required if you need to customise the behaviour of your activities, in most cases you might not need these.
 *
 * @platform android
 */
export enum AndroidLaunchActivityFlag {
  /**
   * See [FLAG_ACTIVITY_NO_HISTORY](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_NO_HISTORY) on the official Android documentation for more information.
   */
  NO_HISTORY = 0,

  /**
   * See [FLAG_ACTIVITY_SINGLE_TOP](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_SINGLE_TOP) on the official Android documentation for more information.
   */
  SINGLE_TOP = 1,

  /**
   * See [FLAG_ACTIVITY_NEW_TASK](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_NEW_TASK) on the official Android documentation for more information.
   */
  NEW_TASK = 2,

  /**
   * See [FLAG_ACTIVITY_MULTIPLE_TASK](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_MULTIPLE_TASK) on the official Android documentation for more information.
   */
  MULTIPLE_TASK = 3,

  /**
   * See [FLAG_ACTIVITY_CLEAR_TOP](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_CLEAR_TOP) on the official Android documentation for more information.
   */
  CLEAR_TOP = 4,

  /**
   * See [FLAG_ACTIVITY_FORWARD_RESULT](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_FORWARD_RESULT) on the official Android documentation for more information.
   */
  FORWARD_RESULT = 5,

  /**
   * See [FLAG_ACTIVITY_PREVIOUS_IS_TOP](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_PREVIOUS_IS_TOP) on the official Android documentation for more information.
   */
  PREVIOUS_IS_TOP = 6,

  /**
   * See [FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) on the official Android documentation for more information.
   */
  EXCLUDE_FROM_RECENTS = 7,

  /**
   * See [FLAG_ACTIVITY_BROUGHT_TO_FRONT](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_BROUGHT_TO_FRONT) on the official Android documentation for more information.
   */
  BROUGHT_TO_FRONT = 8,

  /**
   * See [FLAG_ACTIVITY_RESET_TASK_IF_NEEDED](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_RESET_TASK_IF_NEEDED) on the official Android documentation for more information.
   */
  RESET_TASK_IF_NEEDED = 9,

  /**
   * See [FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) on the official Android documentation for more information.
   */
  LAUNCHED_FROM_HISTORY = 10,

  /**
   * See [FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET) on the official Android documentation for more information.
   */
  CLEAR_WHEN_TASK_RESET = 11,

  /**
   * See [FLAG_ACTIVITY_NEW_DOCUMENT](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_NEW_DOCUMENT) on the official Android documentation for more information.
   */
  NEW_DOCUMENT = 12,

  /**
   * See [FLAG_ACTIVITY_NO_USER_ACTION](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_NO_USER_ACTION) on the official Android documentation for more information.
   */
  NO_USER_ACTION = 13,

  /**
   * See [FLAG_ACTIVITY_REORDER_TO_FRONT](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_REORDER_TO_FRONT) on the official Android documentation for more information.
   */
  REORDER_TO_FRONT = 14,

  /**
   * See [FLAG_ACTIVITY_NO_ANIMATION](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_NO_ANIMATION) on the official Android documentation for more information.
   */
  NO_ANIMATION = 15,

  /**
   * See [FLAG_ACTIVITY_CLEAR_TASK](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_CLEAR_TASK) on the official Android documentation for more information.
   */
  CLEAR_TASK = 16,

  /**
   * See [FLAG_ACTIVITY_TASK_ON_HOME](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_TASK_ON_HOME) on the official Android documentation for more information.
   */
  TASK_ON_HOME = 17,

  /**
   * See [FLAG_ACTIVITY_RETAIN_IN_RECENTS](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_RETAIN_IN_RECENTS) on the official Android documentation for more information.
   */
  RETAIN_IN_RECENTS = 18,

  /**
   * See [FLAG_ACTIVITY_LAUNCH_ADJACENT](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_LAUNCH_ADJACENT) on the official Android documentation for more information.
   */
  LAUNCH_ADJACENT = 19,

  /**
   * See [FLAG_ACTIVITY_MATCH_EXTERNAL](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_MATCH_EXTERNAL) on the official Android documentation for more information.
   */
  MATCH_EXTERNAL = 20,
}

/**
 * Enum used to set the foreground service types identifying the work done by the service.
 * See Android's [foreground service types](https://developer.android.com/develop/background-work/services/fg-service-types) documentation.
 *
 * @platform android
 */
export enum AndroidForegroundServiceType {
  FOREGROUND_SERVICE_TYPE_CAMERA = 64,
  FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE = 16,
  FOREGROUND_SERVICE_TYPE_DATA_SYNC = 1,
  FOREGROUND_SERVICE_TYPE_HEALTH = 256,
  FOREGROUND_SERVICE_TYPE_LOCATION = 8,
  FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK = 2,
  FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION = 32,
  FOREGROUND_SERVICE_TYPE_MEDIA_PROCESSING = 8192,
  FOREGROUND_SERVICE_TYPE_MICROPHONE = 128,
  FOREGROUND_SERVICE_TYPE_PHONE_CALL = 4,
  FOREGROUND_SERVICE_TYPE_REMOTE_MESSAGING = 512,
  FOREGROUND_SERVICE_TYPE_SHORT_SERVICE = 2048,
  FOREGROUND_SERVICE_TYPE_SPECIAL_USE = 1073741824,
  FOREGROUND_SERVICE_TYPE_SYSTEM_EXEMPTED = 1024,
  FOREGROUND_SERVICE_TYPE_MANIFEST = -1,
}
