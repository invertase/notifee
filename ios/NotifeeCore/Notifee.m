//
//  Notifee.m
//  NotifeeCore
//
//  Created by Mike on 31/01/2020.
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "Notifee.h"

@import UserNotifications;


@implementation Notifee

+ (void)initialize:(NSString *)testString {
    [Notifee instance];
    NSLog(@"NotifeeInitialize, %@", testString);
}

# pragma mark - Delegate Methods

- (void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions options))completionHandler{
    NSLog(@"User Info : %@",notification.request.content.userInfo);
    completionHandler(UNAuthorizationOptionSound | UNAuthorizationOptionAlert | UNAuthorizationOptionBadge);
}

//Called to let your app know which action was selected by the user for a given notification.
- (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void(^)(void))completionHandler{
    NSLog(@"User Info : %@",response.notification.request.content.userInfo);
    completionHandler();
}



+ (instancetype)instance {
    static dispatch_once_t once;
    static Notifee *sharedInstance;
    dispatch_once(&once, ^{
        sharedInstance = [[Notifee alloc] init];
        UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
        center.delegate = sharedInstance;
    });
    
    return sharedInstance;
}

# pragma mark - Library Methods

+ (void) cancelNotification:(NSString *)notificationId withBlock:(notifeeMethodVoidBlock) block {
    NSLog(@"cancelNotification, %@", notificationId);
    UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
    [center removeDeliveredNotificationsWithIdentifiers:@[notificationId]];
    [center removePendingNotificationRequestsWithIdentifiers:@[notificationId]];
    block(nil);
}

+ (void) displayNotification:(NSDictionary *)notification withBlock:(notifeeMethodVoidBlock) block {
    NSLog(@"request authorization succeeded!");
    
    UNMutableNotificationContent* content = [[UNMutableNotificationContent alloc] init];
    content.title = @"Hello iOS!!!!";
    content.body = @"Look at me &#128517;";
    content.sound = [UNNotificationSound defaultSound];
    content.categoryIdentifier = @"NOTIFEE";
    
    
    UNNotificationAction* action1 = [UNNotificationAction actionWithIdentifier:@"ACCEPT" title:@"Yes mofo &#128517;" options:nil];
    
    UNNotificationAction* action2 = [UNNotificationAction actionWithIdentifier:@"DECLINE" title:@"Nope" options:nil];
    
    UNNotificationAction* action3 = [UNNotificationAction actionWithIdentifier:@"MAYBE" title:@"Maybe?????" options:nil];
    
    UNTextInputNotificationAction* action4 = [UNTextInputNotificationAction actionWithIdentifier:@"REPLY" title:@"Reply..." options:nil textInputButtonTitle:nil textInputPlaceholder:nil];
    
    UNNotificationCategory* category = [UNNotificationCategory categoryWithIdentifier:@"NOTIFEE" actions:@[] intentIdentifiers:@[] options:nil];
    
    UNNotificationCategory* categoryExt = [UNNotificationCategory categoryWithIdentifier:@"notifee_ext" actions:@[] intentIdentifiers:@[] options:nil];
    
    UNTimeIntervalNotificationTrigger* trigger = [UNTimeIntervalNotificationTrigger
                                                  triggerWithTimeInterval:5 repeats:NO];
    
    UNNotificationRequest* request = [UNNotificationRequest requestWithIdentifier:@"FiveSecond"
                                                                          content:content trigger:nil];
    
    UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
    
    [center setNotificationCategories:@[category, categoryExt]];
    [center addNotificationRequest:request withCompletionHandler:block];
}

# pragma mark - Permissions

+ (void) requestPermission:(NSDictionary *)permissions withBlock:(notifeeMethodBooleanBlock) block {
    UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
    
    UNAuthorizationOptions options = UNAuthorizationOptionNone;
    
    if ([permissions[@"alert"] isEqual: @(YES)]) {
        NSLog(@"permissionAlert");
        options |= UNAuthorizationOptionAlert;
    }
    
    if ([permissions[@"badge"] isEqual: @(YES)]) {
        NSLog(@"permissionBadge");
        options |= UNAuthorizationOptionBadge;
    }
    
    if ([permissions[@"sound"] isEqual: @(YES)]) {
        NSLog(@"permissionSound");
        options |= UNAuthorizationOptionSound;
    }
        
    
    if ([permissions[@"settings"] isEqual: @(YES)]) {
        if (@available(iOS 12.0, *)) {
            NSLog(@"permissionSettings");
            options |= UNAuthorizationOptionProvidesAppNotificationSettings;
        }
    }
    
    if ([permissions[@"provisional"] isEqual: @(YES)]) {
        NSLog(@"permissionProvisional");
        if (@available(iOS 12.0, *)) {
            options |= UNAuthorizationOptionProvisional;
        }
    }
    
    if ([permissions[@"announcement"] isEqual: @(YES)]) {
        NSLog(@"permissionAnnouncement");
        if (@available(iOS 13.0, *)) {
            options |= UNAuthorizationOptionAnnouncement;
        }
    }    
    
    if ([permissions[@"carPlay"] isEqual: @(YES)]) {
        NSLog(@"permissionCarplay");
        options |= UNAuthorizationOptionCarPlay;
    }
    
    id handler = ^(BOOL granted, NSError * _Nullable error) {
        block(error, granted);
    };
    
    [center requestAuthorizationWithOptions:options completionHandler:handler];
}

+ (void) hasPermission:(NSDictionary *)permissions withBlock:(notifeeMethodBooleanBlock) block {
    
}


@end
