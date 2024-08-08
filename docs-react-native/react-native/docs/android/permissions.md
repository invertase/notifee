---
title: Permissions
description: Understanding and Checking Android notification permissions
next: /react-native/docs/android/progress-indicators
previous: /react-native/docs/android/interaction
---

# Understanding permissions

On Android 13+, notifications are blocked by default and you will need to present a runtime permission dialog to the user. You have to target at least SDK 33 to perform a runtime request on Android 13.+. The permission is always granted for prior versions.

A user may revoke the permissions later through various means. In addition, a user may manage or revoke permissions at any of three levels; application-wide, channel groups and channels.

# Requesting permissions

To get started and request notification permission from your users, call the [`requestPermission`](/react-native/reference/requestpermission)
method.

If the user declines the permission, Android prevents the permission dialog from being displayed again. This allows
the users of your application full control of how notifications are handled:

- If the user declines the runtime permission dialog, they must manually allow notifications via the device Settings UI for your application.
- If the user accepts the runtime permission dialog, notifications will be shown using the settings requested (e.g. with or without sound).

The following example shows how to trigger a permission dialog:

```js
import notifee, { AuthorizationStatus } from '@notifee/react-native';

async function requestUserPermission() {
  const settings = await notifee.requestPermission();

  if (settings.authorizationStatus >= AuthorizationStatus.AUTHORIZED) {
    console.log('Permission settings:', settings);
  } else {
    console.log('User declined permissions');
  }
}
```

# Checking permissions

For each level, you can check whether notifications are permitted through a direct Notifee API call, or listen for changes with event listeners.

## Application wide

To check whether the user has enabled notifications for your app, call [`getNotificationSettings`](/react-native/reference/getnotificationsettings). The `authorizationStatus` attributes will returns `DENIED` if user has denied the permission, and `AUTHORIZED` if it's granted.

```js
import notifee, {AuthorizationStatus} from '@notifee/react-native';

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
