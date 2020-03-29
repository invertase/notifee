Pod::Spec.new do |s|
  s.name                = "NotifeeCore"
  s.version             = "1.0.0"
  s.description         = "NotifeeCore"
  s.summary             = <<-DESC
                            NotifeeCore module - podspec internal use only
                          DESC
  s.homepage            = "https://notifee.app"
  s.license             = "PRIVATE"
  s.authors             = "Invertase Limited"
  s.source              = { :git => "https://github.com/notifee/react-native-notifee", :tag => "v#{s.version}" }
  s.social_media_url    = 'http://twitter.com/notifee_app'

  s.ios.deployment_target   = '10.0'
  s.source_files             = 'NotifeeCore/**/*.{h,m}'
end
