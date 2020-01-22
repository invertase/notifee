package app.notifee.core.events;

import android.os.Bundle;

import androidx.annotation.Nullable;

import app.notifee.core.KeepForSdk;
import app.notifee.core.MethodCallResult;

@KeepForSdk
public class BlockStateEvent {
  @KeepForSdk
  public final static int TYPE_APP_BLOCKED = 4;

  @KeepForSdk
  public final static int TYPE_CHANNEL_BLOCKED = 5;

  @KeepForSdk
  public final static int TYPE_CHANNEL_GROUP_BLOCKED = 6;

  private int type;

  private boolean blocked;

  private @Nullable
  Bundle channelOrGroupBundle;

  private MethodCallResult<Void> result;

  private boolean completed = false;

  public BlockStateEvent(
    int type, @Nullable Bundle channelOrGroupBundle, boolean blocked, MethodCallResult<Void> result
  ) {
    this.type = type;
    this.result = result;
    this.blocked = blocked;
    this.channelOrGroupBundle = channelOrGroupBundle;
  }

  @KeepForSdk
  public void setCompletionResult() {
    if (!completed) {
      completed = true;
      result.onComplete(null, null);
    }
  }

  @KeepForSdk
  public int getType() {
    return type;
  }

  @KeepForSdk
  public boolean isBlocked() {
    return blocked;
  }

  @Nullable
  @KeepForSdk
  public Bundle getChannelOrGroupBundle() {
    return channelOrGroupBundle;
  }
}
