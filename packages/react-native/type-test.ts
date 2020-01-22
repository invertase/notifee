import notifee from '@notifee/react-native';
import { Notification } from '@notifee/react-native/types/Notification';

console.log(notifee.AndroidColor.AQUA);

async function myNotificationBuilder(notification: Notification) {
  console.log(notification.android);
  console.log(notification.ios);
}

myNotificationBuilder({} as Notification).then();
