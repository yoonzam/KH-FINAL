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
        	margin: 78px;
        	margin-right: -113px;
        }
        
        .btn btn-warning{
       		background-color:#fa8633;
        }
         .memberimg {
       	  position: relative;
        }
       
		.glyphicon-remove, .glyphicon-camera{
			left:17px;
			position: absolute !important; 
				
		}
		.img100{
			left:27px;
			position: absolute !important; 
		}
		hr{
		margin-left:-11px;
		width: 772px;
		}
		
		
      
    </style>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<%@ include file="/WEB-INF/views/myeats/dropDown.jsp" %>    
	
		<div class="main">
			<div class="background">
				<div class="container">
					<div class="contents" >
						<span style="font-size: 1.5em">그룹1</span>
						<hr>
						<br>
						<br>
				      <div class="row">
				        <div class="col-xs-5  memberimg">
							<img src="https://via.placeholder.com/60" alt="" class="img-circle">
							<a class="glyphicon glyphicon-camera"></a>
				        </div>
				        <div class="col-xs-3">
				        </div>
				        <div class="col-xs-4">
				        	<img src="https://via.placeholder.com/100" alt="" class="img-circle" >
				        	<a class="glyphicon glyphicon-camera img100"></a>
				        </div>
				      </div>
				      	<br>
				      <div class="row">
				        <div class="col-xs-3" style="font-weight: bold;">참가자
				        </div>
				        <div class="col-xs-6">
				        </div>
				        <div class="col-xs-2">
							<div class="row">
						        <div class="col-xs-12"></div>
						        <div class="col-xs-12"></div>
						        <div class="col-xs-12"></div>
				       		</div>
				        </div>
				        <div class="col-xs-1">
				        </div>
				      </div>
				      <div class="row">
						<div class="col-xs-1 memberimg">
							<span class="glyphicon glyphicon-remove"></span>
					      	<img src="https://via.placeholder.com/60" alt="" class="img-circle">
						</div>
					    <div class="col-xs-1 memberimg">
							<span class="glyphicon glyphicon-remove"></span>
					      	<img src="https://via.placeholder.com/60" alt="" class="img-circle">
						</div>
					    <div class="col-xs-1 memberimg">
							<span class="glyphicon glyphicon-remove"></span>
					      	<img src="https://via.placeholder.com/60" alt="" class="img-circle">
						</div>
					    <div class="col-xs-1 memberimg">
							<span class="glyphicon glyphicon-remove"></span>
					      	<img src="https://via.placeholder.com/60" alt="" class="img-circle">
						</div>
					     <div class="col-xs-1 memberimg">
							<span class="glyphicon glyphicon-remove"></span>
					      	<img src="https://via.placeholder.com/60" alt="" class="img-circle">
						</div>
				      </div>
				      <br>
				      <br>
				      <br>
				      <div class="row">
				        <div class="col-xs-3" style="font-weight: bold;">초대하기</div>
				        <div class="col-xs-9"><a>+ 친구 초대하기</a></div>
				      </div>
				      <div class="row">
				        <div class="col-xs-3"></div>
				        <div class="col-xs-9"><a>+ 친구 초대하기</a></div>
				      </div>
				      <div class="row">
				        <div class="col-xs-3"></div>
				        <div class="col-xs-9"><a>+ 친구 초대하기</a></div>
				      </div>
				       <br>
				      <div class="row">
				      	<div class="col-xs-3"></div>
				        <div class="col-xs-3"><input class="btn btn-warning" type="submit" value="저장" ></div>
				        <div class="col-xs-1"></div>
				        <div class="col-xs-4"><input class="btn btn-warning" type="submit" value="취소"></div>
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