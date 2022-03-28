package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    // Many To Many
    @Id
    @GeneratedValue
    private Long id;

    private String name;

//    // 양방향
//    @ManyToMany(mappedBy = "products")
//    private List<Member> members = new ArrayList<>();

    // many to many 대신 사용
    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();

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
