---
title: Remote Notification support
description:  Utilise the power of Notifee with remote notifications.
next: /
previous: /react-native/docs/ios/permissions
---

It's possible to display a notification with Notifee features from outside the app using remote notifications (a.k.a push notifications) in two ways:
 - using APNs keys
 - using `notifee_options` with our notification service extension helper

It is recommended to only use a notification service extension if you require an image or need to modify the contents of the notification before its displayed.

### Using APNs keys

Notification messages sent through APNs follow the APNs payload format which allows us to be able to specify a category or a custom sound with no extra configuration on the client:

```json
// FCM
{
    notification: {
      title: 'A notification title!',
      body: 'A notification body',
    },
    apns: {
        payload: {
            aps: {
                category: 'post', // A category that's already been created by your app
                sound: 'media/kick.wav', // A local sound file you have inside your app's bundle
                 ... // any other properties
            },
        },
    },
    ...
};
```

### Using `notifee_options`

By adding a custom key `notifee_options` in the message payload, the notification will be modified by Notifee before it is finally displayed to the end user.

To get started, you will need to implement a [Notification Service Extension](https://developer.apple.com/documentation/usernotifications/unnotificationserviceextension) for your iOS app, the following steps will guide you through how to set it up.

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

Before, moving to the next step, run the app and check it builds successfully – make sure you have the correct target selected. 

## Edit the payload
Now everything is setup in your app, you can alter your notification payload in two ways:

1. Update the message payload, sent via your backend
2. In a Notification Service Extension in your app when a device receives a remote message

> Make sure that you will set `mutable-content: 1` (mutableContent if you are using firebase admin sdk) when sending notification otherwise Notification Service Extension will NOT be triggered

> Make sure that you will set `content-available: 1` (contentAvailable if you are using firebase admin sdk) if you want to receive notification when your app is in foreground


### 1. Update the message payload, sent via your backend

```json
// FCM
{
    notification: {
      title: 'A notification title!',
      body: 'A notification body',
    },
    apns: {
        payload: {
            aps: {
                // Payloads coming from Admin SDK should specify params in camelCase. 
                // Payloads from REST API should specify in kebab-case
                // see their respective reference documentation
                'contentAvailable': 1, // Important, to receive `onMessage` event in the foreground when message is incoming
                'mutableContent': 1, // Important, without this the extension won't fire
            },
            notifee_options: {
                image: 'https://placeimg.com/640/480/any', // URL to pointing to a remote image
                ios: {
                    sound: 'media/kick.wav', // A local sound file you have inside your app's bundle
                    foregroundPresentationOptions: {alert: true, badge: true, sound: true, banner: true, list: true},
                    categoryId: 'post', // A category that's already been created by your app
                    attachments: [{url: 'https://placeimg.com/640/480/any', thumbnailHidden: true}] // array of attachments of type `IOSNotificationAttachment`
                    ... // any other api properties for NotificationIOS
                },
                ... // any other api properties for Notification, excluding `id`
            },
        },
    },
    ...
};
```

### 2. In a Notification Service Extension in your app when a device receives a remote message

In your NotifeeNotificationService.m file you should have method `didReceiveNotificationRequest` where we are calling `NotifeeExtensionHelper`. Now you can modify
`bestAttemptContent` before you send it to `NotifeeExtensionHelper`:

```objectivec
- (void)didReceiveNotificationRequest:(UNNotificationRequest *)request withContentHandler:(void (^)(UNNotificationContent * _Nonnull))contentHandler {
    self.contentHandler = contentHandler;
    self.bestAttemptContent = [request.content mutableCopy];
  
    NSMutableDictionary *userInfoDict = [self.bestAttemptContent.userInfo mutableCopy];
    userInfoDict[@"notifee_options"] = [NSMutableDictionary dictionary];
    userInfoDict[@"notifee_options"][@"title"] = @"Modified Title";
  
    self.bestAttemptContent.userInfo = userInfoDict;

    [NotifeeExtensionHelper populateNotificationContent:request
                                withContent: self.bestAttemptContent
                                withContentHandler:contentHandler];
}
```

Please note, the `id` of the notification is the `request.identifier` and cannot be changed. For this reason, the `id` property in `notifee_options` should be excluded.

> if both `attachments` and `image` are present, `attachments` will take precedence over `image`

### Handling Events

Currently, notifee supports the following events for remote notifications:
- `PRESSED`
- `ACTION_PRESSED`
- `DISMISSED`

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

> On iOS, only notifications with a `categoryId` will receive a `DISMISSED` event.