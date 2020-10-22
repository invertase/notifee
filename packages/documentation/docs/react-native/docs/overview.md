---
title: Overview
description: Overview of Notifee, the documentation guides and reference API.
next: /react-native/docs/environment-support
---

Notifee is a library for React Native, bringing local notification support to both Android &
iOS applications.

Whilst free for development, to use Notifee in production (release builds) you must first purchase a license. To learn
more about licenses and why they are required, see the [Frequently Asked Questions](/frequently-asked-questions). We
offer free licenses to React Native Firebase core contributors, free Open Source applications & non-profit organisations.
If you wish to request a free license, please [contact us](/contact?reason=license).

# The library

Notifee is a local notifications library and does not integrate with any 3rd party messaging services. This provides
developers greater flexibility on how notifications are managed and integrated with new and existing applications.

The library is a successor to the notifications module in [React Native Firebase v5](https://rnfirebase.io/docs/v5.x.x/notifications/introduction).
The entire library has been reworked from the ground up focusing on new features, testing, documentation and device
compatibility.

## Alternative solutions

With Notifee requiring a paid license for release builds, we appreciate this model may not be viable for everyone. Other
Open Source alternatives are available if required:

- [wix/react-native-notifications](https://github.com/wix/react-native-notifications).
- [react-native-push-notification-ios](https://github.com/react-native-push-notification-ios/push-notification-ios).
- [zo0r/react-native-push-notification](https://github.com/zo0r/react-native-push-notification).
- [invertase/react-native-firebase](https://v5.rnfirebase.io/docs/v5.x.x/notifications/introduction) (version 5 only).
- [expo-notifications](https://docs.expo.io/push-notifications/overview/)

Paid licenses allow us to ensure the library is well maintained & ensures on-going support to the user base. To learn more
see our [Frequently Asked Questions](/frequently-asked-questions).

# Documentation

Our documentation aims to provide a walkthrough guide to integrating notifee with your app on both Android & iOS platforms.

## Android vs iOS

A lot of the Notifee documentation is broken out into Android Concepts vs iOS Concepts. The underlying APIs & features provided by Android & iOS are very different. Notifee aims to provide first-class
support for both platforms which is why there are separate guides for [iOS](https://notifee.app/react-native/docs/ios/introduction) & [Android](https://notifee.app/react-native/docs/android/introduction).

# Reference API

The Notifee [reference API](/react-native/reference) provides developers with a deep dive into each of the methods
& properties which the API exposes. The reference is generated from [TypeScript](https://www.typescriptlang.org/) & is
Open Source - if you wish to improve any of the types, feel free to submit a [Pull Request](https://github.com/notifee/notifee/pulls)
or file an [Issue](https://github.com/notifee/notifee/issues).
