package app.notifee.core.events;

import androidx.work.Data;

import app.notifee.core.KeepForSdk;
import app.notifee.core.MethodCallResult;

@KeepForSdk
public class BlockStateEvent {
  private String type;
  private Data inputData;
  private MethodCallResult<Void> result;

  public BlockStateEvent(String type, Data inputData, MethodCallResult<Void> result) {
    this.type = type;
    this.inputData = inputData;
    this.result = result;
  }

  @KeepForSdk
  public void setResult() {
    result.onComplete(null, null);
  }

}
