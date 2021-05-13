$(document).on("click",".storehostdetail-page",function(e){
	alert("설마이것도 이벤트 발생");
	e.preventDefault();
	var storeId = $(this).parent().attr("value");
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
					/*z+="<td>"+elt.productImage+"</td>";*/
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
            s+="<div id='ssss' data-store='"+storeId+"'>매장명 : "+data.data.storeName+"</div>";
            s+="<div>매장소개 : "+data.data.storeDesc+"</div>";
            s+="<div>매장사진 : "+data.data.storeImage+"</div>";
            s+="<div>매장주소 : "+data.data.storeAddr+"</div>";
        
           	$("#index-main-first").html(s);
			reviewPage();
        }
    });

	
});
	function addIdCheckBtn() {
		alert("중복확인");
		var storeName = $("#mdform-add").find('input[name="storeName"]').val();
		console.log(storeName);
		$.ajax({
			type:"get",
			url:"http://112.169.196.76:47788/stores/check-name/"+storeName,
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
			url:"http://112.169.196.76:47788/stores/check-name/"+storeName,
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
			url:"http://112.169.196.76:47788/stores",
			data:JSON.stringify({"storeName":storeName, "storeDesc":storeDesc, "storeImage":storeImage, "storeAddr":storeAddr}),
			success:function(data){
				//alert("success");
				console.log(data);
			}
		});
		
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
		    url:"http://112.169.196.76:47788/stores",
		    data:JSON.stringify({"storeId":storeId, "storeName":storeName, "storeDesc":storeDesc, "storeImage":storeImage, "storeAddr":storeAddr}),
		    success:function(data){
		        console.log(data);
				console.log("수정성공");
		    }
		});
		mainBodyPage();
	});
	
	//가게 삭제 
	$(document).on("click",".storelist-btn-delete",function(){
		var storeId = $(this).attr("value");
		console.log(storeId);
		
		$.ajax({
			type:"delete",
			url:"http://112.169.196.76:47788/stores/"+storeId,
			success:function(data){
				console.log(data);
			}
		});
		setTimeout(function(){
			mainBodyPage();
		},1000)
	});