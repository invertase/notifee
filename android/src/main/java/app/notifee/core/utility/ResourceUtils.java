package app.notifee.core.utility;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.TypedValue;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import app.notifee.core.ContextHolder;
import app.notifee.core.Logger;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.HashMap;
import java.util.Map;

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
    return resId > 0
        ? new Uri.Builder().scheme(LOCAL_RESOURCE_SCHEME).path(String.valueOf(resId)).build()
        : Uri.EMPTY;
  }

  /**
   * Returns a circular Bitmap from another bitmap. The original bitmap can be any shape.
   *
   * @param bitmap
   * @return Bitmap
   */
  public static Bitmap getCircularBitmap(Bitmap bitmap) {
    Bitmap output;
    Rect srcRect, dstRect;
    float r;
    final int width = bitmap.getWidth();
    final int height = bitmap.getHeight();

    if (width > height) {
      output = Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888);
      int left = (width - height) / 2;
      int right = left + height;
      srcRect = new Rect(left, 0, right, height);
      dstRect = new Rect(0, 0, height, height);
      r = height / 2;
    } else {
      output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
      int top = (height - width) / 2;
      int bottom = top + width;
      srcRect = new Rect(0, top, width, bottom);
      dstRect = new Rect(0, 0, width, width);
      r = width / 2;
    }

    final Canvas canvas = new Canvas(output);

    final int color = Color.RED;
    final Paint paint = new Paint();

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawCircle(r, r, r, paint);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(bitmap, srcRect, dstRect, paint);

    return output;
  }

  /**
   * Returns a Bitmap from any given HTTP image URL, or local resource.
   *
   * @param imageUrl
   * @return Bitmap or null if the image failed to load
   */
  public static ListenableFuture<Bitmap> getImageBitmapFromUrl(
    String imageUrl) {
    Uri imageUri;
    final SettableFuture<Bitmap> bitmapTCS = SettableFuture.create();

    if (!imageUrl.contains("/")) {
      String imageResourceUrl = getImageResourceUrl(imageUrl);
      if (imageResourceUrl == null) {
        bitmapTCS.set(null);
        return bitmapTCS;
      }
      imageUri = getImageSourceUri(imageResourceUrl);
    } else {
      imageUri = getImageSourceUri(imageUrl);
    }

    ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(imageUri).build();

    // TODO(helenaford): handle destroying of fresco after use in background state
    // Needed when the app is killed, and the Fresco hasn't yet been initialized by React Native
    if (!Fresco.hasBeenInitialized()) {
      Logger.w(TAG, "Fresco initializing natively by Notifee");

      // TODO(helenaford): expand on this to initialize with a custom imagePipelineConfig
      Fresco.initialize(ContextHolder.getApplicationContext());
    }

    DataSource<CloseableReference<CloseableImage>> dataSource =
        Fresco.getImagePipeline()
            .fetchDecodedImage(imageRequest, ContextHolder.getApplicationContext());

    dataSource.subscribe(
        new BaseBitmapDataSubscriber() {
          @Override
          protected void onNewResultImpl(@Nullable Bitmap bitmap) {
            bitmapTCS.set(bitmap);
          }

          @Override
          protected void onFailureImpl(
              @NonNull DataSource<CloseableReference<CloseableImage>> dataSource) {
            Logger.e(TAG, "Failed to load an image: " + imageUrl, dataSource.getFailureCause());
            bitmapTCS.set(null);
          }
        },
        CallerThreadExecutor.getInstance());

    return bitmapTCS;
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

    return resourceId > 0
        ? new Uri.Builder()
            .scheme(LOCAL_RESOURCE_SCHEME)
            .path(String.valueOf(resourceId))
            .build()
            .toString()
        : Uri.EMPTY.toString();
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

  /** Attempts to find a resource id by name and type */
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

  public static @Nullable String getSoundName(Uri sound) {
    if (sound == null) return null;
    if (sound.toString().contains("android.resource")) {
      String soundFile = sound.getLastPathSegment();
      try {
        int resourceId = Integer.valueOf(soundFile);
        Logger.e(
            TAG,
            "Loaded sound by resource id. New app builds will fail to play sound. Create a new"
                + " channel to resolve. Issue #341");
        if (resourceId != 0) {
          TypedValue value = new TypedValue();
          Context context = ContextHolder.getApplicationContext();
          context.getResources().getValue(resourceId, value, true);

          CharSequence soundString = value.string;
          if (soundString != null || soundString.length() > 0) {
            return soundString.toString().replace("res/raw/", "");
          }
        }
      } catch (NumberFormatException nfe) {
        // This implies the sound URI last path segment was by file name, not resourceId
        // They were by resourceId prior to issue #341 where we learned that leads to unstable URIs
        // Now we verify the file exists but use the file name from the raw directory
        // We still attempt to resolve by resourceId above to gracefully handle URIs created via our
        // previous behavior
        return soundFile;
      }
    }

    // TODO parse system sounds
    return null;
  }

  public static @Nullable Uri getSoundUri(String sound) {
    Context context = ContextHolder.getApplicationContext();
    if (sound == null) {
      return null;
    } else if (sound.contains("://")) {
      return Uri.parse(sound);
    } else if (sound.equalsIgnoreCase("default")) {
      return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    } else {
      // The API user is attempting to set a sound by file name, verify it exists
      int soundResourceId = getResourceIdByName(sound, "raw");
      if (soundResourceId == 0 && sound.contains(".")) {
        soundResourceId = getResourceIdByName(sound.substring(0, sound.lastIndexOf('.')), "raw");
      }

      if (soundResourceId == 0) {
        return null;
      }

      // Use the actual sound name vs the resource ID, to obtain a stable URI, Issue #341
      return Uri.parse("android.resource://" + context.getPackageName() + "/raw/" + sound);
    }
  }
}
