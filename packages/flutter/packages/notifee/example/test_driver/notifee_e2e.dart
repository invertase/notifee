import 'package:drive/drive.dart' as drive;
import 'package:flutter/foundation.dart';
import 'package:notifee/notifee.dart';
import 'package:notifee/notifee.dart' as notifee;
import 'package:flutter_test/flutter_test.dart';

void testsMain() {
  // setUpAll(() async {
  // });
  group('requestPermission', () {
    test(
      'authorizationStatus returns AuthorizationStatus.authorized on Android',
      () async {
        final result = await notifee.requestPermission();
        expect(result, isA<NotificationSettings>());
        expect(result.authorizationStatus, AuthorizationStatus.authorized);
      },
      skip: defaultTargetPlatform != TargetPlatform.android,
    );
  });

  group('getInitialNotification', () {
    test('returns null when no initial notification', () async {
      expect(await notifee.getInitialNotification(), null);
    });
  });
}

void main() => drive.main(testsMain);
