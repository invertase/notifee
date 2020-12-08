import { expect } from 'chai';
import { TestScope } from 'cavy';
import notifee, {
  AndroidChannel,
  EventType,
  Event,
  AndroidImportance,
} from '@notifee/react-native';

export function NotificationSpec(spec: TestScope): void {
  spec.beforeEach(async () => {
    const channels: AndroidChannel[] = [
      {
        name: 'High Importance',
        id: 'high',
        importance: AndroidImportance.HIGH,
      },
      {
        name: '🐴 Sound',
        id: 'custom_sound',
        importance: AndroidImportance.HIGH,
        sound: 'horse.mp3',
      },
      {
        name: 'Default Importance',
        id: 'default',
        importance: AndroidImportance.DEFAULT,
      },
      {
        name: 'Low Importance',
        id: 'low',
        importance: AndroidImportance.LOW,
      },
      {
        name: 'Min Importance',
        id: 'min',
        importance: AndroidImportance.MIN,
      },
    ];

    await notifee.createChannels(channels);
    // TODO doesn't work in tests as blocks with a UI prompt
    await notifee.requestPermission();
  });

  spec.describe('displayNotification', function() {
    spec.it('displays a notification', async function() {
      return new Promise(async resolve => {
        const unsubscribe = notifee.onForegroundEvent((event: Event) => {
          if (event.type === EventType.DELIVERED) {
            expect(event.detail.notification).not.equal(undefined);
            if (event.detail.notification) {
              expect(event.detail.notification.title).equals('Hello');
              expect(event.detail.notification.body).equals('World');
            }
            unsubscribe();
            resolve();
          }
        });

        return notifee.displayNotification({
          title: 'Hello',
          body: 'World',
          android: {
            channelId: 'high',
          },
        });
      });
    });
  });
}
