package app.notifee.core.model;

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

import android.os.Bundle;
import androidx.annotation.NonNull;
import app.notifee.core.utility.ObjectUtils;
import java.util.concurrent.TimeUnit;

public class TimestampTriggerModel {
  private Bundle mTimeTriggerBundle;
  private int mInterval = -1;
  private TimeUnit mTimeUnit = null;
  private Boolean mWithAlarmManager = false;
  private Boolean mAllowWhileIdle = false;
  private String mRepeatFrequency = null;
  private Long mTimestamp = null;

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
      int repeatFrequency = ObjectUtils.getInt(mTimeTriggerBundle.get("repeatFrequency"));
      mTimestamp = ObjectUtils.getLong(mTimeTriggerBundle.get("timestamp"));

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
    } else if (mTimeTriggerBundle.containsKey("allowWhileIdle")) {
      mWithAlarmManager = true;
      mAllowWhileIdle = mTimeTriggerBundle.getBoolean("allowWhileIdle");
    }
  }

  public static TimestampTriggerModel fromBundle(@NonNull Bundle bundle) {
    return new TimestampTriggerModel(bundle);
  }

  public long getTimestamp() {
    return mTimestamp;
  }

  public long getDelay() {
    long delay = 0;

    if (mTimeTriggerBundle.containsKey("timestamp")) {
      long timestamp = ObjectUtils.getLong(mTimeTriggerBundle.get("timestamp"));
      if (timestamp > 0) {
        delay = Math.round((timestamp - System.currentTimeMillis()) / 1000);
      }
    }

    return delay;
  }

  public void setNextTimestamp() {
    // Skip for non-repeating triggers
    if (mRepeatFrequency == null) {
      return;
    }

    long timestamp = getTimestamp();
    long interval = 0;

    switch (mRepeatFrequency) {
      case TimestampTriggerModel.WEEKLY:
        interval = 7 * DAY_IN_MS;
        break;
      case TimestampTriggerModel.DAILY:
        interval = DAY_IN_MS;
        break;
      case TimestampTriggerModel.HOURLY:
        interval = HOUR_IN_MS;
        break;
    }

    // prevent alarm manager notification firing straight away
    while (timestamp < System.currentTimeMillis()) {
      timestamp += interval;
    }

    this.mTimestamp = timestamp;
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
