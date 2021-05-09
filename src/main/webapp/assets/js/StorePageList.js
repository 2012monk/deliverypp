$(function(){

	$(document).on("click","button.cart-add",function(){
		console.log("추가버튼");
		var cart_list = JSON.parse(localStorage.getItem("cartList"));
		var product = $(this).val();

		var cart = {};
		cart.productName = product;
		cart.productPrice = cart_list[product].productPrice;
		CartAdd(cart);
		CartMain();
	})
	
	$(document).on("click","button.cart-delete",function(){
		console.log("삭제버튼");
		var cart_list = JSON.parse(localStorage.getItem("cartList"));
		var product = $(this).val();

		var cart = {};
		cart.productName = product;
		cart.productPrice = cart_list[product].productPrice;
		CartRemoveOne(cart);
		CartMain();
		//alert(product_name+"_"+price+"_"+entity);
	})
	
	$(document).on("click","button.cart-delete-line",function(){
		console.log("한줄삭제버튼");
		var cart_list = JSON.parse(localStorage.getItem("cartList"));
		var product = $(this).val();

		var cart = {};
		cart.productName = product;
		cart.productPrice = cart_list[product].productPrice;
		CartRemoveLine(cart);
		CartMain();
		//alert(product_name+"_"+price+"_"+entity);
	})
	
	$(document).on("click","button.cart-clear",function(){
		console.log("장바구니비움버튼");
		CartRemoveAll();
		alert("장바구니를 비웠습니다");
	})
	
	$(document).on("click","button.cart-order",function(){
		console.log("장바구니 결제하기 버튼");
		var cart_pay = {}
		cart_pay.storeId = localStorage.getItem("cartStoreId");
		cart_pay.storeName = localStorage.getItem("cartStore");
		//quantity 계산 할 것
		
		var cart_local_list = JSON.parse(localStorage.getItem("cartList"));
		var cart_pay_list = [];
		var cart_count = 0;
		for(var one in cart_local_list)
		{
			cart_pay_list.push(cart_local_list[one]);
			cart_count += parseInt(cart_local_list[one].entity);
		}
		cart_pay.quantity = cart_count.toString();
		cart_pay.totalPrice = localStorage.getItem("cartPrice");
		cart_pay.orderList = cart_pay_list;
		cart_pay.address = $(this).parent().find("#address").val();
		cart_pay.telephone = $(this).parent().find("#telephone").val();
		cart_pay.orderRequirement = $(this).parent().find("#orderRequirement").val();
		cart_pay.paymentType = $(this).parent().find("#paymentType").val();
		
		alert(JSON.stringify(cart_pay));
		
		$.ajax({
			type:"post",
			// url:"http://112.169.196.76:47788/order",
			url:"http://localhost:47788/order",
			data:JSON.stringify(cart_pay),
			dataType:"json",
			success:function(data){
				//alert("성공:"+JSON.stringify(data)+"__"+data.data.redirect_url);
				var url = data.data.redirect_url;
				// window.open(url);
				// location.href=url;
				kakaoPopUp(url)
			}
		})
		
	})
	
	
	
	$("button#myBtn").click(function(){
		console.log("로그인버튼");
		CartMain();
	})



//상품리스트
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
				    s+="<tr><td>"+elt.productName+"</td><td>"+elt.productPrice+"</td><td>"+elt.productDesc+"</td><td>"+elt.productImage+"</td><td><button type='button' onClick='add("+JSON.stringify(elt)+")'>add</button></td><tr>";
			});
				s+="</table>"; 
			
			$("#storecus-productlist").html(s);
		}
	});
	
	$.ajax({
		type:"get",
		url:"http://deli.alconn.co/stores/stid3",
		dataType:"json",
		success:function(data){
			var s="";
				s+="<div>가게소개</div>"
				s+="<div>가게이름 : "+data.data.storeName+"</div>";
				s+="<div>가게주소 : "+data.data.storeAddr+"</div>";
				s+="<div>가게소개 : "+data.data.storeDesc+"</div>";
				s+="<div>가게사진 : "+data.data.storeImage+"</div>";
				s+="<div>가게메뉴 : "+data.data.productList+"</div>";				
			
			$("#storecus-storelist-detail").html(s);
		}
	});
});


function CartMain(){
	console.log("CartMain()실행");
	var cart_list = JSON.parse(localStorage.getItem("cartList"));
	
	var s = "<table class='table table-bordered'><tr><td>업체명</td><td>"+localStorage.getItem("cartStore")+"</td></tr>";
	s += "<tr><td>상품명</td><td>가격</td><td>수량</td><td>추가</td><td>삭제</td><td>비우기</td></tr>";
	
	for(var product in cart_list)
	{
		s+= "<tr>";
		s+= "<td>"+cart_list[product].productName+"</td>";
		s+= "<td>"+cart_list[product].productPrice+"</td>";
		s+= "<td>"+cart_list[product].entity+"</td>";
		s+= "<td><button type='button' value='"+product+"' class='cart-add btn-info'>추가</button></td>";
		s+= "<td><button type='button' value='"+product+"' class='cart-delete btn-info'>삭제</button></td>";
		s+= "<td><button type='button' value='"+product+"' class='cart-delete-line btn-info'>비우기</button></td>";
		s+= "</tr>";
	}
	s+= "<tr><td>총 주문액 : </td><td>";
	s+= localStorage.getItem("cartPrice");
	s+= "</td><td><button type='button' class='cart-clear btn-info' data-dismiss='modal'>비우기</button></td></tr></table>";

	s+= "<table><tr><td>주소</td><td><input type='text' id='address'></td></tr>";
	s+= "<tr><td>연락처</td><td><input type='text' id='telephone'></td></tr>";
	s+= "<tr><td>요청사항</td><td><input type='text' id='orderRequirement'></td></tr>";
	s+= "<tr><td>결제수단</td><td><select id='paymentType'><option value='kakao'>카카오 페이</option></select></td></tr></table>";
	
	s+= "<br><button type='button' class='btn-info cart-order'>결제하기</button>";
	s+= "<button type='button' class='btn-info cart-order-cancel' data-dismiss='modal'>취소하기</button>";
	
	$("div#test").html(s);
	
	$("#myModal").modal();

}

//메뉴창에서 메뉴(들)을 담기 누르면 호출
function CartLoad(cart_list, cart_store){
	/*
	담기로 받아온 JSON 타입
	{
		productID:xx,
		productName:xx,
		productImage:xx,
		storeId:xx,
		productPrice:xx,
		productDesc:xx
	}
	*/
	console.log("CartLoad()실행");
	var cartAdd = JSON.parse(localStorage.getItem("cartList"));
	var cartStore = localStorage.getItem("cartStore");
	
	//장바구니에 다른 가게 품목이 이미 담겨있으면
	if(cartAdd != null && cartStore != cart_store)
	{
		console.log("장바구니에 다른 가게 품목이 이미 담겨있으면 사용자체크");
		//사용자에게 기존 장바구니 비우고 새로 담을지 물어봄
		var check = confirm("한 업체의 품목만 담을 수 있습니다. 기존 목록을 비우고 다시 담으시겠습니까?");
		if(check = true) // Yes 라고 답하면
		{
			CartRemoveAll(); // 싹 비움
		}
		else // No 라고 답하면
		{
			return; // 돌아감
		}
	}
	console.log("cartAdd()실행전");
	CartAdd(cart_list);  // 전부 담기
	console.log("cartAdd()실행후");
	localStorage.setItem("cartStore",cart_store); // 담긴 업체명 수정
	localStorage.setItem("cartStoreId",cart_list.storeId); // 담긴 업체명 ID 수정
	//alert("장바구니에 메뉴를 추가했습니다.");
	
}

function CartAdd(cart){
	console.log("CartAdd확인",JSON.stringify(cart));
	
	if(JSON.parse(localStorage.getItem("cartList")) == null) //카트가 없는경우
	{
		console.log("CartAdd()실행:카트가없는경우");	
		console.log("카트가 없는경우 새로 추가");
		// 카트리스트에 상품1개 새로 추가
		var cartAdd = {}; 
		//저장 포맷 변경은 여기서(1)!!
		cartAdd[cart.productName] = 
			{
				"productId":cart.productId,
				"productName":cart.productName,
				"productImage":cart.productImage,
				"storeId":cart.storeId,
				"productPrice":cart.productPrice,
				"productDesc":cart.productDesc,
				"entity":1
			};
		
		//console.log(JSON.stringify(cartAdd)); 여기까진 ok
		
		localStorage.setItem("cartList",JSON.stringify(cartAdd));
		//주문 총액에 상품가격 추가
		localStorage.setItem("cartPrice",cart.productPrice);
	}
	else //카트가 이미 하나이상 있는경우, (기존에 있는상품 count++ or 새상품 추가)
	{
		console.log("CartAdd()실행:카트가 이미 하나이상 있는경우");
		//일단 기존에 있는 카트의 리스트 JSON객체로 꺼내오기
		var cartAdd = JSON.parse(localStorage.getItem("cartList"));
		
		// 만약 해당상품이 리스트에 존재하면,
		if(cartAdd[cart.productName]!=null)
		{
			//해당상품의 카운트 ++
			cartAdd[cart.productName].entity++;
			//다시 로컬스토리지로 올림
			localStorage.setItem("cartList",JSON.stringify(cartAdd));
		}
		else // 만약 해당상품이 리스트에 없으면,
		{
			//새로 JSON에 추가
			//저장 포맷 변경은 여기서(2)!!
			cartAdd[cart.productName] = 
				{
					"productId":cart.productId,
					"productName":cart.productName,
					"productImage":cart.productImage,
					"storeId":cart.storeId,
					"productPrice":cart.productPrice,
					"productDesc":cart.productDesc,
					"entity":1
				};
			//다시 로컬 스토리지로 올림
			localStorage.setItem("cartList",JSON.stringify(cartAdd));
		}
		
		//주문 총액에 상품가격 증가
		var price = parseInt(localStorage.getItem("cartPrice"));
		price += parseInt(cart.productPrice);
		localStorage.setItem("cartPrice",price);
	}
} 

function CartAddAll(cart_all){
	console.log("CartAddAll()실행");
	for(var product in cart_all)
	{
		CartAdd(cart_all[product]);
	}
}



function CartRemoveOne(cart){
	console.log("CartRemoveOne()실행");
	// 삭제처리. 
	//일단 기존에 있는 카트의 리스트 JSON객체로 꺼내오기
	var cartAdd = JSON.parse(localStorage.getItem("cartList"));
	
	//제거할 상품이 JSON 리스트에 있다면
	if(cartAdd[cart.productName]!=null)
	{
		//개수가 2개이상이면, count--
		if(cartAdd[cart.productName].entity>1)
		{
			cartAdd[cart.productName].entity--;
		}
		else //개수가 1개라면 목록에서 삭제
		{
			delete cartAdd[cart.productName];
		}
		//다시 로컬 스토리지로 올림
		localStorage.setItem("cartList",JSON.stringify(cartAdd));
		
		//주문총액에서 상품가격 차감
		var price = parseInt(localStorage.getItem("cartPrice"));
		price -= parseInt(cart.productPrice);
		localStorage.setItem("cartPrice",price);
		CartClearCheck();
	}
}
function CartRemoveLine(cart){
	console.log("CartRemoveLine()실행");
	// 해당 상품 전체 제거
	//일단 기존에 있는 카트의 리스트 JSON객체로 꺼내오기
	var cartAdd = JSON.parse(localStorage.getItem("cartList"));
	//제거할 상품이 JSON 리스트에 있다면
	if(cartAdd[cart.productName]!=null)
	{
		//해당상품 갯수 받아옴
		var count = parseInt(cartAdd[cart.productName].entity);
		//해당상품 가격 받아옴
		var price = parseInt(cartAdd[cart.productName].productPrice);
		//현재 주문총액 받아옴
		var total_price = parseInt(localStorage.getItem("cartPrice"));
		//주문 총액에서 상품라인 가격 제거후 스토리지에 다시 올림
		localStorage.setItem("cartPrice",total_price-count*price);
		
		//목록에서 해당상품 전체 삭제
		delete cartAdd[cart.productName];
		//다시 로컬 스토리지로 올림
		localStorage.setItem("cartList",JSON.stringify(cartAdd));
		CartClearCheck();
	}
}
function CartRemoveAll(){
	console.log("CartRemoveAll()실행");
	//로컬스토리지 "cart" 통째로 삭제
	localStorage.removeItem("cartList");
	//주문총액 0원 처리
	localStorage.setItem("cartPrice",0);
}

function CartClearCheck(){
	console.log("CartClearCheck()실행");
	var check = JSON.parse(localStorage.getItem("cartList"));
	if(Object.keys(check).length == 0)// 카트가 비어있다면
	{
		alert("장바구니를 비웠습니다");
		// cart = {} 을 cart = null 로 수정
		CartRemoveAll();
		
		// 모달창 dismiss 닫기 처리해야한다 코드 알아보기
		
	}
}

function add(data) {
	var ran = Math.random()*100;
	CartLoad(data, ran>50?"A업체":"B업체");
	CartMain();
	
}

// 추가 부분입니다!

/**
 *
 * @param data 성공시 데이터 주문 정보
 * @author monk
 */
function success(data) {
	document.querySelector('.onsuccess-msg').innerHTML = data;
}

// 카카오 결제페이지 모달 팝업
function kakaoPopUp (uri) {
	$("#myModal").modal('hide');
	$("#kakao-modal").modal()
	$('.kakao-inner').attr('src', uri);
}

// 결제 종료후 리턴 함수
function kakaoHide() {
	$("#kakao-modal").modal('hide');
}