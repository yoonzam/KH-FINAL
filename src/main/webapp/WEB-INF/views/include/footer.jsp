<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<footer>
		Copyright 2021 by TEAM EAT'SMAP. All Rights Reserved.
	</footer>
<script type="text/javascript" src="/resources/js/firebase/firebase-init.js"></script>
<script type="text/javascript" src="/resources/js/firebase/firebase-app.js"></script>
<script type="text/javascript" src="/resources/js/firebase/firebase-messaging.js"></script>	
<script defer type="text/javascript" src="/resources/js/calendar/calendar.js"></script>
<script type="text/javascript">

	/* push시작 */
	firebase.initializeApp({
		    apiKey: "AIzaSyCNuQD68Jw_abElCM3B0XK-n4fNY-H7oJ0",
		    authDomain: "final-332911.firebaseapp.com",
		    databaseURL: "https://final-332911-default-rtdb.asia-southeast1.firebasedatabase.app",
		    projectId: "final-332911",
		    storageBucket: "final-332911.appspot.com",
		    messagingSenderId: "197983811902",
		    appId: "1:197983811902:web:0bc80429719a225823d46f",
		    measurementId: "G-MGDN4W8XFL"
	});	
	
	const messaging = firebase.messaging();
	
	const noticeDiv = 'bell-list';
	const noticeLi = 'notice';
	
	
 	messaging.onMessage((payload) => {
    
	    console.log('[firebase-messaging-sw.js] Received foreground message ', payload);
	    // Customize notification here
	    const notificationTitle = '[on]잇츠맵에서 알립니다!!';
	    const notificationOptions = {
	      body: '방문일정 D-DAY가 하루 남았어요',
	      icon: '/resources/img/member/user.png',
	  	  onclick: "location.href='http://localhost:9090/calendar/'"    
	    };
	
	    self.registration.showNotification(notificationTitle, notificationOptions);
    
    // Update the UI to include the received message.
    //appendMessage(payload);
	}); 
	
	function resetUI() {
	    // Get registration token. Initially this makes a network call, once retrieved
	    // subsequent calls to getToken will return from cache.
	    messaging.getToken({vapidKey: 'BDaUhaUUutwgMI44dAQhkANJgRcgHHWWlEI05fvaQswJf5RmJrupDaTIiSGM1h9xxeaZcR13_lzGKZpTi07ahCs'})
	    .then((currentToken) => {
	      if (currentToken) {
	    	  console.dir('token : ' + currentToken);
	    	  //clientToken = currentToken;	//execute()실행시 담길 값
	    	  fetch('http://localhost:9090/member/saveToken/' + currentToken)
	    	  	.then(response => {
	    	  		if(response.ok) console.dir('성공');
	    	  		else throw new Error(response.status);	
	    		}).catch((error) => {
	    			  console.error('Error', error);
	    		}) 
	    	  
	        sendTokenToServer(currentToken);
	        //updateUIForPushEnabled(currentToken);
	      } else {
	        console.log('No registration token available. Request permission to generate one.');
		    // Show permission request.
	        requestPermission();
	        // Show permission UI.
	        updateUIForPushPermissionRequired();
	        setTokenSentToServer(false);
	      }
	    }).catch((err) => {
	      console.log('An error occurred while retrieving token. ', err);
	      setTokenSentToServer(false);
	    });
	  }
		
	
  // Send the registration token your application server, so that it can:
  // - send messages back to this app
  // - subscribe/unsubscribe the token from topics
  function sendTokenToServer(currentToken) {
    if (!isTokenSentToServer()) {
      console.log('Sending token to server...');
      // TODO(developer): Send the current token to your server.
      setTokenSentToServer(true);
    } else {
      console.log('Token already sent to server so won\'t send it again ' +
          'unless it changes');
    }
  }
		  
  function isTokenSentToServer() {
	    return window.localStorage.getItem('sentToServer') === '1';
  }

  function setTokenSentToServer(sent) {
    window.localStorage.setItem('sentToServer', sent ? '1' : '0');
  }

  function showHideDiv(elementClass, show) {
    const div = document.querySelector('.' + elementClass);
    if (show) {
      div.style = 'display: visible';
    } else {
      div.style = 'display: none';
    }
  }
	  
   function requestPermission() {
	    console.log('Requesting permission...');
	    Notification.requestPermission().then((permission) => {
	      if (permission === 'granted') {
	        console.log('Notification permission granted.');
	        // TODO(developer): Retrieve a registration token for use with FCM.
	        // In many cases once an app has been granted notification permission,
	        // it should update its UI reflecting this.
	        resetUI();
	      } else {
	        console.log('Unable to get permission to notify.');
	      }
	    });
	  }

  
  function deleteToken() {
	    // Delete registration token.
	    messaging.getToken().then((currentToken) => {
	      messaging.deleteToken(currentToken).then(() => {
	        console.log('Token deleted.');
	        setTokenSentToServer(false);
	        // Once token is deleted update UI.
	        resetUI();
	      }).catch((err) => {
	        console.log('Unable to delete token. ', err);
	      });
	    }).catch((err) => {
	      console.log('Error retrieving registration token. ', err);
	      showToken('Error retrieving registration token. ', err);
	    });
	  }
  
  
  // Add a message to the messages element.
  function appendMessage(payload) {
    const messagesElement = document.querySelector('.noticeDiv');
    const dataElement = document.createElement('li');
    const bellElement = document.querySelector('.fa-bell');
    //dataElement.textContent = JSON.stringify(payload, null, 2);
    
    bellElement.textContent = ' 1';
    dataElement.style = 'overflow-x:hidden;';
    dataElement.className = 'notice'
    dataElement.textContent = '누군가 회원님과 잇친을 맺었어요!';
    
    
    messagesElement.appendChild(dataElement);
  }

  function updateUIForPushEnabled(currentToken) {
    showHideDiv(noticeDiv, true);
    //showHideDiv(permissionDivId, false);
    //showToken(currentToken);
    
  }

  function updateUIForPushPermissionRequired() {
    showHideDiv(noticeDiv, true);
    //showHideDiv(permissionDivId, true);
  }

  
	
</script>
</div>
<%@ include file="/WEB-INF/views/common/review.jsp" %>
<%@ include file="/WEB-INF/views/common/upload.jsp" %>
<%@ include file="/WEB-INF/views/calendar/makeSchedule.jsp" %>
