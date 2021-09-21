[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/Notification](../modules/types_Notification.md) / NotificationFullScreenAction

# Interface: NotificationFullScreenAction

[types/Notification](../modules/types_Notification.md).NotificationFullScreenAction

The interface used to describe a full-screen action for a notification.

By setting a `fullScreenAction`, when the notification is displayed, it will launch a full-screen intent.

On Android; when provided to a notification action, the action will only open you application if
a `launchActivity` and/or a `mainComponent` is provided.

Please see the [FullScreen Action](/react-native/docs/android/behaviour#full-screen-action) document to learn more.

## Table of contents

### Properties

- [id](types_Notification.NotificationFullScreenAction.md#id)
- [launchActivity](types_Notification.NotificationFullScreenAction.md#launchactivity)
- [launchActivityFlags](types_Notification.NotificationFullScreenAction.md#launchactivityflags)
- [mainComponent](types_Notification.NotificationFullScreenAction.md#maincomponent)

## Properties

### id

• **id**: `string`

The unique ID for the action.

The `id` property is used to differentiate between full-screen actions. When listening to notification
events, the ID can be read from the `event.detail.notification.android.fullScreenAction` object.

#### Defined in

[src/types/Notification.ts:248](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L248)

___

### launchActivity

• `Optional` **launchActivity**: `string`

The custom Android Activity to launch on a full-screen action.

This property can be used in advanced scenarios to launch a custom Android Activity when the user
performs a full-screen action.

View the [Android Full Screen](/react-native/docs/android/behviour#full-screen-action) docs to learn more.

**`platform`** android

#### Defined in

[src/types/Notification.ts:260](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L260)

___

### launchActivityFlags

• `Optional` **launchActivityFlags**: [`AndroidLaunchActivityFlag`](../enums/types_NotificationAndroid.AndroidLaunchActivityFlag.md)[]

Custom flags that are added to the Android [Intent](https://developer.android.com/reference/android/content/Intent.html) that launches your Activity.

These are only required if you need to customise the behaviour of how your activities are launched; by default these are not required.

**`platform`** android

#### Defined in

[src/types/Notification.ts:269](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L269)

___

### mainComponent

• `Optional` **mainComponent**: `string`

A custom registered React component to launch on press action.

This property can be used to open a custom React component when the notification is displayed.
For this to correctly function on Android, a minor native code change is required.

View the [Full-screen Action](/react-native/docs/android/behviour#full-screen-action) document to learn more.

**`platform`** android

#### Defined in

[src/types/Notification.ts:281](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Notification.ts#L281)
