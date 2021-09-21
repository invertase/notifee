[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidChannel

# Interface: AndroidChannel

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidChannel

An interface for describing an Android Channel.

Channels override any individual notification preferences (e.g. lights/vibration) and the user
has final control over the setting. Once created, only channel metadata can be updated (e.g. name).

View the [Channels & Groups](/react-native/docs/android/channels) documentation to learn more.

**`platform`** android

## Hierarchy

- **`AndroidChannel`**

  ↳ [`NativeAndroidChannel`](types_NotificationAndroid.NativeAndroidChannel.md)

## Table of contents

### Properties

- [badge](types_NotificationAndroid.AndroidChannel.md#badge)
- [bypassDnd](types_NotificationAndroid.AndroidChannel.md#bypassdnd)
- [description](types_NotificationAndroid.AndroidChannel.md#description)
- [groupId](types_NotificationAndroid.AndroidChannel.md#groupid)
- [id](types_NotificationAndroid.AndroidChannel.md#id)
- [importance](types_NotificationAndroid.AndroidChannel.md#importance)
- [lightColor](types_NotificationAndroid.AndroidChannel.md#lightcolor)
- [lights](types_NotificationAndroid.AndroidChannel.md#lights)
- [name](types_NotificationAndroid.AndroidChannel.md#name)
- [sound](types_NotificationAndroid.AndroidChannel.md#sound)
- [soundURI](types_NotificationAndroid.AndroidChannel.md#sounduri)
- [vibration](types_NotificationAndroid.AndroidChannel.md#vibration)
- [vibrationPattern](types_NotificationAndroid.AndroidChannel.md#vibrationpattern)
- [visibility](types_NotificationAndroid.AndroidChannel.md#visibility)

## Properties

### badge

• `Optional` **badge**: `boolean`

Sets whether badges are enabled for the channel.

View the [Badges](/react-native/docs/android/appearance#badges) documentation to learn more.

Defaults to `true`.

This setting cannot be overridden once the channel is created.

#### Defined in

[src/types/NotificationAndroid.ts:815](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L815)

___

### bypassDnd

• `Optional` **bypassDnd**: `boolean`

Sets whether or not notifications posted to this channel can interrupt the user in
'Do Not Disturb' mode.

Defaults to `false`.

This setting cannot be overridden once the channel is created.

**`platform`** android API Level >= 29

#### Defined in

[src/types/NotificationAndroid.ts:827](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L827)

___

### description

• `Optional` **description**: `string`

Sets the user visible description of this channel.

The recommended maximum length is 300 characters; the value may be truncated if it is too long.

This setting can be updated after creation.

**`platform`** android API Level >= 28

#### Defined in

[src/types/NotificationAndroid.ts:838](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L838)

___

### groupId

• `Optional` **groupId**: `string`

Sets what group this channel belongs to. Group information is only used for presentation, not for behavior.

Groups can be created via via [`createChannelGroup`](/react-native/reference/createchannelgroup).

This setting cannot be overridden once the channel is created.

#### Defined in

[src/types/NotificationAndroid.ts:865](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L865)

___

### id

• **id**: `string`

The unique channel ID.

#### Defined in

[src/types/NotificationAndroid.ts:796](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L796)

___

### importance

• `Optional` **importance**: [`AndroidImportance`](../enums/types_NotificationAndroid.AndroidImportance.md)

Sets the level of interruption of this notification channel.

Defaults to `AndroidImportance.DEFAULT`.

This setting can only be set to a lower importance level once set.

#### Defined in

[src/types/NotificationAndroid.ts:874](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L874)

___

### lightColor

• `Optional` **lightColor**: `string`

If lights are enabled (via `lights`), sets/overrides the light color for notifications
posted to this channel.

This setting cannot be overridden once the channel is created.

#### Defined in

[src/types/NotificationAndroid.ts:882](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L882)

___

### lights

• `Optional` **lights**: `boolean`

Sets whether notifications posted to this channel should display notification lights, on devices that support that feature.

Defaults to `true`.

This setting cannot be overridden once the channel is created.

#### Defined in

[src/types/NotificationAndroid.ts:847](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L847)

___

### name

• **name**: `string`

The channel name. This is shown to the user so must be descriptive and relate to the notifications
which will be delivered under this channel.

This setting can be updated after creation.

#### Defined in

[src/types/NotificationAndroid.ts:804](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L804)

___

### sound

• `Optional` **sound**: `string`

Overrides the sound the notification is displayed with.

The default value is to play no sound. To play the default system sound use 'default'.

This setting cannot be overridden once the channel is created.

#### Defined in

[src/types/NotificationAndroid.ts:910](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L910)

___

### soundURI

• `Optional` **soundURI**: `string`

The URI of the notification sound associated with the channel, if any.

This is a read-only value, and is under user control after the channel is created

#### Defined in

[src/types/NotificationAndroid.ts:917](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L917)

___

### vibration

• `Optional` **vibration**: `boolean`

Sets whether notification posted to this channel should vibrate.

Defaults to `true`.

This setting cannot be overridden once the channel is created.

#### Defined in

[src/types/NotificationAndroid.ts:856](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L856)

___

### vibrationPattern

• `Optional` **vibrationPattern**: `number`[]

Sets/overrides the vibration pattern for notifications posted to this channel.

The pattern in milliseconds. Must be an even amount of numbers.

This setting cannot be overridden once the channel is created.

#### Defined in

[src/types/NotificationAndroid.ts:901](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L901)

___

### visibility

• `Optional` **visibility**: [`AndroidVisibility`](../enums/types_NotificationAndroid.AndroidVisibility.md)

Sets whether notifications posted to this channel appear on the lockscreen or not,
and if so, whether they appear in a redacted form.

Defaults to `AndroidVisibility.PRIVATE`.

This setting cannot be overridden once the channel is created.

#### Defined in

[src/types/NotificationAndroid.ts:892](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L892)
