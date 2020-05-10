* 목적 
    * 주문과 주문기록 테이블에서 원하는 필드를 통계에 필요한 테이블로 적재 시키기 위함

* 기술 선택
    * Spring batch + Jenkins + Slack
    * ~~Quartz~~ 
    * ~~Spring boot + Lambda~~ 
    
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
    * Nginx -> jenkins 포트 포워딩
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
         
     * Chunk
     
     * StepScope
     
     
* 참고자료
    * 기억보단기록을 [https://jojoldu.tistory.com/326?category=902551]
    * 스프링 철저 입문 [http://www.yes24.com/Product/Goods/59192207]
    * 처음부터 배우는 스프링 부트2 [http://www.yes24.com/Product/Goods/64584833]




    