package app.notifee.core.event;

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

import android.os.Bundle;
import androidx.annotation.Nullable;
import app.notifee.core.KeepForSdk;
import app.notifee.core.interfaces.MethodCallResult;
import app.notifee.core.utility.ObjectUtils;
import java.util.HashMap;
import java.util.Map;

@KeepForSdk
public class BlockStateEvent {
  @KeepForSdk public static final int TYPE_APP_BLOCKED = 4;

  @KeepForSdk public static final int TYPE_CHANNEL_BLOCKED = 5;

  @KeepForSdk public static final int TYPE_CHANNEL_GROUP_BLOCKED = 6;

  private int type;

  private boolean blocked;

  private @Nullable Bundle channelOrGroupBundle;

  private MethodCallResult<Void> result;

  private boolean completed = false;

  public BlockStateEvent(
      int type,
      @Nullable Bundle channelOrGroupBundle,
      boolean blocked,
      MethodCallResult<Void> result) {
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

  public Map<String, Object> toMap() {
    Map<String, Object> blockStateEventMap = new HashMap<>();
    Map<String, Object> blockStateEventDetailMap = new HashMap<>();
    blockStateEventMap.put("type", type);

    if (type == BlockStateEvent.TYPE_CHANNEL_BLOCKED
        || type == BlockStateEvent.TYPE_CHANNEL_GROUP_BLOCKED) {
      String mapKey = type == BlockStateEvent.TYPE_CHANNEL_BLOCKED ? "channel" : "channelGroup";
      if (channelOrGroupBundle != null) {
        blockStateEventDetailMap.put(mapKey, ObjectUtils.bundleToMap(channelOrGroupBundle));
      }
    }

    if (type == BlockStateEvent.TYPE_APP_BLOCKED) {
      blockStateEventDetailMap.put("blocked", isBlocked());
    }

    blockStateEventMap.put("detail", blockStateEventDetailMap);

    return blockStateEventMap;
  }
}
