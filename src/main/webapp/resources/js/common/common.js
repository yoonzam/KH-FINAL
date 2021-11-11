$(document).ready(()=>{
    getSectionMargin();
    resizeSlideImgHeight();
});

$(window).resize(() => {
    getSectionMargin();
    resizeSlideImgHeight();
});

$('.gnb li').hover((e)=> {
    e.target.style.color='#fa8633';
    e.target.style.transitionDuration='0.2s';
  }, (e)=>{
    e.target.style.color='#333';
});

let getSectionMargin = () => {
    $('section').css('padding-top',$('header').height());
}

let resizeSlideImgHeight = () => {
    let slideImgWidth = $('.layer-popup .slide-img-wrap').width();
    $('.layer-popup .slide-img').height(slideImgWidth);
    $('.layer-popup .slide-img li').height(slideImgWidth);
    $('.layer-popup .info-wrap').height(slideImgWidth);
    $('.layer-popup .slide-btn').css('margin-top',(slideImgWidth/2-18)+'px');
}

/* popup btn */
$('.layer-popup .view-controller a').hover((e) => {
    $('.view-controller span').html('');
    let className = e.currentTarget.getAttribute('class');
    let btnName = '';
    if(className === 'pop-btn-my-list'){
        btnName = '맛찜하기';
    } else if(className === 'pop-btn-calendar'){
        btnName = '캘린더 추가';
    } else if(className === 'pop-btn-edit'){
        btnName = '수정하기';
    } else{
        btnName = '삭제하기';
    }
    $('.view-controller span').html(btnName);
  }, (e)=>{
    $('.view-controller span').html('');
});

$('.dimmed').click(()=>{
    closePopup();
});
let closePopup = () => {
    $('.dimmed-wrap').hide();
}