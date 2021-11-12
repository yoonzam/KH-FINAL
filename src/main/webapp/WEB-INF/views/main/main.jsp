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



</head>
<body>
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<section>
		<div class="section">
			<!--div class="main-visual">
			</div-->
			<div class="main-visual">
				<img src="/resources/img/main/main.png">
			</div>
		

			<div class="search-wrap">
				<div class="search-area">
					<div class="search-form">
						<li>관심 음식<i class='xi-angle-down'></i>
							<div class='dropdown'>
								<a class='drop-link' href='#'>한식</a> 
								<a class='drop-link' href='#'>중식</a> 
								<a class='drop-link' href='#'>양식</a> 
								<a class='drop-link' href='#'>일식</a> 
								<a class='drop-link' href='#'>카페/디저트</a>
								<a class='drop-link' href='#'>술집</a>
							</div>
						</li> 
						<label>
							<input type="text" placeholder="찾으시는 맛집이나 유저 이름을 입력하세요."/>
						</label>
					
						<a class="search-btn" href="/main/search">검색하기<button type="submit"></button></a>
					</div>
				
				</div>
			</div>
			
			
		</div>
	</section>

			<div class="keyword-wrap">
				<h2><span class="color-m">#데이트</span>,  <span class="color-m">#술집</span> 연관 맛집이에요!</h2>
			</div>
				<section class="visual">
						<div class="eats-list">
							<div class="thum">
								<img src="/resources/img/upload/01.jpg">
							</div>
							<div class="info">
								<div class="eats-name">
									스시 아루히 1 
								</div>
							</div>
						</div>
						<div class="eats-list">
							<div class="thum" id="">
								<img src="/resources/img/upload/02.jpg">
							</div>
							<div class="info">
								<div class="eats-name">
									스시 아루히 2
								</div>
							</div>
						</div>
						<div class="eats-list">
							<div class="thum" id="">
								<img src="/resources/img/upload/03.jpg">
							</div>
							<div class="info">
								<div class="eats-name">
									스시 아루히 3
								</div>
							</div>
						</div>
						<div class="eats-list">
							<div class="thum" id="">
								<img src="/resources/img/upload/04.jpg">
							</div>
							<div class="info">
								<div class="eats-name">
									스시 아루히 4
								</div>
							</div>
						</div>
						<div class="eats-list">
							<div class="thum" id="">
								<img src="/resources/img/upload/01.jpg">
							</div>
							<div class="info">
								<div class="eats-name">
									스시 아루히 5
								</div>
							</div>
						</div>
						<div class="eats-list">
							<div class="thum" id="">
								<img src="/resources/img/upload/02.jpg">
							</div>
							<div class="info">
								<div class="eats-name">
									스시 아루히 6
								</div>
							</div>
						</div>
						<div class="eats-list">
							<div class="thum" id="">
								<img src="/resources/img/upload/02.jpg">
							</div>
							<div class="info">
								<div class="eats-name">
									스시 아루히 7
								</div>
							</div>
						</div>
						<div class="eats-list">
							<div class="thum" id="">
								<img src="/resources/img/upload/02.jpg">
							</div>
							<div class="info">
								<div class="eats-name">
									스시 아루히 8
								</div>
							</div>
						</div>

	</section>
	
	<div class="near-wrap">
				<h2>내 주변 <span class="color-m">잇친</span>이 추천한 맛집이에요!</h2>
	</div>
	
	<section class="visual2">
				<div class="eats-list">
					<div class="thum1">
						<img src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="eats-name">
							스시 아루히 1 
						</div>
					</div>
				</div>
				<div class="eats-list">
					<div class="thum1" id="">
						<img src="/resources/img/upload/02.jpg">
					</div>
					<div class="info">
						<div class="eats-name">
							스시 아루히 2
						</div>
					</div>
				</div>
				<div class="eats-list">
					<div class="thum1" id="">
						<img src="/resources/img/upload/03.jpg">
					</div>
					<div class="info">
						<div class="eats-name">
							스시 아루히 3
						</div>
					</div>
				</div>
				<div class="eats-list">
					<div class="thum1" id="">
						<img src="/resources/img/upload/04.jpg">
					</div>
					<div class="info">
						<div class="eats-name">
							스시 아루히 4
						</div>
					</div>
				</div>
	</section>
	
	


	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>