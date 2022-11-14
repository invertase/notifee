import 'dart:async';

import 'package:example/displayed_notifications.dart';
import 'package:example/permissions.dart';
import 'package:example/notification_list.dart';
import 'package:example/trigger_notification_list.dart';
import 'package:example/trigger_notification_list_item.dart';
import 'package:example/notification_list_item.dart';
import 'package:example/create_notification_modal.dart';
import 'package:example/channels.dart';
import 'package:example/common.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:notifee/notifee.dart';
import 'package:notifee/notifee.dart' as notifee;
import 'notification_list.dart' as _notification_list;

/// Define a top-level named handler which background/terminated notifications will
/// call.
///
/// To verify things are working, check out the native platform logs.
Future<void> _notifeeBackgroundHandler(Event event) async {
  if (kDebugMode) {
    print('Handling a background event ${event.type}');
  }
}

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();

  // Set the background handler early on, as a named top-level function
  notifee.onBackgroundEvent(_notifeeBackgroundHandler);

  runApp(const NotifeeExampleApp());
}

/// Entry point for the example application.
class NotifeeExampleApp extends StatelessWidget {
  const NotifeeExampleApp({super.key});
  void superInit() {}
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Notifee Example App',
      theme: ThemeData.dark(),
      routes: {
        '/': (context) => const Application(),
        '/notification': (context) => const NotificationListItemView(),
        '/trigger_notification': (context) =>
            const TriggerNotificationListItemView(),
        '/trigger_notifications': (context) => const TriggerNotificationList(),
        '/displayed_notifications': (context) => const DisplayedNotifications(),
        '/channels': (context) => const Channels(),
      },
    );
  }
}

Future<void> createResources() async {
  /// Create Android Notification Channels.
  Channel channel = Channel(
    id: ExampleAndroidChannelIds.highImportance.name,
    name: 'High Importance Notifications',
    importance: AndroidImportance.high,
  );
  await notifee.deleteChannel(channel.id);
  await notifee.createChannel(channel);

  channel = Channel(
      id: ExampleAndroidChannelIds.horse.name,
      name: 'Horse Sound Notifications',
      importance: AndroidImportance.high,
      sound: 'horse');

  await notifee.deleteChannel(channel.id);
  await notifee.createChannel(channel);

  // Create iOS category with actions
  final List<IOSNotificationCategory> categories = [
    IOSNotificationCategory(id: 'cat-cat', actions: [
      IOSNotificationCategoryAction(id: 'scratch', title: 'scratch'),
      IOSNotificationCategoryAction(id: 'purr', title: 'scratch'),
    ])
  ];

  await notifee.setNotificationCategories(categories);
}

/// Renders the example application.
class Application extends StatefulWidget {
  const Application({super.key});
  @override
  State<StatefulWidget> createState() => _Application();
}

class _Application extends State<Application> {
  @override
  void initState() {
    super.initState();

    createResources();

    notifee
        .getInitialNotification()
        .then((InitialNotification? initialNotification) {
      if (initialNotification != null) {
        Navigator.pushNamed(
          context,
          '/initialNotification',
          arguments:
              NotificationArguments(initialNotification.notification, true),
        );
      }
    });

    void getChannels() async {
      List<Channel> channels = await notifee.getChannels();
      for (var i = 0; i < channels.length; i++) {
        if (channelIds.contains(channels[i].name.toString())) {
        } else {
          channelIds.add(channels[i].name.toString());
        }
      }
      selectedAndroidChannelId = channelIds[0];
    }

    notifee.onForegroundEvent.listen((Event event) {
      if (kDebugMode) {
        print('A new event was published!');
      }
      if (event.detail.notification == null) {
        return;
      }

      Navigator.pushNamed(
        context,
        '/notification',
        arguments: NotificationArguments(
            event.detail.notification!, event.type == EventType.press),
      );
    });
  }

  Future<void> displayNotification() async {
    try {
      createResources();
      NotifeeNotification notification = selectedNotification!;

      if (notification.android == null) {
        notification.android = NotificationAndroid(
            channelId: selectedAndroidChannelId, smallIcon: exampleSmallIcon);
      } else {
        notification.android!.channelId = selectedAndroidChannelId;
        notification.android!.smallIcon = exampleSmallIcon;
      }
      notification.ios = NotificationIOS(categoryId: 'cat-cat');

      await notifee.requestPermission();
      await notifee.displayNotification(notification);
    } catch (e) {
      if (kDebugMode) {
        print(e);
      }
    }
  }

  Future<void> onActionSelected(String value) async {
    switch (value) {
      case 'getDisplayedNotifications':
        {
          if (kDebugMode) {
            print(
              'Notifee Example: Displayed Notifications.',
            );
          }

          Navigator.pushNamed(context, '/displayed_notifications');
        }
        break;
      case 'getTriggerNotifications':
        {
          if (kDebugMode) {
            print(
              'Notifee Example: Trigger Notifications',
            );
          }

          Navigator.pushNamed(context, '/trigger_notifications');
        }
        break;
      case 'getChannels':
        {
          if (kDebugMode) {
            print('Notifee Example: Chanenls');
          }
          Navigator.pushNamed(context, '/channels');
        }
        break;
      case 'cancelDisplayedNotifications':
        {
          if (kDebugMode) {
            print('Notifee Example: cancelDisplayedNotifications');
          }
          await notifee.cancelDisplayedNotifications();
          _notification_list.notifications.value = [];
        }
        break;
      default:
        break;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Notifee Example'),
        actions: <Widget>[
          PopupMenuButton(
            onSelected: onActionSelected,
            itemBuilder: (BuildContext context) {
              return [
                const PopupMenuItem(
                  value: 'getDisplayedNotifications',
                  child: Text('Displayed Notifications'),
                ),
                const PopupMenuItem(
                  value: 'getTriggerNotifications',
                  child: Text('Trigger Notifications'),
                ),
                const PopupMenuItem(
                  value: 'getChannels',
                  child: Text('Channels'),
                ),
                const PopupMenuItem(
                  value: 'cancelDisplayedNotifications',
                  child: Text('Cancel Displayed Notifications'),
                ),
              ];
            },
          ),
        ],
      ),
      floatingActionButton: InkWell(
        splashColor: Colors.deepPurple[700],
        onLongPress: () {
          showModalBottomSheet(
              enableDrag: false,
              isScrollControlled: true,
              backgroundColor: Colors.transparent,
              context: context,
              builder: (context) {
                return const CreateNotificationModal();
              });
        },
        child: FloatingActionButton(
          onPressed: displayNotification,
          backgroundColor: Colors.white,
          child: const Icon(Icons.send),
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: const [
            MetaCard(title: 'Permissions', children: Permissions()),
            MetaCard(
                title: 'Notification Stream', children: NotificationList()),
          ],
        ),
      ),
    );
  }
}

/// UI Widget for displaying metadata.
class MetaCard extends StatelessWidget {
  // ignore: public_member_api_docs
  const MetaCard({required this.title, required this.children, super.key});

  final String title;
  final Widget children;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      margin: const EdgeInsets.only(left: 8, right: 8, top: 8),
      child: Card(
        child: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            children: [
              Container(
                margin: const EdgeInsets.only(bottom: 16),
                child: Text(title, style: const TextStyle(fontSize: 18)),
              ),
              children,
            ],
          ),
        ),
      ),
    );
  }
}
