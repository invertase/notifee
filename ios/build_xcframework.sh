#!/bin/bash
set -e

WORKING_DIR=$(pwd)
FRAMEWORK_FOLDER_NAME="Notifee_XCFramework"
FRAMEWORK_NAME="NotifeeCore"
FRAMEWORK_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/${FRAMEWORK_NAME}.xcframework"
BUILD_SCHEME="NotifeeCore"
IOS_SIMULATOR_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/iOS_simulator.xcarchive"
IOS_DEVICE_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/iOS.xcarchive"
CATALYST_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/catalyst.xcarchive"
TVOS_SIMULATOR_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/tvOS_simulator.xcarchive"
TVOS_DEVICE_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/tvOS.xcarchive"
WATCHOS_SIMULATOR_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/watchOS_simulator.xcarchive"
WATCHOS_DEVICE_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/watchOS.xcarchive"
MACOS_DEVICE_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/macOS.xcarchive"
OUTPUT_FOLDER=${WORKING_DIR}/../packages/react-native/ios/

rm -rf "${WORKING_DIR:?}/${FRAMEWORK_FOLDER_NAME}"
echo "Deleted ${FRAMEWORK_FOLDER_NAME}"
mkdir "${FRAMEWORK_FOLDER_NAME}"
echo "Created ${FRAMEWORK_FOLDER_NAME}"

echo "Archiving for iOS Simulator"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination="iOS Simulator" -archivePath "${IOS_SIMULATOR_ARCHIVE_PATH}" \
  -sdk iphonesimulator SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES | xcpretty -k

echo "Archiving for iOS"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination="iOS" -archivePath "${IOS_DEVICE_ARCHIVE_PATH}" \
  -sdk iphoneos SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES | xcpretty -k

# Do not be tempted to use "-sdk macosx" instead of the "destination" argument. It switches you to AppKit from UIKit
# Do not be tempted to use "-sdk iphoneos" either, or you end up with the incorrect fat framework binary file
# https://developer.apple.com/forums/thread/120542?answerId=374124022#374124022
echo "Archiving for Mac Catalyst"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination='platform=macOS,variant=Mac Catalyst' -archivePath "${CATALYST_ARCHIVE_PATH}" \
  SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES | xcpretty -k

echo "Archiving for tvOS Simulator"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination="tvOS Simulator" -archivePath "${TVOS_SIMULATOR_ARCHIVE_PATH}" \
  -sdk appletvsimulator SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES | xcpretty -k

echo "Archiving for tvOS"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination="tvOS" -archivePath "${TVOS_DEVICE_ARCHIVE_PATH}" \
  -sdk appletvos SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES | xcpretty -k

echo "Archiving for watchOS Simulator"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination="watchOS Simulator" -archivePath "${WATCHOS_SIMULATOR_ARCHIVE_PATH}" \
  -sdk watchsimulator SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES | xcpretty -k

echo "Archiving for watchOS"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination="watchOS" -archivePath "${WATCHOS_DEVICE_ARCHIVE_PATH}" \
  -sdk watchos SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES | xcpretty -k

echo "Archiving for macOS"
\rm -fr ./build
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination="platform=macOS" -archivePath "${MACOS_DEVICE_ARCHIVE_PATH}" \
  -sdk macosx -SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES \
  -derivedDataPath ./build | xcpretty -k

# macOS builds for some reason drop things in the DerivedData path, but do not copy them correctly to the framework.
mkdir -p "${MACOS_DEVICE_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework"
# This copies and flattens (removes the 'Versions/Current' symlink path components) from the archive output
# That is similar to the macOS Catalyst build flattening, but we do that post xcframework creation, here we do it before
cp -r ."/build/Build/Intermediates.noindex/ArchiveIntermediates/${FRAMEWORK_NAME}/IntermediateBuildFilesPath/UninstalledProducts/macosx/${FRAMEWORK_NAME}.framework/Versions/Current/" \
  "${MACOS_DEVICE_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework"

echo "Packaging archives into ${FRAMEWORK_NAME}.xcframework bundle"
xcodebuild -create-xcframework \
  -framework "${IOS_SIMULATOR_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${IOS_DEVICE_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${CATALYST_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${TVOS_SIMULATOR_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${TVOS_DEVICE_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${WATCHOS_SIMULATOR_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${WATCHOS_DEVICE_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${MACOS_DEVICE_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -output "${FRAMEWORK_PATH}" # | xcpretty

# rm -rf "${IOS_SIMULATOR_ARCHIVE_PATH}"
# rm -rf "${IOS_DEVICE_ARCHIVE_PATH}"
# rm -rf "${CATALYST_ARCHIVE_PATH}"
# rm -rf "${TVOS_SIMULATOR_ARCHIVE_PATH}"
# rm -rf "${TVOS_DEVICE_ARCHIVE_PATH}"
# rm -rf "${WATCHOS_SIMULATOR_ARCHIVE_PATH}"
# rm -rf "${WATCHOS_DEVICE_ARCHIVE_PATH}"
# rm -rf "${MACOS_DEVICE_ARCHIVE_PATH}"

# Catalyst framework directory structure flattening step 1:
# - Copy the framework in a specific way: de-referencing symbolic links on purpose
echo "Installing framework into packages/react-native submodule"
rm -rf "${OUTPUT_FOLDER}/${FRAMEWORK_NAME}.xcframework" && \
  cp -rp "${FRAMEWORK_PATH}" "${OUTPUT_FOLDER}"

# Catalyst framework directory structure flattening step 2:
# - Remove the catalyst framework "Versions" directory structure after symbolic link de-reference
rm -rf "${OUTPUT_FOLDER}/${FRAMEWORK_NAME}.xcframework/ios-arm64_x86_64-maccatalyst/${FRAMEWORK_NAME}.framework/Versions"

echo "Cleaning up intermediate files"
rm -rf "${WORKING_DIR:?}/${FRAMEWORK_FOLDER_NAME}"
