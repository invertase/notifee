package com.notifee.testing;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.test.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Assert;
import org.junit.Test;

public class GetNotificationSettingsTest {

  private final long TIMEOUT = 5000;

  @Test
  public void testNotificationSettingsCorrectResponse() throws UiObjectNotFoundException, InterruptedException {
    final UiDevice device = UiDevice.getInstance(getInstrumentation());
    device.pressHome();

    //Launch Notifee-dev's app settings
    Context context = InstrumentationRegistry.getContext();
    Intent appSettingsIntent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    appSettingsIntent.setData(Uri.parse("package:com.notifee.testing"));
    context.startActivity(appSettingsIntent);

    //Disable Notification
    try {
      device.findObject(new UiSelector().text("App notifications")).clickAndWaitForNewWindow();
    }  catch (UiObjectNotFoundException exception) {
      //Skip if cannot found
    }
    try {
      device.findObject(new UiSelector().text("On")).click();
    }  catch (UiObjectNotFoundException exception) {
      //Skip if already off
    }
    device.waitForIdle();

    //Launch App
    Intent appIntent = context.getPackageManager().getLaunchIntentForPackage("com.notifee.testing");
    context.startActivity(appIntent);

    //Check Notification Settings
    device.wait(Until.hasObject(By.text("GET NOTIFICATION SETTINGS")), TIMEOUT);
    device.findObject(new UiSelector().text("GET NOTIFICATION SETTINGS")).click();
    device.wait(Until.hasObject(By.text("Notification Settings")), TIMEOUT);

    device.wait(Until.hasObject(By.text("{\"authorizationStatus\":0,\"iOSSettings\":{\"alert\":1,\"badge\":1,\"criticalAlert\":1,\"showPreviews\":1,\"sound\":1,\"carPlay\":1,\"lockScreen\":1,\"announcement\":1,\"notificationCenter\":1,\"inAppNotificationSettings\":1,\"authorizationStatus\":0}}")), TIMEOUT);

    Assert.assertTrue(device.findObject(new UiSelector().text("{\"authorizationStatus\":0,\"iOSSettings\":{\"alert\":1,\"badge\":1,\"criticalAlert\":1,\"showPreviews\":1,\"sound\":1,\"carPlay\":1,\"lockScreen\":1,\"announcement\":1,\"notificationCenter\":1,\"inAppNotificationSettings\":1,\"authorizationStatus\":0}}")).exists());

    device.findObject(new UiSelector().text("CANCEL")).click();

    //Repeat the step for enabled state
    context.startActivity(appSettingsIntent);
    device.findObject(new UiSelector().text("Off")).click();

    context.startActivity(appIntent);

    device.wait(Until.hasObject(By.text("GET NOTIFICATION SETTINGS")), TIMEOUT);
    device.findObject(new UiSelector().text("GET NOTIFICATION SETTINGS")).click();
    device.wait(Until.hasObject(By.text("Notification Settings")), TIMEOUT);
    Assert.assertTrue(device.findObject(new UiSelector().text("{\"authorizationStatus\":1,\"iOSSettings\":{\"alert\":1,\"badge\":1,\"criticalAlert\":1,\"showPreviews\":1,\"sound\":1,\"carPlay\":1,\"lockScreen\":1,\"announcement\":1,\"notificationCenter\":1,\"inAppNotificationSettings\":1,\"authorizationStatus\":1}}")).exists());
    device.waitForIdle();
    device.findObject(new UiSelector().text("CANCEL")).click();
  }

}
