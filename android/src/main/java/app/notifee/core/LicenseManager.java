package app.notifee.core;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

class LicenseManager {
  private static final String TAG = "License";
  private static final LicenseManager mInstance = new LicenseManager();
  private static final String JWT_KEY_JWT = "0";
  private static final String JWT_KEY_DEVICE_BRAND = "1";
  private static final String JWT_KEY_DEVICE_MODEL = "2";
  private static final String JWT_KEY_DEVICE_OS_VERSION = "3";
  private static final String JWT_KEY_PRODUCT_VERSION = "4";
  private static final String JWT_KEY_FRAMEWORK_VERSION = "5";
  private static final String JWT_KEY_APP_VERSION = "6";
  private static final String JWT_KEY_RESPONSE = "7";
  private static final String JWT_KEY_APP_ID = "n";
  private static final String JWT_KEY_LID = "o";
  private static final String JWT_KEY_ID = "t";
  private static final String JWT_KEY_PLATFORM = "i";
  private static final String JWT_KEY_PRIMARY = "f";
  private static final String JWT_KEY_TYPE = "e";
  private String mServerPublicKey = null;
  private String mClientPrivateKey = null;

  static void initialize() {
    synchronized (LicenseManager.mInstance) {
      if (mInstance.mServerPublicKey == null) {
        mInstance.mServerPublicKey =
            "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA/7MJ1T3Oa+vSACnoc11G\n"
                + "gP1GQ4dNbzJDHRczA6lgX/+gmsDACaZkoe7vyq/fTZrrTVggXLZXvhQjNQwrmDC7\n"
                + "Z1zeFKZZecmxXjL+HxXBZj4fGCoENvOqLop0FkCNvgFS9aYEze+NLQWwWCTmqw08\n"
                + "9tBjlhMAZFrsx4I66aXJZh69KoG8w/v1Hj1Am2BlVSD1K1kS+KTXTV/4Lmn31/gF\n"
                + "s5SCFzB7Y2yLSdxSdljahmDWcdaDY8GIEOtK5JImO53kML08cdpYBksOYqXlk2+a\n"
                + "mTQup6IVzlrQoeu7fIRvJ8w4ExuWOXgKS9thU1x/NLUujAnv7O5PkAWOXoVQF5GM\n"
                + "fzDX6VtbiDQyvwND8nNz2Z3345b53zc+9eghWKo6zDMacNrc1/WNtO5WgMEwilvK\n"
                + "kiVHGWHRYYbKNZKckWf+fkXTOfXiajFkCARzaIvvIHPOoiw/SxYQR7rp8OimlLgR\n"
                + "c5qJw2rCcXrvqSLXvIyF7MHfmwX0yzyy6cYOQBnoYrs/sUxliEiYDg9AMlLSBEoC\n"
                + "qCl2oNFq+UN6H/Onv+FKvCAeTWowsco2OXw9ry1q83FY6ksAW8rh0cA25dosAfcm\n"
                + "DYSDKXKXjmJCL07Ehi1OLACDwb9z7z1zGWISXMAVdkzzL59s01nKbnCcNLQd6CqF\n"
                + "RTTO/skUue3mgizqFfqGb8sCAwEAAQ==";
      }
      if (mInstance.mClientPrivateKey == null) {
        mInstance.mClientPrivateKey =
            "MIIJKAIBAAKCAgEA3hzYAlBLQFPA3ibXKMwHr5QWgmoJzw089XyEptzdGcVa4zkj\n"
                + "n8i8+UZCAzTOFYDVZtKfP2GEBcOQ80WoL1bCAI6eqXPrSgp02iCwviXbFhN1z+s7\n"
                + "/dPHORcuILdcd9XWx7NNB04LPBMt5LlCae18Tvciak8HwaIvbvYL45XpUxkqKf/I\n"
                + "TNqKi+FxR9k146Djkux7lWv1lhQFh9eUmeynwSswXhQnJhFnM94Rn6OTLX7XJSUx\n"
                + "lwrDL0wPfs9NXmIXAkM6muM6NumzOTIIfm6YQULY8Ts1wcS+Z7zzER/Gov+tQfzi\n"
                + "0i6h1xU1ItS27a3iIF6LN2ey8dQFoNnWNgXylwCZIqmkJMLxPu6lTiSpECpw1a4P\n"
                + "qi7wUgJ5INm1brwOpUn3e392l5TRhgwN2vfYLhg0Nsj2LtTIIMqrrrZnQGwsh2fy\n"
                + "G/TY7hFto2odH6NQnN7XmmTAjCRYtlrLD4+KbQ7alVXMyW8AqnRPJ8ULbmwaWq94\n"
                + "3ieI/sI8jtV85b7WULe0lH5BCVMn43cSIn8JBPcn+qFD0pYhI1spCWBk3n82c+2J\n"
                + "OijjbnXaH+mJ/GD7ShOtCWlmoIHCZstIR89B9WmXzp0r7BGi49pzB4E41z5nV8Jo\n"
                + "JOu9/ntC8CRnnx3cZwla1gW5hQX5NYnev/VxNfteYUBDTuArV6dW0HtFmGMCAwEA\n"
                + "AQKCAgBSVdLlGKqsj9+A+ljr8KYwue6WLYSxUjD0t1HdISZ89SG59WZ1Rs52gUrb\n"
                + "MWnrorR4xz7tGdL86AAFjh7IXZrQ5g1+t0/TRIkIivG5qwLJ7jDQAF1evHCvgx5A\n"
                + "Vnham9RgduDpAk8718g9b0dlSPm5s/b3/Y/cgaifs2m41cuGWF++7ehsRN7y94Es\n"
                + "pyJI3U0/G5a8TybcmVNrhci6PnX5L29gkIvqmqztFzblPJfEV66dQGpfmUe9cq6T\n"
                + "zXjasfXhitZgsDbWQE+Ftjxb0ddy6brNwastxybmAa8A/AyAQ9MQERRr4Ylw15W4\n"
                + "8a1V8g1O/n0Q7snwxy3G8LltkUrZai/E1mQO4yMvVOluNxVYees3+iQd/jayOXIZ\n"
                + "EQC977GBXVsSJHVpFoI2V9GET22cMV8oKQorW2U7gzH058u7WCa3PmXG/a4emX0m\n"
                + "DFliLgry1XqE+VJ9DEkWlZyet0SKm6jUcgtqeJTzLQFhy061bCwLQlLIb3n/W87F\n"
                + "2tWa6yZCUVrgNpn+389hnhaS/mMsykAY/4Oq5YVZvdtml7LcewtFa/n6vrGMlOsx\n"
                + "rIv7Q6Pk2QZgphrUbwRS6xc9LxHt9ymiyrMp11he8h4U/tYdZpAJtdF+iuNwO/dR\n"
                + "i4To+kDCcd/KoNQ1H72RxmmwEJJfkYVX5THFbacb5OjNBu1qoQKCAQEA9Mmqpfq2\n"
                + "hyApaFgwsMBSgCOYZ52rYTiLfUqDkw8wZeL5B7WT0nOrEsSdETmRBze6053iQPLP\n"
                + "Lh94JAmrI4kRvySUsJtT0p1kCnw3ldDlEkoVv1iMskI1OD23CVPpzfVJbplcJwLj\n"
                + "LZo1MlteznSDhOQbgDkusYGrtT2Ie6DssTguPXLRmIncRFHKp99HEjP+Dl3qNaZM\n"
                + "VkrzIMpwqVV05w5Fgifa+elLKgnC1m3X2JozRbkcarhl6jQcLB+A++VyzwPcmDA7\n"
                + "DoJC77NkMEKyFkM6SeoolsDS25faJkP/hI8K9HQLsvzFqIDgMSyhuIWig0qIjw5r\n"
                + "Hq92ru/Ha6eemQKCAQEA6ElLJMLwbGTr4Qr4JPH0kuPy7cKQc1cewq0PWB9qcTyr\n"
                + "XVWdlJxwq9QqsRwck4R8zfuqHY8ebY9b6WeSTByjGKlI7ETf0/KdvIzlxoVGDvrX\n"
                + "y1Qz80jDOCoOkbtiqiI9Go9Tqj+ZSfviLK57QHlHOWO7po4I7CEXLaPmPJILNkfG\n"
                + "Nu4jvkK4XehaofgmwjoXT9Zmqf2k1oV/1lWytdKRDE4HBE7cNyFNnDJriPHeMBVM\n"
                + "MwQzAF9BK0jkRAy5BKT6C9xxxgg5uLAQJYQxu4bjCXDDOnSKSk3VlIDQdPop0Y34\n"
                + "ApWwE/zb0S+8C8GKRDqLD6lcHHuRt1y2oVAFoOz4WwKCAQBTz+S81b2/QFTNJDzv\n"
                + "l7nno8hf2c0/CWRBLs0kAfRZPkBz0kjdqrrtPyJkLmiopv0xzYIVKM2lBiNVe3X8\n"
                + "Qccwwe6jFVu65ibFrEg/5Hk6LOGLVV8+/YpJSmAsMm4AFbbhxmKV/NgZ2g3SwxQP\n"
                + "7jbFvnBoE6wYHMTU1k+vvKat+wViBrka2EDxp2uS1ND5u5GGC8PQQLMsbJcYKBgw\n"
                + "8lCHeAx2hvzjymvw5cyvLIbV494gRkQjiiVi7hqjRNod5S7NEI5sET93NUSD29E2\n"
                + "8O8Wzkfb3O+uxjCr/S81IN0Q3wUqM715uDBZBF8+lwB8NE0zVMay7IXiyMMDHJgU\n"
                + "FR0pAoIBAQDUC2RPUK0NJiu1qb/QahdrqC7xIHWg9NydtkGVnkgaytlcQHWzXgP0\n"
                + "t5+pQhJMD9umZaBrj2SlewLaVLPWSyYPsylglZcF8ipQHwb6bFsB/bbUZC9wXPHo\n"
                + "6WuXWUm+KbdB8ajcd2ZFhWx4gWb9+jgsiYCZkHtQovx3q3DXxjH6ARdOuaFjY6DO\n"
                + "CPgDd3ZaQ5FYTk41y9eYBRIn5N9Y37mNVAVPx3V71ij094n232SG3EpNH/42zr28\n"
                + "97N482xKcxfXkAtETenzULXMqZqEp6PF0GxHhm9fWSIpiFXDE0Ltiv3lziOIe4Fm\n"
                + "un6c9LZ1hkO/rkjpr1vb2QTWySf6OZiVAoIBACE740xXJ68fHmaUbBgNGSvINw0/\n"
                + "icg79ccHnfikWhou3AXR8ar9L36FkQWXap6yFFjKCBYHG4zHWAEHfv/6+DGw5NyA\n"
                + "MvjGDdggZVkwVWblSuXP8TD7LLT8dhx/rSt23xnQAGBtDotNfpDsfm05QfDkclfH\n"
                + "k74UxpK7dI/sO2bRInxVWlWa9TLNKcbMnOpkgYxfArxLXFBRWFMap+M7Ym5U7Dcp\n"
                + "1zWl4Qf2eOZJqsOpUWMk95nakiL7RPo/u1590wsyIHoNQZirfULyQlf6f8br6L2Y\n"
                + "3ZbTFU2wr6lIkpTKOTkmS6zGZBxk7GZDZrsilg51EOS6w0T3J3kkrdA5gGE=";
      }
      scheduleLocalWork();
    }
  }

  private static LicenseManager getInstance() {
    return mInstance;
  }

  /** Returns Android license key either from String resources. */
  private static String getLicenseKey() {
    Resources resources = ContextHolder.getApplicationContext().getResources();
    String packageName = ContextHolder.getApplicationContext().getPackageName();
    int identifier = resources.getIdentifier("notifee_config_license", "string", packageName);
    if (identifier != 0) {
      String key = resources.getString(identifier);
      Log.d(TAG, "License key found from resources: " + key);
      return key;
    }

    return null;
  }

  /** Local/offline license validation via JWT. */
  static void doLocalWork(CallbackToFutureAdapter.Completer<ListenableWorker.Result> completer) {
    Logger.d(TAG, "Local verification started.");
    // mark local verification as pending while we try verify the license
    Preferences.getSharedInstance().setIntValue("lvs", LocalVerificationStatus.PENDING);

    // get license key from BuildConfig or JSONConfig
    String androidLicenseKey = getLicenseKey();

    // check a license key has been specified
    if (androidLicenseKey == null) {
      // mark local verification as no license found, when we in debug builds this status is ignored
      // as a license is not required in debug
      Preferences.getSharedInstance().setIntValue("lvs", LocalVerificationStatus.NO_LICENSE);
      WorkManager.getInstance(ContextHolder.getApplicationContext())
          .cancelUniqueWork(Worker.WORK_TYPE_LICENSE_VERIFY_REMOTE);
      completer.set(ListenableWorker.Result.success());
      return;
    }

    // verify the jwt token
    Jws<Claims> verifiedJwt = getInstance().verifyToken(androidLicenseKey);
    if (verifiedJwt == null) {
      // mark local verification as invalid license
      Preferences.getSharedInstance().setIntValue("lvs", LocalVerificationStatus.INVALID_LICENSE);
      WorkManager.getInstance(ContextHolder.getApplicationContext())
          .cancelUniqueWork(Worker.WORK_TYPE_LICENSE_VERIFY_REMOTE);
      completer.set(ListenableWorker.Result.success());
      return;
    }

    Claims jwtBody = verifiedJwt.getBody();
    if (!jwtBody.containsKey(JWT_KEY_APP_ID)) {
      // mark local verification as invalid license
      Preferences.getSharedInstance().setIntValue("lvs", LocalVerificationStatus.INVALID_LICENSE);
      WorkManager.getInstance(ContextHolder.getApplicationContext())
          .cancelUniqueWork(Worker.WORK_TYPE_LICENSE_VERIFY_REMOTE);
      completer.set(ListenableWorker.Result.success());
      return;
    }

    // confirm the license app id matches this application
    String jwtAppId = jwtBody.get(JWT_KEY_APP_ID, String.class);
    String packageName = ContextHolder.getApplicationContext().getPackageName();
    if (!jwtAppId.equals(packageName)) {
      // mark local verification as invalid license as its not for this application id
      Preferences.getSharedInstance().setIntValue("lvs", LocalVerificationStatus.INVALID_LICENSE);
      Logger.e(
          TAG,
          "Your license key is not for this application, expected application to be "
              + jwtAppId
              + " but found "
              + packageName);
      WorkManager.getInstance(ContextHolder.getApplicationContext())
          .cancelUniqueWork(Worker.WORK_TYPE_LICENSE_VERIFY_REMOTE);
      completer.set(ListenableWorker.Result.success());
      return;
    }

    // confirm license is for the right platform
    Integer jwtPlatform = jwtBody.get(JWT_KEY_PLATFORM, Integer.class);
    if (jwtPlatform != LicensePlatform.ANDROID) {
      // mark local verification as invalid license as its not for this platform
      Preferences.getSharedInstance().setIntValue("lvs", LocalVerificationStatus.INVALID_LICENSE);
      Logger.e(TAG, "Your license key is not for this platform (Android)");
      WorkManager.getInstance(ContextHolder.getApplicationContext())
          .cancelUniqueWork(Worker.WORK_TYPE_LICENSE_VERIFY_REMOTE);
      completer.set(ListenableWorker.Result.success());
      return;
    }

    // license is valid, lets schedule the remote check work
    Boolean jwtPrimary = jwtBody.get(JWT_KEY_PRIMARY, Boolean.class);

    long remoteWorkIntervalDays = 7;
    if (!jwtPrimary) {
      // we remote validate secondary keys more often so server can detect if
      // key is being abused, e.g. secondary being used to run a primary app
      remoteWorkIntervalDays = 3;
    }

    scheduleRemoteWork(remoteWorkIntervalDays, jwtPrimary, ExistingPeriodicWorkPolicy.KEEP);

    // mark local verification a valid license
    Preferences.getSharedInstance().setIntValue("lvs", LocalVerificationStatus.OK);

    Logger.d(TAG, "Local verification succeeded.");

    completer.set(ListenableWorker.Result.success());
  }

  /** Remote/online license validation, calls the Notifee API verification endpoint. */
  static void doRemoteWork(
      Data workData, final CallbackToFutureAdapter.Completer<ListenableWorker.Result> completer) {
    Logger.d(TAG, "Remote verification started.");
    boolean isPrimaryKey = workData.getBoolean(Worker.KEY_IS_PRIMARY, false);

    // get license key from BuildConfig or JSONConfig
    String androidLicenseKey = getLicenseKey();

    // check a license key has been specified either by NOTIFEE_API_KEY or JSONConfig
    if (androidLicenseKey == null) {
      Logger.d(TAG, "Remote verification skipped as license key not found.");
      completer.set(ListenableWorker.Result.success());
      return;
    }

    String verifyTokenJwt = buildVerifyLicenseRequestToken(androidLicenseKey);

    OkHttpClient client = new OkHttpClient.Builder().callTimeout(15, TimeUnit.SECONDS).build();

    Request request =
        new Request.Builder()
            .url("https://api.notifee.app/license/verify")
            .post(RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), verifyTokenJwt))
            .build();

    client
        .newCall(request)
        .enqueue(
            new Callback() {
              @Override
              public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Logger.e(TAG, "Remote license verification request failed.", e);
                // schedule to try again sooner if request failed,
                // remote status will remain as pending until verified
                scheduleRemoteWork(1, isPrimaryKey, ExistingPeriodicWorkPolicy.REPLACE);
                completer.set(ListenableWorker.Result.success());
              }

              @Override
              public void onResponse(@NotNull Call call, @NotNull Response response) {
                Logger.d(TAG, "Remote verification API responded.");
                try {
                  String responseBody = null;
                  boolean successful = response.isSuccessful();
                  if (successful && response.body() != null) {
                    responseBody = Objects.requireNonNull(response.body()).string();
                  }

                  // not successful most likely means the API failed
                  if (!successful) {
                    // schedule to try again sooner if request failed, remote
                    // status will remain as pending until verified
                    Logger.d(
                        TAG,
                        "Remote verification API call not successful, code: " + response.code());
                    completer.set(ListenableWorker.Result.success());
                    scheduleRemoteWork(2, isPrimaryKey, ExistingPeriodicWorkPolicy.REPLACE);
                    return;
                  }

                  // verify the response jwt token
                  Jws<Claims> verifiedJwt = getInstance().verifyToken(responseBody);
                  if (verifiedJwt == null) {
                    // schedule to try again sooner if request failed, remote status will remain as
                    // pending
                    // until verified
                    Logger.d(TAG, "Remote verification response not verified.");
                    completer.set(ListenableWorker.Result.success());
                    scheduleRemoteWork(2, isPrimaryKey, ExistingPeriodicWorkPolicy.REPLACE);
                    return;
                  }

                  Claims claims = verifiedJwt.getBody();
                  if (!claims.containsKey(JWT_KEY_RESPONSE)) {
                    // schedule to try again sooner if request failed, remote status will remain as
                    // pending
                    // until verified
                    Logger.d(TAG, "Remote verification response missing key.");
                    completer.set(ListenableWorker.Result.success());
                    scheduleRemoteWork(2, isPrimaryKey, ExistingPeriodicWorkPolicy.REPLACE);
                    return;
                  }

                  Integer remoteStatus = claims.get(JWT_KEY_RESPONSE, Integer.class);
                  Preferences.getSharedInstance().setIntValue("rvs", remoteStatus);

                  long remoteWorkIntervalDays;

                  if (remoteStatus == RemoteVerificationStatus.OK) {
                    remoteWorkIntervalDays = 7;
                  } else {
                    // license validation failed, check again as soon as possible
                    remoteWorkIntervalDays = 2;
                  }

                  Logger.d(TAG, "Remote verification completed with status: " + remoteStatus);

                  completer.set(ListenableWorker.Result.success());
                  scheduleRemoteWork(
                      remoteWorkIntervalDays, isPrimaryKey, ExistingPeriodicWorkPolicy.REPLACE);
                } catch (Exception e) {
                  Logger.e(TAG, "onResponse exception", e);
                  completer.set(ListenableWorker.Result.failure());
                }
              }
            });
  }

  /** Create an immediate work request that validates the license key JWT. */
  private static void scheduleLocalWork() {
    Data workData =
        new Data.Builder()
            .putString(Worker.KEY_WORK_TYPE, Worker.WORK_TYPE_LICENSE_VERIFY_LOCAL)
            .build();

    OneTimeWorkRequest.Builder workRequestBuilder = new OneTimeWorkRequest.Builder(Worker.class);
    workRequestBuilder.addTag(Worker.WORK_TYPE_LICENSE_VERIFY_LOCAL);
    workRequestBuilder.setInputData(workData);

    WorkManager.getInstance(ContextHolder.getApplicationContext())
        .enqueueUniqueWork(
            Worker.WORK_TYPE_LICENSE_VERIFY_LOCAL,
            ExistingWorkPolicy.KEEP,
            workRequestBuilder.build());
  }

  /**
   * Create a periodic work request that runs every few days to validate the license with the remote
   * notifee api. Work request will only run when internet connectivity is detected.
   */
  private static void scheduleRemoteWork(
      long daysInterval,
      boolean isPrimaryKey,
      ExistingPeriodicWorkPolicy existingPeriodicWorkPolicy) {
    // Don't verify on older devices (JWT signing issue).
    if (android.os.Build.VERSION.SDK_INT <= 20) {
      return;
    }

    Constraints constraints =
        new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();

    Data workData =
        new Data.Builder()
            .putString(Worker.KEY_WORK_TYPE, Worker.WORK_TYPE_LICENSE_VERIFY_REMOTE)
            .putBoolean(Worker.KEY_IS_PRIMARY, isPrimaryKey)
            .build();

    PeriodicWorkRequest.Builder workRequestBuilder =
        new PeriodicWorkRequest.Builder(Worker.class, daysInterval, TimeUnit.DAYS);

    workRequestBuilder.setInputData(workData);
    workRequestBuilder.setConstraints(constraints);
    workRequestBuilder.addTag(Worker.WORK_TYPE_LICENSE_VERIFY_REMOTE);

    if (existingPeriodicWorkPolicy == ExistingPeriodicWorkPolicy.REPLACE) {
      workRequestBuilder.setInitialDelay((daysInterval * 24) / 2, TimeUnit.HOURS);
    }

    WorkManager.getInstance(ContextHolder.getApplicationContext())
        .enqueueUniquePeriodicWork(
            Worker.WORK_TYPE_LICENSE_VERIFY_REMOTE,
            existingPeriodicWorkPolicy,
            workRequestBuilder.build());
  }

  /** Builds a remote validation request JWT payload. */
  private static String buildVerifyLicenseRequestToken(String jwtLicenseKey) {
    JwtBuilder jwtBuilder = Jwts.builder();
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    jwtBuilder.setIssuedAt(now);

    long expMillis = nowMillis + 28800000; // 8 hours
    Date expiry = new Date(expMillis);
    jwtBuilder.setExpiration(expiry);

    jwtBuilder.claim(JWT_KEY_JWT, jwtLicenseKey);

    jwtBuilder.claim(JWT_KEY_DEVICE_BRAND, Build.BRAND);
    jwtBuilder.claim(JWT_KEY_DEVICE_MODEL, Build.MODEL);
    jwtBuilder.claim(JWT_KEY_DEVICE_OS_VERSION, "" + Build.VERSION.SDK_INT);

    try {
      jwtBuilder.claim(JWT_KEY_APP_VERSION, getPackageInfo().versionName);
    } catch (Exception e) {
      jwtBuilder.claim(JWT_KEY_APP_VERSION, "<unknown>");
    }

    jwtBuilder.claim(JWT_KEY_PRODUCT_VERSION, Notifee.getNotifeeConfig().getProductVersion());
    jwtBuilder.claim(JWT_KEY_FRAMEWORK_VERSION, Notifee.getNotifeeConfig().getFrameworkVersion());

    try {
      jwtBuilder.signWith(loadPrivateKey(mInstance.mClientPrivateKey), SignatureAlgorithm.RS384);
    } catch (Exception e) {
      Log.e(TAG, "", e);
      return "";
    }

    return jwtBuilder.compact();
  }

  private static PackageInfo getPackageInfo() throws Exception {
    return ContextHolder.getApplicationContext()
        .getPackageManager()
        .getPackageInfo(ContextHolder.getApplicationContext().getPackageName(), 0);
  }

  /** Returns a boolean of whether the current license is valid. */
  static boolean isLicenseInvalid() {
    boolean isDebug =
        (0
            != (ContextHolder.getApplicationContext().getApplicationInfo().flags
                & ApplicationInfo.FLAG_DEBUGGABLE));

    // Free to use in development.
    // Don't verify on older devices (JWT signing issue).
    if (isDebug || android.os.Build.VERSION.SDK_INT <= 20) {
      return false;
    }

    int localStatus = getLocalStatus();
    int remoteStatus = getRemoteStatus();

    // if remote & local statuses are either OK or PENDING then assume the license is valid
    return (remoteStatus != RemoteVerificationStatus.PENDING
            && remoteStatus != RemoteVerificationStatus.OK)
        || (localStatus != LocalVerificationStatus.PENDING
            && localStatus != LocalVerificationStatus.OK);
  }

  private static int getLocalStatus() {
    return Preferences.getSharedInstance().getIntValue("lvs", LocalVerificationStatus.PENDING);
  }

  private static int getRemoteStatus() {
    return Preferences.getSharedInstance().getIntValue("rvs", RemoteVerificationStatus.PENDING);
  }

  private static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
    byte[] buffer = Base64.decode(publicKeyStr, Base64.DEFAULT);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
    return keyFactory.generatePublic(keySpec);
  }

  private static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
    byte[] buffer = Base64.decode(privateKeyStr, Base64.DEFAULT);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
    return keyFactory.generatePrivate(keySpec);
  }

  static void logLicenseWarningForMethod(String methodName) {
    String warning = "Attempted to call method " + methodName + " but your license is invalid.";
    Logger.w(TAG, warning);
  }

  static void logLicenseWarningForEvent(String eventName) {
    String warning = "Attempted to send a " + eventName + " event but your license is invalid.";
    Logger.w(TAG, warning);
  }

  private @Nullable Jws<Claims> verifyToken(String token) {
    Jws<Claims> jwt = null;

    try {
      jwt = Jwts.parser().setSigningKey(loadPublicKey(mServerPublicKey)).parseClaimsJws(token);
    } catch (Exception e) {
      Logger.e(TAG, "Error parsing license key, invalid JWT?", e);
    }

    return jwt;
  }

  private static class LicensePlatform {
    static final int ANDROID = 0;
    static final int IOS = 1;
  }

  private static class LocalVerificationStatus {
    static final int OK = 0;
    static final int PENDING = 1;
    static final int NO_LICENSE = 2;
    static final int INVALID_LICENSE = 3;
  }

  private static class RemoteVerificationStatus {
    static final int OK = 0;
    static final int PENDING = 1;
    static final int BAD_REQUEST_TOKEN = 2;
    static final int BAD_REQUEST_PAYLOAD = 3;
    static final int LICENSE_INVALID = 4;
    static final int LICENSE_PAYLOAD_INVALID = 5;
    static final int LICENSE_NOT_FOUND = 6;
    static final int LICENSE_TOO_MANY_DEVICES = 7;
  }
}
