---
title: Behaviour
description: Control how notifications behave when they are displayed to your users & how they impact a physical device.
next: /react-native/docs/android/channels
previous: /react-native/docs/android/appearance
---

Notifications can behave in various manners when they arrive on a user's device; sounds, vibrations, to how the device
handles interaction can all be altered.

# Sound

Users have full control over _when_ notification sounds are made:

- Disabling notification sound via the channel/notification settings.
- Changing the device volume.
- Toggling "Do not disturb" mode on their device.

By default, notifications will use the device's default notification sound, or whatever the user has chosen as the device default.
If required, you can also provide a custom `.mp3` sound or use a different device sound.

## Custom Sound

Customs sounds must be local `.mp3` files. Unfortunately, it is not possible to use remote sound files at this time.

Go ahead and download your sound file locally. The file needs to be place inside & bundled with your Android project inside
of a `raw` directory in the project resources. For example:

`/android/app/src/main/res/raw/hollow.mp3`

You may need to create the `res` and/or `raw` directories if they do not already exist.

Once added, rebuild your Android project.

Android devices on 8.0 (API level 26) set the sound directly on a channel, whilst devices on lower API versions set the
sound directly on the notification:

```js
// Android >= 8.0 (API level 26)
notifee.createChannel({
  id: 'custom-sound',
  title: 'Channel with custom sound',
  sound: 'hollow',
});

// Android < 8.0 (API level 26)
notifee.displayNotification({
  body: 'Custom sound',
  android: {
    sound: 'hollow',
  },
});
```

The file extension does not need to be provided. If the file could not be found, the default system sound will be used instead.

# Auto Cancelling

This documentation is a work in progress.

# Vibration

When a notification is displayed, if vibration is enabled for the channel/notification and the user's device has vibration
enabled, the default device vibration pattern will be used. It is possible to provide a custom vibration pattern
with an array of numbers.

> On emulators, there is no vibration & will only vibrate on real devices.

The pattern must be an even amount of numbers and follows a "vibrate and delay" schema. Each number is the amount of
milliseconds. For example, "300ms vibrate" then "500ms delay" would be `[300, 500]`. The pattern can be passed to the
`vibrationPattern` property:

```js
notifee.displayNotification({
  body: 'Custom vibration',
  android: {
    vibrationPattern: [300, 500],
  },
});
```

# Lights

Some devices come with an rgba light, which can be used to alert users of a new notification. On supported devices, the
`lights` property can be used to change the color and flashing pattern. The channel/notification must have lights
enabled for this property to take effect.

The property accepts an array, with the first item being the light color, and the other items following a "on and off"
schema. For example, to show a "red" light which shows for 300ms and stays off for 600ms:

```js
import notifee, { AndroidColor } from '@notifee/react-native';

notifee.displayNotification({
  body: 'Custom lights',
  android: {
    lights: [AndroidColor.RED, 300, 600],
  },
});
```

You can also pass a custom hexdecimal value instead of an [`AndroidColor`](/react-native/reference/androidcolor).

On emulators, there is no rgba light & will only show on real devices.

# Ongoing

By default, users are able to dismiss a notification (unless it's a
[Foreground Service](/react-native/docs/android/foreground-service)). It is possible to disable this functionality by
enabling "ongoing" notifications. When the `ongoing` property is set to true, the user cannot dismiss the notification,
and it must be programmatically cancelled via [`cancelNotification`](/react-native/reference/cancelnotification).

<Vimeo id="android-ongoing" caption="Android Ongoing Notification" />

```js
notifee.displayNotification({
  body: 'Ongoing notification',
  android: {
    ongoing: true,
  },
});
```
