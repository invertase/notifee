[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidImportance

# Enumeration: AndroidImportance

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidImportance

The interface describing the importance levels of an incoming notification.

The importance level can be set directly onto a notification channel for supported devices (API Level >= 26)
or directly onto the notification for devices which do not support channels.

The importance is used to both change the visual prompt of a received notification
and also how it visually appears on the device.

View the [Android Appearance](/react-native/docs/android/appearance#importance) documentation to learn more.

**`platform`** android

## Table of contents

### Enumeration members

- [DEFAULT](types_NotificationAndroid.AndroidImportance.md#default)
- [HIGH](types_NotificationAndroid.AndroidImportance.md#high)
- [LOW](types_NotificationAndroid.AndroidImportance.md#low)
- [MIN](types_NotificationAndroid.AndroidImportance.md#min)
- [NONE](types_NotificationAndroid.AndroidImportance.md#none)

## Enumeration members

### DEFAULT

• **DEFAULT** = `3`

The default importance applied to a channel/notification.

The application small icon will show in the device statusbar. When the user pulls down the
notification shade, the notification will show in it's expanded state (if applicable).

#### Defined in

[src/types/NotificationAndroid.ts:1214](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1214)

___

### HIGH

• **HIGH** = `4`

The highest importance level applied to a channel/notification.

The notifications will appear on-top of applications, allowing direct interaction without pulling
down the notification shade. This level should only be used for urgent notifications, such as
incoming phone calls, messages etc, which require immediate attention.

#### Defined in

[src/types/NotificationAndroid.ts:1223](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1223)

___

### LOW

• **LOW** = `2`

A low importance level applied to a channel/notification.

On Android, the application small icon will show in the device statusbar, however the notification will not alert
the user (no sound or vibration). The notification will show in it's expanded state when the
notification shade is pulled down.

On iOS, the notification will not display to the user or alert them. It will still be visible on the devices
notification center.

#### Defined in

[src/types/NotificationAndroid.ts:1235](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1235)

___

### MIN

• **MIN** = `1`

The minimum importance level applied to a channel/notification.

The application small icon will not show up in the statusbar, or alert the user. The notification
will be in a collapsed state in the notification shade and placed at the bottom of the list.

This level should be used when the notification requires no immediate attention. An example of this
importance level is the Google app providing weather updates and only being visible when the
user pulls the notification shade down,

#### Defined in

[src/types/NotificationAndroid.ts:1247](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1247)

___

### NONE

• **NONE** = `0`

The notification will not be shown. This has the same effect as the user disabling notifications
in the application settings.

#### Defined in

[src/types/NotificationAndroid.ts:1253](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1253)
