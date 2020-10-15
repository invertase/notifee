---
title: Channels & Groups
description: Android Channels are a core concept of the notification system for Android. Learn how they impact you and your users.
next: /react-native/docs/android/foreground-service
previous: /react-native/docs/android/behaviour
---

# Understanding Channels

Starting in Android 8.0 (API level 26), all notifications must be assigned to a channel. Applications can create one or more notification channels. Each channel
can be customised with its own unique visual & auditory behaviour. Once a channel has been created with initial settings, the user
then has full control in changing those settings and can also decide how intrusive notifications from your application can be.

> Notifee provides rich control for managing notification channels whilst also supporting devices without channel support (API level < 26).

The screenshots below show the notification settings for the Android "Clock" app. The screenshot on the left shows listing channels as "categories". The screenshot on the right shows
the further options available once you click on a category, in this instance, the "Firing alarms & timers" category.

![Notification settings with channels](https://developer.android.com/images/ui/notifications/channel-settings_2x.png)

# Channel Groups

Channel Groups are available if you'd further like to organize the appearance of your channels in the settings UI. Creating channel groups is a good idea
when your application has many channels, or for example supports multiple accounts (such as work profiles). Groups allow users to easily identify
and control multiple notification channels.

> Assigning a channel to a group does not impact the channel settings, it only updates the applications settings UI.

The below screenshot shows an application's settings which contains two groups ("Personal" and "Work"), each with a notification channel
assigned.

![Channel groups example](https://developer.android.com/images/ui/notifications/channel-groups_2x.png)

# User Control

When a notification channel is first created, a number of settings can be applied by the application which instruct incoming notifications
how to behave when a notification belonging to that channel is created.

It is important to remember channels are designed to give users' control over your notifications.
Users are able to override notification behavior. The user can alter notification behavior for the entire app or for each channel.

# Managing Channels & Groups

Using Notifee, your application can create, update and delete notification channels and groups. The API has been designed to work seamlessly between
devices with and without notification channel support.

## Creating & updating channels

Channels can be both created and updated using the same process. If a channel does not exist on the application it will be created, and if it already
exists it will be updated. When developing an application, it is safe to always assume a channel does not exist and attempt to create it each time
the application boots.

To create a new channel, call the `createChannel` method. The only required fields are the unique `id` and a `name`, if other values are omitted the
channel will be created with sensible defaults, for example:

```js
import notifee, { AndroidImportance } from '@notifee/react-native';

await notifee.createChannel({
  id: 'alarm',
  name: 'Firing alarms & timers',
  lights: false,
  vibration: true,
  importance: AndroidImportance.DEFAULT,
});
```

> Remember! Once the channel has been created only metadata values such as the name can be updated.

On devices which do not support channels (API Level < 26), this method will successfully resolve gracefully. No channel will
be created or be visible in the application settings UI.

See the [AndroidChannel](/react-native/reference/androidchannel) reference API for descriptions and usage of all of the available
properties which can be applied to a channel.

## Deleting channels

To delete a channel from the application, call the `deleteChannel` method with the channel ID you wish to delete:

```js
await notifee.deleteChannel('alarm');
```

Be careful when deleting existing channels, any newly created notifications assigned to the deleted channel will no longer display.

## Creating & updating groups

Similar to creating channels, a notification group can be created and updated using the `createChannelGroup` method. The method
accepts a unique identifier and name. Once created, a channel can be assigned to the group by updating the `groupId` property on
the channel.

```js
// Create a group
await notifee.createChannelGroup({
  id: 'personal',
  name: 'Personal',
});

// Assign the group to the channel
await notifee.createChannel({
  id: 'comments',
  name: 'New Comments',
  groupId: 'personal',
});
```

See the [AndroidChannelGroup](/react-native/reference/androidchannelgroup) reference API for descriptions and usage of all of the available
properties which can be applied to a group.

## Deleting groups

To delete a channel group, call the `deleteChannelGroup` method with the channel group ID you wish to delete:

```js
await notifee.deleteChannelGroup('personal');
```

Deleting a group does not delete channels which are assigned to the group, they will instead be unassigned from the group and continue
to function as expected.

# Listening to channel events

Notifee provides a handy [event](/react-native/docs/events) listener to subscribe to all incoming notification and device events.

In some applications, you may want to know when a user disables a notification channel, or the entire application. For example,
you could save received notifications locally when the channel it belongs to is disabled, and show an in-app "unread"
notification count to the user when they open the application.

Notifee provides 3 event types for channel events:

| Event Type            |                                                                                             |
| --------------------- | ------------------------------------------------------------------------------------------- |
| APP_BLOCKED           | Triggered when the user has toggled to block/unblock all notifications for the application. |
| CHANNEL_BLOCKED       | Triggered when the user has toggled to block/unblock notifications of a specific channel.   |
| CHANNEL_GROUP_BLOCKED | Triggered when the user has toggled to block/unblock notifications of a channel group.      |

Each event type contains a payload with information specific to that event, such as whether channel/app has been blocked,
unblocked and the channel data itself. For example:

```js
import notifee, { EventType } from '@notifee/react-native';

notifee.onForegroundEvent(({ type, detail }) => {
  if (type === EventType.APP_BLOCKED) {
    console.log('User toggled app blocked', detail.blocked);
  }

  if (type === EventType.CHANNEL_BLOCKED) {
    console.log('User toggled channel block', detail.channel.id, detail.blocked);
  }

  if (type === EventType.CHANNEL_GROUP_BLOCKED) {
    console.log('User toggled channel group block', detail.channelGroup.id, detail.blocked);
  }
});
```

These events can also trigger when the user enters the notification settings for the first time, allowing you to
view the user's settings before they toggle the blocked state.

To learn more about handling events in your application, view the [events](/react-native/docs/events) documentation.
