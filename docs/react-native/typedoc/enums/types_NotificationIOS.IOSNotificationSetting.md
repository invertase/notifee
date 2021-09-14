[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationIOS](../modules/types_NotificationIOS.md) / IOSNotificationSetting

# Enumeration: IOSNotificationSetting

[types/NotificationIOS](../modules/types_NotificationIOS.md).IOSNotificationSetting

An enum representing a notification setting for this app on the device.

Value is greater than 0 if setting enabled, compare against an exact value (e.g. NOT_SUPPORTED) for more
granular control.

**`platform`** ios

## Table of contents

### Enumeration members

- [DISABLED](types_NotificationIOS.IOSNotificationSetting.md#disabled)
- [ENABLED](types_NotificationIOS.IOSNotificationSetting.md#enabled)
- [NOT\_SUPPORTED](types_NotificationIOS.IOSNotificationSetting.md#not_supported)

## Enumeration members

### DISABLED

• **DISABLED** = `0`

This setting is currently disabled by the user.

#### Defined in

[src/types/NotificationIOS.ts:305](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L305)

___

### ENABLED

• **ENABLED** = `1`

This setting is currently enabled.

#### Defined in

[src/types/NotificationIOS.ts:310](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L310)

___

### NOT\_SUPPORTED

• **NOT\_SUPPORTED** = `-1`

This setting is not supported on this device. Usually this means that the iOS version required
for this setting has not been met.

#### Defined in

[src/types/NotificationIOS.ts:300](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L300)
