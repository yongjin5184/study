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

###응용 서비스의 크기
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