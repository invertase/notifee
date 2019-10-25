package io.invertase.notifee.core;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NotifeeApi {
  private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain; charset=utf-8");
  private static String API_URL = "https://api.notifee.app";
  private static String LICENSE_VERIFY_ROUTE = "/verify";
  private final OkHttpClient client = new OkHttpClient();

  String getLicenseVerificationStatus(String licenseKey) throws Exception {
    Request request = new Request.Builder()
      .url(API_URL + LICENSE_VERIFY_ROUTE)
      .post(RequestBody.create(MEDIA_TYPE_TEXT, licenseKey))
      .build();

    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) return null;
      if (response.body() == null) return null;
      return response.body().string();
    }
  }
}
