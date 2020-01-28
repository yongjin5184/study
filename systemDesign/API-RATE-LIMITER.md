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
    
3. 요구사항과 시스템의 목표
* 기능적 요구사항
    * entity 가 API 로 보내는 요청의 수를 제한하라 ex) 1초에 15번 요청
    * APIs 는 cluster 를 통해서 접근할 수 있기 때문에, rate limit 는 여러 서버에 걸쳐서 고려되어야 한다.
* 비기능적 요구사항
    * rate limit 는 가용성이 높아야한다.
    * rate limit 는 사용자의 경험을 해치는 latencies 가 있어서는 안된다.

4. 어떻게 Rate limiting 을 하는가?
* Rate limiting 은 consumers 가 API 에 접근할 수 있는 속도와 속도를 정의하는 프로세스다.
* Throttling 은 APIs 의 사용을 주어진 기간동안 customers 에 의한 사용을 컨트롤하는 프로세스다.
* Throttling 은 Application level 또는 API level 정의 할 수 있다.
* Throttling 한도를 초과하면 server 는 HTTP status 429 - Too many requests 를 반환한다.

5. Throttling 의 종류는 어떤 것이 있는가?
* Hard Throttling 
    * API requests 요청 수는 throttle limit 를 초과할 수 없다.  

* Soft Throttling
    * API requests 요청 제한을 percentage 로 정할 수 있다.

* Elastic or Dynamic Throttling
    * 사용 가능한 리소스가 있는 경우, API requests 는 한계점을 초과할 수 있다.
    
6. Rate Limiting 의 algorithms 은 어떤 것이 있는가?
* Fixed Window Algorithm
    * start of the time-unit 에서 end of the time unit 까지의 time window 를 고려한다.
    
* Rolling Window Algorithm
    * time window 는 요청이 이루어진 시점과 time window 의 길이를 더한 것으로 고려한다.
