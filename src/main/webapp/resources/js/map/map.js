/**
 * 
 */
 let viewTimeline = (reviewId) => {
			$.ajax({
				type: 'GET',
				url: '/timeline/detail',
				data:{ id:reviewId },
				dataType: 'json',
				success: (data) => {
					photoReviewCnt = 0;
					$('#pop-review-detail .slide-img').css('transform','translateX(0px)');
					//사진
					let html = '';
					for (var i = 0; i < data.files.length; i++) html += '<li><img src="'+data.files[i]+'"></li>';
					$('#pop-review-detail .slide-img').html(html);
					html = '';
					for (var i = 0; i < data.files.length; i++) html += '<div class="dot" data-num="'+i+'"></div>';
					$('#pop-review-detail .dot-btn').html(html);
					$('#pop-review-detail .dot-btn > div:first-child').addClass('selected');
					
					//사진 슬라이드 버튼
					if(data.files.length > 1) {
						$('#pop-review-detail .slide-btn').html(
							'<i onclick="prevPhoto('+data.files.length+');" class="fas fa-arrow-circle-left"></i>'+
							'<i onclick="nextPhoto('+data.files.length+');" class="fas fa-arrow-circle-right"></i>');
					} else{
						$('#pop-review-detail .slide-btn').html('');
					}
					
					//작성자
					$('#pop-review-detail .writer').html('<a href="member/follow/'+data.memberId+'">'+data.review.memberNick+'</a><a onclick="follow(\''+data.memberId+'\')" class="follow">잇친맺기</a>');
					
					//별점
					html = '<p>맛</p>';
					for (var i = 0; i < 5; i++) {
						if(i < data.review.taste) html += '<i class="fas fa-star">';
						else html += '<i class="far fa-star"></i>';
					}
					$('#pop-review-detail .score > div:first-child').html(html);
					html = '<p>청결</p>';
					for (var i = 0; i < 5; i++) {
						if(i < data.review.clean) html += '<i class="fas fa-star">';
						else html += '<i class="far fa-star"></i>';
					}
					$('#pop-review-detail .score > div:nth-child(2)').html(html);
					html = '<p>서비스</p>';
					for (var i = 0; i < 5; i++) {
						if(i < data.review.service) html += '<i class="fas fa-star">';
						else html += '<i class="far fa-star"></i>';
					}
					$('#pop-review-detail .score > div:last-child').html(html);
					
					//해시태그
					html = '';
					for (var i = 0; i < data.review.hashtag.length; i++) html += '<span>#'+data.review.hashtag[i]+'</span>';
					$('#pop-review-detail .review > .tag').html(html);
					
					//리뷰
					$('#pop-review-detail .review > p').html(data.review.review);
					
					//음식점정보&카테고리
					$('#pop-review-detail .info > .title').html('<i class="fas fa-home"></i> '+data.review.resName+' ('+data.review.category+')');
					$('#pop-review-detail .info > .location').html(data.review.addr);
					
					//세부버튼
					$('#pop-review-detail .pop-btn-edit').attr('onclick','editReview(\''+reviewId+'\');');
					$('#pop-review-detail .pop-btn-delete').attr('onclick','deleteReview(\''+reviewId+'\');');

					$('#pop-review-detail').fadeIn(200);
					resizeSlideImgHeight();
				},
				error: function (e) {
					alert('에러발생');
				}
			});
		}
		
		//사진 슬라이드
		let movePhoto = () => {
			$('#pop-review-detail .dot-btn > div').removeClass('selected');
			$('#pop-review-detail .dot-btn > div[data-num="'+photoReviewCnt+'"]').addClass('selected');
			let slideWidth = $('#pop-review-detail .slide-img img').width();
			$('#pop-review-detail .slide-img').css('transform','translateX('+(-slideWidth*photoReviewCnt)+'px)');
		}
		
		let nextPhoto = (length) => {
			if(photoReviewCnt == length-1) return;
			if(photoReviewCnt == length-2) $('#pop-review-detail .slide-btn i.fa-arrow-circle-right').hide();
			photoReviewCnt++;
			movePhoto();
			$('#pop-review-detail .slide-btn i.fa-arrow-circle-left').show();
		}
		
		let prevPhoto = (length) => {
			if(photoReviewCnt == 0) return;
			if(photoReviewCnt == 1) $('#pop-review-detail .slide-btn i.fa-arrow-circle-left').hide();
			photoReviewCnt--;
			movePhoto();
			$('#pop-review-detail .slide-btn i.fa-arrow-circle-right').show();
		}
		
		//리뷰수정&삭제
		let editReview = (reviewId) => {
			$('#pop-review-detail').hide();
			editFlag = true;
			uploadReview(reviewId);
		}
		
		let deleteReview = (reviewId) => {
			$('#pop-review-detail').hide();
			$.ajax({
				type: 'POST',
				url: '/timeline/delete?id='+reviewId,
				contentType: false,
				processData: false,
			 	cache:false,
				success: () => {
					alert("성공");
					location.reload();
				},
				error: (e) => {
					alert("실패");
				}
			});
		}