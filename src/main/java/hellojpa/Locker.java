package hellojpa;

import javax.persistence.*;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 일대일 양방향
    @OneToOne(mappedBy = "locker")
    private Member member;

}
