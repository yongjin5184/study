1. 트위터 검색이란?
* 트위터 유저는 자신들이 원할 때 언제든 상태 업데이트를 할 수 있다.
* 목표는 모든 사용자의 트윗을 검색할 수 있는 시스템을 설계하는 것이다.

2. 시스템의 요구 사항 및 목표
* 트위터의 총 사용자 수는 15억명, Active User 는 8억명이라고 가정하자.
* 평균 4억개의 트윗이 만들어 진다.
* 트윗의 평균 크기는  300 bytes.
* 매일 5억 건의 검색이 발생한다고 가정.
* 검색어는 AND / OR 과 결합된 여러 단어로 구성되어 있다.

결론적으로, 트윗을 효율적으로 저장하고 쿼리할 수 있는 시스템 디자인이 필요하다.

3. 용량 추정 및 제약
* Storage Capacity 
    * 4억개 트윗 * 300 bytes => 120GB/day


* Total storage per second
    *  120GB / 24hours / 3600sec ~= 1.38MB/second
    
4. System APIs
* search(api_dev_key, search_terms, maximum_results_to_return, sort, page_token)
* api_dev_key (string)
    * 등록된 계정의 API developer key 
* search_terms(string)
    * 검색어를 포함하는 문자열
*maximum_results_to_return(number)
    * 리턴되는 트윗의 수
* maximum_results_to_return(number)
    * sort mode
* page_token(sting)
    * page
    
* return (json)
    * 쿼리 결과가 매치된 트윗 목록에 대한 정보가 포함된 json
    * 각 result entry 는 userID, name, tweet text, tweet ID 등등을 가질 수 있다.
    
5. 고수준 디자인
* 모든 트윗을 데이터 베이스에 저장하고, 어떤 트윗에 어떤 단어가 있는지 추적 가능한 인덱스를 만들어야 한다.
* 인덱스는 검색을 빠르게 할 수 있도록 돕는다.

6. 상세 컴포넌트 디자인
* 1) Storage 
    * 매일 120 GB 새로운 데이터를 저장해야 한다.
    * 효율적으로 데이터를 여러 서버에 분산할 수 있는 파티셔닝 스키마가 필요하다.
    * 5년 계획을 세웠을 때, 120GB * 365days * 5years ~= 200TB
    * 스토리지가 어떤 순간에도 80% 이상 가득차지 않으려면 적어도 250TB 가 필요하다.
    * 내결함성(fault tolerance)을 위해 데이터 복제본을 저장한다면 500TB 가 필요하다.
    * 현대 서버가 데이터의 4TB 을 저장한다고 가정한다면, 5년간 123대의 서버가 필요하다.
    * 단순 디자인으로 TweetID 와 TweetText 의 두개의 열이 있는 테이블에 데이터를 저장한다.
        * TweetID 를 유니크하게 할 수 있다면, 스토리지 서버에 TweetID 로 맵핑할 수있다.
        * 그렇다면, 어떻게 TweetID 를 시스템에서 유니크하게 만들 수 있을까?
            * 400M (하루 새로운 트윗) * 365 days * 5 years => 730 billion 
            * TweetID 를 고유하게 식별할 수 있는 5 bytes 의 수가 필요하다.
            * Hash function 을 통해 유니크한 값을 제공받는다. 
* 2) Index
    * 트윗 queries 는 단어로 구성되어있다. 그래서, 어떤 단어가 어떤 Object 에 속해 있는지 알려주어야 한다.
    * index 가 얼마나 클지 추정해보자.
        * 모든 영단어로 만들기를 원한다면 300K 영단어와 200K 의 명사 총 500K.
        * 500K * 5 (평균 5 character 라고 가정) 필요한 메모리에 공간은 2.5MB.
    * 2년 동안 모든 트윗의 인덱스를 메모리에 유지한다고 가정해보자.
        * 5년 -> 730B 
        * 2년 -> (730B / 5) * 2 = 293B
        * TweetID 5byte
            * TweetID를 저장하는데 필요한 메모리는 295B * 5 => 1460GB   
    * 분산 해시 테이블을 얻을 수 있다. 
        * Key 는 단어, Value 는 해당 단어를 포함하는 TweetID 의 리스트
        * 트윗은 대략 40개의 단어를 갖는데, 'the', 'an' 등등의 작은 단어를 제외하면 대략 15개의 단어르 갖는다.
        * 이는, 각 TweetID 가 index 에 15번 저장된다는 걸 의미한다.
        * (1460 * 15) + 2.5MB ~ = 21TB
        * 높은 사양의 서버가 144GB 의 메모리를 갖는다고 가정하면, 152개의 서버가 필요하다.
    * 두 가지 Sharding 방법
        * Sharing based on Words 
            * 인덱스를 작서앟는 동안 모든 단어를 반복하고 각 단어의 해시를 계산해서 인덱스 될 서버를 찾는다.
            * 이 접근법의 두 가지 문제               
                * 단어를 많이 찾는다면 해당 단어를 보유한 서버에 부하가 있을 수 있다.
                * 단어의 균일 한 분포를 유지하는 것이 매우 어렵다.
            * 데이터를 다시 파티션 하거나 Consistent Hashing 를 사용해야 한다.
        * Sharding based on the tweet object
            * 저장하는 동안 TweetID를 hash 함수로 던져 server 를 찾는다.
            * 특정단어를 찾으려면 모든 서버에 쿼리해야 한다. 각 서버는 TweetID 들의 Set 를 리턴한다.
            * 중앙 서버는 결과를 aggregate 해서 유저에게 리턴한다.
        
7. 내결함성
    * 인덱스 서버가 죽으면 어떤일이 벌어질까?
        * 각 서버의 primary server 가 죽으면 조치할 수 있는 Second replica 을 가진다.
    * 두 서버가 동시에 죽으면 어떻게 할까?
        * 새로운 서버를 띄우고 같은 인덱스를 rebuild 해야한다.
    * 트윗과 인덱스 서버 간의 매핑을 효율적으로 검색하려면 어떻게 해야 할까?
        * 모든 TweetID 를 인덱스 서비에 맵핑하는 역 인덱스를 만들어야 한다.
        * key 는 인덱스 서버 번호, value 는 인덱스 서버에 유지된 TweetID 를 포함하는 HashSet.

8. Cache 
    * Memcached 를 사용해서 많이 사용되는 트윗을 메모리에 저장.
    
9. 로드 밸런싱
    * 로드 밸런싱 위치 1)클라이언트와 응용 프로그램 서버 와 2) 응용 프로그램 서버와 백엔드 서버간
    * 기본적으론 Round Robin LB. 단 서버에 과부하가 걸리면 다른 서버로 요청을 보내는 기능을 수행하기 위해선, 
    백엔드 서버에 주기적으로 쿼리하고 이를 기반으로 트래픽을 조정하는 LB 솔루션을 배치

10. 순위
    * 인기에 따라 트윗에 랭킹을 매기기를 원한다면
        * aggregator server 는 모든 결과를 결합하고 인기도를 기준으로 정렬해서 결과를 사용자한테 보낸다. 