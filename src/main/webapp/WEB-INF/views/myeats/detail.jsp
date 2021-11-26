<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/myeats.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/detail.css" />
<script defer type="text/javascript" src="/resources/js/myeats/detail.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<section>
	<div class="container-wrap">
		<div class="container">
			<ul class="myeats-tab">
				<li><a href="group">그룹관리</a></li>
				<li><a href="post">작성글관리</a></li>
				<li class="selected">맛찜리스트</li>
				<li><a href="/member/modifyForm">회원정보 수정</a></li>
			</ul>
			<div class="section">
				<div class="detail-wrap">
					<h2><i class="fas fa-utensils color-m"></i> 내가 <span class="color-m">찜한 </span> 맛집!</h2>
					<ul class="detail-brd">
						<li>
							<div class="eats-list">
								<div class="thum thum2">
									<img src="/resources/img/upload/01.jpg">
									<div class="info2">
										<div class="eats-location">나의 스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
										<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="eats-list">
								<div class="thum thum2">
									<img src="/resources/img/upload/02.jpg">
									<div class="info2">
										<div class="eats-location">나의 스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
										<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="eats-list">
								<div class="thum thum2">
									<img src="/resources/img/upload/03.jpg">
									<div class="info2">
										<div class="eats-location">나의 스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
										<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="eats-list">
								<div class="thum thum2">
									<img src="/resources/img/upload/04.jpg">
									<div class="info2">
										<div class="eats-location">나의 스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
										<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="eats-list">
								<div class="thum thum2">
									<img src="/resources/img/upload/01.jpg">
									<div class="info2">
										<div class="eats-location">나의 스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
										<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="eats-list">
								<div class="thum thum2">
									<img src="/resources/img/upload/02.jpg">
									<div class="info2">
										<div class="eats-location">나의 스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
										<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="eats-list">
								<div class="thum thum2">
									<img src="/resources/img/upload/03.jpg">
									<div class="info2">
										<div class="eats-location">나의 스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
										<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="eats-list">
								<div class="thum thum2">
									<img src="/resources/img/upload/04.jpg">
									<div class="info2">
										<div class="eats-location">나의 스시 아루히 <i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
										<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div><!-- detail-wrap -->
			</div><!-- section -->
		</div><!-- container -->
	</div><!-- container-wrap -->
	</section>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>