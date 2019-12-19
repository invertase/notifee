package app.notifee.core;

import android.content.Context;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import com.google.common.util.concurrent.ListenableFuture;

class Worker extends ListenableWorker {
  static final String KEY_WORK_TYPE = "workType";
  static final String WORK_TYPE_BLOCK_STATE_RECEIVER = "app.notifee.core.BlockStateBroadcastReceiver.WORKER";

  private ResolvableFuture<Result> mFuture;

  /**
   * @param appContext   The application {@link Context}
   * @param workerParams Parameters to setup the internal state of this worker
   */
  @Keep
  public Worker(
    @NonNull Context appContext, @NonNull WorkerParameters workerParams
  ) {
    super(appContext, workerParams);
  }

  @Override
  public void onStopped() {
    if (mFuture != null) mFuture.set(Result.failure());
    mFuture = null;
  }

  @NonNull
  @Override
  public ListenableFuture<Result> startWork() {
    mFuture = ResolvableFuture.create();

    String workType = getInputData().getString(KEY_WORK_TYPE);
    if (workType == null) {
      Logger.d("Worker", "received incoming task with no input key type.");
      mFuture.set(Result.success());
      return mFuture;
    }

    if (workType.equals(WORK_TYPE_BLOCK_STATE_RECEIVER)) {
      Logger.d("Worker", "received incoming task with type " + workType);
      BlockStateBroadcastReceiver.doWork(getInputData(), mFuture);
    } else {
      Logger.d("Worker", "unknown work type received: " + workType);
      mFuture.set(Result.success());
    }

    return mFuture;
  }
}
