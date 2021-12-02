<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/calendar/calendar.css" />
<script defer type="text/javascript" src="/resources/js/calendar/calendar.js"></script>
<link href='/resources/fullcalendar/main.css' rel='stylesheet'/>
<script src='/resources/fullcalendar/main.js'></script>
<script src='/resources/fullcalendar/ko.js'></script>

</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<section>
		<div class="section"></div>
			<div class="section-wrap">
				<div class="calendar-area">
					<div id="calendar"></div>
				</div>
			</div>
	</section>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<%@ include file="/WEB-INF/views/calendar/schedule.jsp" %>
<script>
	 /* $(document).ready(function() {  */
	 document.addEventListener('DOMContentLoaded', function() {

    	let schedules = [];

    	var calendarEl = document.getElementById('calendar');

    	var calendar = new FullCalendar.Calendar(calendarEl, {
    		
    		/* themeSystem: 'bootstrap', */
    		height: 800,
    		initialView: 'dayGridMonth',
    		selectable: true,
    		locale: 'ko',
	    	
    		headerToolbar: {
	    		left: 'custom',
	    		center: 'title',
	    		right: 'prev,next'
	    	},
	    	
	    	customButtons: {
	    		custom:{
	    			text: '일정 만들기',
	    			click: function(){
	    				$('#pop-schedule-form').css({'display': 'flex'});
	    			}
	    		}
    		},
    		
    		eventSources : [{
    			events: function(info, successCallback, failureCallback) {
    		     	$.ajax({
    		 			url : '/calendar/getSchedule',
    		 			dataType: 'json',
    		 			success: function(datas){
    		 			
    		 				for (var i = 0; i < datas.length; i++) {
    		 					let obj = {
    		 						'id' : datas[i].calendarId,
    		 						'title' : datas[i].calendar.title,
    		 						'start' : datas[i].calendar.date
    		 					}
    		 					schedules.push(obj);
    		 				}
    		 				successCallback(schedules);
    		 			}
    		     	})	
    			},
    			color : '#ffa54f'
    		}],
    		
    		eventClick: function(info){
    			$.ajax({
		 			url : '/calendar/detail',
		 			data:{'id': info.event.id},
		 			dataType: 'json',
		 			success: (data) => {
		 			
		 				$('#detail-title').text(data.calendar.title);
		 				$('#detail-date').text(data.calendar.date);
		 				$('#detail-time').text(data.calendar.time);
		 				$('#detail-place').text(data.calendar.resName);
		 				
		 				/* ObjectId[] participants = request.getParameterValues("participants[]");
		 				for(int i = 0, len = participants.length; i<len ; i++){
		 					Object participants = participants[i];
		 				} */
		 				
		 				/* $('#detail-participant').text(data.calendar.participant); */
		 				$('#detail-participant').text(data.calendar.participants);
		 				
		 				$('#sch-change-btn').click(e => {
		 					
		 					alert('일정을 수정합니다');
		 					$('#pop-schedule-detail').hide();
		 					$('#pop-schedule-form').show();
		 					$('#save-event').text('수정완료');
		 					
		 					console.dir(data.calendar);
		 					
		 					$('#scheduleId').val(data.calendarId);
							$('#title').val(data.calendar.title);
							$('#date').val(data.calendar.date);
							$('#time').val(data.calendar.time);
							$('#location').val(data.calendar.resName);
							$('#participant').val(data.calendar.participant);
							$('input[name="latitude"]').val(data.calendar.location.coordinates[1]);
							$('input[name="longitude"]').val(data.calendar.location.coordinates[0]);
							
		 				})
		 				
		 				$('#sch-delete-btn').click(e => {
		 					//비동기 통신 필요 url : /calendar/delete (post)
		 					//같이 보낼 데이터 : id -> data.calendarId
		 					//success 이후 과정은 다른 코드 참조(복붙)
		 					$.ajax({
						 			url : '/calendar/delete',
						 			type: 'post',
						 			data:{'id': data.calendarId},
						 			dataType: 'json',
						 			
						 			success: (data) => {
						 				alert('일정이 삭제되었습니다.');
						 				$('#pop-schedule-detail').hide();
						 			}
		 					})
		 				
		 				})
		 			}
    			})
		 			
    			$('#pop-schedule-detail').show();
    		}//eventClick
	    		
	 	}); 
    	calendar.render();
    });
	
	 /* $('.fc-event-title-container').click(function(){
			$('#pop-schedule-detail').show();
		}) */
	
		
		
</script>
</body>
</html>