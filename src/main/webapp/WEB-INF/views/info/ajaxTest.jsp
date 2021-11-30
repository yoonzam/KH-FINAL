<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>

<style>


</style>


</head>
<body>
<h3>Ajax Test</h3>


	<div align="center" id="result">
		 <button type="button" id="btnOK">수정</button>
	</div> 

<script type="text/javascript">
	
		
			$("#btnOK").click(function(){  
			      
			    var url="/info/memberInfo";  
			  
			    $.ajax({      
			        type:"GET",  
			        url:url,   
			        dataType: 'json',
			        success:function(data){ 
			      	let html = '';
					for (var i = 0; i < data.length; i++)
						html += '<li>'+data[i].nickname+'</li>';
						$('#result').html(html);
			        },   
			        error:function(e){  
			            alert(e.responseText);  
			        }  
			    });  
			      
			});  


				
	
	
</script>
</body>
</html>