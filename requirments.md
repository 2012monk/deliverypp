## deli app requirements spec

---

#### application 설계 동기와 컨셉

소비자와 판매자를 연결시켜주는 중계 플랫폼

쉽게 접근 할수있고 번거러운 절차 없이 시작 또는 주문 할수 있는 application 을 목표로 한다

---
#### 서비스 요구 사항

고객에게는 간편하게 가게를 찾아서 빠르게 주문 할수있는 시스템

판매자에게는 빠르게 매장정보를 만들고 업데이트 할수 있는 시스템
>
> 공통 요구사항
> -[ ] login and authentication
>   -[ ] 판매자와 클라이언트의 인증 분리
>
> main page 요구사항
> -[ ] 고정 되어 있는 메뉴 바 
> -[ ] 등록 되어 있는 매장들의 리스트 출력 및 view 수정
> -[ ] 
>
> main page option 사항
> -[ ] 카테고리 별 분류
> -[ ] 검색 기능
> 
> store page 요구사항
>> 일반적인 게시판 형식으로 작성
> -[ ] store page 의 CUD 판매자만 가능하게
> -[ ] 가게의 정보 출력
> -[ ] product list 출력
> 
> store page client 요구사항
> -[ ] 제품 정보 확인
> -[ ] 장바구니에 물건을 담아서 유지
> -[ ] 결제 버튼
> 
> option 사항
> -[ ] 판매자에게 문의 `채팅또는 일반적인 메세지`
> -[ ] 즐겨찾기 기능
> -[ ] 리뷰 또는 댓글
> 
> 
> 결제 page 요구사항
> -[ ] 주문 내역 확인서 출력
> -[ ] 개인 세부 정보 입력 폼 
> 
> 
> --- 
> 전체적인 option 사항
> 
> 판매자 주문 관리 시스템
> 
> -[ ] 주문 세부사항 관리
> -[ ] 주문 내역 출력및 확인 가능
> -[ ] 주문서에 확인 사항 작성후 컨펌 또는 취소
> -[ ] pending 중인 주문 시간순으로 출력
> -[ ] 단위 시간별 매출 집계 시스템
> 
> 
> 주문 알림시스템
> -[ ] 고객 주문시 판매자에게 푸시알림
> -[ ] 판매자가 수락시 배송정보를 고객에게 푸시알림
> 

---
Detail 
> main page detail
>
> 판매자 view
> 
> 로그인 시 매장 상세페이지 또는 주문관리 페이지로 이동
> 
> 클라이언트 view
> 
> 리스트에서 가게클릭시 가게 페이지로 이동
> 
> 
> 가게 페이지
> 가게정보 
> - 브랜드 사진
> - 상호명
> - 소개글
> - 주소
> 
> 제품정보
> - 사진
> - 이름
> - 가격
> - 태그 또는 카테고리
> 
> 가게페이지 고객 기능
> 
> 장바구니 기능
> 
> 아이템 유지 방법 -- 체크
> 1. 로컬스토리지 사용
> 2. 세션유지
> 
> 결제버튼 누를시 결제페이지로 넘어감
> 
> 결제 페이지
> 
> 주문정보 보여주고 수정가능하게
> - 수량 수정
> - 아이템 빼기
> 
> 개인정보 입력
> - 주소
> - 이름
> - 전화번호
> 
> 결제기능
> > 무엇인가 찾아서 구현
> 
> 결제 실패시 알림 alert
> 
> 결제 성공시 알림 => 알림확인시 가게 페이지로 리다이렉트
> 
> 
> 결제 성공시 주문정보 => 판매자에게 메세지
> 

---
- jsp 
- jquery 
- javascript 

다만 프론트 백 나누는 것을 지향하려면 

ajax 를 주축으로 구성!

의사 결정

