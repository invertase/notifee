package io.invertase.notifee;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.views.imagehelper.ImageSource;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;

import java.util.Objects;

import javax.annotation.Nonnull;

import io.invertase.notifee.core.NotifeeLogger;

import static io.invertase.notifee.NotifeeNotification.NOTIFICATION_BUILD_EXECUTOR;
import static io.invertase.notifee.core.NotifeeContextHolder.getApplicationContext;

public class NotifeeUtils {

  /**
   * Returns a Bitmap from any given HTTP image URL, or local resource.
   *
   * @param imageUrl
   * @return Bitmap or null if the image failed to load
   */
  public static Task<Bitmap> getImageBitmapFromUrl(String imageUrl) {
    ImageSource imageSource;
    final TaskCompletionSource<Bitmap> bitmapTCS = new TaskCompletionSource<>();
    Task<Bitmap> bitmapTask = bitmapTCS.getTask();

    // supports for resources by name e.g. 'ic_launcher', as the ids of the resources would be
    // unknown in JavaScript code
    if (!imageUrl.contains("/")) {
      String imageResourceUrl = getImageResourceUrl(imageUrl);
      if (imageResourceUrl == null) {
        bitmapTCS.setResult(null);
        return bitmapTask;
      }
      imageSource = new ImageSource(getApplicationContext(), imageResourceUrl);
    } else {
      imageSource = new ImageSource(getApplicationContext(), imageUrl);
    }

    ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(imageSource.getUri()).build();
    DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline().fetchDecodedImage(
      imageRequest,
      getApplicationContext()
    );

    dataSource.subscribe(new BaseBitmapDataSubscriber() {
      @Override
      protected void onNewResultImpl(@javax.annotation.Nullable Bitmap bitmap) {
        bitmapTCS.setResult(bitmap);
      }

      @Override
      protected void onFailureImpl(@Nonnull DataSource<CloseableReference<CloseableImage>> dataSource) {
        NotifeeLogger.e(
          "Utils",
          "Failed to load an image: " + imageUrl,
          dataSource.getFailureCause()
        );
        bitmapTCS.setResult(null);
      }
    }, CallerThreadExecutor.getInstance());

    return bitmapTask;
  }

  /**
   * Returns a resource path for a local resource
   * @param icon
   * @return
   */
  private static String getImageResourceUrl(String icon) {
    int resourceId = getResourceIdByName(icon, "mipmap");

    if (resourceId == 0) {
      resourceId = getResourceIdByName(icon, "drawable");
    }

    if (resourceId == 0) {
      return null;
    }

    return "res:///" + resourceId;
  }

  /**
   * Gets a resource ID by name.
   *
   * @param resourceName
   * @return integer or 0 if not found
   */
  public static int getImageResourceId(String resourceName) {
    int resourceId = getResourceIdByName(resourceName, "mipmap");
    if (resourceId == 0) {
      resourceId = getResourceIdByName(resourceName, "drawable");
    }

    return resourceId;
  }


  /**
   * Attempts to find a resource id by name and type
   */
  private static int getResourceIdByName(String name, String type) {
    String packageName = getApplicationContext().getPackageName();
    return getApplicationContext().getResources().getIdentifier(name, type, packageName);
  }

  /**
   *
   *
   * @param context
   * @param uri
   * @return
   */
  static String getFileName(Context context, Uri uri) {
    String result = null;
    if (uri.getScheme() != null && uri.getScheme().equals("content")) {
      Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

      try {
        if (cursor != null && cursor.moveToFirst()) {
          result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
        }
      } catch (Exception e) {
        // ignore
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

  /**
   * Gets a Uri to a local sound file on the device.
   *
   * @param sound use `default` to return the device default sound
   * @return
   */
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
      NotifeeLogger.d("Utils", "Could not find specified sound " + sound);
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

  /**
   * Converts a person bundle from JS into a Person
   *
   * @param personBundle
   * @return
   */
  public static Task<Person> getPerson(Bundle personBundle) {
    return Tasks.call(NOTIFICATION_BUILD_EXECUTOR, () -> {
      Person.Builder personBuilder = new Person.Builder();

      personBuilder.setName(personBundle.getString("name"));

      if (personBundle.containsKey("id")) {
        personBuilder.setKey(personBundle.getString("id"));
      }

      if (personBundle.containsKey("bot")) {
        personBuilder.setBot(personBundle.getBoolean("bot"));
      }

      if (personBundle.containsKey("important")) {
        personBuilder.setImportant(personBundle.getBoolean("important"));
      }

      if (personBundle.containsKey("icon")) {
        Bitmap icon = Tasks.await(
          getImageBitmapFromUrl(
            Objects.requireNonNull(personBundle.getString("icon"))
          )
        );

        if (icon != null) {
          personBuilder.setIcon(IconCompat.createWithAdaptiveBitmap(icon));
        }
      }


      if (personBundle.containsKey("uri")) {
        personBuilder.setUri(personBundle.getString("uri"));
      }

      return personBuilder.build();
    });
  }

  /**
   * Gets the main activity class name for this application
   */
  static @Nullable
  String getMainActivityClassName() {
    String packageName = getApplicationContext().getPackageName();
    Intent launchIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);

    if (launchIntent == null || launchIntent.getComponent() == null) {
      return null;
    }

    return launchIntent.getComponent().getClassName();
  }

  /**
   * Returns a Class instance for a given class name
   *
   * @param className
   * @return returns a Class instance or null if not found
   */
  static @Nullable
  Class getClassForName(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  /**
   * @param launchActivity
   * @return
   */
  static Class getLaunchActivity(@Nullable String launchActivity) {
    String activity;

    if (launchActivity != null && !launchActivity.equals("default")) {
      activity = launchActivity;
    } else {
      activity = getMainActivityClassName();
    }

    if (activity == null) {
      throw new InvalidNotificationParameterException(
        InvalidNotificationParameterException.ACTIVITY_NOT_FOUND,
        "Launch Activity for notification could not be found."
      );
    }

    Class launchActivityClass = getClassForName(activity);

    if (launchActivityClass == null) {
      throw new InvalidNotificationParameterException(
        InvalidNotificationParameterException.ACTIVITY_NOT_FOUND,
        String.format("Launch Activity for notification does not exist ('%s').", launchActivity)
      );
    }

    return launchActivityClass;
  }
}
