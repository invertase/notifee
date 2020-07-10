package app.notifee.core;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@KeepForSdk
public class InitProvider extends ContentProvider {
  private static final String PROVIDER_AUTHORITY = "notifee-init-provider";

  @Override
  public void attachInfo(Context context, ProviderInfo info) {
    if (info != null && !info.authority.endsWith(InitProvider.PROVIDER_AUTHORITY)) {
      throw new IllegalStateException(
          "Incorrect provider authority in manifest. This is most likely due to a missing "
              + "applicationId variable in application's build.gradle.");
    }

    super.attachInfo(context, info);
  }

  @CallSuper
  @Override
  public boolean onCreate() {
    if (ContextHolder.getApplicationContext() == null) {
      Context context = getContext();
      if (context != null && context.getApplicationContext() != null) {
        context = context.getApplicationContext();
      }
      ContextHolder.setApplicationContext(context);
    }

    return false;
  }

  @Nullable
  @Override
  public Cursor query(
      @NonNull Uri uri,
      String[] projection,
      String selection,
      String[] selectionArgs,
      String sortOrder) {
    return null;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, ContentValues values) {
    return null;
  }

  @Override
  public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(
      @NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return 0;
  }
}
