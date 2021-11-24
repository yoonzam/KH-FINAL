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

/* eslint-env browser, serviceworker, es6 */

'use strict';

//브라우저는 푸시 메시지를 수신하고 푸시의 대상이 되는 서비스 워커를 발견한 후 그 서비스 워커를 깨워 푸시 이벤트를 발송
//이 이벤트를 수신 대기하고 이벤트를 수신했을 때 그 결과로서 알림을 표시

//self : 서비스 워커 자체를 참조하고 있음
self.addEventListener('push', function(event) {
  console.log('[Service Worker] Push Received.');
  console.log(`[Service Worker] Push had this data: "${event.data.text()}"`);

  const title = 'Push Codelab';
  const options = {
    body: 'Yay it works.',
    icon: 'images/icon.png',
    // badge: 'images/badge.png' //안드에서만
  };

  event.waitUntil(self.registration.showNotification(title, options));  //알림생성
  //const notificationPromise = self.registration.showNotification(title, options);
  //event.waitUntil(notificationPromise); //전달된 프라미스가 확인될 때까지 서비스 워커를 활성화 및 실행 상태로 유지
  
});

self.addEventListener('notificationclick', function(event) {
  console.log('[Service Worker] Notification click Received.');

  event.notification.close();

  event.waitUntil(
    clients.openWindow('https://developers.google.com/web/')
  );
});