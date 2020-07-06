---
title: Behaviour
description: Control how notifications behave when they are displayed to your users.
next: /
previous: /
---

# Sound

When a notification is about to be displayed on a device, the permissions requested from the user will be read by the device
(see [`requestPermision`](/react-native/reference/requestpermission)). If sound permission has been granted, the device
will alert the user to the notification audibly with sound. The sound used will be whatever the user has selected within
the device settings.

You can however customise the sound played by providing a string value of an iOS resource or one of the iOS ringtone names, for example:

```js
notifee.displayNotification({
  body: 'Custom sound',
  ios: {
    // iOS ringtone name
    sound: 'Beacon',

    // iOS resource (.wav, aiff, .caf)
    sound: 'local.wav',
  },
});
```

To use the users default sound, set the value as `default`. For no sound, set the value to `null`.

If you are using a custom sound file, it must be less than 30 seconds in length, otherwise the system will play the default sound.

# Critical Notifications

In some scenarios you may wish to alert the user to a notification and bypass the users preferences such as the
mute switch or Do Not Disturb mode. To do this, you can set the `crtitial` flag on the notification:

```js
// iOS > 10
notifee.displayNotification({
  title: 'ALERT!',
  body: 'This is a critical notification!',
  ios: {
    critical: true,
    sound: 'Beacon',
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
    sound: 'Beacon',
    // iOS > 12
    // play at 90% sound volume
    criticalVolume: 0.9,
  },
});
```

The `criticalVolume` value accepts a float value between `0.0` & `1.0`, with `1.0` representing full volume.
