#!/bin/bash
set -eo pipefail

WORKING_DIR=$(pwd)
POD_NAME="NotifeeCore"
POD_PATH="${WORKING_DIR}/ios/${POD_NAME}/"
POD_OUTPUT_FOLDER=${WORKING_DIR}/packages/react-native/ios/NotifeeCore/

rm -rf "${POD_OUTPUT_FOLDER}"
echo "Deleted ${POD_OUTPUT_FOLDER}"
mkdir "${POD_OUTPUT_FOLDER}"
echo "Created ${POD_OUTPUT_FOLDER}"

# - Copy the core pod
echo "Moving core into packages/react-native submodule"
cp -rp "${POD_PATH}" "${POD_OUTPUT_FOLDER}"
