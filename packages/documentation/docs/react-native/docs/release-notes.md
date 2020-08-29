---
title: Releases
description: Notifee API releases
next: /react-native/docs/usage
previous: /react-native/docs/license-keys
---

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
