-keep class io.invertase.notifee.NotifeeEventSubscriber
-keep class io.invertase.notifee.NotifeeInitProvider
-keepnames class com.facebook.react.ReactActivity
-keepnames class io.invertase.notifee.NotifeePackage
-keepnames class io.invertase.notifee.NotifeeApiModule

-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
