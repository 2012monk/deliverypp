$(function(){
	
	s="<button id='storehost-btn-add'>추가</button>";
	
	$("#storehost-header").html(s);
	
});


$(document).on("click","#storehost-btn-add",function(){
	alert("추가하기");
	//매장 등록 폼
	var s="";
	s+="<form id='mdform'>";
	s+="<input type='hidden' id='storeId' value=''>";
	s+="<table>";
	s+="<caption><b>매장등록</b></caption>";
	s+="<tr><td>매장명</td><td><input type='text' name='storeName' required></td><td><button type='button' id='idcheckbtn' onclick='idCheckBtn()'>중복확인</button></td></tr>";
	s+="<tr><td>매장소개</td><td><textarea name='storeDesc' required></textarea></td></tr>";
	s+="<tr><td>매장사진</td><td><input type='text' name='storeImage' required></td></tr>";
	s+="<tr><td>매장주소</td><td><input type='text' name='storeAddr' required></td></tr>";
	s+="<tr><td colspan='2' align='center'><button>추가하기</button></td></tr>";
	s+="</table>";
	s+="</form>";
	$("#storehost-header").html(s);
	
});

function idCheckBtn() {
	var storeName = $("#mdform").find('input[name="storeName"]').val();
	console.log(storeName);
	$.ajax({
		type:"get",
		url:"http://112.169.196.76:47788/stores/check-name/"+storeName,
		success:function(data){
			console.log(data.message);
			if(data.message=="overlap")
				alert("이미 존재하는 매장명입니다.");
			else
				alert("사용 가능한 매장명입니다.");
		},
	});
		
}

$(document).on("submit", "#mdform", function(e){
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
	location.href = "storehost.html";
});

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
});

$(document).on("click","storelist-btn-update",function(){
	var storeId = $(this).attr("value");
	console.log(storeId);
	//매장 등록 폼
	var s="";
	s+="<form id='mdform'>";
	s+="<input type='hidden' id='storeId' value=''>";
	s+="<table>";
	s+="<caption><b>매장등록</b></caption>";
	s+="<tr><td>매장명</td><td><input type='text' name='storeName' required></td><td><button id='idcheckbtn'>중복확인</button></td></tr>";
	s+="<tr><td>매장소개</td><td><textarea name='storeDesc' required></textarea></td></tr>";
	s+="<tr><td>매장사진</td><td><input type='text' name='storeImage' required></td></tr>";
	s+="<tr><td>매장주소</td><td><input type='text' name='storeAddr' required></td></tr>";
	s+="<tr><td colspan='2' align='center'><button>등록 완료</button></td></tr>";
	s+="</table>";
	s+="</form>";
	
	$("#storehost-storelist").html(s);
	
});






function deleteStoreHostListBtn() {
	/*var storeId = document.getElementById("storelist-btn-delete");*/
	var storeId = $(this);
	console.log(storeId);
 	/*$.ajax({
		type:"delete",
		url:"http://112.169.196.76:47788/stores/"+storeId,
		success:function(data){
			console.log(data);
		}
	});*/
}