[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/PowerManagerInfo](../modules/types_PowerManagerInfo.md) / PowerManagerInfo

# Interface: PowerManagerInfo

[types/PowerManagerInfo](../modules/types_PowerManagerInfo.md).PowerManagerInfo

The interface that represents the information returned from `getPowerManagerInfo()`.

View the [Background Restrictions](/react-native/docs/android/background-restrictions) documentation to learn more.

**`platform`** android

## Table of contents

### Properties

- [activity](types_PowerManagerInfo.PowerManagerInfo.md#activity)
- [manufacturer](types_PowerManagerInfo.PowerManagerInfo.md#manufacturer)
- [model](types_PowerManagerInfo.PowerManagerInfo.md#model)
- [version](types_PowerManagerInfo.PowerManagerInfo.md#version)

## Properties

### activity

• `Optional` **activity**: ``null`` \| `string`

The activity that the user will be navigated to if `openPowerManagerSettings()` is called.

Use this as an indicator of what steps the user may have to perform,
in-order to prevent your app from being killed.

If no activity can be found, value will be null.

#### Defined in

[src/types/PowerManagerInfo.ts:42](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/PowerManagerInfo.ts#L42)

___

### manufacturer

• `Optional` **manufacturer**: `string`

The device manufacturer.

For example, Samsung.

#### Defined in

[src/types/PowerManagerInfo.ts:18](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/PowerManagerInfo.ts#L18)

___

### model

• `Optional` **model**: `string`

The device model.

For example, Galaxy S8

#### Defined in

[src/types/PowerManagerInfo.ts:25](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/PowerManagerInfo.ts#L25)

___

### version

• `Optional` **version**: `string`

The Android version

For example, Android 10

#### Defined in

[src/types/PowerManagerInfo.ts:32](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/PowerManagerInfo.ts#L32)
