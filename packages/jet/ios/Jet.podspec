require 'json'
package = JSON.parse(File.read('../package.json'))

Pod::Spec.new do |s|
  s.name                = "Jet"
  s.version             = package["version"]
  s.summary             = package["description"]
  s.description         = <<-DESC
                            Accelerate your React Native module development by testing inside NodeJS; mock-free and native test code free. Perfect for module developers wanting full E2E testing & CI.
                          DESC
  s.homepage            = "http://invertase.io/jet"
  s.license             = package['license']
  s.authors             = "Mike Diarmid (Salakar)"
  s.source              = { :git => "https://github.com/invertase/jet.git", :tag => "v#{s.version}" }
  s.social_media_url    = 'http://twitter.com/invertaseio'
  s.platform            = :ios, "9.0"
  s.source_files        = '*.{h,m}'
  s.dependency          'React'
end
