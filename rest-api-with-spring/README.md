백기선님의 Inflearn 강의를 보고 공부한 내용입니다.

참고 : 그런 Day1, 2-2. 그런 REST API로 괜찮은가 - 이응준님의 발표
* https://www.youtube.com/watch?v=RP_f5dMoHFc&t=5s
* https://slides.com/eungjun/rest#/41

대부분의 Rest API는 REST 하지 않다. 그럼, 제대로 된 Rest API를 만들어 보자.

* Self-descriptive message 해야한다. -> 메시지는 스스로 설명해야한다.
 
    * custom media type / profile link relation 를 사용하여 만족시키자! 

* 애플리케이션의 상태는 Hyper-link로 전이되어야한다.
 
    * Spring Hateoas 를 사용하여 본문에 link를 담아 만족시키자!


2019.12.9 Advice 테스트 코드 추가

*  AOP 는 관점 지향 프로그래밍으로 기능을 **핵심 비즈니스 기능**과 **공통 기능**으로 구분하고, 
    공통 기능을 개발자의 코드 밖에서 필요한 시점에 적용하는 프로그래밍 방법

* **핵심 비지니스 기능**은 횡단(가로)로 동작한다면, 
    **공통 기능** 예를 들어, 권한, 로깅, 트랜잭션과 같은 관심은 각 계층의 바깥쪽에서 종단(세로)으로 동작한다.