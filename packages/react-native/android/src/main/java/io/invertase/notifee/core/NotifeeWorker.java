package io.invertase.notifee.core;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.tasks.Tasks;

import java.util.concurrent.TimeUnit;

import static io.invertase.notifee.NotifeeBroadcastReceiver.BROADCAST_RECEIVER_WORKER_KEY;
import static io.invertase.notifee.NotifeeBroadcastReceiver.onWorkManagerTask;

public class NotifeeWorker extends Worker {
  public static final String INPUT_KEY_TYPE = "type";
  private static String WORK_TYPE_LICENSE_CHECK = "license_check";
  private static String WORK_TYPE_SCHEDULED_NOTIFICATION = "scheduled_notification";

  public NotifeeWorker(
    @NonNull Context context,
    @NonNull WorkerParameters params
  ) {
    super(context, params);
  }

  static void scheduleLicenseCheckWork(
    ExistingPeriodicWorkPolicy existingPeriodicWorkPolicy
  ) {
    Constraints constraints = new Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build();

    Data workData = new Data.Builder().putString(INPUT_KEY_TYPE, WORK_TYPE_LICENSE_CHECK).build();

    PeriodicWorkRequest licenseWorkRequest = new PeriodicWorkRequest.Builder(
      NotifeeWorker.class,
      2,
      TimeUnit.DAYS
    ).setInputData(workData).setConstraints(constraints).build();

    WorkManager
      .getInstance(NotifeeContextHolder.getApplicationContext())
      .enqueueUniquePeriodicWork(
        WORK_TYPE_LICENSE_CHECK,
        existingPeriodicWorkPolicy,
        licenseWorkRequest
      );
  }

  static void scheduleNotificationWork() {
    // TODO
  }

  @NonNull
  @Override
  public Result doWork() {
    String inputKeyType = getInputData().getString(INPUT_KEY_TYPE);

    if (inputKeyType == null) {
      Log.d("NotifeeWorker", "Received incoming task with no input key type.");
      return Result.success();
    }

    boolean success = true;

    try {
      switch (inputKeyType) {
        case BROADCAST_RECEIVER_WORKER_KEY:
          success = Tasks.await(onWorkManagerTask(getInputData()));
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
      success = false;
    }

    if (success) {
      return Result.success();
    } else {
      return Result.failure();
    }


//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && inputKeyType.equals(BROADCAST_RECEIVER_WORKER_KEY)) {
//      Tasks.await(onWorkManagerTask(getInputData()));
//    }
//
//    return Result.success();

//    boolean blocked = getInputData().getBoolean("blocked", false);
//
//
//    Log.d("NotifeeWorker", String.valueOf(blocked));
//
//    Bundle bundle = new Bundle();
//    bundle.putBoolean("blocked", blocked);
//    PendingIntent pendingIntent = NotifeeReceiverService.createIntent(ACTION_APP_BLOCK_STATE_CHANGED, new String[]{"app"}, bundle);
//
//    final TaskCompletionSource<Void> completionSource = new TaskCompletionSource<>();
//    final Task<Void> task = completionSource.getTask();
//
//    try {
//      pendingIntent.send(0, (pendingIntent1, intent, resultCode, resultData, resultExtras) -> completionSource.setResult(null), null);
//    } catch (Exception e) {
//      completionSource.setException(e);
//      e.printStackTrace();
//    }
//
//    try {
//      Tasks.await(task);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//
//    return Result.success();

//    String workType = getInputData().getString(INPUT_KEY_TYPE);
//     TODO do actual work e.g. check license or show notification
//    Log.d("NotifeeWorker", workType);
//    Data outputData = new Data.Builder().build();
//    return Result.success(outputData);
  }
}
