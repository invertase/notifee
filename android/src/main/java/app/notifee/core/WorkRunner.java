package app.notifee.core;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;

import app.notifee.core.events.BlockStateEvent;

class WorkRunner extends Worker {

  static final String INPUT_KEY_TYPE = "type";

  public WorkRunner(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
  }

  @NonNull
  @Override
  public Result doWork() {
    String inputKeyType = getInputData().getString(INPUT_KEY_TYPE);

    if (inputKeyType == null) {
      Logger.d("Worker", "Received incoming task with no input key type.");
      return Result.success();
    }

    boolean success = true;

    final TaskCompletionSource<Void> completionSource = new TaskCompletionSource<>();
    final Task<Void> task = completionSource.getTask();

    BlockStateEvent blockStateEvent = new BlockStateEvent("FOO", getInputData(), (error, result) -> {
      if (error != null) {
        completionSource.setException(error);
      } else {
        completionSource.setResult(null);
      }
    });

    try {
      Tasks.await(task);
    } catch (Exception e) {
      return Result.failure();
    }

    return Result.success();
  }
}
