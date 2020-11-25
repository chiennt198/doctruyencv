//メニュー固定
//$(window).on('load',function () {
//
//	var mainArea = $(".contents");
//	var sideWrap = $("#menu");
//	var sideArea = $(".menu-box_left");
//
//	var wd = $(window); 
//	
//	var mainH = mainArea.height();
//	var sideH = sideWrap.height();
//	
//	
//	if(sideH < mainH) { 
//		sideWrap.css({"height": mainH,"position": "relative"});
//		var sideOver = wd.height()-sideArea.height();
//		var starPoint = sideArea.offset().top + (-sideOver);
//		var breakPoint = sideArea.offset().top + mainH;
//
//		wd.on('scroll',function() {
//			
//			if(wd.height() < sideArea.height()){
//				if(starPoint < wd.scrollTop() && wd.scrollTop() + wd.height() < breakPoint){
//					sideArea.css({"position": "fixed", "bottom": "20px"}); 
//	
//				}else if(wd.scrollTop() + wd.height() >= breakPoint){
//					sideArea.css({"position": "absolute", "bottom": "0"});
//	
//				} else {
//					sideArea.css("position", "static");
//	
//				}
//	
//			}else{
//			
//				var sideBtm = wd.scrollTop() + sideArea.height();
//				
//				if(mainArea.offset().top < wd.scrollTop() && sideBtm < breakPoint){
//					sideArea.css({"position": "fixed", "top": "10px"});
//					
//				}else if(sideBtm >= breakPoint){
//				
//					var fixedSide = mainH - sideH;
//					
//					sideArea.css({"position": "absolute", "top": fixedSide});
//					
//				} else {
//					sideArea.css("position", "static");
//				}
//			}
//				
//		
//		});
//	
//	} 
//
//});
//アンカースクロール
$(function(){
	// #で始まるアンカーをクリックした場合に処理
	$('a[href^=#]').click(function() {
		// スクロールの速度
		var speed = 400; // ミリ秒
		// アンカーの値取得
		var href= $(this).attr("href");
		// 移動先を取得
		var target = $(href == "#" || href == "" ? 'html' : href);
		// 移動先を数値で取得
		var position = target.offset().top;
		// スムーススクロール
	$('body,html').animate({scrollTop:position}, speed, 'swing');
return false;
});
});

// 画像入れ替え
$(function(){
     $('a img').hover(function(){
        $(this).attr('src', $(this).attr('src').replace('_off', '_on'));
          }, function(){
             if (!$(this).hasClass('currentPage')) {
             $(this).attr('src', $(this).attr('src').replace('_on', '_off'));
        }
   });
});

//ページトップへ
$(function() {
	var showFlug = false;
	var topBtn = $('#pagetop');	
	topBtn.css('bottom', '-100px');
	var showFlug = false;
	$(window).scroll(function () {
		if ($(this).scrollTop() > 100) {
			if (showFlug == false) {
				showFlug = true;
				topBtn.stop().animate({'bottom' : '20px'}, 200); 
			}
		} else {
			if (showFlug) {
				showFlug = false;
				topBtn.stop().animate({'bottom' : '-100px'}, 200); 
			}
		}
	});
	//スクロールしてトップ
	topBtn.click(function () {
		$('body,html').animate({
			scrollTop: 0
		}, 500);
		return false;
	});
});
//遷移先選択
$(document).ready(function(){
	$('#target').bind('change', function() {
		var linkurl = $('#target').val();
		$('#link').attr({href:linkurl});
	});

});
