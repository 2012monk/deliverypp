$(function(){
	//가게 리스트 출력 
	var storeId = $(this).attr("value");
	$.ajax({
		type:"get",
		url:"http://112.169.196.76:47788/stores/list",
		dataType:"json",
		success:function(data){
			var s="";
				s+="<table>";
				s+="<caption><b>가게 리스트</b></caption>";
				s+="<tr><th>가게ID</th><th>가게명</th><th>가게 정보</th><th>가게 이미지</th><th>상품리스트</th><th>가게 주소</th></tr>";
				$.each(data.data, function(i,elt){
				    s +="<tr><td>"+elt.storeId+"</td><td>"+elt.storeName+"</td><td>"+elt.storeDesc+"</td><td>"+elt.storeImage+"</td><td>"+elt.productList+"</td><td>"+elt.storeAddr+"</td>";
				    s +="<td><button type='button' class='storelist-btn-delete' value='"+elt.storeId+"'>delete</button></td>";
				    s +="<td><button type='button' id='storelist-btn-update"+i+"' value='"+elt.storeId+"'>update</button></td><tr>";
			});
				s+="</table>"; 
			
			$("#storehost-storelist").html(s);
			
		}
	});
});
