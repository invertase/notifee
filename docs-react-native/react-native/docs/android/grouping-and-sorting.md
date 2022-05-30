---
title: Grouping & Sorting
description: Android notifications can be grouped and sorted before being displayed to users.
next: /react-native/docs/android/interaction
previous: /react-native/docs/android/foreground-service
---

Notifications in Android can be grouped to display related notifications into a single pane in the notification's shade.

<Vimeo id="android-grouped" caption="Android Grouped Notifications" />

# Grouping

Grouped notifications share a unique identifier, which can be provided to the `groupId` property.
Before displaying notifications in a group, you must first create a "summary" notification which holds all future
notifications with the same ID.

The summary notification can be created by setting the `groupSummary` property to `true`:

```js
notifee.displayNotification({
  title: 'Emails',
  subtitle: '3 Unread Emails',
  android: {
    channelId,
    groupSummary: true,
    groupId: '123',
  },
});
```

Further notifications can be added to the group by providing the same `groupId`:

```js
notifee.displayNotification({
  title: 'New Email',
  body: 'Tap to open your email.',
  subtitle: 'Unread',
  android: {
    channelId,
    groupId: '123',
  },
});
```

# Group behaviour

By default, each grouped notification (including the summary notification) will alert the user as normal. This
can be changed so only the summary notification alerts when displayed, or only the children alert. To change the default
behaviour, the `groupAlertBehavior` property can be set.

## Setting behaviour

To set specific behaviour, the alert behaviour type must be set on both the summary and children. For example, to only
alert when the summary notification is displayed, pass `AndroidGroupAlertBehavior.SUMMARY` to the `groupAlertBehavior`
property:

```js
import notifee, { AndroidGroupAlertBehavior } from '@notifee/react-native';

// Create summary
notifee.displayNotification({
  title: 'Emails',
  subtitle: '3 Unread Emails',
  android: {
    channelId,
    groupSummary: true,
    groupId: '123',
    groupAlertBehavior: AndroidGroupAlertBehavior.SUMMARY,
  },
});

// Children
notifee.displayNotification({
  title: 'New Email',
  body: 'Tap to open your email.',
  subtitle: 'Unread',
  android: {
    channelId,
    groupId: '123',
    groupAlertBehavior: AndroidGroupAlertBehavior.SUMMARY,
  },
});
```

To alert when children are displayed, use `AndroidGroupAlertBehavior.CHILDREN` instead. View the
[`AndroidGroupAlertBehavior`](/react-native/reference/androidgroupalertbehavior) documentation.

# Sorting

Notifications are sorted (the order shown from top to bottom in the notification shade) in the order displayed by default.
It is possible to manually sort notifications by a custom value or timestamp.

## Sorting by timestamp

When providing a custom timestamp, the device will automatically sort the notifications (showing the newest at the top).
To provide a timestamp, pass the number of milliseconds to the `timestamp` property on the notification options:

```js
notifee.displayNotification({
  title: 'New message',
  body: 'New message from Sarah Lane',
  android: {
    channelId,
    timestamp: Date.now() - 300000, // 5 minutes ago
    showTimestamp: true,
  },
});
```

## Sorting by key

If the timestamp isn't a valid value you wish to sort by, you can further provide a `sortKey` property to the
notification options. When set the device will sort notifications in a [lexicographical order](https://en.wikipedia.org/wiki/Lexicographical_order):

```js
notifee.displayNotification({
  title: 'New message',
  body: 'New message from Sarah Lane',
  android: {
    channelId,
    timestamp: Date.now() - 300000, // 5 minutes ago
    showTimestamp: true,
    sortKey: '1',
  },
});

notifee.displayNotification({
  title: 'New message',
  body: 'New message from John Doe',
  android: {
    channelId,
    timestamp: Date.now() - 480000, // 8 minutes ago
    showTimestamp: true,
    sortKey: '2',
  },
});
```

The `sortKey` overrides the `timestamp` as the sorting property when provided.
