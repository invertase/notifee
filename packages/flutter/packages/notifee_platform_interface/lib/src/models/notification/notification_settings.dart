enum IOSShowPreviewsSetting {
  /// This setting is not supported on this device. Usually this means that the iOS version required for this setting (iOS 11+) has not been met.
  notDefined,

  /// Never show previews.
  never,

  /// Always show previews even if the device is currently locked.
  always,

  /// Only show previews when the device is unlocked.
  whenAuthorized,
}

enum AuthorizationStatus {
  /// The app user has not yet chosen whether to allow the application to create notifications. Usually
  /// this status is returned prior to the first call of `requestPermission`.
  /// @platform ios
  notDefined,

  /// The app is not authorized to create notifications.
  denined,

  /// The app is authorized to create notifications.
  authorized,

  /// The app is currently authorized to post non-interrupting user notifications
  /// @platform ios iOS >= 12
  provisional,
}

enum NotificationSetting {
  /// This setting is not supported on this device. Usually this means that the iOS version required
  /// for this setting has not been met.
  notSupported,

  /// This setting is currently disabled by the user.
  disabled,

  /// This setting is currently enabled.
  enabled,
}

class IOSNotificationSettings {
  IOSNotificationSettings({
    required this.alert,
    required this.badge,
    required this.criticalAlert,
    required this.showPreviews,
    required this.sound,
    required this.carPlay,
    required this.lockScreen,
    required this.announcement,
    required this.notificationCenter,
    required this.inAppNotificationSettings,
    required this.authorizationStatus,
  });

  /// Enum describing if notifications will alert the user.
  NotificationSetting alert;

  /// Enum describing if notifications can update the application badge.
  NotificationSetting badge;

  /// Enum describing if critical notifications are allowed.
  NotificationSetting criticalAlert;

  /// Enum describing if notification previews will be shown.
  IOSShowPreviewsSetting showPreviews;

  /// Enum describing if notifications can trigger a sound.
  NotificationSetting sound;

  /// Enum describing if notifications can be displayed in a CarPlay environment.
  NotificationSetting carPlay;

  /// Enum describing if notifications will be displayed on the lock screen.
  NotificationSetting lockScreen;

  /// Enum describing if notifications can be announced to the user
  /// via 3rd party services such as Siri.
  ///
  /// For example, if the notification can be automatically read by Siri
  /// while the user is wearing AirPods.
  NotificationSetting announcement;

  /// Enum describing if notifications will be displayed in the notification center.
  NotificationSetting notificationCenter;

  NotificationSetting inAppNotificationSettings;

  /// Overall notification authorization status for the application.
  AuthorizationStatus authorizationStatus;

  factory IOSNotificationSettings.fromMap(Map<String, dynamic> map) =>
      IOSNotificationSettings(
        alert: NotificationSetting.values[map['alert'] + 1],
        announcement: NotificationSetting.values[map['announcement'] + 1],
        authorizationStatus:
            AuthorizationStatus.values[map['authorizationStatus'] + 1],
        sound: NotificationSetting.values[map['sound'] + 1],
        criticalAlert: NotificationSetting.values[map['criticalAlert'] + 1],
        badge: NotificationSetting.values[map['badge'] + 1],
        notificationCenter:
            NotificationSetting.values[map['notificationCenter'] + 1],
        inAppNotificationSettings:
            NotificationSetting.values[map['inAppNotificationSettings'] + 1],
        carPlay: NotificationSetting.values[map['carPlay'] + 1],
        lockScreen: NotificationSetting.values[map['lockScreen'] + 1],
        showPreviews: IOSShowPreviewsSetting.values[map['showPreviews'] + 1],
      );

  Map<String, Object?> asMap() {
    // TODO: IOSNotificationSettings asMap
    Map<String, Object?> map = {};

    map.removeWhere((_, value) => value == null);
    return map;
  }
}

class AndroidNotificationSettings {
  AndroidNotificationSettings({required this.alarm});

  NotificationSetting alarm;

  factory AndroidNotificationSettings.fromMap(Map<String, dynamic> map) =>
      AndroidNotificationSettings(
        alarm: NotificationSetting.values[map['alarm'] + 1],
      );

  Map<String, Object?> asMap() {
    // TODO: AndroidNotificationSettings asMap
    Map<String, Object?> map = {};

    map.removeWhere((_, value) => value == null);
    return map;
  }
}

class NotificationSettings {
  NotificationSettings(
      {required this.authorizationStatus,
      required this.iosNotificationSetting,
      required this.androidNotificationSetting});

  AuthorizationStatus authorizationStatus;
  IOSNotificationSettings iosNotificationSetting;
  AndroidNotificationSettings androidNotificationSetting;

  factory NotificationSettings.fromMap(Map<String, dynamic> map) =>
      NotificationSettings(
        authorizationStatus:
            AuthorizationStatus.values[map['authorizationStatus'] + 1],
        iosNotificationSetting: map['ios'] != null
            ? IOSNotificationSettings.fromMap(
                Map<String, dynamic>.from(map['ios'] as Map))
            : IOSNotificationSettings(
                alert: NotificationSetting.enabled,
                criticalAlert: NotificationSetting.enabled,
                showPreviews: IOSShowPreviewsSetting.always,
                sound: NotificationSetting.enabled,
                carPlay: NotificationSetting.enabled,
                authorizationStatus: AuthorizationStatus.authorized,
                announcement: NotificationSetting.enabled,
                inAppNotificationSettings: NotificationSetting.enabled,
                lockScreen: NotificationSetting.enabled,
                badge: NotificationSetting.enabled,
                notificationCenter: NotificationSetting.enabled),
        androidNotificationSetting: map['android'] != null
            ? AndroidNotificationSettings.fromMap(
                Map<String, dynamic>.from(map['android'] as Map))
            : AndroidNotificationSettings(alarm: NotificationSetting.enabled),
      );

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'authorizationStatus': authorizationStatus,
      'iosNotificationSetting': iosNotificationSetting.asMap(),
      'androidNotificationSetting': androidNotificationSetting.asMap(),
    };

    map.removeWhere((_, value) => value == null);
    return map;
  }
}
