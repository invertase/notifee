package app.notifee.core;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

class WorkRunner extends Worker {
  static final String KEY_WORK_TYPE = "workType";
  static final String WORK_TYPE_BLOCK_STATE_RECEIVER = "app.notifee.core.BlockStateBroadcastReceiver.WORKER";

  public WorkRunner(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
  }

  @NonNull
  @Override
  public Result doWork() {
    String workType = getInputData().getString(KEY_WORK_TYPE);
    if (workType == null) {
      Logger.d("Worker", "Received incoming task with no input key type.");
      return Result.success();
    }

    if (workType.equals(WORK_TYPE_BLOCK_STATE_RECEIVER)) {
      return BlockStateBroadcastReceiver.doWork(getInputData());
    }

    Logger.d("WorkerRunner", "unknown work type received: " + workType);
    return Result.success();
  }
}
