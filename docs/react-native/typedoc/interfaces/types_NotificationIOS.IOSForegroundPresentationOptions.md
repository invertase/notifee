[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationIOS](../modules/types_NotificationIOS.md) / IOSForegroundPresentationOptions

# Interface: IOSForegroundPresentationOptions

[types/NotificationIOS](../modules/types_NotificationIOS.md).IOSForegroundPresentationOptions

An interface to customise how notifications are shown when the app is in the foreground.

By default, Notifee will show iOS notifications in heads-up mode if your app is currently in the foreground.

View the [Foreground Notifications](/react-native/docs/ios/appearance#foreground-notifications) to learn
more.

**`platform`** ios

## Table of contents

### Properties

- [alert](types_NotificationIOS.IOSForegroundPresentationOptions.md#alert)
- [badge](types_NotificationIOS.IOSForegroundPresentationOptions.md#badge)
- [sound](types_NotificationIOS.IOSForegroundPresentationOptions.md#sound)

## Properties

### alert

• `Optional` **alert**: `boolean`

App in foreground dialog box which indicates when a decision has to be made

Defaults to false

#### Defined in

[src/types/NotificationIOS.ts:129](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L129)

___

### badge

• `Optional` **badge**: `boolean`

App in foreground badge update

Defaults to true

#### Defined in

[src/types/NotificationIOS.ts:141](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L141)

___

### sound

• `Optional` **sound**: `boolean`

App in foreground notification sound

Defaults to false

#### Defined in

[src/types/NotificationIOS.ts:135](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L135)
