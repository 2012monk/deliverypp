
function mainHeaderPage() {
	s = "<div class='navbar-logo'>";
		s += "<a href=''>배달의 민족</a>";
		s +="<i class='fas fa-space-shuttle'></i>"
		s +="</div>";
	
		s +="<ul class='navbar-menu'>";
		s +="<li><a href='#main' id='mainpage'>main</div><li>";
		s +="<li><a href='#storecus' id='storecustomer'>storecus</a><li>";
		s +="<li><a href='#basket'>basket</a><li>";
		s +="</ul>";
				
		s +="<ul class='navbar-login'>";
		s +="<li><i class='fas fa-user-plus'></i></li>";
		s +="<li><i class='far fa-id-card'></i></li></ul>";
		
		/*모달 코드 렌더링 처음에 해놔야 나중에 */
		s += '<div id="myModal" class="modal" tabindex="-1" role="dialog">';
  		s += '<div class="modal-dialog" role="document">';
        s += '<div class="modal-content">';
    	s += '<div class="modal-header">';
        s += '<h5 class="modal-title">Modal title</h5>';
        s += '<button type="button" class="close" data-dismiss="modal" aria-label="Close">';
        s += ' <span aria-hidden="true">&times;</span>';
        s += '</button>';
      	s += '</div>';
      	s += '<div class="modal-body">';
       	s += ' <p class="modal-body-p">Modal body text goes here.</p>';
      	s += '</div>';
      	/*s += '<div class="modal-footer">';
       	s += ' <button type="button" class="btn btn-primary">Save changes</button>';
       	s += ' <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>';
      	s += '</div>';*/
    	s += '</div>';
  		s += '</div>';
		s += '</div>';
		$("#index-header").html(s); 
}

function mainBodyPage() {
	if(login_id == "SELLER")
		sellerPage();
	else
		clientPage();
} 

function sellerPage(){
	//가게 리스트 출력 
	var storeId = $(this).attr("value");
	$.ajax({
		type:"get",
		url:"http://deli.alconn.co/stores/list",
		dataType:"json",
		success:function(data){
			var s="";
				s+="<table>";
				s+="<caption><b>가게 상세보기</b><button id='storehost-btn-add'>추가</button></caption>";
				s+="<tr><th>가게ID</th><th>가게명</th><th>가게 정보</th><th>가게 이미지</th><th>상품리스트</th><th>가게 주소</th></tr>";
				$.each(data.data, function(i,elt){
					s +="<tr value='"+elt.storeId+"'><td class='storehostdetail-page' name='storeId' value='"+elt.storeId+"'>"+elt.storeId+"</td><td class='storehostdetail-page' name='storeName' value='"+elt.storeName+"'>"+elt.storeName+"</td><td class='storehostdetail-page' name='storeDesc'>"+elt.storeDesc+"</td><td class='storehostdetail-page' name='storeImage'>"+elt.storeImage+"</td><td name=''>"+elt.productList+"</td><td name='storeAddr'>"+elt.storeAddr+"</td>";
					s +="<td><button type='button' class='storelist-btn-delete' value='"+elt.storeId+"'>delete</button></td>";
					s +="<td><button type='button' class='storelist-btn-update' value='"+elt.storeId+"'>update</button></td><tr>";
			});
				s+="</table>"; 
			
			$("#index-main").html(s);
			
		}
	});
	/*storeHost();*/
}

function clientPage(){
	/*비화원 손님 회원도 여기가 렌더링*/
	var a = "";
	$.ajax({
		type:"get",
		url:"http://deli.alconn.co/stores/list",
		dataType:"json",
		success:function(d){
			$.each(d.data, function(i, elt) {
				console.log(elt.storeId,elt.storeName);
				//console.log(i);
				//a += "<div storename='"+elt.storeId+"'>";
				a += "<div class='main-storelist' data-value='"+elt.storeId+"'>";
				/*a += "<img src='"+elt.storeImage+"'>";*/
				a += "<div>"+elt.storeName+"</div>";
				a +="<div><span>리뷰</span></div>";
				a +="</div>";
				
			});
			$("#index-main").html(a);
			storeCustomerProductList();
		}
	});
}