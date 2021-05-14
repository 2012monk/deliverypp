$(document).on("click","td.storehostdetail-page",function(e){
	alert("설마이것도 이벤트 발생");
	e.preventDefault();
	var storeId = $(this).parent().attr("value");
	console.log(storeId);
	/*가게 상세 상품 목록 등장 */
	productListPage(storeId);
	
	
});
	function addIdCheckBtn() {
		alert("중복확인");
		var storeName = $("#mdform-add").find('input[name="storeName"]').val();
		console.log(storeName);
		$.ajax({
			type:"get",
			url:"http://deli.alconn.co/stores/check-name/"+storeName,
			dataType:"json",
			success:function(data){
				console.log(data.message);
				if(data.message=="overlap")
					alert("이미 존재하는 매장명입니다.");
				else
					alert("사용 가능한 매장명입니다.");
			},
		});
			
	}
	
	function updateIdCheckBtn() {
		alert("수정 폼 중복확인");
		var storeName = $("#mdform-update").find('input[name="storeName"]').val();
		console.log(storeName);
		$.ajax({
			type:"get",
			url:"http://deli.alconn.co/stores/check-name/"+storeName,
			dataType:"json",
			success:function(data){
				console.log(data.message);
				if(data.message=="overlap")
					alert("이미 존재하는 매장명입니다.");
				else
					alert("사용 가능한 매장명입니다.");
			},
		});
			
	}
	//가게 추가 폼 뜨게 하는 이벤트 
	$(document).on("click","#storehost-btn-add",function(e){
		e.preventDefault();
		alert("추가하기");
		/*모달 코드 렌더링 처음에 해놔야 나중에 */
		var m="";
		m += '<div id="store-myModal" class="modal" tabindex="-1" role="dialog">';
  		m += '<div class="modal-dialog" role="document">';
        m += '<div class="modal-content">';
    	m += '<div class="modal-header">';
        m += '<h5 class="modal-title">Modal title</h5>';
        m += '<button type="button" class="close" data-dismiss="modal" aria-label="Close">';
        m += ' <span aria-hidden="true">&times;</span>';
        m += '</button>';
      	m += '</div>';
      	m += '<div class="modal-body">';
       	m += ' <p class="modal-body-po">Modal body text goes here.</p>';
      	m += '</div>';
    	m += '</div>';
  		m += '</div>';
		m += '</div>';
		$("#hidden-store-add-modal").html(m);
		
		//가게 등록 폼
		var s="";
		s+="<form id='mdform-add'>";
		s+="<input type='hidden' id='storeId' value=''>";
		s+="<table>";
		s+="<caption><b>매장등록</b></caption>";
		s+="<tr><td>매장명</td><td><input type='text' name='storeName'></td><td><button type='button' id='idcheckbtn' onclick='addIdCheckBtn()'>중복확인</button></td></tr>";
		s+="<tr><td>매장소개</td><td><textarea name='storeDesc' required></textarea></td></tr>";
		s+="<tr><td>매장사진</td><td><input type='text' name='storeImage' required></td></tr>";
		s+="<tr><td>매장주소</td><td><input type='text' name='storeAddr' required></td></tr>";
		s+="<tr><td colspan='2' align='center'><button>추가하기</button></td></tr>";
		s+="</table>";
		s+="</form>";
		$(".modal-body-po").html(s);
		
		$("#store-myModal").modal();
	});
	//가게 등록 폼에서 추가 버튼 클릭시 
	$(document).on("submit", "#mdform-add", function(e){
		e.preventDefault();
		console.log(this);
		var storeName = $(this).find('input[name="storeName"]').val();
		var storeDesc = $(this).find('textarea[name="storeDesc"]').val();
		var storeImage = $(this).find('input[name="storeImage"]').val();
		var storeAddr = $(this).find('input[name="storeAddr"]').val();
		var storeId = $(this).find('input[id="storeId"]').val();
		console.log(storeName);
		console.log(storeDesc);
		console.log(storeImage);
		console.log(storeAddr);
		$.ajax({
			type:"post",
			url:"http://deli.alconn.co/stores",
			data:JSON.stringify({"storeName":storeName, "storeDesc":storeDesc, "storeImage":storeImage, "storeAddr":storeAddr}),
			success:function(data){
				//alert("success");
				console.log(data);
			}
		});
		$("#store-myModal").modal('hide');
		setTimeout(function(){
			mainBodyPage();
		},1000);
		
	});
	
	//가게 리스트 수정하기 버튼 클릭시 
	$(document).on("click",".storelist-btn-update",function(e){
		e.preventDefault();
		alert("제발");
		var storeId = $(this).attr("value");
		var storeName = $(this).parent().parent().find('td[name="storeName"]').text();
		var storeDesc = $(this).parent().parent().find('td[name="storeDesc"]').text();
		var storeImage = $(this).parent().parent().find('td[name="storeImage"]').text();
		var storeAddr = $(this).parent().parent().find('td[name="storeAddr"]').text();
		console.log(storeId);
		console.log(storeName);
		console.log(storeDesc);
		console.log(storeImage);
		console.log(storeAddr);
		//매장 수정 폼
		var s="";
		s+="<form id='mdform-update'>";
		s+="<input type='hidden' id='storeId' value='"+storeId+"'>";
		s+="<table>";
		s+="<caption><b>매장수정</b></caption>";
		s+="<tr><td>매장명</td><td><input type='text' name='storeName' required value='"+storeName+"'></td><td><button type='button' id='idcheckbtn' onclick='updateIdCheckBtn()'>중복확인</button></td></tr>";
		s+="<tr><td>매장소개</td><td><textarea name='storeDesc' required>"+storeDesc+"</textarea></td></tr>";
		s+="<tr><td>매장사진</td><td><input type='text' name='storeImage' required value='"+storeImage+"'></td></tr>";
		s+="<tr><td>매장주소</td><td><input type='text' name='storeAddr' required value='"+storeAddr+"'></td></tr>";
		s+="<tr><td colspan='2' align='center'><button id='storelistform-btn-update'>수정하기</button></td></tr>";
		s+="</table>";
		s+="</form>";
		
		$("#index-main").html(s);
	
	});
	
	//수정폼에서 진짜 수정하기
	$(document).on("click","#storelistform-btn-update",function(e){
		e.preventDefault();
		var storeName = $("#index-main").find('input[name="storeName"]').val();
		var storeDesc = $("#index-main").find('textarea[name="storeDesc"]').val();
		var storeImage = $("#index-main").find('input[name="storeImage"]').val();
		var storeAddr = $("#index-main").find('input[name="storeAddr"]').val();
		var storeId = $("#index-main").find('input[id="storeId"]').val();
		console.log(storeName);
		console.log(storeDesc);
		console.log(storeImage);
		console.log(storeAddr);
		console.log(storeId);
		$.ajax({
		    type:"PUT",
		    url:"http://deli.alconn.co/stores",
		    data:JSON.stringify({"storeId":storeId, "storeName":storeName, "storeDesc":storeDesc, "storeImage":storeImage, "storeAddr":storeAddr}),
		    success:function(data){
		        console.log(data);
				console.log("수정성공");
				setTimeout(function(){
					mainBodyPage();
				},1000);
		    }
		});
	
	});
	
	//가게 삭제 
	$(document).on("click",".storelist-btn-delete",function(){
		var storeId = $(this).attr("value");
		console.log(storeId);
		
		$.ajax({
			type:"delete",
			url:"http://deli.alconn.co/stores/"+storeId,
			success:function(data){
				console.log(data);
			}
		});
		setTimeout(function(){
			mainBodyPage();
		},1000)
	});
	
	
	
	
	//상품삭제 이벤트 
	$(document).on("click", ".deletebtn", function(){
            var productId=$(this).attr("value");
			var storeId = $("#ssss").attr("data-store");
            console.log(productId);
            $.ajax({
                type:"DELETE",
                url:"http://deli.alconn.co/products/"+productId,
                success:function(data){
                    console.log(data);
                    
                }
            });
			setTimeout(function(){
				productListPage(storeId);
			},1000);
        });

	//상품수정폼
    $(document).on("click", ".product-btn-updateform", function(e){
        e.preventDefault();
        var cc= localStorage.getItem("product-list")
        var productdata=JSON.parse(localStorage.getItem("product-list"));
        console.log($(this).val);
        var idx=parseInt($(this).val());
        console.log(productdata.data[idx].productName);
		alert("상품 ID updatebtn 추가");
		var m="";
		m += '<div id="product-update-myModal" class="modal" tabindex="-1" role="dialog">';
  		m += '<div class="modal-dialog" role="document">';
        m += '<div class="modal-content">';
    	m += '<div class="modal-header">';
        m += '<h5 class="modal-title">Modal title</h5>';
        m += '<button type="button" class="close" data-dismiss="modal" aria-label="Close">';
        m += ' <span aria-hidden="true">&times;</span>';
        m += '</button>';
      	m += '</div>';
      	m += '<div class="modal-body">';
       	m += ' <p class="modal-body-pu">Modal body text goes here.</p>';
      	m += '</div>';
    	m += '</div>';
  		m += '</div>';
		m += '</div>';
		$("#hidden-product-update-modal").html(m);
		
        var s ="";
        s="<form>";
        s="<input id='productId' type='hidden' value='"+productdata.data[idx].productId+"'>"//상품아이디hidden
        s+="<table>";
        s+="<caption>상품수정</caption>";
        s+="<tr><td>상품명</td><td><input type='text' id='productName' value='"+productdata.data[idx].productName+"'></td></tr>";
        s+="<tr><td>상품가격</td><td><input type='text' id='productPrice' value='"+productdata.data[idx].productPrice+"'></td></tr>";
        s+="<tr><td>상품이미지</td><td><input type='text' id='productImage' value='"+productdata.data[idx].productImage+"'></td></tr>";
        s+="<tr><td>상품설명</td><td><input type='text' id='productDesc' value='"+productdata.data[idx].productDesc+"'></td></tr>";
        s+="<tr><td colspan='2' align='center'><button id='updatabtn2'>수정완료</button></td></tr>";
        s+="</table>";
        s+="</form>";
        $(".modal-body-pu").html(s);
		$("#product-update-myModal").modal();
    });
    //상품수정
    $(document).on("click","#updatabtn2",function(e){
        e.preventDefault();
		var storeId = $("#ssss").attr("data-store");
		console.log(storeId);
        var productId=$(".modal-body").find("#productId").val();
        var productName = $(".modal-body").find("#productName").val();
        var productPrice = $(".modal-body").find("#productPrice").val();
        var productImage = $(".modal-body").find("#productImage").val();
        var productDesc	= $(".modal-body").find("#productDesc").val();
        console.log(productId);
        console.log(productName);
        console.log(productPrice);
        console.log(productImage);
		console.log(productDesc);
        $.ajax({
            type:"PUT",
            url:"http://deli.alconn.co/products",
            dataType: "json",
            data:JSON.stringify({"productId":productId,"productName":productName,"productPrice":productPrice,"productImage":productImage,"productDesc":productDesc}),
            success:function(data){
                console.log(data);
            }
        });
		$("#product-update-myModal").modal('hide');
		setTimeout(function(){
			productListPage(storeId);
		},1200);
    });

 	//상품등록폼
    var s="";
    $(document).on("click","#storehost-product-btn-add",function(e){
		var storeId = $("#ssss").attr("data-store");
		e.preventDefault();
		var m="";
		m += '<div id="product-myModal" class="modal" tabindex="-1" role="dialog">';
  		m += '<div class="modal-dialog" role="document">';
        m += '<div class="modal-content">';
    	m += '<div class="modal-header">';
        m += '<h5 class="modal-title">Modal title</h5>';
        m += '<button type="button" class="close" data-dismiss="modal" aria-label="Close">';
        m += ' <span aria-hidden="true">&times;</span>';
        m += '</button>';
      	m += '</div>';
      	m += '<div class="modal-body">';
       	m += ' <p class="modal-body-pr">Modal body text goes here.</p>';
      	m += '</div>';
    	m += '</div>';
  		m += '</div>';
		m += '</div>';
		$("#hidden-product-add-modal").html(m);
		
        s="<form id='spaddform'>";
        s+="<input type='hidden' id='storeId' value='"+storeId+"'>";
        s+="<table id='spaddtable'>";
        s+="<tr><th>상품등록</th></tr>";
        s+="<tr><td>상품명</td><td><input type='text' name='productName'></td></tr>";
        s+="<tr><td>상품가격</td><td><input type='text' name='productPrice'></td></tr>";
        s+="<tr><td>상품사진</td><td><input type='text' name='productImage'></td></tr>";
		s+="<tr><td>상품설명</td><td><input type='text' name='productDesc'></td></tr>";
        s+="<tr colspan='2'><td><button type='submit' id='spaddbtn'>등록완료</button></td></tr>";
        s+="</table>";
        s+="</form>";
		
        $(".modal-body-pr").html(s);
       	$("#product-myModal").modal();
    });
    //상품등록
    $(document).on("submit","#spaddform", function(e){
        e.preventDefault();
        console.log(this);
		console.log("상품등록진입?");
        var productName = $(this).find('input[name="productName"]').val();
        var productPrice = $(this).find('input[name="productPrice"]').val();
        var productImage = $(this).find('input[name="productImage"]').val();
		var productDesc = $(this).find('input[name="prodcutDesc"]').val();
        var storeId = $(this).find('input[id="storeId"]').val();
        console.log(productName);
        console.log(productPrice);
        console.log(productImage);
        console.log(storeId);
        $.ajax({
            type:"post",
            url:"http://deli.alconn.co/products",
          	dataType:"json",
            data:JSON.stringify({"productName":productName, "productPrice":productPrice,"productImage":productImage,"storeId":storeId,"productDesc":productDesc}),
            success:function(data){
                console.log("상품등록 : "+ data);
            }
        });
		$("#product-myModal").modal('hide');
		setTimeout(function(){
			productListPage(storeId);
		},1000);
		
    });


function productListPage(storeId) {

	/*매장 소개*/
	$.ajax({
        type:"get",
        url:"http://deli.alconn.co/stores/"+storeId, 
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


	//상품리스트 출력
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
                z+="<table>";
                z+="<caption><b>상품 리스트</b><button id='storehost-product-btn-add'>상품추가</button></caption>";
                z+="<tr><th>가게ID</th><th>상품ID</th><th>상품명</th><th>상품가격</th><th>상품 정보</th><th>상품 이미지</th></tr>";
                $.each(data.data, function(i,elt){
                    z+="<tr><td>"+elt.storeId+"</td>";
					z+="<td>"+elt.productId+"</td>";
					z+="<td>"+elt.productName+"</td>";
					z+="<td>"+elt.productPrice+"</td>";
					z+="<td>"+elt.productDesc+"</td>";
					/*z+="<td>"+elt.productImage+"</td>";*/
					z+="<td><button class='product-btn-updateform' value='"+i+"'>update</button>&nbsp;";
					z+="<button type='button' class='deletebtn' value='"+elt.productId+"'>delete</button></td></tr>";
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

}