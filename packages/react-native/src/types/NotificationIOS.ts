/*
 * Copyright (c) 2016-present Invertase Limited.
 */

import { AuthorizationStatus } from './Notification';
/**
 * The interface for iOS specific options which are applied to a notification.
 *
 * To learn more about iOS notifications, view the [iOS](/react-native/iOS/introduction)
 * documentation for full examples and usage.
 *
 * @platform ios
 */
export interface NotificationIOS {
  /**
   * Optional array of [IOSNotificationAttachment](/react-native/reference/iosnotificationattachment) interfaces.
   *
   * Attachments allow audio, image, or video content to be displayed with the notification, enriching the user's experience.
   *
   * View the [Attachments](/react-native/ios/appearances#attachments) documentation for more information
   * and usage examples.
   */
  attachments?: IOSNotificationAttachment[];

  /**
   * The application badge count number. Set to null to indicate no change, or 0 to hide.
   */
  badgeCount?: number | null;

  /**
   * The id of a registered `IOSCategory` (via the `setNotificationCategories` API) that will be used to determine the
   * appropriate actions to display for the notification.
   */
  categoryId?: string;

  /**
   * The launch image that will be used when the app is opened from this notification.
   */
  launchImageName?: string;

  /**
   * The name of the sound file to be played. The sound must be in the Library/Sounds folder of the
   * app's data container or the Library/Sounds folder of an app group data container.
   *
   * If the file is not found in a container, the system will look in the app's bundle.
   *
   * Use 'default' to use the default system sound.
   */
  sound?: string;

  /**
   * Value that indicate the importance and delivery timing of a notification.
   *
   * @platform ios iOS >= 15
   */
  interruptionLevel?: IOSNotificationInterruptionLevel;

  /**
   * If the notification is a critical alert set this property to true; critical alerts will bypass
   * the mute switch and also bypass Do Not Disturb.
   *
   * @platform ios iOS >= 12
   */
  critical?: boolean;

  /**
   * The optional audio volume of the critical sound; a float value between 0.0 and 1.0.
   *
   * This property is not used unless the `critical: true` option is also set.
   *
   * @platform ios iOS >= 12
   */
  criticalVolume?: number;

  /**
   * A unique id for the thread or conversation related to this notification.
   * This will be used to visually group notifications together.
   */
  threadId?: string;

  /**
   * The argument that is inserted in the IOSCategory.summaryFormat for this notification.
   *
   * See `IOSCategory.summaryFormat`.
   *
   * @platform ios iOS >= 12
   */
  summaryArgument?: string;

  /**
   * A number that indicates how many items in the summary are being represented.
   *
   * For example if a messages app sends one notification for 3 new messages in a group chat,
   * the summaryArgument could be the name of the group chat and the summaryArgumentCount should be 3.
   *
   * If set, value cannot be 0 or less.
   *
   * See `IOSCategory.summaryFormat`.
   *
   * @platform ios iOS >= 12
   */
  summaryArgumentCount?: number;

  /**
   * The identifier for the window to be opened when the user taps a notification.
   *
   * This value determines the window brought forward when the user taps this notification on iPadOS.
   *
   * @platform ios iOS >= 13
   */
  targetContentId?: string;

  /**
   * Optional property to customise how notifications are presented when the app is in the foreground.
   *
   * By default, Notifee will show iOS notifications in heads-up mode if your app is currently in the foreground.
   */
  foregroundPresentationOptions?: IOSForegroundPresentationOptions;

  /**
   * Optional property for communication notifications
   *
   * @platform ios iOS >= 15
   */
  communicationInfo?: IOSCommunicationInfo;
}

/**
 * An interface to support communication notifications on iOS 15 and above
 *
 * @platform ios
 */
export interface IOSCommunicationInfo {
  conversationId: string;
  body?: string;
  groupName?: string;
  groupAvatar?: string;
  sender: IOSCommunicationInfoPerson;
}

export interface IOSCommunicationInfoPerson {
  id: string;
  displayName: string;
  avatar?: string;
}

/**
 * An interface to customise how notifications are shown when the app is in the foreground.
 *
 * By default, Notifee will show iOS notifications in heads-up mode if your app is currently in the foreground.
 *
 * View the [Foreground Notifications](/react-native/ios/appearance#foreground-notifications) to learn
 * more.
 *
 * @platform ios
 */
export interface IOSForegroundPresentationOptions {
  /**
   * App in foreground dialog box which indicates when a decision has to be made
   *
   * Defaults to true
   * @deprecated Use `banner` and `list` instead
   */
  alert?: boolean;

  /**
   * App in foreground notification sound
   *
   * Defaults to true
   */
  sound?: boolean;

  /**
   * App in foreground badge update
   *
   * Defaults to true
   */
  badge?: boolean;

  /**
   * Present the notification as a banner
   *
   * For iOS 13 and lower, will be equivalent to setting `alert` to true
   *
   * Defaults to true
   */
  banner?: boolean;

  /**
   * Show the notification in Notification Center
   *
   * For iOS 13 and lower, will be equivalent to setting `alert` to true
   *
   * Defaults to true
   */
  list?: boolean;
}

/**
 * An interface representing all the available permissions that can be requested by your app via
 * the [`requestPermission`](/react-native/reference/requestpermission) API.
 *
 * View the [Permissions](/react-native/ios/permissions) to learn
 * more.
 *
 * @platform ios
 */
export interface IOSNotificationPermissions {
  /**
   * Request permission to display alerts.
   *
   * Defaults to true.
   */
  alert?: boolean;

  /**
   * Request permission to display critical notifications.
   *
   * View the [Critical Notifications](/react-native/ios/behaviour#critical-notifications) documentation for more information
   * and usage examples.
   *
   * Defaults to false.
   */
  criticalAlert?: boolean;

  /**
   * Request permission to update the application badge.
   *
   * Defaults to true.
   */
  badge?: boolean;

  /**
   * Request permission to play sounds.
   *
   * Defaults to true.
   */
  sound?: boolean;

  /**
   * Request permission to display notifications in a CarPlay environment.
   *
   * Defaults to true.
   */
  carPlay?: boolean;

  /**
   * Request permission to provisionally create non-interrupting notifications.
   *
   * Defaults to false.
   *
   * @platform ios iOS >= 12
   */
  provisional?: boolean;

  /**
   * Request permission for Siri to automatically read out notification messages over AirPods.
   *
   * Defaults to false.
   *
   * @platform ios iOS >= 13
   */
  announcement?: boolean;

  // TODO later version
  // /**
  //  * Using this permission indicates to iOS that it should display a button for in-app notification
  //  * settings. Pressing this button when your application is open will trigger a Notifee
  //  * 'ACTION_PRESS' event with a `pressAction.id` of 'notification-settings'
  //  * (or via getInitialNotification 'pressAction.id' when app launched)
  //  *
  //  * Defaults to false.
  //  *
  //  * @platform ios iOS >= 12
  //  */
  // inAppNotificationSettings?: boolean;
}

/**
 * An enum representing the show previews notification setting for this app on the device.
 *
 * Value is greater than 0 if previews are to be shown, compare against an exact value
 * (e.g. WHEN_AUTHENTICATED) for more granular control.
 *
 * @platform ios
 */
export enum IOSShowPreviewsSetting {
  /**
   * This setting is not supported on this device. Usually this means that the iOS version required
   * for this setting (iOS 11+) has not been met.
   */
  NOT_SUPPORTED = -1,

  /**
   * Never show previews.
   */
  NEVER = 0,

  /**
   * Always show previews even if the device is currently locked.
   */
  ALWAYS = 1,

  /**
   * Only show previews when the device is unlocked.
   */
  WHEN_AUTHENTICATED = 2,
}

/**
 * An enum representing a notification setting for this app on the device.
 *
 * Value is greater than 0 if setting enabled, compare against an exact value (e.g. NOT_SUPPORTED) for more
 * granular control.
 *
 * @platform ios
 */
export enum IOSNotificationSetting {
  /**
   * This setting is not supported on this device. Usually this means that the iOS version required
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

/**
 * An interface representing the current authorization status and notification-related settings for your app.
 *
 * This interface is returned from [`requestPermission`](/react-native/reference/requestpermission)
 * and [`getNotificationSettings`](/react-native/reference/getnotificationsettings).
 *
 * View the [Observing Settings](/react-native/ios/permissions#observing-settings) documentation to learn more.
 *
 * @platform ios
 */
export interface IOSNotificationSettings {
  /**
   * Enum describing if notifications will alert the user.
   */
  alert: IOSNotificationSetting;

  /**
   * Enum describing if notifications can update the application badge.
   */
  badge: IOSNotificationSetting;

  /**
   * Enum describing if critical notifications are allowed.
   */
  criticalAlert: IOSNotificationSetting;

  /**
   * Enum describing if notification previews will be shown.
   */
  showPreviews: IOSShowPreviewsSetting;

  /**
   * Enum describing if notifications can trigger a sound.
   */
  sound: IOSNotificationSetting;

  /**
   * Enum describing if notifications can be displayed in a CarPlay environment.
   */
  carPlay: IOSNotificationSetting;

  /**
   * Enum describing if notifications will be displayed on the lock screen.
   */
  lockScreen: IOSNotificationSetting;

  /**
   * Enum describing if notifications can be announced to the user
   * via 3rd party services such as Siri.
   *
   * For example, if the notification can be automatically read by Siri
   * while the user is wearing AirPods.
   */
  announcement: IOSNotificationSetting;

  /**
   * Enum describing if notifications will be displayed in the notification center.
   */
  notificationCenter: IOSNotificationSetting;

  inAppNotificationSettings: IOSNotificationSetting;

  /**
   * Overall notification authorization status for the application.
   */
  authorizationStatus: AuthorizationStatus;
}

/**
 * TODO docs, used to provide context to Siri
 *
 * @platform ios
 */
export enum IOSIntentIdentifier {
  START_AUDIO_CALL = 0,

  START_VIDEO_CALL = 1,

  SEARCH_CALL_HISTORY = 2,

  SET_AUDIO_SOURCE_IN_CAR = 3,

  SET_CLIMATE_SETTINGS_IN_CAR = 4,

  SET_DEFROSTER_SETTINGS_IN_CAR = 5,

  SET_SEAT_SETTINGS_IN_CAR = 6,

  SET_PROFILE_IN_CAR = 7,

  SAVE_PROFILE_IN_CAR = 8,

  START_WORKOUT = 9,

  PAUSE_WORKOUT = 10,

  END_WORKOUT = 11,

  CANCEL_WORKOUT = 12,

  RESUME_WORKOUT = 13,

  SET_RADIO_STATION = 14,

  SEND_MESSAGE = 15,

  SEARCH_FOR_MESSAGES = 16,

  SET_MESSAGE_ATTRIBUTE = 17,

  SEND_PAYMENT = 18,

  REQUEST_PAYMENT = 19,

  SEARCH_FOR_PHOTOS = 20,

  START_PHOTO_PLAYBACK = 21,

  LIST_RIDE_OPTIONS = 22,

  REQUEST_RIDE = 23,

  GET_RIDE_STATUS = 24,
}

/**
 * A interface representing a notification category created via [`setNotificationCategories`](/react-native/reference/setnotificationcategories).
 *
 * At minimum, a category must be created with a unique identifier, all other properties are optional.
 *
 * View the [Categories](/react-native/ios/categories) documentation to learn more.
 *
 * @platform ios
 */
export interface IOSNotificationCategory {
  /**
   * The unique ID for the category.
   */
  id: string;

  /**
   * Specify a custom format for the summary text, which is visible when notifications are grouped together.
   *
   * View the [Summary Text](/react-native/ios/categories#category-summary-text) documentation to learn more.
   */
  summaryFormat?: string;

  /**
   * Allow notifications in this category to be displayed in a CarPlay environment.
   *
   * Defaults to `false`.
   */
  allowInCarPlay?: boolean;

  /*
   * Allow notifications in this category to be announced to the user
   * via 3rd party services such as Siri.
   *
   * For example, if the notification can be automatically read by Siri
   * while the user is wearing AirPods.
   *
   * Defaults to `false`.
   */
  allowAnnouncement?: boolean;

  /*
   * Show the notification's title, even if the user has disabled notification previews for the app
   *
   * Defaults to `false`.
   */
  hiddenPreviewsShowTitle?: boolean;

  /*
   * Show the notification's subtitle, even if the user has disabled notification previews for the app
   *
   * Defaults to `false`.
   */
  hiddenPreviewsShowSubtitle?: boolean;

  /*
   * Show the notification's body, even if the user has disabled notification previews for the app
   *
   * Defaults to `false`.
   */
  hiddenPreviewsBodyPlaceholder?: string;

  intentIdentifiers?: IOSIntentIdentifier[];

  /*
   * An array of [IOSNotificationCategoryAction](/react-native/reference/iosnotificationcategoryaction) interfaces.
   *
   * Adds quick actions to a notification. Quick Actions enable users to interact with your application
   * directly from the notification body, providing an overall greater user experience.
   *
   * View the [Quick Actions](react-native/docs/ios/interaction#quick-actions) documentation for more information.
   */
  actions?: IOSNotificationCategoryAction[];
}

/**
 * The interface used to describe a notification quick action for iOS.
 *
 * Quick actions allow users to interact with notifications, allowing you to handle events
 * within your application. When an action completes (e.g. pressing an action, or filling out an input
 * box) an event is sent.
 *
 * View the [Quick Actions](/react-native/ios/interaction#quick-actions) documentation to learn more.
 *
 * @platform ios
 */
export interface IOSNotificationCategoryAction {
  id: string;

  /**
   * The title of the action, e.g. "Reply", "Mark as read" etc.
   */
  title: string;

  /**
   * If provided, the action accepts custom user input.
   *
   * If `true`, the user will be able to provide free text input when the action is pressed.
   *
   * The placeholder and button text can be customized by providing an object
   * of type [`IOSInput`](/react-native/reference/iosinput).
   *
   * View the [Action Input](/react-native/ios/interaction#action-input) documentation to
   * learn more.
   */
  input?: true | IOSInput;

  /**
   * Makes the action red, indicating that the action is destructive.
   */
  destructive?: boolean; // false

  /**
   * Whether this action should cause the application to launch in the foreground.
   */
  foreground?: boolean; // false

  /**
   * Whether this action should require unlocking before being performed.
   */
  authenticationRequired?: boolean; // false
}

/**
 * The interface used to enable advanced user input on a notification.
 *
 * View the [Action input](/react-native/ios/interaction#action-input) documentation to learn more.
 *
 * @platform ios
 */
export interface IOSInput {
  /**
   * Overrides the default button text "Send", next to the input box.
   */
  buttonText?: string;

  /**
   * The placeholder text displayed in the text input field for this action.
   */
  placeholderText?: string;
}

/**
 * An interface for describing an iOS Notification Attachment.
 *
 * View the [Attachments](/react-native/ios/appearance#attachments) documentation to learn more.
 *
 * @platform ios
 */
export interface IOSNotificationAttachment {
  /**
   * A optional unique identifier of the attachment.
   * If no `id` is provided, a unique id is created for you.
   */
  id?: string;

  /**
   * A URL to the media file to display.
   *
   * The value can be any of the following:
   *
   *  - An absolute path to a file on the device
   *  - iOS resource
   *
   * For a list of supported file types, see [Supported File Types](https://developer.apple.com/documentation/usernotifications/unnotificationattachment#1682051) on the official Apple documentation for more information.
   */
  url: string;

  /**
   * An optional hint about an attachment’s file type, as as Uniform Type Identifier (UTI).
   *
   * A list of UTI values can be found [here](https://developer.apple.com/library/archive/documentation/Miscellaneous/Reference/UTIRef/Articles/System-DeclaredUniformTypeIdentifiers.html) e.g. for JPEG you'd use `public.jpeg` as the `typeHint` value.
   *
   * If you do not include this key, the attachment’s filename extension is used to determine its type.
   */
  typeHint?: string;

  /**
   * When set to `true` the thumbnail will be hidden.
   * Defaults to `false`.
   */
  thumbnailHidden?: boolean;

  /**
   * An optional clipping rectangle for a thumbnail image.
   */
  thumbnailClippingRect?: IOSAttachmentThumbnailClippingRect;

  /**
   * The frame number of an animation to use as the thumbnail.
   *
   * For a video, it is the time (in seconds) into the video from which to
   * grab the thumbnail image.
   *
   * For a GIF, it is the frame number of the animation to use
   * as a thumbnail image.
   */
  thumbnailTime?: number;
}

/**
 * The interface used to specify the portion of your image that you want to be displayed as the thumbnail
 *
 * Values are in the range 0.0 to 1.0.
 *
 * For example, specifying an origin (x,y) of (0.25, 0.25) and a size (width, height) of (0.5, 0.5)
 * defines a clipping rectangle that shows only the center portion of the image.
 *
 * @platform ios
 */
export interface IOSAttachmentThumbnailClippingRect {
  x: number;
  y: number;
  width: number;
  height: number;
}

/**
 * Constants that indicate the importance and delivery timing of a notification.
 * https://developer.apple.com/documentation/usernotifications/unnotificationinterruptionlevel
 *
 * @platform ios
 */
export type IOSNotificationInterruptionLevel = 'active' | 'critical' | 'passive' | 'timeSensitive';
