---
title: Interaction
description: Handle the users interaction with your notifications.
next: /react-native/docs/android/permissions
previous: /react-native/docs/android/grouping-and-sorting
---

In their basic form, users can only interact with notifications by clearing them from the notification shade, via the
"Clear all" button on the device or by physically swiping them away. Notifee allows the user to interact
with notifications, and also hook onto events when the interaction occurs.

# Press Action

Notifee does not provide any default interaction behaviour when a user presses a notification. The Android guidelines
suggest a notification should always open the application when pressed. In React Native, we have multiple
options to exactly _how_ the application opens.

If a notification is pressed with no action, a `PRESS` [event](/react-native/docs/events) published to any subscribers.

## Simple behaviour

The most basic & common behaviour is to open the application which displayed the notification:

- If the application is already open and in the foreground, nothing happens.
- If the application is in a minimized state it is moved to the foreground.
- If the application is in a killed state, launch the application.

To set a simple press action, pass an `id` to the `pressAction` property:

```js
notifee.displayNotification({
  title: 'New notification',
  android: {
    channelId,
    pressAction: {
      id: 'default',
    },
  },
});
```

Notifee will now attempt to launch the application when the user presses the notification. View the
[App Open Events](/react-native/docs/events#app-open-events) documentation to learn how to read the notification
which caused the app to launch.

## (Advanced) Custom Component

If required, you can also launch a custom React component when a notification is pressed. This is an advanced concept
which allows fine-grain control over how your application is presented to the user.

First, register a new React component as soon as possible in your project:

```js
function CustomComponent() {
  return (
    <View>
      <Text>A custom component</Text>
    </View>
  );
}

AppRegistry.registerComponent('custom-component', () => CustomComponent);
```

Next, set the `mainComponent` property to the registered component name:

```js
notifee.displayNotification({
  title: 'New notification',
  android: {
    channelId,
    pressAction: {
      id: 'default',
      mainComponent: 'custom-component',
    },
  },
});
```

Inside of your `/android/app/src/main/java/.../MainActivity.java`, override and update the `getMainComponentName` method:

```java
package com.invertase.testing;
import com.facebook.react.ReactActivity;

// Import the NotifeeApiModule
import io.invertase.notifee.NotifeeApiModule;

public class MainActivity extends ReactActivity {
  @Override
  protected String getMainComponentName() {
    return NotifeeApiModule.getMainComponent("app");
  }
}
```

The `getMainComponent` accepts the default registered main component name, which will be used when no initial notification
is available or has no `mainComponent` set.

Rebuild your project. Now when pressed, the application will open using the registered custom component as the main component.

## (Advanced) Android Custom Activity

It is also possible to provide a custom Android Activity to launch. This method is in-place to allow users,
with native development experience, full control over notification interaction.

Create the new Activity in your project. It must extend `com.facebook.react.ReactActivity`:

```java
// com.awesome.app
import com.facebook.react.ReactActivity;

public class CustomActivity extends ReactActivity {
  @Override
  protected String getMainComponentName() {
    return "application";
  }
}
```

Now set the `launchActivity` on the `pressAction`:

```js
notifee.displayNotification({
  title: 'New notification',
  android: {
    channelId,
    pressAction: {
      id: 'default',
      launchActivity: 'com.awesome.app.CustomActivity',
    },
  },
});
```

Rebuild the project. Now when pressed, `CustomActivity` will be used to launch the React context.

# Dismiss Action

When a notification is displayed to users, they are able to dismiss them via the "Clear all" button or physically swiping
them away. This can be prevented by creating an ongoing notification (see [Behaviour](/react-native/docs/behaviour#ongoing))
or with [Foreground Service](/react-native/docs/foreground-service) notifications.

When dismissing you can hook into the `DISMISSED` event, for example, you could update a remote API indicate the user has
not read a notification:

```js
import notifee, { EventType } from '@notifee/react-native';

notifee.onBackgroundEvent(async ({ type, detail, headless }) => {
  if (type === EventType.DISMISSED) {
    // Update remote API
  }
});
```

View the [events](/react-native/docs/events) documentation for more about handling events.

# Quick Actions

Quick Actions enable users to interact with your application directly from the notification body, providing an overall greater
user experience.

<Vimeo id="android-actions-showcase" caption="Android Actions Example" />

Quick Action buttons allow users to perform tasks quickly, and can either trigger your application to open or perform a background
task (such as marking a conversation as read). The buttons can also trigger user input, allowing the user to enter free
text or choose from predefined options.

## Adding actions

A notification can add up to three actions buttons. To ensure a good user experience, we recommend
following these guidelines:

- Action text/icons should not be used more than once.
- Each action should have a clear and obvious meaning.
- The interaction of each action should be unique. Don't duplicate the action performed.
- Pressing the notification body (`via pressAction`) should not perform the same task as an action button.
- Action text should be kept as minimal as possible (e.g. Reply, Snooze).
- Once an action has been completed, the notification should be cancelled or updated.

To create notification actions, pass an array of [`AndroidAction`](/react-native/reference/androidaction) objects into the `actions` array of the notification
options:

```js
notifee.displayNotification({
  title: '08:00am Alarm',
  body: 'The alarm you set for 08:00am requires attention!',
  android: {
    channelId: 'alarms',
    actions: [
      {
        title: 'Snooze',
        icon: 'https://my-cdn.com/icons/snooze.png',
        pressAction: {
          id: 'snooze',
        },
      },
    ],
  },
});
```

The following code will create a notification with a single action "Snooze", with a unique identifier of `snooze`. Depending
on the device & Android API version, the `title` and/or `icon` will be used to display the action button.

## Handling interaction

In the example above, we provided the action an `pressAction` object with an `id` property. All notification actions
should do _something_, whether it's opening the application on a specific screen or performing a background task (e.g. snoozing
an alarm without opening the alarm application).

At a minimum, each `pressAction` requires a unique ID, which can be used with [events](/react-native/docs/events) to
hook into what action was pressed. When pressed, an action sends the `ACTION_PRESS` event type to any subscribers:

```js
import notifee, { EventType } from '@notifee/react-native';

notifee.onForegroundEvent(({ type, detail }) => {
  if (type === EventType.ACTION_PRESS && detail.pressAction.id) {
    console.log('User pressed an action with the id: ', detail.pressAction.id);
  }
});
```

## Opening the application

It is also possible to open the application when a notification action is pressed, for example to view a specific
chat conversation. To trigger the application to open (if it is not already), the `launchActivity` property on the
`pressAction` can be used. Notifee will use this activity to launch your application:

```js
notifee.displayNotification({
  title: 'New message',
  body: 'You have a new message from Sarah!',
  data: {
    chatId: '123',
  },
  android: {
    channelId: 'messages',
    actions: [
      {
        title: 'Open',
        icon: 'https://my-cdn.com/icons/open-chat.png',
        pressAction: {
          id: 'open-chat',
          launchActivity: 'default',
        },
      },
    ],
  },
});
```

View the [App Open Events](/react-native/docs/events#app-open-events) documentation to learn more about finding the
notification which caused your application to open.

## Action input

Users can reply via notification actions, in the form of free text input and/or pre-defined options.

### Free text input

To allow free text input on a notification action, set the `input` property on the action body to `true`:

```js
notifee.displayNotification({
  title: 'New message',
  body: 'You have a new message from Sarah!',
  android: {
    channelId: 'messages',
    actions: [
      {
        title: 'Reply',
        icon: 'https://my-cdn.com/icons/reply.png',
        pressAction: {
          id: 'reply',
        },
        input: true, // enable free text input
      },
    ],
  },
});
```

When set, the device will automatically display an input box when the "Reply" action is pressed:

![Input action example](https://developer.android.com/images/ui/notifications/reply-button_2x.png)

You may also notice the example provides a "Send" icon on the right hand side of the input area. When the user has entered
their free text and presses this icon, it will change to a "pending" state. A pending state indicates to the user
something is happening with their action. It is your responsibility to update the notification once the event has been handled
(e.g. removing it or updating the text). When the notification has been updated, the pending state will be removed automatically.

### Advanced input

The user may also select predefined choices by providing an object to the property `input`. Ensure `allowFreeFormInput` is set
to `false`, and provide an array of choices to the property `choices`:

```js
notifee.displayNotification({
  title: 'New message',
  body: 'You have a new message from Sarah!',
  android: {
    channelId: 'messages',
    actions: [
      {
        title: 'Reply',
        icon: 'https://my-cdn.com/icons/reply.png',
        pressAction: {
          id: 'reply',
        },
        input: {
          allowFreeFormInput: false, // set to false
          choices: ['Yes', 'No', 'Maybe'],
          placeholder: 'Reply to Sarah...',
        },
      },
    ],
  },
});
```

When pressed, the notification choices will automatically change to a pending state until the notification is updated
or cancelled.

To allow users to edit choices before being sent, the `allowFreeFormInput` & `editableChoices` must both be set to `true`.
View the [`AndroidInput`](/react-native/reference/androidinput) documentation for all advanced input options.

## Accessing user input

If the user has provided custom input via free text or choices, we're able to grab the input via the `onBackgroundEvent` subscriber:

```js
import notifee, { EventType } from '@notifee/react-native';

notifee.onBackgroundEvent(async ({ type, detail }) => {
  if (type === EventType.ACTION_PRESS && detail.action.id === 'reply') {
    await updateChat(detail.notification.data.chatId, detail.action.input);
    await notifee.cancelNotification(detail.notification.id);
  }
});
```

Input actions enter a pending state once sent, therefore we must cancel or update the notification once the action has
completed. In the example above, we updated a chat conversation with the input provided by the user.
