package app.notifee.core.events;

import androidx.annotation.Nullable;

import app.notifee.core.KeepForSdk;
import app.notifee.core.MethodCallResult;

@KeepForSdk
public class BlockStateEvent {
  @KeepForSdk
  public static final String TYPE_APP = "app";
  @KeepForSdk
  public static final String TYPE_CHANNEL = "channel";
  @KeepForSdk
  public static final String TYPE_CHANNEL_GROUP = "channel_group";

  private String type;
  private boolean blocked;
  private @Nullable
  String channelOrGroupId;

  private MethodCallResult<Void> result;

  private boolean completed = false;

  public BlockStateEvent(
    String type, @Nullable String channelOrGroupId, boolean blocked, MethodCallResult<Void> result
  ) {
    this.type = type;
    this.result = result;
    this.blocked = blocked;
    this.channelOrGroupId = channelOrGroupId;
  }

  @KeepForSdk
  public void setCompletionResult() {
    if (!completed) {
      completed = true;
      result.onComplete(null, null);
    }
  }

  @KeepForSdk
  public String getType() {
    return type;
  }

  @KeepForSdk
  public boolean isBlocked() {
    return blocked;
  }

  @Nullable
  @KeepForSdk
  public String getChannelOrGroupId() {
    return channelOrGroupId;
  }
}
