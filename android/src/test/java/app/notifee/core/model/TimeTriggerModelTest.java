package app.notifee.core.model;

import static org.junit.Assert.assertEquals;

import android.os.Bundle;
import org.junit.Before;
import org.junit.Test;

public class TimeTriggerModelTest {
  private TimeTriggerModel mTimeTriggerModel = null;

  @Before
  public void before() {
    Bundle trigger = new Bundle();
    Bundle triggerComponents = new Bundle();
    triggerComponents.putInt("minute", 1);
    triggerComponents.putInt("hour", 1);
    triggerComponents.putInt("day", 1);
    triggerComponents.putInt("month", 12);
    triggerComponents.putInt("weekday", 3);
    triggerComponents.putInt("weekdayOrdinal", 2);

    // The ISO 8601 week date of the year.
    triggerComponents.putInt("weekOfYear", 24);
    // The week number of the months.
    triggerComponents.putInt("weekOfMonth", 2);

    trigger.putBundle("components", triggerComponents);
    mTimeTriggerModel = TimeTriggerModel.fromBundle(trigger);
  }

  @Test
  public void addition_isCorrect() {
    assertEquals(4, 2 + 2);
  }
}
