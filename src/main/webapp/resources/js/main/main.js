$(document).ready(()=>{
    resizeImg();
    resizeImg2();
    
    //내위치
    navigator.geolocation.getCurrentPosition(function(position) {
    const longitude = position.coords.longitude;	//경도
    const latitude = position.coords.latitude;	//위도
 
 	//geojson : 경도, 위도
/* 	{
    "type": "Feature",
    "geometry": {
        "type": "Point",
        "coordinates": [-122.126986, 47.639754]
    }
 */
    alert("현재 위치는 : " + latitude + ", "+ longitude);
    
});


});

$(window).resize(() => {
    resizeImg();
    resizeImg2();
});



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
	

