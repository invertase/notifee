package app.notifee.core.event;

import androidx.annotation.NonNull;

public class MainComponentEvent {

  private String mainComponent;

  public MainComponentEvent(@NonNull String mainComponent) {
    this.mainComponent = mainComponent;
  }

  public String getMainComponent() {
    return mainComponent;
  }
}
