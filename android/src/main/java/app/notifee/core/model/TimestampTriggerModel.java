package app.notifee.core.model;

import android.os.Bundle;
import androidx.annotation.NonNull;
import java.util.concurrent.TimeUnit;

public class TimestampTriggerModel {
  private Bundle mTimeTriggerBundle;
  private int mInterval = -1;
  private TimeUnit mTimeUnit = null;

  private static final String TAG = "TimeTriggerModel";

  private TimestampTriggerModel(Bundle bundle) {
    mTimeTriggerBundle = bundle;

    // set initial values
    TimeUnit timeUnit = null;
    if (mTimeTriggerBundle.containsKey("repeatFrequency")) {
      Double d = mTimeTriggerBundle.getDouble("repeatFrequency");
      int repeatFrequency = d.intValue();

      switch (repeatFrequency) {
        case -1:
          // default value for one-time trigger
          break;
        case 0:
          mInterval = 1;
          mTimeUnit = TimeUnit.HOURS;
          break;
        case 1:
          mInterval = 1;
          mTimeUnit = TimeUnit.DAYS;
          break;
        case 2:
          // weekly, 7 days
          mInterval = 7;
          mTimeUnit = TimeUnit.DAYS;
          break;
      }
    }
  }

  public static TimestampTriggerModel fromBundle(@NonNull Bundle bundle) {
    return new TimestampTriggerModel(bundle);
  }

  public long getDelay() {
    long delay = 0;

    if (mTimeTriggerBundle.containsKey("timestamp")) {
      long timestamp = (long) mTimeTriggerBundle.getDouble("timestamp");
      if (timestamp > 0) {
        delay = Math.round((timestamp - System.currentTimeMillis()) / 1000);
      }
    }

    return delay;
  }

  public int getInterval() {
    return mInterval;
  }

  public TimeUnit getTimeUnit() {
    return mTimeUnit;
  }
}
