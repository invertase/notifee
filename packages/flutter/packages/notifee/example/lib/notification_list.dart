// ignore_for_file: require_trailing_commas

import 'package:flutter/material.dart';
import 'package:notifee/notifee.dart';
import 'package:notifee/notifee.dart' as notifee;

import 'notification_list_item.dart';

/// Listens for incoming foreground messages and displays them in a list.
class NotificationList extends StatefulWidget {
  const NotificationList({super.key});

  @override
  State<StatefulWidget> createState() => _NotificationList();
}

final notifications = ValueNotifier<List<NotifeeNotification>>([]);

class _NotificationList extends State<NotificationList> {
  @override
  void initState() {
    super.initState();

    notifee.onForegroundEvent.listen((Event event) {
      if (event.detail.notification == null) {
        return;
      }

      setState(() {
        notifications.value = [
          ...notifications.value,
          event.detail.notification!
        ];
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    if (notifications.value.isEmpty) {
      return const Text('No notifications delivered');
    }

    return ValueListenableBuilder(
      builder: (context, value, widget) {
        return ListView.builder(
            shrinkWrap: true,
            itemCount: notifications.value.length,
            itemBuilder: (context, index) {
              NotifeeNotification notification = notifications.value[index];
              if (notifications.value.isEmpty) {
                return const Text('No notifications delivered');
              }
              return ListTile(
                title: Text(
                    notification.title ?? 'no notification title available'),
                subtitle: Text(
                    notification.body?.toString() ?? DateTime.now().toString()),
                onTap: () => Navigator.pushNamed(context, '/notification',
                    arguments: NotificationArguments(notification, false)),
              );
            });
      },
      valueListenable: notifications,
    );
  }
}
