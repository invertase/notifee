import firebase from 'firebase/compat/app';

const firebaseConfig = {
  apiKey: 'AIzaSyAT1mBDevSlggcqLDNnEAxCmj1KnSuMTtU',
  authDomain: 'rnfirebase-auth-template.firebaseapp.com',
  projectId: 'rnfirebase-auth-template',
  storageBucket: 'rnfirebase-auth-template.appspot.com',
  messagingSenderId: '725336377227',
  appId: '1:725336377227:web:d7c6e3fb3d23f9d506118e',
  measurementId: 'G-9BECK74F26',
};

const initializeApp = (): void => {
  firebase.initializeApp(firebaseConfig);
};

export default initializeApp;
