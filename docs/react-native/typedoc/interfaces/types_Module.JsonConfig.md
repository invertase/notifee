[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/Module](../modules/types_Module.md) / JsonConfig

# Interface: JsonConfig

[types/Module](../modules/types_Module.md).JsonConfig

An interface describing the contents of a `notifee.config.json` file.

View the [License Keys](/react-native/docs/license-keys) documentation for more information.

```json
{
  "android: {
    "license": "..."
  },
  "ios": {
    "license": "..."
  }
}
```

## Table of contents

### Properties

- [android](types_Module.JsonConfig.md#android)
- [ios](types_Module.JsonConfig.md#ios)

## Properties

### android

• `Optional` **android**: [`JsonConfigAndroid`](types_Module.JsonConfigAndroid.md)

Android specific Notifee configuration.

#### Defined in

[src/types/Module.ts:664](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L664)

___

### ios

• `Optional` **ios**: [`JsonConfigIOS`](types_Module.JsonConfigIOS.md)

iOS specific Notifee configuration.

#### Defined in

[src/types/Module.ts:669](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L669)
