require 'json'
package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name                = "NotifeeCore"
  s.version             = "1.0.0"
  s.description         = "NotifeeCore"
  s.summary             = <<-DESC
                            NotifeeCore module - podspec
                          DESC
  s.homepage            = "https://notifee.app"
  s.license             = package['license']
  s.authors             = "Invertase Limited"
  s.source              = { :git => "https://github.com/invertase/notifee" }
  s.social_media_url    = 'http://twitter.com/notifee_app'

  s.ios.deployment_target   = '10.0'
  s.source_files             = 'NotifeeCore/*.{h,m}'
end
