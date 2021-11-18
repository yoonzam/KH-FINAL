<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
			<ul class="myeats-tab">
				<li class="selected">그룹관리</li>
				<li>작성글관리</li>
				<li>맛찜리스트</li>
			</ul>
			
			<c:forEach items="${list}" var="bvo">
			<ul class="group-wrap">
				<li>
					<div class="group">
						<div class="group-img"><img src="/resources/img/upload/01.jpg"></div>
						<p class="group-info">
							<strong>${bvo.groupName}</strong><br>
							<i class="fas fa-user"></i> 5&nbsp;&nbsp;<i class="fas fa-feather"></i> 2021-11-11
						</p>
					</div>
					<div class="controller">
						<a class="group-menu">그룹관리</a>
						<a>수정</a>
						<a>삭제</a>
					</div>
				</li>
				<!-- <li>
					<div class="group">
						<div class="group-img"><img src="/resources/img/upload/02.jpg"></div>
						<p class="group-info">
							<strong>맛집소녀단</strong><br>
							<i class="fas fa-user"></i> 5&nbsp;&nbsp;<i class="fas fa-feather"></i> 2021-11-11
						</p>
					</div>
					<div class="controller">
						<a class="group-menu">그룹관리</a>
						<a>수정</a>
						<a>삭제</a>
					</div>
				</li>
				<li>
					<div class="group">
						<div class="group-img"><img src="/resources/img/upload/03.jpg"></div>
						<p class="group-info">
							<strong>맛집소녀단</strong><br>
							<i class="fas fa-user"></i> 5&nbsp;&nbsp;<i class="fas fa-feather"></i> 2021-11-11
						</p>
					</div>
					<div class="controller">
						<a class="group-menu">그룹관리</a>
						<a>수정</a>
						<a>삭제</a>
					</div>
				</li>
				<li>
					<div class="group">
						<div class="group-img"><img src="/resources/img/upload/01.jpg"></div>
						<p class="group-info">
							<strong>맛집소녀단</strong><br>
							<i class="fas fa-user"></i> 5&nbsp;&nbsp;<i class="fas fa-feather"></i> 2021-11-11
						</p>
					</div>
					<div class="controller">
						<a class="group-menu">그룹관리</a>
						<a>수정</a>
						<a>삭제</a>
					</div>
				</li> -->
			</ul>
			</c:forEach> 
			<div class="btn-area">
				<button class="create-btn">새로운 그룹 만들기</button>
			</div>
		</div>
	</div>
</section>     

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>
//메인화면 자바스크립트
var myNodelist = document.getElementsByTagName(".main LI");
var i;

// 삭제 버튼을 리스트에 붙이는 스크립트
for (i = 0; i < myNodelist.length; i++) {
 var span = document.createElement("SPAN");
    /*\u00D7 x표*/
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  myNodelist[i].appendChild(span);
}

// 삭제 버튼 누르면 삭제되는 스크립트
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
  close[i].onclick = function() {
    var div = this.parentElement;
    div.style.display = "none";
  }
}

// 추가되면 체크표시 아이콘(미완)
var list = document.querySelector('ul');
list.addEventListener('click', function(ev) {
  if (ev.target.tagName === 'LI') {
    ev.target.classList.toggle('checked');
  }
}, false);

// 추가시 x버튼 등 생성(미완)
function newElement() {
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
  li.appendChild(span);

}
</script>
</body>
</html>