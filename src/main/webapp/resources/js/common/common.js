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

$('.dimmed').click(()=>{
    closePopup();
});
let closePopup = () => {
    $('.dimmed-wrap').fadeOut(200);
}

/* 후기등록 */
let uploadStep;
let placeFlag;
let searchPlaces;

$('#btnReview').click(() => {
	//초기화
	uploadStep = 1;
	placeFlag = false;
	searchPlaces = [];
	uploadStepControl();
	
	$('.upload-flag').html('');
	$('#uploadPrevBtn').hide();
	$('#uploadNextBtn').show();
	$('#uploadBtn').hide();
	$('input[name="uploadPlace"]').val('');
	$('#pop-review-form').fadeIn(200);
	
	let options = { center: new kakao.maps.LatLng(37.55317, 126.97279), level: 8 };
	let map = new kakao.maps.Map(document.getElementById('uploadMap'), options);
	
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
		console.log(place);
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
	    $('.hashtag label').removeClass('checked');
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
	
});

$('#uploadNextBtn').click(()=>{
	if(!placeFlag){
		$('.upload-flag.place').html('※장소를 등록하지 않으면 다음 단계로 갈 수 없어요!');
		return;
	} else{
		$('.upload-flag.place').html('');
	}
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
		success: (text) => {
			alert("성공");
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
