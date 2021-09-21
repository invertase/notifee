[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidPerson

# Interface: AndroidPerson

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidPerson

The interface used to describe a person shown in notifications.

Currently used with [`AndroidMessagingStyle`](/react-native/reference/androidmessagingstyle) notifications.

**`platform`** android

## Table of contents

### Properties

- [bot](types_NotificationAndroid.AndroidPerson.md#bot)
- [icon](types_NotificationAndroid.AndroidPerson.md#icon)
- [id](types_NotificationAndroid.AndroidPerson.md#id)
- [important](types_NotificationAndroid.AndroidPerson.md#important)
- [name](types_NotificationAndroid.AndroidPerson.md#name)
- [uri](types_NotificationAndroid.AndroidPerson.md#uri)

## Properties

### bot

• `Optional` **bot**: `boolean`

If `true` this person represents a machine rather than a human. This is used primarily for testing and automated tooling.

Defaults to `false`.

#### Defined in

[src/types/NotificationAndroid.ts:716](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L716)

___

### icon

• `Optional` **icon**: `string`

The icon to display next to the person in the notification. The icon can be URL or local
Android resource.

If not provided, an icon will be automatically creating using the `name` property.

#### Defined in

[src/types/NotificationAndroid.ts:735](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L735)

___

### id

• `Optional` **id**: `string`

An optional unique ID of the person. Setting this property is preferred for unique identification,
however not required. If no value is provided, the `name` will be used instead..

#### Defined in

[src/types/NotificationAndroid.ts:709](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L709)

___

### important

• `Optional` **important**: `boolean`

If `true` this person will be marked as important.

Important users are those who frequently contact the receiving person. If the app is in
"Do not disturb" mode, a notification containing an important person may override this mode
if the person has been whitelisted on the device.

Defaults to `false`.

#### Defined in

[src/types/NotificationAndroid.ts:727](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L727)

___

### name

• **name**: `string`

The name of the person.

If no `id` is provided, the name will be used as the unique identifier.

#### Defined in

[src/types/NotificationAndroid.ts:703](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L703)

___

### uri

• `Optional` **uri**: `string`

URI contact of the person.

The URI can be any of the following:

 - The representation of a contact URI, e.g. `android.provider.ContactsContract.Contacts#CONTENT_LOOKUP_URI`
 - A `mailto:` string
 - A `tel:` string

#### Defined in

[src/types/NotificationAndroid.ts:746](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L746)
