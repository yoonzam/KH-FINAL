<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
 <!-- 부트스트랩-->
 <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- 부트스트랩의 플러그인-->
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
 <style> 
       /* 사이드 네비 */ 
        .sidenav {
		  width: 130px;
		  height:300px;
		  position: fixed;
		  z-index: 1;
		  top: 20px;
		  left: 10px;
		  padding: 8px 0;
		  
		  /* header영역 확보 */
          margin-top: 100px;
		}
		
		.sidenav a {
		  padding: 6px 8px 6px 16px;
		  text-decoration: none;
		  font-size: 25px;
		  color: #2196F3;
		  display: block;
		}
		
		.sidenav a:hover {
		  color: #064579;
		}
		
		.sidenav button {
			margin-left: 18px;
		}
		
		
		
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	   <div class="sidenav">
	   		<!-- 드랍다운 버튼 -->
			<div>
	               <span><a class="glyphicon glyphicon-menu-hamburger" data-toggle="collapse" href="#collapseExample"></a></span>
	        </div>  
	        <div class="btn-group btn-group-vertical collapse "  id="collapseExample">
	            <br>
	            <button type="button" class="btn " style="width: 105px"
	            onClick="location.href='${pageContext.request.contextPath}/myeats/group'" >그룹 관리</button>
	            <br>
	            <button type="button" class="btn " style="width: 105px"
	            onClick="location.href='${pageContext.request.contextPath}/myeats/post'" >작성글 관리</button>
	            <br>
	            <button type="button" class="btn " style="width: 105px"
	            onClick="location.href='${pageContext.request.contextPath}/myeats/detail'">맛찜 리스트</button>
	            <br>
	            <button type="button" class="btn " style="width: 105px"
	            onClick="location.href='${pageContext.request.contextPath}/myeats/detail'">회원정보 수정</button>
	        </div>
     </div>
     <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
  <script>
  
  $(".collapse").on({ window.onload = function() { 
	  $(".collapse").collapse("show"); },
  
	  $(".btn").click(function(){
	        $(".collapse").collapse("toggle");
		});
    
    	
  </script>
     
     
	
		
</body>
</html>