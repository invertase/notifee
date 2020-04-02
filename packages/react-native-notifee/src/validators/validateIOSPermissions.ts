import { IOSNotificationPermissions } from '../types/NotificationIOS';
import { hasOwnProperty, isBoolean } from '../utils';

export default function validateIOSPermissions(
  permissions: IOSNotificationPermissions,
): IOSNotificationPermissions {
  const out: IOSNotificationPermissions = {
    alert: true,
    badge: true,
    sound: true,
    carPlay: true,
    provisional: false,
    announcement: false,
    criticalAlert: false,
  };

  if (!permissions) {
    return out;
  }

  if (hasOwnProperty(permissions, 'alert')) {
    if (!isBoolean(permissions.alert)) {
      throw new Error("'alert' expected a boolean value.");
    }

    out.alert = permissions.alert;
  }

  if (hasOwnProperty(permissions, 'badge')) {
    if (!isBoolean(permissions.badge)) {
      throw new Error("'alert' badge a boolean value.");
    }

    out.badge = permissions.badge;
  }

  if (hasOwnProperty(permissions, 'sound')) {
    if (!isBoolean(permissions.sound)) {
      throw new Error("'sound' expected a boolean value.");
    }

    out.sound = permissions.sound;
  }

  if (hasOwnProperty(permissions, 'carPlay')) {
    if (!isBoolean(permissions.carPlay)) {
      throw new Error("'carPlay' expected a boolean value.");
    }

    out.carPlay = permissions.carPlay;
  }

  if (hasOwnProperty(permissions, 'provisional')) {
    if (!isBoolean(permissions.provisional)) {
      throw new Error("'provisional' expected a boolean value.");
    }

    out.provisional = permissions.provisional;
  }

  if (hasOwnProperty(permissions, 'announcement')) {
    if (!isBoolean(permissions.announcement)) {
      throw new Error("'announcement' expected a boolean value.");
    }

    out.announcement = permissions.announcement;
  }

  if (hasOwnProperty(permissions, 'criticalAlert')) {
    if (!isBoolean(permissions.criticalAlert)) {
      throw new Error("'criticalAlert' expected a boolean value.");
    }

    out.criticalAlert = permissions.criticalAlert;
  }

  return out;
}
