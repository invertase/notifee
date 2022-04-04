import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:notifee/notifee.dart' as notifee;

void main() {
  const MethodChannel channel = MethodChannel('notifee');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return [];
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getTriggerNotificationIds', () async {
    // expect(await notifee.getTriggerNotificationIds(), []);
  });
}
