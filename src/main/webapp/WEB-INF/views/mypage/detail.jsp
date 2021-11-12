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
        
      
		
		/* 메인 */
		.main {
		/* sidebar영역 확보 */
		  margin-left: 140px;
		  
		  font-size: 28px; 
		  padding: 0px 10px;
		  
		  /* header영역 확보 */
          padding-top: 150px; 
		}
		
        div.container {
          width:960px;
          margin:auto;
          border-radius: 12px;

          background-color: #ffffff;
          padding: 30px;
          box-shadow: 0px 20px 80px 0px rgb(153 153 153 / 30%);
          
          line-height:26px;
          font-size:16px;
          font-weight:normal;
          color:black; 
        }
        
		/* row랑 column 영역의 개별 padding값 */
		.row,
		.row > .column {
		  padding: 8px;
		}
		
		/* 3개의 컬럼을 동등하게 영역을 나눔 */
		.column {
		  float: left;
		  width: 33.33%;
		}
		
		/* 게시글 개별영역 */
		.content {
		  padding: 10px;
		}
		
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<%@ include file="/WEB-INF/views/mypage/dropDown.jsp" %>    
	
<div class="main">
	<div class="background">
		<div class="container">
			<div class="row">
				맛찜 리스트
				<hr width="900">
			</div> <!-- row -->
			<br>
			<div class="row">
				<div class="column">
				  <div class="content">
				    <img src="https://via.placeholder.com/100" alt="" class="img-square" style="width:100%">
				    <h4>게시글</h4>
				    <p>내용 요약..</p>
				  </div>
				</div>
				<div class="column">
				  <div class="content">
				    <img src="https://via.placeholder.com/100" alt="" class="img-square" style="width:100%">
				    <h4>게시글</h4>
				    <p>내용 요약..</p>
				  </div>
				</div>
				<div class="column">
				  <div class="content">
				     <img src="https://via.placeholder.com/100" alt="" class="img-square" style="width:100%">
				    <h4>게시글</h4>
				    <p>내용 요약..</p>
				  </div>
				</div>
			</div> <!-- row -->

			<div class="row">
				<div class="column">
				  <div class="content">
				    <img src="https://via.placeholder.com/100" alt="" class="img-square" style="width:100%">
				    <h4>게시글</h4>
				    <p>내용 요약..</p>
				  </div>
				</div>
				<div class="column">
				  <div class="content">
				    <img src="https://via.placeholder.com/100" alt="" class="img-square" style="width:100%">
				    <h4>게시글</h4>
				    <p>내용 요약..</p>
				  </div>
				</div>
				<div class="column">
				  <div class="content">
				     <img src="https://via.placeholder.com/100" alt="" class="img-square" style="width:100%">
				    <h4>게시글</h4>
				    <p>내용 요약..</p>
				  </div>
				</div>
			</div> <!-- row -->
		
			<div class="row">
				<div class="column">
				  <div class="content">
				    <img src="https://via.placeholder.com/100" alt="" class="img-square" style="width:100%">
				    <h4>게시글</h4>
				    <p>내용 요약..</p>
				  </div>
				</div>
				<div class="column">
				  <div class="content">
				    <img src="https://via.placeholder.com/100" alt="" class="img-square" style="width:100%">
				    <h4>게시글</h4>
				    <p>내용 요약..</p>
				  </div>
				</div>
				<div class="column">
				  <div class="content">
				     <img src="https://via.placeholder.com/100" alt="" class="img-square" style="width:100%">
				    <h4>게시글</h4>
				    <p>내용 요약..</p>
				  </div>
				</div>
			</div> <!-- row -->
 			
		</div> <!-- container -->
	</div> <!-- background -->
</div> <!-- main -->
    
  

    <!-- 부트스트랩의 플러그인-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- 부트스트랩의 플러그인-->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>


<%@ include file="/WEB-INF/views/include/footer.jsp" %>


</body>
</html>