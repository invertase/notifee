[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationIOS](../modules/types_NotificationIOS.md) / IOSNotificationCategory

# Interface: IOSNotificationCategory

[types/NotificationIOS](../modules/types_NotificationIOS.md).IOSNotificationCategory

A interface representing a notification category created via [`setNotificationCategories`](/react-native/reference/setnotificationcategories).

At minimum, a category must be created with a unique identifier, all other properties are optional.

View the [Categories](/react-native/docs/ios/categories) documentation to learn more.

**`platform`** ios

## Table of contents

### Properties

- [actions](types_NotificationIOS.IOSNotificationCategory.md#actions)
- [allowAnnouncement](types_NotificationIOS.IOSNotificationCategory.md#allowannouncement)
- [allowInCarPlay](types_NotificationIOS.IOSNotificationCategory.md#allowincarplay)
- [hiddenPreviewsBodyPlaceholder](types_NotificationIOS.IOSNotificationCategory.md#hiddenpreviewsbodyplaceholder)
- [hiddenPreviewsShowSubtitle](types_NotificationIOS.IOSNotificationCategory.md#hiddenpreviewsshowsubtitle)
- [hiddenPreviewsShowTitle](types_NotificationIOS.IOSNotificationCategory.md#hiddenpreviewsshowtitle)
- [id](types_NotificationIOS.IOSNotificationCategory.md#id)
- [intentIdentifiers](types_NotificationIOS.IOSNotificationCategory.md#intentidentifiers)
- [summaryFormat](types_NotificationIOS.IOSNotificationCategory.md#summaryformat)

## Properties

### actions

• `Optional` **actions**: [`IOSNotificationCategoryAction`](types_NotificationIOS.IOSNotificationCategoryAction.md)[]

#### Defined in

[src/types/NotificationIOS.ts:509](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L509)

___

### allowAnnouncement

• `Optional` **allowAnnouncement**: `boolean`

#### Defined in

[src/types/NotificationIOS.ts:476](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L476)

___

### allowInCarPlay

• `Optional` **allowInCarPlay**: `boolean`

Allow notifications in this category to be displayed in a CarPlay environment.

Defaults to `false`.

#### Defined in

[src/types/NotificationIOS.ts:465](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L465)

___

### hiddenPreviewsBodyPlaceholder

• `Optional` **hiddenPreviewsBodyPlaceholder**: `string`

#### Defined in

[src/types/NotificationIOS.ts:497](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L497)

___

### hiddenPreviewsShowSubtitle

• `Optional` **hiddenPreviewsShowSubtitle**: `boolean`

#### Defined in

[src/types/NotificationIOS.ts:490](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L490)

___

### hiddenPreviewsShowTitle

• `Optional` **hiddenPreviewsShowTitle**: `boolean`

#### Defined in

[src/types/NotificationIOS.ts:483](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L483)

___

### id

• **id**: `string`

The unique ID for the category.

#### Defined in

[src/types/NotificationIOS.ts:451](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L451)

___

### intentIdentifiers

• `Optional` **intentIdentifiers**: [`IOSIntentIdentifier`](../enums/types_NotificationIOS.IOSIntentIdentifier.md)[]

#### Defined in

[src/types/NotificationIOS.ts:499](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L499)

___

### summaryFormat

• `Optional` **summaryFormat**: `string`

Specify a custom format for the summary text, which is visible when notifications are grouped together.

View the [Summary Text](/react-native/docs/ios/categories#category-summary-text) documentation to learn more.

#### Defined in

[src/types/NotificationIOS.ts:458](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L458)
