[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidMessagingStyle

# Interface: AndroidMessagingStyle

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidMessagingStyle

The interface used when displaying a Messaging Style notification.

<Vimeo id="android-style-messaging" caption="Android Messaging Style" />

View the [Messaging](/react-native/docs/android/styles#messaging) documentation to learn more.

**`platform`** android

## Table of contents

### Properties

- [group](types_NotificationAndroid.AndroidMessagingStyle.md#group)
- [messages](types_NotificationAndroid.AndroidMessagingStyle.md#messages)
- [person](types_NotificationAndroid.AndroidMessagingStyle.md#person)
- [title](types_NotificationAndroid.AndroidMessagingStyle.md#title)
- [type](types_NotificationAndroid.AndroidMessagingStyle.md#type)

## Properties

### group

• `Optional` **group**: `boolean`

Sets whether this conversation notification represents a group (3 or more persons).

#### Defined in

[src/types/NotificationAndroid.ts:657](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L657)

___

### messages

• **messages**: [`AndroidMessagingStyleMessage`](types_NotificationAndroid.AndroidMessagingStyleMessage.md)[]

An array of messages to display inside the notification.

#### Defined in

[src/types/NotificationAndroid.ts:647](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L647)

___

### person

• **person**: [`AndroidPerson`](types_NotificationAndroid.AndroidPerson.md)

The person who is receiving a message on the current device.

#### Defined in

[src/types/NotificationAndroid.ts:642](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L642)

___

### title

• `Optional` **title**: `string`

If set, overrides the main notification `title` when the notification is expanded.

#### Defined in

[src/types/NotificationAndroid.ts:652](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L652)

___

### type

• **type**: [`MESSAGING`](../enums/types_NotificationAndroid.AndroidStyle.md#messaging)

Constant enum value used to identify the style type.

#### Defined in

[src/types/NotificationAndroid.ts:637](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L637)
