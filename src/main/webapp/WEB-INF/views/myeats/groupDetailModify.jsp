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
			
			<form role = "form" action="/myeats/groupDetailModify" method="post">
			
			<div class="group-view">
			<c:forEach items="${groupService}" var="groupService">
			<form role="form" method="post">	
				<input type="hidden" name="id" value="${groupService.id}" />
			</form>
			
				<div class="group-info">
					<div class="group-profile">
						<div class="group-img">
							<img src="/resources/img/upload/01.jpg">
						</div>
					</div>
					<div class="group-menu">
						<input type="hidden" name="groupIdx" value="${groupService.groupIdx}" />
						<div class="group-title"><i class="fas fa-bell"></i> ${groupService.groupName}
						<input type="text" class="form-control" name="groupName"
								placeholder="${groupService.groupName}"	value="${groupService.groupName}"
								maxlength="8" required >
						
						</div>
						<div class="group-service">
							<button class="main-btn">잇츠맵 바로가기</button>
							<button>수정</button>
							<button class="deletebtn">삭제</button>
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
				
				
				<a href = "group"><button class="btn-list">그룹 목록으로 돌아가기</button></a>
			</c:forEach>	
			</div>
			</form>
		</div>
		
	</div>
</section>     

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>


$(document).ready(function(){
	var frmObj = $("form[role='form']");
	console.log("group.jsp지정된 폼태그..");
	
	
	 $(".deletebtn").on("click", function(){
		frmObj.attr("action", "/myeats/delete");
		frmObj.submit();
	}); 
	 
	//수정처리 페이지 이동
		$("#btn_save").on("click", function(){
			//frmObj.attr("action","/bbs/modifyPage");
			frmObj.submit();
		});
	
});
</script>
</body>
</html>