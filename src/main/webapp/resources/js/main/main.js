$(document).ready(()=>{
    resizeImg();
    resizeImg2();
});

$(window).resize(() => {
    resizeImg();
    resizeImg2();
});

let resizeImg = () => {
    let thum = $(".visual .thum");

    Array.from(thum).forEach((e)=>{
        e.style.width= 400+'px';
        e.style.height= 400+'px';
       
    });
}

let resizeImg2 = () => {
    let thum1 = $(".visual2 .thum1");
    Array.from(thum1).forEach((e)=>{
        e.style.width= 400+'px';
        e.style.height= 400+'px';
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
	


$('.eats-list').hover((e) => {
    e.currentTarget.children[0].children[0].style.transform='scale(1.1)';
    e.currentTarget.children[0].children[0].style.transitionDuration='0.5s';
  }, (e)=>{
    e.currentTarget.children[0].children[0].style.transform='scale(1)';
});

