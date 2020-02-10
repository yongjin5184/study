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
