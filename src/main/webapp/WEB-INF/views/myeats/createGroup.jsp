<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
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
							<%-- <c:forEach items="${group}" var="group"> --%>
							<!-- memberId는 배열값 > Group-->
							<input type="text"  id='addValue' placeholder="초대할 친구의 닉네임을 입력하세요."name="memberNickName[]"
							 value="<%=request.getAttribute("keyword")%>">
							
							
							<input type='button' value='추가' onclick='addList()' style="width: 50px; background-color: orange;" />
							<a href="invite"><button type="button">초대</button></a>
							
							
							<span><a href="invite"><i class="fas fa-minus-square"></i></a><input type="hidden" name="memberNickName[]" value="<%=request.getAttribute("keyword")%>"><ul id='fruits'></ul></span>
							<%-- <span><a href="invite"><i class="fas fa-minus-square"></i></a><input type="hidden" name="memberNickName[]" value="<%=request.getAttribute("keyword")%>"><%=request.getAttribute("keyword")%></span> --%>
							
							<%-- </c:forEach> --%>
						</div>
					</li>
				</ul>
			</div>
			<div class="btn-area">
				<a href = "group"><button class="cancel-btn">취소</button></a>
				<a href = "createGroup"><button type="submit" class="create-btn">만들기</button></a>
			</div>
			</form>
		</div><!-- container -->
	</div><!-- container-wrap -->
</section>    

<script type="text/javascript">
function addList()  {
	  
	  // 1. 추가할 값을 input창에서 읽어온다
	  const addValue 
	    = document.getElementById('addValue').value;
	  
	  // 2. 추가할 li element 생성
	  // 2-1. 추가할 li element 생성
	  const li = document.createElement("li");
	  
	  // 2-2. li에 id 속성 추가 
	  li.setAttribute('id',addValue);
	  
	  // 2-3. li에 text node 추가 
	  const textNode = document.createTextNode(addValue);
	  li.appendChild(textNode);
	  
	  // 3. 생성된 li를 ul에 추가
	  document
	    .getElementById('fruits')
	    .appendChild(li);
	}
</script> 

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

</body>
</html>