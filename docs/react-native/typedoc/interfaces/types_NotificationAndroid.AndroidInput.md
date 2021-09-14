[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidInput

# Interface: AndroidInput

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidInput

The interface used to enable advanced user input on a notification.

View the [Action Input](/react-native/docs/android/interaction#action-input) documentation to learn more.

**`platform`** android

## Table of contents

### Properties

- [allowFreeFormInput](types_NotificationAndroid.AndroidInput.md#allowfreeforminput)
- [allowGeneratedReplies](types_NotificationAndroid.AndroidInput.md#allowgeneratedreplies)
- [choices](types_NotificationAndroid.AndroidInput.md#choices)
- [editableChoices](types_NotificationAndroid.AndroidInput.md#editablechoices)
- [placeholder](types_NotificationAndroid.AndroidInput.md#placeholder)

## Properties

### allowFreeFormInput

• `Optional` **allowFreeFormInput**: `boolean`

Sets whether the user can freely enter text into the input.

This value changes the behaviour of the notification:

- If `true`, when an action is pressed this allows the user to type free form text into the input area.
- If `false`, you must provide an array of `choices` the user is allowed to use as the input.

Defaults to `true`.

#### Defined in

[src/types/NotificationAndroid.ts:482](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L482)

___

### allowGeneratedReplies

• `Optional` **allowGeneratedReplies**: `boolean`

Sets whether generated replies can be added to the action.

Generated replies will only be shown if the input has `choices` and whether the device
is able to generate replies.

Defaults to `true`.

#### Defined in

[src/types/NotificationAndroid.ts:492](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L492)

___

### choices

• `Optional` **choices**: `string`[]

An array of pre-defined input choices the user can select.

If `allowFreeFormInput` is `false`, this property must contain at least one choice.

#### Defined in

[src/types/NotificationAndroid.ts:499](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L499)

___

### editableChoices

• `Optional` **editableChoices**: `boolean`

If `true`, the user will be able to edit the selected choice before sending the action event, however
`allowFreeFormInput` must also be `true`.

By default, the platform will decide whether choices can be editable. To explicitly enable or disable
this, provide `true` or `false`.

#### Defined in

[src/types/NotificationAndroid.ts:508](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L508)

___

### placeholder

• `Optional` **placeholder**: `string`

The placeholder text to display inside of the user input area.

#### Defined in

[src/types/NotificationAndroid.ts:513](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L513)
