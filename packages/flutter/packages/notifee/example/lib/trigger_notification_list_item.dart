// ignore_for_file: require_trailing_commas

import 'package:notifee/notifee.dart';
import 'package:flutter/material.dart';

/// Trigger Notification route arguments.
class TriggerNotificationArguments {
  /// The TriggerNotification
  final TriggerNotification triggerNotification;

  // ignore: public_member_api_docs
  TriggerNotificationArguments(this.triggerNotification);
}

/// Displays information about a [TriggerNotification].
class TriggerNotificationListItemView extends StatelessWidget {
  const TriggerNotificationListItemView({super.key});

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
    final TriggerNotificationArguments args = ModalRoute.of(context)!
        .settings
        .arguments! as TriggerNotificationArguments;
    TriggerNotification triggerNotification = args.triggerNotification;
    var trigger = args.triggerNotification.trigger;

    return Scaffold(
      appBar: AppBar(
        title: Text(triggerNotification.notification.id!),
      ),
      body: SingleChildScrollView(
          child: Padding(
        padding: const EdgeInsets.all(8),
        child: Column(
          children: [
            row('Notification ID', triggerNotification.notification.id),
            row('Trigger', triggerNotification.trigger.toString()),
            ...[
              Padding(
                padding: const EdgeInsets.only(top: 16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'Trigger',
                      style: TextStyle(fontSize: 18),
                    ),
                    if (trigger['type'] == TriggerType.interval) ...[
                      const SizedBox(height: 16),
                      const Text(
                        'Interval Trigger',
                        style: TextStyle(fontSize: 18),
                      ),
                      row('Type', TriggerType.interval.name),
                      row(
                        'Interval',
                        trigger['interval'],
                      ),
                      row(
                        'Time Unit',
                        trigger['timeUnit'],
                      ),
                    ],
                    if (trigger['type'] == TriggerType.timestamp) ...[
                      const SizedBox(height: 16),
                      const Text(
                        'Timestamp Trigger',
                        style: TextStyle(fontSize: 18),
                      ),
                      row(
                        'Timestamp',
                        trigger['timestamp'].toString(),
                      ),
                      row(
                        'Repeat Frequency',
                        trigger['repeatFrequency'].toString(),
                      ),
                      row(
                        'allowWhileIdle',
                        trigger['allowWhileIdle'].toString(),
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
