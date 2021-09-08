package app.notifee.core;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
