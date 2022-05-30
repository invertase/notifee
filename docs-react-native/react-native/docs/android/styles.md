---
title: Styles
description: Android provides out of the box styles for your notifications.
next: /react-native/docs/android/timers
previous: /react-native/docs/android/progress-indicators
---

A basic notification usually includes a title, a line of text and/or actions. Using the `style` property on a notification,
you can provide additional information which can be viewed when the user expands the notification, giving better overall
context of the notification.

# Big Picture

The big picture style allows you to display a large resolution image within the body of your notification when expanded,
and it's possible to also override any large icons.

<Vimeo id="android-style-bigpicture" caption="Android Big Picture Style" />

To implement this style, we provide the `AndroidStyle.BIGPICTURE` to the `style` object:

```js
import notifee, { AndroidStyle } from '@notifee/react-native';

notifee.displayNotification({
  title: 'Image uploaded',
  body: 'Your image has been successfully uploaded',
  android: {
    channelId,
    style: { type: AndroidStyle.BIGPICTURE, picture: 'https://my-cdn.com/user/123/upload/456.png' },
  },
});
```

It is also possible to override the `title`, `largeIcon` and `summary` text when the notification is in the expanded state.
To learn more, view the [`AndroidBigPictureStyle`](/react-native/reference/androidbigpicturestyle) documentation.

# Big Text

In its collapsed form, the notification body of text will truncate itself if it spans more than a few lines (depending on space).
The big text style allows you to show a large volume of text when expanded. This is commonly used for messages/emails by apps
such as GMail.

<Vimeo id="android-style-bigtext" caption="Android Big Text Style" />

To implement this style, we provide the `AndroidStyle.BIGTEXT` to the `style` object:

```js
import notifee, { AndroidStyle } from '@notifee/react-native';

notifee.displayNotification({
  title: 'Image uploaded',
  body: 'Your image has been successfully uploaded',
  android: {
    channelId,
    style: { type: AndroidStyle.BIGTEXT, text: 'Large volume of text shown in the expanded state' },
  },
});
```

It is also possible to override the `title` and `summary` text when the notification is in the expanded state.
To learn more, view the [`AndroidBigTextStyle`](/react-native/reference/androidbigtextstyle) documentation.

# Inbox

Inbox style notifications are used to display multiple lines of content inside of a single notification. Depending on space,
the device will show as many lines of text as possible, and "hide" the remainder.

<Vimeo id="android-style-inbox" caption="Android Inbox Style" />

To implement this style, we provide the `AndroidStyle.INBOX` to the `style` object:

```js
import notifee, { AndroidStyle } from '@notifee/react-native';

notifee.displayNotification({
  title: 'Messages list',
  android: {
    channelId,
    style: {
      type: AndroidStyle.INBOX,
      lines: ['First Message', 'Second Message', 'Third Message', 'Forth Message'],
    },
  },
});
```

It is also possible to override the `title` and `summary` text when the notification is in the expanded state.
To learn more, view the [`AndroidInboxStyle`](/react-native/reference/androidinboxstyle) documentation.

# Messaging

Message style notifications are used when you wish to display the history of an ongoing chat.

<Vimeo id="android-style-messaging" caption="Android Messaging Style" />

To implement this style, we provide the `AndroidStyle.MESSAGING` to the `style` object:

```js
import notifee, { AndroidStyle } from '@notifee/react-native';

notifee.displayNotification({
  title: 'Sarah Lane',
  body: 'Great thanks, food later?',
  android: {
    channelId,
    style: {
      type: AndroidStyle.MESSAGING,
      person: {
        name: 'John Doe',
        icon: 'https://my-cdn.com/avatars/123.png',
      },
      messages: [
        {
          text: 'Hey, how are you?',
          timestamp: Date.now() - 600000, // 10 minutes ago
        },
        {
          text: 'Great thanks, food later?',
          timestamp: Date.now(), // Now
          person: {
            name: 'Sarah Lane',
            icon: 'https://my-cdn.com/avatars/567.png',
          },
        },
      ],
    },
  },
});
```

Whenever a new message is received, the notification created can be expanded to display the previous conversation messages.

At the root, the style accepts a `person` property, which contains information of the current device user. To see more about
the options available to you, view the [`AndroidPerson`](/react-native/reference/androidperson) documentation.

The property `messages`, is an array of historic messages (the first item being the earliest message). Messages generally
contain `text`, a `timestamp` and a `person` (if the user is remote). To view the full list of message options, view the
[`AndroidMessagingStyleMessage`](/react-native/reference/androidmessagingstylemessage) documentation.

We can also update the notification `title` when expanded, and flag to the system the message consists of a group of users
by setting the `group` property to `true`.

# Media

Media style notifications are a way of showing a notification with intractable & dynamic content, for example the current
song/video playing with album art.

> Currently, Media Style notifications are not supported.
