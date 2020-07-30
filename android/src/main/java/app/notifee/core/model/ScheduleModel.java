package app.notifee.core.model;

import android.os.Bundle;
import androidx.annotation.NonNull;
import app.notifee.core.Logger;
import java.util.concurrent.TimeUnit;

public class ScheduleModel {
  private Bundle mScheduleBundle;
  private static final String TAG = "ScheduleModel";

  private ScheduleModel(Bundle bundle) {
    mScheduleBundle = bundle;
  }

  public static ScheduleModel fromBundle(@NonNull Bundle bundle) {
    return new ScheduleModel(bundle);
  }

  public double getDelay() {
    double delay = 0;

    if (mScheduleBundle.containsKey("timestamp")) {
      double timestmap = mScheduleBundle.getDouble("timestamp");
      if (timestmap > 0) {
        delay = Math.round((timestmap - System.currentTimeMillis()) / 1000);
      }
    }

    return delay;
  }

  public TimeUnit getIntervalTimeUnit() {
    TimeUnit timeUnit = TimeUnit.MINUTES;
    if (mScheduleBundle.containsKey("invervalTimeUnit")) {
      String invervalTimeUnit = mScheduleBundle.getString("invervalTimeUnit");
      try {
        timeUnit = TimeUnit.valueOf(invervalTimeUnit);
      } catch (IllegalArgumentException e) {
        Logger.e(
            TAG,
            "An error occurred whilst trying to convert interval time unit: " + invervalTimeUnit,
            e);
      }
    }

    return timeUnit;
  }

  public int getInterval() {
    if (mScheduleBundle.containsKey("interval")) {
      return mScheduleBundle.getInt("interval");
    }

    return -1;
  }
}
