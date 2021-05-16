/* customer 상품 리스트  */
$(document).on("click",".main-storelist",function(){
	/*console.log($(this).attr("data-value"));*/
	var storeId = $(this).attr("data-storeId");
	var storeName = $(this).attr("data-storeName");



	/*가게 상세 상품 목록 등장 */
	$.ajax({
		type:"get",
		url:config.domain + "/products/list/"+storeId,
		dataType:"json",
		success:function(data){
			console.log(data);
			localStorage.setItem("product-list",JSON.stringify(data));
			var z="";
				z+="<form>";
				z+="<input type='hidden' value='"+data.data.productId+"'>";
				z+="<table class='table table-bordered'>";
				z+="<br><br><h2>상품 리스트</h2><hr>";
				z+="<tr style='font-size:15pt;'><th>상품명</th><th>상품가격</th><th>상품 정보</th><th colspan='2'>상품 이미지</th></tr>";
				$.each(data.data, function(i,elt){
					z+="<tr>";
					z+="<td>"+elt.productName+"</td>";
					z+="<td>"+elt.productPrice+"</td>";
					z+="<td>"+elt.productDesc+"</td>";
					// z+="<td>"+elt.productImage+"</td>";
					z+="<td><img alt='상품이미지' src="+elt.productImage+"></td>";
					z+="<td><button class='cus-cart-add btn btn-default' type='button' add_product='"+JSON.stringify(elt)+"', add_storeName='"+storeName+"'><span class='fas fa-2x fa-cart-arrow-down'></span></button></td></tr>";
			});
			z+="</table>";
			z+="</form><br><br><br><br><br><br>";

			c = "<div id='index-main-first'></div>";
			c +="<div id='index-main-second'></div>";
			/*장바구니 이동하는 버튼 여기밖에 ??*/
			c +="<div id='index-main-third'></div>";
			$("#index-main").html(c);
			$.ajax({
				type:"get",
				url:config.domain + "/stores/"+storeId,
				dataType:"json",
				success:function(data){
					/*매장 소개*/
					var s="";
					s+="<h1><b>"+data.data.storeName+"</b></h1><hr>";
					s+="<div id='ssss' data-store='"+storeId+"' data-storeName='"+data.data.storeName+"'></div>";
					s+="<table class='table table-bordered'>";
					s+="<tr><td style='font-size:15pt;'>매장소개</td><td>"+data.data.storeDesc+"</td></tr>";
					// s+="<tr><td style='font-size:15pt;'>매장사진</td><td>"+data.data.storeImage+"</td></tr>";
					s+="<tr><td style='font-size:15pt;'>매장사진</td><td><img alt='매장사진' src="+data.data.storeImage+"></td></tr>";
					s+="<tr><td style='font-size:15pt;'>매장주소</td><td>"+data.data.storeAddr+"</td></tr></table>";

					$("#index-main-first").html(s);
					reviewPage(storeId, storeName);
				}
			});
			$("#index-main-second").html(z);
		}
		
	});




});

$(document).on("click",".cus-cart-add",function(){
	var product = JSON.parse($(this).attr("add_product"));
	var storeName = $(this).attr("add_storeName");
	add(product, storeName);
})