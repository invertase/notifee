package app.notifee.core.database;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import app.notifee.core.model.NotificationModel;
import app.notifee.core.utility.ObjectUtils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

public class WorkDataRepository {
  private WorkDataDao mWorkDataDao;
  private static WorkDataRepository mInstance;

  public static @NonNull WorkDataRepository getInstance(@NonNull Context context) {
    synchronized (WorkDataRepository.class) {
      if (mInstance == null) {
        mInstance = new WorkDataRepository(context);
      }

      return mInstance;
    }
  }

  public WorkDataRepository(Context context) {
    NotifeeCoreDatabase db = NotifeeCoreDatabase.getDatabase(context);
    mWorkDataDao = db.wordDao();
  }

  public void insert(WorkDataEntity workData) {
    NotifeeCoreDatabase.databaseWriteExecutor.execute(
        () -> {
          mWorkDataDao.insert(workData);
        });
  }

  public Task<WorkDataEntity> getWorkDataById(String id) {
    return Tasks.call(
        NotifeeCoreDatabase.databaseWriteExecutor, () -> mWorkDataDao.getWorkDataById(id));
  }

  public void deleteById(String id) {
    NotifeeCoreDatabase.databaseWriteExecutor.execute(
        () -> {
          mWorkDataDao.deleteById(id);
        });
  }

  public void deleteAll() {
    NotifeeCoreDatabase.databaseWriteExecutor.execute(
        () -> {
          mWorkDataDao.deleteAll();
        });
  }

  public static void insertTriggerNotification(
      NotificationModel notificationModel, Bundle triggerBundle) {
    WorkDataEntity workData =
        new WorkDataEntity(
            notificationModel.getId(),
            ObjectUtils.bundleToBytes(notificationModel.toBundle()),
            ObjectUtils.bundleToBytes(triggerBundle));

    mInstance.insert(workData);
  }
}
