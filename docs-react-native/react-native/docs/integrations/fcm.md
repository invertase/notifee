---
title: Firebase Cloud Messaging (FCM)
description: Integrate Firebase Cloud Messaging and display notifications with Notifee.
next: /react-native/docs/integrations/onesignal
previous: /react-native/docs/triggers
---

[Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging) (FCM) is a messaging solution
that lets you reliably send messages at no cost to both Android & iOS devices.

Using FCM, you can send data payloads (via a message) to a device for a specific application. Each message can transfer
a payload of up to 4KB to a client. FCM also provides features to build basic notifications from the Firebase console,
versatile message targeting, client-to-client communication and web notifications support.

# React Native Firebase

[React Native Firebase](https://rnfirebase.io/) is the officially recommended library for
integrating React Native with [Firebase](https://firebase.google.com/). Using React Native Firebase, we can easily
integrate with FCM and display notifications with Notifee.

## Installation & Setup

To get started, you must have the following prerequisites setup and installed:

- An active [Firebase](https://console.firebase.google.com/) project.
- The [`@react-native-firebase/app`](https://www.npmjs.com/package/@react-native-firebase/app) package installed & setup.
- The [`@react-native-firebase/messaging`](https://www.npmjs.com/package/@react-native-firebase/messaging) package installed & setup.

For more information on how to get started with React Native Firebase, check out the [quick start](https://rnfirebase.io)
guide for integrating with your Firebase project, and the steps required to install the [Messaging package](https://rnfirebase.io/messaging/usage).

## Subscribing to messages

React Native Firebase provides two methods for subscribing to messages and both receive a remote message payload:

- `onMessage`: Handles FCM messages when the application is alive/in the foreground.
- `setBackgroundMessageHandler`: Handles FCM messages when the app is in a killed state.

Both expect an async message handling function (or a function that returns a Promise) as an argument.

For the purpose of this guide, we'll set both subscribers up to trigger
the same function when called so we don't have to worry about the application's state:

```js
import messaging from '@react-native-firebase/messaging';

// Note that an async function or a function that returns a Promise 
// is required for both subscribers.
async function onMessageReceived(message) {
  // Do something
}

messaging().onMessage(onMessageReceived);
messaging().setBackgroundMessageHandler(onMessageReceived);
```

## Sending messages

In most cases, messages will be sent via the FCM service using an external service running alongside your application
(e.g. an API). To send a message, we need to [create a messaging token](#Create-messaging-token) that is unique to each device . Once acquired,
it then needs to be saved to a datastore (e.g. an external database). When the time comes to send a message, the
token is read from the datastore and a data payload is sent via FCM to the specific device with the token assigned.

A messaging token is unique per device and application.

### Create token

FCM provides a handy method for retrieving our device-specific token. Once the token is retrieved, it is advised that you store the token
in your backend database of choice as you will need to [use the token](#using-token) to send messages via FCM to that specific device.

> It is recommended you fetch and update the token each time the application boots in-case it has changed.

Within a bootstrap function inside your application, fetch the token & save it against the current user in your datastore.
The example below saves the token via an example API call:

```js
import messaging from '@react-native-firebase/messaging';

async function onAppBootstrap() {
  // Register the device with FCM
  await messaging().registerDeviceForRemoteMessages();

  // Get the token
  const token = await messaging().getToken();

  // Save the token
  await postToApi('/users/1234/tokens', { token });
}
```

If required, you can also listen for [token refreshes](https://rnfirebase.io/reference/messaging#onTokenRefresh)
whilst the application is open in-case a token changes.

### Using tokens

Once we have the tokens stored, we can use them to send messages via the FCM service. This guide covers sending messages
via the Node.JS [`firebase-admin`](https://www.npmjs.com/package/firebase-admin) SDK, however it is also possible to use
[Java](https://firebase.google.com/docs/reference/admin/java/reference/com/google/firebase/messaging/package-summary),
[Python](https://firebase.google.com/docs/reference/admin/python/firebase_admin.messaging),
[C#](https://firebase.google.com/docs/reference/admin/dotnet/namespace/firebase-admin/messaging) or the
[Go](https://godoc.org/firebase.google.com/go/messaging) SDKs, or even directly via a
[HTTP](https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages) request.

> Firebase provides [full examples](https://firebase.google.com/docs/cloud-messaging/send-message) for each SDK in their documentation.

Within a Node environment using the `firebase-admin` SDK, it is possible to send a message using the tokens from our datastore, for
example:

```js
// Node.js
var admin = require('firebase-admin');

// Initialize Firebase
admin.initializeApp({
  credential: admin.credential.applicationDefault(),
  databaseURL: 'https://<DATABASE_NAME>.firebaseio.com',
});

async function sendMessage() {
  // Fetch the tokens from an external datastore (e.g. database)
  const tokens = await getTokensFromDatastore();

  // Send a message to devices with the registered tokens
  await admin.messaging().sendMulticast({
    tokens, // ['token_1', 'token_2', ...]
    data: { hello: 'world!' },
  });
}

// Send messages to our users
sendMessage();
```

The admin SDK will send a data payload to all devices with the registered tokens. We can now modify our `onMessageReceived`
handler we created earlier to log out the data:

```js
function onMessageReceived(message) {
  console.log(`Hello ${message.data.hello}`); // Hello world!
}
```

## Integrating Notifee

Integrating Notifee with an FCM payload comes down to personal preference. You could send an entire Notification object
with the message, or use it to construct your own message.

Data payloads via the messaging API only accept a string, key/value pairs where the value is always a `string` type. Please bear in mind,
each property in the `data` object sent via FCM must be a string as noted below.

### Server creation

Construct an entire notification object directly on the server:

```js
// Node.JS
await admin.messaging().sendMulticast({
  tokens,
  data: {
    notifee: JSON.stringify({
      body: 'This message was sent via FCM!',
      android: {
        channelId: 'default',
        actions: [
          {
            title: 'Mark as Read',
            pressAction: {
              id: 'read',
            },
          },
        ],
      },
    }),
  },
});
```

```js
// React Native
import notifee from '@notifee/react-native';
import messaging from '@react-native-firebase/messaging';

function onMessageReceived(message) {
  notifee.displayNotification(JSON.parse(message.data.notifee));
}

messaging().onMessage(onMessageReceived);
messaging().setBackgroundMessageHandler(onMessageReceived);
```

### Partial creation

Send a partial data payload and create the rest inside of your application:

```js
// Node.JS
await admin.messaging().sendMulticast({
  tokens,
  data: {
    type: 'order_shipped',
    timestamp: Date.now().toString(), // values must be strings!
  },
});
```

```js
// React Native
import notifee from '@notifee/react-native';
import messaging from '@react-native-firebase/messaging';

function onMessageReceived(message) {
  const { type, timestamp } = message.data;

  if (type === 'order_shipped') {
    notifee.displayNotification({
      title: 'Your order has been shipped',
      body: `Your order was shipped at ${new Date(Number(timestamp)).toString()}!`,
      android: {
        channelId: 'orders',
      },
    });
  }
}

messaging().onMessage(onMessageReceived);
messaging().setBackgroundMessageHandler(onMessageReceived);
```

## Further reading

FCM provides great flexibility when sending messages to devices. If required you can send messages to
[specific devices](https://firebase.google.com/docs/cloud-messaging/send-message#send-messages-to-specific-devices),
or to devices [subscribed to topics](https://firebase.google.com/docs/cloud-messaging/send-message#send-messages-to-topics),
or even via [batched messages](https://firebase.google.com/docs/cloud-messaging/send-message#send-a-batch-of-messages).
