---
title: Appearance
description: Customising the appearance of your iOS notifications with Notifee.
next: /react-native/docs/ios/behaviour
previous: /react-native/docs/ios/introduction
---

## Foreground Notifications

By default, Notifee will show iOS notifications in heads-up mode if your app is currently in the foreground.

This behaviour can be customised using the [`IOSForegroundPresentationOptions`](/react-native/reference/iosforegroundpresentationoptions) options:

```js
import notifee from '@notifee/react-native';

await notifee.displayNotification({
  title: 'Your account requires attention',
  body: 'You are overdue payment on one or more of your accounts!',
  ios: {
    foregroundPresentationOptions: {
      alert: true,
      badge: true,
      sound: true,
    },
  },
});
```


## Summary text

After a number of unread notifications have been delivered to the device, iOS will begin to stack (or group) notifications for your
application. The notification on the top of the stack by default will display the summary text, letting the user know how many
unread notifications are available:

![Default summary text](https://images.prismic.io/invertase/8d3cc19e-5cb9-41a4-9c55-1d03c975043e_ios-summary-text-default.png?auto=compress,format)

It is possible to override this default text by providing `summaryArgument` & `summaryArgumentCount` fields
on the notification body combined with a [Category](/react-native/docs/ios/categories) `summaryFormat`.

To learn more, view the [Category Summary Text](/react-native/docs/ios/categories#category-summary-text)
documentation.

> This functionality is only available on iOS >= 12.

