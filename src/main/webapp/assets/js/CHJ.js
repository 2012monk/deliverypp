$(function(){
	var storeName = $("input#storeName").val();
	$.ajax({
		type:"get",
		dataType:"json",
		url:"http://deli.alconn.co/stores/list",
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
//sdafadsklfjdsalfjkasdlkdjsaklfjksljkfdsa