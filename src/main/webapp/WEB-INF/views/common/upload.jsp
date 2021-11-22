<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dimmed-wrap" id="pop-review-form" style="display:none;">
	<div class="dimmed"></div>
	<div class="layer-popup">
		<div class="content view">
			<div class="review-upload">
				<div class="popup-title">후기 등록하기</div>
				<ul>
					<li class="location" data-upload-step="1" style="display:block;">
						<p><strong>#장소등록</strong><br>방문한 곳은 어디인가요?<span class="upload-flag place"></span></p>
						<div class="inp-place">
							<input type="text" name="uploadPlace" placeholder="장소를 입력해주세요." autocomplete="off" spellcheck="false">
							<ul class="location-list">
							</ul>
							<div id="uploadMap" class="upload-map"></div>
						</div>
					</li>
					<li class="hashtag" data-upload-step="2" style="display:none;">
						<p><strong>#카테고리</strong><br>어떤 음식을 드셨나요?</p>
						<div class="select">
							<label class="checked">#한식<input type="checkbox" value="" checked/></label>
							<label class="checked">#중식<input type="checkbox" value="" checked/></label>
							<label class="checked">#양식<input type="checkbox" value="" checked/></label>
							<label class="checked">#일식<input type="checkbox" value="" checked/></label>
							<label class="checked">#카페,디저트<input type="checkbox" value="" checked/></label>
							<label class="checked">#술집<input type="checkbox" value="" checked/></label>
						</div>
						<p><strong>#해시태그</strong><br>음식점을 표현할 수 있는 태그를 모두 선택해주세요!</p>
						<div class="select">
							<label class="checked">#친근함<input type="checkbox" value="" checked/></label>
							<label class="checked">#고급짐<input type="checkbox" value="" checked/></label>
							<label class="checked">#가족<input type="checkbox" value="" checked/></label>
							<label class="checked">#데이트<input type="checkbox" value="" checked/></label>
							<label class="checked">#혼밥<input type="checkbox" value="" checked/></label>
							<br>
							<label class="checked">#가성비<input type="checkbox" value="" checked/></label>
							<label class="checked">#가심비<input type="checkbox" value="" checked/></label>
							<label class="checked">#1~2만원대<input type="checkbox" value="" checked/></label>
							<label class="checked">#2~3만원대<input type="checkbox" value="" checked/></label>
							<label class="checked">#3만원 이상<input type="checkbox" value="" checked/></label>
						</div>
					</li>
				</ul>
				<div class="btn-wrap">
					<div><span id="uploadStep">1</span>/5단계</div>
					<button id="uploadPrevBtn" class="prev">이전</button>
					<button id="uploadNextBtn" class="next">다음</button>
					<button id="uploadBtn" class="next">전송</button>
				</div>
			</div>
		</div>
	</div>
	<a class="close-btn" onclick="closePopup();"><i class="fas fa-times"></i></a>
</div>