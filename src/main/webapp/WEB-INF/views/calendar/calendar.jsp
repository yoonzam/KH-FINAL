<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/calendar/calendar.css" />
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
	 document.addEventListener('DOMContentLoaded', function() {
		 
    	let schedules = [];

    	var calendarEl = document.getElementById('calendar');
    	var calendar = new FullCalendar.Calendar(calendarEl, {
    		
    		/* themeSystem: 'bootstrap', */
    		contentHeight: 100,
    		height: 800,
    		initialView: 'dayGridMonth',
    		fixedWeekCount: false,
    		selectable: true,
    		locale: 'ko',
    		
    		/* eventLimit: true,
    		views: {
    			day : {eventLimit : 5}
    		}, */
	    	
	    	dayMaxEvents: 1,
    		
    		
    		headerToolbar: {
	    		left: 'custom',
	    		center: 'title',
	    		right: 'prev,next'
	    	},
	    	
	    	customButtons: {
	    		custom:{
	    			text: '일정 만들기',
	    			click: function(){
	    				
	    				viewCalendarForm();
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
		 				let text = '';
		 				$('#detail-title').text(data.calendar.title);
		 				$('#detail-date').text(data.calendar.date);
		 				$('#detail-time').text(data.calendar.time);
		 				$('#detail-place').text(data.calendar.resName);
		 				
		 				if (data.participant.length == 0) {
		 					$('#participant-tit').remove();
						}
		 				
		 				for (var i = 0; i < data.participant.length; i++) {
		 					text += data.participant[i].nickname + ' ';
						}
		 				$('#detail-participant').text(text);
		 				
		 				
		 				
		 				//$('#detail-participant').text(data.calendar.participant.nickname);
		 				
		 				
		 				
		 				$('#sch-change-btn').click(e => {
		 					
		 					$('#pop-schedule-detail').hide();
		 					//$('#pop-schedule-form').show();
		 					$('#save-event').text('수정완료');
		 					viewCalendarForm();
		 					
		 					for (var i = 0; i < data.participant.length; i++) {
			 					text += data.participant[i].nickname + ' ';
							}
		 					
		 					$('#scheduleId').val(data.calendarId);
							$('#scheduleForm-title').val(data.calendar.title);
							$('#scheduleForm-date').val(data.calendar.date);
							$('#scheduleForm-time').val(data.calendar.time);
							$('#scheduleForm-location').val(data.calendar.resName);
							//$('#participant').val(data.calendar.participant);
							$('input[name="latitude"]').val(data.calendar.location.coordinates[1]);
							$('input[name="longitude"]').val(data.calendar.location.coordinates[0]);
							
		 				})
		 				
		 				$('#sch-delete-btn').click(e => {
		 					$('#pop-schedule-detail').hide();
		 					$.ajax({
						 			url : '/calendar/delete',
						 			type: 'post',
						 			data:{'id': data.calendarId},
						 			dataType: 'json',
						 			
						 			success: () => {
						 				alert('일정이 삭제되었습니다.');
						 				location.reload();
						 			},
						 			error: () => {
						 				location.reload();
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
	
	
		
		
</script>
</body>
</html>