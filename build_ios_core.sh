#!/bin/bash
set -eo pipefail

WORKING_DIR=$(pwd)
POD_NAME="NotifeeCore"
POD_PATH="${WORKING_DIR}/ios/${POD_NAME}"
POD_OUTPUT_FOLDER_BASE=${WORKING_DIR}/packages/react-native/ios

rm -rf "${POD_OUTPUT_FOLDER_BASE:?}/${POD_NAME}"
echo "Deleted ${POD_OUTPUT_FOLDER_BASE:?}/${POD_NAME}"

# - Copy the core pod
echo "Moving core into packages/react-native submodule"
cp -rvp "${POD_PATH}" "${POD_OUTPUT_FOLDER_BASE}/"
