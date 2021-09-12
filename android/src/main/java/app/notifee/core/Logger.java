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

import android.util.Log;
import androidx.annotation.NonNull;
import app.notifee.core.event.LogEvent;

public class Logger {
  private static final String TAG = "NOTIFEE";

  private static String tagAndMessage(String tag, String message) {
    return "(" + tag + "): " + message;
  }

  @KeepForSdk
  public static void v(@NonNull String tag, String message) {
    Log.v(TAG, tagAndMessage(tag, message));
  }

  @KeepForSdk
  public static void d(@NonNull String tag, String message) {
    Log.d(TAG, tagAndMessage(tag, message));
  }

  @KeepForSdk
  public static void i(@NonNull String tag, String message) {
    Log.i(TAG, tagAndMessage(tag, message));
  }

  @KeepForSdk
  public static void w(@NonNull String tag, String message) {
    Log.w(TAG, tagAndMessage(tag, message));
  }

  @KeepForSdk
  public static void e(@NonNull String tag, String message, Exception e) {
    Log.e(TAG, tagAndMessage(tag, message), e);
    EventBus.post(new LogEvent(LogEvent.LEVEL_ERROR, tag, message, e));
  }

  @KeepForSdk
  public static void e(@NonNull String tag, String message) {
    Log.e(TAG, tagAndMessage(tag, message));
    EventBus.post(new LogEvent(LogEvent.LEVEL_ERROR, tag, message));
  }

  @KeepForSdk
  public static void e(@NonNull String tag, String message, Throwable throwable) {
    Log.e(TAG, tagAndMessage(tag, message), throwable);
    EventBus.post(new LogEvent(LogEvent.LEVEL_ERROR, tag, message, throwable));
  }
}
