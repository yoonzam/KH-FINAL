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


.show-input {
    color: #333;
    padding: 13px;
    border: 1px solid #ddd;
    margin-top: 10px;
    margin-right: -17px;
    background-color: #ffead6;
    margin-left: 20px;
    
}
.invited-input {
   display: none;
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
    cursor: pointer;
    border-radius: 5px;
    padding: 10px;
    color: #fff;
    border: none;
}
#invited-select {
	display:none;
    width: 40%;
    padding: 10px;
    margin-left:10px;
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
							<img id="target_img" src="${!empty groups.thumUrl ? groups.thumUrl : '/resources/img/common/EatsMap_logo.png'}" name="thumUrl">
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
							<div class="friend-list" style="display:flex; align-items:center;"> 
								<input id="inviteButton" type='button' value='잇친목록 조회하기'/>
								<select id="invited-select" name="participant" onchange="addList()"></select>
							</div>
							<ul id='nickNames'></ul>
						</li>
					</ul>
					<ul id='nickNames'>
					<c:forEach items="${groups}" var="groups">
						<c:forEach items="${nickNames}" var="nickNames" begin="0" end="0" >
	          				<li id="nickOne"><i class="fas fa-user"></i>&nbsp;
	          				<input value="${nickNames}">
	          				<input type="hidden" id="delOne" value="${groups.participants[0]}">
	          				<a id="fasOne" ><i class="fas fa-times" ></i>삭제</a></li>
		          		</c:forEach>
	          			<c:forEach items="${nickNames}" var="nickNames" begin="1" end="1" >
	          				<li id="nickTwo"><i class="fas fa-user"></i>&nbsp;
	          				<input value="${nickNames}">
	          				<input type="hidden" id="delTwo"value="${groups.participants[1]}">
	          				<a id="fasTwo" ><i class="fas fa-times"></i>삭제</a></li>
	          			</c:forEach>
	          			<c:forEach items="${nickNames}" var="nickNames" begin="2" end="2" >
	          				<li id="nickThree"><i class="fas fa-user"></i>&nbsp;
	          				<input value="${nickNames}">
	          				<input type="hidden" id="delThree"value="${groups.participants[2]}">
	          				<a id="fasThree"><i class="fas fa-times"></i>삭제</a></li>
	          			</c:forEach>
	          			<c:forEach items="${nickNames}" var="nickNames" begin="3" end="3" >
	          				<li id="nickFour"><i class="fas fa-user"></i>&nbsp;
	          				<input value="${nickNames}">
	          				<input type="hidden" id="delFour"value="${groups.participants[3]}">
	          				<a id="fasFour"><i class="fas fa-times"></i>삭제</a></li>
	          			</c:forEach>
	          			<c:forEach items="${nickNames}" var="nickNames" begin="4" end="4" >
	          				<li id="nickFive"><i class="fas fa-user"></i>&nbsp;
	          				<input value="${nickNames}">
	          				<input type="hidden" id="delFive"value="${groups.participants[4]}">
	          				<a id="fasFive"><i class="fas fa-times"></i>삭제</a></li>
	          			</c:forEach>
	          			<c:forEach items="${nickNames}" var="nickNames" begin="5" end="5" >
	          				<li id="nickSix"><i class="fas fa-user"></i>&nbsp;
	          				<input value="${nickNames}">
	          				<input type="hidden" id="delSix"value="${groups.participants[5]}">
	          				<a id="fasSix"><i class="fas fa-times"></i>삭제</a></li>
	          			</c:forEach>
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
//초대하기 클릭시
$("#inviteButton").click(function(){  
    var url="/info/memberInfo";  
    $.ajax({      
        type:"GET",  
        url:url,   
        dataType: 'json',
        success:function(data){ 
        	let html = '';
        	
        	if (data.length == 0) {
        		html += '<option class="option" value="" disabled="disabled">초대할 잇친이 없습니다.</option>';
			}
	      	for (var i = 0; i < data.length; i++){
				html += '<option class="option" value='+data[i].memberId+'>'+data[i].member.nickname +'</option>';
	      	}
	      	$('#invited-select').html(html);
	      	$('#invited-select').show();
        },   
        error:function(e){  
        }  
    });  
});  

//초대버튼 클릭시 리스트나열 함수
function addList()  {
	 let select = document.querySelector('#invited-select');
	 const text = select.options[select.selectedIndex].text;
	const addedValue = select.options[select.selectedIndex].value;
	
	//id 값(실제 name값 가짐) css에서 안보이게 처리
	var addValue = $("#invited-select").val();
	//첫번째 id처리막기 위한 두번째 변수
	var addValueTwo = $("#invited-select").val();
	var addValueThree = $("#invited-select").val();
	 var li = document.createElement("input");
	 	li.setAttribute('class', "invited-input");
		li.setAttribute('id', addValue);
		/* li.setAttribute("name", "newNickNameOne"); */
	  	li.setAttribute("value", addValue);
	//name 값 보여줌  
	var show = document.createElement("input");
		show.setAttribute('class', "show-input");
		show.setAttribute('id', text);
		show.setAttribute("value", text);
		
		
	 var textNode = document.createTextNode(addValue);
		li.appendChild(textNode);
		show.appendChild(textNode);
	  
	 var icon = document.createElement("i");
		 icon.setAttribute('class', "fas fa-times");
		 icon.setAttribute('id', addValueTwo);
		
	  document.getElementById("nickNames").append(show,icon,li);
	  
	  //초대할 그룹에 name부여
	  var $ids = null;
		$ids = $(".invited-input");
		var $idone = $ids.eq(0);
		var $idtwo = $ids.eq(1);
		var $idtwo = $ids.eq(2);
		var $idtwo = $ids.eq(3);
		var $idtwo = $ids.eq(4);
		var $idtwo = $ids.eq(5);
		$ids.eq(0).attr("name","newNickNameOne");
		$ids.eq(1).attr("name","newNickNameTwo");
		$ids.eq(2).attr("name","newNickNameThree");
		$ids.eq(3).attr("name","newNickNameFour");
		$ids.eq(4).attr("name","newNickNameFive");
		$ids.eq(5).attr("name","newNickNameSix");
		
		//
		icon.onclick=function(){
			document.getElementById(addValueThree).removeAttribute("name");
			document.getElementById(text).style.display  = 'none';
			document.getElementById(addValueThree).remove("input");
			document.getElementById(addValueTwo).remove("i");
			};
	}

//문서시작시
$(document).ready(function(){
	var frmObj = $("form[role='form']");
	
	//그룹삭제
	 $(".delete-btn").on("click", function(){
		frmObj.attr("action", "/myeats/delete");
		frmObj.submit();
	}); 
	 //그룹원삭제
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
	//파일업로드 input 숨김처리
	$('#profile').css('display','none');
	
	
});
</script>
</body>
</html>