$(function(){
	
	s="<button id='storehost-btn-add'>추가</button>";
	
	$("#storehost-header").html(s);
	
});


$(document).on("click","#storehost-btn-add",function(){
	alert("추가하기");
	//가게 등록 폼
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
		url:"http://deli.alconn.co/stores/check-name/"+storeName,
		success:function(data){
			console.log(data.message);
			if(data.message=="overlap")
				alert("이미 존재하는 매장명입니다.");
			else
				alert("사용 가능한 매장명입니다.");
		},
	});
		
}
//가게 등록 폼에서 추가 버튼 클릭시 
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
		url:"http://deli.alconn.co/stores",
		data:JSON.stringify({"storeName":storeName, "storeDesc":storeDesc, "storeImage":storeImage, "storeAddr":storeAddr}),
		success:function(data){
			//alert("success");
			console.log(data);
			console.log("추ㅠ가가추ㅏ구ㅏ");
		}
	});
	/*location.href = "storehost.html";*/
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
});
//가게 리스트 수정하기 버튼 클릭시 
$(document).on("click",".storelist-btn-update",function(e){
	e.preventDefault();
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
	//매장 등록 폼
	var s="";
	s+="<form id='mdform'>";
	s+="<input type='hidden' id='storeId' value='"+storeId+"'>";
	s+="<table>";
	s+="<caption><b>매장등록</b></caption>";
	s+="<tr><td>매장명</td><td><input type='text' name='storeName' required value='"+storeName+"'></td><td><button type='button' id='idcheckbtn' onclick='idCheckBtn()'>중복확인</button></td></tr>";
	s+="<tr><td>매장소개</td><td><textarea name='storeDesc' required>"+storeDesc+"</textarea></td></tr>";
	s+="<tr><td>매장사진</td><td><input type='text' name='storeImage' required value='"+storeImage+"'></td></tr>";
	s+="<tr><td>매장주소</td><td><input type='text' name='storeAddr' required value='"+storeAddr+"'></td></tr>";
	s+="<tr><td colspan='2' align='center'><button id='storelist-btn-update'>수정하기</button></td></tr>";
	s+="</table>";
	s+="</form>";
	
	$("#storehost-storelist").html(s);
	
});

//수정폼에서 진짜 수정하기
$(document).on("click","#storelist-btn-update",function(e){
	e.preventDefault();
	var storeName = $("#storehost-storelist").find('input[name="storeName"]').val();
	var storeDesc = $("#storehost-storelist").find('textarea[name="storeDesc"]').val();
	var storeImage = $("#storehost-storelist").find('input[name="storeImage"]').val();
	var storeAddr = $("#storehost-storelist").find('input[name="storeAddr"]').val();
	var storeId = $("#storehost-storelist").find('input[id="storeId"]').val();
	console.log(storeName);
	console.log(storeDesc);
	console.log(storeImage);
	console.log(storeAddr);
	console.log(storeId);
	$.ajax({
	    type:"PUT",
	    url:"http://deli.alconn.co/stores",
	    /*dataType: "json",*/
	    data:JSON.stringify({"storeId":storeId, "storeName":storeName, "storeDesc":storeDesc, "storeImage":storeImage, "storeAddr":storeAddr}),
	    success:function(data){
	        console.log(data);
			console.log("수정성공");
	    }
	});
});
