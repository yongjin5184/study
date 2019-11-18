#### S3
* S3 
    * Object-based 
    * 99.99 availability / 11 9's durability
    * 0 bytes to 5TB 
    * S3 name space 는 globally unique 함
    * 업로드가 성공하면 HTTP 200 status code 가 생성됨
    * 리전에 저장된 객체는 다른 리전으로 전송하지 않는 한 해당 리전을 벗어나지 않음
    
* S3 Storage Classes

    * S3 Standard
        * 기본 스토리지 클래스
        
    * S3 Standard IA (Infrequent Access)
        * 수명이 길고, 자주 액세스 하지 않는 데이터용으로 설계
        * 지리적으로 분리된 여러개의 가용영역에 중복되게 저장
        
    * S3 OneZone IA
        * 수명이 길고, 자주 액세스 하지 않는 데이터용으로 설계
        * 하나의 가용 영역에만 저장
        
    * Intelligent tiering 
        * 비용에 최적화 설계
        * 액세스 패턴이 바뀔 때 마다, 티어 사이에서 데이터를 이동하여 자동 비용 절감 효과를 제공
        * Machine learning 사용
        
    * S3 Glacier
        * 데이터 아카이빙용
        * 분 단위로 데이터의 일부를 검색해야하는 아카이브에 사
        
    * S3 Glacier Deep Archive
        * 데이터 아카이빙용, 거의 액세스할 필요가 없는 데이터를 아카이빙 할 때 사용
        * 기본 검색 시간은 12시
        * 가장 저렴한 스토리지 옵션 