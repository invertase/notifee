---
title: Foreground Service
description: Display a notification when running an ongoing headless task.
next: /react-native/docs/android/grouping-and-sorting
previous: /react-native/docs/android/channels
---

Foreground services are an advanced Android concept which allows you to display notifications to your users
when running long lived background tasks. The notification acts like any other notification, however it cannot be removed
by the user and lives for the duration of the service.

<Vimeo id="android-foreground-service" caption="Android Foreground Service" />

The service task can run for as long as required whilst the application is open, alive or killed. Once the task has complete
the service is stopped and the notification is removed. Examples of when to use a Foreground Service are:

- Capturing on-going information (such as current location for a fitness or delivery app).
- Playing media to the user.
- Displaying important information to the user, such as directions.
- Showing the status of a local device task, such as deleting files.

> Only a single Foreground Service can be running at any one time for an application.

To create a long running task, Notifee exposes a `registerForegroundService` method. It should be registered
outside of any React components as early as possible in your code (e.g. within the project `index.js` file).

> The Foreground Service is not persisted between hot reloads.

# Creating a service

Let's create our foreground service which we'll later hook into. The callback we register must be a promise, and is passed
a notification object when called.

```js
import notifee from '@notifee/react-native';

notifee.registerForegroundService((notification) => {
  return new Promise(() => {
    // Long running task...
  });
});
```

Whenever you call `stopForegroundService`, the foreground service will close and the notification will be removed. The service can be
stopped by the device (e.g. low memory) or if the user force quits from the application settings.

## Attach a notification

To attach a new notification to the service, the `asForegroundService` property on the notification object can be set to
`true`:

```js
import notifee, { AndroidColor } from '@notifee/react-native';

notifee.displayNotification({
  title: 'Foreground service',
  body: 'This notification will exist for the lifetime of the service runner',
  android: {
    channelId,
    asForegroundService: true,
    color: AndroidColor.RED,
    colorized: true,
  },
});
```

Running this code will create a foreground service notification which is bound to the runner we registered with
`registerForegroundService` above. As our example never returns the promise, the notification will exist for the lifetime
of the application.

You may also notice we have provided `color` and `colorized` properties. When setting `colorized` to `true` on a
foreground service notification, the `color` will be used to change the entire background color of the notification,
which is not possible on standard notifications.

## Building a long lived task

To simplify the experience for developers, a long lived task will continuously run until you call `stopForegroundService`. This allows us to create intervals, or subscribe to events which we can use to update the notification.

For example, we could build a task which subscribes to an event handler:

```js
notifee.registerForegroundService(() => {
  return new Promise(() => {
    // Example task subscriber
    onTaskUpdate(task => {
      if (task.complete) {
          await notifee.stopForegroundService()
      }
    });
  });
});
```

The example code above would show the notification to the user until a new task event with `complete` being true is
sent.

## Updating an existing notification

Foreground notifications behave like any other notification, and can display anything (progress indicators, images etc).
Whilst our service is running, we can also update the current notification to display different content:

```js
notifee.registerForegroundService((notification) => {
  return new Promise(() => {
    // Example task subscriber
    onTaskUpdate(task => {
      if (task.update) {
        notifee.displayNotification({
          id: notification.id,
          body: notification.body,
          android: {
            ...notification.android,
            progress: {
              max: task.update.total,
              current: task.update.current,
            },
          },
        });
      }

      if (task.complete) {
        await notifee.stopForegroundService()
      }
    });
  });
});
```

Above we've updated the callback handler for the `onTaskUpdate` method. Each `task.update` call would update the current notification with a new progress indicator
position. It is important we update the existing notification by ID, otherwise the foreground service would stop and restart. Only a single foreground service
notification can exist for your application at any one time.

## Handling interactions

Much like other notifications, we can subscribe to [events](/react-native/docs/events) when the user interacts with the
Foreground Service notification. The service task runs in its own context, allowing us to subscribe to events within itself.

For example, to stop the foreground service when a user presses an action, we can subscribe to the event inside of the
task:

```js
import notifee, { EventType } from '@notifee/react-native';

// Create the task runner
notifee.registerForegroundService((notification) => {
  return new Promise(() => {
    notifee.onForegroundEvent(({ type, detail }) => {
      if (type === EventType.ACTION_PRESS && detail.pressAction.id === 'stop') {
        await notifee.stopForegroundService()
      }
    });
  });
});
```

A notification can then be created with a [Quick Action](/react-native/docs/android/interaction#quick-actions) which
triggers the event:

```js
notifee.displayNotification({
  title: 'Foreground Service Notification',
  body: 'Press the Quick Action to stop the service',
  android: {
    channelId,
    actions: [
      {
        title: 'Stop',
        pressAction: {
          id: 'stop',
        },
      },
    ],
  },
});
```

It's important to note, for background events there can only be only a single handler registered at one time. It's recommended to listen to your service events in your application's main [`onBackgroundEvent`](/react-native/docs/events#background-events) callback handler so you can continue to listen to other events outside of the service.

## Specifying foreground service types

You may need access to either location, camera or microphone information in your foreground service.

To specify which service types you require, add `notifee`'s foreground service to your `AndroidManifest.xml`:

```xml
<manifest>
    ...
    <!-- For example, with one service type -->
    <service android:name="app.notifee.core.ForegroundService" android:foregroundServiceType="location" />

     <!-- Or, with multiple service types -->
    <service android:name="app.notifee.core.ForegroundService" android:foregroundServiceType="location|camera|microphone" />
</manifest>
```
