$(function() {
	$(document).on("click",".storename",function(){
		var s = "";
		$.ajax({
			type:"get",
			url:"http://112.169.196.76:47788/store/list",
			dataType:"json",
			success:function(d){
				console.log(d);
				$.each(d, function(i, elt) {
					
					s += "<div class='storelist'>";
					s += "<div class='wrapper'>";
					s += "<div class='storename'> "+elt.storeName+"</div>";
					s += "<div > "+elt.storeDesc+"</div>";
					s += "</div>";
					s += "</div>";
				});
				$("#mainpage").html(s);
			}
		})
	});
	
});