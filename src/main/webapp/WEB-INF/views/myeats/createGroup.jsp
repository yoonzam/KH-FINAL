<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
    <%
	Date nowTime = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
%>

    
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
			<h3><i class="fas fa-users-cog"></i> 새로운 그룹 만들기</h3>
			<form method="post">
			<input type="hidden" name="groupcreatedate" value="<%= sf.format(nowTime) %>">
			<div class="group-form">
				<ul>
					<li>
						<span>프로필</span>
						<input type="file">
					</li>
					<li>
						<span>그룹이름</span>
						<input type="text" placeholder="그룹 이름을 입력하세요."name="groupName"  maxlength="8" required >
					</li>
					<li>
						<span>초대하기</span>
						<div class="friend-list">
							<input type="text" placeholder="초대할 친구의 닉네임을 입력하세요."name="memberId">
							<button>초대</button>
							<span><i class="fas fa-minus-square"></i> 알파카</span>
							<span><i class="fas fa-minus-square"></i> 퇴근시간</span>
						</div>
					</li>
				</ul>
			</div>
			<div class="btn-area">
				<a href = "group"><button class="cancel-btn">취소</button></a>
				<a href = "createGroup"><button type="submit" class="create-btn">만들기</button></a>
			</div>
			</form>
		</div>
	</div>
</section>     

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>

</script>
</body>
</html>