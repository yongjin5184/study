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
