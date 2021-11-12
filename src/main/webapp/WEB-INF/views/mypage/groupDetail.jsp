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
		  margin-left: 140px; 
		  font-size: 28px; 
		  padding: 0px 10px;
		  
		  /* header영역 확보 */
          padding-top: 150px; 
          
		}
		
		div.background {
		  width:960px;
          margin:auto;
		  background: url(/resources/img/mypage/food1.png)repeat;
		  border-radius: 12px;
		  
		}
        div.container {
          width:960px;
          margin:auto;
          border-radius: 12px;

          background-color: #ffffff;
          background-color: rgba( 255, 255, 255, 0.6 );
          padding: 30px;
          box-shadow: 0px 20px 80px 0px rgb(153 153 153 / 30%);
          
          line-height:26px;
          font-size:16px;
          font-weight:normal;
          color:black;
        }
        
        div.contents {
        	margin: 30px;
        }
		
		
		
		
      
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<%@ include file="/WEB-INF/views/mypage/dropDown.jsp" %>    
	
		<div class="main">
			<div class="background">
				<div class="container">
					<div class="contents">
						그룹1
						<hr width="900">
						<br>
				      <div class="row">
				        <div class="col-xs-2"><img src="https://via.placeholder.com/60" alt="" class="img-circle"></div>
				        <div class="col-xs-3"><button class="btn btn-warning">프로필 편집</button></div>
				        <div class="col-xs-3"></div>
				        <div class="col-xs-4"><img src="https://via.placeholder.com/200x100" alt="" class="img-square"></div>
				      </div>
				      <br>
				      <div class="row">
				        <div class="col-xs-3">참가자</div>
				        <div class="col-xs-6"></div>
				        <div class="col-xs-2">
				         <div class="row">
					        <div class="col-xs-12"><button class="btn btn-warning">프로필 편집</button></div>
					        <div class="col-xs-12"></div>
					        <div class="col-xs-12"></div>
				        </div>
				        </div>
				        <div class="col-xs-1"></div>
				      </div>
				      <div class="row">
					      <div class="col-xs-1"><img src="https://via.placeholder.com/60" alt="" class="img-circle"></div>
					      <div class="col-xs-1"><img src="https://via.placeholder.com/60" alt="" class="img-circle"></div>
					      <div class="col-xs-1"><img src="https://via.placeholder.com/60" alt="" class="img-circle"></div>
					      <div class="col-xs-1"><img src="https://via.placeholder.com/60" alt="" class="img-circle"></div>
					      <div class="col-xs-1"><img src="https://via.placeholder.com/60" alt="" class="img-circle"></div>
					      <div class="col-xs-1"><img src="https://via.placeholder.com/60" alt="" class="img-circle"></div>
					      <div class="col-xs-1"><img src="https://via.placeholder.com/60" alt="" class="img-circle"></div>
					      <div class="col-xs-1"><img src="https://via.placeholder.com/60" alt="" class="img-circle"></div>
				      </div>
				      <br>
				      <br>
				      <br>
				      <div class="row">
				        <div class="col-xs-3">초대하기</div>
				        <div class="col-xs-9">+ 친구 초대하기</div>
				      </div>
				      <div class="row">
				        <div class="col-xs-3"></div>
				        <div class="col-xs-9">+ 친구 초대하기</div>
				      </div>
				      <div class="row">
				        <div class="col-xs-3"></div>
				        <div class="col-xs-9">+ 친구 초대하기</div>
				      </div>
				       <br>
				      <div class="row">
				      	<div class="col-xs-3"></div>
				        <div class="col-xs-3"><input class="btn btn-warning" type="submit" value="저장"></div>
				        <div class="col-xs-3"></div>
				        <div class="col-xs-3"><input class="btn btn-warning" type="submit" value="취소"></div>
				      </div>
				      </div>
			    </div>
		    </div>

		</div>  
     
  

    <!-- 부트스트랩의 플러그인-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- 부트스트랩의 플러그인-->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>


<%@ include file="/WEB-INF/views/include/footer.jsp" %>


</body>
</html>