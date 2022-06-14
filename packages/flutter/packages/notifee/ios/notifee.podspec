require 'yaml'

pubspec = YAML.load_file(File.join('..', 'pubspec.yaml'))
library_version = pubspec['version'].gsub('+', '-')

Pod::Spec.new do |s|
  s.name             = pubspec['name']
  s.version          = library_version
  s.summary          = pubspec['description']
  s.description      = pubspec['description']
  s.homepage         = pubspec['homepage']
  s.license          = { :file => '../LICENSE' }
  s.source           = { :path => '.' }

  s.source_files = 'Classes/**/*'

  s.ios.deployment_target = '10.0'
  s.authors = 'Invertase'
  s.dependency 'Flutter'
  s.swift_version = '5.0'

#   if defined?($NotifeeCoreFromSources) && $NotifeeCoreFromSources == true
    # internal dev flag used by Notifee devs, ignore
  Pod::UI.warn "RNNotifee: Using NotifeeCore from sources."
  s.dependency 'NotifeeCore'
#   elsif defined?($NotifeeExtension) && $NotifeeExtension == true
#     # App uses Notification Service Extension
#     Pod::UI.warn "RNNotifee: using Notification Service Extension."
#     s.dependency 'RNNotifeeCore'
#   else
#     s.subspec "NotifeeCore" do |ss|
#       ss.source_files = "ios/NotifeeCore/*.{h,mm,m}"
#     end
#   end

  # Flutter.framework does not contain a i386 slice.
  s.static_framework = true
  s.pod_target_xcconfig = {
  'GCC_PREPROCESSOR_DEFINITIONS' => "LIBRARY_VERSION=\\@\\\"#{library_version}\\\" LIBRARY_NAME=\\@\\\"flutter-fire-ml-downloader\\\"",
  'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386'
  }
end
