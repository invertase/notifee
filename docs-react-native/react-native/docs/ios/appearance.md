---
title: Appearance
description: Customising the appearance of your iOS notifications with Notifee.
next: /react-native/docs/ios/badges
previous: /react-native/docs/ios/introduction
---

## Attachments

Using the `attachments` property on a notification, you can attach a media file which can be viewed when the user expands
the notification or as a thumbnail on the notification itself.

<Vimeo id="ios-attachments" caption="iOS Attachments Example" />

When a notification is collapsed, a thumbnail will be shown in the notification, unless `thumbnailHidden` is set to true.

Only a single media file can be shown on the notification, if you provide many then the first attachment that can be successfully resolved will be displayed.

To add attachments, provide an `attachments` array to the `ios` object:

```js
import notifee from '@notifee/react-native';

notifee.displayNotification({
  title: 'Media uploaded',
  body: 'Your media has been successfully uploaded',
  ios: {
    attachments: [
      {
        // iOS resource
        url: 'local-image.png',
        thumbnailHidden: true,
      },
      {
        // Local file path.
        url: '/Path/on/device/to/local/file.mp4',
        thumbnailTime: 3, // optional
      },
      {
        // React Native asset.
        url: require('./assets/my-image.gif'),
      },
      {
        // Remote image
        url: 'https://my-cdn.com/images/123456.png',
      },
    ],
  },
});
```

Audio, image and video files are supported.

See [`IOSNotificationAttachment`](/react-native/reference/iosnotificationattachment) for all available attachment options.

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
      badge: true,
      sound: true,
      banner: true,
      list: true,
    },
  },
});
```

## Summary text

After a number of unread notifications have been delivered to the device, iOS will begin to stack (or group) notifications for your
application. The notification on the top of the stack by default will display the summary text, letting the user know how many
unread notifications are available:

<Vimeo id="ios-summary-text" caption="Default Summary Text Example" />

It is possible to override this default text by providing `summaryArgument` & `summaryArgumentCount` fields
on the notification body combined with a [Category](/react-native/docs/ios/categories) `summaryFormat`.

To learn more, view the [Category Summary Text](/react-native/docs/ios/categories#category-summary-text)
documentation.

> This functionality is only available on iOS >= 12.
