<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dimmed-wrap" style="display:none;">
	<div class="dimmed"></div>
	<div class="layer-popup review">
		<div class="content view">
			<div class="schedule-make">
				<div class="popup-title">일정 만들기</div>
				<div class="schedule-wrap">
					<div class="title" id="list"><span>제목</span><input class="input" type="text" placeholder="제목을 입력하세요"/></div>
					<div class="date" id="list"><span>일자</span><input class="input" type="date"/></div>
					<div class="time" id="list"><span>시간</span><input class="input" type="time"/></div>
					<div class="place" id="list"><span>장소</span><input class="input" type="text" placeholder="장소를 검색하세요"/></div>
					<div class="member" id="list"><span>참석자</span><input class="input" type="text" placeholder="함께할 잇친을 추가하세요"/></div>
				</div>
				<div class="btn-wrap">
					<button class="submit">확인</button>
					<button class="cancel">취소</button>
				</div>
			</div>
		</div>
	</div>
	
	<a class="close-btn" onclick="closePopup();"><i class="fas fa-times"></i></a>
</div>