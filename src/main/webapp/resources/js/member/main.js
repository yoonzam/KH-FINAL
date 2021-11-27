/*
*
*  Push Notifications codelab
*  Copyright 2015 Google Inc. All rights reserved.
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      https://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License
*
*/

/* eslint-env browser, es6 */

'use strict';

const applicationServerPublicKey = 'BP257zflNi2lOg1VPKUaupe2ZQV6x1Vt78ecYycOKdZIQRIbJk68tSYEMx_l6SZkUd1s_yC_dlu_jBwyiw6XDaE';
const pushButton = document.querySelector('.btn-bell');

let isSubscribed = false;
let swRegistration = null;

function urlB64ToUint8Array(base64String) {
  const padding = '='.repeat((4 - base64String.length % 4) % 4);
  const base64 = (base64String + padding)
    .replace(/\-/g, '+')
    .replace(/_/g, '/');

  const rawData = window.atob(base64);
  const outputArray = new Uint8Array(rawData.length);

  for (let i = 0; i < rawData.length; ++i) {
    outputArray[i] = rawData.charCodeAt(i);
  }
  return outputArray;
};

/* push api 실습 */
if ('serviceWorker' in navigator && 'PushManager' in window) {
  console.log('Service Worker and Push is supported');


  navigator.serviceWorker.register('/resources/firebase-messaging-sw.js')	//절대경로
  .then(function(swReg) {
    console.log('Service Worker is registered', swReg);

    swRegistration = swReg;
    //initialiseUI();
  })
  .catch(function(error) {
    console.error('Service Worker Error', error);
  });
} else {
  console.warn('Push messaging is not supported');
  pushButton.textContent = 'Push Not Supported';
};

/* 사용자 구독 상태 확인 */
function initialiseUI() {
  pushButton.addEventListener('click', function() { //버튼 클릭 리스너
    pushButton.disabled = true; //구독하는 동안 사용자가 버튼을 다시 클릭할 수 없도록
    if (isSubscribed) {
      unsubscribeUser();
    } else {
      subscribeUser();
    }
  });
  // Set the initial subscription value
  swRegistration.pushManager.getSubscription()
  .then(function(subscription) {
    isSubscribed = !(subscription === null);

    updateSubscriptionOnServer(subscription);

    if (isSubscribed) {
      console.log('User IS subscribed.');
    } else {
      console.log('User is NOT subscribed.');
    }

    updateBtn();
  });
}
/* 구독취소 */
function unsubscribeUser() {
  swRegistration.pushManager.getSubscription()  //현재구독 호출 -> PushSubscription Promise객체 반환 또는, 없으면 null반환
  .then(function(subscription) {  
    if (subscription) {
      return subscription.unsubscribe();  //Promise 반환
    }
  })
  .catch(function(error) {
    console.log('Error unsubscribing', error);
  })
  .then(function() {
    updateSubscriptionOnServer(null);

    console.log('User is unsubscribed.');
    isSubscribed = false;

    updateBtn();
  });
}
/* 구독하지 않은 상태일 때 */
function subscribeUser() {
  
  const applicationServerKey = urlB64ToUint8Array(applicationServerPublicKey);	//64 URL 안전 인코딩된 애플리케이션 서버의 공개 키를 취하여 UInt8Array로 변환
  
  //pushManager의 subscribe()를 통해 애플리케이션 서버의 공개키와 userVisibleOnly:true 제출
  swRegistration.pushManager.subscribe({  
    userVisibleOnly: true,  //푸시가 전송될 때마다 알림을 표시하도록 허용
    applicationServerKey: applicationServerKey
  })  //Promise 객체 반환
  .then((subscription) => {
      console.log('User is subscribed:', subscription);

      //updateSubscriptionOnServer(subscription);	//web-push api
      

      isSubscribed = true;

      updateBtn();
    })
  .catch(function(err) {
    console.log('Failed to subscribe the user: ', err);
    updateBtn();
  });
};

function updateSubscriptionOnServer(subscription) {
  // TODO: Send subscription to application server
/*
  const subscriptionJson = document.querySelector('.js-subscription-json');
  const subscriptionDetails =
    document.querySelector('.js-subscription-details');

  if (subscription) {
    subscriptionJson.textContent = JSON.stringify(subscription);
    subscriptionDetails.classList.remove('is-invisible');
  } else {
    subscriptionDetails.classList.add('is-invisible');
  }*/
};

/* 구독 상태에 따라 버튼 활성화 */
function updateBtn() {
  //권한이 denied인 경우 사용자 구독이 불가능해서 우리가 할 수 있는 일은 더 이상 없으므로, 버튼을 영구적으로 비활성화
  if (Notification.permission === 'denied') {
    pushButton.textContent = 'Push Messaging Blocked.';
    pushButton.disabled = true;
    updateSubscriptionOnServer(null);
    return;
  }

  if (isSubscribed) {	//구독
    pushButton.textContent = '구독중';
    document.querySelector('.bell-list').style.display ='block';
    
  } else {	//미구독
    pushButton.textContent = '구독하기';
    document.querySelector('.bell-list').style.display ='none';
  }
  pushButton.disabled = false;
}
















