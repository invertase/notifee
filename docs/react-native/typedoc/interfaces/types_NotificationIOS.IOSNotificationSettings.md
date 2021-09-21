[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationIOS](../modules/types_NotificationIOS.md) / IOSNotificationSettings

# Interface: IOSNotificationSettings

[types/NotificationIOS](../modules/types_NotificationIOS.md).IOSNotificationSettings

An interface representing the current authorization status and notification-related settings for your app.

This interface is returned from [`requestPermission`](/react-native/reference/requestpermission)
and [`getNotificationSettings`](/reference/getnotificationsettings).

View the [Observing Settings](/react-native/docs/ios/permissions#observing-settings) documentation to learn more.

**`platform`** ios

## Table of contents

### Properties

- [alert](types_NotificationIOS.IOSNotificationSettings.md#alert)
- [announcement](types_NotificationIOS.IOSNotificationSettings.md#announcement)
- [authorizationStatus](types_NotificationIOS.IOSNotificationSettings.md#authorizationstatus)
- [badge](types_NotificationIOS.IOSNotificationSettings.md#badge)
- [carPlay](types_NotificationIOS.IOSNotificationSettings.md#carplay)
- [criticalAlert](types_NotificationIOS.IOSNotificationSettings.md#criticalalert)
- [inAppNotificationSettings](types_NotificationIOS.IOSNotificationSettings.md#inappnotificationsettings)
- [lockScreen](types_NotificationIOS.IOSNotificationSettings.md#lockscreen)
- [notificationCenter](types_NotificationIOS.IOSNotificationSettings.md#notificationcenter)
- [showPreviews](types_NotificationIOS.IOSNotificationSettings.md#showpreviews)
- [sound](types_NotificationIOS.IOSNotificationSettings.md#sound)

## Properties

### alert

• **alert**: [`IOSNotificationSetting`](../enums/types_NotificationIOS.IOSNotificationSetting.md)

Enum describing if notifications will alert the user.

#### Defined in

[src/types/NotificationIOS.ts:327](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L327)

___

### announcement

• **announcement**: [`IOSNotificationSetting`](../enums/types_NotificationIOS.IOSNotificationSetting.md)

Enum describing if notifications can be announced to the user
via 3rd party services such as Siri.

For example, if the notification can be automatically read by Siri
while the user is wearing AirPods.

#### Defined in

[src/types/NotificationIOS.ts:366](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L366)

___

### authorizationStatus

• **authorizationStatus**: [`IOSAuthorizationStatus`](../enums/types_NotificationIOS.IOSAuthorizationStatus.md)

Overall notification authorization status for the application.

#### Defined in

[src/types/NotificationIOS.ts:378](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L378)

___

### badge

• **badge**: [`IOSNotificationSetting`](../enums/types_NotificationIOS.IOSNotificationSetting.md)

Enum describing if notifications can update the application badge.

#### Defined in

[src/types/NotificationIOS.ts:332](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L332)

___

### carPlay

• **carPlay**: [`IOSNotificationSetting`](../enums/types_NotificationIOS.IOSNotificationSetting.md)

Enum describing if notifications can be displayed in a CarPlay environment.

#### Defined in

[src/types/NotificationIOS.ts:352](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L352)

___

### criticalAlert

• **criticalAlert**: [`IOSNotificationSetting`](../enums/types_NotificationIOS.IOSNotificationSetting.md)

Enum describing if critical notifications are allowed.

#### Defined in

[src/types/NotificationIOS.ts:337](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L337)

___

### inAppNotificationSettings

• **inAppNotificationSettings**: [`IOSNotificationSetting`](../enums/types_NotificationIOS.IOSNotificationSetting.md)

#### Defined in

[src/types/NotificationIOS.ts:373](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L373)

___

### lockScreen

• **lockScreen**: [`IOSNotificationSetting`](../enums/types_NotificationIOS.IOSNotificationSetting.md)

Enum describing if notifications will be displayed on the lock screen.

#### Defined in

[src/types/NotificationIOS.ts:357](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L357)

___

### notificationCenter

• **notificationCenter**: [`IOSNotificationSetting`](../enums/types_NotificationIOS.IOSNotificationSetting.md)

Enum describing if notifications will be displayed in the notification center.

#### Defined in

[src/types/NotificationIOS.ts:371](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L371)

___

### showPreviews

• **showPreviews**: [`IOSShowPreviewsSetting`](../enums/types_NotificationIOS.IOSShowPreviewsSetting.md)

Enum describing if notification previews will be shown.

#### Defined in

[src/types/NotificationIOS.ts:342](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L342)

___

### sound

• **sound**: [`IOSNotificationSetting`](../enums/types_NotificationIOS.IOSNotificationSetting.md)

Enum describing if notifications can trigger a sound.

#### Defined in

[src/types/NotificationIOS.ts:347](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L347)
