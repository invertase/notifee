---
title: Environment Support
description: Understand how the package integrates with the various development ecosystems.
next: /react-native/docs/installation
previous: /react-native/docs/overview
---

# Support Table

The below table contains the current tested and supported environments in which Notifee is compatible with:

| Environment                                                           | Supported             | Notes                                                   |
| --------------------------------------------------------------------- | --------------------- | ------------------------------------------------------- |
| React Native                                                          | v60.0+                | Untested on lower versions.                             |
| Android APIs                                                          | Android 4.1+ (API 16) | Backwards notification compatibility up to Android 4.1. |
| iOS Version                                                           | 10.0+                 |                                                         |
| [Hermes Support](https://facebook.github.io/react-native/docs/hermes) | Yes                   |                                                         |
| Packagers                                                             | Metro                 | Untested on non-standard packagers.                     |

## Additional Requirements

- For iOS development, Xcode 12 is required
- For Android, if you are compiling your app in release mode via the command line, please run `./gradlew :app:assembleRelease`. If you run `./gradlew assembleRelease` it will fail. We are working on a fix, please follow the issue on [GitHub](https://github.com/notifee/documentation/issues/8) for updates.