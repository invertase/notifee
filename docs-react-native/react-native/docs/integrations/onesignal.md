---
title: OneSignal
description: Integrate the OneSignal platform to display custom local notifications with Notifee.
next: /react-native/docs/android/introduction
previous: /react-native/docs/integrations/fcm
---

[OneSignal](https://onesignal.com) is a platform for sending cloud messages to mobile devices

In [their react-native documentation](https://documentation.onesignal.com/docs/react-native-sdk-setup) they describe several things you must do, but [users here have noticed incompatibilities](https://github.com/notifee/react-native-notifee/issues/124#issuecomment-690383445) (and [also here](https://github.com/notifee/react-native-notifee/issues/145#issuecomment-690372202)) when following the documentation because the One Signal libraries appear to be a bit out of date.

With special thanks to users [Ashok Kumar](https://github.com/ashokkumar88) and [burhanDebug](https://github.com/burhanDebug) and [zaweisise](https://github.com/zaweiswise) for helping us figure it out and confirm a solution, this is how to integrate notifee with OneSignal:

1. Do *not* use the onesignal gradle plugin (currently step 3.2 in their documentation)
1. If using a `react-native-onesignal` version earlier than 4.0.0 - edit `node_modules/react-native-onesignal/android/build.gradle`:

In the android dependencies modify the api ('com.onesignal:OneSignal:3.15.1') to

```groovy
api ('com.onesignal:OneSignal:3.15.1') {
		exclude group: 'com.google.android.gms', module: 'play-services-location'
		exclude group: 'com.google.android.gms', module: 'play-services-base'
		exclude group: 'com.google.android.gms', module: 'play-services-ads-identifier'
		exclude group: 'com.google.firebase', module: 'firebase-messaging'
	}
```

We understand that editing files directly in node_modules is unconventional advice, we strongly recommend the use of [patch-package](https://github.com/ds300/patch-package) in order to manage this change. 

`patch-package` will make a patch for this change that you put in source control, it will reliably install the patch when you install npm packages, and it will give you a friendly warning in case One Signal updates their module.

To use patch-package in this case follow these steps:

1. Clean out android build items so they do not clutter the patch: `cd android && ./gradlew clean && cd ..`
1. Install patch-package according to their docs (`mkdir patches` and edit package.json to add patch-package to your postinstall)
1. Create the patch: `npx patch-package react-native-onesignal`
1. Add the patch to source control (for example for git `git add patches && git commit`)
