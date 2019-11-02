#### 밸류 타입

```
public class ShippingInfo {
    private String receiverName;        -- 받는 사람
    private STring receiverPhoneNumber;

    private String shippingAddress1;    -- 주소
    private String shippingAddress2;
    private String shippingZipcode;
}
```

받는 사람과 주소를 개념적으로 묶는다. 즉, 도메인으로 표현한다. 
```
public class Receiver { 
    private String name;
    private String PhoneNumber;
    
    public Receiver(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
```

```
public class Address {
    private String address1;
    private String address2;
    private String zipcode;
    
    public Address(String address1, String address2, String zipcode) {
        this.address1 = address1;
        this.address2 = address2;
        this.zipcode = zipcode;
    }
    ...
}
```

```
public class shippingInfo {
    private Receiver receiver;
    private Address address;
}
```

의미를 명확하게 하기위해 밸류 타입을 사용하는 경우도 있다.
```
public class OrderLine {
    private Product product;
    private int price;
    private int quantity;
    private int amounts;
}
```

OrderLine의 price와 amounts는 int 타입의 숫자를 사용하고 있지만 이들이 의미하는 값은 '돈' 이다. 
```
public class Money {
    private int value;
    
    public Money(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
    /*밸류 타입을 사용하게 되면 벨류 타입을 위한 기능도 추가할 수 있다.*/
    public Money add(Money money) {
        return new Money(this.value + money.value);    
    }
    
    public Money multiply(int multiplier) {
        return new Money(this.value * multiplier);
    }
}
```

```
public class OrderLine {
    private Product product;
    private Money price;
    private int quantity;
    private Money amounts;
}
```

#### 도메인 모델에 set 메서드 넣지 않기

생성자를 통해 필요한 데이터를 모두 받을 수 있도록 하자.
```
public class Order {
    public Order(Orderer orderer, List<OrderLine> orderLines, 
                ShippingInfo shippingInfo, OrderState orderState) {
        setOrderer(orderer);
        setOrderLines(orderLines);    
    }

    private void setOrderer(Orderer orderer) {
        if (orderer == null) throw new IllegalArgumentException("no orderer");
        this.orderer = orderer;
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (orderLines == null || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
    }

    private void calculateTotalAmounts() {
        this.totalAmount = orderLines.stream().mapToInt(x -> x.getAmounts()).sum();
    }
}
```