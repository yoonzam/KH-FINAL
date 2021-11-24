$(document).ready(()=>{
    getSectionMargin();
    resizeSlideImgHeight();
});

$(window).resize(() => {
    getSectionMargin();
    resizeSlideImgHeight();
});

$('.gnb li').hover((e)=> {
    e.target.style.color='#fa8633';
    e.target.style.transitionDuration='0.2s';
  }, (e)=>{
    e.target.style.color='#333';
});

let getSectionMargin = () => {
    $('section').css('padding-top',$('header').height());
}

let resizeSlideImgHeight = () => {
    let slideImgWidth = $('.layer-popup .slide-img-wrap').width();
    $('.layer-popup .slide-img').height(slideImgWidth);
    $('.layer-popup .slide-img li').height(slideImgWidth);
    $('.layer-popup .info-wrap').height(slideImgWidth);
    $('.layer-popup .slide-btn').css('margin-top',(slideImgWidth/2-18)+'px');
}

/* popup btn */
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
/* 맵 후기 버튼을 구분하기 위한 변수 */
let reviewBtn = false;

$('.dimmed').click(()=>{
    closePopup();
    reviewBtn = false;
});
let closePopup = () => {
    $('.dimmed-wrap').fadeOut(200);
}

/* 후기등록 */
let uploadStep;
let placeFlag;
let searchPlaces;

/* 맵상의 후기 버튼 누를 경우 flag */
$("#map_reviewBtn").click(function(event){
      reviewBtn = true;		
});


$('#btnReview, #map_reviewBtn').click(() => {
	//초기화
	uploadStep = 1;
	placeFlag = false;
	searchPlaces = [];
	uploadStepControl();
	


	
	$('.upload-flag').html('');
	$('#uploadPrevBtn').hide();
	$('#uploadNextBtn').show();
	$('#uploadBtn').hide();
	$('#pop-review-form input:text').val('');
	$('#pop-review-form input:file').val('');
	$('#pop-review-form input:radio').prop('checked', false);
	$('#pop-review-form input:checkbox').prop('checked', false);
	$('.hashtag label').removeClass('checked');
	$('.star-review i').attr('class','far fa-star');
	$('#pop-review-form textarea').val('');
	$('.preview-photo').css('display','none');
	$('#pop-review-form').fadeIn(200);
	
	let options = { center: new kakao.maps.LatLng(37.55317, 126.97279), level: 8 };
	let map = new kakao.maps.Map(document.getElementById('uploadMap'), options);
	
	/* 맵 화면상의 후기 입력버튼을 누를 경우 카카오맵과 검색창에 미리 정보 기입*/
	if(reviewBtn){
		let placeInfo = markerInfo;	
		console.dir(placeInfo);
		$('input[name="uploadPlace"]').val(markerInfo.place_name);
		$('input[name="resName"]').val(markerInfo.place_name);
		$('input[name="addr"]').val(markerInfo.road_address_name);
		$('input[name="latitude"]').val(markerInfo.y);
		$('input[name="longitude"]').val(markerInfo.x);
		let options = { center: new kakao.maps.LatLng(placeInfo.y, placeInfo.x), level: 5 };
		let map = new kakao.maps.Map(document.getElementById('uploadMap'), options);
		let content = '<div class="marker-wrap"><p><i class="fas fa-utensils color-m"></i> '+placeInfo.place_name+'</p><div></div></div>';
		let customOverlay = new kakao.maps.CustomOverlay({
		    position: new kakao.maps.LatLng(placeInfo.y, placeInfo.x),
		    content: content
		});
		customOverlay.setMap(map);
		placeFlag = true;
		
	}
	
	$('input[name="uploadPlace"]').keyup(function(){
		let keyword = $(this).val();
		if(!keyword) {
			$('.location-list').html('');
			return;
		}
		
		// 장소 검색 객체 생성
		let ps = new kakao.maps.services.Places();
		// 키워드로 장소 검색
		ps.keywordSearch(keyword, (data, status) => {
			if (status === kakao.maps.services.Status.OK) {
				$('.location-list').html(displayPlaces(data));
				$('.location-list').show();
			}
		}); 
	});
	
	//검색리스트 출력
	let displayPlaces = (places) => {
		searchPlaces = places;
		html = '';
		for ( var i=0; i<places.length; i++ ) {
			if(places[i].category_group_code == 'FD6' || places[i].category_group_code == 'CE7' )
				html += '<li data-place-idx="'+i+'"><span class="place-name">'+places[i].place_name+'</span> <span class="road-address-name">'+places[i].road_address_name+'</span></li>';
		}
		return html;
	}
	
	//검색리스트 클릭시 지도에 표시
	$('.location-list').click(function(e){
		let placeIdx = e.target.dataset.placeIdx;
		let place = searchPlaces[placeIdx];
		$('input[name="uploadPlace"]').val(place.place_name);
		$('input[name="resName"]').val(place.place_name);
		$('input[name="addr"]').val(place.road_address_name);
		$('input[name="latitude"]').val(place.y);
		$('input[name="longitude"]').val(place.x);
		
		drawSpecificMap(place);
		placeFlag = true;
		$('.location-list').hide();
	})
	
	//검색한 음식점 맵에 표시
	let drawSpecificMap = (place) => {
		let latitude = place.y;
		let longitude = place.x;
		let options = { center: new kakao.maps.LatLng(latitude, longitude), level: 5 };
		let map = new kakao.maps.Map(document.getElementById('uploadMap'), options);
		let content = '<div class="marker-wrap"><p><i class="fas fa-utensils color-m"></i> '+place.place_name+'</p><div></div></div>';
		
		let customOverlay = new kakao.maps.CustomOverlay({
		    position: new kakao.maps.LatLng(place.y, place.x),
		    content: content
		});
		customOverlay.setMap(map);
	}
	
	$('.review-upload input:radio').click((e)=>{
	    let label = e.target.parentNode;
	    $('.hashtag .radio label').removeClass('checked');
	    label.classList.add('checked');
	});
	
	$('.review-upload input:checkbox').click((e)=>{
	    let label = e.target.parentNode;
	    label.classList.toggle('checked');
	});
	
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
	
	//파일
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
		}
	});
	
	$('.fa-times-circle').click((e)=>{
		let div = e.target.parentNode;
		div.style.display = 'none';
		div.parentNode.children[1].value='';
	})
	
	
});

let deletePhoto = (e) => {
	let div = e.parentNode;
	div.innerHTML = '';
	div.classList.remove('preview-photo');
	console.log(div);
}

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
		if(!$('#photo1').val() && !$('#photo2').val() && !$('#photo3').val()) {
			$('.upload-flag.photo').html('※1장 이상의 사진은 필수입니다!');
			return;
		}
	}
	$('.upload-flag').html('');
	uploadStep ++;
	uploadStepControl();			
});

$('#uploadPrevBtn').click(()=>{
	uploadStep --;
	uploadStepControl();
});

$('#uploadBtn').click(()=>{
	let form = $('#frmUpload')[0];
	let formData = new FormData(form);
	$.ajax({
		type: "POST",
		url: "/timeline/upload",
		data: formData,
		contentType : false,
	    processData : false,
	 	cache:false,
		success: () => {
			alert("성공");
			window.reload();
		},
		error: (e) => {
			alert("실패");
		}
	});
})

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
