// ignore_for_file: require_trailing_commas

import 'package:notifee/notifee.dart';
import 'package:flutter/material.dart';

/// Notification route arguments.
class NotificationArguments {
  /// The NotifeeNotification
  final NotifeeNotification notification;

  /// Whether this notification caused the application to open.
  final bool openedApplication;

  // ignore: public_member_api_docs
  NotificationArguments(this.notification, this.openedApplication);
}

/// Displays information about a [NotifeeNotification].
class NotificationListItemView extends StatelessWidget {
  const NotificationListItemView({super.key});

  /// A single data row.
  Widget row(String title, String? value) {
    return Padding(
      padding: const EdgeInsets.only(left: 8, right: 8, top: 8),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text('$title: '),
          Expanded(child: Text(value ?? 'N/A')),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final NotificationArguments args =
        ModalRoute.of(context)!.settings.arguments! as NotificationArguments;
    NotifeeNotification notification = args.notification;

    return Scaffold(
      appBar: AppBar(
        title: Text(notification.id!),
      ),
      body: SingleChildScrollView(
          child: Padding(
        padding: const EdgeInsets.all(8),
        child: Column(
          children: [
            row('Triggered application open',
                args.openedApplication.toString()),
            row('Notification ID', notification.id),
            row('iOS', notification.ios?.toString()),
            row('Android', notification.android?.toString()),
            ...[
              Padding(
                padding: const EdgeInsets.only(top: 16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'Notification',
                      style: TextStyle(fontSize: 18),
                    ),
                    row(
                      'Title',
                      notification.title,
                    ),
                    row(
                      'Body',
                      notification.body,
                    ),
                    if (notification.android != null) ...[
                      const SizedBox(height: 16),
                      const Text(
                        'Android Properties',
                        style: TextStyle(fontSize: 18),
                      ),
                      row(
                        'Channel ID',
                        notification.android!.channelId,
                      ),
                      row(
                        'Actions',
                        notification.android!.actions?.toString(),
                      ),
                      row(
                        'Color',
                        notification.android!.color,
                      ),
                      row(
                        'Press Action',
                        notification.android!.pressAction.toString(),
                      ),
                      row(
                        'Importance',
                        notification.android!.importance.toString(),
                      ),
                      row(
                        'Sound',
                        notification.android!.sound,
                      ),
                      row(
                        'Ticker',
                        notification.android!.ticker,
                      ),
                      row(
                        'Visibility',
                        notification.android!.visibility.toString(),
                      ),
                      row(
                        'Group ID',
                        notification.android!.groupId?.toString(),
                      ),
                      row(
                        'Group Summary',
                        notification.android!.groupSummary.toString(),
                      ),
                    ],
                    if (notification.ios != null) ...[
                      const SizedBox(height: 16),
                      const Text(
                        'iOS Properties',
                        style: TextStyle(fontSize: 18),
                      ),
                      row(
                        'Thread Id',
                        notification.ios!.threadId,
                      ),
                      row(
                        'Badge Count',
                        notification.ios!.badgeCount.toString(),
                      ),
                      row(
                        'Sound',
                        notification.ios!.sound,
                      ),
                    ]
                  ],
                ),
              )
            ]
          ],
        ),
      )),
    );
  }
}
