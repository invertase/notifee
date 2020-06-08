---
title: Manual Android Installation
description: How to manually install the package on Android.
next: /react-native/docs/installation/ios
previous: /react-native/docs/installation
---

Users without [autolinking](https://github.com/react-native-community/cli/blob/master/docs/autolinking.md)
support or with non-standard applications can follow the below steps to manually integrate the Notifee
library into their Android project.

## Update Gradle Settings

Add the following to the end of your projects `/android/settings.gradle` file:

```
include ':notifee_react-native'
project(':notifee_react-native').projectDir = new File(rootProject.projectDir, './../node_modules/@notifee/react-native/android')
```

## Update Gradle Dependencies

Add the Notifee module dependency to your `/android/app/build.gradle` file:

```
dependencies {
  ...
  implementation project(path: ":notifee_react-native")
}
```

## Add package to the Android Application

Import and add the Notifee module to your React packages list in the project
`/android/app/src/main/java/**/MainApplication.java`:

Firstly, import the package at the top of the file:

```java
import io.invertase.notifee.NotifeePackage;
```

Now add the imported package to the React packages list:

```java
...
protected List<ReactPackage> getPackages() {
  return Arrays.asList(
    new MainReactPackage(),
    new NotifeePackage(),
...
```

## Rebuild the project

Once the above steps have been completed, rebuild your Android project:

```bash
npx react-native run-android
```
