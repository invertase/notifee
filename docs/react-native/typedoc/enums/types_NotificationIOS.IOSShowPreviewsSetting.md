[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationIOS](../modules/types_NotificationIOS.md) / IOSShowPreviewsSetting

# Enumeration: IOSShowPreviewsSetting

[types/NotificationIOS](../modules/types_NotificationIOS.md).IOSShowPreviewsSetting

An enum representing the show previews notification setting for this app on the device.

Value is greater than 0 if previews are to be shown, compare against an exact value
(e.g. WHEN_AUTHENTICATED) for more granular control.

**`platform`** ios

## Table of contents

### Enumeration members

- [ALWAYS](types_NotificationIOS.IOSShowPreviewsSetting.md#always)
- [NEVER](types_NotificationIOS.IOSShowPreviewsSetting.md#never)
- [NOT\_SUPPORTED](types_NotificationIOS.IOSShowPreviewsSetting.md#not_supported)
- [WHEN\_AUTHENTICATED](types_NotificationIOS.IOSShowPreviewsSetting.md#when_authenticated)

## Enumeration members

### ALWAYS

• **ALWAYS** = `1`

Always show previews even if the device is currently locked.

#### Defined in

[src/types/NotificationIOS.ts:279](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L279)

___

### NEVER

• **NEVER** = `0`

Never show previews.

#### Defined in

[src/types/NotificationIOS.ts:274](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L274)

___

### NOT\_SUPPORTED

• **NOT\_SUPPORTED** = `-1`

This setting is not supported on this device. Usually this means that the iOS version required
for this setting (iOS 11+) has not been met.

#### Defined in

[src/types/NotificationIOS.ts:269](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L269)

___

### WHEN\_AUTHENTICATED

• **WHEN\_AUTHENTICATED** = `2`

Only show previews when the device is unlocked.

#### Defined in

[src/types/NotificationIOS.ts:284](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L284)
