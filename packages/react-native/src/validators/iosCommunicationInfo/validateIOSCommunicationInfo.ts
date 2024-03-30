import { IOSCommunicationInfo } from '../../types/NotificationIOS';
import { isObject, isString } from '../../utils';
import validateIOSCommunicationInfoPerson from './validateIOSCommunicationInfoPerson';

export default function validateIOSCommunicationInfo(
  communicationInfo: IOSCommunicationInfo,
): IOSCommunicationInfo {
  if (!isObject(communicationInfo)) {
    throw new Error('expected an object.');
  }

  if (
    !isString(communicationInfo.conversationId) ||
    communicationInfo.conversationId.length === 0
  ) {
    throw new Error("'conversationId' expected a valid string value.");
  }

  if (!communicationInfo.sender || !isObject(communicationInfo.sender)) {
    throw new Error("'sender' expected a valid object value.");
  }

  let sender;

  try {
    sender = validateIOSCommunicationInfoPerson(communicationInfo.sender);
  } catch (e: any) {
    throw new Error(`'sender' ${e.message}.`);
  }

  const out: IOSCommunicationInfo = {
    conversationId: communicationInfo.conversationId,
    sender,
  };

  if (communicationInfo.body) {
    if (!isString(communicationInfo.body)) {
      throw new Error("'body' expected a valid string value.");
    }

    out.body = communicationInfo.body;
  }

  if (communicationInfo.groupName) {
    if (!isString(communicationInfo.groupName)) {
      throw new Error("'groupName' expected a valid string value.");
    }

    out.groupName = communicationInfo.groupName;
  }

  if (communicationInfo.groupAvatar) {
    if (!isString(communicationInfo.groupAvatar)) {
      throw new Error("'groupAvatar' expected a valid string value.");
    }

    out.groupAvatar = communicationInfo.groupAvatar;
  }

  return out;
}
