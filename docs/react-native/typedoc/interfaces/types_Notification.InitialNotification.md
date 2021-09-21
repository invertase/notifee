[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/Notification](../modules/types_Notification.md) / InitialNotification

# Interface: InitialNotification

[types/Notification](../modules/types_Notification.md).InitialNotification

An interface representing a notification & action that launched the current app / or Android activity.

View the [App open events](/react-native/docs/events#app-open-events) documentation to learn more.

This interface is returned from [`getInitialNotification`](/react-native/reference/getinitialnotification) when
an initial notification is available.

## Table of contents

### Properties

- [input](types_Notification.InitialNotification.md#input)
- [notification](types_Notification.InitialNotification.md#notification)
- [pressAction](types_Notification.InitialNotification.md#pressaction)

## Properties

### input

• `Optional` **input**: `string`

The input from a notification action.

The input detail is available when the [`EventType`](/react-native/reference/eventtype) is:

- [`EventType.ACTION_PRESS`](/react-native/reference/eventtype#action_press)
- The notification quick action has input enabled. View [`AndroidInput`](/react-native/reference/androidinput) for more details.

**`platform`** android API Level >= 20

#### Defined in

[src/types/Notification.ts:101](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L101)

___

### notification

• **notification**: [`Notification`](types_Notification.Notification.md)

The notification which the user interacted with, which caused the application to open.

#### Defined in

[src/types/Notification.ts:84](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L84)

___

### pressAction

• **pressAction**: [`NotificationPressAction`](types_Notification.NotificationPressAction.md)

The press action which the user interacted with, on the notification, which caused the application to open.

#### Defined in

[src/types/Notification.ts:89](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L89)
