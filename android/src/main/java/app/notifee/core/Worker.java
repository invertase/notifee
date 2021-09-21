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

import android.content.Context;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.google.common.util.concurrent.ListenableFuture;

public class Worker extends ListenableWorker {
  static final String KEY_WORK_TYPE = "workType";
  static final String KEY_IS_PRIMARY = "isPrimaryKey";
  static final String WORK_TYPE_BLOCK_STATE_RECEIVER =
      "app.notifee.core.BlockStateBroadcastReceiver.WORKER";
  static final String WORK_TYPE_NOTIFICATION_TRIGGER =
      "app.notifee.core.NotificationManager.TRIGGER";
  static final String WORK_REQUEST_ONE_TIME = "OneTime";
  static final String WORK_REQUEST_PERIODIC = "Periodic";
  static final String KEY_WORK_REQUEST = "workRequestType";
  private static final String TAG = "Worker";

  private CallbackToFutureAdapter.Completer<Result> mCompleter;

  /**
   * @param appContext The application {@link Context}
   * @param workerParams Parameters to setup the internal state of this worker
   */
  @Keep
  public Worker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
    super(appContext, workerParams);
  }

  @Override
  public void onStopped() {
    if (mCompleter != null) mCompleter.set(Result.failure());
    mCompleter = null;
  }

  @NonNull
  @Override
  public ListenableFuture<Result> startWork() {
    return CallbackToFutureAdapter.getFuture(
        completer -> {
          mCompleter = completer;
          String workType = getInputData().getString(KEY_WORK_TYPE);
          if (workType == null) {
            Logger.d(TAG, "received task with no input key type.");
            completer.set(Result.success());
            return "Worker.startWork operation cancelled - no input.";
          }

          switch (workType) {
            case WORK_TYPE_BLOCK_STATE_RECEIVER:
              Logger.d(TAG, "received task with type " + workType);
              BlockStateBroadcastReceiver.doWork(getInputData(), completer);
              break;
            case WORK_TYPE_NOTIFICATION_TRIGGER:
              NotificationManager.doScheduledWork(getInputData(), completer);
              break;
            default:
              Logger.d(TAG, "unknown work type received: " + workType);
              completer.set(Result.success());
              return "Worker.startWork operation cancelled - unknown work type.";
          }

          return "Worker.startWork operation created successfully.";
        });
  }
}
