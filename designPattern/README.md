2019.09.18

1.Command Pattern 
* 객체의 행위(매서드)를 클래스로 만들어 캡슐화 하는 패턴
* A 객체에서 B 객체의 메서드를 호출하려면 A 객체가 B 객체를 참조하고 있어야 한다. 
즉, 의존성이 발생. 이때, Command Pattern 을 이용해 의존성을 제거 할 수 있다. :blush:

2019.10.22

2.Decorator Pattern
* 객체의 결합을 통해 기능을 유연하게 확장할 수 있게 해주는 패턴
* 자바 I/O 클래스 설계에 쓰이는 패턴
* 구조 패턴의 하나

2019.10.23

3.Template Method Pattern
* 틀(탬플릿)을 상속을 이용해 설계하는 패턴
* 공통(중복)사항을 상위 클래스에서 정의하고 하위 클래스에서 구체적 기능을 작성할 경우에 사용
* 행위 패턴의 하나
* 포스팅 : https://yongjin5184.github.io/2019-02-18/template-method

2019.10.24

4.Strategy Pattern
* 전략을 유연하게 바꿀 수 있도록 설계하는 패턴
* 자바는 단일 상속만 가능하다. 
  따라서, 템플릿 메서드 패턴(extends)보다는 전략패턴(implements)을 사용하는 것이 더 나을 수 있다. :+1:

2019.11.04

5.Singleton Pattern

* LazyHolder 기법 
    * "클래스를 로딩하고 초기화 하는 시점은 Thread-safe 를 보장한다"는 점을 활용. 
    * Singleton 클래스에는 LazyHolder 의 클래스 변수가 없기 때문에, Singleton 클래스 로딩 시에는 LazyHolder 클래스를 초기화 하지 않는다.
    * LazyHolder.Instance 를 참조하는 순간 클래스가 로딩되고 초기화 하기 때문에, thread-safe 를 보장한다고 할 수 있다.
    
* Enum 클래스
    * Thread-safe 를 보장한다.
    * Reflection 공격에 안전하다.
    
6.Factory Method Pattern
* 새로운 객체를 만들어 내는 부분을 서브클래스에 위임한는 패턴
* 생성 패턴의 하나

7.Composite Pattern
* 복합 객체와 단일 객체를 클라이언트에서 구별 없이 다루게 해주는 패턴
* 디렉토리안에 파일 / 디렉토리안에 디렉토리를 생각하면 된다.
* 구조 패턴의 하나

2019.11.15

8.Memento Pattern
* [Tutorialspoint 디자인 패턴-Memento 패턴](https://www.tutorialspoint.com/design_pattern/memento_pattern.htm)
* 객체의 상태를 이전 상태로 복원하는데 사용하는 패턴
* 행위 패턴의 하나

* Memento Class 
    * 복원할 객체의 상태를 포함
* Originator
    * 객체에 상태를 생성, 저장
* Caretaker
    * Memento 에서 객체 상태를 복원

2019.11.16    

9.Proxy Pattern
* [Tutorialspoint 디자인 패턴-Proxy 패턴](https://www.tutorialspoint.com/design_pattern/proxy_pattern.htm)
* 외부 세계에 기능을 인터페이스하기 위해 원래 객체를 만들어 반환한다.
* 구조 패턴의 하나
* 결과값을 변경하지 않고, 흐름제어를 위해 사용

2019.11.17

10.Observer Pattern
* [Tutorialspoint 디자인 패턴-Observer 패턴](https://www.tutorialspoint.com/design_pattern/observer_pattern.htm)
* 일대다 의존관계의 경우에 하나의 오브젝트가 수정될 때, 종속 오브젝트에 자동으로 통지되는 패턴
* Subject 가  Observer 를 사용. Client(Abstract) 는 Observer 를 extends 한다.
* 행위 패턴의 하나

11.Adapter Pattern
* [Tutorialspoint 디자인 패턴-Adapter 패턴](https://www.tutorialspoint.com/design_pattern/adapter_pattern.htm)
* 호환성이 맞지 않아 같이 쓸 수 없는 클래스를 연관 관계로 연결해서 사용할 수 있게 하는 패턴
* 구조 패턴의 하나

2019.12.2

12.Facade Pattern
* [Tutorialspoint 디자인 패턴-Facade 패턴](https://www.tutorialspoint.com/design_pattern/facade_pattern.htm)
* 시스템의 복잡성을 숨기기 위해 인터페이스(facade)를 하나 더 추가하는 패턴
* 클라이언트가 시스템에 접근할 수 있는 인터페이스를 제공한다.
* 최소 지식의 원칙
* 구조 패턴의 하나

2019.12.4

12.Bridge Pattern
* [Tutorialspoint 디자인 패턴-Bridge 패턴](https://www.tutorialspoint.com/design_pattern/bridge_pattern.htm)
* 브리지 구조를 제공하여 구현 클래스와 추상 클래스를 분리한다.
* 구조 패턴의 하나

2019.12.16

13.Filter Pattern
* [Tutorialsponits_디자인_패턴-Filter_패턴](https://www.tutorialspoint.com/design_pattern/filter_pattern.htm)
* 객체를 필터링하고 연결하는 패턴
* 구조패턴의 하나

2019.12.17

14. Flyweight Pattern
* [Tutorialsponits_디자인_패턴-Flyweight_패턴](https://www.tutorialspoint.com/design_pattern/flyweight_pattern.htm)
* 생성된 객체의 수를 줄이기 위해 사용 즉, 메모리의 낭비를 줄이고 퍼포먼스를 늘리기 위해 사용
* String 상수 풀은 플라이트웨이트 패턴을 구현
* 구조패턴의 하나


2019.12.18

15. Chain of responsibility Pattern
* [Tutorialsponits_디자인_패턴-Chain_of_responsibility_pattern_패턴](https://www.tutorialspoint.com/design_pattern/chain_of_responsibility_pattern.htm)
* Request 에 대한 Receiver Object Chain 을 작성하는 패턴

2019.12.23

16. Prototype Pattern
* [Tutorialsponits_디자인_패턴-Prototype_패턴](https://www.tutorialspoint.com/design_pattern/prototype_pattern.htm)
* 객체를 직접 만드는데 비용이 많이 드는 경우 사용
* 프로토 타입 인터페이스는 객체의 복제본을 만들도록 구현
* 생성 패턴의 하나

2019.12.26

17. Visitor Pattern
* [Tutorialsponits_디자인_패턴-Visitor_패턴](https://www.tutorialspoint.com/design_pattern/visitor_pattern.htm)
* 객체에 대한 행위의 내용을 외부 클래스로 추출
* 동작 패턴 범주
* [Visitor_패턴](https://blog.naver.com/2feelus/220664244510) 


>> Enum 의 활용
>> * Enum 안의 method 를 override 해서 사용 가능하다.
>> * valueOf method 를 사용 열거형 정수를 가져올 수 있다.

