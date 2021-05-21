package com.example;

import com.facebook.react.ReactActivity;
// Import the NotifeeApiModule
import io.invertase.notifee.NotifeeApiModule;

public class MainActivity extends ReactActivity {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
    @Override
  protected String getMainComponentName() {
    // Used to render custom components when using `pressAction` and `fullScreenAction`
    return NotifeeApiModule.getMainComponent("example");
  }
}
