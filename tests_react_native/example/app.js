/*
 *  Copyright (c) 2016-present Invertase Limited
 */

/* eslint-disable no-console */
import React, { useEffect } from 'react';
import { AppRegistry, StyleSheet, Text, View } from 'react-native';

import notifee from '@notifee/react-native';

function Root() {
  async function init() {
    await notifee.createChannel({
      name: 'Hello World',
      id: 'foo3',
    });

    const returnedNotification = await notifee.displayNotification({
      id: Math.random().toString(10),
      title: 'Hello',
      subtitle: 'World',
      body: 'foobarbazdaz test \n test \n',
      android: {
        channelId: 'foo3',
        largeIcon: 'https://static.invertase.io/assets/invertase-logo.png',
        style: {
          type: notifee.AndroidStyle.BIGPICTURE,
          picture: 'https://static.invertase.io/assets/invertase-logo.png',
        },
      },
    });

    console.warn(JSON.stringify(returnedNotification));
  }

  useEffect(() => {
    init().catch(console.error);
  }, []);

  return (
    <View style={[styles.container, styles.horizontal]}>
      <Text>Hello from Notifee</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  horizontal: {
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    padding: 10,
  },
  logo: {
    height: 120,
    marginBottom: 16,
    width: 135,
  },
});

AppRegistry.registerComponent('testing', () => Root);
