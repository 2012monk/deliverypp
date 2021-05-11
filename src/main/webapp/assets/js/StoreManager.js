/**
Store JSON 포맷
{
    "message": "null",
    "data": {
        "storeId": "stid3",
        "storeName": "두두다",
        "storeDesc": "라라라라랄라라라라",
        "storeImage": null,
        "productList": [],
        "storeAddr": null
    }
}


Order JSON 포맷
{
    "address" : "address",
    "telephone" : "telephone",
    "orderRequirement" : "orderRequirement",
	"paymentInfo" : "paymentInfo",
    "storeId": "storeId",
    "storeName": "storeName",
    "quantity" : "quantity",
    "totalPrice": "totalPrice",
    "orderList": [
      {
        "productId": "pid2",
        "productName": "아이템3421",
        "productImage": null,
        "storeId": "stid2",
        "productPrice": "15000",
        "productDesc": null
      },
      {
        "productId": "pid4",
        "productName": "섬띵",
        "productImage": null,
        "storeId": "stid2",
        "productPrice": "80000",
        "productDesc": null
      }
    ]

  }
 * 
 */


function orderListPage(userEmail){
	// userEmail 계정에서 소유한 주문리스트 샘플데이터
	var order_list = {
		"order1":
	{
		"orderId" : "orderfirst",
	    "address" : "address",
	    "telephone" : "telephone",
	    "orderRequirement" : "orderRequirement",
		"paymentInfo" : "paymentInfo",
	    "storeId": "storeId",
	    "storeName": "storeName",
	    "quantity" : "quantity",
	    "totalPrice": "totalPrice",
	    "orderList": 
		[
		    {
		        "productId": "pid2",
		        "productName": "아이템3421",
		        "productImage": null,
		        "storeId": "stid2",
		        "productPrice": "15000",
		        "productDesc": null
		    },
    		{
		        "productId": "pid4",
		        "productName": "섬띵",
		        "productImage": null,
		        "storeId": "stid2",
		        "productPrice": "80000",
		        "productDesc": null
   			}
		]

	},
		"order2":
	{
	"orderId" : "ordersecond",
    "address" : "address2",
    "telephone" : "telephone2",
    "orderRequirement" : "orderRequirement2",
	"paymentInfo" : "paymentInfo2",
    "storeId": "storeId2",
    "storeName": "storeName2",
    "quantity" : "quantity2",
    "totalPrice": "totalPrice2",
    "orderList": [
      {
        "productId": "aaa",
        "productName": "bbb",
        "productImage": null,
        "storeId": "ccc",
        "productPrice": "1700",
        "productDesc": null
      },
      {
        "productId": "ddd",
        "productName": "eee",
        "productImage": null,
        "storeId": "ccc",
        "productPrice": "8600",
        "productDesc": null
      }
    ]

    }
	}; // ajax에서 받아온 주문 리스트
	function orderList(order_list)
	{
		var s = "";
		for(var order in order_list)
		{
			console.log(JSON.stringify(order_list[order]));
			s += "<table class='table table-bordered'>";
			s += "<tr><td>상품명</td><td>가격</td><td>수량</td>";
			s += "</tr>";
			var product_list = order_list[order].orderList;
			for(var product in product_list)
			{
				var product_name = product_list[product].productName;
				var price = product_list[product].productPrice;
				//var entity = order_list[order][product].entity;
				var entity = "x";
				s+= "<tr>";
				s+= "<td>"+product_name+"</td>";
				s+= "<td>"+price+"</td>";
				s+= "<td>"+entity+"</td>";
				s+= "</tr>";
			}
			
			s+= "<tr><td>총 주문액 : </td><td>";
			s+= order_list[order].totalPrice + "</td></tr>";
			s+= "<tr><td>주소</td><td>"+order_list[order].address+"</td></tr>";
			s+= "<tr><td>연락처</td><td>"+order_list[order].telephone+"</td></tr>";
			s+= "<tr><td>요청사항</td><td>"+order_list[order].orderRequirement+"</td></tr>";
			s+= "<tr><td>결제수단</td><td>"+order_list[order].paymentInfo+"</td></tr></table>";

		}
	
		// 주문리스트 띄울 div 태그 지정
		$("body").html(s);
	}

	orderList(order_list);
}

$(function(){

//주문리스트
$("button#order").click(function(){
	// ajax에서 데이터 받아와서 처리
	/*
	$.ajax({
		type:"post",
		dataType:"json",
		url:"http://112.169.196.76:47788/",
	})
	
	
	*/
	
	
	
})


})
