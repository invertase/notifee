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
import app.notifee.core.Logger;
import app.notifee.core.utility.ObjectUtils;
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
      return ObjectUtils.getInt(mIntervalTriggerBundle.get("interval"));
    }

    return -1;
  }
}
