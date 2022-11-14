import 'package:example/components/accordion.dart';
import 'package:flutter/material.dart';
import 'package:notifee/notifee.dart' as notifee;
import 'package:example/notification_list.dart' as notif_list;

class DisplayedNotifications extends StatefulWidget {
  const DisplayedNotifications({super.key});

  @override
  State<DisplayedNotifications> createState() => _DisplayedNotificationsState();
}

class _DisplayedNotificationsState extends State<DisplayedNotifications> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Displayed Notifications"),
      ),
      body: Container(
          padding: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 60.0),
          child: SingleChildScrollView(
            child: ListView.builder(
              scrollDirection: Axis.vertical,
              shrinkWrap: true,
              itemCount: notif_list.notifications.value.length,
              itemBuilder: (context, index) {
                notifee.NotifeeNotification? notification =
                    notif_list.notifications.value[index];
                if (notif_list.notifications.value.isEmpty) {
                  return const Text('No notifications delivered');
                }
                return Accordion(
                  title:
                      notification.title ?? 'No notification title available',
                  notification: notification,
                );
              },
            ),
          )),
    );
  }
}
