package app.notifee.core;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class BlockStateBroadcastReceiver extends BroadcastReceiver {

  static final String WORKER_KEY = "app.notifee.core.BlockStateBroadcastReceiver.WORKER";

  @Override
  public void onReceive(Context context, Intent intent) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
      return;
    }

    String action = intent.getAction();

    if (action == null) {
      return;
    }

    // TODO aquire wakelock


    String taskIdentifier = null;
    Data.Builder workDataBuilder = new Data.Builder();
    workDataBuilder.putString(WorkRunner.INPUT_KEY_TYPE, WORKER_KEY);


    switch (action) {
      case NotificationManager.ACTION_APP_BLOCK_STATE_CHANGED:
        boolean blocked = intent.getBooleanExtra(NotificationManager.EXTRA_BLOCKED_STATE, false);
        workDataBuilder.putBoolean("blocked", blocked);
        taskIdentifier = NotificationManager.ACTION_APP_BLOCK_STATE_CHANGED;
        break;
      case "android.app.action.NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED":
        return;
      case "android.app.action.NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED":
        return;
    }

    if (taskIdentifier == null) {
      return;
    }

    OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(WorkRunner.class)
      .setInputData(workDataBuilder.build());

    WorkManager
      .getInstance(ContextHolder.getApplicationContext())
      .enqueueUniqueWork(
        taskIdentifier,
        ExistingWorkPolicy.REPLACE,
        builder.build()
      );
  }
}
