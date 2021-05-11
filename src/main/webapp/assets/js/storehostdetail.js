$(function(){
    //상품리스트
        $.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/products/list/stid3", 
            dataType:"json",
            success:function(data){
                localStorage.setItem("product-list",JSON.stringify(data));
                var s="";
                    s+="<form>";
                    s+="<input type='hidden' value="+data.data.productId+">";
                    s+="<table>";
                    s+="<caption>상품 리스트</caption>";
                    s+="<tr><th>가게ID</th><th>상품ID</th><th>상품명</th><th>상품가격</th><th>상품 정보</th><th>상품 이미지</th></tr>";
                    $.each(data.data, function(i,elt){
                        s+="<tr><td>"+elt.storeId+"</td><td>"+elt.productId+"</td><td>"+elt.productName+"</td><td>"+elt.productPrice+"</td><td>"+elt.productDesc+"</td><td>"+elt.productImage+"</td><td><button id='updatebtn' value='"+i+"'>update</button>&nbsp;<button type='button' id='deletebtn' value='"+elt.productId+"'>delete</button></td><tr>";
                });
                    s+="</table>"; 
                    s+="</form>";
            
                $("#storehostdetail-productlist").html(s);
            }
        });
        
        //상품삭제이벤트
        $(document).on("click", "#deletebtn", function(){
            var productId=$(this).attr("value");
            console.log(productId);
            $.ajax({
                type:"DELETE",
                url:"http://112.169.196.76:47788/products/"+productId,
                success:function(data){
                    console.log(data);
                    
                }
            });
        });
        //상품수정폼
        $(document).on("click", "#updatebtn", function(e){
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
            s+="<tr><td colspan='2' align='center'><button id='updatabtn2'>수정완료</button></td></tr>"
            s+="</table>";
            s+="</form>";
            $("#show4").html(s);
        });
        //상품수정
        $(document).on("click","#updatabtn2",function(e){
            e.preventDefault();
            var productId=$("#show4").find("#productId").val();
            var productName = $("#show4").find("#productName").val();
            var productPrice = $("#show4").find("#productPrice").val();
            var productImage = $("#show4").find("#productImage").val();
            var productDesc	= $("#show4").find("#productDesc").val();
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
        });
        
        
        //매장정보
        $.ajax({
            type:"get",
            url:"http://112.169.196.76:47788/stores/stid3", 
            dataType:"json",
            success:function(data){
                var s="";
                    s+="<b>매장소개</b>";
                    s+="<div>매장명 : "+data.data.storeName+"</div>";
                    s+="<div>매장소개 : "+data.data.storeDesc+"</div>";
                    s+="<div>매장사진 : "+data.data.storeImage+"</div>";
                    s+="<div>매장주소 : "+data.data.storeAddr+"</div>";
                
                $("#storehostdetail-storelist").html(s);
            }
        });
        
        //상품등록폼
        var s="";
        $(document).on("click","#spadd",function(){
            s="<form id='spaddform'>";
            s+="<input type='hidden' id='storeId' value='stid3'>";
            s+="<table id='spaddtable'>";
            s+="<tr><th>상품등록</th></tr>";
            s+="<tr><td>상품명</td><td><input type='text' name='productName'></td></tr>";
            s+="<tr><td>상품가격</td><td><input type='text' name='productPrice'></td></tr>";
            s+="<tr><td>상품사진</td><td><input type='text' name='productImage'></td></tr>";
            s+="<tr colspan='2'><td><button type='submit' id='spaddbtn'>등록완료</button></td></tr>";
            s+="</table>";
            s+="</form>";
            $("#show3").html(s);
            $("#spupform").hide();
        });
        //상품등록
        $(document).on("submit","#spaddform", function(e){
            e.preventDefault();
            console.log(this);
            var productName = $(this).find('input[name="productName"]').val();
            var productPrice = $(this).find('input[name="productPrice"]').val();
            var productImage = $(this).find('input[name="productImage"]').val();
            var storeId = $(this).find('input[id="storeId"]').val();
            console.log(productName);
            console.log(productPrice);
            console.log(productImage);
            console.log(storeId);
            $.ajax({
                type:"post",
                url:"http://112.169.196.76:47788/products",
                dataType:"json",
                data:JSON.stringify({"productName":productName, "productPrice":productPrice,"productImage":productImage,"storeId":storeId}),
                success:function(data){
                    console.log(data);
                }
            });
        });
    });