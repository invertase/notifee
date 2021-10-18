---
title: Installation
description: Quick start guide for installing and running on React Native.
next: /react-native/docs/release-notes
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


## 2. Android: Add local maven repository

The Notifee core library is packaged an Android "AAR" file. It is distributed in a "local maven repository" for Android integration.

You must add this repository to your `android/build.gradle` build script file.

Open `android/build.gradle` for editing, and add these lines in your `allprojects` `repositories` section:


```groovy

allprojects {

  // ... you may have other items before the "repositories" section, that is fine

  repositories {

    // ... you will already have some local repositories defined ...

    // ADD THIS BLOCK - this is how Notifee finds it's Android library:
    maven {
      url "$rootDir/../node_modules/@notifee/react-native/android/libs"
    }
  }
}
```

## 3. Autolinking with React Native
Users on React Native 0.60+ automatically have access to "[autolinking](https://github.com/react-native-community/cli/blob/master/docs/autolinking.md)",
requiring no further manual installation steps. To automatically link the package, rebuild your project:

```bash
# For iOS
cd ios/ && pod install --repo-update
npx react-native run-ios

# For Android
npx react-native run-android
```

## Miscellaneous

### Expo Support
Notifee has a built-in expo plugin you can install into an Expo managed project.

First, add Notifee to your project:
`expo install @notifee/react-native`

Then, add `@notifee/react-native` to the list of plugins in your app's Expo config (`app.json` or `app.config.js`):
```json
{
  "name": "my app",
  "plugins": [
    "@notifee/react-native"
  ]
}
```

Finally, ensure you run `expo perbuild` and that's it.

Next, rebuild your app as described in the ["Adding custom native code"](https://docs.expo.io/workflow/customizing/) guide.
