[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationIOS](../modules/types_NotificationIOS.md) / IOSNotificationAttachment

# Interface: IOSNotificationAttachment

[types/NotificationIOS](../modules/types_NotificationIOS.md).IOSNotificationAttachment

An interface for describing an iOS Notification Attachment.

View the [Attachments](/react-native/docs/ios/appearance#attachments) documentation to learn more.

**`platform`** ios

## Table of contents

### Properties

- [id](types_NotificationIOS.IOSNotificationAttachment.md#id)
- [thumbnailClippingRect](types_NotificationIOS.IOSNotificationAttachment.md#thumbnailclippingrect)
- [thumbnailHidden](types_NotificationIOS.IOSNotificationAttachment.md#thumbnailhidden)
- [thumbnailTime](types_NotificationIOS.IOSNotificationAttachment.md#thumbnailtime)
- [typeHint](types_NotificationIOS.IOSNotificationAttachment.md#typehint)
- [url](types_NotificationIOS.IOSNotificationAttachment.md#url)

## Properties

### id

• `Optional` **id**: `string`

A optional unique identifier of the attachment.
If no `id` is provided, a unique id is created for you.

#### Defined in

[src/types/NotificationIOS.ts:591](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L591)

___

### thumbnailClippingRect

• `Optional` **thumbnailClippingRect**: [`IOSAttachmentThumbnailClippingRect`](types_NotificationIOS.IOSAttachmentThumbnailClippingRect.md)

An optional clipping rectangle for a thumbnail image.

#### Defined in

[src/types/NotificationIOS.ts:623](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L623)

___

### thumbnailHidden

• `Optional` **thumbnailHidden**: `boolean`

When set to `true` the thumbnail will be hidden.
Defaults to `false`.

#### Defined in

[src/types/NotificationIOS.ts:618](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L618)

___

### thumbnailTime

• `Optional` **thumbnailTime**: `number`

The frame number of an animation to use as the thumbnail.

For a video, it is the time (in seconds) into the video from which to
grab the thumbnail image.

For a GIF, it is the frame number of the animation to use
as a thumbnail image.

#### Defined in

[src/types/NotificationIOS.ts:634](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L634)

___

### typeHint

• `Optional` **typeHint**: `string`

An optional hint about an attachment’s file type, as as Uniform Type Identifier (UTI).

A list of UTI values can be found [here](https://developer.apple.com/library/archive/documentation/Miscellaneous/Reference/UTIRef/Articles/System-DeclaredUniformTypeIdentifiers.html) e.g. for JPEG you'd use `public.jpeg` as the `typeHint` value.

If you do not include this key, the attachment’s filename extension is used to determine its type.

#### Defined in

[src/types/NotificationIOS.ts:612](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L612)

___

### url

• **url**: `string`

A URL to the media file to display.

The value can be any of the following:

 - An absolute path to a file on the device
 - iOS resource

For a list of supported file types, see [Supported File Types](https://developer.apple.com/documentation/usernotifications/unnotificationattachment#1682051) on the official Apple documentation for more information.

#### Defined in

[src/types/NotificationIOS.ts:603](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationIOS.ts#L603)
