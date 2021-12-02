<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<link rel="stylesheet" type="text/css"
	href="/resources/css/timeline/timeline.css" />
<script type="text/javascript"
	src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=478e845e80d3b693f2a53821b0866272&libraries=services,clusterer,drawing"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/map/map.css" />
<style type="text/css">
.popup {
	grid-column: 2/5;
	grid-row: 14/17;
	z-index: 5;
	display: flex;
	justify-content: center;
}

.popup-wrap {
	display: flex;
	justify-content: center;
	width: 65%;
	height: 100%;
	background-color: white;
	border-radius: 8px;
	padding-left: 20px;
	box-shadow: 2px 2px 4px rgb(0 0 0/ 30%);
}

.rest-info {
	padding: 20px 0;
	flex: 6;
	display: flex;
	flex-direction: column;
	justify-content: space-around;
}

.rest-title {
	font-weight: 700;
	font-size: 20px;
	color: #111;
	margin-top: 0;
	margin-bottom: 10px;
}

.wrap-btn {
	flex: 1;
	position: relative;
	display: flex;
}

.btn {
	padding: 18px;
	border-radius: 5px;
	color: #fff;
	background-color: #fa8633;
	font-weight: 700;
	transition-duration: 0.5s;
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
}

.btn:hover {
	background-color: #ffa54f;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/header.jsp"%>
	<section>
		<div class="container">
			<div class="search">
				<div class="search-form">
					<div class="search-box">
						<label><input class="keyword" type="text"
							placeholder="식당 후기 검색하기"></label> <a id="search"
							class="search-btn">검색</a>
					</div>
					<div class="select-bar">
						<select id="friendList" name="friends" class="select"
							style="display: none;">
							<option disabled selected>🍟잇친이들의 맛집</option>
						
						</select> <select name="category" class="select"
							onchange="changeLangSelect()" id="checkCategory">
							<option disabled="disabled" selected="selected">🎈잇츠맵카테고리</option>
							<c:forEach items="${groups}" var="groups">
								<option  value="${groups.id}" >${groups.groupName}</option>
							</c:forEach>
							<option value="follower">잇친맵</option>
							<option value="social">소셜맵</option>
						</select>
					</div>
				</div>
			</div>
			<div class="map-review">
				<div class="review_wrap" onclick="">
					<div class="img-box">
						<img class="image-thumbnail" src="/resources/img/upload/01.jpg">
					</div>
					<div class="info">
						<div class="title-wrap">
							<div class="eats-name">스시 아루히</div>
							<div class="icons">
								<i onclick="clickLock(this);" class="fas fa-unlock"></i>
							</div>
						</div>

						<div class="eats-location">서울 영등포구</div>
						<div class="eats-tag">
							<span>#가성비</span> <span>#친근함</span> <span>#1~2만원대</span>
						</div>
					</div>
				</div>

			</div>
			<div class="eatsMap">
				<div id="map"></div>
			</div>
			<div class="popup">
				<div class="popup-wrap" style="display: none;">
					<div class="rest-info">
						<h2 class="rest-title" id="place-name"></h2>
						<span class="rest-content" id="road-address"></span>
					</div>
					<div class="wrap-btn">
						<span class="btn" id="map_reviewBtn">후기등록</span>
					</div>
				</div>

			</div>
		</div>

	</section>

	<%@ include file="/WEB-INF/views/include/footer.jsp"%>
	<script type="text/javascript">
	
	/* 유진 추가내용 */
	document.querySelector('.btn').addEventListener('click',() => {
		document.querySelector('#pop-review-form').style.display='flex';
	})
	
	document.querySelector('.review_wrap').addEventListener('click',()=>{
		console.dir("동작중");
		document.querySelector('#pop-review-detail').style.display='flex';
	})
	
		let clickLock = (e) =>{
			
			if (e.className.match("fas fa-unlock")) {
				e.className = "fas fa-lock";
				e.stopPropagation();
				
			}else{
				e.className = "fas fa-unlock";
				e.stopPropagation();
			}
			
			
		}
		
		  var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
		  var options = { //지도를 생성할 때 필요한 기본 옵션
			center : new kakao.maps.LatLng(37.54699, 127.09598), //지도의 중심좌표.
			level : 3
		//지도의 레벨(확대, 축소 정도)
		};
		var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
		
		
		document.querySelector('#search').addEventListener('click', (e) => {
		    let keyword = document.querySelector('.keyword').value;
			
			searchKeyword(keyword);
			document.querySelector(".popup-wrap").style.display = 'none';
		});
		
		document.querySelector('.keyword').addEventListener('keyup', (e)=> {
		    if (e.keyCode === 13) {
		    	let keyword = document.querySelector('.keyword').value;
				
				searchKeyword(keyword);
				document.querySelector(".popup-wrap").style.display = 'none';
		  }  
		});
		
		/*비동기로 백으로 값보내기 */
		let searchKeyword = (keyword) =>{
			let parent = document.querySelector('.map-review');
			removeAllChildNodes(parent);
			fetch("/map/search?keyword=" + keyword)
			  .then(response => {
				  if(response.ok){	//통신 성공시
					  return response.json();
				  }else{
					  throw new Error(response.status);
				  }
			  }).then(json => {	//promise객체의 json
				console.dir(json);
			  	//마크 찍어주기
				markerCreate(json,keyword);
			  	
				searchMap(keyword,json);
				  
				for (var i = 0; i < json.length; i++) {
					  let returnDiv = takeReview(json[i].reviewId,json[i].review.addr,json[i].review.resName,json[i].review.hashtag);
					  
					  var $div = $(returnDiv);
					  $('.map-review').append($div);
				}
					  
			  }).catch(error => {
				  alert("실패");
			  });
		}
		//이전 검색 삭제
		function removeAllChildNodes(parent) {
		    while (parent.firstChild) {
		        parent.removeChild(parent.firstChild);
		    }
		}
		
		//리뷰검색 마크 생성
		let markerCreate = (reviews,keyword) =>{
			var positions = [];
			
			
			for (var i = 0; i < reviews.length; i++) {
				let mark = {
				        title: reviews[i].review.resName, 
				        latlng: new kakao.maps.LatLng(reviews[i].review.location.coordinates[1], reviews[i].review.location.coordinates[0])
				 }
				positions.push(mark);
			}
			
			console.dir(positions);

			// 마커 이미지의 이미지 주소입니다
			var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
			positions.forEach(function(position, idx) {
				 // 마커 이미지의 이미지 크기 입니다
			    var imageSize = new kakao.maps.Size(24, 35); 
			    
			    // 마커 이미지를 생성합니다    
			    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
			   
			    // 마커를 생성합니다
			   var marker = new kakao.maps.Marker({
			        map: map, // 마커를 표시할 지도
			        position: position.latlng, // 마커를 표시할 위치
			        title : position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
			        image : markerImage // 마커 이미지
			        
			    });	
			   kakao.maps.event.addListener(marker, 'click', function(){
				   
				   restInfo(marker,keyword);
			   });
			});
			
			
		}
		let showDiv = (placeName,roadAddress) =>{
			let reviewShow = document.querySelector(".popup-wrap");
			//같은 내용일경우 
			if (document.querySelector('#place-name').innerHTML == placeName 
					&& document.querySelector('#road-address').innerHTML == roadAddress 
					&& reviewShow.style.display == "") {
				
				reviewShow.style.display = "none";
			}else if(document.querySelector('#place-name').innerHTML != placeName 
					&& document.querySelector('#road-address').innerHTML != roadAddress 
					&& reviewShow.style.display == ""){
				reviewShow.style.display = "";
			}else{
				reviewShow.style.display = "";
			}
		}
		
		let restInfo = (marker,keyword) =>{
			
			fetch('https://dapi.kakao.com/v2/local/search/keyword.json?y='+ marker.getPosition().getLat() +'&x='+ marker.getPosition().getLng() +'&radius=1&query=' +keyword, {
				  headers: {
				    Authorization: `KakaoAK de36fa19e556a7179bb149f25fa41a95` 
				  }
				})
				.then(response => response.json())
				.then(json => {
				// 받은 json으로 기능 구현
				console.dir(json.documents[0]);
				markerInfo = json.documents[0];
				//음식점 이름, 주소 텍스트로 변환 
				let placeName = json.documents[0].place_name;
				let roadAddress = json.documents[0].road_address_name;
				
				
				//
				showDiv(placeName,roadAddress);
				
				//팝업창에 내용 기입
				document.querySelector('#place-name').innerHTML = placeName;
				document.querySelector('#road-address').innerHTML = roadAddress;
				
				
			});
			
			
		}
		
		
		
		//review div 받아온 리뷰에 맞게 수정하는 함수
		let takeReview = (id,address,name,tag) =>{
			
			
			let tags = '';
			for (var i = 0; i < tag.length; i++) {
				tags += '<span>#'+tag[i]+'</span>';
			}
				
			
			
			let addr = address;
			let na = name;
			let t = tag;
			//문자열 자르기
			let splitAddr = address.split(' ');
			let splitName = name.split(' ');
			
			let reviewContent = 
				'<div class="review_wrap" onclick="viewTimeline(\'' + id + '\')">'
				+'<div class="img-box">'
				+'<img class="image-thumbnail" src="/resources/img/upload/01.jpg">'
				+'</div>'
				+'<div class="info">'
				+'<div class="title-wrap">'
				+'<div class="eats-name">'+splitName[0]+'</div>'	
				+'<div class="icons"><i onclick="clickLock(this);"class="fas fa-unlock"></i></div>'		
				+'</div>'
				+'<div class="eats-location">'+ splitAddr[0] + ' ' +splitAddr[1] +'</div>'
				+'<div class="eats-tag">'+ tags +'</div>'	
				+'</div>';
			
			return reviewContent;
		}
		
		
	
		/* 맵에 표시된 가게의 json정보를 담는 변수 */
		let markerInfo;
				
		let searchMap = (keyword,markerData) =>{
			
			// 장소 검색 객체를 생성합니다
			var ps = new kakao.maps.services.Places(); 
			// 키워드로 장소를 검색합니다
			ps.keywordSearch(keyword, placesSearchCB); 
			// 키워드 검색 완료 시 호출되는 콜백함수 입니다
			function placesSearchCB (data, status, pagination) {
			    if (status === kakao.maps.services.Status.OK) {
			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
			        // LatLngBounds 객체에 좌표를 추가합니다
			        var bounds = new kakao.maps.LatLngBounds();
			        
			        
			        //데이타 중복 마커 처리
			        let processingData = processingMarker(data,markerData);
			        console.dir(processingData);
			        
			        
			        for (var i=0; i<processingData.length; i++) {
			        	
			            displayMarker(processingData[i]);    
			            bounds.extend(new kakao.maps.LatLng(processingData[i].y, processingData[i].x));
			        }       
			        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
			        map.setBounds(bounds);
			    } 
			}
			// 지도에 마커를 표시하는 함수입니다
			function displayMarker(place) {
			    
			    // 마커를 생성하고 지도에 표시합니다
			    var marker = new kakao.maps.Marker({
			        map: map,
			        position: new kakao.maps.LatLng(place.y, place.x) 
			    });
			    // 마커에 클릭이벤트를 등록합니다
			    kakao.maps.event.addListener(marker, 'click', function() {
			    	restInfo(marker,keyword);
					
				    
				});
			}
		}
		
		//마커 중복제거 
		let processingMarker = (data,markerData) =>{
			let processedMarker = data.filter((review)=>{
	        	for (var i = 0; i < markerData.length; i++) {
	        		if (review.x  == markerData[i].review.location.x 
	        				&& review.y == markerData[i].review.location.y ) {
						return false;
					}
				}
	        	return true;
	        });
			
			return processedMarker;
		}
		
		
		/* 니캉내캉 선택 후 나타나는 group 친구창 */
		let changeLangSelect = () => {
			let check = document.getElementById("checkCategory");
			if ('follower' == check.options[check.selectedIndex].value) {
				console.dir("1동작");
				document.querySelector('#friendList').style.display = "none";
			}else if ('social' == check.options[check.selectedIndex].value) {
				console.dir("2동작");
				document.querySelector('#friendList').style.display = "none";
			}else{
				console.dir("3동작");
				console.dir(check.options[check.selectedIndex].value);
				findGroupMember(check.options[check.selectedIndex].value);
				document.querySelector('#friendList').style.display = "";
			}
		}
		
		//그룹창 선택 후 그룹내 잇친리스트 불러오기
		let findGroupMember = (groupId) =>{
			console.dir("클릭 확인");
			fetch("/map/group?groupId=" + groupId)
			  .then(response => {
				  if(response.ok){	//통신 성공시
					  return response.json();
				  }else{
					  throw new Error(response.status);
				  }
			  }).then(json => {	//promise객체의 json
				console.dir("그룹 멤버리스트");
				console.dir(json);
				optionAdd(json);
					  
			  }).catch(error => {
				  alert("실패");
			  });
			
		}
		
		//멤버리스트 옵션에 추가 메서드
		let optionAdd = (memberList) =>{
			
			
			$('#friendList').children('option:not(:first)').remove();
			let selectEl = document.querySelector("#friendList"); 
			
			for (var i = 0; i < memberList.length; i++) {
				$(selectEl).append("<option value='"+ (memberList[i].memberId)+"'>" + (memberList[i].memberName) + "</option>")
			}
			
		}
		
		
		//map 초기 화면에 리뷰 리스트와 마커 뿌려주기
		var myEetsReview = 	${reviews};	
		console.dir("json잘 받아왔나?");
		console.dir(myEetsReview);
		
	</script>
	<script type="text/javascript" src="/resources/js/map/Geolocation.js"></script>


</body>
</html>
