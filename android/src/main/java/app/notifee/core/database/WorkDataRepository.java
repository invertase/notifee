package app.notifee.core.database;

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
import android.os.Bundle;
import androidx.annotation.NonNull;
import app.notifee.core.model.NotificationModel;
import app.notifee.core.utility.ObjectUtils;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;

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
    mWorkDataDao = db.workDao();
  }

  public void insert(WorkDataEntity workData) {
    NotifeeCoreDatabase.databaseWriteListeningExecutor.execute(
        () -> {
          mWorkDataDao.insert(workData);
        });
  }

  public ListenableFuture<WorkDataEntity> getWorkDataById(String id) {
    return NotifeeCoreDatabase.databaseWriteListeningExecutor.submit(() -> mWorkDataDao.getWorkDataById(id));
  }

  public ListenableFuture<List<WorkDataEntity>> getAllWithAlarmManager(Boolean withAlarmManager) {
    return
        NotifeeCoreDatabase.databaseWriteListeningExecutor.submit(
        () -> mWorkDataDao.getAllWithAlarmManager(withAlarmManager));
  }

  public ListenableFuture<List<WorkDataEntity>> getAll() {
    return NotifeeCoreDatabase.databaseWriteListeningExecutor.submit( () -> mWorkDataDao.getAll());
  }

  public void deleteById(String id) {
    NotifeeCoreDatabase.databaseWriteListeningExecutor.execute(
        () -> {
          mWorkDataDao.deleteById(id);
        });
  }

  public void deleteByIds(List<String> ids) {
    NotifeeCoreDatabase.databaseWriteListeningExecutor.execute(
        () -> {
          mWorkDataDao.deleteByIds(ids);
        });
  }

  public void deleteAll() {
    NotifeeCoreDatabase.databaseWriteListeningExecutor.execute(
        () -> {
          mWorkDataDao.deleteAll();
        });
  }

  public static void insertTriggerNotification(
      NotificationModel notificationModel, Bundle triggerBundle, Boolean withAlarmManager) {
    WorkDataEntity workData =
        new WorkDataEntity(
            notificationModel.getId(),
            ObjectUtils.bundleToBytes(notificationModel.toBundle()),
            ObjectUtils.bundleToBytes(triggerBundle),
            withAlarmManager);

    mInstance.insert(workData);
  }

  public void update(WorkDataEntity workData) {
    NotifeeCoreDatabase.databaseWriteListeningExecutor.execute(
        () -> {
          mWorkDataDao.update(workData);
        });
  }
}
