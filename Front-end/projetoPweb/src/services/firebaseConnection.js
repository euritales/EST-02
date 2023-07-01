
import { initializeApp } from 'firebase/app'
import { getAuth } from 'firebase/auth'
import { getFirestore } from 'firebase/firestore'
import { getStorage } from 'firebase/storage';

const firebaseConfig = {
  apiKey: "AIzaSyCC6tdll1NSARwsCg0q42LQRLcL-hcW57o",
  authDomain: "e-medical-ae708.firebaseapp.com",
  projectId: "e-medical-ae708",
  storageBucket: "e-medical-ae708.appspot.com",
  messagingSenderId: "260769374506",
  appId: "1:260769374506:web:70e8bf6665835990657e53",
  measurementId: "G-00QHHEJDJ5"
};

const firebaseApp = initializeApp(firebaseConfig);

const auth = getAuth(firebaseApp);
const db = getFirestore(firebaseApp);
const storage = getStorage(firebaseApp);

export { auth, db, storage };