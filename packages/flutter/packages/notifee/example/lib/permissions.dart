// ignore_for_file: require_trailing_commas

import 'package:flutter/material.dart';
import 'package:notifee/notifee.dart';
import 'package:notifee/notifee.dart' as notifee;

/// Requests & displays the current user permissions for this device.
class Permissions extends StatefulWidget {
  const Permissions({super.key});
  @override
  State<StatefulWidget> createState() => _Permissions();
}

class _Permissions extends State<Permissions> {
  bool _requested = false;
  bool _fetching = false;
  late NotificationSettings _settings;

  Future<void> requestPermissions() async {
    setState(() {
      _fetching = true;
    });

    NotificationSettings settings = await notifee
        .requestPermission(IOSNotificationPermissions(alert: true));

    setState(() {
      _requested = true;
      _fetching = false;
      _settings = settings;
    });
  }

  Future<void> checkPermissions() async {
    setState(() {
      _fetching = true;
    });

    NotificationSettings settings = await notifee.getNotificationSettings();

    setState(() {
      _requested = true;
      _fetching = false;
      _settings = settings;
    });
  }

  Widget row(String title, String value) {
    return Container(
      margin: const EdgeInsets.only(bottom: 8),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text('$title:', style: const TextStyle(fontWeight: FontWeight.bold)),
          Text(value),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    if (_fetching) {
      return const CircularProgressIndicator();
    }

    if (!_requested) {
      return ElevatedButton(
          onPressed: requestPermissions,
          child: const Text('Request Permissions'));
    }

    return Column(children: [
      row('Authorization Status', statusMap[_settings.authorizationStatus]!),
      ...[
        const SizedBox(height: 16),
        const Text(
          'iOS Settings',
          style: TextStyle(fontSize: 18),
        ),
        row('Alert', settingsMap[_settings.iosNotificationSetting.alert]!),
        row('Announcement',
            settingsMap[_settings.iosNotificationSetting.announcement]!),
        row('Badge', settingsMap[_settings.iosNotificationSetting.badge]!),
        row('Car Play', settingsMap[_settings.iosNotificationSetting.carPlay]!),
        row('Lock Screen',
            settingsMap[_settings.iosNotificationSetting.lockScreen]!),
        row('Notification Center',
            settingsMap[_settings.iosNotificationSetting.notificationCenter]!),
        row('Show Previews',
            previewMap[_settings.iosNotificationSetting.showPreviews]!),
        row('Sound', settingsMap[_settings.iosNotificationSetting.sound]!),
        row('Critical',
            settingsMap[_settings.iosNotificationSetting.criticalAlert]!),
      ],
      ...[
        const SizedBox(height: 16),
        const Text(
          'Android Settings',
          style: TextStyle(fontSize: 18),
        ),
        row('Alarm', settingsMap[_settings.androidNotificationSetting.alarm]!),
      ],
      ElevatedButton(
          onPressed: checkPermissions, child: const Text('Reload Permissions')),
    ]);
  }
}

/// Maps a [AuthorizationStatus] to a string value.
const statusMap = {
  AuthorizationStatus.authorized: 'Authorized',
  AuthorizationStatus.denined: 'Denied',
  AuthorizationStatus.notDefined: 'Not Defined',
  AuthorizationStatus.provisional: 'Provisional',
};

/// Maps a [NotificationSetting] to a string value.
const settingsMap = {
  NotificationSetting.disabled: 'Disabled',
  NotificationSetting.enabled: 'Enabled',
  NotificationSetting.notSupported: 'Not Supported',
};

/// Maps a [IOSShowPreviewsSetting] to a string value.
const previewMap = {
  IOSShowPreviewsSetting.always: 'Always',
  IOSShowPreviewsSetting.never: 'Never',
  IOSShowPreviewsSetting.notDefined: 'Not Defined',
  IOSShowPreviewsSetting.whenAuthorized: 'When Authorized',
};
