import notifee, { AndroidRepeatInterval, RemoteNotification } from '@notifee/react-native';

console.log(notifee.AndroidColor.AQUA);

notifee
  .scheduleNotification(
    {
      title: 'foo',
      body: 'bar',
    },
    {
      repeatInterval: AndroidRepeatInterval.DAY,
      fireDate: Date.now(),
    },
  )
  .then(value => value);

async function myNotificationBuilder(notification: RemoteNotification) {
  console.log(notification.android);
  console.log(notification.ios);
}

myNotificationBuilder({} as RemoteNotification).then();
