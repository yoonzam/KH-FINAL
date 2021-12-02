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
			
			<!-- modify/post, delete/post 로 넘김-->
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
							
						 
							
							<a href="groupDetailModify?id=${groups.id}"><button class = "modifybtn">수정</button></a>
							<button class="deletebtn">삭제</button>
						</div>
					</div>
				</div>
				<div class="group-member">
					<h4>함께하는 잇친 리스트</h4>
					<ul id='nickNames'>
			</c:forEach>		
	      	<c:forEach items="${groups}" var="groups" varStatus="status"  begin="0">
					 <c:if test="${status.first}"><ul id='nickNames'></c:if>
					<c:choose>
						<c:when test="true">
						<c:if test="${groups.participants[0]!= null}">
	          				<li><i class="fas fa-user"></i> ${groups.participants[0]}</li>
	          			</c:if>
	          			<c:if test="${groups.participants[1]!= null}">
	          				<%-- <li><i class="fas fa-user"></i> ${groups.memberNickName[1]}<span>(quitting-time@naver.com)</span> <a><i class="fas fa-times"></i>삭제</a></li> --%>
	          				<li><i class="fas fa-user"></i> ${groups.participants[1]}</li>
	          			</c:if>
	          			<c:if test="${groups.participants[2]!= null}">
	          				<li><i class="fas fa-user"></i> ${groups.participants[2]} </li>
	          			</c:if>
	          			<c:if test="${groups.participants[3]!= null}">
	          				<li><i class="fas fa-user"></i> ${groups.participants[3]}</li>
	          			</c:if>
	          			<c:if test="${groups.participants[4]!= null}">
	          				<li><i class="fas fa-user"></i> ${groups.participants[4]}</li>
	          			</c:if>
	          			<c:if test="${groups.participants[5]!= null}">
	          				<li><i class="fas fa-user"></i> ${groups.participants[5]}</li>
	          			</c:if>
	          			</c:when>
						</c:choose> 
					<c:if test="${status.last}"></ul></c:if> 
			</c:forEach>	
		
	
				</div>
				<a href = "group"><button class="btn-list">그룹 목록으로 돌아가기</button></a>
				
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
	 
	 $(".modifybtn").on("click", function(){
			frmObj.attr("action", "/myeats/groupDetailModify");
			frmObj.submit();
		}); 
	 
	 
	
	 
	 
	 
});
</script>
</body>
</html>