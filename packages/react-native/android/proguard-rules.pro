# EventBus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

-keep class io.invertase.notifee.BuildConfig;
-keep class io.invertase.notifee.NotifeeEventSubscriber;

# React Native
-keepnames class com.facebook.react.ReactActivity;
-keepnames class app.notifee.react.NotifeeApiModule;
-keepnames class app.notifee.react.NotifeeCoreModule;
