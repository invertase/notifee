import notifee from '@notifee/react-native';
import {
  NotificationRepeatInterval,
  RemoteNotification,
} from '@notifee/react-native/types/Notification';

console.log(notifee.AndroidColor.AQUA);

notifee
  .scheduleNotification(
    {
      title: 'foo',
      body: 'bar',
    },
    {
      repeatInterval: NotificationRepeatInterval.DAY,
      fireDate: Date.now(),
    },
  )
  .then(value => value);

async function myNotificationBuilder(notification: RemoteNotification) {
  console.log(notification.android);
  console.log(notification.ios);
}

myNotificationBuilder({} as RemoteNotification).then();
