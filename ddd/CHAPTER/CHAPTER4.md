### 리포지터리와 모델구현 (JPA 중심)
#### 엔티티와 밸류 기본 맵핑 구현
* 애그리거트 루트는 엔티티이므로 @Entity 로 맵핑 설정한다.
* 한 테이블에 엔티티와 밸류 데이터가 같이 있다면,
    * 밸류는 @Embeddable 로 맵핑 설정한다.
    * 밸류 타입 프로퍼티는 @Embeddable 로 맵핑 설정한다.

#### 기본 생성자

* Receiver 밸류 타입의 경우, 불변 타입이더라도 하이버네이트와 같은 JPA 프로바이더는 DB 에서 데이터를 읽어와 매핑된 객체를 생성할 때 기본 생성자를 사용해서 객체를 생성한다.
* 이런 기술적 제약으로 기본 생성자를 추가해야 한다.
```java
    @Embeddable
    public class Receiver {
        @Column(name = "receiver_name")
        private String name;
        @Column(name = "receiver_phone")
        private Stirng phone;
        // JPA를 적용하기 위해 기본 생성자 추가
        protected Receiver() {}
        public Receiver(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }
    }
```

#### 밸류 컬렉션 : 별도 테이블 맵핑
* Order 엔티티는 한 개 이상의 OrderLine 을 가질 수 있다.
* 밸류 컬렉션을 별도 테이블로 매핑할 때는  @ElementCollection 과 @CollectionTable 을 함께 사용한다.
* OrderColumn 애노테이션을 이용해서 지정한 컬럼에 리스트의 인덱스를 저장한다.
* CollectionTable 은 밸류를 저장할 테이블을 지정할 때 사용한다.
```java
    import javax.persistence.*;

    @Entity
    @Table(name = "purchase_order")
    public class Order {
        @ElementCollection
        @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name = "order_name"))
        @OrderColumn(name = "line_idx")
        private List<OrderLine> orderLines;
    }

    @Embeddable
    public class OrderLine {
        @Emdedded
        private ProductId productId;
        
        @Column(name = "price")
        private Money price;

        ...
    }
```

#### 밸류를 이용한 아이디 맵핑

* 밸류 타입을 식별자로 매핑하면 @Id 대신 @EmbeddedId 를 사용한다.
```java
@Entity
@Table(name = "purchase_order")
public class Order {
    @EmbeddedId
    private OrderNo number;
}

@Embeddable
public class OrderNo implements Serializable {
    @Column(name="order_number")
    private String number;
    
    //ex) 2세대 시스템의 주문번호를 구분할 수 있는 기능이 가능하다.
    public boolean is2ndGeneration() {
        return number.startsWith("N");    
    }
}

```

#### 별도 테이블에 저장하는 밸류 맵핑

* 애그리거트에서 루트 엔티티를 뺀 나머지 구성요소는 대부분 밸류이다.
* 루트 엔티티외에 또 다른 엔티티가 있다면 진짜 엔티티인지 의심해봐야 한다.
* 애그리거트에 속한 객체가 밸류인지 엔티티인지 구분하는 방법은 고유 식별자를 갖는지 여부를 확인하는 것이다.