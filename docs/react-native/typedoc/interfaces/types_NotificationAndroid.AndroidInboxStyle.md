[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidInboxStyle

# Interface: AndroidInboxStyle

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidInboxStyle

The interface used when displaying a Inbox Style notification.

<Vimeo id="android-style-inbox" caption="Android Inbox Style" />

View the [Inbox](/react-native/docs/android/styles#inbox) documentation to learn more.

**`platform`** android

## Table of contents

### Properties

- [lines](types_NotificationAndroid.AndroidInboxStyle.md#lines)
- [summary](types_NotificationAndroid.AndroidInboxStyle.md#summary)
- [title](types_NotificationAndroid.AndroidInboxStyle.md#title)
- [type](types_NotificationAndroid.AndroidInboxStyle.md#type)

## Properties

### lines

• **lines**: `string`[]

An array of messages to display, in order provided.

The device will automatically handle displaying the lines visible depending on space in the notification
shade.

#### Defined in

[src/types/NotificationAndroid.ts:611](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L611)

___

### summary

• `Optional` **summary**: `string`

If set, overrides the main notification `summary` when the notification is expanded.

#### Defined in

[src/types/NotificationAndroid.ts:621](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L621)

___

### title

• `Optional` **title**: `string`

If set, overrides the main notification `title` when the notification is expanded.

#### Defined in

[src/types/NotificationAndroid.ts:616](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L616)

___

### type

• **type**: [`INBOX`](../enums/types_NotificationAndroid.AndroidStyle.md#inbox)

Constant enum value used to identify the style type.

#### Defined in

[src/types/NotificationAndroid.ts:603](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L603)
