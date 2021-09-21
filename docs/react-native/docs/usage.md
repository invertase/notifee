---
title: Basic Usage
description: Learn how to import the library & use the library within your project.
next: /react-native/docs/displaying-a-notification
previous: /react-native/docs/release-notes
---

# Importing the library

Once [installed](/react-native/docs/installation), Notifee can be imported into your React Native project:

```js
import notifee from '@notifee/react-native';
```

The default export can then be used to interact with Notifee API, either inside of your React components or standalone
(e.g. inside of a [Headless Task](https://facebook.github.io/react-native/docs/headless-js-android)).

The library also exports the API types, documented within the [Reference API](/react-native/reference). For example to
import the [`AndroidColor`](/react-native/reference/androidcolor) type:

```js
import { AndroidColor } from '@notifee/react-native';
```

# TypeScript

Projects using [TypeScript](https://www.typescriptlang.org) with [React Native](https://facebook.github.io/react-native/docs/typescript) can
expect Notifee to work out of the box with no additional steps required. Simply import the package and the typed API
interface will be available.

Using TypeScript in React Native is entirely optional and is not required to use Notifee.

# Android vs iOS

Notifee supports both Android & iOS using the same interface. The API has been designed to function on both platforms,
even in situations whereby functionality may not exist on one platform. In those cases, the API will "noop" or instantly
resolve.
