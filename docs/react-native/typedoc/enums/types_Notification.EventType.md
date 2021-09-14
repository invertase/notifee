[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/Notification](../modules/types_Notification.md) / EventType

# Enumeration: EventType

[types/Notification](../modules/types_Notification.md).EventType

An enum representing an event type, defined on [`Event`](/react-native/reference/event).

View the [Events](/react-native/docs/events) documentation to learn more about foreground and
background events.

## Table of contents

### Enumeration members

- [ACTION\_PRESS](types_Notification.EventType.md#action_press)
- [APP\_BLOCKED](types_Notification.EventType.md#app_blocked)
- [CHANNEL\_BLOCKED](types_Notification.EventType.md#channel_blocked)
- [CHANNEL\_GROUP\_BLOCKED](types_Notification.EventType.md#channel_group_blocked)
- [DELIVERED](types_Notification.EventType.md#delivered)
- [DISMISSED](types_Notification.EventType.md#dismissed)
- [PRESS](types_Notification.EventType.md#press)
- [TRIGGER\_NOTIFICATION\_CREATED](types_Notification.EventType.md#trigger_notification_created)
- [UNKNOWN](types_Notification.EventType.md#unknown)

## Enumeration members

### ACTION\_PRESS

• **ACTION\_PRESS** = `2`

Event type is sent when a user presses a notification action.

#### Defined in

[src/types/Notification.ts:321](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L321)

___

### APP\_BLOCKED

• **APP\_BLOCKED** = `4`

Event is sent when the user changes the notification blocked state for the entire application or
when the user opens the application settings.

**`platform`** android API Level >= 28

#### Defined in

[src/types/Notification.ts:338](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L338)

___

### CHANNEL\_BLOCKED

• **CHANNEL\_BLOCKED** = `5`

Event type is sent when the user changes the notification blocked state for a channel in the application.

**`platform`** android API Level >= 28

#### Defined in

[src/types/Notification.ts:345](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L345)

___

### CHANNEL\_GROUP\_BLOCKED

• **CHANNEL\_GROUP\_BLOCKED** = `6`

Event type is sent when the user changes the notification blocked state for a channel group in the application.

**`platform`** android API Level >= 28

#### Defined in

[src/types/Notification.ts:352](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L352)

___

### DELIVERED

• **DELIVERED** = `3`

Event type sent when a notification has been delivered to the device. For trigger notifications,
this event is sent at the point when the trigger executes, not when a the trigger notification is created.

It's important to note even though a notification has been delivered, it may not be shown to the
user. For example, they may have notifications disabled on the device/channel/app.

#### Defined in

[src/types/Notification.ts:330](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L330)

___

### DISMISSED

• **DISMISSED** = `0`

Event type is sent when the user dismisses a notification. This is triggered via the user swiping
the notification from the notification shade or performing "Clear all" notifications.

This event is **not** sent when a notification is cancelled or times out.

**`platform`** android Android

#### Defined in

[src/types/Notification.ts:307](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L307)

___

### PRESS

• **PRESS** = `1`

Event type is sent when a notification has been pressed by the user.

On Android, notifications must include an `android.pressAction` property for this event to trigger.

On iOS, this event is always sent when the user presses a notification.

#### Defined in

[src/types/Notification.ts:316](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L316)

___

### TRIGGER\_NOTIFICATION\_CREATED

• **TRIGGER\_NOTIFICATION\_CREATED** = `7`

Event type is sent when a notification trigger is created.

#### Defined in

[src/types/Notification.ts:357](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L357)

___

### UNKNOWN

• **UNKNOWN** = `-1`

An unknown event was received.

This event type is a failsafe to catch any unknown events from the device. Please
report an issue with a reproduction so it can be correctly handled.

#### Defined in

[src/types/Notification.ts:297](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L297)
