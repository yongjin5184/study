* Service
    * /etc/init.d 는 Sys V init script(SysVinit) 가 사용하는 스크립트를 담고 있음
        * 리눅스가 전통적으로 사용해온 서비스 관리 프로그램인 init 프로세스가 사용하는 스크립트들
    * /etc/init.d 안에는 init 프로세스가 특정한 서비스(tomcat) 을 start, stop, restart 할 수 있는 쉘 스크립트들이 들어있음
    * /etc/rc.d 디렉토리에 링크가 연결되어 부팅시에 자동으로 실행하게 할 수도 있음.
    * --status-all, start, stop, restart