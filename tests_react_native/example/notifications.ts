import Notifee from '@notifee/react-native';
import { Notification } from '@notifee/react-native/lib/types/Notification';

export const notifications: { key: string; notification: Notification }[] = [
  {
    key: 'Empty',
    notification: {
      android: {
        channelId: 'foo',
      },
    },
  },

  {
    key: 'Basic',
    notification: {
      title: 'Title',
      body: 'Body of the notification',
      android: {
        // ongoing: true,
        channelId: 'foo',

        // style: {
        //   type: 1,
        //   text: 'Helllllllo World!!!!!!!!',
        // },

        // onPressAction: {
        //   id: 'testing',
        //   reactComponent: 'test_component',
        // },
      },
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
    },
  },

  {
    key: 'Color',
    notification: {
      title: 'Color',
      body: 'Only the small icon should change color',
      android: {
        color: '#9c27b0',
        channelId: 'foo',
      },
    },
  },

  {
    key: 'Actions (event only)',
    notification: {
      title: 'Actions',
      body: 'Notification with actions',
      android: {
        autoCancel: true,
        color: '#9c27b0',
        onlyAlertOnce: true,
        // inputHistory: ['Hey', 'Food tonight?'],
        actions: [
          {
            title: 'Reply, Open & Cancel',
            icon: 'https://invertase.io/icons/icon-48x48.png',
            onPressAction: {
              id: 'first_action',
              // reactComponent: 'test_component',
            },
            input: {},
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
            onPressAction: {
              id: 'second_action',
              reactComponent: 'test_component',
            },
            // input: {
            //   choices: ['You'],
            //   allowFreeFormInput: true,
            //   placeholder: 'Second Input',
            // },
          },
          {
            title: 'Nothing',
            icon: 'https://invertase.io/icons/icon-48x48.png',
            onPressAction: {
              id: 'third_action',
            },
          },
        ],
        channelId: 'foo',
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
          type: Notifee.AndroidStyle.BIGPICTURE,
          picture: 'https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png',
        },
      },
    },
  },
];
