---
title: Remote Notification support
description:  Utilise the power of Notifee with remote notifications.
next: /
previous: /react-native/docs/ios/permissions
---

> It is recommended to only use a notification service extension if you require an image or need to modify the contents of the notification before its displayed.

It's possible to display a notification with Notifee features from outside the app using remote notifications (a.k.a push notifications) by using `notifee_options` in APNs keys with our notification service extension helper.

Adding a custom key `notifee_options` in the message payload enables Notifee to modify the notification before it is finally displayed to the end user. You can also change it as you please during this time.

> ⭐️ In order to bring the iOS experience closer to the Android one, you can still send **data-only/local/background** notifications for Android while using **remote/alert** notifications for iOS [by configuring the right payload.](#configure-the-payload)

To get started, you will need to implement a [Notification Service Extension](https://developer.apple.com/documentation/usernotifications/unnotificationserviceextension) for your iOS app, the following steps will guide you through how to set it up.

### Expo

You can jump straght to [configuring the payload](#configure-the-payload) if you use Expo with one of the community plugins: 
- https://github.com/LunatiqueCoder/expo-notifee-plugin
- https://github.com/evennit/notifee-expo-plugin

## Add the notification service extension

* From Xcode top menu go to: File > New > Target...
* A modal will present a list of possible targets, scroll down or use the filter to select Notification Service Extension. Press Next.
* Add a product name (use `NotifeeNotificationService` to follow along) and click Finish
* Make sure that the **deployment target** of your newly added extension (e.g. `NotifeeNotificationService`) matches the **deployment target** of your app. You can check it by selecting you app's target -> `Build Settings` -> `Deployment`


<!-- <Vimeo id="remote-notification-support-1" caption="Step 1 - Add Your Notification Service Extension" /> -->

## Add target to the Podfile

Ensure that your new extension has access to `NotifeeExtensionHelper` by adding it in the Podfile:
* From the Navigator open the Podfile: Pods > Podfile
* Scroll down to the bottom of the file and add

```ruby
$NotifeeExtension = true

target 'NotifeeNotificationService' do
  pod 'RNNotifeeCore', :path => '../node_modules/@notifee/react-native/RNNotifeeCore.podspec'
end

```
* Assuming you are using `react-native-firebase`, you'll also have to add `use_frameworks!` inside the target above as [you did when you followed their docs](https://rnfirebase.io/#altering-cocoapods-to-use-frameworks). 
* Install or update your pods using pod install from the ios folder

`pod install --repo-update`

<!-- <Vimeo id="remote-notification-support-2" caption="Step 2 - Update Your Pods" /> -->

## Use the extension helper

At this point everything should still be running normally. This is the final step which is invoking the extension helper.

* From the navigator select your extension
* Open the NotificationService.m file
* At the top of the file import NotifeeExtensionHelper.h right after the NotificationService.h as shown below
```objectivec
#import "NotificationService.h"
+ #import "NotifeeExtensionHelper.h"
```

* then replace everything from line 25 to 28 with the extension helper
```objectivec
- // Modify the notification content here...
- self.bestAttemptContent.title = [NSString stringWithFormat:@"%@ [modified]", self.bestAttemptContent.title];
    
- self.contentHandler(self.bestAttemptContent);
+ [NotifeeExtensionHelper populateNotificationContent:request
                                withContent: self.bestAttemptContent
                                withContentHandler:contentHandler];
```
<!-- <Vimeo id="remote-notification-support-3" caption="Step 3 - Edit NotificationService.m" /> -->

## Mutate the content with Notifee:
> 🔗 [Apple Docs: Modifying content in newly delivered notifications](https://developer.apple.com/documentation/usernotifications/modifying-content-in-newly-delivered-notifications#Implement-your-extensions-handler-methods)

#### Objective C:

In your `NotifeeNotificationService.m` file you should have method `didReceiveNotificationRequest` where we are calling `NotifeeExtensionHelper`. Now you can modify
`bestAttemptContent` before you send it to `NotifeeExtensionHelper`:

```objectivec
- (void)didReceiveNotificationRequest:(UNNotificationRequest *)request withContentHandler:(void (^)(UNNotificationContent * _Nonnull))contentHandler {
    self.contentHandler = contentHandler;
    self.bestAttemptContent = [request.content mutableCopy];

     // You can also modify the payload as you please here.
     // You have around 30 seconds

     // Notifee will mutate the notification according to notifee_options
    [NotifeeExtensionHelper populateNotificationContent:request
                                withContent: self.bestAttemptContent
                                withContentHandler:contentHandler];
}
```

#### Swift: 

In your `NotifeeNotificationService.swift` file, use the `didReceive(_:withContentHandler:)` method to enable the `NotifeeExtensionHelper`.
```swift
override func didReceive(_ request: UNNotificationRequest,
                         withContentHandler contentHandler: @escaping (UNNotificationContent) -> Void) {
    self.contentHandler = contentHandler
    self.bestAttempt = (request.content.mutableCopy() as? UNMutableNotificationContent)

    // Here you can also modify the content of the notification as you please here.
    // You have around 30 seconds


    // Notifee will mutate the notification according to notifee_options
    NotifeeExtensionHelper.populateNotificationContent(request,
                            with: self.bestAttempt!,
                            withContentHandler: contentHandler)
}
```

<br> 

Before moving to the next step, run the app and check it builds successfully – make sure you have the correct target selected. 

## Configure the payload
> Make sure that you will set `mutable-content: 1` (mutableContent if you are using firebase admin sdk) when sending notification otherwise Notification Service Extension will NOT be triggered
>
> 🔗 [**Apple docs:** Configure-the-payload-for-thenotification](https://developer.apple.com/documentation/usernotifications/modifying-content-in-newly-delivered-notifications#Configure-the-payload-for-thenotification)

### Here's an example of a payload sent from the backend with Firebase Admin Node.js SDK

```ts

import type {Notification} from '@notifee/react-native/src/types/Notification';
import {AndroidImportance} from '@notifee/react-native/src/types/NotificationAndroid';
import {MulticastMessage} from 'firebase-admin/lib/messaging/messaging-api';
import admin from '../src/firebase-admin';

/**
 * @link https://notifee.app/react-native/reference/notification
 */
const notifeeOptions: Notification = {
  title: 'Title',
  subtitle: 'Subtitle',
  body: 'Main body content of the notification',
  image: 'https://placeimg.com/640/480/any', // URL to pointing to a remote image
  android: {
    channelId: 'default',
    importance: AndroidImportance.HIGH,
    lightUpScreen: true,
    pressAction: {
      id: 'default',
    },
    sound: 'default',
  },
  ios: {
    sound: 'media/kick.wav', // A local sound file you have inside your app's bundle
    categoryId: 'post', // A category that's already been created by your app
    attachments: [{url: 'https://placeimg.com/640/480/any', thumbnailHidden: true}] // array of attachments of type `IOSNotificationAttachment`
    // 🚧 Adding `foregroundPresentationOptions` controls how to
    // 👇 behave when app is UP AND RUNNING, not terminated, AND not in background!
    foregroundPresentationOptions: {
      badge: true,
      banner: true,
      list: true,
      sound: true,
    },
  },
};


/** 
 * @description Firebase Message
 * @link https://firebase.google.com/docs/reference/admin/node/firebase-admin.messaging.basemessage.md#basemessage_interface
 */
const message: MulticastMessage = {
  // ✅ We can continue using local/data-only notification for Android
  // 👍 while triggering iOS remote notifications from `apns`
  data: {notifee_options: JSON.stringify(notifeeOptions)},
  tokens: [],
  android: {
    priority: 'high', // Needed to trigger data-only notifications
  },
  apns: {
    payload: {
      notifee_options: notifeeOptions,
      aps: {
        // Payloads coming from Admin SDK should specify params in camelCase. 
        // Payloads from REST API should specify in kebab-case
        // see their respective reference documentation
        alert: {
          // 🚧 This is needed to trigger an alert/remote notification only for iOS
          // 👍 but Android will continue using data-only notifications
          title: 'ANY_DUMMY_STRING', // Or notifeeOptions.title :)
        },
        'mutableContent': 1, // Important, without this the extension won't fire
      },
    },
  },
};

try {
  admin.messaging().sendEachForMulticast(message)
  res.status(200).end();
} catch (e) {
  res.status(400).end();
}
```

Please note, the `id` of the notification is the `request.identifier` and cannot be changed. For this reason, the `id` property in `notifee_options` should be excluded.

> If both `attachments` and `image` are present, `attachments` will take precedence over `image`

### Handling Events

Currently, notifee supports the following events for remote notifications:
- `PRESSED`
- `ACTION_PRESSED`
- `DISMISSED`

> On iOS, only notifications with a `categoryId` will receive a `DISMISSED` event.

To know identify when an interaction is from a remote notification, we can check if `notification.remote` is populated:

```jsx
import { useEffect } from 'react';
import notifee, { EventType } from '@notifee/react-native';
function App() {
  // Subscribe to events
  useEffect(() => {
    return notifee.onForegroundEvent(({ type, detail }) => {
      console.log('Remote notification info: ', detail.notification?.remote)
      switch (type) {
        case EventType.DISMISSED:
          console.log('User dismissed notification', detail.notification);
          break;
        case EventType.PRESS:
          console.log('User pressed notification', detail.notification);
          break;
      }
    });
  }, []);
}
```
