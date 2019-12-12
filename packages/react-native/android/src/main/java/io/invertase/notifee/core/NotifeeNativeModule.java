package io.invertase.notifee.core;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotifeeNativeModule extends ReactContextBaseJavaModule {
  private static Map<String, ExecutorService> executors = new HashMap<>();
  private String moduleName;

  public NotifeeNativeModule(
    ReactApplicationContext reactContext,
    String moduleName
  ) {
    super(reactContext);
    this.moduleName = moduleName;
  }

  protected static void rejectPromiseWithExceptionMap(Promise promise, Exception exception) {
    NotifeeLogger.e("NativeModule", "rejectPromiseWithExceptionMap:" + exception.getMessage(), exception);
    promise.reject(exception, NotifeeCoreUtils.getExceptionMap(exception));
  }

  protected static void rejectPromiseWithCodeAndMessage(
    Promise promise,
    String code,
    String message,
    Exception exception
  ) {
    NotifeeLogger.e("NativeModule", "rejectPromiseWithCodeAndMessage" + exception.getMessage(), exception);
    WritableMap userInfoMap = Arguments.createMap();
    userInfoMap.putString("code", code);
    userInfoMap.putString("message", message);
    promise.reject(code, message, exception, userInfoMap);
  }

//  public static void rejectPromiseWithCodeAndMessage(Promise promise, String code, String message) {
//    WritableMap userInfoMap = Arguments.createMap();
//    userInfoMap.putString("code", code);
//    userInfoMap.putString("message", message);
//    promise.reject(code, message, userInfoMap);
//  }
//
//  public static void rejectPromiseWithCodeAndMessage(
//    Promise promise,
//    String code,
//    String message,
//    String nativeErrorMessage
//  ) {
//    WritableMap userInfoMap = Arguments.createMap();
//    userInfoMap.putString("code", code);
//    userInfoMap.putString("message", message);
//    userInfoMap.putString("nativeErrorMessage", nativeErrorMessage);
//    promise.reject(code, message, userInfoMap);
//  }

  @Override
  public void initialize() {
    super.initialize();
  }

  public ReactContext getContext() {
    return getReactApplicationContext();
  }

  public ExecutorService getExecutor() {
    ExecutorService existingSingleThreadExecutor = executors.get(getName());
    if (existingSingleThreadExecutor != null) return existingSingleThreadExecutor;
    ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
    executors.put(getName(), newSingleThreadExecutor);
    return newSingleThreadExecutor;
  }

  @Override
  public void onCatalystInstanceDestroy() {
    ExecutorService existingSingleThreadExecutor = executors.get(getName());
    if (existingSingleThreadExecutor != null) {
      existingSingleThreadExecutor.shutdownNow();
      executors.remove(getName());
    }
  }

  public Context getApplicationContext() {
    return getReactApplicationContext().getApplicationContext();
  }

  public Activity getActivity() {
    return getCurrentActivity();
  }

  @NonNull
  @Override
  public String getName() {
    return moduleName;
  }

  @Override
  public Map<String, Object> getConstants() {
    return new HashMap<>();
  }
}
