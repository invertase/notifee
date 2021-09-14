[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidDefaults

# Enumeration: AndroidDefaults

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidDefaults

On devices which do not support notification channels (API Level < 26), the notification
by default will use all methods to alert the user (depending on the importance).

To override the default behaviour, provide an array of defaults to the notification.

On API Levels >= 26, this has no effect and notifications will use the channel behaviour.

**`platform`** android API Level < 26

## Table of contents

### Enumeration members

- [ALL](types_NotificationAndroid.AndroidDefaults.md#all)
- [LIGHTS](types_NotificationAndroid.AndroidDefaults.md#lights)
- [SOUND](types_NotificationAndroid.AndroidDefaults.md#sound)
- [VIBRATE](types_NotificationAndroid.AndroidDefaults.md#vibrate)

## Enumeration members

### ALL

• **ALL** = `-1`

All options will be used, where possible.

#### Defined in

[src/types/NotificationAndroid.ts:1107](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1107)

___

### LIGHTS

• **LIGHTS** = `4`

The notification will use lights to alert the user.

#### Defined in

[src/types/NotificationAndroid.ts:1112](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1112)

___

### SOUND

• **SOUND** = `1`

The notification will use sound to alert the user.

#### Defined in

[src/types/NotificationAndroid.ts:1117](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1117)

___

### VIBRATE

• **VIBRATE** = `2`

The notification will vibrate to alert the user.

#### Defined in

[src/types/NotificationAndroid.ts:1122](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1122)
