<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dimmed-wrap" id="pop-schedule-form" style="display:none;">
	<div class="dimmed"></div>
	<div class="layer-popup review">
		<div class="content view">
			<form class="schedule" method="post" action="upload">
				<div class="popup-title">일정 만들기</div>
				<div class="schedule-list">
					<div class="list title"><span>제목</span><input type="text" name="title" id="title" placeholder="제목을 입력하세요" required/></div>
					<div class="list date"><span>일자</span><input type="date" name="date" id="date" required/></div>
					<div class="list time"><span>시간</span><input type="time" name="time" id="time" required/></div>
					<div class="list place"><span>장소</span><input type="text" name="resName" id="location" placeholder="장소를 입력하세요" required/><ul class="locationList"></ul></div>	
					<div class="list member"><span>참석자</span><input type="text" name="participant" id="participant" placeholder="함께할 잇친을 추가하세요" required/></div>
					<input class="hidden" type="text" name="latitude" style="display: none">
					<input class="hidden" type="text" name="longitude" style="display: none">
					<input class="hidden" type="text" name="scheduleId" id= "scheduleId" style="display: none">
					<div class="btn-wrap">
						<button class="submit" id="save-event">확인</button>
						<a href="/calendar/" class="cancel" id="cancel-btn">취소</a>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<a class="close-btn" onclick="closePopup();"><i class="fas fa-times"></i></a>
</div>