import React from 'react';
import {View, Button, StyleSheet, Text, Platform} from 'react-native';
import notifee, {
  AndroidImportance,
  AndroidVisibility,
} from '@notifee/react-native';

import {notifications} from './utils/notifications';
import {triggers} from './utils/triggers';

type Props = {};

export const Content: React.FC<Props> = () => {
  /* Change the notification payload */
  const notification = notifications.basic;

  /* Change the trigger type */
  const triggerType = triggers.timestamp;

  const onDisplayNotificationPress = async () => {
    await notifee.deleteChannel(notification.android?.channelId || 'default');
    // Create a channel
    await notifee.createChannel({
      id: notification.android?.channelId || 'default',
      name: notification.android?.channelId || 'default',
      importance: notification.android?.importance || AndroidImportance.DEFAULT,
      visibility: notification.android?.visibility || AndroidVisibility.PRIVATE,
      vibration: true,
      sound: notification.android?.sound || 'default',
    });

    try {
      await notifee.displayNotification(notification);
    } catch (e) {
      console.error(e);
    }
  };

  const onTriggerNotificationPress = async () => {
    await notifee.deleteChannel(notification.android?.channelId || 'default');
    // Create a channel
    await notifee.createChannel({
      id: notification.android?.channelId || 'default',
      name: notification.android?.channelId || 'default',
      importance: notification.android?.importance || AndroidImportance.DEFAULT,
      visibility: notification.android?.visibility || AndroidVisibility.PRIVATE,
      vibration: true,
      sound: notification.android?.sound || 'default',
    });
    const channel = await notifee.getChannel(
      notification.android?.channelId || 'default',
    );
    console.log('notification.channel', channel);
    /* Change the trigger */
    const trigger = triggerType();

    await notifee.createTriggerNotification(notification, trigger);
    console.log('Trigger created: ', notification, trigger);
  };

  const onAPIPress = async () => {
    /* Change the API function to test */
    const result = await notifee.cancelAllNotifications();

    console.log('onAPIPress -> ', result != null ? result : 'API Call Success');
  };

  return (
    <View style={styles.container}>
      <View style={styles.content}>
        <View style={styles.contentItem}>
          <View style={styles.contentItemText}>
            <Text>{`Notification: ${notification.title}`}</Text>
          </View>
          <View style={[styles.button]}>
            <Button
              color={(Platform.OS === 'ios' && '#fff') || '#44337A'}
              title={'Display Notification'}
              onPress={onDisplayNotificationPress}
            />
          </View>
        </View>
        <View style={styles.contentItem}>
          <View style={styles.contentItemText}>
            <Text>{`Trigger Type: ${triggerType.name}`}</Text>
          </View>
          <View style={[styles.button]}>
            <Button
              color={(Platform.OS === 'ios' && '#fff') || '#44337A'}
              title={'Create Trigger Notification'}
              onPress={onTriggerNotificationPress}
            />
          </View>
        </View>
        <View style={[styles.contentItem, styles.apiActionButton]}>
          <View style={[styles.button]}>
            <Button
              color={(Platform.OS === 'ios' && '#fff') || '#44337A'}
              title={'Test Notifee API Action'}
              onPress={onAPIPress}
            />
          </View>
        </View>
      </View>
    </View>
  );
};

export default {Content};

const styles = StyleSheet.create({
  container: {
    flex: 0.6,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
    marginTop: 50,
  },
  content: {
    justifyContent: 'space-evenly',
    flex: 0.5,
  },
  contentItem: {
    margin: 20,
  },
  apiActionButton: {
    marginTop: 30,
  },
  contentItemText: {
    textAlign: 'left',
    marginBottom: 5,
  },
  button: {
    backgroundColor: '#44337A',
    color: 'white',
  },
});
