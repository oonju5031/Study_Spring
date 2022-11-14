package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// Bean의 Life Cycle을 관리하는 3가지 방법
public class NetworkClient /*implements InitializingBean, DisposableBean*/ {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("disconnect: " + url);
    }

    /*
    방법 1: 인터페이스 InitializingBean, DisposalBean을 상속한다.
    다음과 같은 단점을 가진다.
    - 스프링 전용 인터페이스이기 때문에 해당 코드가 스프링에 의존하게 된다.
    - 초기화/소멸 메소드의 이름을 변경할 수 없다.
    - 코드를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
     */

    // InitializingBean의 method: 의존관계 주입(=setter 호출)이 끝나면 호출되는 초기화 지원 method
/*    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }*/

    // DisposableBean의 method: Bean이 종료되기 직전에 호출되는 소멸 지원 method
/*    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }*/


    /*
    방법 2: 빈 등록 초기화, 소멸 메소드 지정
    @Bean(initMethod = "functionA", destroyMethod = "functionB")로 초기화 및 소멸 메소드를 지정할 수 있으며, 다음과 같은 이점이 있다.
    - 코드가 스프링에 의존하지 않는다.
    - 초기화/소멸 메소드의 이름을 자유로이 지정할 수 있다.
    - 코드가 아니라 설정 정보를 사용하기 때문에, 코드를 고칠 수 없는 외부 라이브러리에도 적용할 수 있다.

    +) 종료 method 추론
        initMethod는 default value가 ""이지만, destroyMethod는 default vaule로 "(inferred)" (추론)를 가진다.
        라이브러리의 종료 메소드 이름은 대부분 "close" 또는 "shutdown"인 것을 감안하여 해당 이름의 메소드를 추론하여 자동으로 호출해 준다.
        이는 외부 라이브러리를 사용하는 경우 유용하게 사용된다.
        만일 해당 기능을 사용하고 싶지 않을 경우 destroyMethod = ""로 설정하여 주면 된다.
     */

    // Bean 설정에 initMethod로 지정하여 의존관계 주입(=setter 호출)이 끝나면 호출되는 초기화 지원 method
/*    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    // Bean 설정에 destroyMethod로 지정하여 Bean이 종료되기 직전에 호출되는 소멸 지원 method
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }*/


    /*
    방법 3: Annotation @PostConstruct, @PreDestroy 사용
    - 해당 두 annotation은 javax(공식) JSR-250에서 지원하므로 스프링에 의존하지 않는다.
    - 최신 스프링에서 가장 권장하는 방법이다.
    - 단, 코드를 수정해야 하므로 외부 라이브러리에는 적용할 수 없다. 이 경우엔 방법 2를 사용할 것.
     */

    // Annotation을 이용하여 의존관계 주입(=setter 호출)이 끝나면 호출되는 초기화 지원 method
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    // Annotation을 이용하여 Bean이 종료되기 직전에 호출되는 소멸 지원 method
    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
