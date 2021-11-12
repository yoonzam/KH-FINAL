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
<script>
	 $(document).ready(function() { 
	/* document.addEventListener('DOMContentLoaded', function() { */
    	var calendarEl = document.getElementById('calendar');
    	var calendar = new FullCalendar.Calendar(calendarEl, {
    		initialView: 'dayGridMonth',
    		locale: 'ko'
    		}
    	
    	/* ,
	    	headerToolbar:{
	    		left: 'today',
	    		center: 'title',
	    		right: 'custom'
	    	},
	    	customButtons:{
	    		custom:{
	    			text: '일정만들기',
	    			click: function(){
	    				alert('clicked schedule btn');
	    			}
	    		}
    		}
	    	
    	); */
    	
    	calendar.render();
    });
</script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<section>
		<div class="section"></div>
			<div class="section-wrap">
				<div class="calendar-area">
					<div id="calendar"></div>
				</div>
				<!-- <div class="schedule-area">
					<div class="btn-schedule">일정 만들기</div>
					<div class="schedule-wrap">
						<div class="title" id="sch-content"><span>제목</span><input class="input" type="text" placeholder="제목을 입력하세요"/></div>
						<div class="date" id="sch-content"><span>일자</span><input class="input" type="date"/></div>
						<div class="time" id="sch-content"><span>시간</span><input class="input" type="time"/></div>
						<div class="place" id="sch-content"><span>장소</span><input class="input" type="text" placeholder="장소를 검색하세요"/></div>
						<div class="member" id="sch-content"><span>참석자</span><input class="input" type="text" placeholder="함께할 잇친 추가"/></div>
						<div class="btn-wrap" id="sch-content">
							<div class="btn-yes">확인</div>
							<div class="btn-no">취소</div>
						</div>
					</div>
				</div> -->
			</div>
	</section>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script>
	$('.btn-schedule').click(function(){
		$('.schedule-wrap').addClass('active')
	})
	
	$('.btn-yes, .btn-no').click(function(){
		$('.schedule-wrap').removeClass('active')
	})
</script>

</body>
</html>