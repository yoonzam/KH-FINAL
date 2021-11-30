$(document).ready(()=>{
    resizeImg();
});

$(window).resize(() => {
    resizeImg();
});

let resizeImg = () => {
    let thum = $(".timeline-brd .thum");
    let thumWidth = thum.width();
    Array.from(thum).forEach((e)=>{
        let thumImg = e.children[0];
        e.style.height=thumWidth+'px';
        if(thumImg.width >= thumImg.height){
            thumImg.style.height=thumWidth+'px';
        } else{
            thumImg.style.width=thumWidth+'px';
        }
    })
}

$('.eats-list').hover((e) => {
    e.currentTarget.children[0].children[0].style.transform='scale(1.1)';
    e.currentTarget.children[0].children[0].style.transitionDuration='0.5s';
  }, (e)=>{
    e.currentTarget.children[0].children[0].style.transform='scale(1)';
});

$('.filter-btn').click(()=>{
    if($('.filter-menu').css('display')=='none'){
        $('.filter-menu').show();
        $('.filter-btn').css('border','1px solid #fa8633');
    } else{
        $('.filter-menu').hide();
        $('.filter-btn').css('border','1px solid #ddd');
    }
});

$('#allCheck').click(()=>{
    if($('#allCheck').attr('class')=='checked'){
        $('.filter-menu label').removeClass('checked');
        $('.filter-menu input:checkbox').prop('checked', false);
    } else{
        $('.filter-menu label').addClass('checked');
        $('.filter-menu input:checkbox').prop('checked', true);
    }
});

$('.filter-menu input:checkbox').click((e)=>{
    let checkbox = e.currentTarget;
    let label = e.currentTarget.parentNode;
    label.classList.toggle('checked');
});



		// 캘린더 일정 만들기 - 장소 검색
		$('input[name="resName"]').keyup(function(){
		let keyword = $(this).val();
		if(!keyword) {
			$('.locationList').html('');
			return;
		}
		
		// 장소 검색 객체 생성
		let ps = new kakao.maps.services.Places();
		// 키워드로 장소 검색
		ps.keywordSearch(keyword, (data, status) => {
			if (status === kakao.maps.services.Status.OK) {
				$('.locationList').html(displayPlaces(data));
				$('.locationList').show();
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
	
	$('.locationList').click(function(e){
		let placeIdx = e.target.dataset.placeIdx;
		let place = searchPlaces[placeIdx];
		$('input[name="resName"]').val(place.place_name);
		$('input[name="latitude"]').val(place.y);
		$('input[name="longitude"]').val(place.x);
		
		$('.locationList').hide();
	})

/*	$('#sch-change-btn').click((e)=>{
		alert('일정을 수정합니다');
		$('#pop-schedule-detail').hide();
		$('#pop-schedule-form').show();
		
		$.ajax({
			type: 'GET',
			url: '/calendar/edit',
			data:{ id:reviewId },
			dataType: 'json',
			success: (data) => {
				$('input[name="title"]').text(data.title);
				$('input[name="date"]').text(data.date);
				$('input[name="time"]').text(data.time);
				$('input[name="resName"]').text(data.resName);
				$('input[name="participant"]').text(data.participant);			
			}
		})
		

	})*/
	
	$('#sch-delete-btn').on('click', function(){
		
		/*$('#sch-delete-btn').unbind();
		calendar.fullCalendar('removeEvents', $(this).data('id'));
		// delete ?*/
		$('#pop-schedule-detail').hide();
	})


	/*$('#sch-delete-btn').click(function() {
     	calendar.fullCalendar('removeEvents',event._id);
  	});*/

