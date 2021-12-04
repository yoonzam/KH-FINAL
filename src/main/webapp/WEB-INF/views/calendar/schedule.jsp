<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dimmed-wrap" id="pop-schedule-detail" style="display:none;">
	<div class="dimmed"></div>
	<div class="layer-popup schedule">
		<div class="schedule">
				<div class="popup-title" id="detail-date"></div>
				<div class="schedule-spec">
					<div class="list"><span>제목 : </span><span id="detail-title"></span></div>
					<div class="list"><span>시간 : </span><span id="detail-time" ></span></div>
					<div class="list"><span>장소 : </span><span id="detail-place"></span></div>
					<div class="list" id="participant-tit"><span>참석자 : </span><span id="detail-participant"></span></div>
				</div>
				<div class="btn-wrap">
						<button class="change" id="sch-change-btn">수정</button>
						<button class="delete" id="sch-delete-btn" onclick="deleteSchedule();">삭제</button>
				</div>
		</div>
	</div>
	
	<a class="close-btn" onclick="closePopup();"><i class="fas fa-times"></i></a>
</div>