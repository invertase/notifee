#!/bin/bash
set -e
echo "Please ensure you run this from the packages/react-native directory inside the cloned invertase/notifee repo"
echo "You should only do this when your git working set is completely clean (e.g., git reset --hard)"
echo "You must have already run \`yarn\` in the repository so \`npx react-native\` will work"
echo "This scaffolding refresh has been tested on macOS, not on windows or linux"

# Copy the important files out temporarily
if [ -d TEMP ]; then
  echo "TEMP directory already exists - we use that to store files while refreshing."
elif [ -d example ]; then
  echo "Saving files to TEMP while refreshing scaffolding..."
  mkdir -p TEMP/android
  mkdir -p TEMP/android/app/src/main/java/com/example
  cp example/README.md TEMP/
  cp example/android/app/src/main/java/com/example/CustomActivity.java TEMP/android/app/src/main/java/com/example/

  cp -R example/src TEMP/
  cp example/App.tsx TEMP/
  cp -R example/__tests__ TEMP/
fi

# Purge the old sample
\rm -fr example

# Make the new example
echo "Creating example app"
npx react-native init example --template react-native-template-typescript
pushd example

# Update package.json
echo "Adding Notifee app package"
# add notifee dependency
npx json -I -f package.json -e 'this.dependencies["@notifee/react-native"] = "file:../"'
# add post install step for notifee
npx json -I -f package.json -e 'this.scripts.postinstall = "cd node_modules/@notifee/react-native && yarn"'

# Update compileSdkVersion and targetSdkVersion to 33
echo "Updating android/build.gradle"
sed -i -e $'s/ 31/ 33/' android/build.gradle
rm -f android/build.gradle??

echo "Updating AndroidManifest.xml"
sed -i "" -e $'s/android:name=".MainActivity"/android:name=".MainActivity"\\\n      android:showWhenLocked="true"\\\n        android:turnScreenOn="true"/;s/\<\/activity\>/\<\/activity\>\\\n      \<activity\\\n        android:name="com.example.CustomActivity"\\\n      android:showWhenLocked="true"\\\n      android:turnScreenOn="true"\\\n    \/\>/' android/app/src/main/AndroidManifest.xml

echo "Updating MainActivity.java"
sed -i "" -e $'s/package com.example;/package com.example;\\\nimport io.invertase.notifee.NotifeeApiModule;/;s/return "example"/return NotifeeApiModule.getMainComponent("example")/' android/app/src/main/java/com/example/MainActivity.java

echo "Updating iOS Podfile"
# This is just a speed optimization, very optional, but asks xcodebuild to use clang and clang++ without the fully-qualified path
# That means that you can then make a symlink in your path with clang or clang++ and have it use a different binary
# In that way you can install ccache or buildcache and get much faster compiles...
sed -i -e $'s/react_native_post_install(installer)/react_native_post_install(installer)\\\n\\\n    installer.pods_project.targets.each do |target|\\\n      target.build_configurations.each do |config|\\\n        config.build_settings["CC"] = "clang"\\\n        config.build_settings["LD"] = "clang"\\\n        config.build_settings["CXX"] = "clang++"\\\n        config.build_settings["LDPLUSPLUS"] = "clang++"\\\n      end\\\n    end/' ios/Podfile
rm -f ios/Podfile??

echo "Installing pods"
npx pod-install

# We use typescript and there are linter collisions with transitive dependencies on old versions
# Merge the result of a PR we made upstream so lint is clean even with our 3-deep layer of packages
yarn add --dev @react-native-community/eslint-config@^3

yarn

# Copy the important files back in
popd
echo "Copying notifee example files into refreshed example..."
cp -frv TEMP/* example/

# Clean up after ourselves
\rm -fr TEMP
