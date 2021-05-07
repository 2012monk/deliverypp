$(function(){

		$(document).on("click","button.add",function(){
		console.log("추가버튼");
		var product_name = $(this).parent().prev().prev().prev().text();
		var price = $(this).parent().prev().prev().text();
		var entity = $(this).parent().prev().text();
		var cart = {};
		cart.productName = product_name;
		cart.productPrice = price;
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
		cart.productName = product_name;
		cart.productPrice = price;
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
		cart.productName = product_name;
		cart.productPrice = price;
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



//상품리스트
	$.ajax({
		type:"get",
		url:"http://112.169.196.76:47788/products/list/stid2",
		dataType:"json",
		credentials : 'include',
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
		url:"http://112.169.196.76:47788/stores/stid3",
		dataType:"json",
		credentials : 'include',
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

function CartParse(parseOne, storeName)
{
	var cart_list = {};
	//var parse_list = JSON.parse(parseOne)
	var parse_list = parseOne;
	
	//cart_list[parse_list.productName] = {store:storeName,product:parse_list.productName,price:parse_list.productPrice};
	cart_list.store = storeName;
	cart_list.product = parse_list.productName;
	cart_list.price = parse_list.productPrice;
	var cart33 = {"store":"store1","product":"product1","price":"2700"};
	console.log(JSON.stringify(cart_list));
	console.log(JSON.stringify(cart33));
	CartAdd(cart_list);
	console.log("목록을넘김");
}

//메뉴창에서 메뉴(들)을 담기 누르면 호출
function CartLoad(cart_list, cart_store){
	console.log("객체확인:"+JSON.stringify(cart_list));
	console.log("CartLoad()실행");
	var cartAdd = JSON.parse(localStorage.getItem("cartList"));
	var cartStore = localStorage.getItem("cartStore");
	
	//장바구니에 다른 가게 품목이 이미 담겨있으면
	if(cartAdd != null && cartStore != cart_store)
	{
		console.log("장바구니에 다른 가게 품목이 이미 담겨있으면 사용자체크");
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
	console.log("cartAdd()실행전");
	CartAdd(cart_list);  // 전부 담기
	console.log("cartAdd()실행후");
	localStorage.setItem("cartStore",cart_store); // 담긴 업체명 수정
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
		cartAdd[cart.productName] = {"productName":cart.productName,"price":cart.productPrice,"entity":1};
		
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
			cartAdd[cart.productName] = {"productName":cart.productName,"price":cart.productPrice,"entity":1};
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
		var price = parseInt(cartAdd[cart.productName].price);
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
	// const data =e.target.getAttribute("data-product");
	console.log(typeof data)
	
	CartLoad(data, "포명청천");
	CartMain();
	}