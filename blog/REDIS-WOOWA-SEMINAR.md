- redis 소개
    - 파레토의 법칙 - 캐시를 써야하는 이유

-
- Write back
    - cache에만 저장 > 캐시에 특정 시간 동안 데이터가 저장 > 캐시에 있는 데이터를 DB에 저장한다. > 캐시에서 db에 저장된 데이터를 삭제한다.

- memcached는 collection을 제공 안함.
- 랭킹서버
    - in memory sorted (redis) set을 이용하면 랭킹을 구현할 수 있음
    - redis 의 경우 자료구조가 Atomic하기 때문에 해당 condition을 피할 수 있다.

- 자료구조
    - String prefix 에 따라 분산 저장도 가능하다.
    - String, SortedSet 가장 많이 쓴다.
    - 하나의 컬렉션에 너무 많은 아이템을 담으면 좋지 않음

- 레디스 운영
    - Swap 이 있다면 Swap 사용으로 해당 메모리 Page 접근 시 마다 늦어짐
    - 큰 메모리를 사용하는 instance 하나보다는 적은 메모리를 사용하는 instance 여러 개가 안전함
    - 메모리가 부족하면 마이그레이션
    - 자료구조를 저장 할때 내부적으로 ziplist를 쓰기 때문에 메모리를 아끼고 싶으면 ziplist 를 써라.
    - O(N)관련 명령어를 주의하자!
    - 싱글쓰레드, 동시에 처리할 수 있는 명령어는 한번에 하나 참고) 단순한 get/set 초당 10만 TPS  이상
    - 긴 시간이 필요한 명령을 수행하면 망한다.
        - KEYS (백만 개 이상인데 확인을 위해 KEYS 명령을 사용하는 경우)
        - FLUSHALL, FLUSHDB
        - Delete Collections
        - Get ALL Collections 등
    - KEYS 대체 → scan 명령을 사용하는 것으로 하나의 긴 명령을 짧은 여러 번의 명령을 바꿀 수 있다.
    - Collection 의 일부만 가져오거나 Collection을 작은 여러 개의 Collection으로 나눠서 저장
    - Spring security oauth RedisTokenStore 이슈
        - [spring-security-oauth의 RedisTokenStore]([https://charsyam.wordpress.com/2018/05/11/입-개발-spring-security-oauth의-redistokenstore의-사용은-서비스에-적합하지-않/](https://charsyam.wordpress.com/2018/05/11/%EC%9E%85-%EA%B0%9C%EB%B0%9C-spring-security-oauth%EC%9D%98-redistokenstore%EC%9D%98-%EC%82%AC%EC%9A%A9%EC%9D%80-%EC%84%9C%EB%B9%84%EC%8A%A4%EC%97%90-%EC%A0%81%ED%95%A9%ED%95%98%EC%A7%80-%EC%95%8A/))
        - Access Token 저장을 List 자료구조를 통해서 이루어짐
            - 서치 O(N) 명령어 수행
            - 검색 / 삭제시 O(N) 명령이 수행
            - 현재는 Set(O(1)) 명령어로 패치
    - Redis Replication
        - Async Replication
            - Replication Lag 이 발생할 수 있다.

    - 권장 설정
        - Maxclient 설정 50000
        - RDB/AOF 설정  off
        - commands disable
            - ex) keys
            - AWS ElasticCache는 이미하고 있음
        - 전체 장애의 90프로 이상이 KEYS 와 SAVE 설정에서 발생

    - **consistent Hashing**
        - [Consistent Hashing 과 Replication]([https://charsyam.wordpress.com/2016/10/02/입-개발-consistent-hashing-에-대한-기초/](https://charsyam.wordpress.com/2016/10/02/%EC%9E%85-%EA%B0%9C%EB%B0%9C-consistent-hashing-%EC%97%90-%EB%8C%80%ED%95%9C-%EA%B8%B0%EC%B4%88/))
    - redis cluster
        - 장점
            - 자체적인 Primary, Secondary Failover
        - 단점
            - 메모리 사용량이 더 많음

    - Monitoring Factor
        - Redis Info를 통한 정보
            - RSS
            - Used Memory
            - Connection  수
            - 초당 처리 요청
        - System
            - CPI
            - DISK
            - Network traffic
        - Monitor 명령을 통해, 패턴을 분석
        - Write가 Heavy하면 migration도 주의해야함
        - cache용도가 아닌 persistent store로 가면 메모리를 많이 확보하는 방법 밖에 없음