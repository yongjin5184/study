### 아키텍쳐 개요

#### 네 개의 영역
* 아키텍처를 설계할 때 출현하는 전형적인 영역은 '표현', '응용', '도메인', '인프라스트럭처'의 네 영역이다.
    * 표현 영역 : 요청을 받아 응용 영역에 전달하고 응용 영역의 처리 결과를 사용자에게 보여주는 역할
        * ex) 표현 영역의 사용자는 웹브라우저를 사용하는 사람, Rest Api 호출하는 외부 시스템등
    * 응용 영역 : 표현 영역을 통해 사용자의 요청을 전달받난다. 사용자에게 제공해야할 기능을 구현하기 위헤 도메인 모델을 사용한다.
        * ex) '주문등록', '주문취소', '상품상세조회'
    * 도메인 영역 : 도메인 모델을 구현한다.
        * ex) 주문 도메인의 경우, '배송지 변경', '결제 완료' 등의 로직.
    * 인프라 스트럭처 : 구현기술에 대한 것을 다룬다.
        * ex) DBMS, NoSQL 등의 데이터베이스 연동 처리, 메일발송등


#### 도메인 영역의 주요 구성요소
* ENTITY : 고유의 식별자를 갖는 객체. 
    * ex) 주문(Order), 회원(Member), 상품(Product)과 같이 도메인의 고유한 개념 표현.
* VALUE : 고유의 식별자를 갖지 않는 객체로 주로 개념적으로 하나인 도메인 객체의 속성을 표현할 때 사용된다.
    * ex) 배송지 주소 - Address, 구매금액 - Money
* AGGREGATE : Entity 와 Value 객체를 개념적으로 하나로 묶은 것.
    * ex) 주문과 관련된 Order Entity, OrderLine value, Orderer value 객체를 '주문' 애그리거트로 묶을 수 있다.
* REPOSITORY : 도메인 모델의 영속성을 처리한다.
    * ex) DBMS 테이블에서 Entity 객체를 로딩하거나 저장하는 기능을 제공한다.
* DOMAIN SERVICE : 특정 Entity 에 속하지 않는 도메인 로직을 제공한다. 
    * ex) '할인 금액 계산' - 상품, 쿠폰 등등 다양한 조건으로 구현하게 되는데, 여러 Entity 와 Value가 필요로 할 경우, 도메인 서비스에서 로직을 구현한다.

