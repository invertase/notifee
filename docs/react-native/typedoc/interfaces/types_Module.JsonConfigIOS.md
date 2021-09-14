[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/Module](../modules/types_Module.md) / JsonConfigIOS

# Interface: JsonConfigIOS

[types/Module](../modules/types_Module.md).JsonConfigIOS

An interface describing the iOS specific configuration properties for the `notifee.config.json` file.

View the [License Keys](/react-native/docs/license-keys) documentation for more information.

```json
{
  "ios": {
    "license": "..."
  }
}
```

## Table of contents

### Properties

- [license](types_Module.JsonConfigIOS.md#license)

## Properties

### license

• **license**: `string`

The license key created via the Notifee account section. A valid license is required
for production usage with Notifee.

The license must match your Bundle ID when created.

#### Defined in

[src/types/Module.ts:641](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Module.ts#L641)
