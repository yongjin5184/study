* 스프링캠프 2017 - 이벤트 소싱
    * 데이터를 저장하는 방식
    * 상태를 저장한 후에 로그를 기록하는게 아니고, 어떠한 변화를 나타내는 이벤트를 저장소에 기록하고, 일련 이벤트를 재생해서 상태를 만들어 내는 것 
    * 이벤트는 삭제되거나 수정되지 않는다. 추가만 된다.
    * 사용 예) 버전관리 시스템
    * event store : 수많은 이벤트 스트림으로 구성
    * 명령(Command) 은 검증 대상, 네이밍 명령형 동사를 사용
    * 이벤트(Event) 은 이미 지나간 사실, 돌이킬 수 없다. 네이밍 과거형 동사를 사용
    * 저장 Key(Object Id Version), Value (Event type Serialized Payload)
        * ex) id: d859, version : 4, Event type: ShoppingCartItemAdded, Payload : {"itemId" : 1}
    * Event sourcing 은 CQRS 와 같이 사용
        