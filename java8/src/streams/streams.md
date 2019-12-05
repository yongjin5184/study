### Java 8 In Action
4.1 스트림이란 무엇인가?
* 스트림은 자바 API에 새로 추가된 기능으로, 스트림을 이용하면 선언형(즉, 데이터를 처리하는 임시 구현 코드 대신 질의로 표현할 수 있다)으로 컬렉션 데이터를 처리할 수 있다.
* 데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소.
* 컬렉션의 주제는 데이터, 스트림의 주제는 계산.

4.3 스트림과 컬렉션
* 데이터를 언제 계산하느냐가 컬렉션과 스트림의 가장 큰 차이라고 할 수 있다. 컬렉션은 현재 자료구조가 포함하는 모든 값을 메모리에 저장하는 자료구조다.
반면 스트림은 이론적으로 요청할 때만 요소를 계산하는 고정된 자료구조다.
* 스트림은 단 한번만 소비할 수 있다.
* 스트림은 내부 반복을 사용한다.
    * 병렬로 처리할 수 있다. (병렬성을 스스로 관리하지 않아도 된다.)
    * 더 최적화된 다양한 순서로 처리할 수 있다. 
* 중간연산 : 파이프라인을 구성, 어떤 결과도 생성할 수는 없다.
    * filter, map, limit, sorted, distinct
* 최종연산 : 스트림이 아닌 결과를 반환하는 연산이다.
    * forEach, count, collect

5.4 리듀싱 연산
* 모든 스트림 요소를 처리해서 값으로 도출하는 연산.
* 마치 종이를 작은 조각이 될 때까지 반복해서 접는 것과 비슷하다는 의미로 폴드라고 부른다.
* 장점
    * reduce 를 이용하면 내부 반복이 추상화되면서 내부 구현에서 병렬로 reduce 를 실행할 수 있게 된다. 반복적인 합계에서는 sum 변수를 공유해야 하므로 쉽게 병렬화하기 어렵다.
### 가장 빨리 만나는 코어 자바 9
8.스트림 
- 반복자는 특정 순회 전략을 내포하므로 효율적인 동시 실행을 방해한다.

스트림-컬렉션 차이
* 스트림은 요소를 저장하지 않는다.
* 스트림 연산은 원본을 변경하지 않는다. filter 메서드는 스트림에서 요소를 지우는 것이 아니라 해당 요소가 없는 새 스트림을 돌려준다.
* 스트림 연산은 가능하면 지연 방식으로 작동한다.

8.3 filter, map, flatMap 메서드
* filter : 스트림에 들어 있는 요소에서 파생한 요소의 스트림을 만들어 낸다.
* map : 스트림에 들어 있는 값을 특정 방식으로 변환하고 싶을 때 사용한다. 해당 변환을 수행하는 함수를 map 메서드에 전달하면 된다.
* flatMap : 단일 스트림으로 펼치기 위해 사용.
    *  monad 이론의 핵심 아이디어 : T 타입을 G<U>로 변환하는 함수 f. U 타입을 G<V>로 변환하는 함수 g가 있다고 하자. 그러면 이 함수들을 flatMap으로 합성할 수 있다.
    (즉, 먼저 f를 적용한 후 g를 적용한다.)

8.4 서브스트림 추출과 스트림 결합
* stream.takeWhile(predicate) 호출은 프레디케이트가 참인 동안 스트림에서 모든 요소를 가져온 후 중단한다.

8.5 기타 스트림 변환
* distinct : 원본 스트림에 있는 요소의 중복을 제외하고 같은 순서로 돌려주는 스트림을 반환한다.
* sorted : 정렬, 그중 Comparable 요소로 구성된 스트림에 작동하는 것, Comparator를 받는 것.
ex) Stream<String> longestFirst = words.stream().sorted(Comparator.comparing(String::length).reversed());

### 블로그

#### 스트림 생성
* 배열 
```
String[] arr = new String[]{"a", "b", "c"};
Stream<String> stream = Arrays.stream(arr);
Stream<String> streamOfArrayPart = Arrays.stream(arr, 1, 3);
```

* 컬렉션
```
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> stream = list.stream();
```

* 기본 타입 스트림
```
IntStream intStream = IntStream.range(1, 5); // [1, 2, 3, 4]
LongStream longStream = LongStream.rangeClosed(1, 5); // [1, 2, 3, 4, 5]
```

* 문자열 스트림 - 정규표현식으로 문자열을 자른 후, 스트림 생성
```
Stream<String> stringStream = Pattern.compile(", ").splitAsStream("Java, Php, Ruby, Python"); // [Java, Php, Ruby, Python]
```

#### 스트림 가공
```
List<String> list = Arrays.asList("Java", "Python", "Ruby");
```

* Filter : 스트림 내 요소를 하나씩 필터링.
```
Stream<String> stream = names.stream().filter(name -> name.contain("a"));
```

* Map : 스트림 내 요소들을 하나씩 특정 값으로 변환.
```
Stream<String> stream = names.stream().map(String::toUpperCase);
```

* FlatMap : 중첩 구조를 제거 단일 컬렉션으로 변환.
```
String[][] sample = new String[][]{
  {"a", "b"}, {"c", "d"}, {"e", "a"}, {"a", "h"}, {"i", "j"}
};

Stream<String> stream = sample.stream()
.flatMap(array -> Arrays.stream(array)).filter(x-> "a".equals(x));
stream.forEach(System.out::println);

//output
a
a
a
```

* Sort
```
List<String> language = Arrays.asList("Java", "Python", "Php", "Ruby");
language.stream().sorted().collect(Collector.toList());
language.sorted(Comparator.reverseOrder()).collect(Collectors.toList);
```

#### 스트림 결과 만들기
* Count, Sum, Min, Max
```
//스트림이 비어있다면 0을 반환
long count = IntStream.of(1, 3, 5, 7, 9).count();
long sum = LongStream.of(1, 3, 5, 7, 9).count();

// Optional을 반환
OptionalInt min = IntStream.of(1, 3, 5, 7, 9).min();
OptionalInt max = IntStream.of(1, 3, 5, 7, 9).max();
```

* Collecting
    * 리스트로 반환 : Collectors.toList();
    * 스트링으로 이어 붙이기 : Collectors.joining();
    
* Matching
    * 조건을 하나라도 만족하는지 : anyMatch
    * 조건을 모두 만족하는지 : allMatch
    * 조건을 모두 만족하지 않는지 : noneMatch

* Iterating
```
language.stream().forEach(System.out::println);
```