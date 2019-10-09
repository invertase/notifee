#import "Jet.h"
#import <objc/runtime.h>
#import <React/RCTBridge.h>

static NSString *const kRCTDevSettingIsDebuggingRemotely = @"isDebuggingRemotely";
static NSString *const kRCTDevSettingsUserDefaultsKey = @"RCTDevMenu";

@implementation Jet

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE(Jet)

RCT_EXPORT_METHOD(reload) {
  [_bridge reload];
  return;
}

RCT_EXPORT_METHOD(debug:(BOOL)value) {
  dispatch_async(dispatch_get_main_queue(), ^{
    NSDictionary *existingSettings = [[NSUserDefaults standardUserDefaults] objectForKey:kRCTDevSettingsUserDefaultsKey];
    NSMutableDictionary *_settings = existingSettings ? [existingSettings mutableCopy] : [NSMutableDictionary dictionary];
    _settings[kRCTDevSettingIsDebuggingRemotely] = @(value);
    [[NSUserDefaults standardUserDefaults] setObject:_settings forKey:kRCTDevSettingsUserDefaultsKey];
    _bridge.executorClass = value ? objc_getClass("RCTWebSocketExecutor") : nil;
  });
  return;
}

@end
