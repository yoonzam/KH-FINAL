<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/main/search.css" />
<script defer type="text/javascript" src="/resources/js/main/search.js"></script>

<!-- paging -->

<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<section>
		<div class="section">
			<!--div class="main-visual">
			</div-->
			<div class="search-wrap">
				<div class="search-area">
					<form action="/main/search" method="get">
						<%@ include file="/WEB-INF/views/common/searchFilter.jsp" %>
					</form>
				</div>
			</div>
			<div class="timeline-wrap">
				<h2><i class="fas fa-search color-m"></i> <span class="color-m">${keyword}</span> 검색결과"</h2>
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
								<div class="eats-name">${reviews.resName} 
									<i data-like="${reviews.id}" class="eats-like ${reviews.like > 0 ? 'fas fa-heart' : 'far fa-heart'}"></i>
								</div>
								<div class="eats-tag">
									<c:forEach items="${reviews.hashtag}" var="hashtag">
										<span>&#35;${hashtag}</span>
									</c:forEach>
								</div>
								<div class="eats-score"><i class="fas fa-star"></i>
									<fmt:formatNumber value="${(reviews.taste+reviews.clean+reviews.service)/3}" pattern=".0"/>
								</div>
							</div>
						</div>
					</li>
					
					</c:forEach>
					
				</ul>
			</div>
			
			
			<!-- paging -->
			<div class="container">
				<ul class="pagination">
					<%-- [이전] --%>
					<c:if test="${1 == pageObject.page}">
			            <li class="disabled"><a href="#" >«</a></li>
					</c:if>
					<c:if test="${1 != pageObject.page}">
			            <li><a href="/main/search?page=${pageObject.page-1}" >«</a></li>
					</c:if>
					<c:forEach var="i" begin="${pageObject.startPage}" end="${pageObject.endPage}">
						<c:if test="${i == pageObject.page}">
			             <li class="active"><a href="/main/search?page=${i}"><b><c:out value="${i}"/></b></a></li>
						</c:if>
						<c:if test="${i != pageObject.page}">
					 	<li><a href="/main/search?page=${i}"><c:out value="${i}"/></a></li>
						</c:if>
					</c:forEach>
					<%-- [다음] --%>
					<c:if test="${pageObject.totalPage == pageObject.page}">
			            <li class="disabled"><a href="#" >»</a></li>
					</c:if>
					<c:if test="${pageObject.totalPage != pageObject.page}">
			            <li><a href="/main/search?page=${pageObject.page+1}" >»</a></li>
					</c:if>
	            </ul>
			</div>
		</div>
	</section>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>