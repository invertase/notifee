package app.notifee.core.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.HashMap;
import java.util.Map;

import app.notifee.core.ContextHolder;
import app.notifee.core.Logger;

public class ResourceUtils {
  private static final String TAG = "ResourceUtils";
  private static final String LOCAL_RESOURCE_SCHEME = "res";
  private static volatile Map<String, Integer> sResourceIdCache;

  public static Map<String, Integer> getResourceIdCache() {
    if (sResourceIdCache == null) {
      synchronized (ResourceUtils.class) {
        if (sResourceIdCache == null) {
          sResourceIdCache = new HashMap<>();
        }
      }
    }
    return sResourceIdCache;
  }

  public static Uri getImageSourceUri(String source) {
    try {
      Uri uri = Uri.parse(source);
      // verify a scheme is set,
      // so that relative uri (used by static resources) are not handled
      if (uri.getScheme() == null) {
        return getResourceDrawableUri(source);
      }

      return uri;
    } catch (Exception e) {
      return getResourceDrawableUri(source);
    }
  }

  public static Uri getResourceDrawableUri(@Nullable String name) {
    int resId = getResourceIdByName(name, "drawable");
    return resId > 0 ? new Uri.Builder().scheme(LOCAL_RESOURCE_SCHEME).path(String.valueOf(resId))
      .build() : Uri.EMPTY;
  }


  /**
   * Returns a Bitmap from any given HTTP image URL, or local resource.
   *
   * @param imageUrl
   * @return Bitmap or null if the image failed to load
   */
  public static Task<Bitmap> getImageBitmapFromUrl(String imageUrl) {
    Uri imageUri;
    final TaskCompletionSource<Bitmap> bitmapTCS = new TaskCompletionSource<>();
    Task<Bitmap> bitmapTask = bitmapTCS.getTask();

    if (!imageUrl.contains("/")) {
      String imageResourceUrl = getImageResourceUrl(imageUrl);
      if (imageResourceUrl == null) {
        bitmapTCS.setResult(null);
        return bitmapTask;
      }
      imageUri = getImageSourceUri(imageResourceUrl);
    } else {
      imageUri = getImageSourceUri(imageUrl);
    }

    ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(imageUri).build();

    DataSource<CloseableReference<CloseableImage>> dataSource = Fresco.getImagePipeline()
      .fetchDecodedImage(imageRequest, ContextHolder.getApplicationContext());

    dataSource.subscribe(new BaseBitmapDataSubscriber() {
      @Override
      protected void onNewResultImpl(@Nullable Bitmap bitmap) {
        bitmapTCS.setResult(bitmap);
      }

      @Override
      protected void onFailureImpl(
        @NonNull DataSource<CloseableReference<CloseableImage>> dataSource
      ) {
        Logger.e(TAG, "Failed to load an image: " + imageUrl, dataSource.getFailureCause());
        bitmapTCS.setResult(null);
      }
    }, CallerThreadExecutor.getInstance());

    return bitmapTask;
  }

  /**
   * Returns a resource path for a local resource
   *
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

    return resourceId > 0 ? new Uri.Builder().scheme(LOCAL_RESOURCE_SCHEME)
      .path(String.valueOf(resourceId)).build().toString() : Uri.EMPTY.toString();
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
    if (name == null || name.isEmpty()) {
      return 0;
    }

    name = name.toLowerCase().replace("-", "_");

    String key = name + "_" + type;

    synchronized (ResourceUtils.class) {
      if (getResourceIdCache().containsKey(key)) {
        // noinspection ConstantConditions
        return getResourceIdCache().get(key);
      }

      Context context = ContextHolder.getApplicationContext();
      String packageName = context.getPackageName();

      int id = context.getResources().getIdentifier(name, type, packageName);
      getResourceIdCache().put(key, id);
      return id;
    }
  }

  static String getFileNameFromUri(Uri uri) {
    String result = null;
    if (uri.getScheme() != null && uri.getScheme().equals("content")) {
      Context context = ContextHolder.getApplicationContext();
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

}
