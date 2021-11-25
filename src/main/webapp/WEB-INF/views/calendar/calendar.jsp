<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/calendar/calendar.css" />
<script defer type="text/javascript" src="/resources/js/calendar/calendar.js"></script>
<link href='/resources/fullcalendar/main.css' rel='stylesheet'/>
<!-- <link href='https://cdn.jsdelivr.net/npm/bootstrap@4.5.0/dist/css/bootstrap.css' rel='stylesheet' />
<link href='https://cdn.jsdelivr.net/npm/@fortawesome/fontawesome-free@5.13.1/css/all.css' rel='stylesheet'> -->
<script src='/resources/fullcalendar/main.js'></script>
<script src='/resources/fullcalendar/ko.js'></script>
<script>
	 /* $(document).ready(function() {  */
	 document.addEventListener('DOMContentLoaded', function() { 
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
	    	events: [
/*  	    		{title: '치킨먹는날',start: '2021-11-25',end: '2021-11-26'},
	    		{title: '카페',start: '2021-11-27'},
	    		{title: '삼겹살',start: '2021-11-27'} */
	    		
	    		<%-- <%=request.getAttribute("schedule")%> --%>
	    		
	    		/* <c:forEach items="${schedule}" var="schedule"  varStatus="status">
	    			{title: '<c:out value="${schedule.title}"/>',
	    			start: '<c:out value="${schedule.date}"/>'}
	    		</c:forEach> */
	
	    		
	    	],
	    	eventColor: '#ffa54f',
	    	/* textColor: '#fff' */
	 }); 
    	
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
			</div>
	</section>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<%@ include file="/WEB-INF/views/calendar/makeSchedule.jsp" %>
<%@ include file="/WEB-INF/views/calendar/schedule.jsp" %>


</body>
</html>