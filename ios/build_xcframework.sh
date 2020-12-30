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
  -sdk iphonesimulator SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES

echo "Archiving for iOS"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination="iOS" -archivePath "${IOS_DEVICE_ARCHIVE_PATH}" \
  -sdk iphoneos SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES

echo "Archiving for Mac Catalyst"
xcodebuild archive -workspace "./${FRAMEWORK_NAME}.xcworkspace" -scheme ${BUILD_SCHEME} -configuration Release \
  -destination='platform=macOS,arch=x86_64,variant=Mac Catalyst' -archivePath "${CATALYST_ARCHIVE_PATH}" \
  SKIP_INSTALL=NO BUILD_LIBRARY_FOR_DISTRIBUTION=YES

echo "Packaging archives into ${FRAMEWORK_NAME}.xcframework bundle"
xcodebuild -create-xcframework \
  -framework "${SIMULATOR_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${IOS_DEVICE_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -framework "${CATALYST_ARCHIVE_PATH}/Products/Library/Frameworks/${FRAMEWORK_NAME}.framework" \
  -output "${FRAMEWORK_PATH}"

rm -rf "${SIMULATOR_ARCHIVE_PATH}"
rm -rf "${IOS_DEVICE_ARCHIVE_PATH}"
rm -rf "${CATALYST_ARCHIVE_PATH}"

echo "Installing framework into packages/react-native submodule"
rm -rf "${OUTPUT_FOLDER}/${FRAMEWORK_NAME}.xcframework" && \
  cp -a "${FRAMEWORK_PATH}" "${OUTPUT_FOLDER}"

echo "Cleaning up intermediate files"
rm -rf "${WORKING_DIR:?}/${FRAMEWORK_FOLDER_NAME}"
