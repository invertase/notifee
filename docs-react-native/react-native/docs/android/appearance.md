---
title: Appearance
description: Notifications can be displayed to your users in a variety of ways.
next: /react-native/docs/android/behaviour
previous: /react-native/docs/android/introduction
---

This page covers how to customise the appearance of notifications. Whilst changing the appearance can improve
the overall aesthetics of a notification, it isn't required. A notification will simply use default appearance attributes.

# Text Styling

Notifee supports basic HTML attributes for the majority of text properties, allowing you to customize the appearance
of notifications. It is possible to change the text color, font weight and add unicode characters.

<Vimeo id="android-text-style" caption="Android Text Styling Example" />

The code for the example video:

```js
notifee.displayNotification({
  title: '<p style="color: #4caf50;"><b>Styled HTMLTitle</span></p></b></p> &#128576;',
  subtitle: '&#129395;',
  body:
    'The <p style="text-decoration: line-through">body can</p> also be <p style="color: #ffffff; background-color: #9c27b0"><i>styled too</i></p> &#127881;!',
  android: {
    channelId,
    color: '#4caf50',
    actions: [
      {
        title: '<b>Dance</b> &#128111;',
        pressAction: { id: 'dance' },
      },
      {
        title: '<p style="color: #f44336;"><b>Cry</b> &#128557;</p>',
        pressAction: { id: 'cry' },
      },
    ],
  },
});
```

Any unsupported HTML attributes will be automatically removed from the body.

# Icons

Notifications can be shown with two types of icons; small & large. Each icon type has a different purpose as outlined below.

## Small Icons

Small Icons are primarily used for users to identify your app, typically matching your app's logo. Alternatively, they can be used to reflect the content type of the notification. For example, Google uses a TV icon to represent notifications about Films and TV Shows.

The small icon must have a transparent background, otherwise Android will display a solid square.

To create a small icon, you must add the resource directly from Android studio, which will take care of device compatibility and format:

1. Using Android Studio, open your project's `android` directory.
2. Open the `app/main` directory in the file tree.
3. Right click on the `main` directory & select `New -> Image Asset`.
4. Select the "Notification Icons" icon type, and follow the wizard by creating your new icon.![android-small-icon-1](https://user-images.githubusercontent.com/14185925/86519506-52347300-be33-11ea-800b-45019ca83c1e.png)
5. Once finished, Android Studio will inject multiple icon files into your projects resource (`res`) directory.
   ![android-small-icon-2](https://user-images.githubusercontent.com/14185925/86519517-67a99d00-be33-11ea-9ecb-c0b413d5acd0.png)

To set a small icon, add a `smallIcon` property to the notification body:

```js
notifee.displayNotification({
  title: 'Small Icon',
  body: 'A notification using the small icon!.',
  android: {
    // Reference the name created (Optional, defaults to 'ic_launcher')
    smallIcon: 'ic_small_icon',

    // Set color of icon (Optional, defaults to white)
    color: '#9c27b0',
  },
});
```

<Vimeo id="android-small-icon" caption="Android Small Icon Example" />

The image file used in the example can be downloaded [here](https://user-images.githubusercontent.com/14185925/86519462-cde1f000-be32-11ea-8ae6-cdec95754866.png).

## Large Icons

Large icons are used to give a notification additional context, for example showing the avatar of the user sending you a
message. The large icon will be shown in different places by the device depending on the notification configuration.

<Vimeo id="android-large-icon" caption="Android Large Icon Example" />

There are a number of different ways to display a large icon:

- Remote image URL.
- Local image (e.g. `require('./image.jpg')`).
- Absolute file path (e.g. `file:///xxxx/xxxx/xxxx.jpg`).
- Android resources (checks mipmap first, then drawable - Note there appears to be an Android limitation where if the mipmap folder contains any xml files, the icon will not load).

The image will be automatically scaled to fit inside of the notification. It is
recommended you use images a minimum size of 192x192 pixels to handle all device resolutions.

To set a large icon, add a `largeIcon` property to the notification body:

```js
notifee.displayNotification({
  title: 'Chat with Joe Bloggs',
  body: 'A new message has been received from a user.',
  android: {
    // Remote image
    largeIcon: 'https://my-cdn.com/users/123456.png',

    // Local image
    largeIcon: require('../assets/user.jpg'),

    // Absolute file path
    largeIcon: file:///xxxx/xxxx/xxxx.jpg,

    // Android resource (mipmap or drawable)
    largeIcon: 'large_icon',
    
    // Base 64 image
    largeIcon: `data:${image.mime};base64,${image.rawBase64Data}`
  },
});
```

# Badges

Android devices on 8.0 (API level 26) and above have a feature called "notification badges". Badges are a way for users
to interact with notifications without pulling down the notification shade, or opening the app. When enabled, your
application icon will show a "notification dot" (see below) when there are unread notifications.

When a user long-presses on your application icon, the notification badge will appear with unread notification information:

<Vimeo id="android-badge" caption="Android Badge Example" />

Since badges are only available on Android 8.0 and above, the devices will also have [channel](/react-native/docs/android/channels)
support. Using Notifee, there are two ways to control notification badges; via channels and on the notification.

When creating a channel, set the `badge` property to `true` or `false` to control whether notifications posted to the
channel will appear in the badge if unread. This property defaults to `true` when creating a channel:

```js
notifee.createChannel({
  id: 'messages',
  name: 'Private Messages',
  badge: false, // disable in badges
});
```

> You cannot update the badge property value once the channel has been created.

With channel badge support enabled, you can further choose how the icon appears inside of the badge. The example image
above shows the notification large icon being displayed.

To control what icon is used on the badge, the `badgeIconType` property on the notification body can be set. This property
accepts one of three types:

| Type  |                                                                                                                                         |
| ----- | --------------------------------------------------------------------------------------------------------------------------------------- |
| None  | Uses the default preference of the device launcher. Some launchers will display no icon, others will use the `largeIcon` (if provided). |
| Small | Uses the icon provided to `smallIcon`, if available.                                                                                    |
| Large | Uses the icon provided to `largeIcon`, if available.                                                                                    |

The default type used is "Large". To use a different type:

```js
import notifee, { AndroidBadgeIconType } from '@notifee/react-native';

notifee.displayNotification({
  body: 'Notification using small icon in badged mode',
  android: {
    channelId, // channel with badges enabled
    badgeIconType: AndroidBadgeIconType.SMALL,
  },
});
```

# Importance

The importance of a notification changes how it is presented to users. Notifications can be assigned an importance via
the channel for devices on Android 8.0 (API Level 26) and above, or directly on the notification for devices without
channel support.

See below to see how to set an importance level.

## Levels

There are five types of importance levels which can be used to display a notification.

### High

The highest importance level applied to a channel/notification. Notifications will appear on-top of applications, allowing direct interaction without pulling down the notification shade. This level should only be used for urgent notifications, such as incoming phone calls, messages etc, which require immediate attention.

<Vimeo id="android-importance-high" caption="Android High Importance Notification" />

### Default

When no importance has been assigned, the default will be used. When a notification is received, the device `smallIcon` will appear in the notification shade. When the user pulls down the notification shade, the content of the notification will be shown in it's expanded state.

<Vimeo id="android-importance-default" caption="Android Default Importance Notification" />

### Low

A low importance level applied to a channel/notification. The application small icon will show in the device status bar, however the notification will not alert the user (no sound or vibration). The notification will show in it's expanded state when the notification shade is pulled down.

<Vimeo id="android-importance-low" caption="Android Low Importance Notification" />

### Minimum

The minimum importance level applied to a channel/notification. The application small icon will not show up in the status bar, or alert the user. The notification will be in a collapsed state in the notification shade and placed at the bottom of the list.

<Vimeo id="android-importance-minimum" caption="Android Minimum Importance Notification" />

### None

The notification will not be shown. This has the same effect as the user disabling notifications in the application settings.

## Setting importance level

You should always choose an appropriate importance level for the type of notification. Showing an irrelevant or
meta notification (e.g. recommended content) at high importance will more than likely cause the user to disable notifications
for your entire application.

On devices with [channel](/react-native/docs/android/channels) support, the importance is set on the channel. On devices
without channel support, the importance can be set directly on the notification:

```js
import notifee, { AndroidImportance } from '@notifee/react-native';

const channelId = await notifee.createChannel({
  id: 'important',
  name: 'Important Notifications',
  importance: AndroidImportance.HIGH,
});

await notifee.displayNotification({
  title: 'Your account requires attention',
  body: 'You are overdue payment on one or more of your accounts!',
  android: {
    channelId,
    importance: AndroidImportance.HIGH,
  },
});
```

If your application does not target devices lower than Android 8.0 (API Level 26), you can omit the `importance` property
from the notification body.

# Visibility

Controlling the visibility of a notification on the users device is important, especially if a notification contains
sensitive information (such as a banking app). Setting the visibility controls how a notification is shown to a locked
device on the lockscreen.

There are three types of visibilities:

| Type    |                                                                                                               |
| ------- | ------------------------------------------------------------------------------------------------------------- |
| Private | Show the notification on all lockscreens, but conceal information on devices with secure lockscreens enabled. |
| Public  | Show the notification in its entirety on all lockscreens.                                                     |
| Secret  | Do not reveal/show any part of this notification on a secure lockscreen.                                      |

The default visibility is Private.

On devices with [channel](/react-native/docs/android/channels) support, the visibility is set on the channel. On devices
without channel support, the visibility can be set directly on the notification:

```js
import notifee, { AndroidVisibility } from '@notifee/react-native';

const channelId = await notifee.createChannel({
  id: 'secret',
  name: 'Secret Notifications',
  visibility: AndroidVisibility.SECRET,
});

await notifee.displayNotification({
  title: 'Your payment information',
  body: 'A payment has been made to another account',
  android: {
    channelId,
    visibility: AndroidVisibility.SECRET,
  },
});
```

If your application does not target devices lower than Android 8.0 (API Level 26), you can omit the `visibilty` property
from the notification body.

View the [`AndroidVisibility`](/react-native/reference/androidvisibility) documentation for more information.

# Color

Notifications can be colored to match with your application theme. A single color can be used and will be automatically
applied by the system.

<Vimeo id="android-color" caption="Android Red Colored Notification" />

The device uses the color to change various parts of the notification:

- Tinting the small icon.
- Setting text color on notification action text.
- Tinting the progress bar color when using [progress indicators](/react-native/docs/android/progress-indicators).
- Setting the background color of a notification input box.
- Changing the background color of the entire notification when used with [Foreground Services](/react-native/docs/android/foreground-service).

You can use system colors, or a custom hexadecimal color, for example:

```js
import notifee, { AndroidColor } from '@notifee/react-native';

notifee.displayNotification({
  android: {
    color: AndroidColor.RED,
    // or
    color: '#E8210C', // red
  },
});
```

View the [`AndroidColor`](/react-native/reference/androidcolor) documentation for all available system colors.
