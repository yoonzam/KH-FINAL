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

<style type="text/css">

/* css 상태 보고 추후에 이동 예정 */
 #addValue{
	width:200px;
	}
#addButton{
background-color: var(--red-color);
    
    width: 60px;
    cursor: pointer;
    border-radius: 5px;
    padding: 14px 0px;
    transition-duration: 0.5s;
    color: #fff;
    border: none;	
}
</style>
	
</head>

<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<section>
	<div class="container-wrap">
		<div class="container">
			<h3><i class="fas fa-users-cog"></i> 새로운 그룹 만들기</h3>
			
			<form id="frmUpload" action="/myeats/createGroup" method="post" enctype="multipart/form-data">
			<input type="hidden" name="groupcreatedate" value="<%= sf.format(nowTime) %>">
			<div class="group-form">
				<ul>
					<li>
						<span>프로필</span>
						<input id="photo1" type="file"name="photos" accept=".gif, .jpg, .jpeg, .png">
						<div class="preview-photo photo1">
						 
						</div>
					</li>
					<li>
						<span>그룹이름</span>
						<input type="text" placeholder="그룹 이름을 입력하세요."name="groupName"  maxlength="8" required >
						
					</li>
					<li>
						<span>초대하기</span>
						<div class="friend-list"> 
							<input type="text"  id='addValue' placeholder="초대할 친구의 닉네임을 입력하세요."name="memberNickName[]"
							 value="<%=request.getAttribute("keyword")%>">
							
							
							<input id="addButton" type='button' value='추가' onclick='addList()' />
							<a href="invite"><button type="button">초대</button></a>
							
							
							<span><i class="fas fa-minus-square" onclick='removeItem()'></i><input type="hidden" name="memberNickName[]" value="<%=request.getAttribute("keyword")%>"><ul id='nickNames'></ul></span>
							
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
	    .getElementById('nickNames')
	    .appendChild(li);
	}
	
	
function removeItem()  {
	  
	  // 1. <ul> element 선택
	  const ul = document
	    .getElementById('nickNames');
	  
	  // 2. <li> 목록 선택
	  const items = ul.getElementsByTagName('li');
	  
	  // 3. <li> 목록 중 첫번째 item 삭제
	  if(items.length > 0)  {
	    items[0].remove();
	  }
	  
	}
	

</script> 

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

</body>
</html>