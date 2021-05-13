	$(function(){
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
		
		
		var a = "";
		$.ajax({
			type:"get",
			url:"http://112.169.196.76:47788/stores/list",
			dataType:"json",
			success:function(d){
				$.each(d.data, function(i, elt) {
					console.log(elt.storeId,elt.storeName);
					//console.log(i);
					//a += "<div storename='"+elt.storeId+"'>";
					a += "<div class='main-storelist' data-value='"+elt.storeId+"'>";
					/*a += "<img src='"+elt.storeImage+"'>";*/
					a += "<div>"+elt.storeName+"</div>";
					a +="</div>"
					
				});
				$("#index-main").html(a);
			}
		});
		
		
	});
	
	/* 가게 상세 페이지 넘어가는 이벤트 + 가게상세페이지 + 상품 상세페이지 + 장바구니 넘어가는 버튼 */
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
						z+="<td><button id='product-btn-updateform' value='"+i+"'>update</button>&nbsp;";
						z+="<button type='button' id='deletebtn' value='"+elt.productId+"'>delete</button></td></tr>";
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
                s+="<div>매장명 : "+data.data.storeName+"</div>";
                s+="<div>매장소개 : "+data.data.storeDesc+"</div>";
                s+="<div>매장사진 : "+data.data.storeImage+"</div>";
                s+="<div>매장주소 : "+data.data.storeAddr+"</div>";
            
	           	$("#index-main-first").html(s);
            }
        });
		

	});
	/*업데이트 버튼 클릭시*/
	$(document).on("click","#product-btn-updateform",function(e){
		 e.preventDefault();
            var cc= localStorage.getItem("product-list")
            var productdata=JSON.parse(localStorage.getItem("product-list"));
            console.log($(this).val);
            var idx=parseInt($(this).val());
            console.log(productdata.data[idx].productName);
            var s ="";
            s="<form>";
            s="<input id='productId' type='hidden' value='"+productdata.data[idx].productId+"'>"//상품아이디hidden
            s+="<table>";
            s+="<caption>상품수정</caption>";
            s+="<tr><td>상품명</td><td><input type='text' id='productName' value='"+productdata.data[idx].productName+"'></td></tr>"
            s+="<tr><td>상품가격</td><td><input type='text' id='productPrice' value='"+productdata.data[idx].productPrice+"'></td></tr>"
            s+="<tr><td>상품이미지</td><td><input type='text' id='productImage' value='"+productdata.data[idx].productImage+"'></td></tr>"
            s+="<tr><td>상품설명</td><td><input type='text' id='productDesc' value='"+productdata.data[idx].productDesc+"'></td></tr>"
            s+="<tr><td colspan='2' align='center'><button id='product-updateform-updatebtn'>수정완료</button></td></tr>"
            s+="</table>";
            s+="</form>";
            $(".modal-body-p").html(s);
			$("#myModal").modal();
	})
	/*가게 상품 수정 "!폼" 안에 있는 수정 폼 클릭시 */
	$(document).on("click","#product-updateform-updatebtn",function(e){
		e.preventDefault();
        var productId=$(".modal-body-p").find("#productId").val();
        var productName = $(".modal-body-p").find("#productName").val();
        var productPrice = $(".modal-body-p").find("#productPrice").val();
        var productImage = $(".modal-body-p").find("#productImage").val();
        var productDesc	= $(".modal-body-p").find("#productDesc").val();
        console.log(productId)
        console.log(productName)
        console.log(productPrice)
        console.log(productImage)
        $.ajax({
            type:"PUT",
            url:"http://112.169.196.76:47788/products",
            dataType: "json",
            data:JSON.stringify({"productId":productId,"productName":productName,"productPrice":productPrice,"productImage":productImage,"productDesc":productDesc}),
            success:function(data){
                console.log(data);
            }
        });
		alert("수정중입니다. ");
		$("#myModal").modal('hide');
		
		/* 다시 상품 리스트 리로드 하려면 */
		/*productAndStoreDescriptionList();*/
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* 상품 리스트 출력하는 id 출력*/
	function productAndStoreDescriptionList() {
		/*var storeId = $(this).attr("data-value");*/
		var storeId = document.querySelector(".main-storelist").getAttribute("data-value");
		console.log(storeId);
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
						z+="<td><button id='product-btn-updateform' value='"+i+"'>update</button>&nbsp;";
						z+="<button type='button' id='deletebtn' value='"+elt.productId+"'>delete</button></td></tr>";
                });
                z+="</table>"; 
                z+="</form><br><br><br><br><br><br>";
            	
				c = "<div id='index-main-first'></div>";
				c +="<div id='index-main-second'></div>";
				c +="<div id='index-main-thrid'></div>";
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
                s+="<div>매장명 : "+data.data.storeName+"</div>";
                s+="<div>매장소개 : "+data.data.storeDesc+"</div>";
                s+="<div>매장사진 : "+data.data.storeImage+"</div>";
                s+="<div>매장주소 : "+data.data.storeAddr+"</div>";
            
	           	$("#index-main-first").html(s);
            }
        });
	}
