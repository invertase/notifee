[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationIOS](../modules/types_NotificationIOS.md) / IOSNotificationCategoryAction

# Interface: IOSNotificationCategoryAction

[types/NotificationIOS](../modules/types_NotificationIOS.md).IOSNotificationCategoryAction

The interface used to describe a notification quick action for iOS.

Quick actions allow users to interact with notifications, allowing you to handle events
within your application. When an action completes (e.g. pressing an action, or filling out an input
box) an event is sent.

View the [Quick Actions](/react-native/docs/ios/interaction#quick-actions) documentation to learn more.

**`platform`** ios

## Table of contents

### Properties

- [authenticationRequired](types_NotificationIOS.IOSNotificationCategoryAction.md#authenticationrequired)
- [destructive](types_NotificationIOS.IOSNotificationCategoryAction.md#destructive)
- [foreground](types_NotificationIOS.IOSNotificationCategoryAction.md#foreground)
- [id](types_NotificationIOS.IOSNotificationCategoryAction.md#id)
- [input](types_NotificationIOS.IOSNotificationCategoryAction.md#input)
- [title](types_NotificationIOS.IOSNotificationCategoryAction.md#title)

## Properties

### authenticationRequired

• `Optional` **authenticationRequired**: `boolean`

Whether this action should require unlocking before being performed.

#### Defined in

[src/types/NotificationIOS.ts:557](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L557)

___

### destructive

• `Optional` **destructive**: `boolean`

Makes the action red, indicating that the action is destructive.

#### Defined in

[src/types/NotificationIOS.ts:547](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L547)

___

### foreground

• `Optional` **foreground**: `boolean`

Whether this action should cause the application to launch in the foreground.

#### Defined in

[src/types/NotificationIOS.ts:552](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L552)

___

### id

• **id**: `string`

#### Defined in

[src/types/NotificationIOS.ts:524](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L524)

___

### input

• `Optional` **input**: ``true`` \| [`IOSInput`](types_NotificationIOS.IOSInput.md)

If provided, the action accepts custom user input.

If `true`, the user will be able to provide free text input when the action is pressed.

The placeholder and button text can be customized by providing an object
of type [`IOSInput`](/react-native/reference/iosinput).

View the [Action Input](/react-native/docs/ios/interaction#action-input) documentation to
learn more.

#### Defined in

[src/types/NotificationIOS.ts:542](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L542)

___

### title

• **title**: `string`

The title of the action, e.g. "Reply", "Mark as read" etc.

#### Defined in

[src/types/NotificationIOS.ts:529](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L529)
