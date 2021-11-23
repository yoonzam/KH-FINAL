/**
 * 
 */
 //https://firebase.google.com/docs/cloud-messaging/js/client?authuser=0#retrieve_a_messaging_object
 //메시징 객체 가져오기
 import { getMessaging } from "firebase/messaging";

const messaging = getMessaging();

//토큰 액세스	getToken() : Promise<String>객체 반환
getToken(messaging, { vapidKey: 'BDaUhaUUutwgMI44dAQhkANJgRcgHHWWlEI05fvaQswJf5RmJrupDaTIiSGM1h9xxeaZcR13_lzGKZpTi07ahCs' })
.then((currentToken) => {
  if (currentToken) {
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