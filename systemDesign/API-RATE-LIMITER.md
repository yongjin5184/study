### Designing an API Rate Limiter

1. Rate Limiter 란 무엇인가?
* 많은 요청을 받는 서비스를 가지고 있다고 상상했을때, 그 서비스는 초당 한정된 요청만 처리할 수 있다.
* 이런 문제를 다루기 위해, throttling, rate limiting mechanism 이 필요하다. 
* high-level 의 rate limiter 는 entity (user, device, ip 등)의 특정 시간에 수행을 제한한다.
* 예시)
    * 유저는 초당 1번만 메시지를 보낼 수 있다.
    * 유저는 오직 하루에 3번만 신용카드 거래를 실패할 수 있다.
    * Single IP 는 하루에 오직 20개의 계정을 만들 수 있다.
    
2. 왜 API Limiting 이 필요한가?
* Rate Limiting 은 abusive behaviors(DOS, brute-force password attempts) 등을 막는데 도움을 준다.
    * 이런 공격은 real users 로 부터 생산되기도 하지만 bots 에서도 생산된다.
* Rate limiting  은 또한 수익 손실을 막을 수도 있다.
* Rate limiting 로 이익을 얻을 수 잇는 시나리오의 예시)
    * Misbehaving
    * Security
    * To prevent abusive behavior  
    ...
    
