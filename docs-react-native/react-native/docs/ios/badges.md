---
title: Badges
description: Manage the badge count on your application.
next: /react-native/docs/ios/behaviour
previous: /react-native/docs/ios/appearance
---

On iOS devices, the application icon on the device home screen is able to display a badge count, which appears as a red
circle with a number at the top right of the icon. This number indicates how many unread notifications the application
currently has.

![ios-badges](https://user-images.githubusercontent.com/14185925/87808464-a555ef00-c851-11ea-999c-a5afea19e6f1.png)

The device does not keep track of how many unread notifications are currently on the device, therefore it is up to you
to control the badge count.

> Remote messages from FCM/APNs can contain a badge count which will override any existing count if set on the message payload.

# Updating the badge count

## Setting the badge count

To set a count for your application on the device, call the `setBadgeCount` method:

```js
notifee.setBadgeCount(1).then(() => console.log('Badge count set!'));
```

To set the count, the value passed to the method must be an integer greater than zero.

## Removing the badge count

To remove the badge and the count from the application, pass `0` to the `setBadgeCount` method:

```js
notifee.setBadgeCount(0).then(() => console.log('Badge count removed!'));
```

## Getting the badge count

To retrieve the current badge count on the device, all the `getBadgeCount` method:

```js
notifee.getBadgeCount().then(count => console.log('Current badge count: ', count));
```

## Incrementing / Decrementing

Notifee provides helper methods to increment or decrement the current badge count in a single operation.

To increment the badge count, call the `incrementBadgeCount` method:

```js
// Increment by 1
notifee
  .incrementBadgeCount()
  .then(() => notifee.getBadgeCount())
  .then(count => console.log('Badge count incremented by 1 to: ', count));
```

By default, the count will be incremented by 1. To override this behaviour, pass the increment value as a parameter:

```js
// Increment by 3
notifee
  .incrementBadgeCount(3)
  .then(() => notifee.getBadgeCount())
  .then(count => console.log('Badge count incremented by 3 to: ', count));
```

To decrement the badge count, call the `decrementBadgeCount` method:

```js
// Decrement by 1
notifee
  .decrementBadgeCount()
  .then(() => notifee.getBadgeCount())
  .then(count => console.log('Badge count decremented by 1 to: ', count));
```

By default, the count will be decremented by 1. To override this behaviour, pass the decrement value as a parameter:

```js
// Decrement by 3
notifee
  .decrementBadgeCount(3)
  .then(() => notifee.getBadgeCount())
  .then(count => console.log('Badge count decremented by 3 to: ', count));
```

When decrementing, if the new value is less than 1, the badge will be removed automatically, and the returned badge count
will be zero.

# Integration

Generally you will only want to display a badge count when the application is not in the foreground, therefore to integrate badge count handling into your application, a [Background Event](/react-native/docs/events#background-events) can be used to control the badge count.

For example, if a notification is displayed whilst in the background, we can increment the count. However, if the user presses the notification (or an action) we could then decrement the count:

```js
import notifee, { EventType } from '@notifee/react-native';
import { firebase } from '@react-native-firebase/messaging'

// Your app's background handler for incoming remote messages
firebase.messaging().setBackgroundMessageHandler(async (
  remoteMessage: FirebaseMessagingTypes.RemoteMessage
) => {
   await notifee.displayNotification(...)
   // Increment the count by 1
   await notifee.incrementBadgeCount();
})

notifee.onBackgroundEvent(async ({ type, detail }) => {
  const { notification, pressAction } = detail;

  // Check if the user pressed the "Mark as read" action
  if (type === EventType.ACTION_PRESS && pressAction.id === 'mark-as-read') {
    // Decrement the count by 1
    await notifee.decrementBadgeCount();

    // Remove the notification
    await notifee.cancelNotification(notification.id);
  }
});
```

If the user opens the application it is then safe to remove the badge count entirely:

```jsx
function App() {
  useEffect(() => {
    // App launched, remove the badge count
    notifee.setBadgeCount(0).then(() => console.log('Badge count removed'));
  }, []);

  ...
}
```
