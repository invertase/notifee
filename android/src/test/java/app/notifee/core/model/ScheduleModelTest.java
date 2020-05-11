package app.notifee.core.model;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScheduleModelTest {
  private ScheduleModel mScheduleModel = null;

  @Before
  public void before() {
    Bundle schedule = new Bundle();
    Bundle scheduleComponents = new Bundle();
    scheduleComponents.putInt("minute", 1);
    scheduleComponents.putInt("hour", 1);
    scheduleComponents.putInt("day", 1);
    scheduleComponents.putInt("month", 12);
    scheduleComponents.putInt("weekday", 3);
    scheduleComponents.putInt("weekdayOrdinal", 2);

    // The ISO 8601 week date of the year.
    scheduleComponents.putInt("weekOfYear", 24);
    // The week number of the months.
    scheduleComponents.putInt("weekOfMonth", 2);


    schedule.putBundle("components", scheduleComponents);
    mScheduleModel = ScheduleModel.fromBundle(schedule);
  }

  @Test
  public void addition_isCorrect() {
    mScheduleModel.getTimestamp();
    assertEquals(4, 2 + 2);
  }
}
