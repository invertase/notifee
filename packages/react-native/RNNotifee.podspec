
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
  s.source              = { :git => "https://github.com/invertase/notifee", :tag => "v#{s.version}" }
  s.social_media_url    = 'http://twitter.com/notifee_app'

  s.cocoapods_version        = '>= 1.10.0'
  s.ios.deployment_target   = '10.0'

  s.dependency 'React-Core'
  s.source_files =  ['ios/RNNotifee/NotifeeApiModule.h', 'ios/RNNotifee/NotifeeApiModule.m']

  if defined?($NotifeeCoreFromSources) && $NotifeeCoreFromSources == true
    # internal dev flag used by Notifee devs, ignore
    Pod::UI.warn "RNNotifee: Using NotifeeCore from sources."
    s.dependency 'NotifeeCore'
  elsif defined?($NotifeeExtension) && $NotifeeExtension == true
    # App uses Notification Service Extension
    Pod::UI.warn "RNNotifee: using Notification Service Extension."
    s.dependency 'RNNotifeeCore'
  else
    s.subspec "NotifeeCore" do |ss|
      ss.source_files = "ios/NotifeeCore/*.{h,mm,m}"
    end
  end

end

