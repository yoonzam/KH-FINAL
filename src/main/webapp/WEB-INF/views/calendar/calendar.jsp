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
<%@ include file="/WEB-INF/views/calendar/makeSchedule.jsp" %>
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
		 			
		 				$('#detail-title').text(data.title);
		 				$('#detail-date').text(data.date);
		 				$('#detail-time').text(data.time);
		 				$('#detail-place').text(data.resName);
		 				$('#detail-participant').text(data.participant);
		 				
		 				$('#sch-change-btn').click(e => {
		 					
		 					alert('일정을 수정합니다');
		 					$('#pop-schedule-detail').hide();
		 					$('#pop-schedule-form').show();
		 					
		 					$.ajax({
		 						type: 'GET',
		 						url: '/calendar/edit',
		 						data:{ id: info.event.id },
		 						dataType: 'json',
		 						success: (data) => {
		 							$('input[name="title"]').text(data.title);
		 							$('input[name="date"]').text(data.date);
		 							$('input[name="time"]').text(data.time);
		 							$('input[name="resName"]').text(data.resName);
		 							$('input[name="participant"]').text(data.participant);			
		 						}
		 					})
		 				})
		 				
		 			}
    			})
		 			
    			$('#pop-schedule-detail').show();
    		
    			
    		}
	    		
	 	}); 
    	calendar.render();
    });
	
	 /* $('.fc-event-title-container').click(function(){
			$('#pop-schedule-detail').show();
		}) */
	
		
		
</script>
</body>
</html>