### Designing Typeahead Suggestion

1. What is Typeahead Suggestion?
    *  Typeahead Suggestion 는 잘알려지고 빈번하게 검색되는 용어를 검색할 수 있도록 한다.

2. Requirements and Goals of the System
    * 기능적 요구사항
        * 사용자가 입력한 것에서 부터 상위 10개 용어를 제안해야한다.
    * 비 기능적 요구사항
        * 제안은 실시간으로 나타나야 한다.

3. Basic System Design and Algorithm
    * 풀려고하는 문제는 prefix 로 검색할 수있도록 저장할 필요가 있는 string 들을 많이 가지고 있는 것이다.
    * 서비스는 prefix 와 일치하는 다음 terms 를 제안할 것이다.
    * 예를 들어 'cap' 를 입력하면 'cap', 'captain', 'capital' 를 제안해야한다.
    * 최소한의 latency 를 위해 Database 에 의존하지 않고 높은 효율의 data structure 로 memory 에 index 를 저장할 필요가 있다.
    * 가장 적절한 data structure 는 Trie

4. Permanent Storage of the TriePermanent Storage of the Trie
    *

5. Scale Estimation
    * 구글과 비슷한 스케일의 서비스를 설계한다고 하면 매일 50억개의 검색이 기대되고 이것은 초당 60K 개의 쿼리를 제공한다.
    * 50억개의 쿼리에 중복되는 부분을 제외하면 20% 만이 유일하다.
    * unique terms 은 1억개라고 가정한다.
    * 스토리지 예측
        * 평균적으로 쿼리가 3 word 로 구성되어 있고 단어의 평균 길이가 5 characters 라면 평균 15 characters.
        * character 를 저장하는데 2 bytes 가 필요하다고 하면 average query 를 저장하는데 30bytes 가 필요하다.
        * 총 스토리지는 1억 * 30바이트 => 3GB
        * 더이상 검색되지 않는 terms 을 삭제, 매일 2% 의 새로운 쿼리를 제공한다고 가정하면 1년간 index 를 유지할 경우 필요한 스토리지는 3GB + (0.02 * 3GB * 365일) => 25GB