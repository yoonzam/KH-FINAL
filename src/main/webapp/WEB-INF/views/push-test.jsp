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
<script defer src="https://www.gstatic.com/firebasejs/8.10.0/firebase-analytics.js"></script>
<script defer src="https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js"></script>

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


<script type="module">

  //Firebase 초기화

  import { initializeApp } from "https://www.gstatic.com/firebasejs/9.5.0/firebase-app.js";
  import { getAnalytics } from "https://www.gstatic.com/firebasejs/9.5.0/firebase-analytics.js";  
  import { } from "https://www.gstatic.com/firebasejs/9.5.0/firebase-messaging.js";

  // TODO: Add SDKs for Firebase products that you want to use
  // https://firebase.google.com/docs/web/setup#available-libraries

  // Your web app's Firebase configuration
  const firebaseConfig = {
    apiKey: "AIzaSyCNuQD68Jw_abElCM3B0XK-n4fNY-H7oJ0",
    authDomain: "final-332911.firebaseapp.com",
    projectId: "final-332911",
    storageBucket: "final-332911.appspot.com",
    messagingSenderId: "197983811902",
    appId: "1:197983811902:web:0bc80429719a225823d46f",
    measurementId: "G-MGDN4W8XFL"
  };

  // Initialize Firebase
  const app = initializeApp(firebaseConfig);
  const analytics = getAnalytics(app);
</script>
</body>
</html>