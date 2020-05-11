import { expect } from 'chai';
import { TestScope } from 'cavy';
import notifee, { AndroidChannel, EventType, Event, Importance } from '@notifee/react-native';

export function NotificationSpec(spec: TestScope): void {
  spec.beforeEach(async () => {
    const channels: AndroidChannel[] = [
      {
        name: 'High Importance',
        id: 'high',
        importance: Importance.HIGH,
      },
      {
        name: '🐴 Sound',
        id: 'custom_sound',
        importance: Importance.HIGH,
        sound: 'horse.mp3',
      },
      {
        name: 'Default Importance',
        id: 'default',
        importance: Importance.DEFAULT,
      },
      {
        name: 'Low Importance',
        id: 'low',
        importance: Importance.LOW,
      },
      {
        name: 'Min Importance',
        id: 'min',
        importance: Importance.MIN,
      },
    ];

    await notifee.createChannels(channels);
    // TODO doesn't work in tests as blocks with a UI prompt
    // await notifee.requestPermission();
  });

  spec.describe('displayNotification', function() {
    spec.it('displays a notification', async function() {
      return new Promise(async resolve => {
        const unsubscribe = notifee.onForegroundEvent((event: Event) => {
          if (event.type == EventType.DELIVERED) {
            expect(event.detail.notification).to.not.be.undefined;
            console.warn(event.detail);
            if (event.detail.notification) {
              expect(event.detail.notification.title).to.equal('Hello');
              expect(event.detail.notification.body).to.equal('World');
            }
            unsubscribe();
            resolve();
          }
        });

        return notifee.displayNotification({
          title: 'Hello',
          body: 'World',
          ios: {
            importance: Importance.HIGH,
          },
          android: {
            channelId: 'high',
          },
        });
      });
    });
  });
}
