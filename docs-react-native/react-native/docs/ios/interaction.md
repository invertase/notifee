---
title: Interaction
description: Handle user interaction with the notification and quick actions.
next: /react-native/docs/ios/permissions
previous: /react-native/docs/ios/categories
---

Users can interact with notifications in a number of ways; via the Notification Center, when they display in
heads-up mode or via [Quick Actions](#quick-actions).

# Press Action

When a notification is displayed on the device, if the user presses it iOS will cause the application to open. You can
hook into this [event](/react-native/docs/events) if required:

```js
import notifee, { EventType } from '@notifee/react-native';

notifee.onBackgroundEvent(async ({ type, detail }) => {
  if (type === EventType.PRESS) {
    console.log('User pressed the notification.', detail.pressAction.id);
  }
});
```

The `detail` provided with the event contains a `pressAction` with an `id` of `default`. Since there is no way to
override the iOS behaviour for notification press, this is automatically set.

If your application has been freshly launched via a notification press, it will generate an [App open event](/react-native/docs/events#app-open-events).

# Quick Actions

Quick Actions are a way of making a notification more interactive, allowing users to perform quick actions directly from
the notification (e.g. within the devices Notification Center) and not open the application. For example, an incoming
message to a user displayed as a notification could have a "Mark as read" action, along with a "Reply" action which
lets the send a message.

<Vimeo id="ios-category-actions" caption="iOS Category Actions Example" />

At minimum, an action requires a unique identifier & text:

```js
async function setCategories() {
  await notifee.setNotificationCategories([
    {
      id: 'message',
      actions: [
        {
          id: 'mark-as-read',
          title: 'Mark as read',
        },
      ],
    },
  ]);
}
```

## Handling interaction

Whenever an user interacts with an action, an [event](/react-native/docs/events) is sent to the device. The payload
contains the `id` within the `pressAction` property. For example, if the application is in the background and the user
presses a `mark-as-read` action, we can access it by listening to the `ACTION_PRESS` event:

```js
import notifee, { EventType } from '@notifee/react-native';

notifee.onBackgroundEvent(async ({ type, detail }) => {
  if (type === EventType.ACTION_PRESS && detail.pressAction.id === 'mark-as-read') {
    console.log('User pressed the "Mark as read" action.');
  }
});
```

## Foreground Action

The default behaviour when a user interacts with an action is to trigger an event via the `onBackgroundEvent` method
and remove the notification from the device (marking as read). You can however force the action to open the application
into the foreground by setting the `foreground` property to `true`:

```js
async function setCategories() {
  await notifee.setNotificationCategories([
    {
      id: 'message',
      actions: [
        {
          id: 'view-post',
          title: 'View post',
          // Trigger the app to open in the foreground
          foreground: true,
        },
      ],
    },
  ]);
}
```

Once pressed, the application will launch & send a `ACTION_PRESS` event.

## Destructive Action

The action can be further expanded to be a "destructive" one, or only show if the device is unlocked:

```js
async function setCategories() {
  await notifee.setNotificationCategories([
    {
      id: 'message',
      actions: [
        {
          id: 'view-post',
          title: 'View post',
          foreground: true,
        },
        {
          id: 'delete-chat',
          title: 'Delete chat',
          destructive: true,
          // Only show if device is unlocked
          authenticationRequired: true,
        },
      ],
    },
  ]);
}
```

The action will appear with red text, warning the user the action may have destructive intent.

<Vimeo id="ios-action-destructive" caption="iOS Destructive Action" />

By default, a destructive action will not cause the application to open, however the `ACTION_PRESS` event will still be sent
regardless.

## Action input

Notifee also supports allowing users to provide custom user input via an action. To enable this functionality, set the
`input` property to `true`:

```js
async function setCategories() {
  await notifee.setNotificationCategories([
    {
      id: 'message',
      actions: [
        {
          id: 'reply',
          title: 'Reply',
          input: true,
        },
      ],
    },
  ]);
}
```

When the action is pressed, an input box and send button will automatically appear allowing for custom input:

<Vimeo id="ios-action-input" caption="iOS Action Input" />

If required, the input box placeholder text and send button text can be customised by providing an object to the `input`
property:

```js
async function setCategories() {
  await notifee.setNotificationCategories([
    {
      id: 'message',
      actions: [
        {
          id: 'reply',
          title: 'Reply',
          input: {
            placeholderText: 'Send a message...',
            buttonText: 'Send Now',
          },
        },
      ],
    },
  ]);
}
```

### Accessing input

In most cases you'll want to extract the users input and perform an action (e.g. send an API request to your servers).
Once the "Send" button is pressed, the `ACTION_PRESS` event triggers, sending the `pressAction` along with an
`input` property:

```js
import notifee, { EventType } from '@notifee/react-native';

notifee.onBackgroundEvent(async ({ type, detail }) => {
  const { notification, pressAction, input } = detail;

  if (type === EventType.ACTION_PRESS && pressAction.id === 'reply') {
    updateChatOnServer(notification.data.conversationId, input);
  }
});
```
