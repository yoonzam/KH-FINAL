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
        
       
		/*li */
		.container ul li {
		  cursor: pointer;
		  position: relative;
		  margin: 10px 100px;
		  padding: 10px 29px;
		  list-style-type: none;
		  background: #fcebc0;
		  
		  font-size: 18px;
		  transition: 0.2s;
		  user-select: none;
		  border: solid 1px;
		}
		
		/*짝수li 배경색*/
		.container ul li:nth-child(odd) {
		  background: #fff8e6;
		}
		
		/* hover시 배경색 */
		.container ul li:hover {
		  background: #fff4cf;
		}
		
	
		/* 자세히 보기 버튼 */
		.container #myUL li .btn{
			position: relative;
			margin-left: 452px;
		}
		
		/* 새로만들기 버튼 */
		.container .btn-danger{
		  margin-left: 20px;
		}
		
		/* display: none줘서 new용도로 사용예정 */
		.container .glyphicon{
		position: absolute;
		  left: 28px;
		  top: 11px;
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
				<ul id="myUL">
				  <li>
				 	<span class="glyphicon glyphicon-circle-arrow-up">
					</span>
				  	<img src="https://via.placeholder.com/50" alt="" class="img-circle">
				  	&nbsp;&nbsp;&nbsp;그룹1
				  	<input class="btn btn-warning" type="submit" value="자세히 보기">
				  </li>
				  <li>
				  	<span class="glyphicon glyphicon-circle-arrow-up">
					</span>
				  	<img src="https://via.placeholder.com/50" alt="" class="img-circle">
				  	&nbsp;&nbsp;&nbsp;그룹1
				  	<input class="btn btn-warning" type="submit" value="자세히 보기">
				  </li>
				  <li>
				  	<span class="glyphicon glyphicon-circle-arrow-up">
					</span>
				  	<img src="https://via.placeholder.com/50" alt="" class="img-circle">&nbsp;&nbsp;&nbsp;그룹1
				  	<input class="btn btn-warning" type="submit" value="자세히 보기">
				  </li>
				  <li>
				  	<span class="glyphicon glyphicon-circle-arrow-up">
					</span>
				  	<img src="https://via.placeholder.com/50" alt="" class="img-circle">&nbsp;&nbsp;&nbsp;그룹1
				  	<input class="btn btn-warning" type="submit" value="자세히 보기">
				  </li>
				  <li>
				  	<span class="glyphicon glyphicon-circle-arrow-up">
					</span>
				  	<img src="https://via.placeholder.com/50" alt="" class="img-circle">&nbsp;&nbsp;&nbsp;그룹1
				  	<input class="btn btn-warning" type="submit" value="자세히 보기">
				  </li>
				  <li>
				  	<span class="glyphicon glyphicon-circle-arrow-up">
					</span>
				  	<img src="https://via.placeholder.com/50" alt="" class="img-circle">&nbsp;&nbsp;&nbsp;그룹1
				  	<input class="btn btn-warning" type="submit" value="자세히 보기">
				  </li>
				</ul>
			</div>
			<div class="row">
				<div class="col-xs-9"></div>
				<div class="col-xs-3">
					<input class="btn btn-danger" type="button" value="새로 만들기">
				</div>
			</div>
			<div class="row">
				<ol class="pager">
				  <li><a href="#" style="font-size: 16px">이전</a></li>
				  <li><a href="#" style="font-size: 16px">다음</a></li>
				</ol>
				<br>
			</div>
		</div> <!-- container -->
	</div> <!-- background -->
</div> <!-- main -->
     
  

    <!-- 부트스트랩의 플러그인-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- 부트스트랩의 플러그인-->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>


<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>
//메인화면 자바스크립트
var myNodelist = document.getElementsByTagName(".main LI");
var i;

// 삭제 버튼을 리스트에 붙이는 스크립트
for (i = 0; i < myNodelist.length; i++) {
 var span = document.createElement("SPAN");
    /*\u00D7 x표*/
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  myNodelist[i].appendChild(span);
}

// 삭제 버튼 누르면 삭제되는 스크립트
var close = document.getElementsByClassName("close");
var i;
for (i = 0; i < close.length; i++) {
  close[i].onclick = function() {
    var div = this.parentElement;
    div.style.display = "none";
  }
}

// 추가되면 체크표시 아이콘(미완)
var list = document.querySelector('ul');
list.addEventListener('click', function(ev) {
  if (ev.target.tagName === 'LI') {
    ev.target.classList.toggle('checked');
  }
}, false);

// 추가시 x버튼 등 생성(미완)
function newElement() {
  var li = document.createElement("li");
  var inputValue = document.getElementById("myInput").value;
  var t = document.createTextNode(inputValue);
  li.appendChild(t);
  if (inputValue === '') {
    alert("You must write something!");
  } else {
    document.getElementById("myUL").appendChild(li);
  }
  document.getElementById("myInput").value = "";

  var span = document.createElement("SPAN");
  var txt = document.createTextNode("\u00D7");
  span.className = "close";
  span.appendChild(txt);
  li.appendChild(span);

}
</script>
</body>
</html>