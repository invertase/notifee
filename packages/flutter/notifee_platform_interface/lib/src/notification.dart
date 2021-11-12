import 'package:notifee_platform_interface/notifee_platform_interface.dart';

class Notification {
  Notification({this.id});

  String? id;
  String? title;
  String? subtitle;
  String? body;
  Map<String, String>? data;
  NotificationAndroid? android;
  NotificationIOS? ios;

  Map<String, Object?> asMap() {
    return {
      'id': id,
      'title': title,
      'subtitle': subtitle,
      'body': body,
      'data': data,
      // 'android': android?.asMap(),
      // 'ios': ios?.asMap(),
    };
  }
}
