---
title: Debugging
description: Handling errors, enabling logging and viewing logs.
next: /react-native/docs/integrations/fcm
previous: /react-native/docs/triggers
---

# Error Handling

It's recommended that you wrap `displayNotification` and `createTriggerNotification` in a try/catch to catch any validation errors that may be thrown:

```js
try {
  await notifee.displayNotification({
    title: 'Chat with Joe Bloggs',
    body: 'A new message has been received from a user.',
    ...
  });
} catch (e) {
  console.log(e);
}
```

# Native Logs

## Android

- Enable native logs in release mode by running:

`adb shell setprop log.tag.NOTIFEE DEBUG`

- To quickly view Android logs in the terminal:

`adb logcat '*:S' NOTIFEE:D`
