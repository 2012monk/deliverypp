importScripts('https://www.gstatic.com/firebasejs/8.4.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.3.1/firebase-messaging.js');

// <script src="https://www.gstatic.com/firebasejs/8.6.0/firebase-app.js"></script>
const firebaseConfig = {
    apiKey: "AIzaSyAFebVP2F7B8NnlELOMjIlAdm-qebvjd_8",
    authDomain: "delideli-c7932.firebaseapp.com",
    projectId: "delideli-c7932",
    storageBucket: "delideli-c7932.appspot.com",
    messagingSenderId: "559577135117",
    appId: "1:559577135117:web:e184e44ca5ea7235537d44"
};
  // Initialize Firebase
firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();