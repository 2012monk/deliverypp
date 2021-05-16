function kakaoSuccess(res) {
    const tmpl = `<div class="order-message">
    <span>주문이 완료 되었습니다!</span>
    <div class="order-header">
        <table>
            <tr><td colspan="2">{{message}}</td></tr>
            <tr><td>가게이름</td><td>{{data.storeName}}</td></tr>
            <tr><td>주문번호</td><td>{{data.orderId}}</td></tr>
            <tr><td>결제번호</td><td>{{data.tid}}</td></tr>
            <tr><td>주문 상태</td><td>{{data.orderState}}</td></tr>
        </table>
    </div>
    <div class="order-body">
        <div class="order-user-info">
            <table>
                <tr><td colspan="2">주문자 정보</td></tr>
                <tr><td>아이디</td><td>{{data.userEmail}}</td></tr>
                <tr><td>주소</td><td>{{data.userTelephone}}</td></tr>
                <tr><td>주문 요청사항</td><td>{{data.orderRequirement}}</td></tr>
                <tr><td>총금액</td><td>{{data.totalPrice}}</td></tr>
                <tr><td>수량</td><td>{{data.totalAmount}}</td></tr>
                <tr><td>주문일시</td><td>{{data.orderInitDate}}</td></tr>
            </table>
        </div>
        <div class="order-orderList">
            <table>
            {{#data.orderList}}
                <tr><td colspan="2">주문 상품 목록</td></tr>
                <tr><td>상품명</td><td>{{productName}}</td></tr>
                <tr><td>갯수</td><td>{{quantity}}</td></tr>
            {{/data.orderList}}
            </table>
        </div>
    </div>
</div>`
    console.log(JSON.stringify(res));
    const inner = Mustache.render(tmpl, res);
    document.querySelector('#index-main').innerHTML = inner;
}

// kakaoSuccess('{"data" : { "storeName" : "erer"}}')