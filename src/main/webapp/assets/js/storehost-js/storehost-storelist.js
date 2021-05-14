$(function(){
	//가게 리스트 출력 
	var storeId = $(this).attr("value");
	$.ajax({
		type:"get",
		url:"http://deli.alconn.co/stores/list",
		dataType:"json",
		success:function(data){
			var s="";
				s+="<table>";
				s+="<caption><b>가게 상세보기</b></caption>";
				s+="<tr><th>가게ID</th><th>가게명</th><th>가게 정보</th><th>가게 이미지</th><th>상품리스트</th><th>가게 주소</th></tr>";
				$.each(data.data, function(i,elt){
				    s +="<tr><td name='storeId' value='"+elt.storeId+"'>"+elt.storeId+"</td><td name='storeName' value='"+elt.storeName+"'>"+elt.storeName+"</td><td name='storeDesc'>"+elt.storeDesc+"</td><td name='storeImage'>"+elt.storeImage+"</td><td name=''>"+elt.productList+"</td><td name='storeAddr'>"+elt.storeAddr+"</td>";
				    s +="<td><button type='button' class='storelist-btn-delete' value='"+elt.storeId+"'>delete</button></td>";
				    s +="<td><button type='button' class='storelist-btn-update' value='"+elt.storeId+"'>update</button></td><tr>";
			});
				s+="</table>"; 
			
			$("#storehost-storelist").html(s);
			
		}
	});
});
