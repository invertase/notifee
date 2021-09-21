[@notifee/react-native](../README.md) / [Modules](../modules.md) / types/Notification

# Module: types/Notification

## Table of contents

### Enumerations

- [EventType](../enums/types_Notification.EventType.md)

### Interfaces

- [DisplayedNotification](../interfaces/types_Notification.DisplayedNotification.md)
- [Event](../interfaces/types_Notification.Event.md)
- [EventDetail](../interfaces/types_Notification.EventDetail.md)
- [InitialNotification](../interfaces/types_Notification.InitialNotification.md)
- [Notification](../interfaces/types_Notification.Notification.md)
- [NotificationFullScreenAction](../interfaces/types_Notification.NotificationFullScreenAction.md)
- [NotificationPressAction](../interfaces/types_Notification.NotificationPressAction.md)
- [TriggerNotification](../interfaces/types_Notification.TriggerNotification.md)

### Type aliases

- [ForegroundServiceTask](types_Notification.md#foregroundservicetask)

## Type aliases

### ForegroundServiceTask

Ƭ **ForegroundServiceTask**: (`notification`: [`Notification`](../interfaces/types_Notification.Notification.md)) => `Promise`<`void`\>

#### Type declaration

▸ (`notification`): `Promise`<`void`\>

A representation of a Foreground Service task registered via [`registerForegroundService`](/react-native/reference/registerforegroundservice).

The task must resolve a promise once complete, and in turn removes the notification.

View the [Foreground Service](/react-native/docs/android/foreground-service) documentation to
learn more.

##### Parameters

| Name | Type |
| :------ | :------ |
| `notification` | [`Notification`](../interfaces/types_Notification.Notification.md) |

##### Returns

`Promise`<`void`\>

#### Defined in

[src/types/Notification.ts:176](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L176)
