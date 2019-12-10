#### IAM 
* IAM 
    * Identity and Access Management
    * AWS 리소스에 대한 액세스를 안전하게 제어할 수 있는 서비스
    * 세분화된 권한 / 멀티 팩터 인증 (MFA)
    
* IAM 모범 사례
    * 루트 사용자 액세스 키 사용하지 않음 / 삭제 권고
    * 그룹을 사용하여 권한 할당
    * 최소 권한 부여
    * MFA 활성화
    
* ID
    * Users
    * Groups
        * A collection of users
    * Policies
        * 정책을 통해 permission 을  Users/Groups/Role 에게 
    * Roles
        * Role 를 통해 Aws Resources 가 할당됨.
        
#### S3
* S3 
    * Simple Storage Service 
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
        
#### EC2
* EC2
    * Elastic Compute Cloud

* Pricing Models
    * On demand
        * 사용에 따라, 시간/초 단위의 고정된 비율의 요금제
        
    * Reserved
        * capacity reservation 에 따른 요금제
        * 사용량을 예측할 수 있을때 사용
        
    * Spot
        * application 이 유연한 시작/끝 시간이 있을 때 순간적으로 사용하는 요금제
        * 아마존의 공급과 수요에 달려있음
        
    * Dedicated Hosts
        * 전용 호스트 요금제
        * 기존 서버에 한정된 소프트웨어 라이센스를 사용할 수 있음.

#### VPC
* VPC 
    * AWS Cloud 내부에서 구성되는 사용자의 AWS 계정 전용 가상 네트워크
    * 다른 가상 네트워크와 논리적으로 분리. 즉, 논리적으로 격리된 공간을 프로비저닝
    * 하나의 리전내에서만 생성 가능
    * 대체적으로 Multi AZ 기반으로 구성
    * 다수의 Subnet 을 가질 수 있음

* VPC Flow Log
    * VPC 의 네트워크 인터페이스에서 전송되고 수신되는 IP 트래픽에 대한 정보를 수집할 수 있는 기능

* VPC Peering Connection
    * 서로 다른 VPC 끼리 통신하는 기능

* VPC End Point
    * 게이트웨이 및 NAT 인스턴스를 필요로하지 않고 AWS 서비스 및 VPC 엔드포인트 서비스에 비공개로 연결할 수 있음
    
* Subnet
    * VPC 안에서 실제로 리소스가 생성될 수 있는 네트워크
    * 하나의 AZ에 속해야 함
    * 넷마스크 범위 2^16(65535개) ~ 28(16개)
    * 서브넷을 만들지 않을 수도 있지만, VPC 로 아무것도 할 수 없음
    * Public Subnet
        * Internet Gateway, ELB, Public IP/Elastic IP를 가진 인스턴스를 내부에 가지고 있음 
        * Public Subnet 내에 있는 Nat Instance 를 통하여 Private Subnet 내에 있는 instances 이 인터넷이 가능
    * Private Subnet
        * 기본적으로 외부와 차단
        * private ip 만을 가지고 있으며 internet inbound/outbound 가 불가능, 오직 다른 서브넷과의 연결 가능

* Internet Gateway
    * VPC 에 생성된 instance 들은 기본적으로 인터넷 사용 불가, 따라서 인터넷 게이트웨이가 필요
    * 라우팅 테이블에 인터넷 게이트웨이를 향하는 규칙을 추가하면 인터넷과 연결,(대상주소: 0.0.0.0/0 대상: 인터넷 게이트웨이)
    * 인터넷을 사용하고자 하는 리소스는 퍼블릭 IP를 가지고 있어야 함
    * Subnet 을 외부 통신이 되도록 설정 가능
    * Internet Gateway 을 VPC 에 Attach
    * VPC 에는 하나의 IGW 만 attach 할 수 있음
    * Route table 을 생성
    
* Route Table
    * 서브넷과 연결되어 있는 리소스, 서브넷에서 네트워크를 이용할 때 라우트 테이블을 이용해 목적지를 찾음
    * 하나의 라우트 테이블은 VPC 에 속한 다수의 서브넷에서 사용
    
* CIDR 
    * VPC 와 Subnet 을 구성 할때 반드시 설정해야 함
     
* Network ACL (Access Control List)
    * VPC 의 Network ACL 은 Subnet 단위로 적용시킬 수 있.
    * ACL 은 여러 서브넷에 적용이 가능하다. 하지만, 서브넷은 한번에 한개의 ACL 만 연결이 가능
    
* Security Group
    * Security Group 은 instance 단위로 적용시킬 수 있음
    * CL의 경우 Network 레벨에서의 방화벽이라면, Security Group은 인스턴스 레벨의 방화벽
     
* NAT Gateway
    * private Subnet 에서 다른 AWS 서비스에 연결해야하는 경우
    * 인터넷에서 Private instance 에 접근 불가 조건은 유지하면서 반대로 instance 에서 외부 인터넷으로 연결이 필요한 경우