export default [
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
        channelId: 'foo',
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
];
