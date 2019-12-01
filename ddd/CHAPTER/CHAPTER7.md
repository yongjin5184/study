### 도메인 서비스
#### 여러 애그리거트가 필요한 기능
* 도메인 영역의 코드를 작성하다 보면 한 애그리거트로 기능을 구현할 수 없을 때가 있다.
* 대표적인 예가 결제 금액 계산. 로직 상품, 주문, 할인 쿠폰, 회원등 여러 애그리거트가 관여 하기 때문
* 한 애그리거트에 넣기 애매한 도메인 기능을 특정 애그리거트에서 억지로 구현하면 안된다.
* 이때 사용하는 방법이 도메인 서비스를 별도로 구현하는 것이다.

#### 도메인 서비스
* 할인 금액 로직을 위한 도메인 서비스는 다음과 같이 도메인의 의미가 드러나는 용어를 타입과 메서드 이름으로 갖는다.
```java
public class DiscountCalculationService {
    public Money calculateDiscountAmounts(
        List<OrderLine> orderLines,
        List<Coupon> coupons,
        MemeberGrade grade
    ) {
        Money couponDiscount = coupons.stream()
                                      .map(coupon -> calculateDiscount(coupon))
                                      .reduce(Money(0), (v1, v2) -> v1.add(v2));
        Money membershipDiscount = calculateDiscount(orderer.getMember().getGrade());
        
        return couponDiscount.add(membershipDiscount);
    }   
    
    private Money calculateDiscount(Coupon coupon){
        ...
    }
    
    private Money calculateDiscount(MemberGrade grade){
        ...
    }
}
```

```java
public class Order {
    public void calculateAmounts (DiscountCalculationService disCalSvc, MemberGrade grade) {
        Money totalAmounts = getTotalAmounts();
        Money discountAmounts = disCalSvc.calculateDiscountAmounts(this.orderLines, this.coupon, grade);
        this.paymentAmounts = totalAmounts.minus(discountAmounts);
    }
}
// 애그리거트 객체에 도메인 서비스를 전달하는 것은 응용 서비스 책임이다.
public class OrderService {
    private DiscountCalculationService discountCalculationService;
    
    @Transactional
    public OrderNo placeOrder(OrderRequest orderRequest) {
        OrderNo orderNo = orderRepository.nextId();
        Order order = createOrder(OrderNo, orderRequest);
        orderRepository.save(order);
        
        return orderNo;
    }
    
    private Order createOrder(OrderNo orderNo, OrderRequest orderReq) {
        Member member = findMember(orderReq.getOrdererId());
        Order order = new Order(orderNo, orderReq.getOrderLines(), 
                orderReq.getCoupons(), createOrderer(member), orderReq.getShippingInfo());
        order.calculateAmounts(this.discountCalculationService, member.getGrade());
        return order;
    }
}
```
* 도메인 서비스 객체를 애그리거트에 주입하지 않기
* discountCalculationService 필드는 데이터 자체와 관련이 없다.
* 아래 코드는 잘못된 것
```java
public class Order {
    @Autowired
    private DiscountCalculationService discountCalculationService;
}
```
* 도메인 서비스는 도메인 영역에 위치한다.
