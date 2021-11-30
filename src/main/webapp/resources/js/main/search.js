$(document).ready(()=>{
    resizeImg();
});

$(window).resize(() => {
    resizeImg();
});

let resizeImg = () => {
    let thum = $(".timeline-brd .thum");
    let thumWidth = thum.width();
    Array.from(thum).forEach((e)=>{
        e.style.height=thumWidth+'px';
    });
}

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

/*
function clickLike(id, like){
	if(like == 0){
		let reviewId = document.getElementById(id);
		let like = reviewId.style.color ="blue"
		
		$.ajax({
		    url		:	'/main/search', //request 보낼 서버의 경로
		    type	:	'post', 
		    data:{	
				done: like,
				id	: id,
			}, //보낼 데이터
		    success: function(data) {
		        //서버로부터 정상적으로 응답이 왔을 때 실행
				alert("클릭완")
				
		    },
		    error: function(err) {
		        //서버로부터 응답이 정상적으로 처리되지 못햇을 때 실행
	
		    }
		});	
	}else{
		let reviewId = document.getElementById(id);
		let clicked = reviewId.style.color ="none"
		
		$.ajax({
		    url		:	'/main/search', //request 보낼 서버의 경로
		    type	:	'post', 
		    data:{	
				like: like,
				id	: id,
			}, //보낼 데이터
		    success: function(data) {
		        //서버로부터 정상적으로 응답이 왔을 때 실행
				alert("클릭완")
				
		    },
		    error: function(err) {
		        //서버로부터 응답이 정상적으로 처리되지 못햇을 때 실행
	
		    }
		});	
	}



}


*/

