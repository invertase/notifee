package com.notifee.testing;

import com.facebook.react.ReactActivity;
import io.invertase.notifee.NotifeeApiModule;

public class MainActivity extends ReactActivity {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return NotifeeApiModule.getMainComponent("testing");
  }
}
