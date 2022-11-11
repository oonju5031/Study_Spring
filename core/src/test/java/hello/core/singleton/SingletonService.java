package hello.core.singleton;

public class SingletonService {
    // 클래스의 인스턴스(객체)가 오직 1개만 생성하도록 보장하는 디자인 패턴이다.

    // Singleton pattern을 구현하는 방법은 여러 가지가 있다. 여기서는 객체를 미리 생성해두는 가장 간단하고 안전한 방법을 사용한다.
    // 객체 인스턴를 2개 이상 생성하지 못하도록 막아야 하므로, private 생성자를 이용하여 외부에서 임의로 new키워드를 사용할 수 없도록 막는다.

    // 자기 자신을 내부에 가지되, Static으로 선언하여 클래스 레벨에서 객체가 단 하나만 생성되도록 한다.
    // 코드 실행 시 자동으로 SingletonService가 하나 생성되어 static 영역의 instance 변수에 들어간다.
    private static final SingletonService instance = new SingletonService();

    // SingletonService 객체 인스턴스가 필요한 경우 해당 public static 메소드를 통해서만 조회하도록 허용한다.
    public static SingletonService getInstance() {
        return instance;
    }

    // private 생성자를 사용하여 외부에서 새로 생성(new SingletonService)하지 못하도록 막는다.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
