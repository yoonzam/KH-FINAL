$(document).ready(()=>{

	    //내위치
    navigator.geolocation.getCurrentPosition(function(position) {
    let longitude = position.coords.longitude;	//경도
    let latitude = position.coords.latitude;	//위도
//	alert("현재 위치는 : " + longitude + ", "+ latitude);
 

	let obj = {longitude_: longitude, latitude_: latitude}
	
	setLocation(obj);

	});
	
    resizeImg();
    resizeImg2();
    

});


function setLocation(obj){

	$.ajax({
	    url: "/main/setLocation",
	    method: "get",
	    type: "json",
	    data: obj,
	    success: function(data) {

			console.dir(data);
			let html = "";
			for(i = 0; i < data.length; i++){
				html += '<div class="eats-list">'
					 +  '<div class="thum1">'
					 +  '<img src="'+ data[i].reviews.thumUrl +'">'
					 +  '</div>'
					 +  '<div class="info">'
					 +  '<div class="eats-location">'
					 +  data[i].reviews.addr.split(' ')[0] + data[i].reviews.addr.split(' ')[1]
					 +  ' > ' + data[i].reviews.category
					 +  '</div>'
					 +  '<div class="eats-name">' + data[i].reviews.resName;
						if(data[i].reviews.like > 0) {
		    				html += '<i data-like="' + data[i].reviewId + '" class="eats-like fas fa-heart"></i>';
		    			} else{
		    				html += '<i data-like="' + data[i].reviewId + '" class="eats-like far fa-heart"></i>';
			    		}
	    		html += '</div>'
	    			 +  '<div class="eats-tag">';
	    		 		for(j = 0; j < data[i].reviews.hashtag.length; j++) {
	    		 			html += '<span>#' + data[i].reviews.hashtag[j] + '</span>';
	    		 		}
	    		html += '</div>'
	    			  + '<div class="eats-score">'
	    			  + '<i class="fas fa-star"></i>' + ((data[i].reviews.taste + data[i].reviews.clean + data[i].reviews.service)/3).toFixed(1)
	    			  + '</div>'
	    			  + '</div>'
					  + '</div>';
//				console.dir(data[i].reviewId);
//				console.dir(data[i].reviews.resName);
//				console.dir(data[i].reviews.hashtag);
			}
			
		$('.visual2').append(html);
		
		
			let thum1 = $(".visual2 .thum1");
		   		Array.from(thum1).forEach((e)=>{
		        e.style.width= 400+'px';
		        e.style.height= 250+'px';
			 });


		$('.visual2').slick({
		  centerMode: true,
		  centerPadding: '60px',
		  slidesToShow: 3,
		  responsive: [
		    {
		      breakpoint: 768,
		      settings: {
		        arrows: false,
		        centerMode: true,
		        centerPadding: '40px',
		        slidesToShow: 3
		      }
		    },
		    {
		      breakpoint: 480,
		      settings: {
		        arrows: false,
		        centerMode: true,
		        centerPadding: '40px',
		        slidesToShow: 1
		      }
		    }
		  ]
		});
			


		 }
		 
//		error: console.log("위치값 전송 실패")
	})    
};
	



$('.eats-list').hover((e) => {
    e.currentTarget.children[0].children[0].style.transform='scale(1.1)';
    e.currentTarget.children[0].children[0].style.transitionDuration='0.5s';
  }, (e)=>{
    e.currentTarget.children[0].children[0].style.transform='scale(1)';
});

$('.filter-btn').click(()=>{
    if($('.filter-menu').css('display')=='none'){
        $('.filter-menu').show();
        $('.filter-btn').css('border','1px solid #fa8633');
    } else{
        $('.filter-menu').hide();
        $('.filter-btn').css('border','1px solid #ddd');
    }
});

$('#allCheck').click(()=>{
    if($('#allCheck').attr('class')=='checked'){
        $('.filter-menu label').removeClass('checked');
        $('.filter-menu input:checkbox').prop('checked', false);
    } else{
        $('.filter-menu label').addClass('checked');
        $('.filter-menu input:checkbox').prop('checked', true);
    }
});

$('.filter-menu input:checkbox').click((e)=>{
    let checkbox = e.currentTarget;
    let label = e.currentTarget.parentNode;
    label.classList.toggle('checked');
});

$(window).resize(() => {
    resizeImg();
    resizeImg2();
});

let resizeImg = () => {
    let thum = $(".visual .thum");

    Array.from(thum).forEach((e)=>{
        e.style.width= 400+'px';
        e.style.height= 250+'px';
       
    });
}

let resizeImg2 = () => {
    let thum1 = $(".visual2 .thum1");
    Array.from(thum1).forEach((e)=>{
        e.style.width= 400+'px';
        e.style.height= 250+'px';
    });
}




$('.visual').slick({
  centerMode: true,
  centerPadding: '60px',
  slidesToShow: 3,
  responsive: [
    {
      breakpoint: 768,
      settings: {
        arrows: false,
        centerMode: true,
        centerPadding: '40px',
        slidesToShow: 3
      }
    },
    {
      breakpoint: 480,
      settings: {
        arrows: false,
        centerMode: true,
        centerPadding: '40px',
        slidesToShow: 1
      }
    }
  ]
});
	



