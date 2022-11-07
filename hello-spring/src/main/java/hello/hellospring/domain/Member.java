package hello.hellospring.domain;

import javax.persistence.*;

@Entity  // JPA가 관리하는 Entity임을 나타내는 Annotation이다.
public class Member {

    // @ID는 해당 변수가 PK(Primary Key)임을 의미한다.
    // @GeneratedValue(strategy = GenerationType.IDENTITY)는 해당 변수가 DB 내부에서 자동으로 생성해 준다는 것을 의미한다(h2의 member 테이블에서 id값이 자동으로 생성되는 것에 해당).
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "username"): 만일 DB Table상에서 Column명이 username인 경우 해당 annotation으로 mapping한다.
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
