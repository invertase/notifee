import firebase from 'firebase/compat/app';
import 'firebase/compat/messaging';

import initializeApp from './firebase-init.ts';
initializeApp();

const messaging = firebase.messaging;

export default messaging;
