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
$('#btnReview').click(() => {
	let searchPlaces;
	
	$('#pop-review-form').fadeIn(200);
	let options = { center: new kakao.maps.LatLng(37.55317, 126.97279), level: 8 };
	let map = new kakao.maps.Map(document.getElementById('uploadMap'), options);
	
	$('input[name="uploadLocation"]').keyup(function(){
		let keyword = $(this).val();
		$('.location-list').html('');
		if(!keyword) return;
		
		// 장소 검색 객체 생성
		let ps = new kakao.maps.services.Places();
		// 키워드로 장소 검색
		ps.keywordSearch(keyword, (data, status) => {
			if (status === kakao.maps.services.Status.OK) displayPlaces(data);
		}); 
	});
	
	//검색리스트 출력
	let displayPlaces = (places) => {
		searchPlaces = places;
		html = '';
		for ( var i=0; i<places.length; i++ ) {
			if(places[i].category_group_code == 'FD6' || places[i].category_group_code == 'CE7' )
				html += '<li data-place-value="'+i+'"><span class="place-name">'+places[i].place_name+'</span> <span class="road-address-name">'+places[i].road_address_name+'</span></li>';
		}
		$('.location-list').html(html);
		$('.location-list').show();
	}
	
	//검색리스트 클릭시 지도에 표시
	$('.location-list').click(function(e){
		$('.location-list').hide();
		let placeValue = e.target.dataset.placeValue;
		drawSpecificMap(searchPlaces[placeValue]);
	})
	
	//검색한 음식점 맵에 표시
	let drawSpecificMap = (place) => {
		let options = { center: new kakao.maps.LatLng(place.y, place.x), 
						level: 5, 
						keyboardShortcuts:true};
		let map = new kakao.maps.Map(document.getElementById('uploadMap'), options);
		let content = '<div class="marker-wrap"><p>'+place.place_name+'</p><div></div></div>';
		
		let customOverlay = new kakao.maps.CustomOverlay({
		    position: new kakao.maps.LatLng(place.y, place.x),
		    content: content,
		    xAnchor: 0.3,
		    yAnchor: 0.91
		});
		customOverlay.setMap(map);
	}
});
	
