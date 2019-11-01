import notifee, { RemoteNotification } from '@notifee/react-native';

console.log(notifee.AndroidColor.AQUA);

notifee
  .displayNotification({
    title: 'foo',
    body: 'bar',
  })
  .then(value => value);

async function myNotificationBuilder(notification: RemoteNotification) {
  console.log(notification.android);
  console.log(notification.ios);
}

myNotificationBuilder({} as RemoteNotification).then();
