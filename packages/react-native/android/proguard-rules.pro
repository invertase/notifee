-keep class io.invertase.notifee.NotifeeEventSubscriber
-keep class io.invertase.notifee.NotifeeInitProvider
-keepnames class io.invertase.notifee.NotifeePackage
-keepnames class io.invertase.notifee.NotifeeApiModule

# We depend on certain classes to exist under their names for dynamic
# class-loading to work. We use this to handle new arch / old arch backwards
# compatibility despite the class names moving around
-keep class com.facebook.react.defaults.DefaultNewArchitectureEntryPoint { *; }
-keep class com.facebook.react.ReactApplication { *; }
-keep class com.facebook.react.ReactHost { *; }
-keep class * extends com.facebook.react.ReactHost { *; }
-keepnames class com.facebook.react.ReactActivity

# Preserve all annotations.
-keepattributes *Annotation*

# Keep the classes/members we need for client functionality.
-keep @interface androidx.annotation.Keep
-keep @androidx.annotation.Keep class *
-keepclasseswithmembers class * {
  @androidx.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
  @androidx.annotation.Keep <methods>;
}

# Keep the classes/members we need for client functionality.
-keep @interface app.notifee.core.KeepForSdk
-keep @app.notifee.core.KeepForSdk class *
-keepclasseswithmembers class * {
  @app.notifee.core.KeepForSdk <fields>;
}
-keepclasseswithmembers class * {
  @app.notifee.core.KeepForSdk <methods>;
}

# Preserve all .class method names.
-keepclassmembernames class * {
    java.lang.Class class$(java.lang.String);
    java.lang.Class class$(java.lang.String, boolean);
}

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

# Preserve the special static methods that are required in all enumeration
# classes.
-keepclassmembers class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# --------------------------------
#            LIBRARIES
# --------------------------------

# Work Manager
-keepclassmembers class * extends androidx.work.ListenableWorker {
    public <init>(android.content.Context,androidx.work.WorkerParameters);
}

# EventBus
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# OkHttp3
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
