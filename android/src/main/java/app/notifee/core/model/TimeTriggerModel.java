package app.notifee.core.model;

import android.os.Bundle;
import androidx.annotation.NonNull;
import java.util.concurrent.TimeUnit;

public class TimeTriggerModel {
  private Bundle mTimeTriggerBundle;
  private static final String TAG = "TimeTriggerModel";

  private TimeTriggerModel(Bundle bundle) {
    mTimeTriggerBundle = bundle;
  }

  public static TimeTriggerModel fromBundle(@NonNull Bundle bundle) {
    return new TimeTriggerModel(bundle);
  }

  public double getDelay() {
    double delay = 0;

    if (mTimeTriggerBundle.containsKey("timestamp")) {
      double timestmap = mTimeTriggerBundle.getDouble("timestamp");
      if (timestmap > 0) {
        delay = Math.round((timestmap - System.currentTimeMillis()) / 1000);
      }
    }

    return delay;
  }

  public TimeUnit getIntervalTimeUnit() {
    TimeUnit timeUnit = TimeUnit.MINUTES;
    if (mTimeTriggerBundle.containsKey("repeatIntervalTimeUnit")) {
      //      String repeatIntervalTimeUnit = mScheduleBundle.getInt("repeatIntervalTimeUnit");
      //      try {
      //        timeUnit = TimeUnit.valueOf(repeatIntervalTimeUnit);
      //      } catch (IllegalArgumentException e) {
      //        Logger.e(
      //            TAG,
      //            "An error occurred whilst trying to convert interval time unit: " +
      // repeatIntervalTimeUnit,
      //            e);
      //      }
    }

    return timeUnit;
  }

  public int getInterval() {
    if (mTimeTriggerBundle.containsKey("repeatInterval")) {
      Double d = mTimeTriggerBundle.getDouble("repeatInterval");
      return d.intValue();
    }

    return -1;
  }
}
