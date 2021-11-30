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
    let label = e.currentTarget.parentNode;
    label.classList.toggle('checked');
});

//페이징
let timelinePageCnt = 1;
document.addEventListener('scroll', function() {
    if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
    	$.ajax({
			type: 'POST',
			url: '/timeline/?page='+(timelinePageCnt+1),
			dataType: 'json',
			contentType: false,
			processData: false,
		 	cache:false,
			success: (data) => {
				if(data.length == 0) return;
				timelinePageCnt++;
				let html = '';
				for(i = 0; i < data.length; i++) {
		    		html += '<li onclick="viewTimeline(\'' + data[i].reviewId + '\')">'
		    			  + '	<div class="eats-list">'
		    			  + '		<div class="thum">'
		    			  + '			<img src="'+ data[i].review.thumUrl +'">'
		    			  + '		</div>'
		    			  + '		<div class="info">'
		    			  + '			<div class="eats-location">'
		    			  + 				data[i].review.addr.split(' ')[0] + data[i].review.addr.split(' ')[1]
		    			  + 				' > ' + data[i].review.category
		    			  + '</div>'
		    			  + '			<div class="eats-name">' + data[i].review.resName;
		    		if(data[i].review.like > 0) {
		    			html += '			<i data-like="' + data[i].reviewId + '" class="eats-like fas fa-heart"></i>';
		    		} else{
		    			html += '			<i data-like="' + data[i].reviewId + '" class="eats-like far fa-heart"></i>';
		    		}
		    		html += '			</div>'
		    			  + '			<div class="eats-tag">';
	    		 	for(j = 0; j < data[i].review.hashtag.length; j++) {
	    		 		html += '				<span>#' + data[i].review.hashtag[j] + '</span>';
	    		 	}
		    		html += '			</div>'
		    			  + '		<div class="eats-score">'
		    			  + '			<i class="fas fa-star"></i>' + ((data[i].review.taste+data[i].review.clean+data[i].review.service)/3).toFixed(1)
		    			  + '		</div>'
		    			  + '	</div>'
		    			  + '</li>';
		    	} //for-end
				$('.timeline-brd').append(html);
				resizeImg();
			},
			error: (e) => {
				alert("실패");
			}
		});
    }
});