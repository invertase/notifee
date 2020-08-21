package app.notifee.core.utility;

import android.graphics.Color;
import androidx.annotation.Nullable;
import java.util.HashMap;

public class ColorUtils {
  public static @Nullable String getColorString(int color) {
    String colorString = iColorNameMap.get(color);
    if (colorString != null) {
      return colorString;
    } else {
      return "#" + Integer.toHexString(color).substring(2);
    }
  }

  private static final HashMap<Integer, String> iColorNameMap;

  static {
    iColorNameMap = new HashMap<>();
    iColorNameMap.put(Color.BLACK, "black");
    iColorNameMap.put(Color.DKGRAY, "darkgray");
    iColorNameMap.put(Color.GRAY, "gray");
    iColorNameMap.put(Color.LTGRAY, "lightgray");
    iColorNameMap.put(Color.WHITE, "white");
    iColorNameMap.put(Color.RED, "red");
    iColorNameMap.put(Color.GREEN, "green");
    iColorNameMap.put(Color.BLUE, "blue");
    iColorNameMap.put(Color.YELLOW, "yellow");
    iColorNameMap.put(Color.CYAN, "cyan");
    iColorNameMap.put(Color.MAGENTA, "magenta");
    iColorNameMap.put(0xFF00FFFF, "aqua");
    iColorNameMap.put(0xFFFF00FF, "fuchsia");
    iColorNameMap.put(Color.DKGRAY, "darkgrey");
    iColorNameMap.put(Color.GRAY, "grey");
    iColorNameMap.put(Color.LTGRAY, "lightgrey");
    iColorNameMap.put(0xFF00FF00, "lime");
    iColorNameMap.put(0xFF800000, "maroon");
    iColorNameMap.put(0xFF000080, "navy");
    iColorNameMap.put(0xFF808000, "olive");
    iColorNameMap.put(0xFF800080, "purple");
    iColorNameMap.put(0xFFC0C0C0, "silver");
    iColorNameMap.put(0xFF008080, "teal");
  }
}
