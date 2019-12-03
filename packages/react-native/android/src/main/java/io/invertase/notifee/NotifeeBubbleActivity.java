package io.invertase.notifee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.react.ReactActivity;

// TODO Handle errors when activity paused/killed
// Pausing an activity that is not the current activity, this is incorrect! Current activity: MainActivity Paused activity: NotifeeBubbleActivity
public class NotifeeBubbleActivity extends ReactActivity {

  @Override
  protected String getMainComponentName() {
    return "bubble";
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
}
