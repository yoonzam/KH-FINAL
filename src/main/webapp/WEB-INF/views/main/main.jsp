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

<style type="text/css">

i.eats-like.fas.fa-heart {
   color: #f15050;
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<section>
		<div class="section">
			<div class="main-visual">
			</div>
			
			<div class="search-wrap">
				<div class="search-area">
					<form action="/main/search" method="get">
						<%@ include file="/WEB-INF/views/common/searchFilter.jsp" %>
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
	
			<div class="eats-list" onclick="viewTimeline('${reviews.id}')">
				<div class="thum">
					<img src="${!empty reviews.thumUrl ? reviews.thumUrl : '/resources/img/common/upload-logo.png'}">
				</div>
				<div class="info">
					<div class="eats-location">
						<c:set var="addr" value="${fn:split(reviews.addr,' ')}" />
						${addr[0]} ${addr[1]}&nbsp;&#62;&nbsp;${reviews.category}
					</div>
					<div class="eats-name">${reviews.resName} 
						<i data-like="${reviews.id}" class="eats-like ${reviews.like > 0 ? 'fas fa-heart' : 'far fa-heart'}"></i>
					</div>
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
	
<section class="visual2"></section>
<%@ include file="/WEB-INF/views/include/footer.jsp"%>
<script type="text/javascript">
resetUI();


</script>
</body>
</html>