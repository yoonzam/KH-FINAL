<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dimmed-wrap" id="pop-schedule-form" style="display:none;">
	<div class="dimmed"></div>
	<div class="layer-popup review">
		<div class="content view">
			<form class="schedule" method="post" action="upload">
				<div class="popup-title">일정 만들기</div>
				<div class="schedule-list">
					<div class="list title"><span>제목</span><input type="text" name="title" id="title" placeholder="제목을 입력하세요"/></div>
					<div class="list date"><span>일자</span><input type="date" name="date" id="date"/></div>
					<div class="list time"><span>시간</span><input type="time" name="time" id="time"/></div>
					<div class="list place"><span>장소</span><input type="text" name="location" id="location" placeholder="장소를 검색하세요"/></div>
					<div class="list member"><span>참석자</span><input type="text" name="participant" id="participant" placeholder="함께할 잇친을 추가하세요"/></div>
					<div class="btn-wrap">
						<button class="submit" id="save-event">확인</button>
						<button class="cancel" onclick="closePopup();">취소</button>
					</div>
			</div>
			</form>
		</div>
	</div>
	
	<a class="close-btn" onclick="closePopup();"><i class="fas fa-times"></i></a>
</div>