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
				<input type="hidden"  name="id" value="${groups.id}" />
				<!-- 테스트용 -->
				<input type="hidden"  name="delNickName" value="삭제테스트" /> 
				
				<div class="group-info">
					<div class="group-profile">
						<div class="group-img">
							<img src="${!empty groups.thumUrl ? groups.thumUrl : '/resources/img/common/upload-logo.png'}" name="thumUrl"
							value="${groups.thumUrl}">
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
				</c:forEach>
				<div class="group-member">
					<h4>함께하는 잇친 리스트</h4>
					
					
					
				<c:forEach items="${groups}" var="groups" varStatus="status"  begin="0">
					 <c:if test="${status.first}"><ul id='nickNames'></c:if>
					 
					 	<li>
						<span>초대하기</span>
						<div class="friend-list"> 
							<select id="invited-select" name="participant" onchange="addList()"></select>
							<input id="inviteButton" type='button' value='초대'/>
							<ul id='nickNames'>
							</ul>
						</div>
					</li>
					
					
						<c:choose>
							<c:when test="true">
							<c:if test="${groups.participants[0]!= null}">
		          				<li id="nickOne"><i class="fas fa-user"></i> ${groups.participants[0]}<a id="fasOne" onclick='deleteDiv()'><i class="fas fa-times" ></i>삭제</a></li>
		          			</c:if>
		          			<c:if test="${groups.participants[1]!= null}">
		          				<li id="nickTwo"><i class="fas fa-user"></i> ${groups.participants[1]}<a id="fasTwo" onclick='deleteDiv()'><i class="fas fa-times"></i>삭제</a></li>
		          			</c:if>
		          			<c:if test="${groups.participants[2]!= null}">
		          				<li id="nickThree"><i class="fas fa-user"></i> ${groups.participants[2]} <a id="fasThree" onclick='deleteDiv()'><i class="fas fa-times"></i>삭제</a></li>
		          			</c:if>
		          			<c:if test="${groups.participants[2]!= null}">
		          				<li id="nickFour"><i class="fas fa-user"></i> ${groups.participants[2]} <a id="fasFour" onclick='deleteDiv()'><i class="fas fa-times"></i>삭제</a></li>
		          			</c:if>
		          			<c:if test="${groups.participants[4]!= null}">
		          				<li id="nickFive"><i class="fas fa-user"></i> ${groups.participants[4]}<a id="fasFive" onclick='deleteDiv()'><i class="fas fa-times"></i>삭제</a></li>
		          			</c:if>
		          			<c:if test="${groups.participants[5]!= null}">
		          				<li id="nickSix"><i class="fas fa-user"></i> ${groups.participants[5]}<a id="fasSix" onclick='deleteDiv()'><i class="fas fa-times"></i>삭제</a></li>
		          			</c:if>
		          			</c:when>
							</c:choose> 
					<c:if test="${status.last}"></ul></c:if> 
				</c:forEach>	
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
	
 var addValue = $("#invited-select").val();
 var li = document.createElement("input");
 	li.setAttribute('class', "invited-input");
	li.setAttribute('id', addValue);
	li.setAttribute("name", "newNickNameOne");
	li.setAttribute("value", addValue);
  
 var textNode = document.createTextNode(addValue);
	li.appendChild(textNode);
  
 var icon = document.createElement("i");
	 icon.setAttribute('class', "fas fa-times");
	
  /* document.getElementById("nickNames").appendChild(li); */
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
	
	
	 $(".delete-btn").on("click", function(){
		frmObj.attr("action", "/myeats/delete");
		frmObj.submit();
	}); 
	 
	 $("#fasOne").click(function(){
           $("#nickOne").remove();  
       }); 
	 $("#fasTwo").click(function(){
            $("#nickTwo").remove();  
        }); 
	 $("#fasThree").click(function(){
            $("#nickThree").remove();  
        }); 
	 $("#fasFour").click(function(){
         $("#nickFour").remove();  
     });
	 $("#fasFive").click(function(){
         $("#nickFive").remove();  
    	 }); 
	 $("#fasSix").click(function(){
         $("#nickSix").remove();  
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