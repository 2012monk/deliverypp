$(function() {
	var s = "";
	$.ajax({
		credentials : "include",
		type:"get",
		url:"http://deli.alconn.co/stores/list",
		dataType:"json",
		success:function(d){
			/*console.log(d);*/
			$.each(d.data, function(i, elt) {
				console.log(elt.storeName);
				console.log(i);
				s += "<a href='storecus.html' storename='"+elt.storeId+"'>";
				s += "<div class='store-block'>";
				s += "<img src='"+elt.storeImage+"'>";
				s += "<div>"+elt.storeName+"<div>";
				s += "<div>";
				s +="</a>"
				
			});
			$("#main-storelist").html(s);
		}
	});
});

