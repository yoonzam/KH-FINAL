<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/myeats.css" />
<style type="text/css">

/* css 상태 보고 추후에 이동 예정 */
.form-control{
    width: 83.5%;
    padding: 13px;
    border-radius: 5px;
    border: 1px solid #aaa;
}
</style>

</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<section>
	<div class="container-wrap">
		<div class="container">
			
			
			
			<div class="group-view">
			<c:forEach items="${groups}" var="groups">
			<form role = "form" action="/myeats/groupDetailModify" method="post" enctype="multipart/form-data">	
				<input type="hidden" id = "id" name="id" value="${groups.id}" />
				
				<div class="group-info">
					<div class="group-profile">
						<div class="group-img">
							<img src="${!empty groups.thumUrl ? groups.thumUrl : '/resources/img/common/upload-logo.png'}" name="thumUrl"
							value="${groups.thumUrl}">
							<!-- <li>
							<span>프로필</span>
							<input id="photo1" type="file"name="photos" accept=".gif, .jpg, .jpeg, .png">
							<div class="preview-photo photo1">
							</div>
							</li> -->
						</div>
					</div>
					<div class="group-menu">
						<div class="group-title"><i class="fas fa-bell"></i>
						<input type="text" class="form-control" name="groupName"
								placeholder="${groups.groupName}"	value="${groups.groupName}"
								maxlength="8" required >
						
						</div>
						<div class="group-service">
							<button class="main-btn">잇츠맵 바로가기</button>
							<button class="complete-btn">완료</button>
							<button class="delete-btn">삭제</button>
						</div>
					</div>
				</div>
				
				<div class="group-member">
					<h4>함께하는 잇친 리스트</h4>
					<ul>
						<li><i class="fas fa-user"></i> ${groups.memberNickName[0]}<span>(alpaca@naver.com)</span> <a><i class="fas fa-times"></i>삭제</a></li>
						<li><i class="fas fa-user"></i> ${groups.memberNickName[1]}<span>(quitting-time@naver.com)</span> <a><i class="fas fa-times"></i>삭제</a></li>
					</ul>
				</div>
				</form>
				
				<a href = "group"><button class="btn-list">그룹 목록으로 돌아가기</button></a>
			</c:forEach>	
			</div>
			
		</div>
		
	</div>
</section>     

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>


$(document).ready(function(){
	var frmObj = $("form[role='form']");
	console.log("group.jsp지정된 폼태그..");
	
	
	 $(".delete-btn").on("click", function(){
		frmObj.attr("action", "/myeats/delete");
		frmObj.submit();
	}); 
	 
	//수정처리 페이지 이동
		$(".complete-btn").on("click", function(){
		frmObj.attr("action", "/myeats/groupDetailModify");
		formObj.attr("method", "post");
		frmObj.submit();
		});
	
});
</script>
</body>
</html>