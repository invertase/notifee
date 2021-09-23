import {
  AndroidCategory,
  AndroidImportance,
  AndroidStyle,
  AndroidVisibility,
  Notification,
} from '@notifee/react-native';

type NotificationItems = {
  basic: Notification;
  image: Notification;
  quickActions: Notification;
  fullScreen: Notification;
};

export const notifications: NotificationItems = {
  basic: {
    title: 'Basic',
    body: 'notification',
    android: {
      channelId: 'default',
      pressAction: {
        id: 'default',
      },
    },
    ios: {
      sound: 'default',
    },
  },
  image: {
    title: 'Image',
    body: 'notification',
    android: {
      channelId: 'default',
      pressAction: {
        id: 'default',
      },
      style: {
        type: AndroidStyle.BIGPICTURE,
        picture:
          'https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png',
      },
    },
    ios: {
      sound: 'default',
      attachments: [
        {
          url: 'https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png',
        },
      ],
    },
  },
  quickActions: {
    title: 'Quick Actions',
    body: 'notification',
    id: 'quickAction',
    android: {
      channelId: 'default',
      pressAction: {
        id: 'default',
      },
      actions: [
        {
          title: 'Reply, Open & Cancel',
          pressAction: {
            id: 'first_action_reply',
          },
          // input: {
          //   choices: ['Yes', 'No', 'Maybe'],
          //   placeholder: 'Reply to Sarah...',
          // },
          input: {},
        },
        {
          title: 'Nothing',
          pressAction: {
            id: 'second_action_nothing',
          },
        },
      ],
    },
    ios: {
      sound: 'default',
      categoryId: 'quickActions',
    },
  },
  fullScreen: {
    title: 'Full-screen',
    body: 'notification',
    android: {
      channelId: 'fullscreen',
      // Recommended to set a category
      category: AndroidCategory.CALL,
      // Recommended to set importance to high
      importance: AndroidImportance.HIGH,
      visibility: AndroidVisibility.PUBLIC,
      sound: 'default',
      fullScreenAction: {
        id: 'default',
        // mainComponent: 'full-screen-main-component'
        launchActivity: 'com.example.CustomActivity',
      },
    },
    ios: {
      sound: 'default',
    },
  },
};
