<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/myeats.css" />
<style type="text/css">

/* css 상태 보고 추후에 이동 예정 */
.deletebtn {
	border:none;
	background-color: #ccc;
	color: #fff;
	font-size: 1em;
	}
</style>

</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<section>
	<div class="container-wrap">
		<div class="container">
			<ul class="myeats-tab">
				<li class="selected">그룹관리</li>
				<li><a href="post">작성글관리</a></li>
				<li><a href="detail">맛찜리스트</a></li>
				<li>회원정보 수정</li>
			</ul>
			
			
			
			<c:forEach items="${list}" var="grouplist" varStatus="status"  begin="0">
				<c:if test="${status.first}"><ul class="group-wrap"></c:if>
				<c:choose>
				<c:when test="${status.count % 2 == 0}">
				<li>
					<div class="group">
						<div class="group-img"><img src="/resources/img/upload/01.jpg"></div>
						<p class="group-info">
							<strong>${grouplist.groupName}</strong><br>
							<i class="fas fa-user"></i> 5&nbsp;&nbsp;<i class="fas fa-feather"></i>
							<fmt:formatDate pattern="yyyy/MM/dd" value="${grouplist.groupcreatedate}"/>
						</p>
					</div>
					<div class="controller">
						<a href="groupDetail?groupIdx=${grouplist.groupIdx}" class="group-menu">그룹관리</a>
						<a href="groupDetail?groupIdx=${grouplist.groupIdx}">수정</a>

						<!-- 폼태그 스타일 유지 위해 div로 display: none 줌 -->
						<div style="display: none;">
						<form role="form" method="post">	<!-- delete/post로 넘김 -->
							<input type="hidden" name="id" value="${grouplist.id}" />
						</form>
						</div>  
						

						<a><button type="submit" class="deletebtn">삭제</button></a>
					</div>
				</li>
				</c:when>
				
			
			<c:otherwise>
				<li>
					<div class="group">
						<div class="group-img"><img src="/resources/img/upload/02.jpg"></div>
						<p class="group-info">
							<strong>${grouplist.groupName}</strong><br>
							<i class="fas fa-user"></i> 5&nbsp;&nbsp;<i class="fas fa-feather"></i>
							<fmt:formatDate pattern="yyyy/MM/dd" value="${grouplist.groupcreatedate}"/>
						</p>
					</div>
					<div class="controller">
						<a href="groupDetail?groupIdx=${grouplist.groupIdx}" class="group-menu">그룹관리</a>
						<a href="groupDetail?groupIdx=${grouplist.groupIdx}">수정</a>
						
						<!-- 폼태그 스타일 유지 위해 div로 display: none 줌 -->
						<div style="display: none;">
						<form role="form" method="post">	
							<input type="hidden" name="id" value="${grouplist.id}" />
						</form>
						</div>  
						
						
						<a><button type="submit" class="deletebtn">삭제</button></a>
						
					</div>
				</li> 
			
				</c:otherwise>
				</c:choose>
				<c:if test="${status.last}"></ul></c:if>
				</c:forEach>
				
			
			<div class="btn-area">
				<a href ="createGroup"><button class="create-btn">새로운 그룹 만들기</button></a>
			</div>
		</div>
	</div>
</section>  

   

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>
 //메인화면 자바스크립트
/* var myNodelist = document.getElementsByTagName(".main LI");
var i; */

// 삭제 버튼을 리스트에 붙이는 스크립트
/* for (i = 0; i < myNodelist.length; i++) {
 var span = document.createElement("SPAN");
    /*\u00D7 x표*/
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  myNodelist[i].appendChild(span);
} */

// 삭제 버튼 누르면 삭제되는 스크립트
/* var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
  close[i].onclick = function() {
    var div = this.parentElement;
    div.style.display = "none";
  }
} */

// 추가되면 체크표시 아이콘(미완)
/* var list = document.querySelector('ul');
list.addEventListener('click', function(ev) {
  if (ev.target.tagName === 'LI') {
    ev.target.classList.toggle('checked');
  }
}, false); */

// 추가시 x버튼 등 생성(미완)
/* function newElement() {
  var li = document.createElement("li");
  var inputValue = document.getElementById("myInput").value;
  var t = document.createTextNode(inputValue);
  li.appendChild(t);
  if (inputValue === '') {
    alert("You must write something!");
  } else {
    document.getElementById("myUL").appendChild(li);
  }
  document.getElementById("myInput").value = "";

  var span = document.createElement("SPAN");
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  li.appendChild(span);  */
  
  //그룹 생성 정상 처리시
  var result = '${result}';
	
	if(result == 'success'){
		alert("정상 처리 되었습니다!!!");
	}

}

$(document).ready(function(){
	var frmObj = $("form[role='form']");
	console.log("group.jsp지정된 폼태그..");
	
	
	 $(".deletebtn").on("click", function(){
		
		 //test
		 var test =${grouplist.id}
		 console.log(test);
		
		 
		frmObj.attr("action", "/myeats/delete");
		frmObj.submit();
	}); 
	
});

</script>
</body>
</html>