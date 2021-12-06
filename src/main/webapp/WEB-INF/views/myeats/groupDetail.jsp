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
				<c:forEach items="${groups}" var="groups">
				<form role="form" method="post">	
					<input type="hidden" id="id" name="id" value="${groups.id}" />
				</form>
				<div class="group-info">
					<div class="group-profile">
						<div class="group-img">
							<img src="${!empty groups.thumUrl ? groups.thumUrl : '/resources/img/common/upload-logo.png'}">
						</div>
					</div>
					<div class="group-menu">
						<div class="group-title"><i class="fas fa-bell"></i> ${groups.groupName}</div>
						<div class="group-service">
							<button class="main-btn">잇츠맵 바로가기</button>
							<c:if test="${authentication.id == groups.memberId }">
								<a href="/myeats/groupDetailModify?id=${groups.id}"><button class = "modifybtn">수정</button></a>
								<button class="deletebtn">삭제</button>
							</c:if>
							<c:if test="${authentication.id != groups.memberId }">
								<button class="leavebtn">그룹 나가기</button>
							</c:if>
						</div>
					</div>
				</div>
				</c:forEach>	
				<div class="group-member">
					<h4>함께하는 잇친 리스트</h4>
					<ul id='nickNames'>
					<c:forEach items="${nickNames}" var="nickNames" >
						<li><i class="fas fa-user"></i>&nbsp; ${nickNames}</li>
					</c:forEach>
					</ul>
				</div>
				<a href = "group"><button class="btn-list">그룹 목록으로 돌아가기</button></a>
			</div><!-- group-view -->
		</div><!-- container -->
	</div><!-- container-wrap -->
</section>     

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>
$(document).ready(function(){
	var frmObj = $("form[role='form']");
	
	 $(".leavebtn").on("click", function(){
			frmObj.attr("action", "/myeats/groupLeave");
			frmObj.attr("method", "post");
			frmObj.submit();
	});
	
	 $(".deletebtn").on("click", function(){
		frmObj.attr("action", "/myeats/delete");
		frmObj.submit();
	}); 
	 
	 $(".modifybtn").on("click", function(){
			frmObj.attr("action", "/myeats/groupDetailModify");
			frmObj.submit();
	}); 
});
</script>
</body>
</html>