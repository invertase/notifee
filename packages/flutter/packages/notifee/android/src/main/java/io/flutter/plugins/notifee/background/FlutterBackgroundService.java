package io.flutter.plugins.notifee.background;

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
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.FlutterShellArgs;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FlutterBackgroundService extends JobIntentService {
  private static final String TAG = "FLTNotifeeService";

  private static final List<Intent> messagingQueue =
      Collections.synchronizedList(new LinkedList<>());

  /** Background Dart execution context. */
  private static BackgroundExecutor flutterBackgroundExecutor;

  /** Schedule the message to be handled by the {@link FlutterBackgroundService}. */
  public static void enqueueMessageProcessing(Context context, Intent messageIntent) {
    Object message = messageIntent.getExtras().get("notification");

    enqueueWork(
        context, FlutterBackgroundService.class, BackgroundUtils.JOB_ID, messageIntent, true);
  }

  public static void startBackgroundIsolate(long callbackHandle, FlutterShellArgs shellArgs) {
    if (flutterBackgroundExecutor != null) {
      Log.w(TAG, "Attempted to start a duplicate background isolate. Returning...");
      return;
    }
    flutterBackgroundExecutor = new BackgroundExecutor();
    flutterBackgroundExecutor.startBackgroundIsolate(callbackHandle, shellArgs);
  }

  /* package */
  static void onInitialized() {
    Log.i(TAG, "NotifeeBackgroundService started!");
    synchronized (messagingQueue) {
      // Handle all the message events received before the Dart isolate was
      // initialized, then clear the queue.
      for (Intent intent : messagingQueue) {
        flutterBackgroundExecutor.executeDartCallbackInBackgroundIsolate(intent, null);
      }
      messagingQueue.clear();
    }
  }

  public static void initialize(
      long callbackHandle, long userCallbackHandle, FlutterShellArgs shellArgs) {
    BackgroundExecutor.setCallbackDispatcher(callbackHandle);
    BackgroundExecutor.setUserCallbackHandle(userCallbackHandle);
    startBackgroundIsolate(callbackHandle, shellArgs);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    if (flutterBackgroundExecutor == null) {
      flutterBackgroundExecutor = new BackgroundExecutor();
    }
    flutterBackgroundExecutor.startBackgroundIsolate();
  }

  @Override
  protected void onHandleWork(@NonNull final Intent intent) {
    if (!flutterBackgroundExecutor.isDartBackgroundHandlerRegistered()) {
      Log.w(
          TAG,
          "A background message could not be handled in Dart as no onBackgroundMessage handler has"
              + " been registered.");
      return;
    }

    // If we're in the middle of processing queued messages, add the incoming
    // intent to the queue and return.
    synchronized (messagingQueue) {
      if (flutterBackgroundExecutor.isNotRunning()) {
        Log.i(TAG, "Service has not yet started, messages will be queued.");
        messagingQueue.add(intent);
        return;
      }
    }

    // There were no pre-existing callback requests. Execute the callback
    // specified by the incoming intent.
    final CountDownLatch latch = new CountDownLatch(1);
    new Handler(getMainLooper())
        .post(
            () -> flutterBackgroundExecutor.executeDartCallbackInBackgroundIsolate(intent, latch));

    try {
      latch.await();
    } catch (InterruptedException ex) {
      Log.i(TAG, "Exception waiting to execute Dart callback", ex);
    }
  }
}
