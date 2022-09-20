---
title: Permissions
description: iOS devices require that permission is granted from users before notifications can be displayed on the device.
next: /react-native/docs/ios/remote-notification-support
previous: /react-native/docs/ios/interaction
---

# Understanding permissions

Before diving into requesting notification permissions from your users, it is important to understand how iOS handles permissions.

Notifications cannot be shown to users if the user has not granted your application permission. The overall notification permission of a
single application can be either "granted" or "declined". Upon installing a new application, the default status is "declined".

In order to receive a "granted" status, you must request permission from your user (see below). The user can either accept or decline
request to grant permissions. If granted, notifications will be delivered based on the permission settings which were requested. If the
user declines the request, you cannot re-request permission. Instead they must manually enable notification permissions from the iOS
Settings UI.

# Requesting permissions

To get started and request notification permission from your users, call the [`requestPermission`](/react-native/reference/requestpermission)
method. By default, Notifee requests permissions with sensible default settings, however you can customize these if required (see below).

<Vimeo id="ios-request-permission" caption="iOS Requesting Notification Permission" />

Once a user has selected a permission status, iOS prevents the permission dialog from being displayed again. This allows
the users of your application full control of how notifications are handled:

- If the user declines permission, they must manually allow notifications via the Settings UI for your application.
- If the user has accepted permission, notifications will be shown using the settings requested (e.g. with or without sound).

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

## Permission settings

Although overall notification permission can be granted, the permissions can be further broken down into settings. Settings
are used by the device to control notifications, for example by alerting the user via sound. During the request for permission,
you can provide a custom object of settings if you wish to override the defaults, for example:

```js
await notifee.requestPermission({
  sound: false,
  announcement: true,
  inAppNotificationSettings: false,
  // ... other permission settings
});
```

The full list of permission settings can be seen in the table below along with their default values (view the [`IOSNotificationPermissions`](/react-native/reference/iosnotificationpermissions) reference for more info):

| Permission                  | Default | Description                                                                                                                                                                                             |
| --------------------------- | ------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `alert`                     | `true`  | Sets whether notifications can be displayed to the user on the device.                                                                                                                                  |
| `announcement`              | `false` | If enabled, Siri will read the notification content out when devices are connected to AirPods.                                                                                                          |
| `badge`                     | `true`  | Sets whether a notification dot will appear next to the app icon on the device when there is unread notifications.                                                                                      |
| `carPlay`                   | `true`  | Sets whether notifications will appear when the device is connected to [CarPlay](https://www.apple.com/ios/carplay/).                                                                                   |
| `inAppNotificationSettings` | `false` | If enabled, users will be navigate to your app directly from the Notification Settings UI for your application. See [In-App Notification Settings](#in-app-notification-settings) for more information. |
| `provisional`               | `false` | Sets whether provisional permissions are granted. See [Provisional permission](#provisional-permission) for more information.                                                                           |
| `sound`                     | `true`  | Sets whether a sound will be played when a notification is displayed on the device.                                                                                                                     |

The settings provided will be stored by the device and will be visible in the iOS Settings UI for your application.

If the permission dialog has already been presented to the user and you wish to update the existing permission settings (e.g. enabling sound),
the setting will be silently updated and the `requestPermission` will instantly resolve without showing a dialog.

### Observing settings

In some cases, you may wish to observe what permission/settings have been granted on the device. The `requestPermission`
API used above resolves with an object value containing the current [`NotificationSettings`](/react-native/reference/notificationsettings).
The settings contain information such as whether the user has specific settings enabled/disabled, and whether notification
permission is enabled/disabled for the entire application, for example:

```js
import notifee from '@notifee/react-native';

async function checkApplicationPermission() {
  const settings = await notifee.requestPermission();

  if (settings.authorizationStatus) {
    console.log('User has notification permissions enabled');
  } else {
    console.log('User has notification permissions disabled');
  }

  console.log('iOS settings: ', settings.ios);
}
```

The value of each setting in `settings.ios` returns a [`IOSNotificationSettings`](/react-native/reference/iosnotificationsettings) value, which can be
one of three values:

- `NOT_SUPPORTED`: The device either does not support the type of permission (the iOS API may be too low), or the permission has not been requested.
- `DISABLED`: The setting has manually been disabled by the user in the iOS Settings UI.
- `ENABLED`: The user has accept the permission & it is enabled.

To help improve chances of the the user accepting, it is recommended that permission is requested at a time which makes
sense during the flow of your application (e.g. starting a new chat), where the user would expect to receive notifications.

It is also possible to fetch the current permission settings without requesting permission, by calling the [`getNotificationSettings`](/react-native/reference/getnotificationsettings) API instead:

```js
async function getExistingSettings() {
  const settings = await notifee.getNotificationSettings();

  if (settings) {
    console.log('Current permission settings: ', settings);
  }
}
```

### Provisional permission

Devices on iOS 12+ can take advantage of provisional permissions. This type of permission system allows for notification
permission to be instantly granted without displaying a dialog. The permission allows notifications to be displayed
quietly - meaning they're only visible within the device notification center.

<Vimeo id="ios-provisional-setting" caption="iOS Provisional Notification Setting" />

To enable provisional notifications, provide `true` to the `provisional` property with `requestPermission`:

```js
await notifee.requestPermission({
  provisional: true,
});
```

Users can then choose a permission option via the notification itself, and select whether they can continue to display quietly, display
prominently or not at all.

### In-App Notification Settings

> This feature is still a work in progress and will be available in a later release.

Devices on iOS 12+ can take advantage of the `inAppNotificationSettings` setting. By default, this value is `false` so must be
requested via the `requestPermission` API:

```js
await notifee.requestPermission({
  inAppNotificationSettings: true,
});
```

When enabled, a button is displayed inside of your applications Settings UI. When pressed, your application is opened
and this gives you the chance to navigate to a specific notification settings screen in your UI. This screen could be used
to show users how their current notification permissions will impact the usability of the application.

TODO example video
