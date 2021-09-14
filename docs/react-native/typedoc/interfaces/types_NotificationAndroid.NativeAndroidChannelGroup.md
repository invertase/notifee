[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / NativeAndroidChannelGroup

# Interface: NativeAndroidChannelGroup

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).NativeAndroidChannelGroup

An interface which describes a channel group which has been fetched from the device.

Contains additional information which is only available when fetching the channel group from the device.

**`platform`** android API Level >= 26

## Hierarchy

- [`AndroidChannelGroup`](types_NotificationAndroid.AndroidChannelGroup.md)

  ↳ **`NativeAndroidChannelGroup`**

## Table of contents

### Properties

- [blocked](types_NotificationAndroid.NativeAndroidChannelGroup.md#blocked)
- [channels](types_NotificationAndroid.NativeAndroidChannelGroup.md#channels)
- [description](types_NotificationAndroid.NativeAndroidChannelGroup.md#description)
- [id](types_NotificationAndroid.NativeAndroidChannelGroup.md#id)
- [name](types_NotificationAndroid.NativeAndroidChannelGroup.md#name)

## Properties

### blocked

• **blocked**: `boolean`

Returns whether or not notifications posted to a Channel belonging to this group are
blocked by the user.

On API levels < 28, returns `false`.

View the [Listening to channel events](/react-native/docs/android/channels#listening-to-channel-events)
documentation to learn more about subscribing to when a channel is blocked by the user.

**`platform`** android API Level >= 28

#### Defined in

[src/types/NotificationAndroid.ts:995](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L995)

___

### channels

• **channels**: [`NativeAndroidChannel`](types_NotificationAndroid.NativeAndroidChannel.md)[]

Returns a list of channels assigned to this channel group.

#### Defined in

[src/types/NotificationAndroid.ts:1000](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1000)

___

### description

• `Optional` **description**: `string`

An optional description of the group. This is visible to the user.

On Android APIs less than 28 this will always be undefined.

**`platform`** android API Level >= 28

#### Inherited from

[AndroidChannelGroup](types_NotificationAndroid.AndroidChannelGroup.md).[description](types_NotificationAndroid.AndroidChannelGroup.md#description)

#### Defined in

[src/types/NotificationAndroid.ts:973](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L973)

___

### id

• **id**: `string`

Unique id for this channel group.

#### Inherited from

[AndroidChannelGroup](types_NotificationAndroid.AndroidChannelGroup.md).[id](types_NotificationAndroid.AndroidChannelGroup.md#id)

#### Defined in

[src/types/NotificationAndroid.ts:956](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L956)

___

### name

• **name**: `string`

The name of the group. This is visible to the user so should be a descriptive name which
categorizes other channels (e.g. reminders).

The recommended maximum length is 40 characters; the value may be truncated if it is too long.

#### Inherited from

[AndroidChannelGroup](types_NotificationAndroid.AndroidChannelGroup.md).[name](types_NotificationAndroid.AndroidChannelGroup.md#name)

#### Defined in

[src/types/NotificationAndroid.ts:964](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L964)
