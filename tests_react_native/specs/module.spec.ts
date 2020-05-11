import { TestScope } from 'cavy';

import notifee, { AndroidChannel, Importance } from '@notifee/react-native';

export function ModuleSpec(spec: TestScope): void {
  spec.beforeEach(async () => {
    const channels: AndroidChannel[] = [
      {
        name: 'High Importance',
        id: 'high',
        importance: Importance.HIGH,
        // sound: 'hollow',
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
    await Promise.all(channels.map($ => notifee.createChannel($)));
  });

  spec.describe(`displayNotification`, function() {
    spec.it(`it`, async function() {
      await notifee.displayNotification(
        {
          title: 'Scheduled lol ' + Math.floor(Date.now() / 1000),
          body: 'World',
          android: {
            channelId: 'high',
          },
        },
        {
          timestamp: Math.floor(Date.now() / 1000) + 60,
        },
      );
      await new Promise(resolve => setTimeout(resolve, 120000));
    });
  });
}
