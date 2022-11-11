package hello.core.singleton;

public class StatefulService {
    /*
    * 싱글톤 패턴은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 상태를 유지(stateful)하게 설계하면 안 된다.
    * 즉 무상태(stateless)로 설계하여야 하는데, 이를 만족시키는 조건은 다음과 같다.
    *   1. 특정 클라이언트에 의존하는 필드가 없어야 한다.
    *   2. 특정 클라이언트가 값을 변경할 수 있는 필드가 없어야 한다.
    *   3. 가급적이면 읽기만 가능하여야 한다.
    *   4. 필드 대신 자바에서 공유되지 않는 지역변수/파라미터/ThreadLocal 등을 사용하여야 한다.
    * */

    // 다음은 Spring Container(= Singleton Container)가 stateless가 아닐 경우 발생하는 장애에 대한 클래스이다.

    // 상태를 유지하는 필드: stateful
    private int price;

    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price;  // 여기에서 문제가 발생
    }

    public int getPrice() {
        return price;
    }
}
