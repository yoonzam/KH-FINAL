<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dimmed-wrap" id="pop-review-form" style="display:none;">
	<div class="dimmed"></div>
	<div class="layer-popup">
		<div class="content view">
			<div class="review-upload">
				<form id="frmUpload" action="/timeline/upload" method="post" enctype="multipart/form-data">
					<div class="popup-title">후기 등록하기</div>
					<ul class="upload-contents">
						<li class="location" data-upload-step="1" style="display:block;">
							<p><strong>#장소등록</strong><br>방문한 곳은 어디인가요?<span class="upload-flag place"></span></p>
							<div class="inp-place">
								<input type="text" name="uploadPlace" placeholder="장소를 입력해주세요." autocomplete="off" spellcheck="false">
								<ul class="location-list">
								</ul>
								<div id="uploadMap" class="upload-map"></div>
								<input class="hidden" type="text" name="resName">
								<input class="hidden" type="text" name="addr">
								<input class="hidden" type="text" name="latitude">
								<input class="hidden" type="text" name="longitude">
							</div>
						</li>
						<li class="hashtag" data-upload-step="2" style="display:none;">
							<p><strong>#카테고리</strong><br>어떤 음식을 드셨나요?</p>
							<div class="select">
								<label>#한식<input type="radio" name="category" value="cg01" /></label>
								<label>#중식<input type="radio" name="category" value="cg02" /></label>
								<label>#양식<input type="radio" name="category" value="cg03" /></label>
								<label>#일식<input type="radio" name="category" value="cg04" /></label>
								<label>#아시아<input type="radio" name="category" value="cg05" /></label>
								<label>#분식<input type="radio" name="category" value="cg06" /></label>
								<label>#카페,디저트<input type="radio" name="category" value="cg07" /></label>
								<label>#술집<input type="radio" name="category" value="cg08" /></label>
							</div>
							<p><strong>#해시태그</strong><br>음식점을 표현할 수 있는 태그를 모두 선택해주세요!</p>
							<div class="select">
								<label>#친근함<input type="checkbox" name="hashtag" value="md01" /></label>
								<label>#고급짐<input type="checkbox" name="hashtag" value="md02" /></label>
								<label>#가족<input type="checkbox" name="hashtag" value="md03" /></label>
								<label>#데이트<input type="checkbox" name="hashtag" value="md04" /></label>
								<label>#혼밥<input type="checkbox" name="hashtag" value="md05" /></label>
								<label>#혼밥<input type="checkbox" name="hashtag" value="md06" /></label>
								<br>
								<label>#가성비<input type="checkbox" name="hashtag" value="pr01" /></label>
								<label>#가심비<input type="checkbox" name="hashtag" value="pr02" /></label>
								<label>#1~2만원대<input type="checkbox" name="hashtag" value="pr03" /></label>
								<label>#2~3만원대<input type="checkbox" name="hashtag" value="pr04" /></label>
								<label>#3만원 이상<input type="checkbox" name="hashtag" value="pr05" /></label>
							</div>
						</li>
						<li class="review-content" data-upload-step="3" style="display:none;">
							<p><strong>#후기작성</strong><br>회원님의 후기를 들려주세요!</p>
							<div class="star-review">
								<div>
									<span>맛</span>
									<i class="far fa-star" data-score="1"></i><i class="far fa-star" data-score="2"></i><i class="far fa-star" data-score="3"></i><i class="far fa-star" data-score="4"></i><i class="far fa-star" data-score="5"></i>
									<input type="text" name="taste" value="" readonly>
								</div>
								<div>
									<span>청결</span>
									<i class="far fa-star" data-score="1"></i><i class="far fa-star" data-score="2"></i><i class="far fa-star" data-score="3"></i><i class="far fa-star" data-score="4"></i><i class="far fa-star" data-score="5"></i>
									<input type="text" name="clean" value="" readonly>
								</div>
								<div>
									<span>서비스</span>
									<i class="far fa-star" data-score="1"></i><i class="far fa-star" data-score="2"></i><i class="far fa-star" data-score="3"></i><i class="far fa-star" data-score="4"></i><i class="far fa-star" data-score="5"></i>
									<input type="text" name="service" value="" readonly>
								</div>
							</div>
							<textarea name="review" maxlength="200" placeholder="후기를 입력해주세요! (300자)" spellcheck="false"></textarea>
							<div class="textarea-count"><span>0</span>/최대 200자</div>
						</li>
						<li class="review-content" data-upload-step="4" style="display:none;">
							<p><strong>#이미지 등록</strong><br>이미지는 최대 3장까지 등록 가능합니다.</p>
							<input type="file" name="photos">
						</li>
					</ul>
					<div class="btn-wrap">
						<div><span id="uploadStep">1</span>/5단계</div>
						<button type="button" id="uploadPrevBtn" class="prev">이전</button>
						<button type="button" id="uploadNextBtn" class="next">다음</button>
						<button type="button" id="uploadBtn" class="next">전송</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<a class="close-btn" onclick="closePopup();"><i class="fas fa-times"></i></a>
</div>