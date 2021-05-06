$(function() {

	var s = "";
	$.ajax({
		credentials : "include",
		type:"get",
		url:"http://112.169.196.76:47788/stores/list",
		dataType:"json",
		success:function(d){
			/*console.log(d);*/
			$.each(d, function(i, elt) {
				
				s += "<div class='store-block'>";
				/*s += "<img src='"+elt[0].storeImage+"'>";*/
				s += "<div>"+elt[0].storeName+"<div>";
				s += "<div>";
				
			});
			$("#main-storelist").html(s);
		}
	});
	
	$(document).on("click","#main-storelist",function(){
		alert("가게 상세페이지 로 들어가지는거 ");
		$(location).attr("href","storecus.html");
	})
});

