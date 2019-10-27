const EXCLUDE_KEYS = [
  'uid',
  'extras',
  'actions',
  'stats',
  'mAdjustments',
  'mGlobalSortKey',
  'audioAttributes',
];

const keyNameOverrideMap = {
  pri: 'priority',
  effectiveNotificationChannel: 'channel',
  seen: 'hasSeen',
  opPkg: 'packageName',
  template: 'style',
  groupKey: 'group',
  fgServiceShown: 'foregroundServiceShown',
  subText: 'subtitle',
  tickerText: 'ticker',
  showChronometer: 'usesChronometer',
};

/**
 *
 * @param value
 */
export function parseValue(value: string): null | string | number | boolean {
  let _value = value;
  if (_value.startsWith('(') && _value.endsWith(')')) {
    _value = _value.substr(1, _value.length - 2);
  }

  if (_value.startsWith("'") && _value.endsWith("'")) {
    _value = _value.substr(1, _value.length - 2);
  }

  if (_value === 'true') return true;
  if (_value === 'false') return false;
  if (_value === 'null') return null;

  // bug on dumpsys format `hidden` parameter, keep for now
  if (_value === '=true') return true;
  // bug on dumpsys format `hidden` parameter, keep for now
  if (_value === '=false') return false;

  // numbers
  if (_value.match(/^[0-9.-]+$/gm)) {
    return _value.includes('.') ? parseFloat(_value) : parseInt(_value, 10);
  }

  return _value;
}

/**
 *
 * @param source
 */
function parseNotificationRecordIcon(source: string) {
  // https://regex101.com/r/WQGUcJ/5
  const matches = /^.*\s\/\s(.*)$/g.exec(source);
  if (!matches || !matches.length) return source;
  return matches[1] || source;
}

/**
 *
 * @param source
 */
function parseNotificationRecordKey(source: string) {
  let [, , id, tag] = source.split('|');
  return { id: parseValue(id || null), tag: parseValue(tag || null) };
}

/**
 *
 * @param source
 */
function parseNotificationRecordGroupKey(source: string) {
  const parts = source.split('|');
  if (!parts.length) return { group: null };
  const lastPart = parts[parts.length - 1];
  if (!lastPart.includes('g:')) return { group: null };
  return { group: parseValue(lastPart.replace(/^g:/, '')) };
}

function parseNotificationRecordChannel(source: string) {
  if (source == null || !source.length) return null;

  const newChannelObject = {};

  const delimitedProps = source
    .replace('NotificationChannel{', '')
    .replace(/}$/, '')
    .split(', ');

  for (let i = 0; i < delimitedProps.length; i++) {
    let [key, value] = delimitedProps[i].split(/=(.+)?/);
    key = replaceMPrefix(key);
    key = keyNameOverrideMap[key] || key;
    newChannelObject[key] = parseValue(value || 'null');
  }

  return newChannelObject;
}

/**
 *
 * @param source
 */
function parseNotificationRecordExtras(source: string) {
  // https://regex101.com/r/WQGUcJ/6
  const match = source.match(/^\s\s\s\s\s\sextras={\n(.|\n)*\s\s\s\s\s\s}/gm);
  if (!match) return {};
  const android = {};
  const custom = {};

  const lines = match[0]
    .split('\n')
    .filter((line: string) => line.startsWith(' '.repeat(8)))
    .map((line: string) => line.trim());

  for (let i = 0; i < lines.length; i++) {
    const line = lines[i];
    // https://regex101.com/r/WQGUcJ/8
    const lineMatches = /^(.*)=([^\n]+)$/g.exec(line);
    if (!lineMatches || !lineMatches.length) {
      console.log(`Failed to parse extras line: ${line}`);
      continue;
    }

    const [, key, valueSource] = lineMatches;
    const [type, value] = valueSource.split(/\s(.+)/);
    const valueParsed = parseValue(value === undefined ? type : value);

    if (key.startsWith('android.')) {
      // android keys like 'title'
      const keyWithoutPrefix = key.replace('android.', '');
      android[keyWithoutPrefix] = valueParsed;

      if (keyWithoutPrefix === 'remoteInputHistory' && valueParsed !== null) {
        const currentIndex = i;
        const inputArray = [];
        i += valueParsed as number;

        for (let j = 0; j < valueParsed; j++) {
          const inputElement = lines[currentIndex + j + 1];
          inputArray.push(inputElement.trim().replace(/\[[0-9]{1,2}]\s/, ''));
        }

        android[keyWithoutPrefix] = inputArray;
      }
    } else {
      // custom keys, e.g. keys set via `setExtras`
      custom[key] = valueParsed;
    }
  }

  return { android, custom };
}

/**
 *
 * @param source
 */
function parseNotificationRecordActions(source: string) {
  // https://regex101.com/r/WQGUcJ/9
  const match = source.match(/^\s\s\s\s\s\sactions={\n(.|\n)*?\s\s\s\s\s}/gm);
  if (!match) return [];
  const actions = [];

  const lines = match[0]
    .split('\n')
    .filter((line: string) => line.startsWith(' '.repeat(8)) && !line.endsWith('}'))
    .map((line: string) => line.trim());

  for (let i = 0; i < lines.length; i++) {
    const line = lines[i];
    // https://regex101.com/r/WQGUcJ/10
    const lineMatches = /^\[\d+]\s"(.*)"/g.exec(line);
    if (!lineMatches || !lineMatches.length) {
      console.log(`Failed to parse actions line: "${line}"`);
      continue;
    }
    actions.push(lineMatches[1]);
  }

  return actions;
}

/**
 *
 * @param key
 */
function replaceMPrefix(key: string) {
  return key.replace(/^m([A-Z])/g, (substring: string) => {
    return substring.substr(1).toLowerCase();
  });
}

/**
 *
 * @param source
 */
function parseNotificationRecord(source: string) {
  let m;
  const newNotificationRecord = { _raw: source };
  // https://regex101.com/r/WQGUcJ/4
  const regex = /^\s\s\s\s\s\s([a-zA-Z]*)=(.*)$/gm;

  while ((m = regex.exec(source)) !== null) {
    // This is necessary to avoid infinite loops with zero-width matches
    if (m.index === regex.lastIndex) {
      regex.lastIndex++;
    }

    const [, key, value] = m;
    if (!EXCLUDE_KEYS.includes(key)) {
      newNotificationRecord[keyNameOverrideMap[key] || key] = parseValue(value.trim());
    }
  }

  const parsedExtras = parseNotificationRecordExtras(source);
  Object.assign(newNotificationRecord, parsedExtras.android);
  newNotificationRecord['data'] = parsedExtras.custom;
  newNotificationRecord['actions'] = parseNotificationRecordActions(source);

  const newNotificationRecordCleaned = {};
  const unmappedKeyValues = Object.entries(newNotificationRecord);

  for (let i = 0; i < unmappedKeyValues.length; i++) {
    let key: string = unmappedKeyValues[i][0];
    let value: any = unmappedKeyValues[i][1];
    key = keyNameOverrideMap[key] || key;

    if (key === 'icon') {
      value = parseNotificationRecordIcon(value.trim());
    }

    if (key === 'style') {
      value = value.replace('android.app.Notification$', '');
    }

    if (key === 'channel') {
      value = parseNotificationRecordChannel(value);
    }

    if (key === 'key') {
      const parsedKeyProps = parseNotificationRecordKey(value.trim());
      Object.assign(newNotificationRecordCleaned, parsedKeyProps);
      continue;
    }

    if (key === 'group') {
      const parsedGroupKeyProps = parseNotificationRecordGroupKey(value.trim());
      Object.assign(newNotificationRecordCleaned, parsedGroupKeyProps);
      continue;
    }

    key = replaceMPrefix(key);

    newNotificationRecordCleaned[key] = value;
  }

  return newNotificationRecordCleaned;
}

/**
 *
 * @param source
 */
function parseNotificationList(source: string) {
  // https://regex101.com/r/WQGUcJ/1
  const matches = source.match(/Notification List:\n([\s\S]*?)(?=\n\n\s\s\S)/g);
  if (!matches || !matches.length) return [];

  // https://regex101.com/r/WQGUcJ/2
  const notificationRecordMatches = matches[0].match(
    /NotificationRecord(\s|\S)*?(?=NotificationRecord|$)/g,
  );
  if (!notificationRecordMatches || !notificationRecordMatches.length) return [];

  return notificationRecordMatches
    .map((recordMatch: string) => parseNotificationRecord(recordMatch))
    .filter(Boolean)
    .sort((a: { creationTimeMs: number }, b: { creationTimeMs: number }) =>
      Number(a.creationTimeMs < b.creationTimeMs),
    );
}

/**
 *
 * @param source
 */
export default function parseNotificationDumpsys(source: string) {
  return parseNotificationList(source);
}

/**
 *
 * @param string
 */
export function stringToHashCode(string: string) {
  let i;
  let char;
  let hash = 0;
  const length = string.length;

  if (length === 0) return hash;

  for (i = 0; i < length; i++) {
    char = string.charCodeAt(i);
    hash = (hash << 5) - hash + char;
    hash |= 0;
  }

  return hash;
}
