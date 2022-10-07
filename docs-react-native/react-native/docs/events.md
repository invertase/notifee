---
title: Events
description: Handling notification and device events.
next: /react-native/docs/triggers
previous: /react-native/docs/displaying-a-notification
---

Notification & device events are an important concept as they allow applications to react to events (e.g. a user pressing
a displayed notification and changing the in-app screen).

# Listening for events

Using Notifee it is possible to display notifications in various situations, for example via [Headless JS](https://facebook.github.io/react-native/docs/headless-js-android)
tasks, directly from your application (e.g. Button press) or via 3rd party services such as [FCM](/react-native/docs/integrations/fcm).

This flexibility opens up a key challenge for React Native developers; handling events when the application is both
active & killed. Notifee provides a simple API for handling both scenarios.

## Foreground Events

An application is deemed in the "foreground" only under the following situation:

- The device is unlocked, and the application is running & is in view (foreground).

In any other situation a [Background Event](/react-native/docs/events#background-events) is used instead. If the user
has opened your application and switched to another task (e.g. opened another app, pressed the "home" button) but not
closed/quit your application, it is still classed as being in the background.

To handle foreground events, the [`useEffect`](https://reactjs.org/docs/hooks-effect.html) hook can be used with the
[`onForegroundEvent`](/react-native/reference/onforegroundevent) method:

```jsx
import { useEffect } from 'react';
import notifee, { EventType } from '@notifee/react-native';

function App() {
  // Subscribe to events
  useEffect(() => {
    return notifee.onForegroundEvent(({ type, detail }) => {
      switch (type) {
        case EventType.DISMISSED:
          console.log('User dismissed notification', detail.notification);
          break;
        case EventType.PRESS:
          console.log('User pressed notification', detail.notification);
          break;
      }
    });
  }, []);
}
```

> To view all event types, view the [EventType](/react-native/reference/eventtype) documentation.

The foreground event handler runs inside of our React Native code, allowing you to update the application UI or perform
asynchronous actions such as performing a HTTP request.

To learn about handling user interaction with events, view the [Android Interaction](/react-native/docs/android/interaction)
documentation.

### Unsubscribing

The `onForegroundEvent` method returns a function which can be used to unsubscribe from future events:

```js
const unsubscribe = notifee.onForegroundEvent(...);

// Sometime later...
unsubscribe();
```

## Background events

An application is deemed in the "background" under the following situations:

- The device is locked.
- The application is running & is not in view (minimized).
- The application is killed/quit.

The [`onBackgroundEvent`](/react-native/reference/onbackgroundevent) method is used to register a callback handler
which will be executed whenever a background event is sent. Executing the callback handler in the background requires
device resources so it is important that any code is executed efficiently & quickly.

> To run long running tasks on Android, view the [Foreground Service](/react-native/docs/android/foreground-service) documentation.

Background tasks run without React context, meaning you cannot update your application UI. You can however perform logic
to update a remote database, update local device storage or even display/update a notification with Notifee! An example
of a background event would be handling a "Mark as read" action by updating your database and cancelling the notification.

Only a single background event handler can be registered. To register your handler, the [`onBackgroundEvent`](/react-native/reference/onbackgroundevent)
method should be registered as early on in your project as possible (e.g. the `index.js` file):

```js
// index.js
import { AppRegistry } from 'react-native';
import notifee, { EventType } from '@notifee/react-native';
import App from './App';

notifee.onBackgroundEvent(async ({ type, detail }) => {
  const { notification, pressAction } = detail;

  // Check if the user pressed the "Mark as read" action
  if (type === EventType.ACTION_PRESS && pressAction.id === 'mark-as-read') {
    // Update external API
    await fetch(`https://my-api.com/chat/${notification.data.chatId}/read`, {
      method: 'POST',
    });

    // Remove the notification
    await notifee.cancelNotification(notification.id);
  }
});

// Register main application
AppRegistry.registerComponent('app', () => App);
```

The handler callback expects that once the task has completed a Promise is returned/resolved. The above example makes use
of an [`async function`](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Statements/async_function) which
returns an implicit Promise once complete. If you are not using `async`, ensure your handler callback returns a promise
once complete.

> Please note, for iOS, the `DELIVERED` event is not fired for trigger notifications when the app is in the background.

# App open events

If user interaction on a notification has caused your application to open, you may want to obtain the notification which
triggered the app to open (e.g. a specific chat message). Notifee exposes a `getInitialNotification` method on Android,
which can be called early on in your React lifecycle to obtain the notification which opened the application.

> `getInitialNotification` is deprecated on iOS in favour of the `PRESS` event received by the `onForegroundEvent` event handler

It is recommended to always check if a notification has caused your app to open before displaying the main app content.
Once consumed, the initial notification is removed.

For example, setup a "bootstrap" function inside of the root component of your application:

```jsx
import React, { useState, useEffect } from 'react';
import notifee from '@notifee/react-native';

function App() {
  const [loading, setLoading] = useState(true);

  // Bootstrap sequence function
  async function bootstrap() {
    const initialNotification = await notifee.getInitialNotification();

    if (initialNotification) {
      console.log('Notification caused application to open', initialNotification.notification);
      console.log('Press action used to open the app', initialNotification.pressAction);
    }
  }

  useEffect(() => {
    bootstrap()
      .then(() => setLoading(false))
      .catch(console.error);
  }, []);

  if (loading) {
    return null;
  }

  ...
}
```

If available, the `initialNotification` contains the notification & press action which triggered the app to open. Both
can be combined to perform logic inside of your application which the user expects from their interaction, for example,
opening a chat screen with a specific user who trigger the notification.

Once the initial notification has been consumed with a call to `getInitialNotification`, it is removed. If the app re-opens
before the initial notification has been consumed (e.g. the user manual closing & reopening), it will not be available.
