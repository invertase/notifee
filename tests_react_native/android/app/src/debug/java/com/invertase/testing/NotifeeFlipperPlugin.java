package com.invertase.testing;

import com.facebook.flipper.core.FlipperConnection;
import com.facebook.flipper.core.FlipperObject;
import com.facebook.flipper.core.FlipperPlugin;

public class NotifeeFlipperPlugin implements FlipperPlugin {

  private FlipperConnection mFlipperConnection = null;

  @Override
  public String getId() {
    return "flipper-plugin-notifee";
  }

  @Override
  public void onConnect(FlipperConnection connection) throws Exception {
    mFlipperConnection = connection;

    newRow();
  }

  @Override
  public void onDisconnect() throws Exception {
    mFlipperConnection = null;
  }

  @Override
  public boolean runInBackground() {
    return false;
  }

  private void newRow() {
    FlipperObject.Builder flipperBuilder = new FlipperObject.Builder();

    flipperBuilder.put("foo", "bar");
    flipperBuilder.put("bar", "baz");

    if (mFlipperConnection != null) {
      mFlipperConnection.send("newRow", flipperBuilder.build());
    }
  }
}
