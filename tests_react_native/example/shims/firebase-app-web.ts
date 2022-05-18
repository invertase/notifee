import firebase from 'firebase/compat/app';

import initializeApp from './firebase-init.ts';
initializeApp();

const app = firebase.app();
export default app;
