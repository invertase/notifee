---
title: Timestamps & Timers
description: Notifications can be displayed with timestamps and timers (stopwatch & countdowns).
next: /react-native/docs/ios/introduction
previous: /react-native/docs/android/styles
---

# Timestamps

A timestamp can be set on the notification which can be used for [sorting](/react-native/docs/android/grouping-and-sorting), and to
visually show the user when the notification was delivered.

Set the `showTimestamp` value to `true` and a human friendly readable time will be shown. The example below will indicate to the
user that the message was delivered `8 minutes ago`:

```js
notifee.displayNotification({
  title: 'Message from Sarah Lane',
  body: 'Tap to view your unread message from Sarah.',
  subtitle: 'Messages',
  android: {
    channelId,
    largeIcon: 'https://my-cdn/users/123.png',
    timestamp: Date.now() - 480000, // 8 minutes ago
    showTimestamp: true,
  },
});
```

The following would display a notification with a human readable time:

<Vimeo id="android-timestamp" caption="Android Timestamp" />

# Timers

In some cases, displaying an ongoing counting timer (a chronometer) alongside the notification can be useful. For example, for showing
the ongoing time of a phone call.

To show a timer, set the `showChronometer` property on the notification options to `true`:

```js
notifee.displayNotification({
  title: 'Message from Sarah Lane',
  body: 'Tap to view your unread message from Sarah.',
  subtitle: 'Messages',
  android: {
    channelId,
    largeIcon: 'https://my-cdn/users/123.png',
    timestamp: Date.now() - 480000, // 8 minutes ago
    showTimestamp: true,
    showChronometer: true,
  },
});
```

<Vimeo id="android-chronometer" caption="Android Timer" />

If combined with a `timestamp` the timer will count positively/negatively from the specified time.

## Timer direction

By default, the timer will count upwards to the time. To reverse the direction and count downwards, set the
`chronometerDirection` to `down`:

```js
notifee.displayNotification({
  title: '&#11088; Claim Your Prize &#11088;',
  body: 'Tap to claim your time limited prize! Hurry! &#10024;',
  subtitle: 'Prizes',
  android: {
    channelId,
    showChronometer: true,
    chronometerDirection: 'down',
    timestamp: Date.now() + 300000, // 5 minutes
  },
});
```

The following would display a notification with a countdown timer:

<Vimeo id="android-chronometer-down" caption="Android Count Down Timer" />
