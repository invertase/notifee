package app.notifee.core.bundles;

import android.os.Bundle;

import androidx.annotation.NonNull;

public class ScheduleBundle {
  private Bundle mScheduleBundle;

  private ScheduleBundle(Bundle bundle) {
    mScheduleBundle = bundle;
  }

  public static ScheduleBundle fromBundle(@NonNull Bundle bundle) {
    return new ScheduleBundle(bundle);
  }

  public int getTimestamp() {
    if (mScheduleBundle.containsKey("timestamp")) {
      return mScheduleBundle.getInt("timestamp");
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
