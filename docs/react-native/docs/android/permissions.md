---
title: Permissions
description: Android device grants permission for notification by default
next: /react-native/docs/android/progress-indicators
previous: /react-native/docs/android/interaction
---

# Understanding permission

On Android, notification permission is granted by default. However, a user may revoke notification permissions later through various means. In addition, a user may manage or revoke this notification permission at any of three levels; application-wide, channel groups and channels. 

# Checking permission

For each level, you can check whether notifcation permission is enabled on as-need basis or with event listeners.

## Application wide

To check whether the user has enabled application-wide notifications, call [`getNotificationSettings`](/reference/getnotificationsettings). The `authorizationStatus` attributes will returns `DENIED` if user has denied the permission, and `AUTHORIZED` if it's granted.


```js
import notifee from '@notifee/react-native';

async function checkNotificationPermission() {
  const settings = await notifee.getNotificationSettings();

  if (settings.authorizationStatus == AuthorizationStatus.AUTHORIZED) {
    console.log('Notification permission has been authorized');
  } else if (settings.authorizationStatus == AuthorizationStatus.DENIED) {
    console.log('Notification permission has been denied');
  }
}
```

In some cases where notification is disabled, you might want to give an option for user to open notification settings to enable it. After you have setup your UI, you can use Notifee's [`openNotificationSettings`](/reference/opennotificationsettings).

## Channels & Channels Groups

To check whether the user has enabled notification for specific channels or channel groups, call either [`getChannel`](/reference/getchannel) or [`getChannelGroups`](/reference/getchannelgroups). Both of these functions will return a respective object with `blocked` attributes in it.

```js
import notifee from '@notifee/react-native';

async function checkChannelPermission() {
  const channel = await notifee.getChannel();

  if (channel.blocked) {
    console.log('Channel notification has been authorized');
  } else {
    console.log('Channel notification has been denied');
  }
}
```

For more understanding on channels, you can refer to [Channels & Groups](react-native/docs/android/channels).

## Permission events

For listening to permission change events, refer to [Listening to channel events](react-native/docs/android/channels#listening-to-channel-events).