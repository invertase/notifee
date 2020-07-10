package app.notifee.core;

import app.notifee.core.interfaces.EventListener;

@KeepForSdk
public class NotifeeConfig {
  private String mProductVersion;
  private String mFrameworkVersion;
  private EventListener mEventSubscriber;

  private NotifeeConfig(
      String mJsonConfig,
      String mProductVersion,
      String mFrameworkVersion,
      EventListener mEventSubscriber) {
    this.mProductVersion = mProductVersion;
    this.mFrameworkVersion = mFrameworkVersion;
    this.mEventSubscriber = mEventSubscriber;
  }

  public String getProductVersion() {
    return mProductVersion;
  }

  public String getFrameworkVersion() {
    return mFrameworkVersion;
  }

  EventListener getEventSubscriber() {
    return mEventSubscriber;
  }

  @KeepForSdk
  public static class Builder {
    private String mJsonConfig;
    private String mProductVersion;
    private String mFrameworkVersion;
    private EventListener mEventSubscriber;

    @KeepForSdk
    public void setProductVersion(String mProductVersion) {
      this.mProductVersion = mProductVersion;
    }

    @KeepForSdk
    public void setFrameworkVersion(String mFrameworkVersion) {
      this.mFrameworkVersion = mFrameworkVersion;
    }

    @KeepForSdk
    public void setEventSubscriber(EventListener mEventSubscriber) {
      this.mEventSubscriber = mEventSubscriber;
    }

    @KeepForSdk
    public NotifeeConfig build() {
      return new NotifeeConfig(mJsonConfig, mProductVersion, mFrameworkVersion, mEventSubscriber);
    }
  }
}
