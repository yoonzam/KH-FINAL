var editTitle = $('#title');
var editDate = $('#date');
var editTime = $('#time');
var editPlace = $('#location');
var editMember = $('#participant');

/*새로운 일정 생성*/
var newEvent = function(start,end,eventType){
	
	editTitle.val('');
	editDate.val('');
	editTime.val('');
	editPlace.val('');
	editMember.val('');
	
	$('save-event').unbind();
	$('save-event').on('click', function(){
		var eventData={
			_id: eventId,
			title: editTitle.val(),
			date: editDate.val(),
			time: editTime.val(),
			place: editPlace.val(),
			member: editMember.val()
		};
		
		if(eventData.title === ''){
			alert('제목을 입력해주세요.');
			return false;
		}
		
		if(eventData.date === ''){
			alert('날짜를 입력해주세요.');
			return false;
		}
		
		if(eventData.time === ''){
			alert('시간을 입력해주세요.');
			return false;
		}
		
		if(eventData.place === ''){
			alert('장소를 입력해주세요.');
			return false;
		}
		
		// 새로운 일정 저장
		$.ajax({
			type: "get",
			url: "",
			data: {
				//...
			},
			success: function (response){
				
			}
		})
		
		
		
		
	});
	
	
	
	
	
	
	
}





