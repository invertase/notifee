package io.invertase.notifee;

import com.facebook.react.modules.systeminfo.ReactNativeVersion;

import java.util.Map;

import app.notifee.core.InitProvider;
import app.notifee.core.Notifee;
import app.notifee.core.NotifeeConfig;
import android.content.Context;
import android.content.res.Resources;

public class NotifeeInitProvider extends InitProvider {
  @Override
  public boolean onCreate() {
    boolean onCreate = super.onCreate();
    Context context = super.getContext();

    Resources resources = context.getResources();
    String packageName = context.getPackageName();
    int identifier = resources.getIdentifier("notifee_json", "string", packageName);

    String jsonConfig = "{}";
    if (identifier != 0) {
      jsonConfig = resources.getString(identifier);
    }

    NotifeeConfig.Builder configBuilder = new NotifeeConfig.Builder();
    configBuilder.setJsonConfig(jsonConfig);
    configBuilder.setProductVersion(BuildConfig.VERSION_NAME);
    configBuilder.setFrameworkVersion(getReactNativeVersionString());
    configBuilder.setEventSubscriber(new NotifeeEventSubscriber());

    Notifee.configure(configBuilder.build());
    return onCreate;
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
