
var list_range = "";
//var domain = "https://112.169.196.76:47788";
// var domain = "https://deli.alconn.co";

function reviewPage(storeId, storeName){

    
/* 리뷰리스트 데이터 형식
    var ajax_data = 
        {
            data : [
            
            {
                reviewId : "reviewid",
                storeId: "stid",
                reviewRating : "rate",
                reviewImage : "photo",
                userEmail : "writer",
                reviewContent : "content300제한",
                reviewDate : "date",
                replyList : [
                    { reviewId:"72495aa-fff", replyId : "ida",userEmail : "reple writer1", replyContent : "reple content1", replyDate : "reple date1" } ,
                    { reviewId:"72495aa-fff", replyId : "idb",userEmail : "reple writer2", replyContent : "reple content2", replyDate : "reple date2" } ,
                    { reviewId:"72495aa-fff", replyId : "idc",userEmail : "reple writer3", replyContent : "reple content3", replyDate : "reple date3" } 
                ]
            },
            {
                reviewId : "글번호2",
                storeId: "stid2",
                reviewRating : "별점",
                reviewImage : "사진",
                userEmail : "리뷰작성자",
                reviewContent : "작성내용300자제한",
                reviewDate : "작성일시",
                replyList : [
                    { reviewId:"72495aa-ttt", replyId : "id1", userEmail : "리플 작성자4", replyContent : "리플 내용4", replyDate : "작성일시4" } ,
                    { reviewId:"72495aa-ttt", replyId : "id2",userEmail : "리플 작성자5", replyContent : "리플 내용5", replyDate : "작성일시5" } ,
                    { reviewId:"72495aa-ttt", replyId : "id3",userEmail : "리플 작성자6", replyContent : "리플 내용6", replyDate : "작성일시6" }
                ]
            }
            
            ]
        }
*/

  
    //출력할 리뷰목록 DB에서 store id 로 받아오기
    $.ajax({
        type:"get",

        url:config.domain +"/review/store/"+storeId,

        dataType:"json",
        success:function(ajax_data){
            var sw="";
            // #review-write 태그로 storeId, storeName 전송
            sw+= "<h2>"+storeName+" 리뷰 게시판</h2><button id='review-write' storeId='"+storeId+"' storeName='"+storeName+"'>리뷰 쓰기</button>";
            sw+= "<button id='review-mylist'>내가 쓴 리뷰</button><button id='review-all'>전체글 보기</button>";
            sw+= "<div id='write-form'></div>";
            var idx=1;

            if(ajax_data.data.length == 0)
                sw+="<hr><h3>작성된 리뷰가 없습니다.</h3>";
            ajax_data.data.forEach(function(w){
                var rating = "";
                for(var i=0; i<parseInt(w.reviewRating); i++)
                    rating += "★";
                var s="";
                //테스트용 s+="<button review_id='"+w.reviewId+"' type='button' id='testbtn'>리뷰테스트</button>";
                s+="<hr><div review_id='"+w.reviewId+"'>";
                //리뷰수정,삭제버튼
                s+="<button id='review-mod' idx='"+idx+"'>수정</button><button id='review-del' idx='"+idx+"'>삭제</button>";
                //리뷰 테이블
                s+= "<table class='table table-bordered'><tr><td>번호</td><td>작성자</td><td>별점</td><td>작성일</td></tr>";
                s+="<tr><td>"+idx+"</td><td>"+w.userEmail+"</td><td>"+rating+"</td><td>"+w.reviewDate.substring(0,10)+"</td></tr>";
                // var s_photo ="<tr class='photo'><td colspan='4'><img style='width:300px;height:300px;' src='"+w.reviewImage+"'></td></tr>";
                var s_photo ="<tr class='photo'><td colspan='4'><img src='"+w.reviewImage+"'></td></tr>";
                if(w.reviewImage!=null)
                    s+=s_photo;
                s+="<tr><td colspan='4' class='content'>"+w.reviewContent+"</td></tr></table>";
                //댓글 등록버튼
                s+="<div><button idx='"+idx+"' id='reply-write'>Reply</button></div>";
                //댓글 테이블
                s+="<table review_id='"+w.reviewId+"' class='table table-bordered'>";
                var idx_reply = 0;

                w.replyList.forEach(function(r){
                    if(r!=null)
                    {
                        s+="<tr review_id='"+w.reviewId+"' reply_id='"+r.replyId+"'><td><span class='glyphicon glyphicon-arrow-right'></span></td>";
                        s+="<td>"+r.userEmail+"</td><td>"+r.replyContent+"</td><td>"+r.replyDate.substring(0,16)+"</td>";
                        //댓글 수정,삭제 버튼
                        //테스트용 s+="<button id='replytest' reply_id='"+r.replyId+"'>리플테스트</button>";
                        s+="<td><button id='reply-mod'>수정</button></td><td><button idx='"+idx+"' idx_reply='"+idx_reply+"' id='reply-del'>삭제</button></tr>";

                        idx_reply++;
                    }
                })

                s+="</table></div>";
                if(list_range == "my_review")
                {
                    if(w.userEmail == config.userEmail)
                        sw += s;
                }
                else if(list_range == "all_review")
                {
                    sw += s;
                }
                else
                    sw += s;
                idx++;
            })
            sw+= "<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>";

            $("#index-main-third").html(sw);


        }
    })


}





// 내가 쓴 리뷰만 출력
$(document).on("click","#review-mylist",function(){
    list_range = "my_review";
    var storeId = $("#review-write").attr("storeId");
    var storeName = $("#review-write").attr("storeName");
    reviewPage(storeId, storeName);
})

// 모든 리뷰 출력
$(document).on("click","#review-all",function(){
    list_range = "all_review";
    var storeId = $("#review-write").attr("storeId");
    var storeName = $("#review-write").attr("storeName");
    reviewPage(storeId, storeName);
})

// 리뷰 작성폼 출력
$(document).on("click","#review-write",function(){

    // 로그인 정보에서 접속중인 아이디정보 가져오기 ->작성자명(이메일)
    var s = "<form method='post' enctype='multipart/form-data'><table class='table table-bordered'>";
    s += "<tr><td>작성자</td><td id='userEmail'>"+config.userEmail+"</td></tr>";
    s += "<tr><td>별점</td><td><select id='review-rate'><option>5</option><option>4</option><option>3</option><option>2</option><option>1</option></select></td></tr>";
    s += "<tr><td>사진 첨부</td><td><input id='photo' type='file'></td></tr>";
    s += "<tr><td>리뷰</td><td><textarea id='content' style='width:500px;height:150px;'></textarea></td></tr>";
    s += "<tr><td><button type='button' id='review-submit'>등록</button><button type='button' id='review-cancel'>취소</button></td></tr>";
    s += "</table></form>";
    $("#write-form").html(s);
})

// 리뷰 작성폼 닫기
$(document).on("click","#review-cancel",function(){
    $("#write-form").html("");
    var storeId = $("#review-write").attr("storeId");
    var storeName = $("#review-write").attr("storeName");
    reviewPage(storeId, storeName);
})

// 리뷰 작성후 전송
$(document).on("click","#review-submit",function(){
    var storeId = $("#review-write").attr("storeId");
    var table = $(this).closest("table");
    var review_json = {};
    review_json.userEmail = table.find("#userEmail").text();
    review_json.reviewRating = table.find("#review-rate").val();
    review_json.reviewContent = table.find("#content").val();
    review_json.userEmail = config.userEmail;
    review_json.storeId = storeId;
    review_json.replyLIst = [];

    // 첨부이미지 경로가 비어있지 않다면 사진이미지 전송
    if(table.find("input#photo").val()!="")
    {
        review_json.reviewImage = document.querySelector('#photo').files[0].name;
        var formData = new FormData();
        formData.append('photo',document.querySelector('#photo').files[0], document.querySelector('#photo').files[0].name);

        $.ajax({
            type:"post",
            data:formData,
            url:config.domain +"/upload",
            processData: false,
            contentType: false,
            success:function(d){
                console.log("이미지 전송 성공:"+JSON.stringify(d));
            }
        })
    }
    else
        console.log("이미지 첨부 없음");

    // 텍스트 데이터 전송
    $.ajax({
        type:"post",
        data:JSON.stringify(review_json),
        url:config.domain +"/review",
        //dataType:"json",
        success:function(d){
            $("#write-form").html("");
            var storeId = $("#review-write").attr("storeId");
            var storeName = $("#review-write").attr("storeName");
            reviewPage(storeId, storeName);
        }
    })
})

    //리뷰 수정폼 출력
    $(document).on("click","#review-mod",function(){
        var review_div = $(this).parent();
        var review_id = $(this).parent().attr("review_id");
        $.ajax({
            type:"get",
            url:config.domain +"/review/"+review_id,
            dataType:"json",
            success:function(r){
                var s = "<form method='post' enctype='multipart/form-data'><table class='table table-bordered'>";
                s += "<tr><td>작성자</td><td id='userEmail'>"+r.data.userEmail+"</td></tr>";
                s += "<tr><td>별점</td><td><select id='review-rate'><option>5</option><option>4</option><option>3</option><option>2</option><option>1</option></select></td></tr>";
                s += "<tr><td>사진 첨부</td><td><input id='photo' type='file'></td></tr>";
                s += "<tr><td>리뷰</td><td><textarea id='content' style='width:500px;height:150px;'>"+r.data.reviewContent+"</textarea></td></tr>";
                s += "<tr><td><button type='button' review_id='"+review_id+"' id='review-mod-submit'>등록</button><button type='button' id='review-mod-cancel'>취소</button></td></tr>";
                s += "</table></form>";
                review_div.html(s);
                review_div.find("#review-rate").val(r.data.reviewRating).attr("selected","selected");
            }
        })
    })

//리뷰 수정폼 닫기
$(document).on("click","#review-mod-cancel",function(){
    var storeId = $("#review-write").attr("storeId");
    var storeName = $("#review-write").attr("storeName");
    reviewPage(storeId, storeName);
})

//리뷰 수정후 전송
$(document).on("click","#review-mod-submit",function(){
    var storeId = $("#review-write").attr("storeId");
    var table = $(this).closest("table");
    var review_json = {};
    review_json.reviewId = $(this).attr("review_id");
    review_json.userEmail = table.find("#userEmail").text();
    review_json.reviewRating = table.find("#review-rate").val();
    review_json.reviewContent = table.find("#content").val();
    review_json.userEmail = config.userEmail;
    review_json.storeId = storeId;
    review_json.replyList = [];

    console.log("수정보낼 데이터:"+JSON.stringify(review_json));

    // 첨부이미지 경로가 비어있지 않다면 사진이미지 전송
    if(table.find("input#photo").val()!="")
    {
        review_json.reviewImage = document.querySelector('#photo').files[0].name;
        var formData = new FormData();
        formData.append('photo',document.querySelector('#photo').files[0], document.querySelector('#photo').files[0].name);
        $.ajax({
            type:"post",
            data:formData,
            url:config.domain +"/upload",
            processData: false,
            contentType: false,
            success:function(d){

            }
        })
    }

    // 수정된 텍스트 전송
    $.ajax({
        type:"put",
        data:JSON.stringify(review_json),
        url:config.domain +"/review",
        //dataType:"json",
        success:function(d){
            var storeId = $("#review-write").attr("storeId");
            var storeName = $("#review-write").attr("storeName");
            reviewPage(storeId, storeName);
        }
    })

})

//리뷰 삭제 처리
$(document).on("click","#review-del",function(){
    var review_id = $(this).parent().attr("review_id");
    console.log(review_id);
    $.ajax({
        type:"delete",
        url:config.domain +"/review/"+review_id,
        //url:config.domain +"/review/null",
        //dataType:"json",
        success:function(d){
            var storeId = $("#review-write").attr("storeId");
            var storeName = $("#review-write").attr("storeName");
            reviewPage(storeId, storeName);
        }
    })

})


// 리뷰 댓글 작성폼 출력
$(document).on("click","#reply-write",function(){
    console.log("작성버튼 실행");
    var review_id = $(this).parent().parent().attr("review_id");
    var s = "<table class='table table-bordered'>";
    s += "<tr><td>작성자</td><td>"+config.userEmail+"</td></tr>";
    s += "<tr><td>댓글 내용</td><td><textarea style='width:500px;height:150px;'></textarea></td></tr>";
    s += "<tr><td><button review_id='"+review_id+"' id='reply-submit'>등록</button><button id='reply-cancel'>취소</button></td></tr>";
    s += "</table>";
    $(this).parent().html(s);
})

//리뷰 댓글 작성/수정폼 닫기
$(document).on("click","#reply-cancel",function(){
    var storeId = $("#review-write").attr("storeId");
    var storeName = $("#review-write").attr("storeName");
    reviewPage(storeId, storeName);
})

//리뷰 댓글 작성후 전송
$(document).on("click","#reply-submit",function(){
    console.log("댓글 전송");
    var review_id = $(this).attr("review_id");
    var reply = {};
    reply.reviewId = review_id;
    reply.userEmail = config.userEmail;
    reply.replyContent = $(this).closest("table").find("textarea").val();


    $.ajax({
        type:"post",
        data:JSON.stringify(reply),
        url:config.domain +"/reply",
        dataType:"json",
        success:function(d){
            var storeId = $("#review-write").attr("storeId");
            var storeName = $("#review-write").attr("storeName");
            reviewPage(storeId, storeName);
        }
    })

})

//리뷰 댓글 삭제 버튼
$(document).on("click","#reply-del",function(){
    var review_id = $(this).closest("tr").attr("review_id");
    var reply_id = $(this).closest("tr").attr("reply_id");
    console.log("삭제할 댓글id:"+reply_id);
    $.ajax({
        type:"delete",
        url:config.domain +"/reply/"+reply_id,
        success:function(d){
            var storeId = $("#review-write").attr("storeId");
            var storeName = $("#review-write").attr("storeName");
            reviewPage(storeId, storeName);
        }
    })

})

//리뷰 댓글 수정폼 출력
$(document).on("click","#reply-mod",function(){
    var this_tr = $(this).closest("tr");
    var reply_id = this_tr.attr("reply_id");
    var review_id = this_tr.attr("review_id");

    $.ajax({
        type:"get",
        url:config.domain +"/review/store/stid22",
        dataType:"json",
        success:function(ajax_data){
            // 이미 열려있는 다른 댓글수정폼 닫기
            ajax_data.data.forEach(function(d){
                d.replyList.forEach(function(r){
                    $("tr[reply_id]").each(function(index,item){
                        var reply_id = $(this).attr("reply_id");
                        var review_id = $(this).attr("review_id");
                        if(d.reviewId == review_id && r.replyId == reply_id)
                        {
                            var s="";
                            s+="<td><span class='glyphicon glyphicon-arrow-right'></span></td>";
                            s+="<td>"+r.userEmail+"</td><td>"+r.replyContent+"</td><td>"+r.replyDate+"</td>";
                            //댓글 수정,삭제 버튼
                            s+="<td><button id='reply-mod'>수정</button></td><td><button id='reply-del'>삭제</button>";
                            $(this).html(s);
                        }
                    })
                })
            })

            //현재 클릭한 댓글 수정폼 열기
            var s = "<table class='table table-bordered'>";
            s += "<tr><td>작성자</td><td>"+config.userEmail+"</td></tr>";
            s += "<tr><td>댓글 내용</td><td><textarea style='width:300px;height:25px;'></textarea></td></tr>";
            s += "<tr><td><button review_id='"+review_id+"' reply_id='"+reply_id+"' id='reply-mod-submit'>등록</button></td><td><button id='reply-cancel'>취소</button></td></tr>";
            s += "</table>";
            $(this_tr).html(s);

        }
    })

})

//리뷰 댓글 수정후 전송
$(document).on("click","#reply-mod-submit",function(){
    //댓글작성코드 복붙후 수정안함. 전부 수정할것
    var reply_id = $(this).attr("reply_id");
    var review_id = $(this).attr("review_id");

    var reply = {};
    reply.replyId = reply_id;
    reply.reviewId = review_id;
    reply.userEmail = config.userEmail;
    reply.replyContent = $(this).closest("tr").find("textarea").val();


    $.ajax({
        type:"put",
        data:JSON.stringify(reply),
        url:config.domain +"/reply",
        dataType:"json",
        success:function(d){
            var storeId = $("#review-write").attr("storeId");
            var storeName = $("#review-write").attr("storeName");
            reviewPage(storeId, storeName);
        }
    })

})
