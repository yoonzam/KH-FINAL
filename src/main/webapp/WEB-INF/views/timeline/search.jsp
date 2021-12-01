<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/timeline/timeline.css" />
<script defer type="text/javascript" src="/resources/js/timeline/search.js"></script>

<!-- paging -->

<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<section>
		<div class="section">
			<div class="search-wrap">
				<div class="search-area">
					<form action="/timeline/search" method="get">
						<%@ include file="/WEB-INF/views/common/searchFilter.jsp" %>
					</form>
				</div>
			</div>
			<div class="timeline-wrap">
				<h2><i class="fas fa-search color-m"></i> <span class="color-m">${keyword}</span> 검색결과</h2>
				<ul class="timeline-brd">
					<c:forEach items="${reviews}" var="reviews">
						<li onclick="viewTimeline('${reviews.id}')">
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
										${reviews.resName} <i data-like="${reviews.id}" class="eats-like ${reviews.like > 0 ? 'fas fa-heart' : 'far fa-heart'}"></i>
									</div>
									<div class="eats-tag">
										<c:forEach items="${reviews.hashtag}" var="hashtag">
											<span>&#35;${hashtag}</span>
										</c:forEach>
									</div>
									<div class="eats-score">
										<i class="fas fa-star"></i>
										<fmt:formatNumber value="${(reviews.taste+reviews.clean+reviews.service)/3}" pattern=".0"/>
									</div>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</section>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>