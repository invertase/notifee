---
title: Progress Indicators
description: Use notifications to display progress on activities within your application.
next: /react-native/docs/android/styles
previous: /react-native/docs/android/permissions
---

Notifications can display progress indicators to show users a status of an ongoing operation, for example, the progress
of file being uploaded/download.

<Vimeo id="android-progress-summary" caption="Android Progress (w/ Big Picture Style)" />

Progress indicators can be added alongside other notification options, and can be updated many times to show realtime progress.

# Creating a progress indicator

To create a notification with a progress indicator, a `progress` object can be attached to the notifications `android`
object:

```js
notifee.displayNotification({
  android: {
    progress: {
      max: 10,
      current: 5,
    },
  },
});
```

The `max` value indicates the maximum value of the progress bar. The `current` indicating the current progress value.
The values can be any positive integer, and the `current` value cannot be greater than the `max`.

## Indeterminate progress

In some scenarios, it may not be possible to determine the overall progress of an ongoing operation. It is also possible
to show an indeterminate (unknown) progress state, where the progress indicator is in a state of flux (animating left-to-right) until it is removed by the user.
This can be achieved using the `indeterminate` flag:

```js
notifee.displayNotification({
  android: {
    progress: {
      max: 10,
      current: 5,
      indeterminate: true,
    },
  },
});
```

If `indeterminate` is true, any `max` and `current` values will be ignored.

# Updating a progress indicator

In most cases, you'll want to handle the progress "complete" status of a notification.

<Vimeo id="android-progress-update" caption="Android Progress Updating" /> 
 
To update a notification, use the unique ID generated when creating the notification for the first time. The following
example upload task event handler updates an existing notification (using the `id`) each time a new event.

```js
onUploadTaskEvent(async (event, upload) => {
  if (event.status === 'start') {
    await notifee.displayNotification({
      id: upload.id,
      android: {
        progress: {
          max: upload.size,
          current: 0,
        },
      },
    });
  }

  if (event.status === 'update') {
    await notifee.displayNotification({
      id: upload.id,
      android: {
        progress: {
          max: upload.size,
          current: upload.current,
        },
      },
    });
  }

  if (upload.size === upload.current) {
    await notifee.displayNotification({
      id: upload.id,
      title: 'Finalizing upload...',
      android: {
        progress: {
          indeterminate: true,
        },
      },
    });
  }

  if (event.status === 'complete') {
    await notifee.cancelNotification(upload.id);
  }
});
```

Displaying multiple notifications will also cause the device to notify the user multiple times (e.g. a sound for each update).
Use the `onlyAlertOnce` property to only alert the user the first time a notification is displayed:

```js
notifee.displayNotification({
  title: 'Updating',
  android: {
    onlyAlertOnce: true,
  },
});
```
