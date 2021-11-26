<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dimmed-wrap" id="pop-review-detail" style="display:none;">
	<div class="dimmed"></div>
	<div class="layer-popup review">
		<div class="content view">
			<div class="slide-img-wrap">
				<ul class="slide-img">
				</ul>
				<div class="dot-btn">
					<div class="dot selected"></div>
					<div class="dot"></div>
					<div class="dot"></div>
					<div class="dot"></div>
					<div class="dot"></div>
				</div>
				<div class="slide-btn">
				</div>
			</div>
			<ul class="info-wrap">
				<li class="writer"></li>
				<li class="score">
					<div></div>
					<div></div>
					<div></div>
				</li>
				<li class="review">
					<div class="tag"></div>
					<p></p>
				</li>
				<li class="info">
					<div class="title"><i class="fas fa-home"></i>&nbsp;</div>
					<a class="location"></a>
				</li>
				<li class="view-controller">
					<span></span>
					<a class="pop-btn-my-list"><i class="fas fa-heart"></i></a>
					<a class="pop-btn-calendar"><i class="far fa-calendar-plus"></i></a>
					<a class="pop-btn-edit"><i class="fas fa-edit"></i></a>
					<a class="pop-btn-delete"><i class="fas fa-trash-alt"></i></a>
				</li>
			</ul>
		</div>
	</div>
	<a class="close-btn" onclick="closePopup();"><i class="fas fa-times"></i></a>
</div>