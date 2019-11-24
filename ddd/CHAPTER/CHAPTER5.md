### 리포지터리의 조회 기능 (JPA 중심)
#### 검색을 위한 스펙

* 리포지터리는 애그리거트의 저장소
* 식별자외에 여러 다양한 조건으로 애그리거트를 찾아야 할 때가 있다.
```java
public interface OrderRepositroy {
    ORder findById(OrderNo id);
    List<Order> findByOrderer(String ordererId, Date fromDate, Date toDate);
}
```

* 검색 조건의 조합이 다양해지면 모든 조합별로 find 매서드를 정의할 수 없다.
* 스펙은 애그리거트가 특정 조건을 충족하는지 여부를 검사한다.
```java
public interface Specification<T> {
    public boolean isSatisfiedBy(T agg);
}
```

* Ex) Order 애그리거트 객체가 특정 고객의 주문인지 확인하는 스펙
```java
public class OrdererSpec implements Specification<Order> {
    private String ordererId;
    public OrdererSpec(String ordererId) {
        this.ordererId = ordererId;
    }
    
    public boolean isSatisfiedBy(Order agg){
        return agg.getOrdererId().getMemberId().getId().equals(ordererId);
    }
}

public class MemoryOrderRepository implements OrderRepository {
    public List<Order> findAll(Specification spec) {
        List<Order> allOrders = findAll();
        return allOrders.stream().filter(order -> spec.isSatisfiedBy(order)).collect(toList());
    }
}
```