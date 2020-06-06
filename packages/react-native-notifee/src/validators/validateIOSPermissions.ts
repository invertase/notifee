import { IOSNotificationPermissions } from '../types/NotificationIOS';
import { objectHasProperty, isBoolean } from '../utils';

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

  if (objectHasProperty(permissions, 'alert')) {
    if (!isBoolean(permissions.alert)) {
      throw new Error("'alert' expected a boolean value.");
    }

    out.alert = permissions.alert;
  }

  if (objectHasProperty(permissions, 'badge')) {
    if (!isBoolean(permissions.badge)) {
      throw new Error("'alert' badge a boolean value.");
    }

    out.badge = permissions.badge;
  }

  if (objectHasProperty(permissions, 'sound')) {
    if (!isBoolean(permissions.sound)) {
      throw new Error("'sound' expected a boolean value.");
    }

    out.sound = permissions.sound;
  }

  if (objectHasProperty(permissions, 'carPlay')) {
    if (!isBoolean(permissions.carPlay)) {
      throw new Error("'carPlay' expected a boolean value.");
    }

    out.carPlay = permissions.carPlay;
  }

  if (objectHasProperty(permissions, 'provisional')) {
    if (!isBoolean(permissions.provisional)) {
      throw new Error("'provisional' expected a boolean value.");
    }

    out.provisional = permissions.provisional;
  }

  if (objectHasProperty(permissions, 'announcement')) {
    if (!isBoolean(permissions.announcement)) {
      throw new Error("'announcement' expected a boolean value.");
    }

    out.announcement = permissions.announcement;
  }

  if (objectHasProperty(permissions, 'criticalAlert')) {
    if (!isBoolean(permissions.criticalAlert)) {
      throw new Error("'criticalAlert' expected a boolean value.");
    }

    out.criticalAlert = permissions.criticalAlert;
  }

  return out;
}
