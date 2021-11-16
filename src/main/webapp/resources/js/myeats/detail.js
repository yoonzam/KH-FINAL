$(document).ready(()=>{
    resizeImg();
});

$(window).resize(() => {
    resizeImg();
});

let resizeImg = () => {
    let thum = $(".detail-brd .thum");
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

