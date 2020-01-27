import notifee, { EventType } from '@notifee/react-native';

notifee.onEvent(async event => {
  const { type, headless, detail } = event;

  let eventTypeString;

  switch (type) {
    case EventType.UNKNOWN:
      eventTypeString = 'UNKNOWN';
      console.log('Notification Id', detail.notification?.id);
      break;
    case EventType.DISMISSED:
      eventTypeString = 'DISMISSED';
      console.log('Notification Id', detail.notification?.id);
      break;
    case EventType.PRESS:
      eventTypeString = 'PRESS';
      console.log('Action ID', detail.pressAction?.id);
      break;
    case EventType.ACTION_PRESS:
      eventTypeString = 'ACTION_PRESS';
      console.log('Action ID', detail.pressAction?.id);
      break;
    case EventType.DELIVERED:
      eventTypeString = 'DELIVERED';
      console.log('Notification Id', detail.notification?.id);
      break;
    case EventType.APP_BLOCKED:
      eventTypeString = 'APP_BLOCKED';
      console.log('Blocked', detail.blocked);
      break;
    case EventType.CHANNEL_BLOCKED:
      eventTypeString = 'CHANNEL_BLOCKED';
      console.log('Channel', detail.channel);
      break;
    case EventType.CHANNEL_GROUP_BLOCKED:
      eventTypeString = 'CHANNEL_GROUP_BLOCKED';
      console.log('Channel Group', detail.channelGroup);
      break;
    case EventType.SCHEDULED:
      eventTypeString = 'SCHEDULED';
      break;
    default:
      eventTypeString = 'UNHANDLED_NATIVE_EVENT';
  }

  if (headless) {
    console.warn(`Received a ${eventTypeString} event in headless mode.`);
  } else {
    console.warn(`Received a ${eventTypeString} event in JS mode.`);
  }
});
