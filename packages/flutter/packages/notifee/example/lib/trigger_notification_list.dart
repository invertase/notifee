// ignore_for_file: require_trailing_commas

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:notifee/notifee.dart';
import 'package:notifee/notifee.dart' as notifee;

import 'trigger_notification_list_item.dart';

/// List of trigger notifications
class TriggerNotificationList extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _TriggerNotificationList();
}

class _TriggerNotificationList extends State<TriggerNotificationList> {
  List<TriggerNotification> _items = [];

  @override
  void initState() {
    super.initState();
    notifee.getTriggerNotifications().then((triggerNotifications) {
      setState(() {
        _items = triggerNotifications;
      });
    });
  }

  Future<void> createTriggerNotification() async {
    try {
      await notifee.createChannel(Channel(id: 'general', name: 'General', importance: AndroidImportance.high));
      NotifeeNotification notification = NotifeeNotification(
          title: "Notification Title",
          body: "With a body",
          subtitle: "And a subtitle",
          ios: NotificationIOS(),
          android: NotificationAndroid(
              channelId: 'general', smallIcon: 'ic_launcher'));
      await notifee.requestPermission();
      TimestampTrigger trigger = TimestampTrigger(timestamp: DateTime.now().add(const Duration(seconds: 15)).millisecondsSinceEpoch);
      await notifee.createTimestampTriggerNotification(notification: notification, trigger: trigger);

      setState(() {
        _items = [..._items, TriggerNotification(notification: notification, trigger: trigger.asMap())];
      });

    } catch (e) {
      if (kDebugMode) {
        print(e);
      }
    }
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
        title: const Text('Trigger Notifications'),
        actions: [
            IconButton(
              icon: const Icon(
                Icons.close,
                color: Colors.white,
              ),
              onPressed: () async {
                await notifee.cancelTriggerNotifications();
                final triggerNotifications = await notifee.getTriggerNotifications();
                setState(() {
                  _items = triggerNotifications;
                });
              },
            )
        ],

    ),
    floatingActionButton: Builder(
      builder: (context) => FloatingActionButton(
        onPressed: createTriggerNotification,
        backgroundColor: Colors.white,
        child: const Icon(Icons.send),
      ),
    ),
    body: SingleChildScrollView(
    child: Column(
    children: [
    ListView.builder(
        shrinkWrap: true,
        itemCount: _items.length,
        itemBuilder: (context, index) {
          TriggerNotification item = _items[index];

          return ListTile(
            title: Text(
                item.notification.title ?? 'no notification title available'),
            subtitle:
                Text(item.notification.body?.toString() ?? DateTime.now().toString()),
            onTap: () => Navigator.pushNamed(context, '/trigger_notification',
                arguments: TriggerNotificationArguments(item)),
          );
        })
    ])));
  }
}
