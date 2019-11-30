### 응용 서비스와 표현 영역
#### 표현 영역과 응용 영역

* 도메인 영역만 잘 만든다고 끝나는 것은 아님. 사용자와 도메인을 연결해주는 매캐체가 필요하다.
* 응용 영역과 표현 영역이 사용자와 도메인을 연결해 주는 매개체 역할을 한다.

* 표현영역
    * HTTP 요청이 표현영역에 전달
    * 어떤 기능을 실행하고 싶어 하는지 판별하고 그 기능을 제공하는 응용 서비스를 실행
    * 응용 서비스가 요구하는 형식으로 사용자 요청을 변환
* 응용영역
    * 실제 사용자가 원하는 기능을 제공하는 것은 응용영역에 위치한 서비스

#### 응용 서비스의 역할

* 표현 영역 입장에서 보았을 때 응용 서비스는 도메인 영역과 표현 영역을 연결해 주는 창구인 **파사드 역할**을 한다.
* 도메인 객체간의 흐름 제어, 트랜젝션, 접근 제어, 이벤트 처
```java
public Result doSmeFunc(Some Req) {리
    // 1. 리포지터리에서 애그리거트를 구한다.
    SomeAgg agg = someAggRepository.findById(req.getId());
    checkNull(agg);
    
    // 2. 애그리거트의 도메인 기능을 실행한다.
    agg.doFunc(req.getValue());
   
    // 3. 결과를 리턴한다.
    return createSuccessResult(agg);
}
```

### 도메인 로직 넣지 않기
* 도메인 로직은 도메인 영역에 위치하고 응용 서비스는 도메인 로직을 구현하지 않아야 한다.
* 도메인 로직은 두 가지 이점이 있다.
    * 높은 응집도
    * 코드 중복을 제거
```java
public class ChangePasswordService {
    
    public void changePassword(String memberId, String oldPw, String newPw) {
        Member member = memberRepository.findById(memberId);
        checkMember(member);
        member.changePassword(oldPw, newPw);
    }
}
```

```java
public class Member {
    public void changePassword(String oldPw, String newPw) {
        if(!matchPassword(oldPw)) throw new BadPasswordException();
        setPassword(newPw);        
    }   

    public boolean matchPassword(String pwd) {
        return passwordEncoder.matches(pwd);
    }
    
    private void setPassword(String newPwd) {
        if(isEmpty(newPw)) throw new IllegalArgumentException("no new password");
        this.password = newPw;
    }
}
```

### 응용 서비스의 크기
* 각 클래스 마다 구분되는 역할을 갖는 설계  
* 기능을 하나의 응용서비스 클레스에서 모두 구현하는 방식보다 구분되는 기능을 별도의 서비스 클래스로 구현하는 방식이 낫다.
```java
//각 응용 서비스에서 공통되는 로직을 별도 클래스로 구현
import static com.~~~.application.MemberServiceHelper.*
public class ChangePasswordService {
    private MemberRepository memberRepository;
    
    public void changePassword(String memberId, String curPw, String newPW) {
        Member member = findExistingMember(memberRepository, memberId);
        member.changePawssword(curPw, newPw);
    }
}
```

```java
// 공통 로직을 제공하는 메서드를 응용 서비스에서 사용
public final class MemberServiceHelper {
    public static Member findExistingMember(MEmberRepository repo, String memberId) {
        Member member = memberRepository.findById(memberId);
        if(member == null) 
            throw new NoMemberException(memberId);
        return member;
    }
}
```

### 응용 서비스의 인터페이스와 클래스
* Service 를 구현한 ServiceImpl 가 필요한지 여부에 대해 생각해 볼 것
* 구현 클래스가 다수 존재하거나 런타임에 구현 객체를 교체해야할 경우 인터페이스가 유용하게 필요하다.
* 응용 서비스는 보통 런타임에 이를 교체하는 경우가 거의 없을 뿐만 아니라 한 응용 서비스의 구현 클래스가 두 개인 경우도 매우 드물다.

### 메서드 파라미터와 값 리턴
* 스프링을 이용해 요청 파라미터를 자바 객체로 변환해 주는 기능을 사용하면 편리
* 주문 기능 실행 후, 결과 데이터로 주문번호를 보여주는 기능
```java
// 주문번호만을 리턴해주는 서비스
public class OrderService {
    @Transactional
    public OrderNo placeOrder(OrderRequest orderRequest) {
        OrderNo orderNo = orderRepositroy.nextId();
        Order order = createOrder(orderNo, orderRequest);
        orderRepository.save(order);
        return orderNo;
    }
}

//주문 애그리거트를 리턴해주는 서비스
//표현 영역에서 도메인 로직을 실행 할 수 있기 때문에 응집도를 낮추는 원인이 될 수 있음
public class OrderService {
    @Transactional
    public Order placeOrder(OrderRequest orderRequest) {
        ...
        return Order;
    }
}
```

### 표현 영역에 의존하지 않기
* 응용 서비스의 파라미터 타입을 결정할 때, 주의할 점은 표현 영역과 관련된 타입을 사용하면 안된다.
* HttpServletRequest, HttpSession 을 응용서비스에서 파라미터로 전달하면 안된다.

### 트랜잭션 처리
* @Transactional 을 사용하여 트랜잭션을 관리하는 것은 응용 서비스의 중요한 역할이다.

### 도메인 이벤트 처리
* 암호 초기화 기능은 다음과 같이 암호 변경 후에 '암호 변경됨' 이벤트를 발생시킬 수 있다.
* 도메인간의 의존성이나 외부시스템에 대한 의존을 낮춰줄 수 있다.
```java
public class Member {
    private Password password;
    
    public void initializePassword() {
        String newPassword = generateRandomPassword();
        this.password = new Password(newPassword);
        Events.raise(new PasswordChangedEvent(this.id, password));    
    }
}

public class InitPasswordService {
    @Transactional
    public void initializePassword(String memberId) {
        Event.handle((PasswordChangedEvent evt) -> {
            //회원에게 이메일 발송하는 기능 구현
        });
    }
}
```

#### 표현 영역의 역할
* 표현 영역의 책임
    * 사용자가 시스템을 사용할 수 있는 (화면) 흐름을 제공하고 제어한다.
    * 사용자의 요청을 알맞은 응용 서비스에 전다류ㅏ고 결과를 사용자에게 제공한다.
    * 사용자의 세션을 관리한다.

### 값 검증
* 표현 영역 : 필수 값, 값 형식, 범위 등을 검증한다.
* 응용 서비스 : 데이터의 존재 유무와 같은 노리적 오류를 검증한다.

### 권한 검사
* 표현 영역에서 할 수 있는 가장 기본적인 검사는 인증된 사용자인지 아닌지 여부를 검사하는 것이다.
* 대표적인 예가 회원 정보 변경 기능이다.
    * 회원 정보 변경을 처리하는 URL 에 대해 컨트롤러에 웹 요청을 전달하기 전에 서블릿 필터를 통해 인증된 사용자의 웹 요청만 컨트롤러에 전달한다.
    * 인증된 사용자가 아닐 경우 로그인 화면으로 리다이렉트 시킨다.
    
### 조회 전용 기능과 응용 서비스
* 조회 화면만을 위해 별도 조회 전용 모델과 DAO 를 만든다면 반드시 응용 서비스가 존재할 필요는 없다. 