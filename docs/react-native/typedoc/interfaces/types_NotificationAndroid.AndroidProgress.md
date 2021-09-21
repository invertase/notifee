[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidProgress

# Interface: AndroidProgress

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidProgress

Interface for defining the progress of an Android Notification.

<Vimeo id="android-progress-summary" caption="Android Progress (w/ Big Picture Style)" />

View the [Progress Indicators](/react-native/docs/android/progress-indicators) documentation to learn more.

**`platform`** android

## Table of contents

### Properties

- [current](types_NotificationAndroid.AndroidProgress.md#current)
- [indeterminate](types_NotificationAndroid.AndroidProgress.md#indeterminate)
- [max](types_NotificationAndroid.AndroidProgress.md#max)

## Properties

### current

• `Optional` **current**: `number`

The current progress value.

E.g. setting to `4` with a `max` value of `10` would set a fixed progress bar on the notification at 40% complete.

#### Defined in

[src/types/NotificationAndroid.ts:771](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L771)

___

### indeterminate

• `Optional` **indeterminate**: `boolean`

If `true`, overrides the `max` and `current` values and displays an unknown progress style. Useful when you have no
knowledge of a tasks completion state.

Defaults to `false`.

#### Defined in

[src/types/NotificationAndroid.ts:779](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L779)

___

### max

• `Optional` **max**: `number`

The maximum progress number. E.g `10`.

Must be greater than the `current` value.

#### Defined in

[src/types/NotificationAndroid.ts:764](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L764)
