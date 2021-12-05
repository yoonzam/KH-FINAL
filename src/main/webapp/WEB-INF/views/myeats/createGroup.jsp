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
.group-form select {
    width: calc(100% - 92px);
    padding: 13px;
    border-radius: 5px;
    border: 1px solid #aaa;
}
.invited-input{
   padding: 3px !important;
   border-style: none !important;
}
</style>
</head>

<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<section>
	<div class="container-wrap">
		<div class="container">
			<h3><i class="fas fa-users-cog"></i> 새로운 그룹 만들기</h3>
			<form role="form" id="frmUpload" action="/myeats/createGroup" method="post" enctype="multipart/form-data">
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
								<select id="invited-select" name="participant" onchange="addList()"></select>
								
								<!-- <input id="addButton" type='button' value='추가'  /> --><!-- onclick='addList()' -->
								<button type="button" id="inviteButton">초대</button>
								
								<span><i class="fas fa-minus-square" onclick='removeItem()'></i>
								<ul id='nickNames'></ul>
								</span>
							</div>
						</li>
					</ul>
				</div>
				<div class="btn-area">
					<a><button type="submit" class="create-btn">만들기</button></a>
					<button type="button" class="cancel-btn" onclick="location.href='group'">취소</button>
				</div>
			</form>
		</div><!-- container -->
	</div><!-- container-wrap -->
</section> 
   
<script type="text/javascript">
$("#inviteButton").click(function(){  
    var url="/info/memberInfo";  
    $.ajax({      
        type:"GET",  
        url:url,   
        dataType: 'json',
        success:function(data){ 
        	
      		let html = '';
	      	for (var i = 0; i < data.length; i++){
				html += '<option class="option" value='+data[i].memberId+'>'+data[i].member.nickname +'</option>';
				$('#invited-select').html(html);
	      	}
        },   
        error:function(e){  
        }  
    });  
});  

function addList()  {
		
	let select = document.querySelector('#invited-select');
	const text = select.options[select.selectedIndex].text;
	const addValue = select.options[select.selectedIndex].value;
 	const input = document.createElement("input");
 	const li = document.createElement("li");
 	
 	li.setAttribute('class', "invited-input");
  
 	input.setAttribute('type','hidden');
 	input.setAttribute('class', "invited-input");
 	input.setAttribute('id', text);
 	input.setAttribute("name", "participants[]");
 	input.setAttribute("value", addValue);
	
	const textNode = document.createTextNode(text);
	li.appendChild(textNode);
	  
	document.getElementById('nickNames').appendChild(li);
	document.getElementById('nickNames').appendChild(input);
}
	
	
function removeItem()  {
	  
	  const ul = document.getElementById('nickNames');
	  
	  const items = ul.getElementsByTagName('li');
	  const inputValues = ul.getElementsByTagName('input');
	  
	  if(items.length > 0)  {
	    items[0].remove();
	    inputValues[0].remove();
	  }
	  
	}
	
 $(document).ready(function(){
		var frmObj = $("form[role='form']");
		
		
		$(".create-btn").on("click", function(){
			frmObj.attr("action", "/myeats/createGroup");
			formObj.attr("method", "post");
			frmObj.submit();
			});
		
		 
	});
</script> 
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>