<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/myeats.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/post.css" />
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
				<li class="selected">작성글관리</li>
				<li><a href="detail">맛찜리스트</a></li>
				<li><a href="/member/modifyForm">회원정보 수정</a></li>
			</ul>
		    <div class="profile">
				<div class="wrap-profile-img">
					<div class="profile-img">
						<img src="/resources/img/upload/02.jpg">
					</div>
				</div><!-- wrap-profile-img -->
	            <div class="wrap-profile-info">
					<div class="postCnt">
						<h3 class="postCnt-txt">게시물</h3>
						<span class="cnt">11</span>
					</div>
					<div class="followCnt">
						<h3 class="postCnt-txt">내 잇친</h3>
						<span class="cnt">111</span>
					</div>
					<div class="followingCnt">
						<h3 class="postCnt-txt">나를 추가한 잇친</h3>
						<span class="cnt">101</span>
					</div>
				</div><!-- wrap-profile-info -->
			</div>
			<div class="btn-wrap">
				<a class="btn-edit-profile">잇친 맺기</a>
			</div> 
			
            <div class="section">
				<div class="detail-wrap">
					<h2><i class="fas fa-utensils color-m"></i> 게시글 <span class="color-m">관리하기 </span> </h2>
					
						<c:forEach items="${allReviews}" var="allReviews" varStatus="status"  begin="0"  >
						<c:if test="${status.first}"><ul class="detail-brd"></c:if>
						<c:choose>
						<c:when test="true">
						<li>
							<div class="eats-list">
								<div class="thum thum2">
									<img src="/resources/img/upload/01.jpg">
									<div class="info2">
										<div class="eats-location">${allReviews.resName}<i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
										<div class="eats-score"><i class="fas fa-star"></i>5.0</div>
									</div>
								</div>
							</div>
						</li>
						
						</c:when>
						</c:choose> 
						<c:if test="${status.last}"></ul></c:if>
						</c:forEach>
				
				</div><!-- detail-wrap -->
		</div><!-- section -->
		
		<!-- 페이징 -->
		<%-- <div class="page">
      		<ul class="pagination">
       		 <li><pageNav:pageNav listURI="post" pageObject="${pageObject}"></pageNav:pageNav></li>
      		</ul>
  		 </div> --%>
  		 
	</div><!-- container -->
</div><!-- container-wrap -->
</section>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>


</body>
</html>