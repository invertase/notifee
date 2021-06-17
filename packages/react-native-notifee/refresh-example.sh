#!/bin/bash
set -e

echo "You should run this from directory where you have cloned the @notifee/react-native repo"
echo "You should only do this when your git working set is completely clean (e.g., git reset --hard)"
echo "You must run \`npm install react-native-cli -g\` prior to running this script"
echo "You must have already run \`yarn\` in the repository so \`npx react-native\` will work"
echo "This scaffolding refresh has been tested on macOS, if you use it on linux, it might not work"

# Copy the important files out temporarily
if [ -d TEMP ]; then
  echo "TEMP directory already exists - we use that to store files while refreshing."
#   exit 1
elif [ -d example ]; then
  echo "Saving files to TEMP while refreshing scaffolding..."
  mkdir -p TEMP/android
  mkdir -p TEMP/android/app/src/main/java/com/example
  cp example/README.md TEMP/
  cp example/android/local.properties TEMP/android/ || true
  cp example/android/app/src/main/AndroidManifest.xml TEMP/android/app/src/main/
  cp example/android/app/src/main/java/com/example/MainActivity.java TEMP/android/app/src/main/java/com/example/
  cp example/android/app/src/main/java/com/example/CustomActivity.java TEMP/android/app/src/main/java/com/example/

  cp -R example/src TEMP/
  cp example/App.tsx TEMP/
  cp example/tsconfig.json TEMP/
  cp -R example/__tests__ TEMP/
fi

# Purge the old sample
\rm -fr example

# Make the new example
echo "Creating example app"
npx react-native init example
pushd example
echo "Adding Notifee app package"
yarn add github:notifee/react-native-notifee

# add post install step for notifee
npx json -I -f package.json -e 'this.scripts.postinstall = "cd node_modules/@notifee/react-native && yarn"'

# cd node_modules/@notifee/react-native && yarn add genversion && cd ../../..

# react-native 0.60 is cocoapods mainly now, so run pod install after installing @notifee/react-native
echo "Installing pods"
cd ios && pod install && cd ..

# remove App.js in favour of our custom App.tsx
rm App.js

# We use typescript and there are linter collisions with transitive dependencies on old versions
# Merge the result of a PR we made upstream so lint is clean even with our 3-deep layer of packages
yarn add @react-native-community/eslint-config@^3

rm -f ios/Podfile??

yarn

# Copy the important files back in
popd
echo "Copying notifee example files into refreshed example..."
cp -frv TEMP/* example/

# Clean up after ourselves
\rm -fr TEMP