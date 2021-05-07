$(function(){
	$.ajax({
		type:"get",
		url:"http://deli.alconn.co/products/list/stid2",
		dataType:"json",
		success:function(data){
			var s="";
				s+="<table>";
				s+="<caption>상품 리스트</caption>";
				s+="<tr><th>상품명</th><th>상품가격</th><th>상품 정보</th><th>상품 이미지</th></tr>";
				$.each(data.data, function(i,elt){
				    s+="<tr><td>"+elt.productName+"</td><td>"+elt.productPrice+"</td><td>"+elt.productDesc+"</td><td>"+elt.productImage+"</td><td><button type='button' onClick='cartLoad("+ JSON.stringify(elt)+")>add</button> </td><tr>";
			});
				s+="</table>"; 
			
			$("#storecus-productlist").html(s);
		}
	});
});