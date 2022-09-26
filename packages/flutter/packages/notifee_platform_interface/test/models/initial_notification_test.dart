import 'package:notifee_platform_interface/notifee_platform_interface.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  group('$InitialNotification', () {
    test('returns expected values ', () {
      final notification = NotifeeNotification();
      final initialNotification = InitialNotification(
          notification: notification, pressAction: Null, input: "hello");
      expect(initialNotification.pressAction, Null);
      expect(initialNotification.notification.id, notification.id);
      expect(initialNotification.input, "hello");
    });
  });
}
