package io.invertase.notifee;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import io.invertase.notifee.core.NotifeeContextHolder;
import io.invertase.notifee.core.NotifeeWorker;

import static io.invertase.notifee.NotifeeReceiverService.RECEIVER_SERVICE_TASK_KEY;
import static io.invertase.notifee.core.NotifeeCoreUtils.isAppInForeground;

public class NotifeeBroadcastReceiver extends BroadcastReceiver {
  public static final String BROADCAST_RECEIVER_WORKER_KEY = "io.invertase.notifee.BROADCAST_RECEIVER_WORKER_KEY";
  private static final String TAG = "NotifeeBroadcastRec";

  public static Task<Boolean> onWorkManagerTask(Data inputData) {
    boolean blocked = inputData.getBoolean("blocked", false);

    final TaskCompletionSource<Boolean> completionSource = new TaskCompletionSource<>();
    final Task<Boolean> task = completionSource.getTask();


    NotifeeHeadlessJsTasks notifeeTasks = NotifeeHeadlessJsTasks.getInstance(
      NotifeeContextHolder.getApplicationContext()
    );

    HeadlessJsTaskContext headlessJsTaskContext = HeadlessJsTaskContext.getInstance(
      notifeeTasks.getReactContext()
    );

    HeadlessJsTaskConfig headlessJsTaskConfig = new HeadlessJsTaskConfig(
      RECEIVER_SERVICE_TASK_KEY,
      Arguments.createMap(),
      60000,
      false
    );

    UiThreadUtil.runOnUiThread(() -> {
      int taskId = headlessJsTaskContext.startTask(headlessJsTaskConfig);

      notifeeTasks.addListenerForTaskId(taskId, () -> {
        Log.d("ELLIOT", "onTaskFinished " + taskId);
        completionSource.setResult(true);
      });
    });

    return task;
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

    // todo move to worker task?
    if (!isAppInForeground(context)) {
      HeadlessJsTaskService.acquireWakeLockNow(context);
    }

    String taskIdentifier = null;
    Data.Builder workDataBuilder = new Data.Builder();
    workDataBuilder.putString(NotifeeWorker.INPUT_KEY_TYPE, BROADCAST_RECEIVER_WORKER_KEY);

    switch (action) {
      case NotificationManager.ACTION_APP_BLOCK_STATE_CHANGED:
        Log.d(TAG, "Creating worker task for ACTION_APP_BLOCK_STATE_CHANGED");
        boolean blocked = intent.getBooleanExtra(NotificationManager.EXTRA_BLOCKED_STATE, false);

        taskIdentifier = NotificationManager.ACTION_APP_BLOCK_STATE_CHANGED;
        workDataBuilder.putBoolean("blocked", blocked);
        break;
    }

    if (taskIdentifier == null) {
      return;
    }

    OneTimeWorkRequest.Builder builder = new OneTimeWorkRequest.Builder(NotifeeWorker.class)
      .setInputData(workDataBuilder.build());

    WorkManager
      .getInstance(NotifeeContextHolder.getApplicationContext())
      .enqueueUniqueWork(
        taskIdentifier,
        ExistingWorkPolicy.REPLACE,
        builder.build()
      );


//        Bundle bundle = new Bundle();
//        bundle.putBoolean("blocked", blocked);

//        pendingIntent = NotifeeReceiverService.createIntent(ACTION_APP_BLOCK_STATE_CHANGED, new String[]{"app"}, bundle);


//
//          .


//        break;
//      case NotificationManager.ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED:
//        Log.d(TAG, "Creating pending intent for ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED");
//
//        String channelId = intent.getStringExtra(NotificationManager.EXTRA_NOTIFICATION_CHANNEL_ID);
//        pendingIntent = NotifeeReceiverService.createIntent(
//          ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED,
//          new String[]{"channel"},
//          Arguments.toBundle(NotifeeNotificationChannel.getChannel(channelId))
//        );
//        break;
//      case NotificationManager.ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED:
//        Log.d(TAG, "Creating pending intent for ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED");
//
//        String channelGroupId = intent.getStringExtra(NotificationManager.EXTRA_NOTIFICATION_CHANNEL_GROUP_ID);
//        pendingIntent = NotifeeReceiverService.createIntent(
//          ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED,
//          new String[]{"channelGroup"},
//          Arguments.toBundle(NotifeeNotificationChannel.getChannelGroup(channelGroupId))
//        );
//        break;
  }


//    if (pendingIntent != null) {
//      try {
//        pendingIntent.send();
//      } catch (Exception e) {
//        Log.e(TAG, "Failed to send pending intent.");
//        e.printStackTrace();
//      }
//    }
}

