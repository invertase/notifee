---
title: Triggers
description: Understand how to use triggers to create notifications that fire under specific conditions.
next: /react-native/docs/integrations/fcm
previous: /react-native/docs/events
---

Triggers can be used to display notifications in-advance when a specific condition is met such as time.

For example, you may wish to notify your user when they have a meeting at work.

<Vimeo id="triggers-example" caption="Triggers Example" />

> Currently only supported on Android, but iOS will be coming soon.

# Creating a trigger notification

```js
function Screen() {
  async function onCreateTriggerNotification() {
    import notifee, { TimeTrigger, TriggerType, TimeUnit } from '@notifee/react-native';
    // Create a time-based trigger
    const trigger: TimeTrigger = {
      type: TriggerType.TIME,
      timestamp: Date.now() + 600000, // 10 minutes
    };

    // Create a trigger notification
    await notifee.createTriggerNotification(
      {
        title: 'Meeting with Jane',
        body: 'Today at 11:20am',
        android: {
          channelId,
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

> To see a full list of triggers and their properties, view the [Trigger](/react-native/reference/trigger) documentation.

The `createTriggerNotification` method is called passing in a `notification` and a `trigger`. You must ensure the channel is created first as well as other attributes your notification may have such as categories.

Go ahead and press the button! A notification will appear in 10 minutes, giving the user a reminder they have a meeting with Jane!

# Updating a trigger notification

Trigger notifications work in the same way as any other notification. They have a random unique ID assigned to them which can be used to update pending trigger notifications. If there is no trigger with that ID, a new one will be created.

Let's update our trigger we created previously, but this time your user wants to be reminded each week when they have a meeting with Jane! For this, we will use the `repeatInterval` and `repeatIntervalTimeUnit`:

```js
async function onCreateTriggerNotification() {
  import notifee, { TimeTrigger, TriggerType, TimeUnit } from '@notifee/react-native';

   const date = new Date(Date.now() + 600000);
   date.setDate(date.getDate() + 7); // initial trigger notification next week

   const trigger: TimeTrigger = {
     type: TriggerType.TIME,
     timestamp: date,
     repeatInterval: 7,
     repeatIntervalTimeUnit: TimeUnit.DAYS
   };

  await notifee.createTriggerNotification({
   {
        id: '123',
        title: 'Meeting with Jane',
        body: 'Today at 11:20am',
        android: {
          channelId,
        },
      },
      trigger,
  });
}
```

> To update any notifications that are already displayed, you can update them using [displayNotification](/react-native/docs/displaying-a-notification)

# Cancelling a trigger notification

There may be situations whereby you may want to cancel the trigger notification and stop any future notifications from displaying.

To cancel a trigger notification, the [`cancelNotification`](/react-native/docs/displaying-a-notification#cancelling-a-notification) method can be called with the unique notification ID.

It's also possible to cancel all of your trigger notifications, by calling [cancelTriggerNotifications]('https://notifee.app/react-native/reference/canceltriggernotifications') or [cancelAllNotifications]('https://notifee.app/react-native/reference/cancelallnotifications).
