/**
 * 
 */

/*
import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from 'firebase/messaging';
*/
// If you do not serve/host your project using Firebase Hosting see https://firebase.google.com/docs/web/setup

importScripts('https://www.gstatic.com/firebasejs/9.2.0/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.2.0/firebase-messaging-compat.js');


const firebaseConfig = {
    apiKey: "AIzaSyCNuQD68Jw_abElCM3B0XK-n4fNY-H7oJ0",
    authDomain: "final-332911.firebaseapp.com",
    databaseURL: "https://final-332911-default-rtdb.asia-southeast1.firebasedatabase.app",
    projectId: "final-332911",
    storageBucket: "final-332911.appspot.com",
    messagingSenderId: "197983811902",
    appId: "1:197983811902:web:0bc80429719a225823d46f",
    measurementId: "G-MGDN4W8XFL"
};

firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();


/* 토큰 액세스 */

// Get registration token. Initially this makes a network call, once retrieved
// subsequent calls to getToken will return from cache.
messaging.getToken(messaging, { vapidKey: 'BDaUhaUUutwgMI44dAQhkANJgRcgHHWWlEI05fvaQswJf5RmJrupDaTIiSGM1h9xxeaZcR13_lzGKZpTi07ahCs' }
).then((currentToken) => {
  if (currentToken) {
	console.log('유진토큰 : ' + currentToken);
	
    // Send the token to your server and update the UI if necessary
    // ...
  } else {
    // Show permission request UI
    console.log('No registration token available. Request permission to generate one.');
    // ...
  }
}).catch((err) => {
  console.log('An error occurred while retrieving token. ', err);
  // ...
});

messaging.onMessage(function(payload) {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  // Customize notification here
  const notificationTitle = '[on]잇츠맵에서 알립니다!!';
  const notificationOptions = {
    body: '방문일정 D-DAY가 하루 남았어요',
    icon: '/resources/img/member/user.png',
	onclick: "location.href='http://localhost:9090/calendar/'"    
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});

/* https://firebase.google.com/docs/cloud-messaging/concept-options */
messaging.onBackgroundMessage(function(payload) {
  console.log('[firebase-messaging-sw.js] Received background message ', payload);
  // Customize notification here
  const notificationTitle = '[BACK]잇츠맵에서 알립니다!!';
  const notificationOptions = {
    body: '잇츠맵에 새로운 알림이 도착했어요! 지금바로 확인해보세요~',
    icon: '/resources/img/member/alarm.png',
    onclick: "location.href='http://localhost:9090/calendar/'"    
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});

