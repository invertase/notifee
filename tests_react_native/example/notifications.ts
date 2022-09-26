import {
  AndroidStyle,
  Notification,
  AndroidLaunchActivityFlag,
  AndroidCategory,
  AndroidImportance,
} from '@notifee/react-native';
import { AndroidFlags } from '@notifee/react-native/src';

export const notifications: { key: string; notification: Notification | Notification[] }[] = [
  {
    key: 'Empty',
    notification: {
      android: {
        channelId: 'custom_sound',
        pressAction: {
          id: 'default',
        },
      },
      ios: {
        sound: 'media/kick.wav',
      },
    },
  },
  {
    key: 'FullScreenAction',
    notification: {
      title: 'Full-screen',
      android: {
        asForegroundService: false,
        channelId: 'high',
        autoCancel: false,
        category: AndroidCategory.CALL,
        importance: AndroidImportance.HIGH,
        fullScreenAction: {
          id: 'default',
          mainComponent: 'full_screen',
        },
      },
    },
  },
  {
    key: 'Basic',
    notification: {
      id: 'basic',
      title: '<p style="color: #4caf50;"><b>Styled HTMLTitle</span></p></b></p> &#128576;',
      android: {
        channelId: 'high',
        pressAction: {
          id: 'default',
        },
      },
      ios: {},
    },
  },
  {
    key: 'Loop Sound',
    notification: {
      id: 'loopSound',
      title: 'loop sound',
      android: {
        channelId: 'custom_sound',
        pressAction: {
          id: 'default',
        },
        ongoing: true,
        loopSound: true,
      },
      ios: {},
    },
  },
  {
    key: 'Android Flags',
    notification: {
      id: 'customSound',
      title: 'custom sound',
      android: {
        channelId: 'default',
        pressAction: {
          id: 'default',
        },
        sound: 'hollow',
        ongoing: true,
        flags: [AndroidFlags.FLAG_INSISTENT],
      },
      ios: {},
    },
  },
  {
    key: 'Tag',
    notification: {
      id: 'tag',
      title: 'With tag',
      android: {
        channelId: 'high',
        pressAction: {
          id: 'default',
        },
        tag: 'example-tag',
      },
      ios: {},
    },
  },
  {
    key: 'Subtitle',
    notification: {
      title: 'Title',
      body: 'Body of the notification',
      subtitle: 'Subtitle text',
      android: {
        channelId: 'foo',
      },
      ios: {
        sound: 'Beacon',
      },
    },
  },

  {
    key: 'Color',
    notification: {
      title: 'Color',
      body: 'Only the small icon should change color',
      android: {
        smallIcon: 'ic_small_icon',
        color: '#553c9a',
        channelId: 'foo',
      },
      ios: {
        critical: true,
        criticalVolume: 1.0,
        sound: 'Beacon',
      },
    },
  },

  {
    key: 'Actions (event only)',
    notification: {
      title: 'Actions',
      body: 'Notification with actions',
      ios: {
        categoryId: 'actions',
      },
      android: {
        pressAction: {
          id: 'default',
        },
        autoCancel: true,
        color: '#9c27b0',
        onlyAlertOnce: true,
        // inputHistory: ['Hey', 'Food tonight?'],
        actions: [
          {
            title: 'Reply, Open & Cancel',
            icon: 'https://invertase.io/icons/icon-48x48.png',
            pressAction: {
              id: 'default',
            },
            input: true,
            // input: {
            //   choices: ['Hey'],
            //   allowFreeFormInput: true,
            //   editableChoices: true,
            //   placeholder: 'Reply...',
            // },
          },
          {
            title: 'Open & Cancel',
            icon: 'https://invertase.io/icons/icon-48x48.png',
            pressAction: {
              id: 'second_action',
              mainComponent: 'test_component',
            },
          },
          {
            title: 'Nothing',
            icon: 'https://invertase.io/icons/icon-48x48.png',
            pressAction: {
              id: 'third_action',
            },
          },
        ],
        channelId: 'high',
      },
    },
  },
  {
    key: 'Service',
    notification: {
      title: 'Background Task',
      body: 'Doing some work...',
      android: {
        autoCancel: true,
        color: '#9c27b0',
        onlyAlertOnce: true,
        asForegroundService: true,
        progress: {
          max: 120,
          current: 0,
        },
        actions: [
          {
            title: 'Stop',
            icon: 'https://invertase.io/icons/icon-48x48.png',
            pressAction: {
              id: 'stop',
            },
          },
        ],
        channelId: 'foo',
      },
      ios: {
        categoryId: 'stop',
      },
    },
  },
  {
    key: 'Big Picture Style',
    notification: {
      title: 'Big Picture Style',
      body: 'Expand for a cat',
      android: {
        channelId: 'foo',
        style: {
          type: AndroidStyle.BIGPICTURE,
          picture: 'https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png',
        },
      },
      ios: {
        attachments: [
          {
            id: 'image',
            url: 'media/cat.png',
            thumbnailHidden: true,
            thumbnailClippingRect: {
              x: 0.1,
              y: 0.1,
              width: 0.1,
              height: 0.1,
            },
          },
        ],
      },
    },
  },
  {
    key: 'iOS Video',
    notification: {
      title: 'iOS Attachments: Video',
      body: 'Expand to see a video',
      ios: {
        attachments: [
          {
            id: 'video',
            url: 'media/dog.mp4',
            thumbnailTime: 1,
          },
        ],
      },
    },
  },
  {
    key: 'iOS GIF',
    notification: {
      title: 'iOS Attachments: GIF',
      body: 'Expand to see a gif',
      ios: {
        attachments: [
          {
            id: 'gif',
            url: 'media/back.gif',
            thumbnailTime: 6,
          },
        ],
      },
    },
  },
  {
    key: 'Android Launch Activity Flags',
    notification: {
      title: 'Testing SINGLE_TOP launch.',
      body: 'Expand for a cat!',
      android: {
        channelId: 'foo',
        pressAction: {
          id: 'default',
          launchActivity: 'default',
          launchActivityFlags: [AndroidLaunchActivityFlag.SINGLE_TOP],
        },
        lights: ['#ffffff', 25, 50],
        style: {
          type: AndroidStyle.BIGPICTURE,
          picture: 'https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png',
        },
      },
    },
  },
  {
    key: 'Chronometer', // See git issue #53
    notification: {
      android: {
        color: '#9c27b0',
        channelId: 'foo',
        showChronometer: true,
        chronometerDirection: 'down',
        timestamp: Date.now() + 200000,
      },
    },
  },
  {
    key: 'Dismiss', // See git issue #62
    notification: [
      {
        id: 'personal',
        title: 'Personal Chat',
        android: {
          channelId: 'default',
          groupSummary: true,
          groupId: 'Personal',
        },
      },
      {
        title: 'Dismiss',
        body: 'Notification with dismiss',
        ios: {
          categoryId: 'dismiss',
        },
        android: {
          autoCancel: true,
          color: '#9c27b0',
          onlyAlertOnce: true,
          actions: [
            {
              title: 'Dismiss',
              icon: 'https://invertase.io/icons/icon-48x48.png',
              pressAction: {
                id: 'dismiss',
              },
            },
          ],
          channelId: 'default',
          groupId: 'Personal',
        },
      },
    ],
  },
];
