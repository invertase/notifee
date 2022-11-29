/*
 * Copyright (c) 2016-present Invertase Limited
 */

package io.invertase.notifee;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import app.notifee.core.InitProvider;
import app.notifee.core.Notifee;
import com.facebook.react.modules.systeminfo.ReactNativeVersion;
import java.util.Map;

public class NotifeeInitProvider extends InitProvider {
  @Override
  public boolean onCreate() {
    boolean onCreate = super.onCreate();

    Notifee.initialize(new NotifeeEventSubscriber());
    return onCreate;
  }

  private String getApplicationVersionString() {
    String version = "unknown";
    Context context = this.getContext();
    if (context != null) {
      PackageManager pm = context.getPackageManager();
      try {
        PackageInfo pInfo = pm.getPackageInfo(context.getPackageName(), 0);
        version = pInfo.versionName;
      } catch (Exception e) {
        // is there anything useful to log the unbelievably unexpected inability to get package
        // info?
      }
    }

    return version;
  }

  private String getReactNativeVersionString() {
    Map<String, Object> versionMap = ReactNativeVersion.VERSION;
    int major = (int) versionMap.get("major");
    int minor = (int) versionMap.get("minor");
    int patch = (int) versionMap.get("patch");
    String prerelease = (String) versionMap.get("prerelease");

    String versionName = major + "." + minor + "." + patch;
    if (prerelease != null) {
      versionName += "." + prerelease;
    }

    return versionName;
  }
}
