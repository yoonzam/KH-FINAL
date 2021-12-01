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
    margin-right: 10px;
    background-color: #ffead6;
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
    width: 50%;
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
				<!-- <input type="hidden"  name="delNickName" value="추가테스트" /> -->
				
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
							<select id="invited-select">
							</select>
							<input id="addButton" type='button' value='추가' onclick='addList()' />
							<input id="inviteButton" type='button' value='초대'/>
							<ul id='nickNames'>
							</ul>
						</div>
					</li>
					
					
						<c:choose>
							<c:when test="true">
							<c:if test="${groups.memberNickName[0]!= null}">
		          				<li id="nickOne"><i class="fas fa-user"></i> ${groups.memberNickName[0]}<a id="fasOne"><i class="fas fa-times" ></i>삭제</a></li>
		          			</c:if>
		          			<c:if test="${groups.memberNickName[1]!= null}">
		          				<li><i class="fas fa-user"></i> ${groups.memberNickName[1]}<a><i class="fas fa-times"></i>삭제</a></li>
		          			</c:if>
		          			<c:if test="${groups.memberNickName[2]!= null}">
		          				<li><i class="fas fa-user"></i> ${groups.memberNickName[2]} <a><i class="fas fa-times"></i>삭제</a></li>
		          			</c:if>
		          			<c:if test="${groups.memberNickName[3]!= null}">
		          				<li><i class="fas fa-user"></i> ${groups.memberNickName[3]}<a><i class="fas fa-times"></i>삭제</a></li>
		          			</c:if>
		          			<c:if test="${groups.memberNickName[4]!= null}">
		          				<li><i class="fas fa-user"></i> ${groups.memberNickName[4]}<a><i class="fas fa-times"></i>삭제</a></li>
		          			</c:if>
		          			<c:if test="${groups.memberNickName[5]!= null}">
		          				<li><i class="fas fa-user"></i> ${groups.memberNickName[5]}<a><i class="fas fa-times"></i>삭제</a></li>
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
	
 var addValue = $("#invited-select").val();
 var li = document.createElement("input");
 	li.setAttribute('class', "invited-input");
	li.setAttribute('id', addValue);
	li.setAttribute("name", "newNickNameOne");
	li.setAttribute("value", addValue);
  
	var textNode = document.createTextNode(addValue);
	li.appendChild(textNode);
  
  document
    .getElementById("nickNames")
    .appendChild(li);
}

app.delete("/groupDetailModify/:id", (request, response) => {
    const id = Number(request.params.id)
    items.splice(id, 1)
    response.send("success")
})










$(document).ready(function(){
	var frmObj = $("form[role='form']");
	
	
	 $(".delete-btn").on("click", function(){
		frmObj.attr("action", "/myeats/delete");
		frmObj.submit();
	}); 
	 
		 $("#fasOne").click(function(){
            $("#nickOne").remove();  
        }); 
        
        $("#fasOne").click(function () {
            $.ajax({
                url: "/groupDetailModify/0",
                method: "DELETE",
                dataType: "text",
                success: function (data) {
                    $('#nickOne').val(data)
                    console.log(data)
                }
            })
        })
        

        
	 
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