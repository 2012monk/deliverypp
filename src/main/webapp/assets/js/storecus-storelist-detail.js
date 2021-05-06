$(function(){
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

})