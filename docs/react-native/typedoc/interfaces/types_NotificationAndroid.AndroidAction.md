[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidAction

# Interface: AndroidAction

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidAction

The interface used to describe a notification quick action for Android.

Notification actions allow users to interact with notifications, allowing you to handle events
within your application. When an action completes (e.g. pressing an action, or filling out an input
box) an event is sent.

View the [Quick Actions](/react-native/docs/android/interaction#quick-actions) documentation to learn more.

**`platform`** android

## Table of contents

### Properties

- [icon](types_NotificationAndroid.AndroidAction.md#icon)
- [input](types_NotificationAndroid.AndroidAction.md#input)
- [pressAction](types_NotificationAndroid.AndroidAction.md#pressaction)
- [title](types_NotificationAndroid.AndroidAction.md#title)

## Properties

### icon

• `Optional` **icon**: `string`

An remote http or local icon path representing the action. Newer devices may not show the icon.

Recommended icon size is 24x24 px.

#### Defined in

[src/types/NotificationAndroid.ts:450](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L450)

___

### input

• `Optional` **input**: ``true`` \| [`AndroidInput`](types_NotificationAndroid.AndroidInput.md)

If provided, the action accepts user input.

If `true`, the user will be able to provide free text input when the action is pressed. This
property can be further configured for advanced inputs.

View the [Action Input](/react-native/docs/android/interaction#action-input) documentation to
learn more.

#### Defined in

[src/types/NotificationAndroid.ts:461](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L461)

___

### pressAction

• **pressAction**: [`NotificationPressAction`](types_Notification.NotificationPressAction.md)

The press action interface describing what happens when an action completes.

Note; unlike the `pressAction` in the notification body, an action does not need to open the application
and can perform background tasks. See the [AndroidPressAction](/react-native/reference/androidpressaction) reference
or [Quick Actions](/react-native/docs/android/interaction#quick-actions) documentation to learn more.

#### Defined in

[src/types/NotificationAndroid.ts:438](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L438)

___

### title

• **title**: `string`

The title of the action, e.g. "Reply", "Mark as read" etc.

#### Defined in

[src/types/NotificationAndroid.ts:443](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L443)
