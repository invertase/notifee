[@notifee/react-native](../README.md) / [Modules](../modules.md) / [NotifeeApiModule](../modules/NotifeeApiModule.md) / default

# Class: default

[NotifeeApiModule](../modules/NotifeeApiModule.md).default

## Hierarchy

- `NotifeeNativeModule`

  ↳ **`default`**

## Implements

- [`Module`](../interfaces/types_Module.Module.md)

## Table of contents

### Constructors

- [constructor](NotifeeApiModule.default.md#constructor)

### Accessors

- [config](NotifeeApiModule.default.md#config)
- [emitter](NotifeeApiModule.default.md#emitter)
- [native](NotifeeApiModule.default.md#native)

### Methods

- [cancelAllNotifications](NotifeeApiModule.default.md#cancelallnotifications)
- [cancelDisplayedNotification](NotifeeApiModule.default.md#canceldisplayednotification)
- [cancelDisplayedNotifications](NotifeeApiModule.default.md#canceldisplayednotifications)
- [cancelNotification](NotifeeApiModule.default.md#cancelnotification)
- [cancelTriggerNotification](NotifeeApiModule.default.md#canceltriggernotification)
- [cancelTriggerNotifications](NotifeeApiModule.default.md#canceltriggernotifications)
- [createChannel](NotifeeApiModule.default.md#createchannel)
- [createChannelGroup](NotifeeApiModule.default.md#createchannelgroup)
- [createChannelGroups](NotifeeApiModule.default.md#createchannelgroups)
- [createChannels](NotifeeApiModule.default.md#createchannels)
- [createTriggerNotification](NotifeeApiModule.default.md#createtriggernotification)
- [decrementBadgeCount](NotifeeApiModule.default.md#decrementbadgecount)
- [deleteChannel](NotifeeApiModule.default.md#deletechannel)
- [deleteChannelGroup](NotifeeApiModule.default.md#deletechannelgroup)
- [displayNotification](NotifeeApiModule.default.md#displaynotification)
- [getBadgeCount](NotifeeApiModule.default.md#getbadgecount)
- [getChannel](NotifeeApiModule.default.md#getchannel)
- [getChannelGroup](NotifeeApiModule.default.md#getchannelgroup)
- [getChannelGroups](NotifeeApiModule.default.md#getchannelgroups)
- [getChannels](NotifeeApiModule.default.md#getchannels)
- [getDisplayedNotifications](NotifeeApiModule.default.md#getdisplayednotifications)
- [getInitialNotification](NotifeeApiModule.default.md#getinitialnotification)
- [getNotificationCategories](NotifeeApiModule.default.md#getnotificationcategories)
- [getNotificationSettings](NotifeeApiModule.default.md#getnotificationsettings)
- [getPowerManagerInfo](NotifeeApiModule.default.md#getpowermanagerinfo)
- [getTriggerNotificationIds](NotifeeApiModule.default.md#gettriggernotificationids)
- [getTriggerNotifications](NotifeeApiModule.default.md#gettriggernotifications)
- [hideNotificationDrawer](NotifeeApiModule.default.md#hidenotificationdrawer)
- [incrementBadgeCount](NotifeeApiModule.default.md#incrementbadgecount)
- [isBatteryOptimizationEnabled](NotifeeApiModule.default.md#isbatteryoptimizationenabled)
- [isChannelBlocked](NotifeeApiModule.default.md#ischannelblocked)
- [isChannelCreated](NotifeeApiModule.default.md#ischannelcreated)
- [onBackgroundEvent](NotifeeApiModule.default.md#onbackgroundevent)
- [onForegroundEvent](NotifeeApiModule.default.md#onforegroundevent)
- [openBatteryOptimizationSettings](NotifeeApiModule.default.md#openbatteryoptimizationsettings)
- [openNotificationSettings](NotifeeApiModule.default.md#opennotificationsettings)
- [openPowerManagerSettings](NotifeeApiModule.default.md#openpowermanagersettings)
- [registerForegroundService](NotifeeApiModule.default.md#registerforegroundservice)
- [requestPermission](NotifeeApiModule.default.md#requestpermission)
- [setBadgeCount](NotifeeApiModule.default.md#setbadgecount)
- [setNotificationCategories](NotifeeApiModule.default.md#setnotificationcategories)
- [stopForegroundService](NotifeeApiModule.default.md#stopforegroundservice)

## Constructors

### constructor

• **new default**(`config`)

#### Parameters

| Name | Type |
| :------ | :------ |
| `config` | `NativeModuleConfig` |

#### Overrides

NotifeeNativeModule.constructor

#### Defined in

[src/NotifeeApiModule.ts:66](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L66)

## Accessors

### config

• `get` **config**(): [`JsonConfig`](../interfaces/types_Module.JsonConfig.md)

#### Returns

[`JsonConfig`](../interfaces/types_Module.JsonConfig.md)

#### Defined in

[src/NotifeeNativeModule.ts:42](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeNativeModule.ts#L42)

___

### emitter

• `get` **emitter**(): `EventEmitter`

#### Returns

`EventEmitter`

#### Defined in

[src/NotifeeNativeModule.ts:52](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeNativeModule.ts#L52)

___

### native

• `get` **native**(): `NativeModulesStatic`

#### Returns

`NativeModulesStatic`

#### Defined in

[src/NotifeeNativeModule.ts:56](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeNativeModule.ts#L56)

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

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[cancelAllNotifications](../interfaces/types_Module.Module.md#cancelallnotifications)

#### Defined in

[src/NotifeeApiModule.ts:135](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L135)

___

### cancelDisplayedNotification

▸ **cancelDisplayedNotification**(`notificationId`): `Promise`<`void`\>

API used to cancel a single displayed notification.

This method does not cancel [Foreground Service](/react-native/docs/android/foreground-service)
notifications.

#### Parameters

| Name | Type |
| :------ | :------ |
| `notificationId` | `string` |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[cancelDisplayedNotification](../interfaces/types_Module.Module.md#canceldisplayednotification)

#### Defined in

[src/NotifeeApiModule.ts:158](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L158)

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

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[cancelDisplayedNotifications](../interfaces/types_Module.Module.md#canceldisplayednotifications)

#### Defined in

[src/NotifeeApiModule.ts:140](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L140)

___

### cancelNotification

▸ **cancelNotification**(`notificationId`): `Promise`<`void`\>

API used to cancel a single notification.

The `cancelNotification` API removes removes any displayed notifications or ones with triggers
set for the specified ID.

This method does not cancel [Foreground Service](/react-native/docs/android/foreground-service)
notifications.

#### Parameters

| Name | Type |
| :------ | :------ |
| `notificationId` | `string` |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[cancelNotification](../interfaces/types_Module.Module.md#cancelnotification)

#### Defined in

[src/NotifeeApiModule.ts:150](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L150)

___

### cancelTriggerNotification

▸ **cancelTriggerNotification**(`notificationId`): `Promise`<`void`\>

API used to cancel a single trigger notification.

#### Parameters

| Name | Type |
| :------ | :------ |
| `notificationId` | `string` |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[cancelTriggerNotification](../interfaces/types_Module.Module.md#canceltriggernotification)

#### Defined in

[src/NotifeeApiModule.ts:168](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L168)

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

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[cancelTriggerNotifications](../interfaces/types_Module.Module.md#canceltriggernotifications)

#### Defined in

[src/NotifeeApiModule.ts:145](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L145)

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

#### Parameters

| Name | Type |
| :------ | :------ |
| `channel` | [`AndroidChannel`](../interfaces/types_NotificationAndroid.AndroidChannel.md) |

#### Returns

`Promise`<`string`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[createChannel](../interfaces/types_Module.Module.md#createchannel)

#### Defined in

[src/NotifeeApiModule.ts:178](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L178)

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

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelGroup` | [`AndroidChannelGroup`](../interfaces/types_NotificationAndroid.AndroidChannelGroup.md) |

#### Returns

`Promise`<`string`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[createChannelGroup](../interfaces/types_Module.Module.md#createchannelgroup)

#### Defined in

[src/NotifeeApiModule.ts:220](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L220)

___

### createChannelGroups

▸ **createChannelGroups**(`channelGroups`): `Promise`<`void`\>

API to create and update multiple channel groups on supported Android devices.

This API is used to perform a single operation to create or update channel groups. See the
[`createChannelGroup`](/react-native/reference/createchannelgroup) documentation for more information.

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelGroups` | [`AndroidChannelGroup`](../interfaces/types_NotificationAndroid.AndroidChannelGroup.md)[] |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[createChannelGroups](../interfaces/types_Module.Module.md#createchannelgroups)

#### Defined in

[src/NotifeeApiModule.ts:241](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L241)

___

### createChannels

▸ **createChannels**(`channels`): `Promise`<`void`\>

API to create and update multiple channels on supported Android devices.

This API is used to perform a single operation to create or update channels. See the
[`createChannel`](/react-native/reference/createchannel) documentation for more information.

#### Parameters

| Name | Type |
| :------ | :------ |
| `channels` | [`AndroidChannel`](../interfaces/types_NotificationAndroid.AndroidChannel.md)[] |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[createChannels](../interfaces/types_Module.Module.md#createchannels)

#### Defined in

[src/NotifeeApiModule.ts:199](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L199)

___

### createTriggerNotification

▸ **createTriggerNotification**(`notification`, `trigger`): `Promise`<`string`\>

API used to create a trigger notification.

All channels/categories should be created before calling this method during the apps lifecycle.

View the [Triggers](/react-native/docs/triggers) documentation for more information.

#### Parameters

| Name | Type |
| :------ | :------ |
| `notification` | [`Notification`](../interfaces/types_Notification.Notification.md) |
| `trigger` | [`Trigger`](../modules/types_Trigger.md#trigger) |

#### Returns

`Promise`<`string`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[createTriggerNotification](../interfaces/types_Module.Module.md#createtriggernotification)

#### Defined in

[src/NotifeeApiModule.ts:303](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L303)

___

### decrementBadgeCount

▸ **decrementBadgeCount**(`decrementBy?`): `Promise`<`void`\>

Decrements the badge count for this application on the current device by a specified
value.

Defaults to an decrement of `1`.

#### Parameters

| Name | Type |
| :------ | :------ |
| `decrementBy?` | `number` |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[decrementBadgeCount](../interfaces/types_Module.Module.md#decrementbadgecount)

#### Defined in

[src/NotifeeApiModule.ts:547](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L547)

___

### deleteChannel

▸ **deleteChannel**(`channelId`): `Promise`<`void`\>

API used to delete a channel by ID on supported Android devices.

Channels can be deleted using this API by providing the channel ID. Channel information (including
the ID) can be retrieved from the [`getChannels`](/react-native/reference/getchannels) API.

> When a channel is deleted, notifications assigned to that channel will fail to display.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelId` | `string` |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[deleteChannel](../interfaces/types_Module.Module.md#deletechannel)

#### Defined in

[src/NotifeeApiModule.ts:266](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L266)

___

### deleteChannelGroup

▸ **deleteChannelGroup**(`channelGroupId`): `Promise`<`void`\>

API used to delete a channel group by ID on supported Android devices.

Channel groups can be deleted using this API by providing the channel ID. Channel information (including
the ID) can be retrieved from the [`getChannels`](/react-native/reference/getchannels) API.

Deleting a group does not delete channels which are assigned to the group, they will instead be
unassigned the group and continue to function as expected.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelGroupId` | `string` |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[deleteChannelGroup](../interfaces/types_Module.Module.md#deletechannelgroup)

#### Defined in

[src/NotifeeApiModule.ts:278](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L278)

___

### displayNotification

▸ **displayNotification**(`notification`): `Promise`<`string`\>

API used to immediately display or update a notification on the users device.

This API is used to display a notification on the users device. All
channels/categories should be created before triggering this method during the apps lifecycle.

View the [Displaying a Notification](/react-native/docs/displaying-a-notification)
documentation for more information.

#### Parameters

| Name | Type |
| :------ | :------ |
| `notification` | [`Notification`](../interfaces/types_Notification.Notification.md) |

#### Returns

`Promise`<`string`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[displayNotification](../interfaces/types_Module.Module.md#displaynotification)

#### Defined in

[src/NotifeeApiModule.ts:290](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L290)

___

### getBadgeCount

▸ **getBadgeCount**(): `Promise`<`number`\>

Get the current badge count value for this application on the current device.

Returns `0` on Android.

#### Returns

`Promise`<`number`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getBadgeCount](../interfaces/types_Module.Module.md#getbadgecount)

#### Defined in

[src/NotifeeApiModule.ts:508](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L508)

___

### getChannel

▸ **getChannel**(`channelId`): `Promise`<``null`` \| [`NativeAndroidChannel`](../interfaces/types_NotificationAndroid.NativeAndroidChannel.md)\>

API used to return a channel on supported Android devices.

This API is used to return a `NativeAndroidChannel`. Returns `null` if no channel could be matched to
the given ID.

A "native channel" also includes additional properties about the channel at the time it's
retrieved from the device. View the [`NativeAndroidChannel`](/react-native/reference/nativeandroidchannel)
documentation for more information.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelId` | `string` |

#### Returns

`Promise`<``null`` \| [`NativeAndroidChannel`](../interfaces/types_NotificationAndroid.NativeAndroidChannel.md)\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getChannel](../interfaces/types_Module.Module.md#getchannel)

#### Defined in

[src/NotifeeApiModule.ts:327](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L327)

___

### getChannelGroup

▸ **getChannelGroup**(`channelGroupId`): `Promise`<``null`` \| [`NativeAndroidChannelGroup`](../interfaces/types_NotificationAndroid.NativeAndroidChannelGroup.md)\>

API used to return a channel group on supported Android devices.

This API is used to return an `NativeAndroidChannelGroup`. Returns `null` if no channel could be matched to
the given ID.

A "native channel group" also includes additional properties about the channel group at the time it's
retrieved from the device. View the [`NativeAndroidChannelGroup`](/react-native/reference/nativeandroidchannelgroup)
documentation for more information.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelGroupId` | `string` |

#### Returns

`Promise`<``null`` \| [`NativeAndroidChannelGroup`](../interfaces/types_NotificationAndroid.NativeAndroidChannelGroup.md)\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getChannelGroup](../interfaces/types_Module.Module.md#getchannelgroup)

#### Defined in

[src/NotifeeApiModule.ts:347](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L347)

___

### getChannelGroups

▸ **getChannelGroups**(): `Promise`<[`NativeAndroidChannelGroup`](../interfaces/types_NotificationAndroid.NativeAndroidChannelGroup.md)[]\>

API used to return all channel groups on supported Android devices.

This API is used to return a `NativeAndroidChannelGroup`. Returns an empty array if no channel
groups exist.

A "native channel group" also includes additional properties about the channel group at the time it's
retrieved from the device. View the [`NativeAndroidChannelGroup`](/react-native/reference/nativeandroidchannelgroup)
documentation for more information.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

#### Returns

`Promise`<[`NativeAndroidChannelGroup`](../interfaces/types_NotificationAndroid.NativeAndroidChannelGroup.md)[]\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getChannelGroups](../interfaces/types_Module.Module.md#getchannelgroups)

#### Defined in

[src/NotifeeApiModule.ts:359](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L359)

___

### getChannels

▸ **getChannels**(): `Promise`<[`NativeAndroidChannel`](../interfaces/types_NotificationAndroid.NativeAndroidChannel.md)[]\>

API used to return all channels on supported Android devices.

This API is used to return a `NativeAndroidChannel`. Returns an empty array if no channels
exist.

A "native channel" also includes additional properties about the channel at the time it's
retrieved from the device. View the [`NativeAndroidChannel`](/react-native/reference/nativeandroidchannel)
documentation for more information.

View the [Channels & Groups](/react-native/docs/android/channels) documentation for more information.

#### Returns

`Promise`<[`NativeAndroidChannel`](../interfaces/types_NotificationAndroid.NativeAndroidChannel.md)[]\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getChannels](../interfaces/types_Module.Module.md#getchannels)

#### Defined in

[src/NotifeeApiModule.ts:339](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L339)

___

### getDisplayedNotifications

▸ **getDisplayedNotifications**(): `Promise`<[`DisplayedNotification`](../interfaces/types_Notification.DisplayedNotification.md)[]\>

API used to return the notifications that are displayed.

#### Returns

`Promise`<[`DisplayedNotification`](../interfaces/types_Notification.DisplayedNotification.md)[]\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getDisplayedNotifications](../interfaces/types_Module.Module.md#getdisplayednotifications)

#### Defined in

[src/NotifeeApiModule.ts:106](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L106)

___

### getInitialNotification

▸ **getInitialNotification**(): `Promise`<``null`` \| [`InitialNotification`](../interfaces/types_Notification.InitialNotification.md)\>

API used to fetch the notification which causes the application to open.

This API can be used to fetch which notification & press action has caused the application to
open. The call returns a `null` value when the application wasn't launched by a notification.

Once the initial notification has been consumed by this API, it is removed and will no longer
be available. It will also be removed if the user relaunches the application.

View the [App open events](/react-native/docs/events#app-open-events) documentation for more
information and example usage.

#### Returns

`Promise`<``null`` \| [`InitialNotification`](../interfaces/types_Notification.InitialNotification.md)\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getInitialNotification](../interfaces/types_Module.Module.md#getinitialnotification)

#### Defined in

[src/NotifeeApiModule.ts:367](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L367)

___

### getNotificationCategories

▸ **getNotificationCategories**(): `Promise`<[`IOSNotificationCategory`](../interfaces/types_NotificationIOS.IOSNotificationCategory.md)[]\>

Gets the currently set notification categories on this Apple device.

Returns an empty array on Android.

#### Returns

`Promise`<[`IOSNotificationCategory`](../interfaces/types_NotificationIOS.IOSNotificationCategory.md)[]\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getNotificationCategories](../interfaces/types_Module.Module.md#getnotificationcategories)

#### Defined in

[src/NotifeeApiModule.ts:477](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L477)

___

### getNotificationSettings

▸ **getNotificationSettings**(): `Promise`<[`IOSNotificationSettings`](../interfaces/types_NotificationIOS.IOSNotificationSettings.md)\>

Get the current notification settings for this application on the current device.

On Android, all of the properties on the `IOSNotificationSettings` interface response return
as `AUTHORIZED`.

#### Returns

`Promise`<[`IOSNotificationSettings`](../interfaces/types_NotificationIOS.IOSNotificationSettings.md)\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getNotificationSettings](../interfaces/types_Module.Module.md#getnotificationsettings)

#### Defined in

[src/NotifeeApiModule.ts:485](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L485)

___

### getPowerManagerInfo

▸ **getPowerManagerInfo**(): `Promise`<[`PowerManagerInfo`](../interfaces/types_PowerManagerInfo.PowerManagerInfo.md)\>

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

#### Returns

`Promise`<[`PowerManagerInfo`](../interfaces/types_PowerManagerInfo.PowerManagerInfo.md)\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getPowerManagerInfo](../interfaces/types_Module.Module.md#getpowermanagerinfo)

#### Defined in

[src/NotifeeApiModule.ts:581](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L581)

___

### getTriggerNotificationIds

▸ **getTriggerNotificationIds**(): `Promise`<`string`[]\>

API used to return the ids of trigger notifications that are pending.

View the [Triggers](/react-native/docs/triggers) documentation for more information.

#### Returns

`Promise`<`string`[]\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getTriggerNotificationIds](../interfaces/types_Module.Module.md#gettriggernotificationids)

#### Defined in

[src/NotifeeApiModule.ts:98](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L98)

___

### getTriggerNotifications

▸ **getTriggerNotifications**(): `Promise`<[`TriggerNotification`](../interfaces/types_Notification.TriggerNotification.md)[]\>

API used to return the trigger notifications that are pending.

#### Returns

`Promise`<[`TriggerNotification`](../interfaces/types_Notification.TriggerNotification.md)[]\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[getTriggerNotifications](../interfaces/types_Module.Module.md#gettriggernotifications)

#### Defined in

[src/NotifeeApiModule.ts:102](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L102)

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

#### Returns

`void`

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[hideNotificationDrawer](../interfaces/types_Module.Module.md#hidenotificationdrawer)

#### Defined in

[src/NotifeeApiModule.ts:609](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L609)

___

### incrementBadgeCount

▸ **incrementBadgeCount**(`incrementBy?`): `Promise`<`void`\>

Increments the badge count for this application on the current device by a specified
value.

Defaults to an increment of `1`.

#### Parameters

| Name | Type |
| :------ | :------ |
| `incrementBy?` | `number` |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[incrementBadgeCount](../interfaces/types_Module.Module.md#incrementbadgecount)

#### Defined in

[src/NotifeeApiModule.ts:528](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L528)

___

### isBatteryOptimizationEnabled

▸ **isBatteryOptimizationEnabled**(): `Promise`<`boolean`\>

API used to check if battery optimization is enabled for your application.

Supports API versions >= 23.

View the [Background Restrictions](/react-native/docs/android/behaviour#background-restrictions) documentation for more information.

#### Returns

`Promise`<`boolean`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[isBatteryOptimizationEnabled](../interfaces/types_Module.Module.md#isbatteryoptimizationenabled)

#### Defined in

[src/NotifeeApiModule.ts:566](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L566)

___

### isChannelBlocked

▸ **isChannelBlocked**(`channelId`): `Promise`<`boolean`\>

API used to check if a channel is blocked.

On iOS, this will default to false

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelId` | `string` |

#### Returns

`Promise`<`boolean`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[isChannelBlocked](../interfaces/types_Module.Module.md#ischannelblocked)

#### Defined in

[src/NotifeeApiModule.ts:110](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L110)

___

### isChannelCreated

▸ **isChannelCreated**(`channelId`): `Promise`<`boolean`\>

API used to check if a channel is created.

On iOS, this will default to true

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelId` | `string` |

#### Returns

`Promise`<`boolean`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[isChannelCreated](../interfaces/types_Module.Module.md#ischannelcreated)

#### Defined in

[src/NotifeeApiModule.ts:122](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L122)

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

| Name | Type |
| :------ | :------ |
| `observer` | (`event`: [`Event`](../interfaces/types_Notification.Event.md)) => `Promise`<`void`\> |

#### Returns

`void`

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[onBackgroundEvent](../interfaces/types_Module.Module.md#onbackgroundevent)

#### Defined in

[src/NotifeeApiModule.ts:371](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L371)

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

| Name | Type |
| :------ | :------ |
| `observer` | (`event`: [`Event`](../interfaces/types_Notification.Event.md)) => `void` |

#### Returns

`fn`

▸ (): `void`

##### Returns

`void`

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[onForegroundEvent](../interfaces/types_Module.Module.md#onforegroundevent)

#### Defined in

[src/NotifeeApiModule.ts:379](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L379)

___

### openBatteryOptimizationSettings

▸ **openBatteryOptimizationSettings**(): `Promise`<`void`\>

API used to open the Android System settings for the application.

If the API version is >= 23, the battery optimization settings screen is displayed, otherwise,
this is a no-op & instantly resolves.

View the [Background Restrictions](/react-native/docs/android/behaviour#background-restrictions) documentation for more information.

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[openBatteryOptimizationSettings](../interfaces/types_Module.Module.md#openbatteryoptimizationsettings)

#### Defined in

[src/NotifeeApiModule.ts:574](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L574)

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

#### Parameters

| Name | Type |
| :------ | :------ |
| `channelId?` | `string` |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[openNotificationSettings](../interfaces/types_Module.Module.md#opennotificationsettings)

#### Defined in

[src/NotifeeApiModule.ts:396](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L396)

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

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[openPowerManagerSettings](../interfaces/types_Module.Module.md#openpowermanagersettings)

#### Defined in

[src/NotifeeApiModule.ts:595](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L595)

___

### registerForegroundService

▸ **registerForegroundService**(`runner`): `void`

API used to register a foreground service on Android devices.

This method is used to register a long running task which can be used with Foreground Service
notifications.

Only a single foreground service can exist for the application, and calling this method more
than once will update the existing task runner.

View the [Foreground Service](/react-native/docs/android/foreground-service) documentation for
more information.

#### Parameters

| Name | Type |
| :------ | :------ |
| `runner` | (`notification`: [`Notification`](../interfaces/types_Notification.Notification.md)) => `Promise`<`void`\> |

#### Returns

`void`

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[registerForegroundService](../interfaces/types_Module.Module.md#registerforegroundservice)

#### Defined in

[src/NotifeeApiModule.ts:440](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L440)

___

### requestPermission

▸ **requestPermission**(`permissions?`): `Promise`<[`IOSNotificationSettings`](../interfaces/types_NotificationIOS.IOSNotificationSettings.md)\>

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

#### Parameters

| Name | Type |
| :------ | :------ |
| `permissions` | [`IOSNotificationPermissions`](../interfaces/types_NotificationIOS.IOSNotificationPermissions.md) |

#### Returns

`Promise`<[`IOSNotificationSettings`](../interfaces/types_NotificationIOS.IOSNotificationSettings.md)\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[requestPermission](../interfaces/types_Module.Module.md#requestpermission)

#### Defined in

[src/NotifeeApiModule.ts:408](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L408)

___

### setBadgeCount

▸ **setBadgeCount**(`count`): `Promise`<`void`\>

Set the badge count value for this application on the current device.

If set to zero, the badge count is removed from the device. The count must also
be a positive number.

#### Parameters

| Name | Type |
| :------ | :------ |
| `count` | `number` |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[setBadgeCount](../interfaces/types_Module.Module.md#setbadgecount)

#### Defined in

[src/NotifeeApiModule.ts:516](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L516)

___

### setNotificationCategories

▸ **setNotificationCategories**(`categories`): `Promise`<`void`\>

Set the notification categories to be used on this Apple device.

#### Parameters

| Name | Type |
| :------ | :------ |
| `categories` | [`IOSNotificationCategory`](../interfaces/types_NotificationIOS.IOSNotificationCategory.md)[] |

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[setNotificationCategories](../interfaces/types_Module.Module.md#setnotificationcategories)

#### Defined in

[src/NotifeeApiModule.ts:452](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L452)

___

### stopForegroundService

▸ **stopForegroundService**(): `Promise`<`void`\>

Call this to stop the foreground service that is running

#### Returns

`Promise`<`void`\>

#### Implementation of

[Module](../interfaces/types_Module.Module.md).[stopForegroundService](../interfaces/types_Module.Module.md#stopforegroundservice)

#### Defined in

[src/NotifeeApiModule.ts:602](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/NotifeeApiModule.ts#L602)
