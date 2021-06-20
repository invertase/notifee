package app.notifee.core.model;

import android.os.Bundle;
import androidx.annotation.NonNull;
import java.util.concurrent.TimeUnit;

public class TimestampTriggerModel {
  private Bundle mTimeTriggerBundle;
  private int mInterval = -1;
  private TimeUnit mTimeUnit = null;
  private Boolean mWithAlarmManager = false;
  private Boolean mAllowWhileIdle = false;
  private String mRepeatFrequency = null;

  public static final String HOURLY = "HOURLY";
  public static final String DAILY = "DAILY";
  public static final String WEEKLY = "WEEKLY";

  private static final int MINUTES_IN_MS = 60 * 1000;
  private static final long HOUR_IN_MS = 60 * MINUTES_IN_MS;
  private static final long DAY_IN_MS = 24 * HOUR_IN_MS;

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
          mRepeatFrequency = HOURLY;
          break;
        case 1:
          mInterval = 1;
          mTimeUnit = TimeUnit.DAYS;
          mRepeatFrequency = DAILY;
          break;
        case 2:
          // weekly, 7 days
          mInterval = 7;
          mTimeUnit = TimeUnit.DAYS;
          mRepeatFrequency = WEEKLY;
          break;
      }
    }

    if (mTimeTriggerBundle.containsKey("alarmManager")) {
      mWithAlarmManager = true;

      Bundle alarmManagerBundle = mTimeTriggerBundle.getBundle("alarmManager");

      if (alarmManagerBundle.containsKey("allowWhileIdle")) {
        mAllowWhileIdle = alarmManagerBundle.getBoolean("allowWhileIdle");
      }
    }
  }

  public static TimestampTriggerModel fromBundle(@NonNull Bundle bundle) {
    return new TimestampTriggerModel(bundle);
  }

  public long getTimestamp() {
    return (long) mTimeTriggerBundle.getDouble("timestamp");
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

  public long getNextTimestamp() {
    long timestamp = getTimestamp();
    switch (mRepeatFrequency) {
      case TimestampTriggerModel.WEEKLY:
        timestamp = timestamp + 7 * DAY_IN_MS;
        break;
      case TimestampTriggerModel.DAILY:
        timestamp = timestamp + DAY_IN_MS;
        break;
      case TimestampTriggerModel.HOURLY:
        timestamp = timestamp + HOUR_IN_MS;
        break;
    }

    return timestamp;
  }

  public int getInterval() {
    return mInterval;
  }

  public TimeUnit getTimeUnit() {
    return mTimeUnit;
  }

  public Boolean getWithAlarmManager() {
    return mWithAlarmManager;
  }

  public Boolean getAllowWhileIdle() {
    return mAllowWhileIdle;
  }

  public String getRepeatFrequency() {
    return mRepeatFrequency;
  }

  public Bundle toBundle() {
    return (Bundle) mTimeTriggerBundle.clone();
  }
}
