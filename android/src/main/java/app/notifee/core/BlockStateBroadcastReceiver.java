package app.notifee.core;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker.Result;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;

import app.notifee.core.events.BlockStateEvent;

public class BlockStateBroadcastReceiver extends BroadcastReceiver {
  static Result doWork(Data workData) {
    final TaskCompletionSource<Void> completionSource = new TaskCompletionSource<>();
    final Task<Void> task = completionSource.getTask();

    BlockStateEvent blockStateEvent = new BlockStateEvent(workData.getString("eventType"),
      workData.getString("channelOrGroupId"), workData.getBoolean("blocked", false),
      (error, result) -> {
        if (error != null) {
          completionSource.setException(error);
        } else {
          completionSource.setResult(null);
        }
      }
    );

    EventBus.post(blockStateEvent);

    try {
      Tasks.await(task);
    } catch (Exception e) {
      return Result.failure();
    }

    return Result.success();
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
      return;
    }

    String action = intent.getAction();
    if (action == null) {
      return;
    }

    Data.Builder workDataBuilder = new Data.Builder();
    workDataBuilder.putString(WorkRunner.KEY_WORK_TYPE, WorkRunner.WORK_TYPE_BLOCK_STATE_RECEIVER);

    switch (action) {
      case NotificationManager.ACTION_APP_BLOCK_STATE_CHANGED:
        workDataBuilder.putString("eventType", BlockStateEvent.TYPE_APP);
        break;
      case NotificationManager.ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED:
        workDataBuilder.putString("eventType", BlockStateEvent.TYPE_CHANNEL);
        workDataBuilder.putString("channelOrGroupId",
          intent.getStringExtra(NotificationManager.EXTRA_NOTIFICATION_CHANNEL_ID)
        );
        break;
      case NotificationManager.ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED:
        workDataBuilder.putString("eventType", BlockStateEvent.TYPE_CHANNEL_GROUP);
        workDataBuilder.putString("channelOrGroupId",
          intent.getStringExtra(NotificationManager.EXTRA_NOTIFICATION_CHANNEL_GROUP_ID)
        );
        break;
      default:
        Logger.d(BlockStateBroadcastReceiver.class.getName(),
          "unknown intent action received, ignoring."
        );
        return;
    }

    workDataBuilder.putBoolean("blocked",
      intent.getBooleanExtra(NotificationManager.EXTRA_BLOCKED_STATE, false)
    );

    OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(WorkRunner.class)
      .setInputData(workDataBuilder.build());

    WorkManager.getInstance(ContextHolder.getApplicationContext())
      .enqueueUniqueWork(action, ExistingWorkPolicy.REPLACE, builder.build());
  }
}
