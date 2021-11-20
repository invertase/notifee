---
title: Releases
description: Notifee API releases
next: /react-native/docs/usage
previous: /react-native/docs/installation
---

## 4.0.0
- **[Android]: BREAKING CHANGE** : the minimum compileSdkVersion required is 31, to fix an issue with Android 12 where the app will crash due to a missing Intent immutability flag (Fixes [#238](https://github.com/invertase/notifee/issues/238)). **Please note, JDK11 is strongly recommended when using compile or target sdk 31**

## 3.0.4
- **[Android]**:  Fixes an issue with buildtools to support gradle plugin 4.2+ (Fixes [#211](https://github.com/invertase/notifee/issues/211))
- **[iOS]**: Fixes an issue that was introduced in the previous patch when calling `setBadgeCount` with 0 (Fixes [#212](https://github.com/invertase/notifee/issues/212))

## 3.0.3
- **[iOS]**:  Fix iOS API availability guards, and all compile warnings (Fixes [#204](https://github.com/invertase/notifee/issues/204)) 

## 3.0.2
- **[Android]**: Include support for expo managed projects [[Learn More]](/react-native/docs/installation#miscellaneous)]
- **[Android]**: Adds support for Android 12

## 3.0.1
- **[Android]**: Fixes an issue where the wrong quick action was triggered when fired in quick succession (Fixes [#121](https://github.com/invertase/notifee/issues/121))
- **[Android]**: Fixes an issue where notifications created by FCM were not being removed from the notification tray when cancelled (Fixes [#120](https://github.com/invertase/notifee/issues/120))
- **[iOS]**: Compiled module no longer depends on a XCFramework, as part of the migration to fully open source the library. [[Learn More]](https://invertase.io/blog/open-sourcing-notifee)

## 3.0.0
- **[Android]: BREAKING CHANGE** - the minimum SDK version has been updated from 16 to 20, providing backwards notification compatibility up to Android 4.4W.
- **[Android]**: Fixes an issue with `getDisplayedNotifications` where the id returned is not the original notification id. (Fixes [#381](https://github.com/notifee/react-native-notifee/issues/381))
- **[Android]**: Fixes an issue with displaying a notification with `android.tag` (Fixes [#382](https://github.com/notifee/react-native-notifee/issues/382))
- **[Android]**: Introduces support to cancel a notification with a `tag`
- Removed licensing validation and related code - Notifee is now free and fully open source. [[Learn More]](https://invertase.io/blog/open-sourcing-notifee)

## 2.0.0
- **[Android]: BREAKING CHANGE** - you must add a new maven local repository to your `android/build.gradle` file. (Fixes [#151](https://github.com/notifee/react-native-notifee/issues/151)). See step #2 in [the installation guide](https://notifee.app/react-native/docs/installation)

## 1.11.0
- **[Android]**: Fixes an issue where `getInitialNotification` was sometimes throwing an error leading to a crash if activity was null. (Fixes [#374](https://github.com/notifee/react-native-notifee/issues/374))

## 1.11.0
- **[Android]**: Fixes an issue where the initial notification wasn't being populated for full-screen actions.
- **[iOS]**: Introduces a new method signature for `NotifeeExtensionHelper.populateNotificationContent` where the `request` is passed down as well as the `bestAttemptContent`. View the [Remote Notification Support](https://notifee.app/react-native/docs/ios/remote-notification-support) documentation for more information.
- **[iOS]**: Adds a note in the reference documentation that `getInitialNotification()` for iOS is deprecated in favour of `onForegroundEvent`.


## 1.10.1
- **[Android]**: Fixes an issue on Android to prevent `cancelDisplayedNotifications` cancelling trigger notifications. (Fixes [#349](https://github.com/notifee/react-native-notifee/issues/349))

## 1.10.0
- **[Android/iOS]**: Introduces the following APIs:
  - `getDisplayedNotifications`
  - `getTriggerNotifications`
  - `isChannelBlocked`
  - `isChannelCreated`
  - `cancelAllNotifications(ids)`
  - `cancelDisplayedNotifications(ids)`
  - `cancelTriggerNotifications(ids)`
- **BREAKING**: Fixes an issue when setting a custom sound on a notification channel (Fixes [#341](https://github.com/notifee/react-native-notifee/issues/341)). Sounds now must be specified without the file extension, previously it was optional. **If you were using custom sounds on Android>7 prior to this, you will need to create a *new* notification channel or the reference to your custom sound on the channel will likely not survive app updates. We apologize for the inconvenience**

## 1.9.2
- **[Android]**: Fixes an issue where an error is thrown when the intent was null in rare cases for foreground service events.

## 1.9.1
- **[Android]**: Fixes an issue where an error is thrown when using trigger notifications with AlarmManager on Android 8 & 7

## 1.9.0
- **[Android]**: Support Alarm Manager for trigger notifications.
- **[iOS]**: Xcode 12.4 and above is now supported

## 1.8.1
- **[Android/iOS]**: Fix npm bundle to exclude example app

## 1.8.0
- **[Android/iOS]**: Include support for jest tests by including a `jest-mock.js` file.
- **[Android]**: Add an extra safety check to prevent app from crashing if a `launchActivity` property is set to an invalid activity class.

## 1.7.0
- **[Android/iOS]**: Allows title, body, and subtitle to be set to undefined in addition to string values to prevent an empty space on Android.
- **[Android/iOS]**: Updated validation to only throw an error for platform-specific properties if the app is running the same platform (Feature enhancement [#297](https://github.com/notifee/react-native-notifee/issues/297)).
- **[iOS]**: Xcode 12.5 is required on iOS.

## 1.6.0
- **[Android]**: Fixed an issue with jwt gradle build dependency.

## 1.5.0
- **[Android]**: Implemented support for `notifee.hideNotificationDrawer` (Feature enhancement [#200](https://github.com/notifee/react-native-notifee/issues/200)).

## 1.4.0
- **[Android]**: Added support for `fullScreenAction` on `NotificationAndroid` (Feature enhancement [#45](https://github.com/notifee/react-native-notifee/issues/45)).

## 1.3.1
- **[Android]**: Fixed an issue where sometimes the app would throw an exception when the user changes the notification blocked state for either a channel or the entire application (Fixes [#237](https://github.com/notifee/react-native-notifee/issues/237)).

## 1.3.0
- **[Android]**: Fixed an issue where sometimes the foreground service failed to stop when calling `resolve` due to an issue with hot reloading. It can be stopped by `notifee.stopForegroundService()`. To learn more, view the [Foreground Service](/react-native/docs/android/foreground-service) documentation.

## 1.2.1
- **[Android]**: Fixed an issue with Notifee's build script where sometimes an error would occur that the target variant task already existed.

## 1.2.0
- **[iOS]**:  Includes a Notification Service Extension Helper to take advantage of Notifee with remote notifications. To learn more, view the [Remote Notification Support](/react-native/docs/ios/remote-notification-support) documentation.


## 1.1.2
- **[iOS]**:  Enforce v1.10+ of CocoaPods (Fixes [#230](https://github.com/notifee/react-native-notifee/issues/230)).

## 1.1.1
- **[Android]**: Includes a solution to fix an error due to a potential race condition that occurs when the user changes the notification blocked state for either a channel or the entire application (Fixes [#237](https://github.com/notifee/react-native-notifee/issues/237)).

## 1.1.0

- **[Android]**: Implemented support for `circularLargeIcon` on `NotificationAndroid` (Feature enhancement [#199](https://github.com/notifee/react-native-notifee/issues/199)).
- **[iOS]**: Prevent badge count from clearing notifications from the tray when it reaches 0 (Fixes [#214](https://github.com/notifee/react-native-notifee/issues/214)).

## 1.0.0

- **NOTE**: Version bump.

## 0.16.0

- **[Android]**: Fixed an issue with license validation on Android 6 (Fixes [#87](https://github.com/notifee/react-native-notifee/issues/87))
- **[iOS]**: Added support for Apple Silicon (arm64) and Mac Catalyst builds (Fixes [#162](https://github.com/notifee/react-native-notifee/issues/162), [#215](https://github.com/notifee/react-native-notifee/issues/215))

## 0.15.2

- **[Android]**: Added additional logs to help with debugging license validation issues.
- **[Android/iOS]**: Fixed an issue where sometimes the `NotifeeApiModule` would fail to resolve `this.native` due to the context changing.

## 0.15.1

- **[Android]**: Fixed an issue where sometimes the `largeIcon` wouldn't show when the app is closed.
- **[Android/iOS]**: Fixed an issue to show a more descriptive warning if the background event handler isn't set via `onBackgroundEvent`.

## 0.15.0

- **[Android]**: Implemented additional support to help with background restrictions on Android, includes two new methods `getPowerManagerInfo` and `openPowerManagerSettings`.
- **[Android]**: Fixed an issue with subtitle where the default value was causing two dots on some devices.
- **[Android/iOS]**: Added support to cancel either a displayed or a trigger notification.

## 0.14.0

- **[Android]**: Fixed an issue where `isBatteryOptimizationEnabled` was returning the result as an object instead of a boolean.
- **[Android]**: Fixed an issue where `openBatteryOptimizationSettings` was sometimes throwing an exception for Samsung and Oppo phones on Android versions 6.0.
- **[iOS]**: Fixed an issue when a notification is pressed while the app is in the background on iOS 14, which sometimes would cause the app to crash.

## 0.13.2

- **[Android]**: Fixed an issue where trigger notifications created with v0.12.x and below would cause the app to crash when upgrading to v0.13.x.

## 0.13.1

- **[iOS]**: Added support to handle remote urls for [Attachments](https://notifee.app/react-native/docs/ios/appearance#attachments).
- **[iOS]**: Added iOS support for `repeatFrequency` on `TimestampTrigger`. See [Triggers](https://notifee.app/react-native/docs/triggers).

## 0.13.0

- **[Android]**: Added support for `repeatFrequency` on `TimestampTrigger` to be able to create hourly, daily or weekly trigger notifications. See [Triggers](https://notifee.app/react-native/docs/triggers).
- **[Android]**: Implemented support to help with background restrictions on Android, includes two methods `isBatteryOptimizationEnabled` and `openBatteryOptimizationSettings`. See [Background Restrictions](https://notifee.app/react-native/docs/android/behaviour#background-restrictions).
- **[Android]**: Fixed an issue when creating a trigger notification, where sometimes the input data would reach the maximum number of bytes allowed.

## 0.12.3

- **[Android]**: Fixed an issue where `pressAction` with the `default` id failed to open the app when the notification was pressed.
- **[Android]**: Fixed an issue with gradle plugin 4.1 and `Build.VERSION_NAME` which prevented the app from building.

## 0.12.2

- **[iOS]**: Fixed an issue where the `DELIVERED` foreground event wasn't being sent for trigger notifications.
- **[iOS]**: Fixed an issue with the iOS module that sent events before the JS bundle was ready.

## 0.12.1

- **[iOS]**: Fixed an issue with the iOS module that prevented the library from compiling.

## 0.12.0

- **[Android]**: `lightColor` and `sound` are now returned when calling `getChannel` or `getChannels`.
- **BREAKING**: `TimeTrigger` has been removed, in favour of `TimestampTrigger` and `IntervalTrigger`.
- **[iOS]**: Trigger Notifications are now supported on `iOS`. See [Triggers](https://notifee.app/react-native/docs/triggers).

## 0.11.1

- **[Android]**: Fixed an issue with `cancelNotification` for trigger notifications.
- **[Android]**: Fixed an issue with remote verification on devices <= 20
- **[iOS]**: Call original delegate when intercepting notification response on iOS

## 0.11.0

- **[Android]**: Add support for Trigger Notifications on Android. See [Triggers](https://notifee.app/react-native/docs/triggers).
- **[Android]**: Fixed an issue with `getChannels` and `getChannelGroups` where the methods were throwing an error
- **[iOS]**: Fixed an issue with iOS 14 where sometimes the app would freeze briefly when receiving an push notification

## 0.10.0

- **[Android]**: Fixed an issue with sounds for Android versions < 8.0 (API level 26)
- **[Android]**: Fixed an issue with `notifee.config.json` where sometimes the script could not find the 'app' gradle project automatically.

## 0.9.0

- **[iOS]**: Add support for iOS `onBackgroundEvent`.
- **[iOS]**: Pool JS events until RN Bridge is ready.
- **[Android]**: Allow multiple `onBackgroundEvent` observers when inside a foreground service task.

## 0.8.0

- **[iOS]**: Add support for iOS notification attachments. See [iOS Attachments](https://notifee.app/react-native/docs/ios/appearance#attachments).

## 0.7.2

- **[iOS]**: Fixed an issue where notifications would sometimes not appear in the foreground.

## 0.7.1

- **[Android]**: Fixed an issue where the Headless task key was incorrect for the foreground service task.update

## 0.7.0

- **[Android]**: Implemented support for [`launchActivityFlags`](https://notifee.app/react-native/reference/notificationpressaction#launchactivityflags) - allowing you to customise the launch behaviour of your activities.
  - See [`AndroidLaunchActivityFlag`](https://notifee.app/react-native/reference/androidlaunchactivityflag) for supported flags.

## 0.6.1

- **[Android]**: Fixed an issue with Android proguard rules that may have prevented the library from being used when minified.

## 0.6.0

- **[Android]**: `notifee.config.json` now supports specifying options for build flavours & types.
  - See [this comment](https://github.com/notifee/react-native-notifee/pull/67#issuecomment-640136025) comment for an example.
- **[TypeScript]**: Reworked type definitions to fix minor definition issues.
- **BREAKING**: `Importance` has now been renamed to `AndroidImportance` and is no longer supported on `iOS` (replaced with `foregroundPresentationOptions`).
- **[iOS]**: Implemented support for `ios.foregroundPresentationOptions` to control foreground notification behaviour on iOS
  - See the [iOS appearance guide](https://notifee.app/react-native/docs/ios/appearance) for more information.
- **[Android]**: Fixed an issue where creating multiple `channel` or `channelGroups` would fail to create.
- **[Android]**: `requestPermission` now correctly resolves a dummy instance of `IOSNotificationSettings` (previously `null` on Android) for cross-platform compatibility.
- **[Android]**: `largeIcon` & `picture` (from big picture style) now supports React Native asset loading, e.g. `largeIcon: require('./image.png')`.
