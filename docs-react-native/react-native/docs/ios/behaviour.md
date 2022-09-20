---
title: Behaviour
description: Control how notifications behave when they are displayed to your users.
next: /react-native/docs/ios/categories
previous: /react-native/docs/ios/badges
---

# Sound

When a notification is about to be displayed on a device, the permissions requested from the user will be read by the device
(see [`requestPermission`](/react-native/reference/requestpermission)). If sound permission has been granted, the device
will alert the user to the notification audibly with sound. The sound used will be whatever the user has selected within
the device settings.

You can however customise the sound played by providing a string value of an iOS resource, for example:

```js
notifee.displayNotification({
  body: 'Custom sound',
  ios: {
    // iOS resource (.wav, aiff, .caf)
    sound: 'local.wav',
  },
});
```

To use the users default sound, set the value as `default`. For no sound, do not include the property.

If you are using a custom sound file, it must be less than 30 seconds in length, otherwise the system will play the default sound.

# Interruption Levels

With iOS 15’s new Focus Mode, users are more in control over when app notifications can “interrupt” them with a sound or vibration. We can make use of the new `interruptionLevel` [API]([url](https://developer.apple.com/documentation/usernotifications/unnotificationcontent/3747256-interruptionlevel)) to control the notification’s importance and required delivery timing.

```js
// iOS > 15
notifee.displayNotification({
  title: 'ALERT!',
  body: 'This is a critical notification!',
  ios: {
    interruptionLevel: 'timeSensitive',
  },
});
```

# Critical Notifications

In some scenarios you may wish to alert the user to a notification and bypass the users preferences such as the mute switch or Do Not Disturb mode.

To do this, request `criticalAlert` permission via [`requestPermission`](/react-native/reference/requestpermission) and set the `critical` flag on the notification:

```js
// iOS > 12
notifee.requestPermission({
  //...,
  criticalAlert: true
});

// iOS > 10
notifee.displayNotification({
  title: 'ALERT!',
  body: 'This is a critical notification!',
  ios: {
    critical: true,
    sound: 'local.wav',
  },
});
```

On iOS >= 12, you can further control the sound volume level of a critical notification by passing a `criticalVolume`
property to your `ios` notification options:

```js
// iOS > 10
notifee.displayNotification({
  title: 'ALERT!',
  body: 'This is a critical notification!',
  ios: {
    critical: true,
    sound: 'local.wav',
    // iOS > 12
    // play at 90% sound volume
    criticalVolume: 0.9,
  },
});
```

The `criticalVolume` value accepts a float value between `0.0` & `1.0`, with `1.0` representing full volume.

> Critical notifications require a special entitlement issued by Apple. A request can be submitted at https://developer.apple.com/contact/request/notifications-critical-alerts-entitlement.
