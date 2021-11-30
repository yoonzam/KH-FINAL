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
    width: calc(100% - 160px);
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
							<%-- <input type="text"  id='addValue' placeholder="초대할 친구의 닉네임을 입력하세요."
							 value="<%=request.getAttribute("keyword")%>"> --%>
							<select id="invited-select">
							</select>
							
							
							<input id="addButton" type='button' value='추가' onclick='addList()' />
							<button type="button" id="inviteButton">초대</button>
							
				
							
							<span><i class="fas fa-minus-square" onclick='removeItem()'></i>
							<%-- <input type="hidden" name="memberNickName[]" value="<%=request.getAttribute("keyword")%>"> --%>
							<ul id='nickNames'></ul>
							</span>
							
						</div>
					</li>
				</ul>
			</div>
			</form>
			<div class="btn-area">
				<a href = "group"><button class="cancel-btn">취소</button></a>
				<a><button type="submit" class="create-btn">만들기</button></a>
			</div>
			
			
			
			
				
				
		</div><!-- container -->
	</div><!-- container-wrap -->
</section>    
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">

/* $("#inviteButton").click(function(){  
    
    var url="/info/memberInfo";  
  
    $.ajax({      
        type:"GET",  
        url:url,   
        dataType: 'json',
        success:function(data){ 
      	let html = '';
		for (var i = 0; i < data.length; i++)
			html += '<li><a>'+data[i].nickname+'</a></li>';
			$('#modifyDiv').html(html);
        },   
        error:function(e){  
            alert(e.responseText);  
        }  
    });  
    
});   */

$("#inviteButton").click(function(){  
    
    var url="/info/memberInfo";  
  
    $.ajax({      
        type:"GET",  
        url:url,   
        dataType: 'json',
        success:function(data){ 
      	let html = '';
      	for (var i = 0; i < data.length; i++)
			html += '<option value='+data[i].nickname+'>'+data[i].nickname +'</option>';
			
			$('#invited-select').html(html);
        },   
        error:function(e){  
            alert(e.responseText);  
        }  
    });  
});  
function addList()  {
	
		const addValue = $("#invited-select").val();
	 	const li = document.createElement("input");
	  
	 	li.setAttribute('class', "invited-input");
		li.setAttribute('id', addValue);
		li.setAttribute("name", "memberNickName[]");
		li.setAttribute("value", addValue);
	  
		const textNode = document.createTextNode(addValue);
		li.appendChild(textNode);
	  
	  document
	    .getElementById('nickNames')
	    .appendChild(li);
	}
	
	
function removeItem()  {
	  
	  const ul = document
	    .getElementById('nickNames');
	  
	  const items = ul.getElementsByTagName('li');
	  
	  if(items.length > 0)  {
	    items[0].remove();
	  }
	  
	}
	
$(document).ready(function(){
		var frmObj = $("form[role='form']");
		console.log("createGroup.jsp지정된 폼태그..");
		
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