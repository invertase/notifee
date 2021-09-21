[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationIOS](../modules/types_NotificationIOS.md) / IOSAuthorizationStatus

# Enumeration: IOSAuthorizationStatus

[types/NotificationIOS](../modules/types_NotificationIOS.md).IOSAuthorizationStatus

An enum representing the notification authorization status for this app on the device.

Value is greater than 0 if authorized, compare against an exact status (e.g. PROVISIONAL) for a more
granular status.

**`platform`** ios

## Table of contents

### Enumeration members

- [AUTHORIZED](types_NotificationIOS.IOSAuthorizationStatus.md#authorized)
- [DENIED](types_NotificationIOS.IOSAuthorizationStatus.md#denied)
- [NOT\_DETERMINED](types_NotificationIOS.IOSAuthorizationStatus.md#not_determined)
- [PROVISIONAL](types_NotificationIOS.IOSAuthorizationStatus.md#provisional)

## Enumeration members

### AUTHORIZED

• **AUTHORIZED** = `1`

The app is authorized to create notifications.

#### Defined in

[src/types/NotificationIOS.ts:247](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L247)

___

### DENIED

• **DENIED** = `0`

The app is not authorized to create notifications.

#### Defined in

[src/types/NotificationIOS.ts:242](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L242)

___

### NOT\_DETERMINED

• **NOT\_DETERMINED** = `-1`

The app user has not yet chosen whether to allow the application to create notifications. Usually
this status is returned prior to the first call of `requestPermission`.

#### Defined in

[src/types/NotificationIOS.ts:237](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L237)

___

### PROVISIONAL

• **PROVISIONAL** = `2`

The app is currently authorized to post non-interrupting user notifications

**`platform`** ios iOS >= 12

#### Defined in

[src/types/NotificationIOS.ts:253](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L253)
