* 목적 
    * 주문과 주문기록 테이블에서 원하는 필드를 통계에 필요한 테이블로 적재 시키기 위함

* 기술 선택
    * Spring batch + Jenkins + Slack
    * ~~Quartz~~ 
    * ~~Spring boot + Lambda~~ 
    * 선택 이유
        1) 큐나 스케줄링등 다양한 방법을 통해서 대량 데이터 처리를 할 수 있지만, 사내에 조만간 필요하게 될 배치 시스템을 구축하기 위해 Spring Batch 를 선택
        2) Jenkins 를 통해 향후 Build 자동화의 가능성도 열어둠
*        
* 기술
    * Spring Boot
        * 2.2.x
    * Spring Batch
        * 4.2.x
    * Java 
        * java-1.8.0-openjdk
    * Build
        * ~~Maven~~
        * Gradle
    * Repository
        * ~~JPA~~
        * Mybatis-boot-starter 2.2.x
        
* 서버 구성
    * Nginx -> jenkins
    * Jenkins 의 **Shell script** 를 통해 **Schedule 되어있는 시간**에 jar 파일을 실행하는 형태
    * 현재 고민
        * 기존 local 에서 jar 를 만들어 서버로 복사하는 상황, Jenkins/Git action 를 이용해 자동화 할 수 있을 듯(?)
        
* 메타 테이블
    * 테이블 생성
        * 위치
            * org.springframework.batch:spring-batch-core > schema-mysql.sql
        * application.yml
            * spring.batch.initialize-schema 에서 설정 가능(always, never, embedded) 
    * JOB
        * BATCH_JOB_INSTANCE 
            * Job Parameter 에 따라 생성되는 테이블
        
        * BATCH_JOB_EXECUTION
            * JOB_INSTANCE 와 부모-자식 관계
            * JOB_INSTANCE 가 성공/실패한 내역을 가지고 있음
        
        * BATCH_JOB_EXECUTION_PARAM
            * BATCH_JOB_EXECUTION 테이블이 생성될 당시에 입력 받은 Job Parameter 를 담고 있음
    
        * BATCH_JOB_EXECUTION_CONTEXT
            * JOB 의 ExecutionContext 와 관련된 정보를 기록
            
        * BATCH_JOB_SEQ, BATCH_JOB_EXECUTION_SEQ
            * 시퀀스 테이블
        
        * 참고
            * 동일한 Job Parameter 로 성공한 기록이 있으면 재수행이 안됨
    * STEP
        * BATCH_STEP_EXECUTION 
            * Job 에서 실행되는 Step 의 정보 기록
            * JOB_EXECUTION 과 1 : N 관계
            
        * BATCH_STEP_EXECUTION_CONTEXT
            * STEP 의 ExecutionContext 와 관련된 정보를 기록
            * SHORT_CONTEXT Field
                ```java
              {"batch.taskletType":"org.springframework.batch.core.step.item.ChunkOrientedTasklet",
              "MyBatisPagingItemReader.read.count":4,
              "batch.stepType":"org.springframework.batch.core.step.tasklet.TaskletStep"}
                ```
        * BATCH_STEP_EXECUTION_SEQ
            * 시퀀스 테이블
    
* Spring Batch 기본 구조
    * JobLauncher
        * 배치 애플리케이션을 기동하기 위한 인터페이스
        * 모든 배치 애플리케이션은 이 클래스를 통해 실행됨
    * Job
        * 일련의 처리 과정을 하나의 단위로 만든 실행 단위
    * Step
        * Job 을 구성하는 세부 처리 단위
        * 하나의 Job 은 N개의 Step 으로 구성 될 수 있음
        * Step 은 **Chunk 방식**이나 **Tasklet 방식** 중 한가지 형태로 실행
    * JobRepository
        * Job 이나 Step 의 상태를 관리
    
    * JobParameter
        * 외부에서 파라미터를 받아 Batch 컴포넌트에서 쓰기 위한 목적
    
    * Chunk
        * 데이터를 한 건씩 처리하는 것이 아니라 일정 개수 단위(청크)로 처리하는 방식
    
    * JobScope
        * Step 에서 사용
    
    * StepScope
        * Tasklet, ItemReader, ItemProcessor, ItemWriter 에서 사용
        * 호출하는 쪽에서 파라미터에 null 이 들어가는 이유는 애플리케이션 실행시 파라미터를 할당하지 않기 때문
        * Spring 의 기본 Scope 는 singleton, @StepScope 를 사용하게 되면 
        Spring Batch 가 Spring 컨테이너를 통해 지정된 Step 의 실행시점에 해당 컴포넌트를 Spring Bean 으로 생성
        * 이로 인해 얻는 이점
            * Late Binding 이 가능해서, 애플리케이션이 실행되는 시점이 아니라 비지니스 로직 처리 단계(Controller, Service) 에서
            Job Parameter 를 할당할 수 있음
            * 동일한 컴포넌트를 병렬로 사용할 때 각각의 Step 에서 별도의 Tasklet 를 생성하고 관리하기 때문에 서로의 상태를 침범할 일이 없음
     
        * application.yml
            * JOB 이 여러개 생길 때, 해당 JOB 만을 실행 시키기 위한 방법
                * spring.batch.job.names 을 통해 Program arguments 로 job name 을 설정
                * @ConditionalOnProperty 으로 property 의 value 가 job name 을 가지고 있는지 체크
     
    * 배치 실행
        * 정상 종료시
            * 일단 정상적으로 종료된 배치 처리는 다시 실행할 수가 없음
            * JobInstanceAlreadyCompleteException 예외 발생
            * 이때, 같은 Parameter 의 Job 을 실행하기 위해 JobParameter 에 System.currentTimeMillis() 를 넘기는 방법이 있
        
        * 비정상 종료시 
            * Step1, Step2 로 되어있는 Job 의 경우 Step1 까지 마치고 Step2 에서 에러가 났을 시 
            * BATCH_STEP_EXECUTION 테이블 
                * Step1 COMPLETED 처리
                * Step2 FAILED 처리
            * 반드시 실행한 배치 처리와 명령행 인수를 똑같이 지정하여 재처리
                * Step2 실패한 부분에서 부터 다시 실
        
* 검증
    * 이전 테이블의 데이터를 통계에 필요한 테이블로 배치를 통해 적재
    * RDS performance Insight 로 확인하면서 데이터 적재 
    * 6만개
        * 소요시간 : 102s
    * 9만개
        * 소요시간 : 139s
    * 12만개 
        * 소요시간 : 174s
    * 15만개
        * 소요시간 : 224s
    * 22만개
        * 소요시간 : 319s
    * 26만개
        * 소요시간 : 404s
     
* 참고자료
    * 기억보단기록을 [https://jojoldu.tistory.com/326?category=902551]
    * 스프링 철저 입문 [http://www.yes24.com/Product/Goods/59192207]
    * 처음부터 배우는 스프링 부트2 [http://www.yes24.com/Product/Goods/64584833]

* 회의 후, 고려해야할 사항
    * 병렬 처리 어떻게 할 것인가, 지금은 Job이 하나이기 때문에 상관없지만 나중에는 고려해야할 듯
    * 다시 구축 할 때, 누군가가 보고 할 수 있는 방법을 적어 놓기 → 문서화
    * 청크를 변경하면서 데이터를 넣어보면 시간이 어떻게 되는지 테스트해 보자
    * Writer 의 IO 가 어떻게 되는지 확인해보자
    * 애매한 로직은 어디 들어가야하는지 ex) 로직이 끝나고 문자 보낸다. 이는 process 에서 해야할 까? writer 에서 해야할까?
    * 다음 스프린트에 비중이 낮은거 가져와서 배치로 돌려보자
        * 배치로 할 만한게 어떤게 있는지 확인
    * 새벽에 한번만 돌아가는데 EC2 죽여야하지 않나?
    * jenkins 서버를 나눠보는 것도 고려



    