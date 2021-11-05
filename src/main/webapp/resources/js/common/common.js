$(document).ready(()=>{
    getMarginSec();
});

$(window).resize(() => {
    getMarginSec();
});

$('.gnb li').hover((e) => {
    e.currentTarget.style.color='#fa8633';
    e.currentTarget.style.transitionDuration='0.2s';
  }, (e)=>{
    e.currentTarget.style.color='#333';
});

$('#btnReview').hover((e) => {
    e.currentTarget.style.backgroundColor='#fa5433';
    e.currentTarget.style.transitionDuration='0.5s';
  }, (e)=>{
    e.currentTarget.style.backgroundColor='#fa8633';
});

let getMarginSec = () => {
    $('section').css('padding-top',$('header').height());
}
