---
title: Installation
description: Quick start guide for installing and running on React Native.
next: /react-native/docs/license-keys
previous: /react-native/docs/environment-support
---

## 1. Install via npm

Install Notifee to the root of your React Native project with [npm](https://www.npmjs.com/) or
[Yarn](https://yarnpkg.com/lang/en/)

```bash
# Using npm
npm install --save @notifee/react-native

# Using Yarn
yarn add @notifee/react-native
```

## 2. Autolinking with React Native

Users on React Native 0.60+ automatically have access to "[autolinking](https://github.com/react-native-community/cli/blob/master/docs/autolinking.md)",
requiring no further manual installation steps. To automatically link the package, rebuild your project:

```bash
# For iOS
cd ios/ && pod install --repo-update
npx react-native run-ios

# For Android
npx react-native run-android
```

## Manual linking

If you're using an older version of React Native without autolinking support, or wish to integrate
into an existing project, you can follow the manual installation steps for [iOS](/react-native/docs/installation/ios)
and [Android](/react-native/docs/installation/android).
