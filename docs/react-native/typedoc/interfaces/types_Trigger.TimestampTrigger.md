[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/Trigger](../modules/types_Trigger.md) / TimestampTrigger

# Interface: TimestampTrigger

[types/Trigger](../modules/types_Trigger.md).TimestampTrigger

Interface for building a trigger with a timestamp.

View the [Triggers](/react-native/docs/triggers) documentation to learn more.

## Table of contents

### Properties

- [alarmManager](types_Trigger.TimestampTrigger.md#alarmmanager)
- [repeatFrequency](types_Trigger.TimestampTrigger.md#repeatfrequency)
- [timestamp](types_Trigger.TimestampTrigger.md#timestamp)
- [type](types_Trigger.TimestampTrigger.md#type)

## Properties

### alarmManager

• `Optional` **alarmManager**: `boolean` \| [`TimestampTriggerAlarmManager`](types_Trigger.TimestampTriggerAlarmManager.md)

Choose to schedule your trigger notification with Android's AlarmManager API.

By default, trigger notifications are created with Android's WorkManager API.

**`platform`** android

#### Defined in

[src/types/Trigger.ts:34](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Trigger.ts#L34)

___

### repeatFrequency

• `Optional` **repeatFrequency**: [`RepeatFrequency`](../enums/types_Trigger.RepeatFrequency.md)

The frequency at which the trigger repeats.
If unset, the notification will only be displayed once.

For example:
 if set to `RepeatFrequency.HOURLY`, the notification will repeat every hour from the timestamp specified.
 if set to `RepeatFrequency.DAILY`, the notification will repeat every day from the timestamp specified.
 if set to `RepeatFrequency.WEEKLY`, the notification will repeat every week from the timestamp specified.

#### Defined in

[src/types/Trigger.ts:25](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Trigger.ts#L25)

___

### timestamp

• **timestamp**: `number`

The timestamp when the notification should first be shown, in milliseconds since 1970.

#### Defined in

[src/types/Trigger.ts:14](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Trigger.ts#L14)

___

### type

• **type**: [`TIMESTAMP`](../enums/types_Trigger.TriggerType.md#timestamp)

Constant enum value used to identify the trigger type.

#### Defined in

[src/types/Trigger.ts:10](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Trigger.ts#L10)
