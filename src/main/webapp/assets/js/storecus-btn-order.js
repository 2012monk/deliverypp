$(function(){
	s ="";
	s += "<button id='btn-back'>뒤로가기</button>";
	s += "<button id='btn-order'>주문하기</button>";
	
	$("#storecus-btn-order").html(s);
	
});

$(document).on("click","#btn-back",function(){
	alert("뒤로가기");
	$(location).attr("href","main.html");
})

$(document).on("click","#btn-order",function(){
	alert("주문하기");
	$(location).attr("href","basket.html");
})