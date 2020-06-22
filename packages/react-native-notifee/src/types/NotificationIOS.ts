/* eslint-disable @typescript-eslint/interface-name-prefix */

/*
 * Copyright (c) 2016-present Invertase Limited.
 */

export interface NotificationIOS {
  /**
   * Optional array of [IOSNotificationAttachment](/react-native/reference/iosnotificationattachment) interfaces.
   *
   * Attachments allow audio, image, or video content to be displayed with the notification, enriching the user's experience.
   *
   * View the [Attachments](/react-native/docs/ios/appearances#attachments) documentation for more information
   * and usage examples.s
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

  foregroundPresentationOptions?: IOSForegroundPresentationOptions;
}

export interface IOSForegroundPresentationOptions {
  /**
   * App in foreground dialog box which indicates when a decision has to be made
   *
   * Defaults to false
   */
  alert?: boolean;
  /**
   * App in foreground notification sound
   *
   * Defaults to false
   */
  sound?: boolean;
  /**
   * App in foreground badge update
   *
   * Defaults to true
   */
  badge?: boolean;
}

/**
 * An interface representing all the available permissions that can be requested by your app via
 * the `requestPermission` API.
 */
export interface IOSNotificationPermissions {
  /**
   * Request permission to display alerts.
   *
   * Defaults to true.
   */
  alert?: boolean;

  // TODO add support, look into whether using UNAuthorizationOptionCriticalAlert without entitlement
  //    will cause an app review failure.
  criticalAlert?: boolean; // false

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
 * An enum representing the notification authorization status for this app on the device.
 *
 * Value is truthy if authorized, compare against an exact status (e.g. PROVISIONAL) for a more
 * granular status.
 */
export enum IOSAuthorizationStatus {
  /**
   * The app user has not yet chosen whether to allow the application to create notifications. Usually
   * this status is returned prior to the first call of `requestPermission`.
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

/**
 * An enum representing the show previews notification setting for this app on the device.
 *
 * Value is truthy if previews are to be shown, compare against an exact value
 * (e.g. WHEN_AUTHENTICATED) for more granular control.
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
 * Value is truthy if setting enabled, compare against an exact value (e.g. NOT_SUPPORTED) for more
 * granular control.
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
 * TODO docs
 */
export interface IOSNotificationSettings {
  alert: IOSNotificationSetting;
  badge: IOSNotificationSetting;
  criticalAlert: IOSNotificationSetting;
  showPreviews: IOSShowPreviewsSetting;
  sound: IOSNotificationSetting;
  carPlay: IOSNotificationSetting;
  lockScreen: IOSNotificationSetting;
  announcement: IOSNotificationSetting;
  notificationCenter: IOSNotificationSetting;
  inAppNotificationSettings: IOSNotificationSetting;
  authorizationStatus: IOSAuthorizationStatus;
}

/**
 * TODO docs, used to provide context to Siri
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
 * TODO docs
 */
export interface IOSNotificationCategory {
  id: string;
  summaryFormat?: string;
  allowInCarPlay?: boolean;
  allowAnnouncement?: boolean;
  hiddenPreviewsShowTitle?: boolean;
  hiddenPreviewsShowSubtitle?: boolean;
  hiddenPreviewsBodyPlaceholder?: string;
  intentIdentifiers?: IOSIntentIdentifier[];
  actions?: IOSNotificationCategoryAction[];
}

/**
 * TODO docs
 */
export interface IOSNotificationCategoryAction {
  id: string;
  title: string;
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
 * TODO docs
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
 * View the [Attachments](/react-native/docs/ios/appearance#attachments) documentation to learn more.
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
   * An optional hint about an attachment’s file type.
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
 */
export interface IOSAttachmentThumbnailClippingRect {
  x: number;
  y: number;
  width: number;
  height: number;
}
