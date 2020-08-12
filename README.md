# spring-reative-programming

### 1.Reactive Stream 배경
       - Iterable  <--> Observer(duality)
         (pull)         (push)
         
       - 1) Complete를 어떻게 시킬 것인가?
       - 2) Error를 어떻게 처리 해야 하는가?
       
       
### 2. 본격적으로 들어가보자
       url : https://github.com/reactive-streams/reactive-streams-jvm
       
       
### Servlet 3.0 : 비동기 서블릿
       - HTTP connection은 이미 논블록킹 IO
       - 서블릿 요청 읽기, 응답쓰기는 블록킹
       - 비동기 작업 시작 즉시 서블릿 쓰레드 반납
       - 비동기 작업이 완료되면 서블릿 쓰레드 재할당
       - 비동기 서블릿 컨텍스트 이용 (AsyncContext)
### Servlet 3.1 : 논블록킹 IO
        - 논블록킹 서블릿 요청, 응답 처리
        - Callback
        
        
### 비동기 서블릿
        - DeferredResult 큐
       