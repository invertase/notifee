import { IOSCommunicationInfoPerson } from '../../types/NotificationIOS';
import { objectHasProperty, isObject, isString, isUndefined } from '../../utils';

export default function validateIOSCommunicationInfoPerson(
  person: IOSCommunicationInfoPerson,
): IOSCommunicationInfoPerson {
  if (!isObject(person)) {
    throw new Error("'person' expected an object.");
  }

  if (!isString(person.id) || person.id.length === 0) {
    throw new Error('"person.id" expected a valid string value.');
  }

  if (!isString(person.displayName) || person.displayName.length === 0) {
    throw new Error('"person.displayName" expected a valid string value.');
  }

  const out: IOSCommunicationInfoPerson = {
    id: person.id,
    displayName: person.displayName,
  };

  if (objectHasProperty(person, 'avatar') && !isUndefined(person.avatar)) {
    if (!isString(person.avatar)) {
      throw new Error('"person.avatar" expected a valid object value.');
    }

    out.avatar = person.avatar;
  }

  return out;
}
