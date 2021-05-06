$(function(){
	var cart = {"store":"store1","product":"product1","price":"2700"};
	var cart2 = {"store":"store2","product":"product2","price":"4600"};
	var cart_all = {"product3":{"store":"store3","product":"product3","price":"1300"},
					"product4":{"store":"store4","product":"product4","price":"6900"}}
	var cart_all2 = {};
	cart_all2.product3 = cart;
	cart_all2.product4 = cart2;
	
	
	CartLoad(cart_all,"store1");
	CartLoad(cart_all2,"store2");
	
	
	
	
	
	$(document).on("click","button.add",function(){
		console.log("추가버튼");
		var product_name = $(this).parent().prev().prev().prev().text();
		var price = $(this).parent().prev().prev().text();
		var entity = $(this).parent().prev().text();
		var cart = {};
		cart.product = product_name;
		cart.price = price;
		CartAdd(cart);
		CartMain();
		
		//alert(product_name+"_"+price+"_"+entity);
	})
	
	$(document).on("click","button.delete",function(){
		console.log("삭제버튼");
		var product_name = $(this).parent().prev().prev().prev().prev().text();
		var price = $(this).parent().prev().prev().prev().text();
		var entity = $(this).parent().prev().prev().text();
		var cart = {};
		cart.product = product_name;
		cart.price = price;
		CartRemoveOne(cart);
		CartMain();
		//alert(product_name+"_"+price+"_"+entity);
	})
	
	$(document).on("click","button.delete-line",function(){
		console.log("한줄삭제버튼");
		var product_name = $(this).parent().prev().prev().prev().prev().prev().text();
		var price = $(this).parent().prev().prev().prev().prev().text();
		var entity = $(this).parent().prev().prev().prev().text();
		var cart = {};
		cart.product = product_name;
		cart.price = price;
		CartRemoveLine(cart);
		CartMain();
		//alert(product_name+"_"+price+"_"+entity);
	})
	
	$(document).on("click","button.clear",function(){
		console.log("장바구니비움버튼");
		CartRemoveAll();
		alert("장바구니를 비웠습니다");
	})
	
	$("button#myBtn").click(function(){
		console.log("로그인버튼");
		CartMain();
	})
	
	CartMain();
	
})


function CartMain(){
	console.log("CartMain()실행");
	var cart_list = JSON.parse(localStorage.getItem("cartList"));
	var cart_store = localStorage.getItem("cartStore");
	var cart_price = localStorage.getItem("cartPrice");
	
	var s = "<table class='table table-bordered'>";
	s += "<tr><td>상품명</td><td>가격</td><td>수량</td>";
	s += "<td>추가</td>";
	s += "<td>삭제</td>";
	s += "<td>비우기</td>";
	s += "</tr>";
	
	for(var product in cart_list)
	{
		var product_name = cart_list[product].productName;
		var price = cart_list[product].price;
		var entity = cart_list[product].entity;
		s+= "<tr>";
		s+= "<td>"+product_name+"</td>";
		s+= "<td>"+price+"</td>";
		s+= "<td>"+entity+"</td>";
		s+= "<td><button type='button' class='add btn-info'>추가</button></td>";
		s+= "<td><button type='button' class='delete btn-info'>삭제</button></td>";
		s+= "<td><button type='button' class='delete-line btn-info'>비우기</button></td>";
		s+= "</tr>";
	}
	s+= "<tr><td>총 주문액 : </td><td>";
	s+= localStorage.getItem("cartPrice");
	s+= "</td><td><button type='button' class='clear btn-info' data-dismiss='modal'>비우기</button></table>";
	$("div#test").html(s);
	$("#myModal").modal();

	
	
}


//메뉴창에서 메뉴(들)을 담기 누르면 호출
function CartLoad(cart_all, cart_store){
	console.log("CartLoad()실행");
	var cartAdd = JSON.parse(localStorage.getItem("cartList"));
	var cartStore = localStorage.getItem("cartStore");
	
	//장바구니에 다른 가게 품목이 이미 담겨있으면
	if(cartAdd != null && cartStore != cart_store)
	{
		//사용자에게 기존 장바구니 비우고 새로 담을지 물어봄
		//alert("기존 목록을 비우고 다시 담으시겠습니까?");
		if(true) // Yes 라고 답하면
		{
			CartRemoveAll(); // 싹 비움
		}
		else // No 라고 답하면
		{
			return; // 돌아감
		}
	}
	
	CartAddAll(cart_all);  // 전부 담기
	localStorage.setItem("cartStore",cart_store); // 담긴 업체명 수정
	//alert("장바구니에 메뉴를 추가했습니다.");
	
}

function CartAdd(cart){
	console.log("CartAdd()실행");
	if(JSON.parse(localStorage.getItem("cartList")) == null) //카트가 없는경우
	{	
		console.log("카트가 없는경우 새로 추가");
		// 카트리스트에 상품1개 새로 추가
		var cartAdd = {}; 
		cartAdd[cart.product] = {"productName":cart.product,"price":cart.price,"entity":1};
		localStorage.setItem("cartList",JSON.stringify(cartAdd));
		//주문 총액에 상품가격 추가
		localStorage.setItem("cartPrice",cart.price);
	}
	else //카트가 이미 하나이상 있는경우, (기존에 있는상품 count++ or 새상품 추가)
	{
		//일단 기존에 있는 카트의 리스트 JSON객체로 꺼내오기
		var cartAdd = JSON.parse(localStorage.getItem("cartList"));
		
		// 만약 해당상품이 리스트에 존재하면,
		if(cartAdd[cart.product]!=null)
		{
			//해당상품의 카운트 ++
			cartAdd[cart.product].entity++;
			//다시 로컬스토리지로 올림
			localStorage.setItem("cartList",JSON.stringify(cartAdd));
		}
		else // 만약 해당상품이 리스트에 없으면,
		{
			//새로 JSON에 추가
			cartAdd[cart.product] = {"productName":cart.product,"price":cart.price,"entity":1};
			//다시 로컬 스토리지로 올림
			localStorage.setItem("cartList",JSON.stringify(cartAdd));
		}
		
		//주문 총액에 상품가격 증가
		var price = parseInt(localStorage.getItem("cartPrice"));
		price += parseInt(cart.price);
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
	if(cartAdd[cart.product]!=null)
	{
		//개수가 2개이상이면, count--
		if(cartAdd[cart.product].entity>1)
		{
			cartAdd[cart.product].entity--;
		}
		else //개수가 1개라면 목록에서 삭제
		{
			delete cartAdd[cart.product];
		}
		//다시 로컬 스토리지로 올림
		localStorage.setItem("cartList",JSON.stringify(cartAdd));
		
		//주문총액에서 상품가격 차감
		var price = parseInt(localStorage.getItem("cartPrice"));
		price -= parseInt(cart.price);
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
	if(cartAdd[cart.product]!=null)
	{
		//해당상품 갯수 받아옴
		var count = parseInt(cartAdd[cart.product].entity);
		//해당상품 가격 받아옴
		var price = parseInt(cartAdd[cart.product].price);
		//현재 주문총액 받아옴
		var total_price = parseInt(localStorage.getItem("cartPrice"));
		//주문 총액에서 상품라인 가격 제거후 스토리지에 다시 올림
		localStorage.setItem("cartPrice",total_price-count*price);
		
		//목록에서 해당상품 전체 삭제
		delete cartAdd[cart.product];
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

function CartOutput(){
	//장바구니 카트 리스트 JSON 전달
	var totalList = JSON.parse(localStorage.getItem("cartList"));
	//총 장바구니 주문액 INT 전달
	var totalPrice = parseInt(localStorage.getItem("cartPrice"));
}


function test(){
	var cart = {"store":"store1","product":"product1","price":"2700"};
	
	var aa = {};
	aa[cart.store] = cart;
	var bb = {};
	bb[cart.product] = aa;
	localStorage.setItem("test",JSON.stringify(bb));
}