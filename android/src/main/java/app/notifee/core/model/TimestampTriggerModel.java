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
  private AlarmType mAlarmType = AlarmType.SET_EXACT;
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

      Object typeObj = alarmManagerBundle.get("type");

      int type;
      if (typeObj != null) {
        type = ObjectUtils.getInt(typeObj);
      } else {
        type = 2;
      }

      // this is for the deprecated `alarmManager.allowWhileIdle` option
      if (alarmManagerBundle.containsKey("allowWhileIdle")
          && alarmManagerBundle.getBoolean("allowWhileIdle")) {
        type = 3;
      }

      switch (type) {
        case 0:
          mAlarmType = AlarmType.SET;
          break;
        case 1:
          mAlarmType = AlarmType.SET_AND_ALLOW_WHILE_IDLE;
          break;
        // default behavior when alarmManager is true:
        default:
        case 2:
          mAlarmType = AlarmType.SET_EXACT;
          break;
        case 3:
          mAlarmType = AlarmType.SET_EXACT_AND_ALLOW_WHILE_IDLE;
          break;
        case 4:
          mAlarmType = AlarmType.SET_ALARM_CLOCK;
          break;
      }
    } else if (mTimeTriggerBundle.containsKey("allowWhileIdle")) {
      // for dart
      mWithAlarmManager = true;
      mAlarmType = AlarmType.SET_EXACT_AND_ALLOW_WHILE_IDLE;
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

  public enum AlarmType {
    SET,
    SET_AND_ALLOW_WHILE_IDLE,
    SET_EXACT,
    SET_EXACT_AND_ALLOW_WHILE_IDLE,
    SET_ALARM_CLOCK,
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

  public AlarmType getAlarmType() {
    return mAlarmType;
  }

  public String getRepeatFrequency() {
    return mRepeatFrequency;
  }

  public Bundle toBundle() {
    return (Bundle) mTimeTriggerBundle.clone();
  }
}
