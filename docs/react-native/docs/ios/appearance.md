---
title: Appearance
description: Customising the appearance of your iOS notifications with Notifee.
next: /react-native/docs/ios/behaviour
previous: /react-native/docs/ios/introduction
---

# Importance

The importance of a notification controls how your notification is presented (in a "heads-up" format) to users whilst your
application is the foreground. Importance has no effect on background notifications, for customising notification behaviour
whilst your app is backgrounded use the [Permissions API](/react-native/docs/ios/permissions).

You should always choose an appropriate importance level for the type of notification. Showing an irrelevant or meta
notification (e.g. recommended content) at high importance may cause your users to disable notifications
for your entire application.

Notifications can be assigned an importance level by setting the `importance` property on each notification you create:

```js
import notifee, { Importance } from '@notifee/react-native';

await notifee.displayNotification({
  title: 'Your account requires attention',
  body: 'You are overdue payment on one or more of your accounts!',
  ios: {
    importance: Importance.HIGH,
  },
});
```

## Levels

The Notifee iOS implementation currently supports three types of importance levels.

### High

The highest importance level applied to a notification. Notifications will show in
"heads-up mode" and also play a sound when your application is in the foreground.

<Vimeo id="ios-importance-high" caption="iOS High Importance" />

### Default

The default importance level applied to a notification. Notifications will show in "heads-up mode" whilst your app is in
the foreground, however; they will not alert the user audibly with sound.

<Vimeo id="ios-importance-default" caption="iOS Default Importance" />

### None

The lowest importance level applied to a notification. The notification will not be displayed whilst your app is in the
foreground, however events will still be triggered.

This importance could be used to manually display your own in-app UI for incoming notifications whilst your app is active.

# Summary text

After a number of unread notifications have been delivered to the device, iOS will begin to stack (or group) notifications for your
application. The notification on the top of the stack by default will display the summary text, letting the user know how many
unread notifications are available:

![Default summary text](https://images.prismic.io/invertase/8d3cc19e-5cb9-41a4-9c55-1d03c975043e_ios-summary-text-default.png?auto=compress,format)

It is possible to override this default text by providing `summaryArgument` & `summaryArgumentCount` fields
on the notification body combined with a [Category](/react-native/docs/ios/categories) `summaryFormat`.

To learn more, view the [Category Summary Text](/react-native/docs/ios/categories#category-summary-text)
documentation.

> This functionality is only available on iOS >= 12.

