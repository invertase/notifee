package com.invertase.testing;

import androidx.test.rule.ActivityTestRule;

import com.cavynativereporter.RNCavyNativeReporterModule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class NotifeeTest {

  private final String message;
  private final boolean passed;

  public NotifeeTest(String message, boolean passed) {
    this.message = message;
    this.passed = passed;
  }

  @Parameterized.Parameters(name = "{0}")
  public static Collection<Object[]> getTests() throws Exception {
    ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    activityRule.launchActivity(null);

    Collection<Object[]> tests = new ArrayList<>();
    RNCavyNativeReporterModule.waitForReport(240);

    ArrayList<Object> results =
        Objects.requireNonNull(RNCavyNativeReporterModule.cavyReport.getArray("results"))
            .toArrayList();

    for (int i = 0; i < results.size(); i++) {
      HashMap result = (HashMap) results.get(i);
      tests.add(new Object[] {result.get("message"), result.get("passed")});
    }

    activityRule.finishActivity();

    return tests;
  }

  @Test
  public void jsTest() {
    System.out.println(message);
    assertTrue(message, passed);
  }
}
