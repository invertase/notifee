[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidBigPictureStyle

# Interface: AndroidBigPictureStyle

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidBigPictureStyle

The interface used when displaying a Big Picture Style notification.

<Vimeo id="android-style-bigpicture" caption="Android Big Picture Style" />

View the [Big Picture](/react-native/docs/android/styles#big-picture) documentation to learn more.

**`platform`** android

## Table of contents

### Properties

- [largeIcon](types_NotificationAndroid.AndroidBigPictureStyle.md#largeicon)
- [picture](types_NotificationAndroid.AndroidBigPictureStyle.md#picture)
- [summary](types_NotificationAndroid.AndroidBigPictureStyle.md#summary)
- [title](types_NotificationAndroid.AndroidBigPictureStyle.md#title)
- [type](types_NotificationAndroid.AndroidBigPictureStyle.md#type)

## Properties

### largeIcon

• `Optional` **largeIcon**: `string` \| `number` \| `object`

A local file path using the 'require()' method or a HTTP or file URL to the picture to display.

If set, overrides the main notification `largeIcon` when the notification is expanded.

#### Defined in

[src/types/NotificationAndroid.ts:551](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L551)

___

### picture

• **picture**: `string` \| `number` \| `object`

A local file path using the 'require()' method or a HTTP or file URL to the picture to display.

The image will be automatically resized depending on the device and it's size. If the image could
not be found a blank space will appear.

#### Defined in

[src/types/NotificationAndroid.ts:538](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L538)

___

### summary

• `Optional` **summary**: `string`

If set, overrides the main notification `summary` when the notification is expanded.

#### Defined in

[src/types/NotificationAndroid.ts:556](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L556)

___

### title

• `Optional` **title**: `string`

If set, overrides the main notification `title` when the notification is expanded.

#### Defined in

[src/types/NotificationAndroid.ts:543](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L543)

___

### type

• **type**: [`BIGPICTURE`](../enums/types_NotificationAndroid.AndroidStyle.md#bigpicture)

Constant enum value used to identify the style type.

#### Defined in

[src/types/NotificationAndroid.ts:529](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L529)
