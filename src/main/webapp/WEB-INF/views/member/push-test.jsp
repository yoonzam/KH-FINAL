<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="/resources/css/main/main.css" />
<script defer type="text/javascript" src="/resources/js/main/main.js"></script>

<!-- slick.js -->
<link rel="stylesheet" type="text/css" href="http://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<script type="text/javascript" src="http://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

<!-- 화살표 아이콘 -->
<link rel="stylesheet" href="http://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">

<!-- firebase v8 -->
<script defer src="https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js"></script>
<script defer src="https://www.gstatic.com/firebasejs/8.10.0/firebase-auth.js"></script>
<script defer src="https://www.gstatic.com/firebasejs/8.10.0/firebase-analytics.js"></script>
<script defer src="https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js"></script>

<script defer src="https://apis.google.com/js/api.js"></script>
<script defer src="/resources/js/member/push-messaging.js"></script>

</head>
<body>
<div class="wrap">
	<header>
		<div class="header pc">
			<h1><img src="/resources/img/common/logo_ver1.png"></h1>
			<ul class="gnb">
				<li onclick="location.href='/map/'">잇츠맵</li>
				<li onclick="location.href='/calendar/'">잇츠캘린더</li>
				<li onclick="location.href='/timeline/'">타임라인</li>
				<li onclick="location.href='/myeats/group'">마이잇츠</li>
			</ul>
			<div class="util">
				<div onclick="location.href='/member/logout'" class="btn-logout">로그아웃</div>
				<div class="btn-bell new"><i class="fas fa-bell"> 2</i></div>
				<!--div class="btn-bell"><i class="far fa-bell"></i></div-->
				<div class="bell-list" style="display: none;">
					<div class="bell-list-title">알림목록</div>
					<ul>
						<li>누군가 회원님과 잇친을 맺었어요!</li>
						<li>새로운 후기를 올려주세요!</li>
					</ul>
				</div>
				<div id="btnReview" class="btn-review">후기등록</div>
			</div>
		</div>
		<div class="header m">
			<div class="flex-wrap">
				<h1><img src="/resources/img/common/logo_ver1.png"></h1>
				<div class="util">
					<div class="btn-logout">로그아웃</div>
					<button disabled class="btn-bell new"><i class="fas fa-bell"> 2</i></button>
					<div class="bell-list" style="display: none;">
						<div class="bell-list-title">알림목록</div>
						<ul>
							<li>누군가 회원님과 잇친을 맺었어요!</li>
							<li>새로운 후기를 올려주세요!</li>
						</ul>
					</div>
					<div id="btnReview" class="btn-review">후기등록</div>
				</div>
			</div>
			<ul class="gnb">
				<li>잇츠맵</li>
				<li>잇츠캘린더</li>
				<li>타임라인</li>
				<li>마이잇츠</li>
			</ul>
		</div>
	</header>
	<section>
		<div class="section">
			<button onclick="authenticate().then(loadClient)">authorize and load</button>
			<button onclick="execute()">execute</button>
					
			<div class="main-visual">
				<img src="/resources/img/main/main.png">
			</div>
			<div class="search-wrap">
				<div class="search-area">
					<div class="search-form">
						<label>
							<input type="text" placeholder="찾으시는 맛집이나 유저 이름을 입력하세요."/>
						</label>
						<a class="filter-btn"><i class="fas fa-filter">검색필터</i></a>
						<a href="/main/search"><button type="submit"><i class="fas fa-search"></i></button></a>
					</div>
					<div class="filter-menu">
						<ul>
							<li>
								<span>선택</span>
								<label id="allCheck" class="checked">전체선택</label>
							</li>
							<li>
								<span>종류</span>
								<label class="checked">#한식<input type="checkbox" value="" checked/></label>
								<label class="checked">#중식<input type="checkbox" value="" checked/></label>
								<label class="checked">#양식<input type="checkbox" value="" checked/></label>
								<label class="checked">#일식<input type="checkbox" value="" checked/></label>
								<label class="checked">#카페,디저트<input type="checkbox" value="" checked/></label>
								<label class="checked">#술집<input type="checkbox" value="" checked/></label>
							</li>
							<li>
								<span>분위기</span>
								<label class="checked">#친근함<input type="checkbox" value="" checked/></label>
								<label class="checked">#고급짐<input type="checkbox" value="" checked/></label>
								<label class="checked">#가족<input type="checkbox" value="" checked/></label>
								<label class="checked">#데이트<input type="checkbox" value="" checked/></label>
								<label class="checked">#혼밥<input type="checkbox" value="" checked/></label>
							</li>
							<li>
								<span>가격대</span>
								<label class="checked">#가성비<input type="checkbox" value="" checked/></label>
								<label class="checked">#가심비<input type="checkbox" value="" checked/></label>
								<label class="checked">#1~2만원대<input type="checkbox" value="" checked/></label>
								<label class="checked">#2~3만원대<input type="checkbox" value="" checked/></label>
								<label class="checked">#3만원 이상<input type="checkbox" value="" checked/></label>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section>
		<div id="token_div" style="display: block;">
            <h4>Registration Token</h4>
            <p id="token" style="word-break: break-all;"></p>
            <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"
                    onclick="deleteToken()">Delete Token</button>
        </div>
	    <div id="permission_div" style="display: block;">
            <h4>Needs Permission</h4>
            <p id="token"></p>
            <button class="mdl-button mdl-js-button mdl-button--raised mdl-button--colored"
                    onclick="requestPermission()">Request Permission</button>
        </div>
          <!-- div to display messages received by this app. -->
        <div id="messages"></div>
	</section>
	<h2><i class="fas fa-utensils color-m"></i> 여기는 어떠세요? <span class="color-m">#데이트</span> <span class="color-m">#술집</span></h2>
	<section class="visual"> 
			<div class="eats-list">
				<div class="thum">
					<img src="/resources/img/upload/01.jpg">
				</div>
				<div class="info">
					<div class="eats-location">서울 영등포구</div>
					<div class="eats-name">스시 아루히1 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
					<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
					<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
				</div>
			</div>
			<div class="eats-list">
				<div class="thum" id="">
					<img src="/resources/img/upload/02.jpg">
				</div>
				<div class="info">
					<div class="eats-location">서울 영등포구</div>
					<div class="eats-name">스시 아루히2 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
					<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
					<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
				</div>
			</div>
			<div class="eats-list">
				<div class="thum" id="">
					<img src="/resources/img/upload/03.jpg">
				</div>
				<div class="info">
					<div class="eats-location">서울 영등포구</div>
					<div class="eats-name">스시 아루히3 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
					<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
					<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
				</div>
			</div>
			<div class="eats-list">
				<div class="thum" id="">
					<img src="/resources/img/upload/04.jpg">
				</div>
				<div class="info">
					<div class="eats-location">서울 영등포구</div>
					<div class="eats-name">스시 아루히4 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
					<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
					<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
				</div>
			</div>
			<div class="eats-list">
				<div class="thum" id="">
					<img src="/resources/img/upload/01.jpg">
				</div>
				<div class="info">
					<div class="eats-location">서울 영등포구</div>
					<div class="eats-name">스시 아루히5 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
					<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
					<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
				</div>
			</div>
			<div class="eats-list">
				<div class="thum" id="">
					<img src="/resources/img/upload/02.jpg">
				</div>
				<div class="info">
					<div class="eats-location">서울 영등포구</div>
					<div class="eats-name">스시 아루히6 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
					<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
					<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
				</div>
			</div>
			<div class="eats-list">
				<div class="thum" id="">
					<img src="/resources/img/upload/02.jpg">
				</div>
				<div class="info">
					<div class="eats-location">서울 영등포구</div>
					<div class="eats-name">스시 아루히7 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
					<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
					<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
				</div>
			</div>
			<div class="eats-list">
				<div class="thum" id="">
					<img src="/resources/img/upload/02.jpg">
				</div>
				<div class="info">
					<div class="eats-location">서울 영등포구</div>
					<div class="eats-name">스시 아루히8 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
					<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
					<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
				</div>
			</div>

	</section>

	<h2><i class="fas fa-utensils color-m"></i>   내 주변 <span class="color-m">잇친</span>이 추천한 맛집이에요!</h2>
	
	<section class="visual2">
		<div class="eats-list">
			<div class="thum1">
				<img src="/resources/img/upload/01.jpg">
			</div>
			<div class="info">
				<div class="eats-location">서울 영등포구</div>
				<div class="eats-name">스시 아루히1 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
				<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
				<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
			</div>
		</div>
		<div class="eats-list">
			<div class="thum1" id="">
				<img src="/resources/img/upload/02.jpg">
			</div>
			<div class="info">
				<div class="eats-location">서울 영등포구</div>
				<div class="eats-name">스시 아루히2 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
				<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
				<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
			</div>
		</div>
		<div class="eats-list">
			<div class="thum1" id="">
				<img src="/resources/img/upload/03.jpg">
			</div>
			<div class="info">
				<div class="eats-location">서울 영등포구</div>
				<div class="eats-name">스시 아루히3 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
				<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
				<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
			</div>
		</div>
		<div class="eats-list">
			<div class="thum1" id="">
				<img src="/resources/img/upload/04.jpg">
			</div>
			<div class="info">
				<div class="eats-location">서울 영등포구</div>
				<div class="eats-name">스시 아루히4 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
				<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
				<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
			</div>
		</div>
		<div class="eats-list">
			<div class="thum1" id="">
				<img src="/resources/img/upload/01.jpg">
			</div>
			<div class="info">
				<div class="eats-location">서울 영등포구</div>
				<div class="eats-name">스시 아루히5 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
				<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
				<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
			</div>
		</div>
		<div class="eats-list">
			<div class="thum1" id="">
				<img src="/resources/img/upload/02.jpg">
			</div>
			<div class="info">
				<div class="eats-location">서울 영등포구</div>
				<div class="eats-name">스시 아루히6 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
				<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
				<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
			</div>
		</div>
		<div class="eats-list">
			<div class="thum1" id="">
				<img src="/resources/img/upload/02.jpg">
			</div>
			<div class="info">
				<div class="eats-location">서울 영등포구</div>
				<div class="eats-name">스시 아루히7 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
				<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
				<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
			</div>
		</div>
		<div class="eats-list">
			<div class="thum1" id="">
				<img src="/resources/img/upload/02.jpg">
			</div>
			<div class="info">
				<div class="eats-location">서울 영등포구</div>
				<div class="eats-name">스시 아루히8 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
				<div class="eats-tag"><span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span></div>
				<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
			</div>
		</div>

	</section>
	
	


<%@ include file="/WEB-INF/views/include/footer.jsp"%>

<script type="text/javascript" src="/resources/js/member/main.js"></script>
<!-- 
<script type="module" src="/resources/firebase-messaging-sw.js"></script>
 -->
<script src="/resources/js/firebase/firebase-app.js"></script>
<script src="/resources/js/firebase/firebase-messaging.js"></script>
<script src="/resources/js/firebase/firebase-init.js"></script>

<script type="text/javascript">

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
	
	const tokenDivId = 'token_div';
	const permissionDivId = 'permission_div';
	
 	messaging.onMessage((payload) => {
	    console.log('Message received. ', payload);
	    // Update the UI to include the received message.
	    appendMessage(payload);
	  });
 	
 	
 	function resetUI() {
	    clearMessages();
	    showToken('loading...');
	    // Get registration token. Initially this makes a network call, once retrieved
	    // subsequent calls to getToken will return from cache.
	    messaging.getToken({vapidKey: 'BDaUhaUUutwgMI44dAQhkANJgRcgHHWWlEI05fvaQswJf5RmJrupDaTIiSGM1h9xxeaZcR13_lzGKZpTi07ahCs'})
	    .then((currentToken) => {
	      if (currentToken) {
	        sendTokenToServer(currentToken);
	        updateUIForPushEnabled(currentToken);
	      } else {
	        // Show permission request.
	        console.log('No registration token available. Request permission to generate one.');
	        // Show permission UI.
	        updateUIForPushPermissionRequired();
	        setTokenSentToServer(false);
	      }
	    }).catch((err) => {
	      console.log('An error occurred while retrieving token. ', err);
	      showToken('Error retrieving registration token. ', err);
	      setTokenSentToServer(false);
	    });
	  }
 	
 	
 	  function showToken(currentToken) {
 		    // Show token in console and UI.
 		    const tokenElement = document.querySelector('#token');
 		    tokenElement.textContent = currentToken;
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

		  function showHideDiv(divId, show) {
		    const div = document.querySelector('#' + divId);
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
	    const messagesElement = document.querySelector('#messages');
	    const dataHeaderElement = document.createElement('h5');
	    const dataElement = document.createElement('pre');
	    dataElement.style = 'overflow-x:hidden;';
	    dataHeaderElement.textContent = 'Received message:';
	    dataElement.textContent = JSON.stringify(payload, null, 2);
	    messagesElement.appendChild(dataHeaderElement);
	    messagesElement.appendChild(dataElement);
	  }

	  // Clear the messages element of all children.
	  function clearMessages() {
	    const messagesElement = document.querySelector('#messages');
	    while (messagesElement.hasChildNodes()) {
	      messagesElement.removeChild(messagesElement.lastChild);
	    }
	  }

	  function updateUIForPushEnabled(currentToken) {
	    showHideDiv(tokenDivId, true);
	    showHideDiv(permissionDivId, false);
	    showToken(currentToken);
	  }

	  function updateUIForPushPermissionRequired() {
	    showHideDiv(tokenDivId, false);
	    showHideDiv(permissionDivId, true);
	  }

	  resetUI();



</script>
</body>
</html>