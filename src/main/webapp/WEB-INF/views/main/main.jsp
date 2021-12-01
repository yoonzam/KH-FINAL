<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<div class="main-visual">
				<img src="/resources/img/main/main.png">
			</div>
			<div class="search-wrap">
				<div class="search-area">
				<form action="/main/search" method="post">
					<div class="search-form">
						<label>
							<input type="text" name="keyword_" placeholder="찾으시는 맛집이나 유저 이름을 입력하세요."/>
						</label>
						<a class="filter-btn"><i class="fas fa-filter">검색필터</i></a>
						<button type="submit"><i class="fas fa-search"></i></button>
					</div>
					<div class="filter-menu">
						<ul>
							<li>
								<span>선택</span>
								<label id="allCheck" class="checked">전체선택</label>
							</li>
							<li>
								<span>종류</span>
								<label class="checked">#한식<input type="checkbox" name="category_" value="cg01" checked/></label>
								<label class="checked">#중식<input type="checkbox" name="category_" value="cg02" checked/></label>
								<label class="checked">#양식<input type="checkbox" name="category_" value="cg03" checked/></label>
								<label class="checked">#일식<input type="checkbox" name="category_" value="cg04" checked/></label>
								<label class="checked">#아시아<input type="checkbox" name="category_" value="cg05" checked/></label>
								<label class="checked">#분식<input type="checkbox" name="category_" value="cg06" checked/></label>
								<label class="checked">#카페/디저트<input type="checkbox" name="category_" value="cg07" checked/></label>
								<label class="checked">#술집<input type="checkbox" name="category_" value="cg08" checked/></label>
							</li>
							<li>
								<span>분위기</span>
								<label class="checked">#친근함<input type="checkbox" name="hashtag_" value="md01" checked/></label>
								<label class="checked">#고급짐<input type="checkbox" name="hashtag_" value="md02" checked/></label>
								<label class="checked">#가족<input type="checkbox" name="hashtag_" value="md03" checked/></label>
								<label class="checked">#데이트<input type="checkbox" name="hashtag_" value="md04" checked/></label>
								<label class="checked">#혼밥<input type="checkbox" name="hashtag_" value="md05" checked/></label>
								<label class="checked">#회식<input type="checkbox" name="hashtag_" value="md06" checked/></label>
							</li>
							<li>
								<span>가격대</span>
								<label class="checked">#가성비<input type="checkbox" name="hashtag_" value="pr01" checked/></label>
								<label class="checked">#가심비<input type="checkbox" name="hashtag_" value="pr02" checked/></label>
								<label class="checked">#1~2만원대<input type="checkbox" name="hashtag_" value="pr03" checked/></label>
								<label class="checked">#2~3만원대<input type="checkbox" name="hashtag_" value="pr04" checked/></label>
								<label class="checked">#3만원 이상<input type="checkbox" name="hashtag_" value="pr05" checked/></label>
							</li>
						</ul>
					</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<h2><i class="fas fa-utensils color-m"></i> 여기는 어떠세요? 
	
	<span class="color-m">#<c:out value="${reviewsAndHashtag.maxHash1}" /></span> <span class="color-m">
	<c:if test="${reviewsAndHashtag.maxHash1 != reviewsAndHashtag.maxHash2 }">#<c:out value="${reviewsAndHashtag.maxHash2}" /></c:if></span></h2>
	<section class="visual"> 
		<c:forEach items="${reviewsAndHashtag.reviews}" var="reviews">
	
			<div class="eats-list">
				<div class="thum">
					<img src="${!empty reviews.thumUrl ? reviews.thumUrl : '/resources/img/common/upload-logo.png'}">
				</div>
				<div class="info">
					<div class="eats-location">
						<c:set var="addr" value="${fn:split(reviews.addr,' ')}" />
						${addr[0]} ${addr[1]}&nbsp;&#62;&nbsp;${reviews.category}
					</div>
					<div class="eats-name">
						${reviews.resName}
					<i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
					<div class="eats-tag">
						<c:forEach items="${reviews.hashtag}" var="hashtag">
							<span>&#35;${hashtag}</span>
						</c:forEach>
					<div class="eats-score"><i class="fas fa-star"></i>
						<fmt:formatNumber value="${(reviews.taste+reviews.clean+reviews.service)/3}" pattern=".0"/>
					</div>
				</div>
			</div>
		</div>
		
		</c:forEach>
	</section>

	<h2><i class="fas fa-utensils color-m"></i>   내 주변 <span class="color-m">잇친</span>이 추천한 맛집이에요!</h2>
	
	<section class="visual2">
		<c:forEach items="${locationReviewList}" var="reviews">
	
			<div class="eats-list">
			<div class="thum1">
					<img src="${!empty reviews.thumUrl ? reviews.thumUrl : '/resources/img/common/upload-logo.png'}">
				</div>
				<div class="info">
					<div class="eats-location">
						<c:set var="addr" value="${fn:split(reviews.addr,' ')}" />
						${addr[0]} ${addr[1]}&nbsp;&#62;&nbsp;${reviews.category}
					</div>
					<div class="eats-name">
						${reviews.resName}
					<i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
					<div class="eats-tag">
						<c:forEach items="${reviews.hashtag}" var="hashtag">
							<span>&#35;${hashtag}</span>
						</c:forEach>
					<div class="eats-score"><i class="fas fa-star"></i>
						<fmt:formatNumber value="${(reviews.taste+reviews.clean+reviews.service)/3}" pattern=".0"/>
					</div>
				</div>
			</div>
		</div>
		
		</c:forEach>
	
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
		


	</section>
	
	


	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
</body>
</html>