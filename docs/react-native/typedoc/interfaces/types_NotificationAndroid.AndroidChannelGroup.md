[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidChannelGroup

# Interface: AndroidChannelGroup

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidChannelGroup

An interface for describing an Android Channel Group.

Channel groups have no impact on the notification, they are used to help group channels in the applications
settings UI.

View the [Channels & Groups](/react-native/docs/android/channels) documentation to learn more.

**`platform`** android API Level >= 26

## Hierarchy

- **`AndroidChannelGroup`**

  ↳ [`NativeAndroidChannelGroup`](types_NotificationAndroid.NativeAndroidChannelGroup.md)

## Table of contents

### Properties

- [description](types_NotificationAndroid.AndroidChannelGroup.md#description)
- [id](types_NotificationAndroid.AndroidChannelGroup.md#id)
- [name](types_NotificationAndroid.AndroidChannelGroup.md#name)

## Properties

### description

• `Optional` **description**: `string`

An optional description of the group. This is visible to the user.

On Android APIs less than 28 this will always be undefined.

**`platform`** android API Level >= 28

#### Defined in

[src/types/NotificationAndroid.ts:973](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L973)

___

### id

• **id**: `string`

Unique id for this channel group.

#### Defined in

[src/types/NotificationAndroid.ts:956](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L956)

___

### name

• **name**: `string`

The name of the group. This is visible to the user so should be a descriptive name which
categorizes other channels (e.g. reminders).

The recommended maximum length is 40 characters; the value may be truncated if it is too long.

#### Defined in

[src/types/NotificationAndroid.ts:964](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L964)
