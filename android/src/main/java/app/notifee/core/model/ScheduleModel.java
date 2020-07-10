package app.notifee.core.model;

import android.os.Bundle;
import androidx.annotation.NonNull;

public class ScheduleModel {
  private Bundle mScheduleBundle;

  private ScheduleModel(Bundle bundle) {
    mScheduleBundle = bundle;
  }

  public static ScheduleModel fromBundle(@NonNull Bundle bundle) {
    return new ScheduleModel(bundle);
  }

  public double getTimestamp() {
    if (mScheduleBundle.containsKey("timestamp")) {
      return mScheduleBundle.getDouble("timestamp");
    }

    return -1;
  }

  public int getInterval() {
    if (mScheduleBundle.containsKey("interval")) {
      return mScheduleBundle.getInt("interval");
    }

    return -1;
  }
}
