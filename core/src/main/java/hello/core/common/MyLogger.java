package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
// web scope: html 요청 하나가 들어오고 나갈 때까지 유지되는 스코프이며, 각각의 http 요청마다 별도의 bean instance가 생성되고 관리된다.
// Proxy를 이용한 빈 생성 지연: CGLIB 라이브러리로 일단 내 클래스를 상속받은 가짜 프록시 객체를 생성하여 주입한다. 이 프록시 객체는 요청을 받을 때 진짜 bean을 요청하는 위임 로직이 들어 있다.
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;  // Universally Unique IDentifier
    private String requestURL;

    // requestURL은 bean이 생성되는 시점에는 알 수 없으므로, setter를 통해 외부에서 입력받도록 한다.
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        // LOG: [UUID][requestURL]{message}
        System.out.println("[" + uuid + "][" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        // Bean이 생성되는 시점에 자동으로 초기화 메소드를 사용하여 uuid를 생성 및 저장한다.
        // 이 bean은 HTTP 요청당 하나씩 생성되므로, 각 HTTP요청을 구분할 수 있게 된다.
        uuid = UUID.randomUUID().toString();  // unique한 UUID를 무작위로 생성
        System.out.println("[" + uuid + "] request scope bean create: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close: " + this);
    }
}
