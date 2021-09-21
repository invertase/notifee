[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidVisibility

# Enumeration: AndroidVisibility

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidVisibility

Interface used to define the visibility of an Android notification.

Use with the `visibility` property on the notification.

View the [Visibility](/react-native/docs/android/appearance#visibility) documentation to learn more.

Default value is `AndroidVisibility.PRIVATE`.

**`platform`** android

## Table of contents

### Enumeration members

- [PRIVATE](types_NotificationAndroid.AndroidVisibility.md#private)
- [PUBLIC](types_NotificationAndroid.AndroidVisibility.md#public)
- [SECRET](types_NotificationAndroid.AndroidVisibility.md#secret)

## Enumeration members

### PRIVATE

• **PRIVATE** = `0`

Show the notification on all lockscreens, but conceal sensitive or private information on secure lockscreens.

#### Defined in

[src/types/NotificationAndroid.ts:1179](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1179)

___

### PUBLIC

• **PUBLIC** = `1`

Show this notification in its entirety on all lockscreens.

#### Defined in

[src/types/NotificationAndroid.ts:1184](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1184)

___

### SECRET

• **SECRET** = `-1`

Do not reveal any part of this notification on a secure lockscreen.

Useful for notifications showing sensitive information such as banking apps.

#### Defined in

[src/types/NotificationAndroid.ts:1191](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1191)
