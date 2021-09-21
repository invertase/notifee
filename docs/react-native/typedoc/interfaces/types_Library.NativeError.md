[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/Library](../modules/types_Library.md) / NativeError

# Interface: NativeError

[types/Library](../modules/types_Library.md).NativeError

An Error that has occurred in native Android or iOS code converted into a JavaScript Error.

## Hierarchy

- `Error`

  ↳ **`NativeError`**

## Table of contents

### Properties

- [code](types_Library.NativeError.md#code)
- [message](types_Library.NativeError.md#message)
- [name](types_Library.NativeError.md#name)
- [nativeErrorCode](types_Library.NativeError.md#nativeerrorcode)
- [nativeErrorMessage](types_Library.NativeError.md#nativeerrormessage)
- [stack](types_Library.NativeError.md#stack)

## Properties

### code

• `Readonly` **code**: `string`

Error code, e.g. `invalid-parameter`

#### Defined in

[src/types/Library.ts:12](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Library.ts#L12)

___

### message

• `Readonly` **message**: `string`

Error message

#### Overrides

Error.message

#### Defined in

[src/types/Library.ts:17](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Library.ts#L17)

___

### name

• **name**: `string`

#### Inherited from

Error.name

#### Defined in

node_modules/typescript/lib/lib.es5.d.ts:973

___

### nativeErrorCode

• `Readonly` **nativeErrorCode**: `string` \| `number`

The native returned error code, different per platform

#### Defined in

[src/types/Library.ts:22](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Library.ts#L22)

___

### nativeErrorMessage

• `Readonly` **nativeErrorMessage**: `string`

The native returned error message, different per platform

#### Defined in

[src/types/Library.ts:27](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/Library.ts#L27)

___

### stack

• `Optional` **stack**: `string`

#### Inherited from

Error.stack

#### Defined in

node_modules/typescript/lib/lib.es5.d.ts:975
