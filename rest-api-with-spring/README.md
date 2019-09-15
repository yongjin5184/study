백기선님의 Inflearn 강의를 보고 공부한 내용입니다.

참고 : 그런 Day1, 2-2. 그런 REST API로 괜찮은가 - 이응준님의 발표
* https://www.youtube.com/watch?v=RP_f5dMoHFc&t=5s
* https://slides.com/eungjun/rest#/41

대부분의 Rest API는 REST 하지 않다. 그럼, 제대로 된 Rest API를 만들어 보자.

* Self-descriptive message 해야한다. -> 메시지는 스스로 설명해야한다.
 
    * custom media type / profile link relation 를 사용하여 만족시키자! 

* 애플리케이션의 상태는 Hyper-link로 전이되어야한다.
 
    * Spring Hateoas 를 사용하여 본문에 link를 담아 만족시키자!