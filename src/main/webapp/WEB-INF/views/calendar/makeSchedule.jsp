<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dimmed-wrap" id="pop-schedule-form" style="display:none;">
	<div class="dimmed"></div>
	<div class="layer-popup schedule">
		<div class="content view">
			<form class="schedule" method="post" action="upload">
				<div class="popup-title">일정 만들기</div>
				<div class="schedule-list">
					<div class="list title"><span>제목</span><input type="text" name="title" id="title" placeholder="제목을 입력하세요" required/></div>
					<div class="list date"><span>일자</span><input type="date" name="date" id="date" required/></div>
					<div class="list time"><span>시간</span><input type="time" name="time" id="time" required/></div>
					<div class="list place"><span>장소</span><input type="text" name="resName" id="location" placeholder="장소를 입력하세요" required/><ul class="locationList"></ul></div>	
					<div class="list member"><span>참석자</span>
						<select class="list" name="participants[]" id="participant" multiple="multiple">
							<option value="" id="default-option">---함께할 잇친을 추가하세요---</option>
						</select>
					</div>
					<input class="hidden" type="text" name="latitude" style="display: none">
					<input class="hidden" type="text" name="longitude" style="display: none">
					<input class="hidden" type="text" name="scheduleId" id= "scheduleId" style="display: none">
					<div class="btn-wrap">
						<button class="submit" id="save-event">확인</button>
						<button class="cancel" id="cancel-btn">취소</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<a class="close-btn" onclick="closePopup();"><i class="fas fa-times"></i></a>
<script type="text/javascript">
$(document).ready(() => {
    $.ajax({      
        type:"GET",  
        url: '/calendar/memberList',   
        dataType: 'json',
        success:function(data){ 
        	console.dir(data);
        	if(data != null){
          		let html = '';
          		let textNode = '';
          		
    	      	for (var i = 0; i < data.length; i++){
    	      		
    	      		let option = document.createElement("option");
    	      		
    	      		option.value = data[i].memberId;
    	      		option.innerHTML = data[i].member.nickname;
    	      		
    				document.querySelector('#participant').appendChild(option);
    	      	}         		
        	}
        }
    });
});

</script>
</div>

<script>

// 캘린더 일정 만들기 - 장소 검색
$('input[name="resName"]').keyup(function () {
    let keyword = $(this).val();
    if (!keyword) {
        $('.locationList').html('');
        return;
    }
    // 장소 검색 객체 생성
    let ps = new kakao.maps.services.Places();
    // 키워드로 장소 검색
    ps.keywordSearch(keyword, (data, status) => {
        if (status === kakao.maps.services.Status.OK) {
            let html = '';
            searchPlaces = data;
            for (var i = 0; i < data.length; i++) {
                if (data[i].category_group_code == 'FD6' || data[i].category_group_code == 'CE7')
                    html += '<li data-place-idx="' + i + '"><span class="place-name">' + data[i].place_name + '</span> <span class="road-address-name">' + data[i].road_address_name + '</span></li>';
            }
            $('.locationList').html(html);
            $('.locationList').show();
        }
    });
});

$('.locationList').click(function (e) {
    let placeIdx = e.target.dataset.placeIdx;
    let place = searchPlaces[placeIdx];
    $('input[name="resName"]').val(place.place_name);
    $('input[name="latitude"]').val(place.y);
    $('input[name="longitude"]').val(place.x);

    $('.locationList').hide();
})


$('#sch-delete-btn').on('click', function () {

    /*$('#sch-delete-btn').unbind();
    calendar.fullCalendar('removeEvents', $(this).data('id'));
    // delete ?*/
    $('#pop-schedule-detail').hide();
})

</script>