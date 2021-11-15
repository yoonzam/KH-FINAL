<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dimmed-wrap" style="display:none;">
	<div class="dimmed"></div>
	<div class="layer-popup review">
		<div class="content view">
			<div class="popup-title">일정 만들기</div>
			<ul class="schedule-wrap">
				<li class="title"><span>제목</span><input class="input" type="text" placeholder="제목을 입력하세요"/></li>
				<li class="date"><span>일자</span><input class="input" type="date"/></li>
				<li class="time"><span>시간</span><input class="input" type="time"/></li>
				<li class="place"><span>장소</span><input class="input" type="text" placeholder="장소를 검색하세요"/></li>
				<li class="member"><span>참석자</span><input class="input" type="text" placeholder="함께할 잇친을 추가하세요"/></li>
				<li class="btn-wrap">
					<a class="submit">확인</a>
					<a class="cancel">취소</a>
				</li>
			</ul>
		</div>
	</div>
	
	<a class="close-btn" onclick="closePopup();"><i class="fas fa-times"></i></a>
</div>