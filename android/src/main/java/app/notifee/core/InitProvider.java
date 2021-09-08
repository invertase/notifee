package app.notifee.core;

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
