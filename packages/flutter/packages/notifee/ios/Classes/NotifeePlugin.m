#import "NotifeePlugin.h"
#if __has_include(<notifee/notifee-Swift.h>)
#import <notifee/notifee-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "notifee-Swift.h"
#endif

@implementation NotifeePlugin

+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar> *)registrar {
  [NotifeePluginSwift registerWithRegistrar:registrar];
}

- (NSString *_Nonnull)flutterChannelName {
  return @"plugins.flutter.io/notifee";
}

@end
