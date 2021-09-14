[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/Trigger](../modules/types_Trigger.md) / IntervalTrigger

# Interface: IntervalTrigger

[types/Trigger](../modules/types_Trigger.md).IntervalTrigger

Interface for building a trigger that repeats at a specified interval.

View the [Triggers](/react-native/docs/triggers) documentation to learn more.

## Table of contents

### Properties

- [interval](types_Trigger.IntervalTrigger.md#interval)
- [timeUnit](types_Trigger.IntervalTrigger.md#timeunit)
- [type](types_Trigger.IntervalTrigger.md#type)

## Properties

### interval

• **interval**: `number`

How frequently the notification should be repeated.

For example, if set to 30, the notification will be displayed every 30 minutes.

Must be set to a minimum of 15 minutes.

#### Defined in

[src/types/Trigger.ts:82](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Trigger.ts#L82)

___

### timeUnit

• `Optional` **timeUnit**: [`TimeUnit`](../enums/types_Trigger.TimeUnit.md)

The unit of time that the `interval` is measured in.

For example, if set to `TimeUnit.DAYS` and repeat interval is set to 3, the notification will repeat every 3 days.

Defaults to `TimeUnit.SECONDS`

#### Defined in

[src/types/Trigger.ts:91](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Trigger.ts#L91)

___

### type

• **type**: [`INTERVAL`](../enums/types_Trigger.TriggerType.md#interval)

Constant enum value used to identify the trigger type.

#### Defined in

[src/types/Trigger.ts:73](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Trigger.ts#L73)
