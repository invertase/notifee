---
title: Installation
description: Quick start guide for installing and running on React Native.
next: /react-native/docs/release-notes
previous: /react-native/docs/environment-support
---

## 1. Install from npmjs.com

Install Notifee to the root of your React Native project with [npm](https://www.npmjs.com/) or
[Yarn](https://yarnpkg.com/lang/en/)

```bash
# Using npm
npm install --save @notifee/react-native

# Using Yarn
yarn add @notifee/react-native
```

## 2. Configuration Changes

### 2a. Android API versions

The values of the compileSdkVersion & targetSdkVersion might need to be chnaged. compileSdkVersion needs to be at least 33. In addition, if your app is going to request permission on Android 13 via requestPermission, targetSdkVersion needs to be at least 33, as well. These setting are in the file `/android/build.gradle`. 

```gradle
buildscript {
  ext {
    compileSdkVersion = 33 // at least 33
    targetSdkVersion = 33 // If requesting permission on Android 13 via requestPermission, at least 33 
    ... 
 } 
 ... 
}
```

Your android project will fail to build with missing symbols if you do not alter these settings, as we require API33 symbols to support Android 13 notification permissions.

### 2b. Gradle versions

The minimum version of gradle is 7.2.x, and the mimimum version of the android gradle plugin is 7.1.x.

Your android project will fail to build with errors related to resource compilation / packaging if you do not use a new enough android gradle plugin / gradle combination.

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

Finally, ensure you run `expo prebuild` and rebuild your app as described in the ["Adding custom native code"](https://docs.expo.io/workflow/customizing/) guide.

Please note that Notifee needs Java JDK 11+ to build on Android. If you are building the app with EAS, you need to change the image used for the build. You will have to use `image` as `ubuntu-18.04-jdk-11-ndk-r19c` or another image that has jdk 11 as in the following configuration. ([EAS Build Server Configuration](https://docs.expo.dev/build-reference/infrastructure/#image--ubuntu-1804-jdk-8-ndk-r19c--alias--default))

eas.json:
```json
{
  "build": {
    "dev": {
      "developmentClient": true,
      "distribution": "internal",
      "android": {
        "image": "ubuntu-18.04-jdk-11-ndk-r19c"
      }
    }
}
```
