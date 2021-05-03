$(function(){
	var storeName = $("input#storeName").val();
	$.ajax({
		credentials : "include",
		type:"get",
		dataType:"json",
		url:"http://112.169.196.76:47788/store/list",
		data:{"storeName":storeName},
		success:function(d){
			console.log(d);
			
			var s="";
			s+= "<table class='table table-bordered'>";
			$.each(d, function(idx, elt) {

				s += "<tr>";
				s += "<td>메뉴명:"+elt.storeName+"</td>";
				s += "<td>메뉴설명:"+elt.storeDesc+"</td>";
				s += "<td>가격:"+elt.storeId+"</td>";
				s += "</tr>";
			})
			s+= "</table>";
			$("div#menu_list").html(s);
		}
	})
})