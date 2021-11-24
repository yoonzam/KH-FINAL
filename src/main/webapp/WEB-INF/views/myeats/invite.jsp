<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>

<style>
#findword {
  background-image: url('/css/searchicon.png');
  background-position: 10px 12px; 
  background-repeat: no-repeat; 
  width: 100%; 
  font-size: 16px; 
  padding: 12px 20px 12px 40px; 
  border: 1px solid #ddd; 
  margin-bottom: 12px; 
}

#myUL {
  
  list-style-type: none;
  padding: 0;
  margin: 0;
}

#myUL li a {
  border: 1px solid #ddd; 
  margin-top: -1px; 
  background-color: #f6f6f6; 
  padding: 12px; 
  text-decoration: none; 
  font-size: 18px; 
  color: black; 
  display: block;
}

#myUL li a:hover:not(.header) {
  background-color: #eee; 
}

/* 페이징 가운데 정렬 */	
.page{
  text-align: center;  
  width: 100%;
  }

.pagination {
  list-style: none;
  display: inline-block;
  padding: 0;
  margin-top: 20px;
  }

.pagination li {
  display: inline;
  text-align: center;
  }

  
</style>
      
      
</head>
<body>
<section>
	<div class="container-wrap">
		<div class="container">
			<select name="findType" style="display: none;">
				<option  value="S" 
					<c:out value="${fCri.findType == 'S'? 'selected' : ''}"/>>닉네임
				</option>
			</select>
			<input type="text" id="findword" onkeyup="myFunction()" placeholder="Search for nickname"
			name="keyword" value="${fCri.keyword}">
			<button style="display: none;" id="findBtn">검색</button><br/><br/>
			
			<c:forEach items="${list}" var="memberlist" varStatus="status"  begin="0"  >
			<c:if test="${status.first}"><ul id="myUL"></c:if>
			<c:choose>
				<c:when test="true">
				<li>
			  <li><a href="#"><strong>${memberlist.nickname}</strong></a></li>
			  	</c:when>
				</c:choose> 
			<c:if test="${status.last}"></ul></c:if>
			</c:forEach>
			
				<!-- paging -->
	  		 	<div class="page">
	      			<ul class="pagination">
	       				<li class="<c:out value="${pageObject.page == cnt? 'pagebtn active':'btn'}"/>">
	       					<pageNav:pageNav listURI="invite" pageObject="${pageObject}" ></pageNav:pageNav>
	       				</li>
	      			</ul>
	  			</div>

		</div><!-- container -->
	</div><!-- container-wrap -->
</section>  

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#findword').keypress(function(e){
		self.location ="invite"+"${pageObject.makeURI(1)}"
			+"&findType="
			+$("select option:selected").val()
			+"&keyword="+$("#findword").val();
	});
	
}); 
</script>

<script>
  function myFunction() {
  var input, filter, ul, li, a, i, txtValue;
  input = document.getElementById('findword');
  filter = input.value.toUpperCase();
  ul = document.getElementById("myUL");
  li = ul.getElementsByTagName('li');

  for (i = 0; i < li.length; i++) {
    a = li[i].getElementsByTagName("a")[0];
    txtValue = a.textContent || a.innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      li[i].style.display = "";
    } else {
      li[i].style.display = "none";
    }
  }
}
</script>
</body>
</html>