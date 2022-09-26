import 'package:notifee_platform_interface/src/method_channel/method_channel_notifee.dart';

import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';

typedef MethodCallCallback = dynamic Function(MethodCall methodCall);

void handleMethodCall(MethodCallCallback methodCallCallback) =>
    MethodChannelNotifee.channel.setMockMethodCallHandler((call) async {
      return await methodCallCallback(call);
    });

void initializeMethodChannel() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelNotifee.channel.setMockMethodCallHandler((call) async {
    return null;
  });
}

void main() {
  initializeMethodChannel();
  MethodChannelNotifee? notifee;
  bool mockPlatformExceptionThrown = false;
  bool mockExceptionThrown = false;
  final List<MethodCall> log = <MethodCall>[];

  setUpAll(() async {
    notifee = MethodChannelNotifee();

    handleMethodCall((MethodCall call) {
      log.add(call);
      switch (call.method) {
        case 'getTriggerNotificationIds':
          if (mockExceptionThrown) {
            throw Exception();
          } else if (mockPlatformExceptionThrown) {
            throw PlatformException(code: 'UNKNOWN');
          }
          return Future.value(["id"]);
        default:
          return Future.value(null);
      }
    });
  });

  setUp(() {
    mockPlatformExceptionThrown = false;
    mockExceptionThrown = false;
    log.clear();
  });

  group('getTriggerNotificationIds()', () {
    test('invoke getTriggerNotificationIds with correct args', () async {
      final result = await notifee!.getTriggerNotificationIds();

      expect(result, equals(["id"]));
      expect(
        log,
        equals(<Matcher>[
          isMethodCall('getTriggerNotificationIds', arguments: null),
        ]),
      );
    });

    test('catch [PlatformException] error', () {
      mockPlatformExceptionThrown = true;

      expect(() => notifee!.getTriggerNotificationIds(),
          throwsA(isInstanceOf<Exception>()));
    });
  });
}
