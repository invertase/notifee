package app.notifee.core;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Keep;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker.Result;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

import app.notifee.core.events.BlockStateEvent;

public class BlockStateBroadcastReceiver extends BroadcastReceiver {
  private final static String TAG = "BlockState";

  @Keep
  public BlockStateBroadcastReceiver() {
  }

  static void doWork(
    Data workData, ResolvableFuture<Result> completer
  ) {
    Logger.v(TAG, "doWork STARTED");

    final MethodCallResult<Void> methodCallResult;
    methodCallResult = (error, result) -> {
      if (error != null) {
        Logger.e(TAG, "doWork FAILURE", error);
        completer.set(Result.failure());
      } else {
        Logger.v(TAG, "doWork SUCCESS");
        completer.set(Result.success());
      }
    };

    BlockStateEvent blockStateEvent = new BlockStateEvent(workData.getString("eventType"),
      workData.getString("channelOrGroupId"), workData.getBoolean("blocked", false),
      methodCallResult
    );

    EventBus.post(blockStateEvent);
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

    String uniqueWorkId = action;
    Data.Builder workDataBuilder = new Data.Builder();
    workDataBuilder.putString(Worker.KEY_WORK_TYPE, Worker.WORK_TYPE_BLOCK_STATE_RECEIVER);

    switch (action) {
      case NotificationManager.ACTION_APP_BLOCK_STATE_CHANGED:
        workDataBuilder.putString("eventType", BlockStateEvent.TYPE_APP);
        break;
      case NotificationManager.ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED:
        workDataBuilder.putString("eventType", BlockStateEvent.TYPE_CHANNEL);
        String channelId = intent.getStringExtra(NotificationManager.EXTRA_NOTIFICATION_CHANNEL_ID);
        workDataBuilder.putString("channelOrGroupId", channelId);
        uniqueWorkId += "." + channelId;
        break;
      case NotificationManager.ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED:
        workDataBuilder.putString("eventType", BlockStateEvent.TYPE_CHANNEL_GROUP);
        String channelGroupId = intent
          .getStringExtra(NotificationManager.EXTRA_NOTIFICATION_CHANNEL_GROUP_ID);
        workDataBuilder.putString("channelOrGroupId", channelGroupId);
        uniqueWorkId += "." + channelGroupId;
        break;
      default:
        Logger.d(TAG, "unknown intent action received, ignoring.");
        return;
    }

    workDataBuilder.putBoolean("blocked",
      intent.getBooleanExtra(NotificationManager.EXTRA_BLOCKED_STATE, false)
    );

    OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(Worker.class)
      .setInitialDelay(3, TimeUnit.SECONDS).setInputData(workDataBuilder.build());

    WorkManager.getInstance(ContextHolder.getApplicationContext())
      .enqueueUniqueWork(uniqueWorkId, ExistingWorkPolicy.REPLACE, builder.build());

    Logger.v(TAG, "scheduled new work for unique id " + uniqueWorkId);
  }
}
