[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/Notification](../modules/types_Notification.md) / DisplayedNotification

# Interface: DisplayedNotification

[types/Notification](../modules/types_Notification.md).DisplayedNotification

An interface representing a notification that is currently displayed in the notification tray.

## Table of contents

### Properties

- [date](types_Notification.DisplayedNotification.md#date)
- [id](types_Notification.DisplayedNotification.md#id)
- [notification](types_Notification.DisplayedNotification.md#notification)
- [trigger](types_Notification.DisplayedNotification.md#trigger)

## Properties

### date

• `Optional` **date**: `string`

Date the notification was shown to the user

#### Defined in

[src/types/Notification.ts:116](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L116)

___

### id

• `Optional` **id**: `string`

ID of the notification

#### Defined in

[src/types/Notification.ts:111](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L111)

___

### notification

• **notification**: [`Notification`](types_Notification.Notification.md)

The payload that was used to create the notification (if available)

#### Defined in

[src/types/Notification.ts:121](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L121)

___

### trigger

• **trigger**: [`Trigger`](../modules/types_Trigger.md#trigger)

The trigger that was used to schedule the notification (if available)

**`platform`** iOS

#### Defined in

[src/types/Notification.ts:128](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L128)
