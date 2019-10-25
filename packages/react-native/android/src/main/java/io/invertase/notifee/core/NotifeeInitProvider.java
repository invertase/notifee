package io.invertase.notifee.core;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

public class NotifeeInitProvider extends ContentProvider {
  private static final String PROVIDER_AUTHORITY = "notifee-init-provider";

  @Override
  public void attachInfo(Context context, ProviderInfo info) {
    if (info != null && !info.authority.endsWith(NotifeeInitProvider.PROVIDER_AUTHORITY)) {
      throw new IllegalStateException(
        "Incorrect provider authority in manifest. This is most likely due to a missing "
          + "applicationId variable in application's build.gradle.");
    }
    super.attachInfo(context, info);
  }

  @Override
  @OverridingMethodsMustInvokeSuper
  public boolean onCreate() {
    if (NotifeeContextHolder.getApplicationContext() == null) {
      Context applicationContext = getContext();
      if (applicationContext != null && applicationContext.getApplicationContext() != null) {
        applicationContext = applicationContext.getApplicationContext();
      }
      NotifeeContextHolder.setApplicationContext(applicationContext);
    }

    return false;
  }

  @Nullable
  @Override
  public Cursor query(
    @Nonnull Uri uri,
    String[] projection,
    String selection,
    String[] selectionArgs,
    String sortOrder
  ) {
    return null;
  }

  @Nullable
  @Override
  public String getType(@Nonnull Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@Nonnull Uri uri, ContentValues values) {
    return null;
  }

  @Override
  public int delete(@Nonnull Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(
    @Nonnull Uri uri,
    ContentValues values,
    String selection,
    String[] selectionArgs
  ) {
    return 0;
  }
}
