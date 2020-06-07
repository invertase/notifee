#!/usr/bin/env bash
#
# Copyright (c) 2016-present Invertase Limited & Contributors
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this library except in compliance with the License.
# You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
set -e

_MAX_LOOKUPS=2;
_SEARCH_RESULT=''
_RN_ROOT_EXISTS=''
_CURRENT_LOOKUPS=1
_JSON_ROOT="'ios'"
_JSON_FILE_NAME='notifee.config.json'
_JSON_OUTPUT_BASE64='e30=' # { }
_CURRENT_SEARCH_DIR=${PROJECT_DIR}
_PLIST_BUDDY=/usr/libexec/PlistBuddy
_TARGET_PLIST="${BUILT_PRODUCTS_DIR}/${INFOPLIST_PATH}"
_DSYM_PLIST="${DWARF_DSYM_FOLDER_PATH}/${DWARF_DSYM_FILE_NAME}/Contents/Info.plist"

# plist arrays
_PLIST_ENTRY_KEYS=()
_PLIST_ENTRY_TYPES=()
_PLIST_ENTRY_VALUES=()

function setPlistValue {
  echo "info:      setting plist entry '$1' of type '$2' in file '$4'"
  ${_PLIST_BUDDY} -c "Add :$1 $2 '$3'" "$4" || echo "info:      '$1' already exists"
}

echo "info: -> NOTIFEE build script started"
echo "info: 1) Locating ${_JSON_FILE_NAME} file:"

if [[ -z ${_CURRENT_SEARCH_DIR} ]]; then
  _CURRENT_SEARCH_DIR=$(pwd)
fi;

while true; do
  _CURRENT_SEARCH_DIR=$(dirname "$_CURRENT_SEARCH_DIR")
  if [[ "$_CURRENT_SEARCH_DIR" == "/" ]] || [[ ${_CURRENT_LOOKUPS} -gt ${_MAX_LOOKUPS} ]]; then break; fi;
  echo "info:      ($_CURRENT_LOOKUPS of $_MAX_LOOKUPS) Searching in '$_CURRENT_SEARCH_DIR' for a ${_JSON_FILE_NAME} file."
  _SEARCH_RESULT=$(find "$_CURRENT_SEARCH_DIR" -maxdepth 2 -name ${_JSON_FILE_NAME} -print | head -n 1)
  if [[ ${_SEARCH_RESULT} ]]; then
    echo "info:      ${_JSON_FILE_NAME} found at $_SEARCH_RESULT"
    break;
  fi;
  _CURRENT_LOOKUPS=$((_CURRENT_LOOKUPS+1))
done

if [[ ${_SEARCH_RESULT} ]]; then
  _JSON_OUTPUT_RAW=$(cat "${_SEARCH_RESULT}")
  _RN_ROOT_EXISTS=$(ruby -e "require 'rubygems';require 'json'; output=JSON.parse('$_JSON_OUTPUT_RAW'); puts output[$_JSON_ROOT]" || echo '')

  if [[ ${_RN_ROOT_EXISTS} ]]; then
    _JSON_OUTPUT_BASE64=$(python -c 'import json,sys,base64;print(base64.b64encode(json.dumps(json.loads(open('"'${_SEARCH_RESULT}'"').read())['${_JSON_ROOT}'])))' || echo "e30=")
  fi

  _PLIST_ENTRY_KEYS+=("notifee_json_raw")
  _PLIST_ENTRY_TYPES+=("string")
  _PLIST_ENTRY_VALUES+=("$_JSON_OUTPUT_BASE64")
else
  _PLIST_ENTRY_KEYS+=("notifee_json_raw")
  _PLIST_ENTRY_TYPES+=("string")
  _PLIST_ENTRY_VALUES+=("$_JSON_OUTPUT_BASE64")
  echo "warning:   A notifee.config.json file was not found."
fi;

echo "info: 2) Injecting Info.plist entries: "

# Log out the keys we're adding
for i in "${!_PLIST_ENTRY_KEYS[@]}"; do
  echo "    ->  $i) ${_PLIST_ENTRY_KEYS[$i]}" "${_PLIST_ENTRY_TYPES[$i]}" "${_PLIST_ENTRY_VALUES[$i]}"
done

for plist in "${_TARGET_PLIST}" "${_DSYM_PLIST}" ; do
  if [[ -f ${plist} ]]; then
    for i in "${!_PLIST_ENTRY_KEYS[@]}"; do
      setPlistValue "${_PLIST_ENTRY_KEYS[$i]}" "${_PLIST_ENTRY_TYPES[$i]}" "${_PLIST_ENTRY_VALUES[$i]}" "${plist}"
    done
  else
    echo "warning:   A Info.plist build output file was not found (${plist})"
  fi
done

echo "info: <- NOTIFEE build script finished"
