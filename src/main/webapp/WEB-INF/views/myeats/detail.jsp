<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/myeats.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/detail.css" />
<script defer type="text/javascript" src="/resources/js/myeats/detail.js"></script>
<style type="text/css">


/* 페이징 가운데 정렬 */	
.page{
  text-align: center;  
  width: 100%;
  }

.pagination {
  list-style: none;
  display: inline-block;
  padding: 0;
  margin-top: 20px;
  }

.pagination li {
  display: inline;
  text-align: center;
  }

/* 페이징 */
.pagination {
  display: inline-block;
}

.pagination a {
  color: black;
  padding: 8px 16px;
  margin-right: 3px;
  width: 15px;
  font: bold 12px tahoma;
  cursor: pointer;
}

.pagination a:active,.pagination a:hover,.pagination a:focus{
	background-color: #ccc;
	color:#fff;
	border:1px solid #ccc;
	border-radius: 5px;
}

</style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<section>
	<div class="container-wrap">
		<div class="container">
			<ul class="myeats-tab">
				<li><a href="/myeats/group">그룹관리</a></li>
				<li><a href="/myeats/post">작성글관리</a></li>
				<li class="selected">맛찜리스트</li>
				<li><a href="/member/edit-profile">회원정보 수정</a></li>
			</ul>
			<div class="section">
				<div class="detail-wrap">
					<h2><i class="fas fa-utensils color-m"></i> 내가 <span class="color-m">찜한 </span> 맛집!</h2>
						<c:if test="${empty reviews }">
							<h4 class="empty-review" style="padding: 40px; text-align:center; border: 1px solid #ddd; width: 928px; margin: auto;">찜한 게시물이 존재하지 않습니다.</h4>
						</c:if>
						<c:if test="${not empty reviews }">
							<ul class="detail-brd">
								<c:forEach items="${reviews}" var="review" varStatus="status"  begin="0"  >
									<li onclick="viewTimeline('${review.id}')">
										<div class="eats-list">
											<div class="thum thum2">
												<img src="${ not empty review.thumUrl ? review.thumUrl : '/resources/img/common/upload-logo.png'}">
												<div class="info2">
													<div class="eats-location">${review.resName}<i onclick="clickLike();" class="eats-like far fa-heart"></i></div>
													<div class="eats-score">
														<i class="fas fa-star"></i>
														<fmt:formatNumber value="${(review.taste+review.clean+review.service)/3}" pattern=".0"/>
													</div>
												</div>
											</div>
										</div>
									</li>
								</c:forEach>
							</ul>						
						</c:if>
				</div><!-- detail-wrap -->
			</div><!-- section -->
			
			<!-- 페이징 -->
			<div class="page">
	       		 <pageNav:pageNav listURI="detail" pageObject="${pageObject}"></pageNav:pageNav>
	  		 </div>
	  		 
		</div><!-- container -->
	</div><!-- container-wrap -->
	</section>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>