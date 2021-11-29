<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dimmed-wrap" id="pop-schedule-detail" style="display:none;">
	<div class="dimmed"></div>
	<div class="layer-popup review">
		<div class="schedule">
				<div class="popup-title">2021년</div>
				<div class="schedule-spec">
					<div class="title" id="list"><span>제목 : </span><span></span></div>
					<div class="time" id="list"><span>시간 : </span><span></span></div>
					<div class="place" id="list"><span>장소 : </span><span></span></div>
					<div class="member" id="list"><span>참석자 : </span><span></span></div>
				</div>
				<div class="btn-wrap">
						<button class="change">수정</button>
						<button class="delete">삭제</button>
				</div>
		</div>
	</div>
	
	<a class="close-btn" onclick="closePopup();"><i class="fas fa-times"></i></a>
</div>