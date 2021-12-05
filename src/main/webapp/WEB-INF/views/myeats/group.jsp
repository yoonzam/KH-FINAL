<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resources/css/myeats/myeats.css" />
<%@ include file="/WEB-INF/views/include/head.jsp" %>

<style type="text/css">

/* css 상태 보고 추후에 이동 예정 */
.delete{
	border:none;
	background-color: #ccc;
	color: #fff;
	font-size: 1em;
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
  
  
  
/* 그룹생성 */	
.btn-area {
    text-align: end;
}

.btn-area button {
    cursor: pointer;
    font-weight: 8;
    font-size: 15px;
    padding: 10px 15px;
}

/* 페이징 */
.pagination {
  display: inline-block;
}

.pagination a {
  color: black;
  float: left;
  padding: 8px 16px;
  text-decoration: none;
}

.pagination a:active{
	background-color: #eee;

}

</style>

</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<section>
	<div class="container-wrap">
		<div class="container">
			<ul class="myeats-tab">
				<li class="selected">그룹관리</li>
				<li><a href="/myeats/post">작성글관리</a></li>
				<li><a href="/myeats/detail">맛찜리스트</a></li>
				<li><a href="/member/edit-profile">회원정보 수정</a></li>
			</ul>
				<c:forEach items="${groups}" var="groups" varStatus="status"  begin="0"  >
				<c:if test="${status.first}"><ul class="group-wrap"></c:if>
				<c:choose>
					<c:when test="true">
					<li>
						<div class="group">
							<div class="group-img"><img src="${!empty groups.thumUrl ? groups.thumUrl : '/resources/img/common/upload-logo.png'}"></div>
								<p class="group-info">
									<strong>${groups.groupName}</strong><br>
									<i class="fas fa-user"></i> ${fn:length(groups.participants) }    &nbsp;&nbsp;<i class="fas fa-feather"></i>
									<fmt:formatDate pattern="yyyy/MM/dd" value="${groups.groupcreatedate}"/>
								</p>
						</div>
						<c:if test="${authentication.id == groups.memberId }">
						<div class="controller">
							<a href="groupDetail?id=${groups.id}" class="group-menu">그룹관리</a>
							<a href="groupDetail?id=${groups.id}">수정</a>
							<a href="/myeats/delete?id=${groups.id}">삭제</a> 
						</div>
						</c:if>
						<c:if test="${authentication.id != groups.memberId }">
						<div class="controller">
							<a href="groupDetail?id=${groups.id}" class="group-menu">그룹보기</a>
						</div>
						</c:if>
					</li>
					</c:when>
				</c:choose> 
				<c:if test="${status.last}"></ul></c:if>
				</c:forEach>
			
				<div class="btn-area">
					<a href = "createGroup"><button type="submit" class="create-btn">그룹 만들기</button></a>
				</div>
			
				<!-- paging -->
	  		 	<div class="page">
	      			<ul class="pagination">
	       				<li class="<c:out value="${pageObject.page == cnt? 'pagebtn active':'btn'}"/>">
	       					<pageNav:pageNav listURI="group" pageObject="${pageObject}" ></pageNav:pageNav>
	       				</li>
	      			</ul>
	  			</div>
		</div><!-- container -->
	</div><!-- container-wrap -->
</section>  

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
</body>
</html>