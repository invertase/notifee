[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/Module](../modules/types_Module.md) / ModuleWithStatics

# Interface: ModuleWithStatics

[types/Module](../modules/types_Module.md).ModuleWithStatics

## Hierarchy

- [`Module`](types_Module.Module.md)

- [`ModuleStatics`](types_Module.ModuleStatics.md)

  ↳ **`ModuleWithStatics`**

## Table of contents

### Properties

- [SDK\_VERSION](types_Module.ModuleWithStatics.md#sdk_version)

### Methods

- [cancelAllNotifications](types_Module.ModuleWithStatics.md#cancelallnotifications)
- [cancelDisplayedNotification](types_Module.ModuleWithStatics.md#canceldisplayednotification)
- [cancelDisplayedNotifications](types_Module.ModuleWithStatics.md#canceldisplayednotifications)
- [cancelNotification](types_Module.ModuleWithStatics.md#cancelnotification)
- [cancelTriggerNotification](types_Module.ModuleWithStatics.md#canceltriggernotification)
- [cancelTriggerNotifications](types_Module.ModuleWithStatics.md#canceltriggernotifications)
- [createChannel](types_Module.ModuleWithStatics.md#createchannel)
- [createChannelGroup](types_Module.ModuleWithStatics.md#createchannelgroup)
- [createChannelGroups](types_Module.ModuleWithStatics.md#createchannelgroups)
- [createChannels](types_Module.ModuleWithStatics.md#createchannels)
- [createTriggerNotification](types_Module.ModuleWithStatics.md#createtriggernotification)
- [decrementBadgeCount](types_Module.ModuleWithStatics.md#decrementbadgecount)
- [deleteChannel](types_Module.ModuleWithStatics.md#deletechannel)
- [deleteChannelGroup](types_Module.ModuleWithStatics.md#deletechannelgroup)
- [displayNotification](types_Module.ModuleWithStatics.md#displaynotification)
- [getBadgeCount](types_Module.ModuleWithStatics.md#getbadgecount)
- [getChannel](types_Module.ModuleWithStatics.md#getchannel)
- [getChannelGroup](types_Module.ModuleWithStatics.md#getchannelgroup)
- [getChannelGroups](types_Module.ModuleWithStatics.md#getchannelgroups)
- [getChannels](types_Module.ModuleWithStatics.md#getchannels)
- [getDisplayedNotifications](types_Module.ModuleWithStatics.md#getdisplayednotifications)
- [getInitialNotification](types_Module.ModuleWithStatics.md#getinitialnotification)
- [getNotificationCategories](types_Module.ModuleWithStatics.md#getnotificationcategories)
- [getNotificationSettings](types_Module.ModuleWithStatics.md#getnotificationsettings)
- [getPowerManagerInfo](types_Module.ModuleWithStatics.md#getpowermanagerinfo)
- [getTriggerNotificationIds](types_Module.ModuleWithStatics.md#gettriggernotificationids)
- [getTriggerNotifications](types_Module.ModuleWithStatics.md#gettriggernotifications)
- [hideNotificationDrawer](types_Module.ModuleWithStatics.md#hidenotificationdrawer)
- [incrementBadgeCount](types_Module.ModuleWithStatics.md#incrementbadgecount)
- [isBatteryOptimizationEnabled](types_Module.ModuleWithStatics.md#isbatteryoptimizationenabled)
- [isChannelBlocked](types_Module.ModuleWithStatics.md#ischannelblocked)
- [isChannelCreated](types_Module.ModuleWithStatics.md#ischannelcreated)
- [onBackgroundEvent](types_Module.ModuleWithStatics.md#onbackgroundevent)
- [onForegroundEvent](types_Module.ModuleWithStatics.md#onforegroundevent)
- [openBatteryOptimizationSettings](types_Module.ModuleWithStatics.md#openbatteryoptimizationsettings)
- [openNotificationSettings](types_Module.ModuleWithStatics.md#opennotificationsettings)
- [openPowerManagerSettings](types_Module.ModuleWithStatics.md#openpowermanagersettings)
- [registerForegroundService](types_Module.ModuleWithStatics.md#registerforegroundservice)
- [requestPermission](types_Module.ModuleWithStatics.md#requestpermission)
- [setBadgeCount](types_Module.ModuleWithStatics.md#setbadgecount)
- [setNotificationCategories](types_Module.ModuleWithStatics.md#setnotificationcategories)
- [stopForegroundService](types_Module.ModuleWithStatics.md#stopforegroundservice)

## Properties

### SDK\_VERSION

• **SDK\_VERSION**: `string`

Returns the current Notifee SDK version in use.

#### Inherited from

[ModuleStatics](types_Module.ModuleStatics.md).[SDK_VERSION](types_Module.ModuleStatics.md#sdk_version)

#### Defined in

[src/types/Module.ts:685](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L685)

## Methods

### cancelAllNotifications

▸ **cancelAllNotifications**(`notificationIds?`): `Promise`<`void`\>

API used to cancel all notifications.

The `cancelAllNotifications` API removes any displayed notifications from the users device and
any pending trigger notifications.

This method does not cancel Android [Foreground Service](/react-native/docs/android/foreground-service)
notifications.

#### Parameters

| Name | Type |
| :------ | :------ |
| `notificationIds?` | `string`[] |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[cancelAllNotifications](types_Module.Module.md#cancelallnotifications)

#### Defined in

[src/types/Module.ts:31](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L31)

___

### cancelDisplayedNotification

▸ **cancelDisplayedNotification**(`notificationId`): `Promise`<`void`\>

API used to cancel a single displayed notification.

This method does not cancel [Foreground Service](/react-native/docs/android/foreground-service)
notifications.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `notificationId` | `string` | The unique notification ID. This is automatically generated and returned when creating a notification, or has been set manually via the `id` property. |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[cancelDisplayedNotification](types_Module.Module.md#canceldisplayednotification)

#### Defined in

[src/types/Module.ts:70](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L70)

___

### cancelDisplayedNotifications

▸ **cancelDisplayedNotifications**(`notificationIds?`): `Promise`<`void`\>

API used to cancel any displayed notifications.

This method does not cancel Android [Foreground Service](/react-native/docs/android/foreground-service)
notifications.

#### Parameters

| Name | Type |
| :------ | :------ |
| `notificationIds?` | `string`[] |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[cancelDisplayedNotifications](types_Module.Module.md#canceldisplayednotifications)

#### Defined in

[src/types/Module.ts:39](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L39)

___

### cancelNotification

▸ **cancelNotification**(`notificationId`): `Promise`<`void`\>

API used to cancel a single notification.

The `cancelNotification` API removes removes any displayed notifications or ones with triggers
set for the specified ID.

This method does not cancel [Foreground Service](/react-native/docs/android/foreground-service)
notifications.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `notificationId` | `string` | The unique notification ID. This is automatically generated and returned when creating a notification, or has been set manually via the `id` property. |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[cancelNotification](types_Module.Module.md#cancelnotification)

#### Defined in

[src/types/Module.ts:58](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L58)

___

### cancelTriggerNotification

▸ **cancelTriggerNotification**(`notificationId`): `Promise`<`void`\>

API used to cancel a single trigger notification.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `notificationId` | `string` | The unique notification ID. This is automatically generated and returned when creating a notification, or has been set manually via the `id` property. |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[cancelTriggerNotification](types_Module.Module.md#canceltriggernotification)

#### Defined in

[src/types/Module.ts:79](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L79)

___

### cancelTriggerNotifications

▸ **cancelTriggerNotifications**(`notificationIds?`): `Promise`<`void`\>

API used to cancel any trigger notifications.

#### Parameters

| Name | Type |
| :------ | :------ |
| `notificationIds?` | `string`[] |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[cancelTriggerNotifications](types_Module.Module.md#canceltriggernotifications)

#### Defined in

[src/types/Module.ts:44](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L44)

___

### createChannel

▸ **createChannel**(`channel`): `Promise`<`string`\>

API to create and update channels on supported Android devices.

Creates a new Android channel. Channels are used to collectively assign notifications to
a single responsible channel. Users can manage settings for channels, e.g. disabling sound or vibration.
Channels can be further organized into groups (see `createChannelGroup`).

By providing a `groupId` property, channels can be assigned to groups created with
[`createChannelGroup`](/react-native/reference/createchannelgroup).

The channel ID is returned once the operation has completed.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

**`platform`** android

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `channel` | [`AndroidChannel`](types_NotificationAndroid.AndroidChannel.md) | The [`AndroidChannel`](/react-native/reference/androidchannel) interface used to create/update a group. |

#### Returns

`Promise`<`string`\>

#### Inherited from

[Module](types_Module.Module.md).[createChannel](types_Module.Module.md#createchannel)

#### Defined in

[src/types/Module.ts:98](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L98)

___

### createChannelGroup

▸ **createChannelGroup**(`channelGroup`): `Promise`<`string`\>

API to create or update a channel group on supported Android devices.

Creates a new Android channel group. Groups are used to further organize the appearance of your
channels in the settings UI. Groups allow users to easily identify and control multiple
notification channels.

Channels can be assigned to groups during creation using the
[`createChannel`](/react-native/reference/createchannel) method.

The channel group ID is returned once the operation has completed.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

**`platform`** android

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `channelGroup` | [`AndroidChannelGroup`](types_NotificationAndroid.AndroidChannelGroup.md) | The [`AndroidChannelGroup`](/react-native/reference/androidchannelgroup) interface used to create/update a group. |

#### Returns

`Promise`<`string`\>

#### Inherited from

[Module](types_Module.Module.md).[createChannelGroup](types_Module.Module.md#createchannelgroup)

#### Defined in

[src/types/Module.ts:129](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L129)

___

### createChannelGroups

▸ **createChannelGroups**(`channelGroups`): `Promise`<`void`\>

API to create and update multiple channel groups on supported Android devices.

This API is used to perform a single operation to create or update channel groups. See the
[`createChannelGroup`](/react-native/reference/createchannelgroup) documentation for more information.

**`platform`** android

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `channelGroups` | [`AndroidChannelGroup`](types_NotificationAndroid.AndroidChannelGroup.md)[] | An array of [`AndroidChannelGroup`](/react-native/reference/androidchannelgroup) interfaces. |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[createChannelGroups](types_Module.Module.md#createchannelgroups)

#### Defined in

[src/types/Module.ts:140](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L140)

___

### createChannels

▸ **createChannels**(`channels`): `Promise`<`void`\>

API to create and update multiple channels on supported Android devices.

This API is used to perform a single operation to create or update channels. See the
[`createChannel`](/react-native/reference/createchannel) documentation for more information.

**`platform`** android

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `channels` | [`AndroidChannel`](types_NotificationAndroid.AndroidChannel.md)[] | An array of [`AndroidChannel`](/react-native/reference/androidchannel) interfaces. |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[createChannels](types_Module.Module.md#createchannels)

#### Defined in

[src/types/Module.ts:109](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L109)

___

### createTriggerNotification

▸ **createTriggerNotification**(`notification`, `trigger`): `Promise`<`string`\>

API used to create a trigger notification.

All channels/categories should be created before calling this method during the apps lifecycle.

View the [Triggers](/react-native/docs/triggers) documentation for more information.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `notification` | [`Notification`](types_Notification.Notification.md) | The [`Notification`](/react-native/reference/notification) interface used to create a notification. |
| `trigger` | [`Trigger`](../modules/types_Trigger.md#trigger) | The [`Trigger`](/react-native/reference/trigger) interface used to create a trigger. |

#### Returns

`Promise`<`string`\>

#### Inherited from

[Module](types_Module.Module.md).[createTriggerNotification](types_Module.Module.md#createtriggernotification)

#### Defined in

[src/types/Module.ts:200](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L200)

___

### decrementBadgeCount

▸ **decrementBadgeCount**(`decrementBy?`): `Promise`<`void`\>

Decrements the badge count for this application on the current device by a specified
value.

Defaults to an decrement of `1`.

**`platform`** ios

#### Parameters

| Name | Type |
| :------ | :------ |
| `decrementBy?` | `number` |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[decrementBadgeCount](types_Module.Module.md#decrementbadgecount)

#### Defined in

[src/types/Module.ts:499](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L499)

___

### deleteChannel

▸ **deleteChannel**(`channelId`): `Promise`<`void`\>

API used to delete a channel by ID on supported Android devices.

Channels can be deleted using this API by providing the channel ID. Channel information (including
the ID) can be retrieved from the [`getChannels`](/react-native/reference/getchannels) API.

> When a channel is deleted, notifications assigned to that channel will fail to display.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

**`platform`** android

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `channelId` | `string` | The unique channel ID to delete. |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[deleteChannel](types_Module.Module.md#deletechannel)

#### Defined in

[src/types/Module.ts:155](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L155)

___

### deleteChannelGroup

▸ **deleteChannelGroup**(`channelGroupId`): `Promise`<`void`\>

API used to delete a channel group by ID on supported Android devices.

Channel groups can be deleted using this API by providing the channel ID. Channel information (including
the ID) can be retrieved from the [`getChannels`](/react-native/reference/getchannels) API.

Deleting a group does not delete channels which are assigned to the group, they will instead be
unassigned the group and continue to function as expected.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

**`platform`** android

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `channelGroupId` | `string` | The unique channel group ID to delete. |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[deleteChannelGroup](types_Module.Module.md#deletechannelgroup)

#### Defined in

[src/types/Module.ts:171](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L171)

___

### displayNotification

▸ **displayNotification**(`notification`): `Promise`<`string`\>

API used to immediately display or update a notification on the users device.

This API is used to display a notification on the users device. All
channels/categories should be created before triggering this method during the apps lifecycle.

View the [Displaying a Notification](/react-native/docs/displaying-a-notification)
documentation for more information.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `notification` | [`Notification`](types_Notification.Notification.md) | The [`Notification`](/react-native/reference/notification) interface used to create a notification for both Android & iOS. |

#### Returns

`Promise`<`string`\>

#### Inherited from

[Module](types_Module.Module.md).[displayNotification](types_Module.Module.md#displaynotification)

#### Defined in

[src/types/Module.ts:185](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L185)

___

### getBadgeCount

▸ **getBadgeCount**(): `Promise`<`number`\>

Get the current badge count value for this application on the current device.

Returns `0` on Android.

**`platform`** ios

#### Returns

`Promise`<`number`\>

#### Inherited from

[Module](types_Module.Module.md).[getBadgeCount](types_Module.Module.md#getbadgecount)

#### Defined in

[src/types/Module.ts:465](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L465)

___

### getChannel

▸ **getChannel**(`channelId`): `Promise`<``null`` \| [`NativeAndroidChannel`](types_NotificationAndroid.NativeAndroidChannel.md)\>

API used to return a channel on supported Android devices.

This API is used to return a `NativeAndroidChannel`. Returns `null` if no channel could be matched to
the given ID.

A "native channel" also includes additional properties about the channel at the time it's
retrieved from the device. View the [`NativeAndroidChannel`](/react-native/reference/nativeandroidchannel)
documentation for more information.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

**`platform`** android

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `channelId` | `string` | The channel ID created with [`createChannel`](/react-native/reference/createchannel). If a unknown channel ID is provided, `null` is returned. |

#### Returns

`Promise`<``null`` \| [`NativeAndroidChannel`](types_NotificationAndroid.NativeAndroidChannel.md)\>

#### Inherited from

[Module](types_Module.Module.md).[getChannel](types_Module.Module.md#getchannel)

#### Defined in

[src/types/Module.ts:235](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L235)

___

### getChannelGroup

▸ **getChannelGroup**(`channelGroupId`): `Promise`<``null`` \| [`NativeAndroidChannelGroup`](types_NotificationAndroid.NativeAndroidChannelGroup.md)\>

API used to return a channel group on supported Android devices.

This API is used to return an `NativeAndroidChannelGroup`. Returns `null` if no channel could be matched to
the given ID.

A "native channel group" also includes additional properties about the channel group at the time it's
retrieved from the device. View the [`NativeAndroidChannelGroup`](/react-native/reference/nativeandroidchannelgroup)
documentation for more information.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

**`platform`** android

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `channelGroupId` | `string` | The channel ID created with [`createChannelGroup`](/react-native/reference/createchannelgroup). If a unknown channel group ID is provided, `null` is returned. |

#### Returns

`Promise`<``null`` \| [`NativeAndroidChannelGroup`](types_NotificationAndroid.NativeAndroidChannelGroup.md)\>

#### Inherited from

[Module](types_Module.Module.md).[getChannelGroup](types_Module.Module.md#getchannelgroup)

#### Defined in

[src/types/Module.ts:287](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L287)

___

### getChannelGroups

▸ **getChannelGroups**(): `Promise`<[`NativeAndroidChannelGroup`](types_NotificationAndroid.NativeAndroidChannelGroup.md)[]\>

API used to return all channel groups on supported Android devices.

This API is used to return a `NativeAndroidChannelGroup`. Returns an empty array if no channel
groups exist.

A "native channel group" also includes additional properties about the channel group at the time it's
retrieved from the device. View the [`NativeAndroidChannelGroup`](/react-native/reference/nativeandroidchannelgroup)
documentation for more information.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

**`platform`** android

#### Returns

`Promise`<[`NativeAndroidChannelGroup`](types_NotificationAndroid.NativeAndroidChannelGroup.md)[]\>

#### Inherited from

[Module](types_Module.Module.md).[getChannelGroups](types_Module.Module.md#getchannelgroups)

#### Defined in

[src/types/Module.ts:303](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L303)

___

### getChannels

▸ **getChannels**(): `Promise`<[`NativeAndroidChannel`](types_NotificationAndroid.NativeAndroidChannel.md)[]\>

API used to return all channels on supported Android devices.

This API is used to return a `NativeAndroidChannel`. Returns an empty array if no channels
exist.

A "native channel" also includes additional properties about the channel at the time it's
retrieved from the device. View the [`NativeAndroidChannel`](/react-native/reference/nativeandroidchannel)
documentation for more information.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

**`platform`** android

#### Returns

`Promise`<[`NativeAndroidChannel`](types_NotificationAndroid.NativeAndroidChannel.md)[]\>

#### Inherited from

[Module](types_Module.Module.md).[getChannels](types_Module.Module.md#getchannels)

#### Defined in

[src/types/Module.ts:269](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L269)

___

### getDisplayedNotifications

▸ **getDisplayedNotifications**(): `Promise`<[`DisplayedNotification`](types_Notification.DisplayedNotification.md)[]\>

API used to return the notifications that are displayed.

#### Returns

`Promise`<[`DisplayedNotification`](types_Notification.DisplayedNotification.md)[]\>

#### Inherited from

[Module](types_Module.Module.md).[getDisplayedNotifications](types_Module.Module.md#getdisplayednotifications)

#### Defined in

[src/types/Module.ts:212](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L212)

___

### getInitialNotification

▸ **getInitialNotification**(): `Promise`<``null`` \| [`InitialNotification`](types_Notification.InitialNotification.md)\>

API used to fetch the notification which causes the application to open.

This API can be used to fetch which notification & press action has caused the application to
open. The call returns a `null` value when the application wasn't launched by a notification.

Once the initial notification has been consumed by this API, it is removed and will no longer
be available. It will also be removed if the user relaunches the application.

View the [App open events](/react-native/docs/events#app-open-events) documentation for more
information and example usage.

#### Returns

`Promise`<``null`` \| [`InitialNotification`](types_Notification.InitialNotification.md)\>

#### Inherited from

[Module](types_Module.Module.md).[getInitialNotification](types_Module.Module.md#getinitialnotification)

#### Defined in

[src/types/Module.ts:317](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L317)

___

### getNotificationCategories

▸ **getNotificationCategories**(): `Promise`<[`IOSNotificationCategory`](types_NotificationIOS.IOSNotificationCategory.md)[]\>

Gets the currently set notification categories on this Apple device.

Returns an empty array on Android.

**`platform`** ios

#### Returns

`Promise`<[`IOSNotificationCategory`](types_NotificationIOS.IOSNotificationCategory.md)[]\>

#### Inherited from

[Module](types_Module.Module.md).[getNotificationCategories](types_Module.Module.md#getnotificationcategories)

#### Defined in

[src/types/Module.ts:446](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L446)

___

### getNotificationSettings

▸ **getNotificationSettings**(): `Promise`<[`IOSNotificationSettings`](types_NotificationIOS.IOSNotificationSettings.md)\>

Get the current notification settings for this application on the current device.

On Android, all of the properties on the `IOSNotificationSettings` interface response return
as `AUTHORIZED`.

**`platform`** ios

#### Returns

`Promise`<[`IOSNotificationSettings`](types_NotificationIOS.IOSNotificationSettings.md)\>

#### Inherited from

[Module](types_Module.Module.md).[getNotificationSettings](types_Module.Module.md#getnotificationsettings)

#### Defined in

[src/types/Module.ts:456](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L456)

___

### getPowerManagerInfo

▸ **getPowerManagerInfo**(): `Promise`<[`PowerManagerInfo`](types_PowerManagerInfo.PowerManagerInfo.md)\>

API used to get information about the device and its power manager settings, including manufacturer, model, version and activity.

If `activity` is `null`, `openPowerManagerSettings()` will be noop.

On iOS, an instance of `PowerManagerInfo` will be returned with `activity` set to `null`.

View the [Background Restrictions](/react-native/docs/android/background-restrictions) documentation for more information.

```js
import notifee from `@notifee/react-native`;

const powerManagerInfo = await notifee.getPowerManagerInfo();

if (powerManagerInfo.activity) {
 // 1. ask the user to adjust their Power Manager settings
 // ...

 // 2. open settings
 await notifee.openPowerManagerSettings();
}
```

**`platform`** android

#### Returns

`Promise`<[`PowerManagerInfo`](types_PowerManagerInfo.PowerManagerInfo.md)\>

#### Inherited from

[Module](types_Module.Module.md).[getPowerManagerInfo](types_Module.Module.md#getpowermanagerinfo)

#### Defined in

[src/types/Module.ts:549](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L549)

___

### getTriggerNotificationIds

▸ **getTriggerNotificationIds**(): `Promise`<`string`[]\>

API used to return the ids of trigger notifications that are pending.

View the [Triggers](/react-native/docs/triggers) documentation for more information.

#### Returns

`Promise`<`string`[]\>

#### Inherited from

[Module](types_Module.Module.md).[getTriggerNotificationIds](types_Module.Module.md#gettriggernotificationids)

#### Defined in

[src/types/Module.ts:207](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L207)

___

### getTriggerNotifications

▸ **getTriggerNotifications**(): `Promise`<[`TriggerNotification`](types_Notification.TriggerNotification.md)[]\>

API used to return the trigger notifications that are pending.

#### Returns

`Promise`<[`TriggerNotification`](types_Notification.TriggerNotification.md)[]\>

#### Inherited from

[Module](types_Module.Module.md).[getTriggerNotifications](types_Module.Module.md#gettriggernotifications)

#### Defined in

[src/types/Module.ts:217](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L217)

___

### hideNotificationDrawer

▸ **hideNotificationDrawer**(): `void`

API used to hide the notification drawer, for example,
when the user presses one of the quick actions on the notification, you may wish to hide the drawer.

Please use this functionality carefully as it could potentially be quite intrusive to the user.

Requires the following permission to be added to your `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />
```

```js
import notifee from `@notifee/react-native`;

notifee.hideNotificationDrawer();
```

**`platform`** android

#### Returns

`void`

#### Inherited from

[Module](types_Module.Module.md).[hideNotificationDrawer](types_Module.Module.md#hidenotificationdrawer)

#### Defined in

[src/types/Module.ts:595](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L595)

___

### incrementBadgeCount

▸ **incrementBadgeCount**(`incrementBy?`): `Promise`<`void`\>

Increments the badge count for this application on the current device by a specified
value.

Defaults to an increment of `1`.

**`platform`** ios

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `incrementBy?` | `number` | The value to increment the badge count by. |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[incrementBadgeCount](types_Module.Module.md#incrementbadgecount)

#### Defined in

[src/types/Module.ts:489](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L489)

___

### isBatteryOptimizationEnabled

▸ **isBatteryOptimizationEnabled**(): `Promise`<`boolean`\>

API used to check if battery optimization is enabled for your application.

Supports API versions >= 23.

View the [Background Restrictions](/react-native/docs/android/behaviour#background-restrictions) documentation for more information.

**`platform`** android

#### Returns

`Promise`<`boolean`\>

#### Inherited from

[Module](types_Module.Module.md).[isBatteryOptimizationEnabled](types_Module.Module.md#isbatteryoptimizationenabled)

#### Defined in

[src/types/Module.ts:522](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L522)

___

### isChannelBlocked

▸ **isChannelBlocked**(`channelId`): `Promise`<`boolean`\>

API used to check if a channel is blocked.

On iOS, this will default to false

**`platform`** android

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelId` | `string` |

#### Returns

`Promise`<`boolean`\>

#### Inherited from

[Module](types_Module.Module.md).[isChannelBlocked](types_Module.Module.md#ischannelblocked)

#### Defined in

[src/types/Module.ts:253](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L253)

___

### isChannelCreated

▸ **isChannelCreated**(`channelId`): `Promise`<`boolean`\>

API used to check if a channel is created.

On iOS, this will default to true

**`platform`** android

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelId` | `string` |

#### Returns

`Promise`<`boolean`\>

#### Inherited from

[Module](types_Module.Module.md).[isChannelCreated](types_Module.Module.md#ischannelcreated)

#### Defined in

[src/types/Module.ts:244](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L244)

___

### onBackgroundEvent

▸ **onBackgroundEvent**(`observer`): `void`

API used to handle events when the application is in a background state.

Applications in a background state will use an event handler registered by this API method
to send events. The handler must return a Promise once complete and only a single event handler
can be registered for the application.

View the [Background events](/react-native/docs/events#background-events) documentation for more
information and example usage.

To listen to foreground events, see the [`onForegroundEvent`](/react-native/reference/onforegroundevent) documentation.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `observer` | (`event`: [`Event`](types_Notification.Event.md)) => `Promise`<`void`\> | A Function which returns a Promise, called on a new event when the application is in a background state. |

#### Returns

`void`

#### Inherited from

[Module](types_Module.Module.md).[onBackgroundEvent](types_Module.Module.md#onbackgroundevent)

#### Defined in

[src/types/Module.ts:334](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L334)

___

### onForegroundEvent

▸ **onForegroundEvent**(`observer`): () => `void`

API used to handle events when the application is in a foreground state.

Applications in a foreground state will use an event handler registered by this API method
to send events. Multiple foreground observers can be registered throughout the applications
lifecycle. The method returns a function, used to unsubscribe from further events,

View the [Foreground events](/react-native/docs/events#foreground-events) documentation for more
information and example usage.

To listen to background events, see the [`onBackgroundEvent`](/react-native/reference/onbackgroundevent) documentation.

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `observer` | (`event`: [`Event`](types_Notification.Event.md)) => `void` | A Function which returns a Promise, called on a new event when the application is in a foreground state. |

#### Returns

`fn`

▸ (): `void`

API used to handle events when the application is in a foreground state.

Applications in a foreground state will use an event handler registered by this API method
to send events. Multiple foreground observers can be registered throughout the applications
lifecycle. The method returns a function, used to unsubscribe from further events,

View the [Foreground events](/react-native/docs/events#foreground-events) documentation for more
information and example usage.

To listen to background events, see the [`onBackgroundEvent`](/react-native/reference/onbackgroundevent) documentation.

##### Returns

`void`

#### Inherited from

[Module](types_Module.Module.md).[onForegroundEvent](types_Module.Module.md#onforegroundevent)

#### Defined in

[src/types/Module.ts:351](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L351)

___

### openBatteryOptimizationSettings

▸ **openBatteryOptimizationSettings**(): `Promise`<`void`\>

API used to open the Android System settings for the application.

If the API version is >= 23, the battery optimization settings screen is displayed, otherwise,
this is a no-op & instantly resolves.

View the [Background Restrictions](/react-native/docs/android/behaviour#background-restrictions) documentation for more information.

**`platform`** android

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[openBatteryOptimizationSettings](types_Module.Module.md#openbatteryoptimizationsettings)

#### Defined in

[src/types/Module.ts:511](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L511)

___

### openNotificationSettings

▸ **openNotificationSettings**(`channelId?`): `Promise`<`void`\>

API used to open the Android System settings for the application.

If the API version is >= 26:
- With no `channelId`, the notification settings screen is displayed.
- With a `channelId`, the notification settings screen for the specific channel is displayed.

If the API version is < 26, the application settings screen is displayed. The `channelId`
is ignored.

If an invalid `channelId` is provided (e.g. does not exist), the settings screen will redirect
back to your application.

On iOS, this is a no-op & instantly resolves.

**`platform`** android

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `channelId?` | `string` | The ID of the channel which will be opened. Can be ignored/omitted to display the overall notification settings. |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[openNotificationSettings](types_Module.Module.md#opennotificationsettings)

#### Defined in

[src/types/Module.ts:372](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L372)

___

### openPowerManagerSettings

▸ **openPowerManagerSettings**(): `Promise`<`void`\>

API used to navigate to the appropriate Android System settings for the device.

Call `getPowerManagerInfo()` first to find out if the user's device is supported.

View the [Background Restrictions](/react-native/docs/android/background-restrictions) documentation for more information.

```js
import notifee from `@notifee/react-native`;

const powerManagerInfo = await notifee.getPowerManagerInfo

if (powerManagerInfo.activity) {
// 1. ask the user to adjust their Power Manager settings
// ...

// 2. if yes, navigate them to settings
await notifee.openPowerManagerSettings();
}
```

**`platform`** android

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[openPowerManagerSettings](types_Module.Module.md#openpowermanagersettings)

#### Defined in

[src/types/Module.ts:574](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L574)

___

### registerForegroundService

▸ **registerForegroundService**(`task`): `void`

API used to register a foreground service on Android devices.

This method is used to register a long running task which can be used with Foreground Service
notifications.

Only a single foreground service can exist for the application, and calling this method more
than once will update the existing task runner.

View the [Foreground Service](/react-native/docs/android/foreground-service) documentation for
more information.

**`platform`** android

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `task` | [`ForegroundServiceTask`](../modules/types_Notification.md#foregroundservicetask) | The runner function which runs for the duration of the service's lifetime. |

#### Returns

`void`

#### Inherited from

[Module](types_Module.Module.md).[registerForegroundService](types_Module.Module.md#registerforegroundservice)

#### Defined in

[src/types/Module.ts:389](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L389)

___

### requestPermission

▸ **requestPermission**(`permissions?`): `Promise`<[`IOSNotificationSettings`](types_NotificationIOS.IOSNotificationSettings.md)\>

Request specific notification permissions for your application on the current device.

Both iOS & Android return an `IOSNotificationSettings` interface. To check whether overall
permission was granted, check the `authorizationStatus` property in the response:

```js
import notifee, { IOSAuthorizationStatus } from '@notifee/react-native';

const settings = await notifee.requestPermission(...);

if (settings.authorizationStatus === IOSAuthorizationStatus.DENIED) {
  console.log('User denied permissions request');
} else if (settings.authorizationStatus === IOSAuthorizationStatus.AUTHORIZED) {
   console.log('User granted permissions request');
} else if (settings.authorizationStatus === IOSAuthorizationStatus.PROVISIONAL) {
   console.log('User provisionally granted permissions request');
}
```

Use the other `IOSNotificationSettings` properties to view which specific permissions were
allowed.

On Android, all of the properties on the `IOSNotificationSettings` interface response return
as `AUTHORIZED`.

**`platform`** ios

#### Parameters

| Name | Type |
| :------ | :------ |
| `permissions?` | [`IOSNotificationPermissions`](types_NotificationIOS.IOSNotificationPermissions.md) |

#### Returns

`Promise`<[`IOSNotificationSettings`](types_NotificationIOS.IOSNotificationSettings.md)\>

#### Inherited from

[Module](types_Module.Module.md).[requestPermission](types_Module.Module.md#requestpermission)

#### Defined in

[src/types/Module.ts:428](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L428)

___

### setBadgeCount

▸ **setBadgeCount**(`count`): `Promise`<`void`\>

Set the badge count value for this application on the current device.

If set to zero, the badge count is removed from the device. The count must also
be a positive number.

**`platform`** ios

#### Parameters

| Name | Type | Description |
| :------ | :------ | :------ |
| `count` | `number` | The number value to set as the badge count. |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[setBadgeCount](types_Module.Module.md#setbadgecount)

#### Defined in

[src/types/Module.ts:477](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L477)

___

### setNotificationCategories

▸ **setNotificationCategories**(`categories`): `Promise`<`void`\>

Set the notification categories to be used on this Apple device.

**`platform`** ios

#### Parameters

| Name | Type |
| :------ | :------ |
| `categories` | [`IOSNotificationCategory`](types_NotificationIOS.IOSNotificationCategory.md)[] |

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[setNotificationCategories](types_Module.Module.md#setnotificationcategories)

#### Defined in

[src/types/Module.ts:437](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L437)

___

### stopForegroundService

▸ **stopForegroundService**(): `Promise`<`void`\>

Call this to stop the foreground service that is running

**`platform`** android

#### Returns

`Promise`<`void`\>

#### Inherited from

[Module](types_Module.Module.md).[stopForegroundService](types_Module.Module.md#stopforegroundservice)

#### Defined in

[src/types/Module.ts:397](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L397)
