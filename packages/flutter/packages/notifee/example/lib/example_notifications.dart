import 'package:notifee/notifee.dart';

/// Used by displayNotification() to demonstrate different [Channel] behaviour
enum ExampleAndroidChannelIds { horse, highImportance }

String exampleSmallIcon = 'ic_launcher';

// TODO: make into a dropdown
String selectedAndroidChannelId = ExampleAndroidChannelIds.horse.name;

Map<String, NotifeeNotification> exampleNotifications = {
  'randomId': NotifeeNotification(title: 'randomId'),
  'basic': NotifeeNotification(id: 'basic', title: 'basic', body: 'body'),
  'attachments': NotifeeNotification(
      id: 'attachments',
      title: 'attachments',
      ios: NotificationIOS(attachments: [
        IOSNotificationAttachment(
            id: 'attachment-cat',
            url:
                'https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png')
      ])),
};

NotifeeNotification? selectedNotification = exampleNotifications['randomId'];
