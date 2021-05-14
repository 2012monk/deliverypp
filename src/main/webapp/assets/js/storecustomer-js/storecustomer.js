/* customer 상품 리스트  */
$(document).on("click",".main-storelist",function(){
	/*console.log($(this).attr("data-value"));*/
	var storeId = $(this).attr("data-storeId");
	var storeName = $(this).attr("data-storeName");

	

	/*가게 상세 상품 목록 등장 */
	$.ajax({
		type:"get",
		url:"http://deli.alconn.co/products/list/"+storeId, 
		dataType:"json",
		success:function(data){
			console.log(data);
			localStorage.setItem("product-list",JSON.stringify(data));
			var z="";
				z+="<form>";
				z+="<input type='hidden' value='"+data.data.productId+"'>";
				z+="<table class='table table-bordered'>";
				z+="<hr><br><br><h2>상품 리스트</h2>";
				z+="<tr style='font-size:15pt;'><th>상품명</th><th>상품가격</th><th>상품 정보</th><th>상품 이미지</th></tr>";
				$.each(data.data, function(i,elt){
					z+="<tr>";
					z+="<td>"+elt.productName+"</td>";
					z+="<td>"+elt.productPrice+"</td>";
					z+="<td>"+elt.productDesc+"</td>";
					z+="<td>"+elt.productImage+"</td>";
					z+="<td><button class='btn btn-default' type='button' onClick='add("+JSON.stringify(elt)+","+storeName+")'><span class='fas fa-2x fa-cart-arrow-down'></span></button></td></tr>";
			});
			z+="</table>"; 
			z+="</form><br><br><br><br><br><br>";
			
			c = "<div id='index-main-first'></div>";
			c +="<div id='index-main-second'></div>";
			/*장바구니 이동하는 버튼 여기밖에 ??*/
			c +="<div id='index-main-third'><button id='basket-movepage' style='btn btn-lg'>장바구니 이동 하는 버튼 </button></div>";
			$("#index-main").html(c);
			$.ajax({
				type:"get",
				url:"http://deli.alconn.co/stores/"+storeId, 
				dataType:"json",
				success:function(data){
					var s="";
					s+="<h1><b>"+data.data.storeName+"</b></h1><hr>";
					s+="<div id='ssss' data-store='"+storeId+"' data-storeName='"+data.data.storeName+"'></div>";
					s+="<table class='table table-bordered'>";
					s+="<tr><td style='font-size:15pt;'>매장소개</td><td>"+data.data.storeDesc+"</td></tr>";
					s+="<tr><td style='font-size:15pt;'>매장사진</td><td>"+data.data.storeImage+"</td></tr>";
					s+="<tr><td style='font-size:15pt;'>매장주소</td><td>"+data.data.storeAddr+"</td></tr></table>";
				
					$("#index-main-first").html(s);
					reviewPage(storeId, storeName);
				}
			});
			$("#index-main-second").html(z);
		}
		
	});
	
	/*매장 소개*/
	
	
});

