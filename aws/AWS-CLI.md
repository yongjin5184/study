* AWS CLI
    * AWS Command Line Interface
    * 커멘드 라인에서 AWS API 를 직접 호출 할 수 있음
    
* Homebrew 를 사용해 awscli 를 설치
```java
$ brew install awscli
```

* 환경 변수 설정
    *  ~/.aws/credentials, ~/.aws/config 수정
    * aws_access_key_id 와 aws_secret_access_key 는 IAM 에서 확인
    
    ```java
      [default]
      region=ap-northeast-2
      output=json
    ```
  
  ```java
      [default]
      aws_access_key_id=.....
      aws_secret_access_key=....
  ```
  
* 연결된 계정으로 ec2 를 확인 
  ```java
    aws ec2 describe-instances
  ```
   
    