package io.invertase.jet;

import android.app.Activity;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Jet extends ReactContextBaseJavaModule {
    Jet(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    private ReactInstanceManager getReactInstanceManager() {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) return null;

        ReactApplication reactApplication = (ReactApplication) currentActivity.getApplication();
        return reactApplication.getReactNativeHost().getReactInstanceManager();
    }


    /**
     * Reload JS Bundle
     */
    @ReactMethod
    public void reload() {
        final ReactInstanceManager instanceManager = getReactInstanceManager();
        if (instanceManager == null) return;

        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) return;

        currentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                instanceManager.recreateReactContextInBackground();
            }
        });
    }

    /**
     * Toggle remote debugging
     *
     * @param value true/false
     */
    @ReactMethod
    public void debug(Boolean value) {
        final ReactInstanceManager instanceManager = getReactInstanceManager();
        if (instanceManager == null) return;

        instanceManager.getDevSupportManager().getDevSettings().setRemoteJSDebugEnabled(value);
        reload();
    }

    @Override
    public String getName() {
        return "Jet";
    }
}
