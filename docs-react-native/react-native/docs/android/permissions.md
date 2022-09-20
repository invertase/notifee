---
title: Permissions
description: Understanding and Checking Android notification permissions
next: /react-native/docs/android/progress-indicators
previous: /react-native/docs/android/interaction
---

# Understanding permissions

On Android, notification permissions are granted by default. However, a user may revoke them later through various means. In addition, a user may manage or revoke permissions at any of three levels; application-wide, channel groups and channels. 

# Checking permissions

For each level, you can check whether notifications are permitted through a direct Notifee API call, or listen for changes with event listeners.

## Application wide

To check whether the user has enabled notifications for your app, call [`getNotificationSettings`](/react-native/reference/getnotificationsettings). The `authorizationStatus` attributes will returns `DENIED` if user has denied the permission, and `AUTHORIZED` if it's granted.

```js
import notifee from '@notifee/react-native';

async function checkNotificationPermission() {
  const settings = await notifee.getNotificationSettings();

  if (settings.authorizationStatus == AuthorizationStatus.AUTHORIZED) {
    console.log('Notification permissions has been authorized');
  } else if (settings.authorizationStatus == AuthorizationStatus.DENIED) {
    console.log('Notification permissions has been denied');
  }
}
```

If you determine notifications are disabled, you may want to offer the user the ability to open notification settings to enable it. The [`openNotificationSettings`](/react-native/reference/opennotificationsettings) API may be used to implement this feature.

## Channels & Channels Groups

To check whether the user has enabled notifications for specific channels or channel groups, call either [`getChannel`](/react-native/reference/getchannel) or [`getChannelGroups`](/react-native/reference/getchannelgroups). Both of these functions will return a respective object with `blocked` attributes in it.

```js
import notifee from '@notifee/react-native';

async function checkChannelPermission() {
  const channel = await notifee.getChannel();

  if (channel.blocked) {
    console.log('Channel is disabled');
  } else {
    console.log('Channel is enabled');
  }
}
```

> For more understanding on channels, you can refer to the [Channels & Groups](/react-native/docs/android/channels) documentation.

## Permission events

For listening to permission change events, refer to the [Listening to channel events](/react-native/docs/android/channels#listening-to-channel-events) documentation.
