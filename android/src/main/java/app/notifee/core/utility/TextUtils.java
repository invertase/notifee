package app.notifee.core.utility;

import android.text.Spanned;

import androidx.core.text.HtmlCompat;

public class TextUtils {

  public static Spanned fromHtml(String text) {
    return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY);
  }

}
