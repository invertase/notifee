package app.notifee.core.model;

import android.os.Bundle;
import androidx.annotation.NonNull;
import app.notifee.core.Logger;
import java.util.concurrent.TimeUnit;

public class IntervalTriggerModel {
  private Bundle mIntervalTriggerBundle;
  private static final String TAG = "IntervalTriggerModel";

  private IntervalTriggerModel(Bundle bundle) {
    mIntervalTriggerBundle = bundle;
  }

  public static IntervalTriggerModel fromBundle(@NonNull Bundle bundle) {
    return new IntervalTriggerModel(bundle);
  }

  public TimeUnit getTimeUnit() {
    TimeUnit timeUnit = TimeUnit.SECONDS;
    if (mIntervalTriggerBundle.containsKey("timeUnit")) {
      String repeatIntervalTimeUnit = mIntervalTriggerBundle.getString("timeUnit");
      try {
        timeUnit = TimeUnit.valueOf(repeatIntervalTimeUnit);
      } catch (IllegalArgumentException e) {
        Logger.e(
            TAG,
            "An error occurred whilst trying to convert interval time unit: "
                + repeatIntervalTimeUnit,
            e);
      }
    }

    return timeUnit;
  }

  public int getInterval() {
    if (mIntervalTriggerBundle.containsKey("interval")) {
      Double d = mIntervalTriggerBundle.getDouble("interval");
      return d.intValue();
    }

    return -1;
  }
}
