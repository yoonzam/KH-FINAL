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
    width: 88.4%;
    padding: 13px;
    border-radius: 5px;
    border: 1px solid #aaa;
}
.invited-input {
    color: #333;
    padding: 13px;
    border: 1px solid #ddd;
    margin-top: 10px;
    margin-right: -17px;
    background-color: #ffead6;
    margin-left: 20px;
}
#addButton {
    background-color: var(--red-color);
    width: 25px;
    cursor: pointer;
    border-radius: 5px;
    padding: 2px 0px;
    color: #fff;
    border: none;
}
#inviteButton {
    background-color: var(--main-color);
    width: 25px;
    cursor: pointer;
    border-radius: 5px;
    padding: 2px 0px;
    color: #fff;
    border: none;
}
#invited-select {
	margin-top:8px;
    width: 30%;
    padding: 2px;
    border-radius: 5px;
    border: 1px solid #aaa;
}

li, input {
    border-style: none;
}
.btn-edit-profile {
    background-color: #ccc;
    border-radius: 5px;
    padding: 14px;
    transition-duration: 0.5s;
    color: #fff;
    border: none;
    font-size: 16px;
}

</style>

</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<section>
	<div class="container-wrap">
		<div class="container">
			<div class="group-view">
			<form role = "form" action="/myeats/groupDetailModify" method="post" enctype="multipart/form-data">	
			<c:forEach items="${groups}" var="groups">
				<input type="hidden"  name="id" value="${groups.id}" />
				<div class="group-info">
					<div class="group-profile">
						<div class="group-img">
							<img id="target_img" src="${!empty groups.thumUrl ? groups.thumUrl : '/resources/img/common/upload-logo.png'}" name="thumUrl">
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
							<label for="profile" class="btn-edit-profile">이미지 수정</label>
							 <input type="file" name = "photos" id="profile" >
						</div>
					</div>
				</div>
				</c:forEach>
				<div class="group-member">
					<h4>함께하는 잇친 리스트</h4>
					<ul> 
					<li>
						<span>초대하기</span>
						<div class="friend-list"> 
							<select id="invited-select" name="participant" onchange="addList()"></select>
							<input id="inviteButton" type='button' value='초대'/>
							<ul id='nickNames'>
							</ul>
						</div>
					</li>
					</ul>
					 <ul id='nickNames'>
							<c:forEach items="${nickNames}" var="nickNames" begin="0" end="0" >
		          				<li id="nickOne"><i class="fas fa-user"></i>&nbsp;
		          				<input id="delOne" value="${nickNames}">
		          				<a id="fasOne" ><i class="fas fa-times" ></i>삭제</a></li>
		          			</c:forEach>
		          			<c:forEach items="${nickNames}" var="nickNames" begin="1" end="1" >
		          				<li id="nickTwo"><i class="fas fa-user"></i>&nbsp;
		          				<input id="delTwo" value="${nickNames}">
		          				<a id="fasTwo" ><i class="fas fa-times"></i>삭제</a></li>
		          			</c:forEach>
		          			<c:forEach items="${nickNames}" var="nickNames" begin="2" end="2" >
		          				<li id="nickThree"><i class="fas fa-user"></i>&nbsp;
		          				<input id="delThree" value="${nickNames}">
		          				<a id="fasThree"><i class="fas fa-times"></i>삭제</a></li>
		          			</c:forEach>
		          			<c:forEach items="${nickNames}" var="nickNames" begin="3" end="3" >
		          				<li id="nickFour"><i class="fas fa-user"></i>&nbsp;
		          				<input id="delFour" value="${nickNames}">
		          				<a id="fasFour"><i class="fas fa-times"></i>삭제</a></li>
		          			</c:forEach>
		          			<c:forEach items="${nickNames}" var="nickNames" begin="4" end="4" >
		          				<li id="nickFive"><i class="fas fa-user"></i>&nbsp;
		          				<input id="delFive" value="${nickNames}">
		          				<a id="fasFive"><i class="fas fa-times"></i>삭제</a></li>
		          			</c:forEach>
		          			<c:forEach items="${nickNames}" var="nickNames" begin="5" end="5" >
		          				<li id="nickSix"><i class="fas fa-user"></i>&nbsp;
		          				<input id="delSix" value="${nickNames}">
		          				<a id="fasSix"><i class="fas fa-times"></i>삭제</a></li>
		          			</c:forEach>
						</ul>
				</div>
				</form>
				<a href = "group"><button class="btn-list">그룹 목록으로 돌아가기</button></a>
			</div>
		</div>
	</div>
</section>     

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>

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
	const addedValue = select.options[select.selectedIndex].value;
	
	var addValue = $("#invited-select").val();
	 var li = document.createElement("input");
	 	li.setAttribute('class', "invited-input");
		li.setAttribute('id', addValue);
		li.setAttribute("name", "newNickNameOne");
		li.setAttribute("value", text);//value는 텍스트
	  
	 var textNode = document.createTextNode(addValue);
		li.appendChild(textNode);
	  
	 var icon = document.createElement("i");
		 icon.setAttribute('class', "fas fa-times");
		
	  document.getElementById("nickNames").append(li,icon);
	}
	


function deleteList()  {
	var div = document.getElementId('nickOne');
	 div.remove();
	var li = document.createElement("input");
	 	li.setAttribute('class', "delNickName");
	  
	  document.getElementByClassName("fas fa-times").append(li);
	}



$(document).ready(function(){
	var frmObj = $("form[role='form']");
	
	//그룹삭제
	 $(".delete-btn").on("click", function(){
		frmObj.attr("action", "/myeats/delete");
		frmObj.submit();
	}); 
	 //개별삭제
	 $("#fasOne").click(function(){
		 var delOne = document.getElementById("delOne")
			delOne.setAttribute('name',"delNickNameOne");
           $("#nickOne").hide();  
       }); 
	 $("#fasTwo").click(function(){
		 var delTwo = document.getElementById("delTwo")
			delTwo.setAttribute('name',"delNickNameTwo");
            $("#nickTwo").hide();  
        }); 
	 $("#fasThree").click(function(){
		 var delThree = document.getElementById("delThree")
			delThree.setAttribute('name',"delNickNameThree");
            $("#nickThree").hide();  
        }); 
	 $("#fasFour").click(function(){
		 var delFour = document.getElementById("delFour")
			delFour.setAttribute('name',"delNickNameFour");
         $("#nickFour").hide();  
     });
	 $("#fasFive").click(function(){
		 var delFive = document.getElementById("delFive")
			delFive.setAttribute('name',"delNickNameFive");
         $("#nickFive").hide();  
    	 }); 
	 $("#fasSix").click(function(){
		 var delSix = document.getElementById("delSix")
			delSix.setAttribute('name',"delNickNameSix");
         $("#nickSix").hide();  
    	 }); 
        
        

        
	 
	//수정처리 페이지 이동
		$(".complete-btn").on("click", function(){
		frmObj.attr("action", "/myeats/groupDetailModify");
		formObj.attr("method", "post");
		frmObj.submit();
		});
	
	//이미지 수정
		document.querySelector('#profile').addEventListener('change', (e) => {
		
		//e.preventDefault();	 
		let files = document.getElementById('profile').files;
		
		for (let file of files) {
			if(validFileType(file)){
				document.getElementById('target_img').src = URL.createObjectURL(file);
			}
		}
		
		});
		   
		let fileTypes = [
		   "image/gif",
		   "image/jpeg",
		   "image/pjpeg",
		   "image/png",
		   "image/tiff",
		   "image/webp",
		   "image/x-icon"
		 ];
		 
		function validFileType(file) {
		   return fileTypes.includes(file.type);
		 }
		
		
		$('#profile').css('display','none');
	
	
});
</script>
</body>
</html>