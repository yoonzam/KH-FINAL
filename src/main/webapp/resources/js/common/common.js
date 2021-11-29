/* 공통 JS */
let getSectionMargin = () =>  $('section').css('padding-top',$('header').height());
let resizeSlideImgHeight = () => {
    let slideImgWidth = $('.layer-popup .slide-img-wrap').width();
    $('.layer-popup .slide-img').height(slideImgWidth);
    $('.layer-popup .slide-img li').width(slideImgWidth);
    $('.layer-popup .slide-img li').height(slideImgWidth);
    $('.layer-popup .info-wrap').height(slideImgWidth);
    $('.layer-popup .slide-btn').css('margin-top',(slideImgWidth/2-18)+'px');
}
$(document).ready(()=>{
    getSectionMargin();
    resizeSlideImgHeight();
});
$(window).resize(() => {
    getSectionMargin();
    resizeSlideImgHeight();
});

/** 후기 **/
let uploadStep;
let placeFlag;
let searchPlaces;
let photoCount;
let reviewBtn = false;	//맵Flag
let editFlag = false;	//리뷰수정Flag

/* 팝업 제어 */
let closePopup = () => $('.dimmed-wrap').fadeOut(200);
$('.dimmed').click(()=>{
    closePopup();
    reviewBtn = false;	//맵Flag
    editFlag = false;	//리뷰수정Flag
});

/* 후기 디테일 버튼 이름 표시 */
$('.layer-popup .view-controller a').hover((e) => {
    $('.view-controller span').html('');
    let className = e.currentTarget.getAttribute('class');
    let btnName = '';
    if(className === 'pop-btn-my-list'){
        btnName = '맛찜하기';
    } else if(className === 'pop-btn-calendar'){
        btnName = '캘린더 추가';
    } else if(className === 'pop-btn-edit'){
        btnName = '수정하기';
    } else{
        btnName = '삭제하기';
    }
    $('.view-controller span').html(btnName);
  }, (e)=>{
    $('.view-controller span').html('');
});

/* 지도 그리기 */
let drawMap = (latitude, longitude, resName) => {
	let options = { center: new kakao.maps.LatLng(latitude, longitude), level: 5 };
	let map = new kakao.maps.Map(document.getElementById('uploadMap'), options);
	let content = '<div class="marker-wrap"><p><i class="fas fa-utensils color-m"></i> '+resName+'</p><div></div></div>';
	let customOverlay = new kakao.maps.CustomOverlay({
	    position: new kakao.maps.LatLng(latitude, longitude),
	    content: content
	});
	customOverlay.setMap(map);
	placeFlag = true;
}

/* 맵 페이지에서 후기등록 버튼 클릭 이벤트 */
$("#map_reviewBtn").click(function(){
	reviewBtn = true;
	uploadReview();
});

let uploadReview = (reviewId) => {
//	초기화
	uploadStep = 1;
	photoCount = 0;
	placeFlag = false;
	searchPlaces = [];
	uploadStepControl();
	


	
	$('.upload-flag').html('');
	$('#uploadPrevBtn').hide();
	$('#uploadNextBtn').show();
	$('#uploadBtn').hide();
	$('#uploadBtn').attr('onclick','uploadConfirm();');
	$('#pop-review-form input:text').val('');
	$('#pop-review-form input:file').val('');
	$('#pop-review-form input:radio').prop('checked', false);
	$('#pop-review-form input:checkbox').prop('checked', false);
	$('.hashtag label').removeClass('checked');
	$('.star-review i').attr('class','far fa-star');
	$('#pop-review-form textarea').val('');
	$('.preview-photo').css('display','none');
	$('#pop-review-form select[name="group"]').val('').prop("selected", true);
	$('#pop-review-form select[name="privacy"]').val('0').prop("selected", true);
	$('#pop-review-form select[name="privacy"]').attr('disabled',false);
	$('#pop-review-form').fadeIn(200);
	
	let options = { center: new kakao.maps.LatLng(37.55317, 126.97279), level: 8 };
	let map = new kakao.maps.Map(document.getElementById('uploadMap'), options);
	
//	맵에서 등록시 기존 데이터 입력
	if(reviewBtn){
		let placeInfo = markerInfo;
		$('input[name="uploadPlace"]').val(markerInfo.place_name);
		$('input[name="resName"]').val(markerInfo.place_name);
		$('input[name="addr"]').val(markerInfo.road_address_name);
		$('input[name="latitude"]').val(markerInfo.y);
		$('input[name="longitude"]').val(markerInfo.x);
		drawMap(placeInfo.y, placeInfo.x, placeInfo.place_name); //맵 그리기
	}
	
//	수정시 기존 데이터 입력
	if(editFlag){
		$.ajax({
			type: 'GET',
			url: '/timeline/edit',
			data:{ id:reviewId },
			dataType: 'json',
			success: (data) => {
				$('#uploadBtn').attr('onclick','uploadConfirm("'+reviewId+'")');
				
				//장소
				$('input[name="uploadPlace"]').val(data.review.resName);
				$('input[name="resName"]').val(data.review.resName);
				$('input[name="addr"]').val(data.review.addr);
				$('input[name="latitude"]').val(data.review.location.y);
				$('input[name="longitude"]').val(data.review.location.x);
				drawMap(data.review.location.y, data.review.location.x, data.review.resName); //맵 그리기
				
				//카테고리
				let inpRadio = $('.review-upload input:radio');
				for ( var i=0; i<inpRadio.length; i++ ) {
					let label = inpRadio[i].parentNode;
					if(label.innerText == '#'+data.review.category) {
						label.className += 'checked';
						inpRadio[i].checked = true;
					}
				}
				
				//해시태그
				let inpCheckbox = $('.review-upload input:checkbox');
				for ( var i=0; i<inpCheckbox.length; i++ ) {
					let label = inpCheckbox[i].parentNode;
					for ( var j=0; j<data.review.hashtag.length; j++ ) {
						if(label.innerText == '#'+data.review.hashtag[j]) {
							label.className += 'checked';
							inpCheckbox[i].checked = true;
						}
					}
				}
				
				//리뷰
				let starTaste = $('.star-review div')[0].children;
				let inpTaste = starTaste[starTaste.length-1];
				inpTaste.value = data.review.taste;
				for(var i = 1; i < starTaste.length-1; i ++) if(i <= data.review.taste) starTaste[i].className = 'fas fa-star';
				let starClean = $('.star-review div')[1].children;
				let inpClean = starClean[starClean.length-1];
				inpClean.value = data.review.clean;
				for(var i = 1; i < starClean.length-1; i ++) if(i <= data.review.clean) starClean[i].className = 'fas fa-star';
				let starService = $('.star-review div')[2].children;
				let inpService = starService[starService.length-1];
				inpService.value = data.review.service;
				for(var i = 1; i < starService.length-1; i ++) if(i <= data.review.service) starService[i].className = 'fas fa-star';
				$('textarea[name="review"]').val(data.review.review);
				
				//사진
				let prevBox = $('#pop-review-form .upload-box > div');
				for(i = 0; i < data.files.length; i++) {
					let img = prevBox[i].children[2].children[0];					
					img.setAttribute("src", data.files[i]);
					$("#hdPhotos"+(i+1)).val(data.files[i]);
					prevBox[i].children[2].style.display = 'block';
					photoCount++;
				}
				
				//그룹&공개범위
				if(data.review.group == null) {
					//$('#pop-review-form select[name="privacy"]').val("'+data.review.privacy+'").prop("selected", true);
				} else {
					//$('#pop-review-form select[name="group"]').val("'+data.review.group+'").prop("selected", true);
					//$('#pop-review-form select[name="privacy"]').prop('disabled',true);
				}
			},
			error: function (e) {
				alert('에러발생');
			}
		});
	}
	
//	등록 시작
	$('input[name="uploadPlace"]').keyup(function(){
		let keyword = $(this).val();
		if(!keyword) {
			$('.location-list').html('');
			return;
		}
		//장소검색 객체 생성
		let ps = new kakao.maps.services.Places();
		//키워드로 장소 검색
		ps.keywordSearch(keyword, (data, status) => {
			if (status === kakao.maps.services.Status.OK) {
				searchPlaces = data;
				let html = '';
				for ( var i=0; i<data.length; i++ ) {
					if(data[i].category_group_code == 'FD6' || data[i].category_group_code == 'CE7' )
						html += '<li data-place-idx="'+i+'"><span class="place-name">'+data[i].place_name+'</span> <span class="road-address-name">'+data[i].road_address_name+'</span></li>';
				}
				$('.location-list').html(html);
				$('.location-list').show();
			}
		}); 
	});
	
	//검색리스트 클릭시 지도에 표시
	$('.location-list').click(function(e){
		let placeIdx = e.target.dataset.placeIdx;
		let place = searchPlaces[placeIdx];
		$('input[name="uploadPlace"]').val(place.place_name);
		$('input[name="resName"]').val(place.place_name);
		$('input[name="addr"]').val(place.road_address_name);
		$('input[name="latitude"]').val(place.y);
		$('input[name="longitude"]').val(place.x);
		
		drawMap(place.y, place.x, place.place_name); //맵 그리기
		$('.location-list').hide();
	})
	
	//카테고리&해시태그
	$('.review-upload input:radio').click((e)=>{
	    let label = e.target.parentNode;
	    $('.hashtag .radio label').removeClass('checked');
	    label.classList.add('checked');
	});
	$('.review-upload input:checkbox').click((e)=>{
	    let label = e.target.parentNode;
	    if(label.className == 'checked') {
			label.classList.remove('checked');
		} else{
			label.classList.add('checked');
		}
	});
	
	//별점&리뷰
	$('.star-review i').click(function(e){
		let score = e.target.dataset.score;
		let category = e.target.parentNode;
		let inp = category.children[category.children.length-1];
		inp.value = score;
		for(i=1; i < category.children.length; i++){
			if(i <= score) category.children[i].className = 'fas fa-star';
			else category.children[i].className = 'far fa-star';
		}
	});
	$('textarea[name="review"]').keyup(function(e){
		let review = $(this).val();
		if(review.length < 200) $('.textarea-count span').html(review.length);
		else $('.textarea-count span').html(200);
	});
	
	//사진등록
	$('#pop-review-form input:file').on('change',function(e){
		let file = e.target.files[0];
		let fileArr = file.name.split('.');
		let fileFormat = fileArr[fileArr.length-1];
		if(fileFormat != 'gif' && fileFormat != 'png' && fileFormat != 'jpg' && fileFormat != 'jpeg' && fileFormat != 'png'){
			alert('사진은 jpg, jpeg, png, gif 형식만 지원합니다.');
			e.target.value='';
			return;
		} else {
			let reader = new FileReader();
			reader.onload = function(rslt) {
				let div = e.target.parentNode.children[2];
				let img = div.children[0];
				img.setAttribute("src", rslt.target.result);
				div.style.display = 'block';
			};
			reader.readAsDataURL(file);
			photoCount++;
		}
	});
	
	//사진삭제
	$('.fa-times-circle').click((e)=>{
		let div = e.target.parentNode;
		div.style.display = 'none';
		div.parentNode.children[1].value='';
		div.children[2].value += '_d';
		photoCount--;
	});
	
	//그룹&공개범위
	$('#pop-review-form select[name="group"]').on('change',function(e){
		if(e.target.value != ""){
			$('#pop-review-form select[name="privacy"]').prop('disabled',true);
			$('#pop-review-form select[name="privacy"]').val('0').prop("selected", true);
		} else{
			$('#pop-review-form select[name="privacy"]').prop('disabled',false);
		}
	});
};

/* 리뷰 버튼 제어 */
$('#uploadNextBtn').click(()=>{
	if(uploadStep == 1){
		if(!placeFlag) {
			$('.upload-flag.place').html('※장소를 등록하지 않으면 다음 단계로 갈 수 없어요!');
			return;
		}
	} else if(uploadStep == 2){
		if($('#pop-review-form input:radio:checked').length == 0) {
			$('.upload-flag.radio').html('※카테고리는 필수입니다!');
			return;
		}
		if($('#pop-review-form input:checkbox:checked').length == 0) {
			$('.upload-flag.checkbox').html('※해시태그는 필수입니다!');
			return;
		}
	} else if(uploadStep == 3){
		if(!$('#pop-review-form input[name=taste]').val() || !$('#pop-review-form input[name=clean]').val() || !$('#pop-review-form input[name=service]').val()) {
			$('.upload-flag.review').html('※별점은 필수입니다!');
			return;
		}
	} else if(uploadStep == 4){
		if(photoCount < 1) {
			$('.upload-flag.photo').html('※1장 이상의 사진은 필수입니다!');
			return;
		}
	}
	$('.upload-flag').html('');
	uploadStep++;
	uploadStepControl();			
});

$('#uploadPrevBtn').click(()=>{
	uploadStep--;
	uploadStepControl();
});

let uploadStepControl = () => {
	if(uploadStep == 1) {
		$('#uploadNextBtn').show();
		$('#uploadPrevBtn').hide();
	} else if(uploadStep == 5) {
		$('#uploadNextBtn').hide();
		$('#uploadBtn').show();
	} else {
		$('#uploadBtn').hide();
		$('#uploadNextBtn').show();
		$('#uploadPrevBtn').show();
	}
	$('#uploadStep').html(uploadStep);
	$('.upload-contents li').hide();
	$('.upload-contents li[data-upload-step="'+uploadStep+'"]').show();
}

/* 리뷰 업로드 */
let uploadConfirm = (reviewId) => {
	let form = $('#frmUpload')[0];
	let formData = new FormData(form);
	formData.append("reviewId",reviewId); //수정시 ID 필요
	if(editFlag){
		$.ajax({
			type: "POST",
			url: "/timeline/edit",
			data: formData,
			contentType : false,
	    	processData : false,
		 	cache:false,
			success: () => {
				alert("성공");
				location.reload();
			},
			error: (e) => {
				alert("실패");
			}
		});
	} else{
		$.ajax({
			type: "POST",
			url: "/timeline/upload",
			data: formData,
			contentType : false,
	    	processData : false,
		 	cache:false,
			success: () => {
				alert("성공");
				location.reload();
			},
			error: (e) => {
				alert("실패");
			}
		});
	}
}