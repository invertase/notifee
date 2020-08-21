---
title: Triggers
description: Understand how to use triggers to create notifications that fire under specific conditions.
next: /react-native/docs/integrations/fcm
previous: /react-native/docs/events
---

Triggers can be used to display notifications in-advance when a specific condition is met such as time.

For example, you may wish to notify your user when they have a meeting at work.

<Vimeo id="triggers-example" caption="Triggers Example" />

# Handling trigger notifications

## Creating a trigger notification

```js
import React from 'react';
import { View, Button } from 'react-native';
import notifee, { TimestampTrigger, TriggerType } from '@notifee/react-native';

function Screen() {
  async function onCreateTriggerNotification() {
    // Create a time-based trigger
    const trigger: TimestampTrigger = {
      type: TriggerType.TIMESTAMP,
      timestamp: Date.now() + 600000, // 10 minutes
    };

    // Create a trigger notification
    await notifee.createTriggerNotification(
      {
        title: 'Meeting with Jane',
        body: 'Today at 11:20am',
        android: {
          channelId: 'your-channel-id',
        },
      },
      trigger,
    );
  }

  return (
    <View>
      <Button title="Create Trigger Notification" onPress={() => onCreateTriggerNotification()} />
    </View>
  );
}
```

> To see a full list of triggers and their properties, view the [Trigger](/react-native/reference/trigger) reference documentation.

The `createTriggerNotification` method is called passing in a `notification` and a `trigger`. You must ensure the channel is created first as well as other attributes your notification may have such as categories.

Go ahead and press the button! A notification will appear in 10 minutes, giving the user a reminder they have a meeting with Jane!

## Updating a trigger notification

Trigger notifications work in the same way as any other notification. They have a random unique ID assigned to them which can be used to update pending trigger notifications. If there is no trigger with that ID, a new one will be created.

Let's update our trigger we created previously, but this time the meeting with Jane is in 3 hours.

```js
import notifee, { TimestampTrigger, TriggerType } from '@notifee/react-native';

async function onCreateTriggerNotification() {
    const trigger: TimestampTrigger = {
      type: TriggerType.TIMESTAMP,
      timestamp: Date.now() + 10800, // 3 hours
    };

  await notifee.createTriggerNotification(
    {
      id: '123',
      title: 'Meeting with Jane',
      body: 'Today at 2:20pm',
      android: {
        channelId: 'your-channel-id',
      },
    },
    trigger,
  );
}
```

> To update any notifications that are already displayed, you can update them using [displayNotification](/react-native/docs/displaying-a-notification)

## Retrieving trigger notifications

To retrieve a list of all your trigger notifications you can call [getTriggerNotificationIds](/react-native/reference/gettriggernotificationids) method and a list of ids will be returned.

```js
notifee.getTriggerNotificationIds().then(ids => console.log('All trigger notifications: ', ids));
```

## Cancelling a trigger notification

There may be situations whereby you may want to cancel the trigger notification and stop any future notifications from displaying.

To cancel a trigger notification, the [`cancelNotification`](/react-native/docs/displaying-a-notification#cancelling-a-notification) method can be called with the unique notification ID.

It's also possible to cancel all of your trigger notifications, by calling [cancelTriggerNotifications](/react-native/reference/canceltriggernotifications) or [cancelAllNotifications](/react-native/reference/cancelallnotifications).

# Trigger Types

## Timestamp Trigger

The [`TimestampTrigger`](/react-native/reference/timestamptrigger) allows you to create a trigger that displays a notification once at a specific time and date, using the property `timestamp`:

```js
import notifee, { TimestampTrigger, TriggerType, TimeUnit } from '@notifee/react-native';

const trigger: TimestampTrigger = {
  type: TriggerType.TIMESTAMP,
  timestamp: Date.now() + 10800, // 3 hours
};
```

## Interval Trigger

The [`IntervalTrigger`](/react-native/reference/intervaltrigger) allows you to create a trigger that repeats at a specific interval. The trigger accepts two properties, an `interval` and an optional `timeUnit`.

For example, to set a trigger to repeat every 30 minutes from now:

```js
import notifee, { IntervalTrigger, TriggerType, TimeUnit } from '@notifee/react-native';

const trigger: IntervalTrigger = {
  type: TriggerType.INTERVAL,
  interval: 30
  timeUnit: TimeUnit.MINUTES
};
```
