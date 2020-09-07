package app.notifee.core.database;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

public class WorkDataRepository {

  private WorkDataDao mWorkDataDao;

  public WorkDataRepository(Application application) {
    NotifeeCoreDatabase db = NotifeeCoreDatabase.getDatabase(application);
    mWorkDataDao = db.wordDao();
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
}
