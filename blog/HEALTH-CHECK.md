* 서버의 상태를 체크하고 죽은 서버를 살려야 한다. 어떤게 필요할 까?
두 개의 람다
    1. Health Check lambda
    
    ```javascript 1.8
    import os
    import requests
    
    def check(url):
        r = requests.get(url)
        return str(r.status_code)
    
    def lambda_handler(event, context):
        url = os.environ['url']
        response_code = os.environ['response_code']
        if check(url) != response_code:
            raise Exception("{url} is down".format(url=url))
    ```
    2. Server restart lambda
    
    ```javascript 1.8
    var exec = require('ssh-exec')
    var v_host = 'XX.XX.XX.XXX'
    exec('ls -lh', {
      user: 'root',
      host: 'XX.XX.XX.XXX', 
      password: 'password'
    }).pipe(process.stdout , function (err, data) {
        if ( err ) { console.log(v_host); console.log(err); }
      console.log(data)
    })
    ```

* 의문사항)
    * WAS 를 다시 띄울 때, 외부 서버에 직접 접속해서 명령어를 실행해도 되는지 된다면, (외부 서버 접근을 열어줘야 할 듯)
    * 람다에 비밀번호 정보가 있는 코드가 있으면 안될 것 같은데, 이 처리는 어떻게 해야하는지
 
