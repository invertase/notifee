require 'json'
package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name                = "RNNotifee"
  s.version             = package["version"]
  s.description         = package["description"]
  s.summary             = <<-DESC
                            A feature rich local notifications library for React Native Android & iOS.
                          DESC
  s.homepage            = "https://notifee.app"
  s.license             = package['license']
  s.authors             = "Invertase Limited"
  s.source              = { :git => "https://github.com/notifee/react-native-notifee", :tag => "v#{s.version}" }
  s.social_media_url    = 'http://twitter.com/notifee_app'

  s.cocoapods_version        = '>= 1.10.0'
  s.ios.deployment_target    = '10.0'
  s.source_files             = 'ios/RNNotifee/*.{h,m}'
  s.dependency 'React-Core'

  if defined?($NotifeeCoreFromSources) && $NotifeeCoreFromSources == true
    # internal dev flag used by Notifee devs, ignore
    Pod::UI.warn "RNNotifee: Using NotifeeCore from sources."
    s.dependency 'NotifeeCore'
  else
    s.ios.vendored_frameworks = 'ios/NotifeeCore.xcframework'
    s.preserve_paths = 'ios/NotifeeCore.xcframework'
  end
end
