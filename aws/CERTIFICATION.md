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
    
* 용어

    |이름|설명|
    |:---------------------------------------------------------------------------------:|:---------------------------------------------------------------|
    |권한|AWS 의 서비스나 자원에 어떤 작업을 할 수 있는지 명시해두는 규칙이다. 예를 들어, "서울 리전에 있는 모든 EC2를 조회할 수 있다"와 같은 항목이 하나의 권한이 된다.|
    |정책|권한들의 모음이다. 사용자나 그룹들에 권한을 직접 적용할 수는 없고 권한들로 만든 정책을 적용해야한다.|
    |사용자|사용자는 AWS 의 기능과 자원을 이용하는 객체다. 접속하는 사용자인 경우에는 비밀번호가 제공되지만, 프로그램인 경우에는 액세스키 ID 와 비밀 액세스키가 제공된다.|
    |그룹|여러 사용자에게 공통으로 권한을 부여할 수 있게 만들어진 개념이다. 하나의 그룹에 여러 명의 사용자를 지정할 수 있다.|
    |역할|어떤 행위를 하는 객체에 여러 정책을 적용한다는 점에서 사용자와 비슷하지만 객체가 사용자가 아닌 서비스나 다른 AWS 계정의 사용자라는 점에서 차이가 있다. 보통은 사용자가 아닌 특정 서비스에서 생성한 객체에 권한을 부여하는 데 사용된다.|
    |인스턴스 프로파일|사용자가 사람을 구분하고 그 사람에 권한을 주기 위한 개념이었다면 인스턴스 프로파일은 EC2 인스턴스를 구분하고 그 인스턴스에 권한을 주기 위한 개념이다.|
    
    
        
#### S3
* S3 
    * Simple Storage Service 
    * Object-based 
    * 99.99 availability / 11 9's durability
    * 객체 하나의 크기는 1Byte ~ 5TB
    * 저장 가능한 객체 갯수 무제한 
    * S3 name space 는 globally unique 함
    * 업로드가 성공하면 HTTP 200 status code 가 생성됨
    * 리전에 저장된 객체는 다른 리전으로 전송하지 않는 한 해당 리전을 벗어나지 않음
    * 웹(HTTP 프로토콜) 에서 파일에 접근할 수 있다.
    
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
        * 분 단위로 데이터의 일부를 검색해야하는 아카이브에 사용
        
    * S3 Glacier Deep Archive
        * 데이터 아카이빙용, 거의 액세스할 필요가 없는 데이터를 아카이빙 할 때 사용
        * 기본 검색 시간은 12시
        * 가장 저렴한 스토리지 옵션 
        
    * S3 Amazon S3 Reduced Redundancy Storage(RRS)
        * 중요하지 않고 재생성 가능한 데이터를 Amazon S3 스탠다드 스토리지 보다 낮은 수준의 중복성으로 저장할 수 있는 Amazon S3의 스토리지 옵션. 
    
    * [S3의 스토리지 클래스](https://bit.ly/2MWI8oY)
    
* Storage Gateway (AWS 서비스 중 하나)
    * On-promise 환경과 AWS 의 Storage 인프라를 연결시켜주는 서비스

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
    * VPC 의 Network ACL 은 Subnet 단위로 적용시킬 수 있음.
    * ACL 은 여러 서브넷에 적용이 가능하다. 하지만, 서브넷은 한번에 한개의 ACL 만 연결이 가능
    
* Security Group
    * Security Group 은 instance 단위로 적용시킬 수 있음
    * CL의 경우 Network 레벨에서의 방화벽이라면, Security Group 은 인스턴스 레벨의 방화벽
     
* NAT Gateway
    * Private Subnet 에서 다른 AWS 서비스에 연결해야하는 경우
    * 인터넷에서 Private instance 에 접근 불가 조건은 유지하면서 반대로 instance 에서 외부 인터넷으로 연결이 필요한 경우

* Direct Connect 
    * 내부 네트워크를 프라이빗 연결로 Direct Connect 와 연결
    
* Route 53
    * www.example.com 과 같은 도메인 이름을 192.0.2.1 과 같은 IP 주소로 변환
    * 사용자의 요청을 AWS 의 다른 서비스로 연결 
    * 도메인 등록 대행

* EBS
    * Elastic Block Store, 일종의 하드디스크
    * 볼륨 특성
        * General Purpose(gp2) SSD - 범용 SSD
            * 다양한 워크로드에 사용할 수 있으며 가격 대비 성능이 우수한 범용 SSD 불륨
        * Provisioned IOPS SSD (io1)- 프로비저닝된 IOPS SSD
            * 지연 시간이 짧거나 처리량이 많은 미션 크리티컬 워크로드에 적한한 고성능 SSD 불륨
        * Throughput Optimized HDD (st1) - 처리량에 최적화된 HDD 
            * 자주 액세스하는 처리량 집약적 워크로드에 적합한 저비용 HDD 볼륨
            * 저비용으로 일관되고 높은 처리량을 요구하는 스트리밍워크로드,
        * Cold HDD (sc1)
            * 자주 액세스하지 않는 워크로드에 적합한 최저 비용 HDD 볼륨        
    * 스냅샷은 자동으로 암호화
    
* EFS
    * Elastic File System, Amazon EC2 인스턴스에 사용할 수 있는 간단하고 확장 가능한 파일 스토리지
    * 저장 사용량 만큼만 비용이 발생
    * 파일이 추가되고 제거됨에 따라 용량이 자동으로 증가하고 줄어듬
    * EC2 에 사용할 수 있는 파일 스토리지 서비스
    * 최대 수천 개의 Amazon EC2 인스턴스를 위한 파일 시스템 인터페이스, 
    파일 시스템 엑세스 시맨틱(강력한 일관성 및 파일 잠금 등) 및 동시에 액세스 가능한 스토리지를 제공
    
* Trusted Advisor
    * AWS 의 모범 사례에 따라 리소스르 프로비저닝 하는데 도움이 되도록 실시간 지침을 제공
    * 성능, 비용 최적화, 보안, 내결함성의 통찰력을 제공

* CloudHSM
    * AWS 클라우드상의 관리형 하드웨어 보안 모듈(HSM)
    * AWS 클라우드에서 자체 암호화 키를 손쉽게 생성 및 사용할 수 있도록 지원하는 클라우드 기반 하드웨어 보안 모듈(HSM)


### DataBase

#### Aurora
* 3개의 가용 영역 전체에 자동 복제

#### DynamoDB
* 키-값 및 문서 데이터베이스
* 완전 관리형 
* DynamoDB Accelerator(DAX)
    * DAX 는 DynamoDB를 위한 가용성이 뛰어난 완전관리형 인 메모리 cache
    
#### Redshift
* Redshift 는 OLAP 데이터베이스를 위해 설계된 관리형 데이터 웨어하우스 솔루션
* PostgreSQL 기반
* Redshift 는 열 기반 스토리지를 사용
* 저장 속도와 효율성이 향상되고 개별 열의 데이터를 더 빨리 쿼리
* Data warehouse

##### KMS (Key Management Service)
*  데이터 암호화에 사용하는 암호화 키를 쉽게 생성하고 제어할 수 있게 해주는 관리형 서비스

#### ELB
* CLB (Classic Load Balancer)
    * L4 계층부터 L7 계층 까지 포괄적인 로드밸런싱이 가능
    * Sticky Session 등의 기능

* ALB (Application Load Balancer)
    * Classic Load Balancer 이후 출시된 서비스
    * HTTP 및 HTTPS 트래픽 로드밸런싱에 최적화
    * HTT 의 URL, FTP 의 파일명, 쿠키 정보 등을 분석해서 정교한 로드 밸런싱이 가능
    * Host-based Routing, Path-based Routing 지원 
    * Content Based Routing
    * Sticky Session 등의 기능
     
* NLB (Network Load Balancer)
    * AWS Load Balancer 에 Elastic IP(고정)을 부여
    * TCP 레이어 지원

#### Auto Scaling group
* EC2 인스턴스의 트래픽에 따라서 자동으로 추가적인 EC2 인스턴스를 생성 및 삭제해 최적의 서비스를 제공하는 기능

* Auto Scaling lifecycle hooks
    * EC2 instance 가 Scale in, Scale out 될때, Hook 으로 이벤트를 받을 수 있음
    * 서버가 시작하거나 종료될 때 Action 이 필요한 경우, Hook 으로 이벤트를 받을 수 있음
    
* Auto Scaling Cooldown
    * Auto Scaling 은 simple scaling policies 때, cooldown 를 지원
    * Simple scaling policies 을 사용하여 동적으로 scaling 하면 cooldown period 만큼 기다렸다가 scaling 을 재개
    
* Health Check Grace Period : 
    * EC2 인스턴스가 InService 상태일 때 설정한 초만큼 헬스 체크를 뒤로 미룸

#### Amazon EMR
* 빅데이터 플랫폼 - 데이터 집약적인 작업을 수행하는데 필요한 적당한 용량을 즉시 프로비저닝할 수 있음
* 하둡 프레입워크를 사용하여 하나의 대형 컴퓨터를 사용하여 데이터를 저장 및 처리하는 대신 여러 컴퓨터를 함께 클러스트링하여 대량의 데이터 세트를 병렬로 분석

#### Elastic ip 
#### Proxy
* [리버스_프록시 구성](https://www.joinc.co.kr/w/man/12/proxy)
* [포워드 프록시(forward proxy) 리버스 프록시(reverse proxy) 의 차이](https://www.lesstif.com/pages/viewpage.action?pageId=21430345) 