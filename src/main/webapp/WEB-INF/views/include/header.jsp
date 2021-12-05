<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>잇츠맵 나만의 맛집 지도</title>
</head>
<body>
<div id="loadingImg" class="loading-img"><img src="/resources/img/common/loading.png"></div>
<div class="wrap">
	<header>
		<div class="header pc">
			<h1 onclick="location.href='/main/'"><img src="/resources/img/common/logo_ver1.png"></h1>
			<ul class="gnb">
				<li onclick="location.href='/map/'">잇츠맵</li>
				<li onclick="location.href='/calendar/'">잇츠캘린더</li>
				<li onclick="location.href='/timeline/'">타임라인</li>
				<li onclick="location.href='/myeats/group'">마이잇츠</li>
			</ul>
			<div class="util">
				<div onclick="location.href='/member/logout'" class="btn-logout">로그아웃</div>
				<c:if test="${noticeCnt == 0}">
					<div class="btn-bell"><i class="fas fa-bell"></i></div>
				</c:if>
				<c:if test="${noticeCnt != 0}">
					<div class="btn-bell new">
						<i class="fas fa-bell"> ${noticeCnt}</i>
					</div>
				</c:if>

				<c:if test="${noticeCnt != 0}">
					<div class="bell-list">
						<div class="bell-list-title">알림목록</div>
							<ul class="noticeDiv">
							<c:if test="${notice.calendarNotice == 1}">
								<li class="notice" id="dday">맛집 일정이 하루 남았어요!</li>
							</c:if>
							<c:if test="${notice.followNotice == 1 }">
								<li class="notice" id="follow">누군가 회원님과 잇친을 맺었어요!</li>
							</c:if>
							<c:if test="${notice.participantNotice == 1 }">	
								<li class="notice" id="calendar">누군가 회원님과 맛집에 가고 싶어해요!</li>
							</c:if>
							<c:if test="${notice.groupNotice == 1 }">
								<li class="notice" id="group">니캉내캉 그룹에 초대되었어요!</li>
							</c:if>
							</ul>							
					</div>					
				</c:if>
				<div onclick="uploadReview();" id="btnReview" class="btn-review">후기등록</div>
			</div>
		</div>
		<div class="header m">
			<div class="flex-wrap">
				<h1><img src="/resources/img/common/logo_ver1.png"></h1>
				<div class="util">
					<div class="btn-logout">로그아웃</div>
					<div class="btn-bell new"><i class="fas fa-bell"><c:if test="${noticeCnt != 0}"> ${noticeCnt}</c:if></i></div>
					<c:if test="${noticeCnt != 0}">
						<div class="bell-list" style="display: none;">
							<div class="bell-list-title">알림목록</div>
								<ul>
								<c:if test="${notice.calendarNotice == 1 }">
									<li class="notice" id="dday">맛집 일정이 하루 남았어요!</li>
								</c:if>
								<c:if test="${notice.followNotice == 1 }">
									<li class="notice" id="follow">누군가 회원님과 잇친을 맺었어요!</li>
								</c:if>
								<c:if test="${notice.participantNotice == 1 }">	
									<li class="notice" id="calendar">누군가 회원님과 맛집에 가고 싶어해요!</li>
								</c:if>
								<c:if test="${notice.groupNotice == 1 }">
									<li class="notice" id="group">니캉내캉 그룹에 초대되었어요!</li>
								</c:if>
								</ul>							
						</div>					
					</c:if>

					<div id="btnReview" class="btn-review">후기등록</div>
				</div>
			</div>
			<ul class="gnb">
				<li>잇츠맵</li>
				<li>잇츠캘린더</li>
				<li>타임라인</li>
				<li>마이잇츠</li>
			</ul>
		</div>
	</header>
	

	<script type="text/javascript">
	
	document.querySelector('.btn-bell.new').addEventListener('mouseover', () => {
		document.querySelector('.bell-list').style.display = 'block';
		setTimeout(() => document.querySelector('.bell-list').style.display = 'none', 3000);
	})
	
	document.querySelectorAll('.notice').forEach(e => {
		e.addEventListener('click', (li) => {
			console.dir(li.target.id);
			fetch('/member/removeNotice?id=' + li.target.id)
			.then(response => {
				  if(response.ok){	//통신 성공시
					switch (li.target.id) {
					case 'dday':location.href='/calendar/';	break;
					case 'calendar':location.href='/calendar/';	break;
					case 'follow':location.href='/myeats/post';	break;
					case 'group':location.href='/myeats/group'; break;}
				  }else{
					  throw new Error(response.status);
				  }
			})
		})	
	})
	
	
	</script>
