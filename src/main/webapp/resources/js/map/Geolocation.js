/**
 * 
 */
 function getLocation() {
  if (navigator.geolocation) { // GPS를 지원하면
    navigator.geolocation.getCurrentPosition(function(position) {
     
      	let lat = position.coords.latitude;
      	let lng = position.coords.longitude;
      	 

      	let filterd = myEetsMap.filter((review)=>{
				if(1.5 > getDistanceFromLatLonInKm(lat,lng,review.review.location.y,review.review.location.x)){
					return true;
				}
				return false;
				
			});
		console.dir(filterd);
		if(filterd.length == 0){
			 alert("근처의 리뷰가 없습니다.");
		}else{
			//원래 있는 지도에 마커 지우기
			removeOverlay();
			removeMarker();
	    	markerCreate(filterd);
		}
		
    	
		
      
    }, function(error) {
      console.error(error);
    }, {
      enableHighAccuracy: false,
      maximumAge: 0,
      timeout: Infinity
    });
  } else {
    alert('GPS를 지원하지 않습니다');
  }
}

//자신과 리뷰음식점과의 거리 km리턴
function getDistanceFromLatLonInKm(lat1,lng1,lat2,lng2) {
    function deg2rad(deg) {
        return deg * (Math.PI/180)
    }

    var R = 6371; // Radius of the earth in km
    var dLat = deg2rad(lat2-lat1);  // deg2rad below
    var dLon = deg2rad(lng2-lng1);
    var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    var d = R * c; // Distance in km
    return d;
}



getLocation();