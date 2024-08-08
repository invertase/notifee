
import Flutter
import NotifeeCore

let kFLTNotifeeChannelName = "plugins.invertase.io/notifee"

public class NotifeePluginSwift: NSObject, FlutterPlugin, NotifeeCoreDelegate {
    public static var eventSink: FlutterEventSink?
    private var messenger: FlutterBinaryMessenger
    private var channel: FlutterMethodChannel?

    var args = NSDictionary()

    init(messenger: FlutterBinaryMessenger, channel: FlutterMethodChannel) {
        self.messenger = messenger
        self.channel = channel
    }

    public func didReceiveNotifeeCoreEvent(_ event: [AnyHashable: Any]) {
        print(event)

        DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) {
            if UIApplication.shared.applicationState == .background {
                self.channel?.invokeMethod("Notifee#onBackgroundEvent", arguments: event)
            } else {
                NotifeePluginSwift.eventSink?(event)
            }
        }
    }

    public static func register(with registrar: FlutterPluginRegistrar) {
        let binaryMessenger: FlutterBinaryMessenger
        binaryMessenger = registrar.messenger()

        let channel = FlutterMethodChannel(name: kFLTNotifeeChannelName, binaryMessenger: binaryMessenger)
        let instance = NotifeePluginSwift(messenger: binaryMessenger, channel: channel)

        registrar.addMethodCallDelegate(instance, channel: channel)
        registrar.publish(instance)

        NotifeeCore.setCoreDelegate(instance)

        let eventChannel = FlutterEventChannel(name: "plugins.invertase.io/notifee/on_foreground", binaryMessenger: binaryMessenger)
        eventChannel.setStreamHandler(NotifeeStreamHandler())
    }

    func displayNotification(arguments: [String: Any], result: @escaping FlutterResult) {
        NotifeeCore.displayNotification(arguments, with: { (error: Error?) in
            if error != nil {
                result(error)
            } else {
                result(nil)
            }
        })
    }

    func createTriggerNotification(arguments: [String: Any], result: @escaping FlutterResult) {
        NotifeeCore.createTriggerNotification(arguments["notification"] as! [String: Any], withTrigger: arguments["trigger"] as! [String: Any], with: { (error: Error?) in
            if error != nil {
                result(error)
            } else {
                result(nil)
            }
        })
    }

    func cancelAllNotifications(arguments: [String: Any], result: @escaping FlutterResult) {
        NotifeeCore.cancelAllNotifications(arguments["type"] as! Int, with: { (error: Error?) in
            if error != nil {
                result(error)
            } else {
                result(nil)
            }
        })
    }

    func cancelAllNotificationsWithIds(arguments: [String: Any], result: @escaping FlutterResult) {
        NotifeeCore.cancelAllNotifications(withIds: arguments["type"] as! Int, withIds: arguments["ids"] as! [String], with: { (error: Error?) in
            if error != nil {
                result(error)
            } else {
                result(nil)
            }
        })
    }

    func requestPermission(arguments _: [String: Any], result: @escaping FlutterResult) {
        NotifeeCore.requestPermission(["alert": true, "sound": true, "badge": true], with: { (error: Error?, settings: Any?) in
            if error != nil {
                result(error)
            } else {
                result(settings)
            }
        })
    }

    func getBadgeCount(result: @escaping FlutterResult) {
        NotifeeCore.getBadgeCount { (error: Error?, count: Any?) in
            if error != nil {
                result(error)
            } else {
                result(count)
            }
        }
    }

    func getNotificationCategories(result: @escaping FlutterResult) {
        NotifeeCore.getNotificationCategories { (error: Error?, categories: Any?) in
            if error != nil {
                result(error)
            } else {
                result(categories)
            }
        }
    }

    func getNotificationSettings(result: @escaping FlutterResult) {
        NotifeeCore.getNotificationSettings { (error: Error?, settings: Any?) in
            if error != nil {
                result(error)
            } else {
                result(settings)
            }
        }
    }

    func getTriggerNotificationIds(result: @escaping FlutterResult) {
        NotifeeCore.getTriggerNotificationIds { (error: Error?, ids: Any?) in
            if error != nil {
                result(error)
            } else {
                result(ids)
            }
        }
    }

    func getTriggerNotifications(result: @escaping FlutterResult) {
        NotifeeCore.getTriggerNotifications { (error: Error?, notifications: Any?) in
            if error != nil {
                result(error)
            } else {
                result(notifications)
            }
        }
    }

    func getDisplayedNotifications(result: @escaping FlutterResult) {
        NotifeeCore.getDisplayedNotifications { (error: Error?, notifications: Any?) in
            if error != nil {
                result(error)
            } else {
                result(notifications)
            }
        }
    }

    func getInitialNotification(result: @escaping FlutterResult) {
        NotifeeCore.getInitialNotification { (error: Error?, notification: Any?) in
            if error != nil {
                result(error)
            } else {
                result(notification)
            }
        }
    }

    func incrementBadgeCount(arguments: Int, result: @escaping FlutterResult) {
        NotifeeCore.incrementBadgeCount(arguments, with: { (error: Error?) in
            if error != nil {
                result(error)
            } else {
                result(nil)
            }
        })
    }

    func decrementBadgeCount(arguments: Int, result: @escaping FlutterResult) {
        NotifeeCore.decrementBadgeCount(arguments, with: { (error: Error?) in
            if error != nil {
                result(error)
            } else {
                result(nil)
            }
        })
    }

    func setBadgeCount(arguments: Int, result: @escaping FlutterResult) {
        NotifeeCore.setBadgeCount(arguments, with: { (error: Error?) in
            if error != nil {
                result(error)
            } else {
                result(nil)
            }
        })
    }

    func setNotificationCategories(arguments: [[AnyHashable: Any]], result: @escaping FlutterResult) {
        NotifeeCore.setNotificationCategories(arguments, with: { (error: Error?) in
            if error != nil {
                result(error)
            } else {
                result(nil)
            }
        })
    }

    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        if call.method == "displayNotification" {
            displayNotification(arguments: call.arguments as! [String: Any], result: result)
        } else if call.method == "getInitialNotification" {
            getInitialNotification(result: result)
        } else if call.method == "createTriggerNotification" {
            createTriggerNotification(arguments: call.arguments as! [String: Any], result: result)
        } else if call.method == "cancelAllNotifications" {
            cancelAllNotifications(arguments: call.arguments as! [String: Any], result: result)
        } else if call.method == "cancelAllNotificationsWithIds" {
            cancelAllNotificationsWithIds(arguments: call.arguments as! [String: Any], result: result)
        } else if call.method == "requestPermission" {
            requestPermission(arguments: call.arguments as! [String: Any], result: result)
        } else if call.method == "getNotificationSettings" {
            getNotificationSettings(result: result)
        } else if call.method == "getTriggerNotificationIds" {
            getTriggerNotificationIds(result: result)
        } else if call.method == "getTriggerNotifications" {
            getTriggerNotifications(result: result)
        } else if call.method == "incrementBadgeCount" {
            incrementBadgeCount(arguments: call.arguments as! Int, result: result)
        } else if call.method == "decrementBadgeCount" {
            decrementBadgeCount(arguments: call.arguments as! Int, result: result)
        } else if call.method == "setBadgeCount" {
            setBadgeCount(arguments: call.arguments as! Int, result: result)
        } else if call.method == "getBadgeCount" {
            getBadgeCount(result: result)
        } else if call.method == "setNotificationCategories" {
            setNotificationCategories(arguments: call.arguments as! [[AnyHashable: Any]], result: result)
        } else if call.method == "getNotificationCategories" {
            getNotificationCategories(result: result)
        }
    }
}

class NotifeeStreamHandler: NSObject, FlutterStreamHandler {
    public func onListen(withArguments _: Any?, eventSink events: @escaping FlutterEventSink) -> FlutterError? {
        NotifeePluginSwift.eventSink = events
        return nil
    }

    public func onCancel(withArguments _: Any?) -> FlutterError? {
        NotifeePluginSwift.eventSink = nil
        return nil
    }
}
