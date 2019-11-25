package io.invertase.notifee;

import android.content.Context;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;

import static io.invertase.notifee.core.NotifeeContextHolder.getApplicationContext;

class NotifeeUtils {
  /**
   * Attempts to find a device resource id by name and type
   */
  static int getResourceIdByName(String name, String type) {
    String packageName = getApplicationContext().getPackageName();
    return getApplicationContext().getResources().getIdentifier(name, type, packageName);
  }

//  static int getResourceId(Context context, String type, String image) {
//    return context
//      .getResources()
//      .getIdentifier(image, type, context.getPackageName());
//  }

  static String getFileName(Context context, Uri uri) {
    String result = null;
    if (uri.getScheme() != null && uri.getScheme().equals("content")) {
      Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

      try {
        if (cursor != null && cursor.moveToFirst()) {
          result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
        }
      } finally {
        if (cursor != null) {
          cursor.close();
        }
      }
    }

    if (result == null) {
      result = uri.getPath();
      if (result != null) {
        int cut = result.lastIndexOf('/');
        if (cut != -1) {
          result = result.substring(cut + 1);
        } else {
          result = "default";
        }
      }
    }

    if (result == null || result.equals("notification_sound")) result = "default";

    return result;
  }


//  static Uri getSound(Context context, String sound) {
//    if (sound == null) {
//      return null;
//    } else if (sound.contains("://")) {
//      return Uri.parse(sound);
//    } else if (sound.equalsIgnoreCase("default")) {
//      return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//    } else {
//      int soundResourceId = getResourceId(context, "raw", sound);
//      if (soundResourceId == 0) {
//        soundResourceId = getResourceId(context, "raw", sound.substring(0, sound.lastIndexOf('.')));
//      }
//      return Uri.parse("android.resource://" + context.getPackageName() + "/" + soundResourceId);
//    }
//  }

  static Uri getSoundUri(@Nullable String sound) {
    if (sound == null) {
      return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    if (sound.contains("://")) {
      return Uri.parse(sound);
    }

    if (sound.equalsIgnoreCase("default")) {
      return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    int resourceId = getResourceIdByName(sound, "raw");

    if (resourceId == 0 && sound.contains(".")) {
      resourceId = getResourceIdByName(sound.substring(0, sound.lastIndexOf('.')), "raw");
    }

    // If still no resource, default
    if (resourceId == 0) {
      Log.d("NotificationUtils", "Could not find specified sound " + sound);
      return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    return Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + resourceId);
  }

  /**
   * Converts a JS vibration pattern into a valid native pattern
   *
   * @param pattern array of numbers
   * @return long[]
   */
  static long[] parseVibrationPattern(ReadableArray pattern) {
    long[] vibration = new long[pattern.size()];
    for (int i = 0; i < pattern.size(); i++) {
      vibration[i] = (long) pattern.getDouble(i);
    }
    return vibration;
  }

  /**
   * Gets a JS vibration pattern array from a native pattern
   *
   * @param pattern long[]
   * @return WritableArray
   */
  static WritableArray getVibrationPattern(long[] pattern) {
    WritableArray vibrationArray = Arguments.createArray();
    if (pattern != null) {
      for (long val : pattern) {
        vibrationArray.pushDouble(val);
      }
    }
    return vibrationArray;
  }

  /**
   * Converts a native color int into a JS Hexadecimal string
   *
   * @param color native color
   * @return hex code
   */
  static String getColor(int color) {
    return String.format("#%06X", (0xFFFFFF & color));
  }
}
