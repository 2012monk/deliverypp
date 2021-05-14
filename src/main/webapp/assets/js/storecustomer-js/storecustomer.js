/* customer 상품 리스트  */
function storeCustomerProductList() {
	$(document).on("click",".main-storelist",function(){
		/*console.log($(this).attr("data-value"));*/
		var storeId = $(this).attr("data-value");
		/*가게 상세 상품 목록 등장 */
		$.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/products/list/"+storeId, 
            dataType:"json",
            success:function(data){
				console.log(data);
                localStorage.setItem("product-list",JSON.stringify(data));
                var z="";
                    z+="<form>";
                    z+="<input type='hidden' value='"+data.data.productId+"'>";
                    z+="<table>";
                    z+="<caption>상품 리스트</caption>";
                    z+="<tr><th>가게ID</th><th>상품ID</th><th>상품명</th><th>상품가격</th><th>상품 정보</th><th>상품 이미지</th></tr>";
                    $.each(data.data, function(i,elt){
                        z+="<tr><td>"+elt.storeId+"</td>";
						z+="<td>"+elt.productId+"</td>";
						z+="<td>"+elt.productName+"</td>";
						z+="<td>"+elt.productPrice+"</td>";
						z+="<td>"+elt.productDesc+"</td>";
						z+="<td>"+elt.productImage+"</td>";
						z+="<td><button type='button' onClick='add("+JSON.stringify(elt)+")'>add</button></td></tr>";
                });
                z+="</table>"; 
                z+="</form><br><br><br><br><br><br>";
            	
				c = "<div id='index-main-first'></div>";
				c +="<div id='index-main-second'></div>";
				/*장바구니 이동하는 버튼 여기밖에 ??*/
				c +="<div id='index-main-third'><button id='basket-movepage' style='btn btn-lg'>장바구니 이동 하는 버튼 </button></div>";
			  	$("#index-main").html(c);
				
				$("#index-main-second").html(z);
            }
			
		});
		
		/*매장 소개*/
		$.ajax({
	        type:"get",
	        url:"http://112.169.196.76:47788/stores/"+storeId, 
	        dataType:"json",
	        success:function(data){
	            var s="";
	            s+="<b>매장소개</b>";
	            s+="<div id='ssss' data-store='"+storeId+"'>매장명 : "+data.data.storeName+"</div>";
	            s+="<div>매장소개 : "+data.data.storeDesc+"</div>";
	            s+="<div>매장사진 : "+data.data.storeImage+"</div>";
	            s+="<div>매장주소 : "+data.data.storeAddr+"</div>";
	        
	           	$("#index-main-first").html(s);
				reviewPage();
	        }
	    });
		
	});
}