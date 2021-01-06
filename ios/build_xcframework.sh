#!/bin/bash
set -e

WORKING_DIR=$(pwd)
FRAMEWORK_FOLDER_NAME="Notifee_XCFramework"
FRAMEWORK_NAME="NotifeeCore"
FRAMEWORK_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/${FRAMEWORK_NAME}.xcframework"
BUILD_SCHEME="NotifeeCore"
SIMULATOR_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/simulator.xcarchive"
IOS_DEVICE_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/iOS.xcarchive"
CATALYST_ARCHIVE_PATH="${WORKING_DIR}/${FRAMEWORK_FOLDER_NAME}/catalyst.xcarchive"
OUTPUT_FOLDER=${WORKING_DIR}/../packages/react-native/ios/

rm -rf "${WORKING_DIR:?}/${FRAMEWORK_FOLDER_NAME}"
echo "Deleted ${FRAMEWORK_FOLDER_NAME}"
mkdir "${FRAMEWORK_FOLDER_NAME}"
echo "Created ${FRAMEWORK_FOLDER_NAME}"

echo "Archiving for iOS Simulator"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination="iOS Simulator" -archivePath "${SIMULATOR_ARCHIVE_PATH}" \
  -sdk iphonesimulator SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES | xcpretty -k

echo "Archiving for iOS"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination="iOS" -archivePath "${IOS_DEVICE_ARCHIVE_PATH}" \
  -sdk iphoneos SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES | xcpretty -k

# Do not be tempted to use "-sdk macosx" instead of the "destination" argument. It switches you to AppKit from UIKit
# https://developer.apple.com/forums/thread/120542?answerId=374124022#374124022
echo "Archiving for Mac Catalyst"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination='platform=macOS,arch=x86_64,variant=Mac Catalyst' -archivePath "${CATALYST_ARCHIVE_PATH}" \
  SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES | xcpretty -k

echo "Packaging archives into ${FRAMEWORK_NAME}.xcframework bundle"
xcodebuild -create-xcframework \
  -framework "${SIMULATOR_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${IOS_DEVICE_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${CATALYST_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -output "${FRAMEWORK_PATH}" | xcpretty

rm -rf "${SIMULATOR_ARCHIVE_PATH}"
rm -rf "${IOS_DEVICE_ARCHIVE_PATH}"
rm -rf "${CATALYST_ARCHIVE_PATH}"

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
