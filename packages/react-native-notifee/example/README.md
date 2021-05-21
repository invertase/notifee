# Notifee example project

## Installation

* `git clone https://github.com/notifee/react-native-notifee.git`
* `cd react-native-notifee/example`
* `yarn`

## Running Android

* Make sure you have no other packagers running!
* `react-native start`
* Start an emulator (e.g., using Android Studio -> Tools -> AVD Manager -> start one)
* `react-native run-android`

## Running iOS

* Make sure you have no other packagers running!
* `react-native start`
* `cd ios && pod install && cd ..`
* `react-native run-ios`

## Troubleshooting

* If things don't work, clean up your build and node_modules folders, `yarn install` and rebuild.

## How to use

The app consists of 3 buttons:
- One to display a notification
- One to create a trigger notification
- One to test out other APIs

<img width="368" alt="Screenshot 2021-05-21 at 17 56 54" src="https://user-images.githubusercontent.com/14185925/119172482-ef08fa80-ba5d-11eb-97b3-52a6f61a005e.png">

### Notifications

You can customise which notification is displayed by choosing from the [example notification payloads](https://github.com/notifee/react-native-notifee/blob/master/example/src/utils/notifications.ts) in `src/utils/notifications` and setting it in [src/content](https://github.com/notifee/react-native-notifee/blob/master/example/src/content.tsx)

```js
// const notification = notifications.basic;
const notification = notifications.image;
```

### Basic

https://user-images.githubusercontent.com/14185925/119176405-faaaf000-ba62-11eb-9d78-22b132396d53.mov

### Image

<img width="910" alt="Screenshot 2021-05-21 at 18 37 04" src="https://user-images.githubusercontent.com/14185925/119176913-8c1a6200-ba63-11eb-9e51-44e316a9fb3c.png">

### Quick Actions

<img width="930" alt="Screenshot 2021-05-21 at 18 50 34" src="https://user-images.githubusercontent.com/14185925/119178307-6ee69300-ba65-11eb-89e7-43ab205b2ea7.png">

### Full-screen action

https://user-images.githubusercontent.com/14185925/119170236-e19e4100-ba5a-11eb-8e10-6f1e804ef8f3.mov

## Triggers

You can customise which trigger is displayed by choosing from the [example trigger payloads](https://github.com/notifee/react-native-notifee/blob/master/example/src/utils/triggers.ts) in `src/utils/triggers` and setting it in [src/content](https://github.com/notifee/react-native-notifee/blob/9625612a5740744d64ac1b2fba4e2d8a8fbe04e5/example/src/content.tsx#L18)

```js
// const triggerType = triggers.timestamp;
const triggerType = triggers.interval;
```

### Timestamp

The timestamp trigger is set to trigger 5 seconds from now, to customise the timestamp date you can update the function `getTimestamp` in [src/utils/triggers](https://github.com/notifee/react-native-notifee/blob/9625612a5740744d64ac1b2fba4e2d8a8fbe04e5/example/src/utils/triggers.ts#L14):

```js
/* Timestamp Date */
const getTimestamp = () => {
  const timestampDate = new Date(Date.now());
  timestampDate.setSeconds(timestampDate.getSeconds() + 5);
  return timestampDate.getTime();
};

/* Trigger */
const trigger = {
    timestamp: timestampDate,
    type: TriggerType.TIMESTAMP,
}
```

### Interval

The interval trigger is set to trigger every 60 seconds, to customise the interval you can update the code in [src/utils/triggers](https://github.com/notifee/react-native-notifee/blob/9625612a5740744d64ac1b2fba4e2d8a8fbe04e5/example/src/utils/triggers.ts#L28):
```js
/* Interval */
const interval = 60;

/* Trigger */
const trigger = {
    timeUnit: TimeUnit.SECONDS,
    type: TriggerType.INTERVAL,
    interval: interval,
  }
```

## Other APIs

You can customise which API is called when pressing the button labelled 'Test Notifee API Action' by editing the code in [src/content](https://github.com/notifee/react-native-notifee/blob/9625612a5740744d64ac1b2fba4e2d8a8fbe04e5/example/src/content.tsx#L63)

```js
const onAPIPress = async () => {
  /* Change the API function to test */
  const result = await notifee.cancelAllNotifications();

  console.log('onAPIPress -> ', result != null ? result : 'API Call Success');
};
```

## Requesting Permissions

On iOS, it's required to request permissions to be able to display a notification, this is called in [App.tsx](https://github.com/notifee/react-native-notifee/blob/9625612a5740744d64ac1b2fba4e2d8a8fbe04e5/example/App.tsx):

```js
  useEffect(() => {
    (async () => {
      await requestUserPermission();
    })();
  }, []);
```

## Subscribing to events

In [App.tsx](https://github.com/notifee/react-native-notifee/blob/9625612a5740744d64ac1b2fba4e2d8a8fbe04e5/example/App.tsx), `onBackgroundEvent` and `onForegroundEvent` are registered to receive events:

```js
notifee.onBackgroundEvent(async ({type, detail}) => {
  // ...
})

function App() {

  useEffect(() => {
    // ...
    return notifee.onForegroundEvent(async ({type, detail}) => {
    // ...
    }))
  }, [)
}
```
