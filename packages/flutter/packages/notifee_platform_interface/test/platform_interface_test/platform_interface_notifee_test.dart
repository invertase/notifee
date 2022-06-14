import 'package:flutter_test/flutter_test.dart';
import 'package:notifee_platform_interface/notifee_platform_interface.dart';
import 'package:notifee_platform_interface/src/platform_interface/platform_interface_notifee.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

void main() {
  late NotifeePlatform notifeePlatform;

  group('$NotifeePlatform()', () {
    setUpAll(() async {
      notifeePlatform = NotifeePlatform();
    });

    test('Constructor', () {
      expect(notifeePlatform, isA<NotifeePlatform>());
      expect(notifeePlatform, isA<PlatformInterface>());
    });

    test('throws if getTriggerNotificationIds', () async {
      try {
        await notifeePlatform.getTriggerNotificationIds();
      } on UnimplementedError catch (e) {
        expect(e.message,
            equals('getTriggerNotificationIds() is not implemented'));
        return;
      }
      fail('Should have thrown an [UnimplementedError]');
    });
  });
}
