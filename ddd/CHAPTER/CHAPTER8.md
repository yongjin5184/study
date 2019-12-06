### 애그리거트 트랜잭션 관리
#### 애그리거트와 트랜잭션
* 애그리거트에 대해 사용할 수 있는 대표적인 트랜잭션 처리 방식에는 선점 잠금과 비선점 잠금의 두 가지 방식이 있다.

#### 선점 잠금
* 선점 잠금은 보통 DBMS 가 제공하는 행 단위 잠금을 사용해서 구현한다.
* JPA 의 EntityManager 는 LockModeType 을 인자로 받는 find() 메서드를 제공하는데 LockModeType.PESSIMISTIC_WRITE 를 값으로 전달하면
해당 엔티티와 매핑된 테이블을 이용해서 선점 잠금 방식을 적용할 수 있다.
```java
    Order order = entityManager.find(Order.class, orderNo, LockModeType.PESSIMISTIC_WRITE);
```

#### 선점 잠금과 교착 상태
* 교착상태에 빠지지 않도록 하려면 잠금을 구할 때 최대 대기 시간을 지정해야 한다.
```java
    Map<String, Object> hints = new HashMap<>();
    hints.put("javax.persistence.lock.timeout", 2000);
    Order order = entityManager.find(Order.class, orderNo, LockModeType.PESSIMISTIC_WRITE, hints)
```

#### 비선점 잠금
* 비선점 잠금 방식은 잠금을 헤서 동시에 접근하는 것을 막는 대신 변경한 데이터를 실제 DBMS에 반영하는 시점에 변경 가능 여부를 확인하는 방식이다.
```java
    UPDATE aggtable SET version = version + 1, colx = ?, coly = ? 
    WHERE aggid = ? and version = 현재 버전
```

*JPA 는 버전을 이용한 비선점 잠금 기능을 지원한다.
```java
    @Entity
    @Table(name = "purchase_order")
    @Access(AccessType.FIELD)
    public class Order {
        @EmbeddedId
        private OrderNo number;
        
        @Version
        private long version;
    }
```

* 오프라인 선점 잠금
    * 단일 트랜잭션에서 동시 변경을 막는 선점 잠금 방식과 달리 오프라인 선점 잠금은 여러 트랜잭션에 걸쳐 동시 변경을 막는다.
    * 첫 번째 트랜잭션을 시작할 때 오프라인 잠금을 선점하고, 마지막트랜잭션에서 잠금을 해제한다. 잠금을 해제하기 전까지 다른 사용자는 잠금을 구할 수 없다.