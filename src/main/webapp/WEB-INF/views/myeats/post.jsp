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
<link rel="stylesheet" type="text/css" href="/resources/css/member/following-list.css" />
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
						<h3 class="postCnt-txt" ><a onclick="viewFollowing('${memberId}')">내 잇친</a></h3>
						<span class="cnt">${followCnt}</span>
					</div>
					<div class="followingCnt">
						<h3 class="postCnt-txt" ><a onclick="viewFollower('${memberId}')">나를 추가한 잇친</a></h3>
						<span class="cnt">${followerCnt }</span>
					</div>
				</div><!-- wrap-profile-info -->
			</div>

            <div class="section">
				<div class="detail-wrap">
					<h2><i class="fas fa-utensils color-m"></i> 게시글 <span class="color-m">관리하기 </span> </h2>
					
						<c:if test="${empty reviews }">
							<h4 class="empty-review" style="padding: 40px; text-align:center; border: 1px solid #ddd; width: 928px; margin: auto;">게시물이 존재하지 않습니다.</h4>
						</c:if>
						<c:if test="${not empty reviews }">
						<ul class="detail-brd">
							<c:forEach items="${reviews}" var="review">
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
		<%-- <div class="page">
      		<ul class="pagination">
       		 <li><pageNav:pageNav listURI="post" pageObject="${pageObject}"></pageNav:pageNav></li>
      		</ul>
  		 </div> --%>
  		 
	</div><!-- container -->
</div><!-- container-wrap -->
</section>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<%@ include file="/WEB-INF/views/myeats/following-pop.jsp" %>
<%@ include file="/WEB-INF/views/myeats/follower-pop.jsp" %>
<script type="text/javascript">
	

let viewFollowing = (memberId) => {
	
	$('.wrap-list-following').empty();

	let data = {id : memberId};
	let header = new Headers();
	header.append('Content-Type', 'application/json;charset=UTF-8 ');

	fetch('/member/follow-pop',{
	 	method : 'POST',
	 	headers : header,
	 	body : JSON.stringify(data),
	 	
	}).then(response => {
		if(response.ok){
			return response.json();
	 	}else{
	  		throw new Error(response.status);
	 	}
	}).then(data => {
		let html = '';
		if(data.length == 0){
			html += '<h3 style="text-align:center; padding-top:15px;">잇친을 팔로우해 보세요!</h3>';
		}
		
		if(data.length > 0){
			for (var i = 0; i < data.length; i++) {
				html += '<li id="' + data[i].memberId + '">'	//팔로워
					  + '	<a href="/member/follow/' + data[i].member.nickname + '">'+ data[i].member.nickname +'</a>'
					  + '	<a onclick="popUnfollow(\''+data[i].memberId+'\')" class="btn-pop unfollow">잇친끊기</a>'
					  + '</li>'
			}			
		}

		document.querySelector('#following-pop').style.display = 'flex';
		$('.wrap-list-following').append(html);
		
	}).catch((error) => {
		  console.error('Error', error);
	});
	
	
};

let viewFollower = (memberId) => {
	
	$('.wrap-list-follower').empty();

	let data = {id : memberId};
	let header = new Headers();
	header.append('Content-Type', 'application/json;charset=UTF-8 ');

	fetch('/member/follower-pop',{
	 	method : 'POST',
	 	headers : header,
	 	body : JSON.stringify(data),
	 	
	}).then(response => {
		if(response.ok){
			return response.json();
	 	}else{
	  		throw new Error(response.status);
	 	}
	}).then(data => {
		let html = '';
		let followEachOther = data.followEachOther; //Follower 서로 친구인 멤버의 id를 담은 리스트
		let followerInfo = data.memberInfo;			//List<Map>
		
		if(data.length == 0){
			html += '<h3 style="text-align:center; padding-top:15px;">나를 팔로우한 잇친이 없습니다.</h3>';
			$('.wrap-list-follower').append(html);
		}
		
		if(followerInfo.length > 0){
			
			for (var i = 0; i < followerInfo.length; i++) {	//팔로워
				
				let list = document.createElement('li');
				let aNickname = document.createElement('a');
				
				list.id = followerInfo[i].memberId;
				$('.wrap-list-follower').append(list);
				
				aNickname.href='/member/follow/' + followerInfo[i].member.nickname;
				aNickname.innerHTML = followerInfo[i].member.nickname;
				
				$('#' + followerInfo[i].memberId).append(aNickname);				
				
				document.querySelector('#follower-pop').style.display = 'flex';
				
			}
			
			//forEach문 사용할것!
			for ( var j = 0; j < followEachOther.length; j++) {	//서로이웃인 팔로워 정보 만큼 돌면서 일치 아이디 조회
				for (var i = 0; i < followerInfo.length; i++) {
					let aBtn = '';
					if (followerInfo[i].memberId == followEachOther[j].memberId) {
						aBtn = document.createElement('a');				
						aBtn.className = 'btn-pop unfollow';
						aBtn.innerHTML = '잇친 끊기';
						$('#' + followerInfo[i].memberId ).append($(aBtn));
						$('#' + followerInfo[i].memberId + ' a:nth-child(2)').attr('onclick', 'popUnfollow('+ followerInfo[i].memberId +')');	//
						
						//$('#' + followerInfo[i].memberId + ' a:nth-child(2)').attr('onclick', 'popUnfollow('+ followerInfo[i].memberId +')');
					}else{
						aBtn = document.createElement('a');				
						aBtn.className = 'btn-pop follow';
						aBtn.innerHTML = '잇친 맺기';
						$('#' + followerInfo[i].memberId ).append($(aBtn));
						$('#' + followerInfo[i].memberId + ' a:nth-child(2)').attr('onclick', 'popfollow('+ followerInfo[i].memberId +')');
						
						//$('#' + followerInfo[i].memberId + ' a:nth-child(2)').attr('onclick', 'popfollow('+ followerInfo[i].memberId +')');
					}
						
				}
		
			}
			
		}
		
		console.dir(data);
		console.dir(followEachOther);
		console.dir(followerInfo);
	
	}).catch((error) => {
		  console.error('Error', error);
	});	
	
	
}


let popUnfollow = (followingId) => {
	$.ajax({
		type: 'POST',
		url: '/member/follow-cancel',
		data: JSON.stringify({ followingId : followingId }),
		contentType: 'application/json',
	 	cache:false,
		success: (memberId) => {
			$('#' + memberId).remove();
		},
		error: (e) => {
			alert("실패");
		}
	});
}

let popfollow = (followingId) => {	//팔로우할 아이디
	$.ajax({
		type: 'POST',
		url: '/member/follow',
		data: JSON.stringify({ followingId : followingId }),
		contentType: 'application/json',
		cache: false,
		success: (memberId) => {
			$('#' + memberId + ' a:nth-child(2)').attr('class',"btn-pop unfollow");
			$('#' + memberId + ' a:nth-child(2)').attr('onclick', 'popUnfollow('+ followerInfo[i].memberId +')');
		},
		error: (e) => {
			alert("실패");
		}
	});	
}

/* 						
html += '<li id="' + followerInfo[i].memberId + '">'
	  + '	<a href="/member/follow/' + followerInfo[i].member.nickname + '">'+ followerInfo[i].member.nickname +'</a>'
	  + '	<a onclick="popUnfollow(\''+ followerInfo[i].memberId+'\')" class="btn-pop unfollow">잇친끊기</a>'
	  + '</li>'						
}else{
html += '<li id="' + followerInfo[i].memberId + '">'
	  + '	<a href="/member/follow/' + followerInfo[i].member.nickname + '">'+ followerInfo[i].member.nickname +'</a>'
	  + '	<a onclick="popfollow(\''+ followerInfo[i].memberId+'\')" class="btn-pop follow">잇친맺기</a>'
	  + '</li>'						
}	 
*/	

</script>
</body>
</html>