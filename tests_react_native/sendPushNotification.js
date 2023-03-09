var admin = require('firebase-admin');
var serviceAccount = require('./notifee-admin.json');

// TODO: put as env
const TOKEN = 'cITR5TB2QAqJ1FCzTsFTX5:APA91bFc9narvq1sLQ7lsAauIExsHMBjVOek3g7-2wRN7EjqAfMOc3YQQLl8k5LrWCTxcZ4R_z5_4CH6eWS7uq91zMP5QUNV-HAww43typmDyvq1V948HIk-8jk-ugFqrkSRXrxkJtK-';

var payload = {
  android: {
    priority: 'high',
    notification: {
      tag: 'auniquetag'
    }
  },
  notification: {
    body: 'A notification body',
    title: 'A notification title!',
  },
  data: {
    tag: 'auniquetag'
  },
  apns: {
    payload: {
      aps: {
        sound: 'default',
        category: 'communications',
        mutableContent: 1,
        contentAvailable: 1,
      },
      notifee_options: {
        image: 'https://placeimg.com/640/480/any', // URL to pointing to a remote image
        ios: {
          communicationInfo: {
            conversationId: 'id-abcde',
            sender: {
              id: 'senderId',
              avatar: 'https://placeimg.com/640/480/any',
              displayName: 'Helena Ford',
            },
          },
        },
      },
    },
  },
  token: TOKEN,
};
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});
admin
  .messaging()
  .send(payload)
  .then(response => {
    console.log('Successfully sent message:', response);
  })
  .catch(error => {
    console.log('Error sending message:', error);
  })
  .finally(() => process.exit());
