<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/myeats.css" />
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<section>
	<div class="container-wrap">
		<div class="container">
			
			<div class="group-view">
				<div class="group-profile">
					<div class="group-img">
						<img src="/resources/img/upload/01.jpg">
					</div>
				</div>
				<div class="group-info">
					<div class="group-title"><i class="fas fa-bell"></i> 맛집소녀단</div>
					<div class="group-service">
						<button class="main-btn">잇츠맵 바로가기</button>
						<button>수정</button>
						<button>삭제</button>
					</div>
					<div class="group-member">
						<h4>함께하는 잇친 리스트</h4>
						<ul>
							<li><i class="fas fa-user"></i> 알파카 <a><i class="fas fa-times"></i> 삭제</a></li>
							<li><i class="fas fa-user"></i> 퇴근시간 <a><i class="fas fa-times"></i> 삭제</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>     

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>

</script>
</body>
</html>