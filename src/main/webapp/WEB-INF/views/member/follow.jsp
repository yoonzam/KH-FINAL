<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
				<li><a href="/myeats/group">그룹관리</a></li>
				<li class="selected">작성글관리</li>
				<li><a href="/myeats/detail">맛찜리스트</a></li>
				<li><a href="/member/edit-profile">회원정보 수정</a></li>
			</ul>
		    <div class="profile">
				<div class="wrap-profile-img">
					<div class="profile-img">
						<c:if test="${empty member.profile }">
							<img src="/resources/img/member/user.png">
						</c:if>
						<c:if test="${not empty member.profile }">
							<img src="http://localhost:9090/file/${member.profile}">
						</c:if>
					</div>
				</div><!-- wrap-profile-img -->
	            <div class="wrap-profile-info">
					<div class="postCnt">
						<h3 class="postCnt-txt">게시물</h3>
						<span class="cnt">${fn:length(reviews)}</span>
					</div>
					<div class="followCnt">
						<h3 class="postCnt-txt">내 잇친</h3>
						<span class="cnt">${followCnt}</span>
					</div>
					<div class="followingCnt">
						<h3 class="postCnt-txt">나를 추가한 잇친</h3>
						<span class="cnt">${followerCnt }</span>
					</div>
				</div><!-- wrap-profile-info -->
			</div>
			<c:if test="${empty follow.id }">
				<div class="btn-wrap">
					<div class="btn-follow">잇친 맺기</div>
					<a class="btn-follow-cancel" style="display: none">잇친 취소</a>
				</div> 			
			</c:if>
			<c:if test="${not empty follow.id }">
				<div class="btn-wrap">
					<div class="btn-follow" style="display: none">잇친 맺기</div>
					<a class="btn-follow-cancel">잇친 취소</a>
				</div> 			
			</c:if>			
            <div class="section">
				<div class="detail-wrap">
					<h2><i class="fas fa-utensils color-m"></i> ${member.nickname } 님<span class="color-m"> 게시물 </span> </h2>
					
						<c:if test="${empty reviews }">
							<h4 class="empty-review" style="padding: 40px; text-align:center; border: 1px solid #ddd; width: 928px; margin: auto;">게시물이 존재하지 않습니다.</h4>
						</c:if>
						<c:if test="${not empty reviews }">
						<ul class="detail-brd">
							<c:forEach items="${reviews}" var="review" varStatus="status"  begin="0"  >
							<li>
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
		<%-- <div class="page">
      		<ul class="pagination">
       		 <li><pageNav:pageNav listURI="post" pageObject="${pageObject}"></pageNav:pageNav></li>
      		</ul>
  		 </div> --%>
  		 
	</div><!-- container -->
</div><!-- container-wrap -->
</section>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script type="text/javascript">

document.querySelector('.btn-follow').addEventListener('click', e => {
	
	let data = {followingId : `${member.id}`};
	let header = new Headers();
	header.append('Content-Type', 'application/json;charset=UTF-8 ')
	
	fetch('/member/follow',{
	 	method : 'POST',
	 	headers : header,
	 	body : JSON.stringify(data),
	 
	}).then(response => {
		if(response.ok){
			document.querySelector('.btn-follow').style.display = 'none';
			document.querySelector('.btn-follow-cancel').style.display = 'block';
	 	}else{
	  		throw new Error(response.status);
	 	}
	}).catch((error) => {
		  console.error('Error', error);
	  })
})

document.querySelector('.btn-follow-cancel').addEventListener('click', e => {
	
	let data = {followingId : `${member.id}`};
	let header = new Headers();
	header.append('Content-Type', 'application/json;charset=UTF-8 ')
	
	fetch('/member/follow-cancel',{
	 	method : 'POST',
	 	headers : header,
	 	body : JSON.stringify(data),
	 
	}).then(response => {
		if(response.ok){
			document.querySelector('.btn-follow').style.display = 'block';
			document.querySelector('.btn-follow-cancel').style.display = 'none';
	 	}else{
	  		throw new Error(response.status);
	 	}
	}).catch((error) => {
		  console.error('Error', error);
	  })
})


</script>

</body>
</html>