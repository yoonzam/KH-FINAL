<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
			<c:forEach items="${groupService}" var="groupService">
				<div class="group-info">
					<div class="group-profile">
						<div class="group-img">
							<img src="/resources/img/upload/01.jpg">
						</div>
					</div>
					<div class="group-menu">
						<input type="hidden" name="bid" value="${groupService.groupIdx}" />
						<div class="group-title"><i class="fas fa-bell"></i> ${groupService.groupName}</div>
						<div class="group-service">
							<button class="main-btn">잇츠맵 바로가기</button>
							<button>수정</button>
							<button>삭제</button>
						</div>
					</div>
				</div>
				<div class="group-member">
					<h4>함께하는 잇친 리스트</h4>
					<ul>
						<li><i class="fas fa-user"></i> 알파카<span>(alpaca@naver.com)</span> <a><i class="fas fa-times"></i>삭제</a></li>
						<li><i class="fas fa-user"></i> 퇴근시간<span>(quitting-time@naver.com)</span> <a><i class="fas fa-times"></i>삭제</a></li>
					</ul>
				</div>
				<button class="btn-list">그룹 목록으로 돌아가기</button>
			</c:forEach>	
			</div>
		</div>
	</div>
</section>     

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>

</script>
</body>
</html>