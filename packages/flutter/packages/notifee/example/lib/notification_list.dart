// ignore_for_file: require_trailing_commas

import 'package:flutter/foundation.dart';
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

class _NotificationList extends State<NotificationList> {
  List<NotifeeNotification> _notifications = [];

  @override
  void initState() {
    super.initState();

    notifee.onForegroundEvent.listen((Event event) {
      if (kDebugMode) {
        print("onForegroundEvent $event");
        print("type ${event.type}");
      }
      if (event.detail.notification == null) {
        return;
      }

      setState(() {
        _notifications = [..._notifications, event.detail.notification!];
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    if (_notifications.isEmpty) {
      return const Text('No notifications delivered');
    }

    return ListView.builder(
        shrinkWrap: true,
        itemCount: _notifications.length,
        itemBuilder: (context, index) {
          NotifeeNotification notification = _notifications[index];

          return ListTile(
            title:
                Text(notification.title ?? 'no notification title available'),
            subtitle: Text(
                notification.body?.toString() ?? DateTime.now().toString()),
            onTap: () => Navigator.pushNamed(context, '/notification',
                arguments: NotificationArguments(notification, false)),
          );
        });
  }
}
