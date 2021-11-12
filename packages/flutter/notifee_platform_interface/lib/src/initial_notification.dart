import 'package:notifee_platform_interface/notifee_platform_interface.dart';

class InitialNotification {
  InitialNotification(
      {required this.notification, required this.pressAction, this.input});

  Notification notification;
  Object pressAction;
  String? input;
}
